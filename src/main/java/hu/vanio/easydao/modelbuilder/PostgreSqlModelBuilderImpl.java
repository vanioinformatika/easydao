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
public class PostgreSqlModelBuilderImpl implements ModelBuilder {

    /* ModelBuilder util inject */
    private ModelBuilderUtil gu;

    public PostgreSqlModelBuilderImpl(ModelBuilderUtil gu) {
        this.gu = gu;
    }

    @Override
    public List<Table> getTableList(Connection con, boolean hasPrefix, boolean hasPostfix) throws SQLException {
        System.out.println("getTableList");
        String query = "select"
                + " c.relname as TABLE_NAME,"
                + " obj_description(c.oid) as COMMENT"
                + " from pg_catalog.pg_class c "
                + " where c.relname like ? and c.relkind = 'r'"
                + " order by TABLE_NAME";

        List<Table> tableList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(query);) {
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    String tableComment = rs.getString("COMMENT");
                    System.out.println("table name: " + tableName);
                    if (tableComment == null) {
                        tableComment = gu.EMPTY_COMMENT;
                    }
                    String javaName = gu.createJavaName(tableName, true, hasPrefix, hasPostfix);

                    // @FIXME: processing field for table!
                    List<Field> fieldList = new ArrayList<>();
//                    for (Table tableData : tableList) {
//                        Set<String> pkFields = getPrimaryKeyFieldNames(tableData.getDbName());
//                        List<FieldData> fields = getFields(tableData.getDbName(), pkFields);
//                        tableData.setFields(fields);
//                    }
                    Table table = new Table(tableName, tableComment, javaName, fieldList);
                    tableList.add(table);
                }
            }
        }
        return tableList;
    }

}
