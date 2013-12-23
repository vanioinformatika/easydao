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

import hu.vanio.easydao.type.Oracle11JdbcType;
import java.util.HashMap;

/**
 * PostgreSQL database selects.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Oracle11Config implements Config {

    /* Sql query for table list, result: TABLE_NAME, COMMENTS fields */
    final String selectForTableList = "select ut.table_name as TABLE_NAME, tc.comments as COMMENTS"
            + " from USER_TABLES ut, USER_TAB_COMMENTS tc"
            + " where tc.TABLE_NAME = ut.TABLE_NAME";
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
            + " where utc.table_name = ? and ucc.table_name = utc.table_name and ucc.column_name = utc.column_name";
    /* Sql query for primary key field name list, result: COLUMN_NAME */
    final String selectForPrimaryKeyFieldNameList = "select COLUMN_NAME"
            + " from USER_CONS_COLUMNS c, USER_CONSTRAINTS t"
            + " where c.TABLE_NAME=upper(?) and"
            + " t.CONSTRAINT_TYPE = 'P' and"
            + " t.CONSTRAINT_NAME = c.CONSTRAINT_NAME"
            + " order by c.position";

    public Oracle11Config() {
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
    public HashMap<String, Class> getJdbcTypeMapping() {
        return Oracle11JdbcType.MAP;
    }

}
