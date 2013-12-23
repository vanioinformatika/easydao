/*
 * The MIT License
 *
 * Copyright 2013 Vanio Informatika Kft.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hu.vanio.easydao.modelbuilder;

import hu.vanio.easydao.model.Field;
import hu.vanio.easydao.model.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements model builder for PostgreSQL database.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class PostgreSqlModelBuilderImpl extends ModelBuilder {

    public PostgreSqlModelBuilderImpl(Connection con, boolean hasTablePrefix, boolean hasTablePostfix, boolean hasFieldPrefix, boolean hasFieldPostfix) {
        super(con, hasTablePrefix, hasTablePostfix, hasFieldPrefix, hasFieldPostfix);
    }

    @Override
    public List<Table> getTableList() throws SQLException {
        System.out.println("getTableList");
        String query = "select c.relname as TABLE_NAME, obj_description(c.oid) as COMMENT from pg_catalog.pg_class c"
                + " where c.relname like '%' and c.relname not like 'sql_%' and c.relname not like 'pg_%' and c.relkind = 'r' order by TABLE_NAME;";

        List<Table> tableList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(query);) {
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    String tableComment = rs.getString("COMMENT");

                    System.out.println("table name: " + tableName + " = " + tableComment);

                    if (tableComment == null) {
                        tableComment = EMPTY_COMMENT;
                    }
                    String javaName = createJavaName(tableName, true, hasTablePrefix, hasTablePostfix);
                    Table table = new Table(tableName, tableComment, javaName, null);
                    tableList.add(table);
                }
            }
        }
        return tableList;
    }

    @Override
    protected List<String> getPrimaryKeyFieldNameList(String tableName) throws SQLException {
        String query = "select a.attname as COLUMN_NAME "
                + "from pg_catalog.pg_constraint cn, pg_catalog.pg_attribute a, pg_catalog.pg_class c "
                + "where c.relname = ?  "
                + "  and c.relkind = 'r' "
                + "  and a.attrelid = c.oid "
                + "  and a.attnum > 0  "
                + "  and a.attisdropped is false "
                + "  and cn.conrelid = c.oid and cn.contype = 'p' "
                + "  and a.attnum = any(cn.conkey)";

        List<String> fieldNameList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, tableName);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    String fieldName = rs.getString("COLUMN_NAME");
                    fieldNameList.add(fieldName);
                }
            }
        }
        return fieldNameList;
    }

    @Override
    protected List<Field> getFieldList(String tableName) throws SQLException {
        System.out.println("\ngetFieldList of " + tableName.toUpperCase());
        String query = "select a.attname as COLUMN_NAME,"
                + " pg_catalog.format_type(a.atttypid, a.atttypmod) as DATA_TYPE,"
                + " a.attnotnull as NOT_NULL,"
                + " a.attndims as ARRAY_DIM_SIZE,"
                + " a.atthasdef as HAS_DEFAULT_VALUE,"
                + " col_description(c.oid, a.attnum) as COMMENT"
                + " from pg_catalog.pg_class c, pg_catalog.pg_attribute a"
                + " where c.relname = ?"
                + " and c.relkind = 'r'"
                + " and a.attrelid = c.oid"
                + " and a.attnum > 0"
                + " and a.attisdropped is false"
                + " order by a.attnum";
        List<Field> fieldList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, tableName);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    boolean nullable = !rs.getBoolean("NOT_NULL");
                    boolean array = rs.getInt("ARRAY_DIM_SIZE") > 0;
                    String dbName = rs.getString("COLUMN_NAME");
                    boolean primaryKey = getPrimaryKeyFieldNameList(tableName).indexOf(dbName) >= 0;
                    String dbType = rs.getString("DATA_TYPE");
                    String comment = rs.getString("COMMENT");
                    String javaName = createJavaName(dbName, false, hasFieldPrefix, hasFieldPostfix);
                    Class javaType = getJavaType(dbType);
                    Field field = new Field(primaryKey, nullable, array, dbName, dbType, comment, javaName, javaType);

                    System.out.println(field.toString());

                    fieldList.add(field);
                }
            }
        }
        return fieldList;
    }

}
