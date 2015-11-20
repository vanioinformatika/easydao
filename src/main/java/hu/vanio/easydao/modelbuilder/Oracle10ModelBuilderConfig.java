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

/**
 * Oracle 10 configuration.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Oracle10ModelBuilderConfig extends OracleModelBuilderConfig {

    final String selectForIndexList = 
            "select decode(idx.uniqueness, 'UNIQUE', 't', 'NONUNIQUE', 'f') as UNIQUENESS, idx.index_name as INDEX_NAME, idx.table_name as TABLE_NAME, " +
            "            ( select wm_concat(col.column_name) COLUMN_NAMES " +
            "              from user_ind_columns col  " +
            "              where col.table_name = idx.table_name and col.index_name = idx.index_name " +
            "              group by col.index_name) as COLUMN_NAMES " +
            "from user_indexes idx " +
            "where idx.table_name = upper(?) " +
            "  and idx.index_type = 'NORMAL' " +
            "  and idx.dropped = 'NO' " +
            "order by idx.table_name";

    @Override
    public String getSelectForIndexList() {
        return this.selectForIndexList;
    }

}
