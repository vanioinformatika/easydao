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
import java.util.Map;

/**
 * Database model builder configuration for model builder
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
abstract class ModelBuilderConfig {

    /**
     * Return java type of field.
     * If you need, then override in descendants.
     * @param typeMap database specific type map database type string (regular expression) -> java type.
     * @param dbType database type string, i.e: numeric(10,2), date, timestamp
     * @return Class java class of db field type
     */
    final protected Class convertToJavaType(HashMap<String, Class> typeMap, String dbType) throws IllegalArgumentException {
        Class clazz = null;
        boolean found = false;
        for (Map.Entry<String, Class> e : typeMap.entrySet()) {
            if (dbType.matches(e.getKey())) {
                // found type
                clazz = e.getValue();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("There is no Java type definiton for " + dbType + " database type!");
        }
        return clazz;
    }

    /**
     * Return replacement name of table or field name.
     * You must use it, when database table names does not always use table or field name convention,
     * i.e.: CUS_CUSTOMER (good), CUS_ORDER (good), CSTATISTIC (it is a wrong table name declaration).
     * @param dbName database table or field name
     * @return replacementMap name map for the table or field
     */
    final protected String getReplacementName(HashMap<String, String> replacementMap, String dbName) {
        String rn = replacementMap.get(dbName);
        if (rn == null) {
            rn = replacementMap.get(dbName.toLowerCase());
            if (rn == null) {
                replacementMap.get(dbName.toUpperCase());
            }
        }
        return rn;
    }

    /**
     * Return replacement map of table names.
     * @return map
     */
    abstract public HashMap<String, String> getReplacementNameOfTables();

    /**
     * Return replacement map of field names.
     * @return map
     */
    abstract public HashMap<String, String> getReplacementNameOfFields();

    /**
     * Return java type.
     * @param dbType field type string from database
     * @return java class
     * @throws IllegalArgumentException java class not found for dbType
     */
    abstract public Class getJavaType(String dbType) throws IllegalArgumentException;

    /**
     * Sql query for table list, result: TABLE_NAME, COMMENT fields
     * @return SQL query, TABLE_NAME, COMMENT
     */
    abstract public String getSelectForTableList();

    /**
     * Sql query for field list by table name, result: COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENT
     * @return SQL query, COLUMN_NAME, DATA_TYPE, NOT_NULL, ARRAY_DIM_SIZE, HAS_DEFAULT_VALUE, COMMENT
     */
    abstract public String getSelectForFieldList();

    /**
     * Sql query for primary key field name list, result: COLUMN_NAME
     * @return SQL query, COLUMN_NAME
     */
    abstract public String getSelectForPrimaryKeyFieldNameList();

}
