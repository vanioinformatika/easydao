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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Szalai Gyula <gyula.szala@vanio.hu>
 */


public abstract class OracleModelBuilderConfig extends ModelBuilderConfig implements IModelBuilderConfig {
    
    /* Sql query for table list, result: TABLE_NAME, COMMENTS fields */
    final String selectForTableList = "select ut.table_name as TABLE_NAME, tc.comments as COMMENTS"
            + " from user_tables ut, user_tab_comments tc"
            + " where tc.table_name = ut.table_name and ut.table_name not like '%$%'";
    /* Sql query for field list by table name, result: COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENTS */
    final String selectForFieldList = "select utc.column_name as COLUMN_NAME,"
            + " decode(utc.char_used, 'C', utc.char_length, utc.data_length) as DATA_LENGTH,"
            + " case when utc.data_type = 'NUMBER' then lower(concat(concat(concat(concat(concat(utc.data_type, '('), utc.data_precision), ','), utc.data_scale), ')'))"
            + "      when utc.data_type = 'VARCHAR2' then lower(concat(concat(concat(utc.data_type, '('), data_length), ')'))"
            + " else lower(utc.data_type)"
            + " end as DATA_TYPE,"
            + " case when utc.nullable = 'Y' then 1"
            + " else 0"
            + " end as NOT_NULL,"
            + " 0 as ARRAY_DIM_SIZE,"
            + " null as HAS_DEFAULT_VALUE,"
            + " ucc.comments as COMMENTS"
            + " from user_tab_cols utc, user_col_comments ucc"
            + " where utc.table_name = ? and ucc.table_name = utc.table_name and ucc.column_name = utc.column_name"
            + " order by column_id";
    /* Sql query for primary key field name list, result: COLUMN_NAME */
    final String selectForPrimaryKeyFieldNameList = "select COLUMN_NAME"
            + " from user_cons_columns c, user_constraints t"
            + " where c.table_name=upper(?) and"
            + " t.constraint_type = 'P' and"
            + " t.constraint_name = c.constraint_name"
            + " order by c.position";
    
    final String selectForSequenceCheck = "SELECT COUNT(*) FROM user_sequences WHERE sequence_name = ?";
    
    /* Data type mapping: database -> java */
    public static final Map<String, String> JAVA_TYPE_MAP = new HashMap<>();

    /* see: http://docs.oracle.com/cd/B19306_01/java.102/b14188/datamap.htm */
    static {
        JAVA_TYPE_MAP.put("char|long|string|varchar|varchar2", String.class.getName());
        JAVA_TYPE_MAP.put("bytea|raw|long raw", byte[].class.getName());
        JAVA_TYPE_MAP.put("binary_integer|natural|naturaln|pls_integer|positive|positiven|signtype|int|integer|smallint", Integer.class.getName());
        JAVA_TYPE_MAP.put("dec|decimal", BigDecimal.class.getName());
        JAVA_TYPE_MAP.put("double precision|float", Double.class.getName());
        JAVA_TYPE_MAP.put("real", Float.class.getName());
        JAVA_TYPE_MAP.put("rowid", java.sql.RowId.class.getName());
        JAVA_TYPE_MAP.put("boolean", Boolean.class.getName());
        JAVA_TYPE_MAP.put("clob", java.sql.Clob.class.getName());
        JAVA_TYPE_MAP.put("blob", java.sql.Blob.class.getName());

        JAVA_TYPE_MAP.put("date", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("double precision|float8", Double.class.getName());

        JAVA_TYPE_MAP.put("number|numeric", Integer.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\(,\\)", Integer.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\(,0\\)", Long.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\([1-9]\\)", Integer.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\([1-9],[0]\\)", Integer.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\([1][0-8]\\)", Long.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\([1][0-8],[0]\\)", Long.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\((19|[2-9]\\d|\\d{3,})\\)", String.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\((19|[2-9]\\d|\\d{3,}),[0]\\)", String.class.getName());
        JAVA_TYPE_MAP.put("(number|numeric)\\([\\d]+,[1-9]+\\)", Double.class.getName());

        JAVA_TYPE_MAP.put("date", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("timestamp with tz", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("timestamp with local tz", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("timestamp", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("timestamp\\([0-9]\\)", java.sql.Timestamp.class.getName());
        JAVA_TYPE_MAP.put("varchar2\\([\\d]*\\)", String.class.getName());
        JAVA_TYPE_MAP.put("varray", java.sql.Array.class.getName());
    }

    @Override
    public String getSelectForTableList() {
        return this.selectForTableList;
    }

    @Override
    public abstract String getSelectForIndexList();
    
    @Override
    public String getSelectForFieldList() {
        return this.selectForFieldList;
    }

    @Override
    public String getSelectForPrimaryKeyFieldNameList() {
        return this.selectForPrimaryKeyFieldNameList;
    }
    
    @Override
    public String getSelectForSequenceCheck() {
        return this.selectForSequenceCheck;
    }

    @Override
    public String getJavaType(String dbType) throws IllegalArgumentException {
        return convertToJavaType(JAVA_TYPE_MAP, dbType);
    }
    
    
}
