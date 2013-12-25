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

import java.util.HashMap;

/**
 * PostgreSQL 9 configuration.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class PostgreSql9Config extends Config {

    /* Sql query for table list, result: TABLE_NAME, COMMENTS fields */
    final String selectForTableList = "select c.relname as TABLE_NAME, obj_description(c.oid) as COMMENTS from pg_catalog.pg_class c"
            + " where c.relname like '%' and c.relname not like 'sql_%' and c.relname not like 'pg_%' and c.relkind = 'r' order by TABLE_NAME;";
    /* Sql query for field list by table name, result: COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENTS */
    final String selectForFieldList = "select a.attname as COLUMN_NAME,"
            + " pg_catalog.format_type(a.atttypid, a.atttypmod) as DATA_TYPE,"
            + " a.attnotnull as NOT_NULL,"
            + " a.attndims as ARRAY_DIM_SIZE,"
            + " a.atthasdef as HAS_DEFAULT_VALUE,"
            + " col_description(c.oid, a.attnum) as COMMENTS"
            + " from pg_catalog.pg_class c, pg_catalog.pg_attribute a"
            + " where c.relname = ?"
            + " and c.relkind = 'r'"
            + " and a.attrelid = c.oid"
            + " and a.attnum > 0"
            + " and a.attisdropped is false"
            + " order by a.attnum";
    /* Sql query for primary key field name list, result: COLUMN_NAME */
    final String selectForPrimaryKeyFieldNameList = "select a.attname as COLUMN_NAME "
            + "from pg_catalog.pg_constraint cn, pg_catalog.pg_attribute a, pg_catalog.pg_class c "
            + "where c.relname = ?  "
            + "  and c.relkind = 'r' "
            + "  and a.attrelid = c.oid "
            + "  and a.attnum > 0  "
            + "  and a.attisdropped is false "
            + "  and cn.conrelid = c.oid and cn.contype = 'p' "
            + "  and a.attnum = any(cn.conkey)";

    /* Data type mapping: database -> java */
    public static final HashMap<String, Class> JAVA_TYPE_MAP = new HashMap<>();

    /* see: http://www.postgresql.org/docs/current/static/datatype.html */
    static {
        JAVA_TYPE_MAP.put("boolean", Boolean.class);
        JAVA_TYPE_MAP.put("boolean\\[\\]", Boolean[].class);
        JAVA_TYPE_MAP.put("bytea", Byte[].class);
        JAVA_TYPE_MAP.put("character", String.class);
        JAVA_TYPE_MAP.put("character\\([\\d]+\\)", String.class);
        JAVA_TYPE_MAP.put("character\\([\\d]+\\)\\[\\]", String[].class);
        JAVA_TYPE_MAP.put("character\\[\\]", String[].class);
        JAVA_TYPE_MAP.put("character varying", String.class);
        JAVA_TYPE_MAP.put("character varying\\([\\d]+\\)", String.class);
        JAVA_TYPE_MAP.put("character varying\\([\\d]+\\)\\[\\]", String[].class);
        JAVA_TYPE_MAP.put("date", java.sql.Timestamp.class);
        JAVA_TYPE_MAP.put("double precision|float8", Double.class);
        JAVA_TYPE_MAP.put("integer|int|int4|smallint|smallserial|serial", Integer.class);
        JAVA_TYPE_MAP.put("bigint|int8|bigserial|money", Long.class);
        JAVA_TYPE_MAP.put("json", String.class);
        JAVA_TYPE_MAP.put("numeric", Integer.class);
        JAVA_TYPE_MAP.put("numeric\\([1-9]\\)", Integer.class);
        JAVA_TYPE_MAP.put("numeric\\([1-9],[0]\\)", Integer.class);
        JAVA_TYPE_MAP.put("numeric\\([1][0-9]\\)", Long.class);
        JAVA_TYPE_MAP.put("numeric\\([1][0-9]+,[0]\\)", Long.class);
        JAVA_TYPE_MAP.put("numeric\\([\\d]+,[1-9]+\\)", Double.class);
        JAVA_TYPE_MAP.put("numeric\\[\\]", Integer[].class);
        JAVA_TYPE_MAP.put("numeric\\([1][0-9]\\)\\[\\]", Long[].class);
        JAVA_TYPE_MAP.put("numeric\\([1-9]\\)\\[\\]", Integer[].class);
        JAVA_TYPE_MAP.put("numeric\\([\\d]+,[\\d]+\\)\\[\\]", Double[].class);
        JAVA_TYPE_MAP.put("timestamp without time zone", java.sql.Timestamp.class);
        JAVA_TYPE_MAP.put("timestamp with time zone", java.sql.Timestamp.class);
        JAVA_TYPE_MAP.put("timestamp", java.sql.Timestamp.class);
        JAVA_TYPE_MAP.put("text", String.class);
        JAVA_TYPE_MAP.put("uuid", String.class);
        JAVA_TYPE_MAP.put("xml", String.class);
    }

    public PostgreSql9Config() {
    }

    @Override
    public String getSelectForTableList() {
        return this.selectForTableList;
    }

    @Override
    public String getSelectForFieldList() {
        return this.selectForFieldList;
    }

    @Override
    public String getSelectForPrimaryKeyFieldNameList() {
        return this.selectForPrimaryKeyFieldNameList;
    }

    @Override
    public Class getJavaType(String dbType) throws IllegalArgumentException {
        return convertToJavaType(JAVA_TYPE_MAP, dbType);
    }
}
