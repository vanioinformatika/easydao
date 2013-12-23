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

import hu.vanio.easydao.generator.PostgresJdbcType;
import hu.vanio.easydao.model.Database;
import hu.vanio.easydao.model.Field;
import hu.vanio.easydao.model.Table;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;

/**
 * Abstract class for model builders.
 * These methods build java model of database: tables, fields and so on.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
abstract class ModelBuilder implements IModelBuilder {

    /** There is no comment in database */
    final public String EMPTY_COMMENT = "FIXME: Warning: There is no comment in database!";

    /* Database connection */
    protected Connection con;
    /* true, if table has prefix */
    protected boolean hasTablePrefix;
    /* true, if table has postfix */
    protected boolean hasTablePostfix;
    /* true, if field has prefix */
    protected boolean hasFieldPrefix;
    /* true, if table has postfix */
    protected boolean hasFieldPostfix;

    /**
     * Model builder contrucor.
     * @param con database connection
     * @param hasTablePrefix true, if table has prefix
     * @param hasTablePostfix true, if table has postfix
     * @param hasFieldPrefix true, if field has prefix
     * @param hasFieldPostfix true, if table has postfix
     */
    public ModelBuilder(Connection con, boolean hasTablePrefix, boolean hasTablePostfix, boolean hasFieldPrefix, boolean hasFieldPostfix) {
        this.con = con;
        this.hasTablePrefix = hasTablePrefix;
        this.hasTablePostfix = hasTablePostfix;
        this.hasFieldPrefix = hasFieldPrefix;
        this.hasFieldPostfix = hasFieldPostfix;
    }

    @Override
    public Database build() throws SQLException {
        Database database = null;
        List<Table> tableList = this.getTableList();
        for (Table table : tableList) {
            table.setFieldList(this.getFieldList(table.getDbName()));
        }
        return database;
    }

    /**
     * Load all tables from database.
     * @param con database JDBC connection
     * @param hasTablePrefix true, if table has prefix
     * @param hasTablePostfix true, if table has postfix
     * @return table list
     * @throws SQLException
     */
    abstract protected List<Table> getTableList() throws SQLException;

    /**
     * Load primary key field names for a table.
     * @param tableName table name
     * @return List of name of primary keys.
     */
    abstract protected List<String> getPrimaryKeyFieldNameList(String tableName) throws SQLException;

    /**
     * Load field data for a table, and set its data.
     * @param tableName table name
     * @param primaryKeyFieldNameList list of primary keys
     * @return list of fields of table
     * @throws SQLException
     */
    abstract protected List<Field> getFieldList(String tableName) throws SQLException;

    /**
     * Create Java name from database's name
     * @param dbName database's name
     * @param firstCharToUpperCase set first result char to uppercase, i.e: class name
     * @param hasPrefix dbName has prefix
     * @param hasPostFix dbName has postfix
     * @return Java name based on java convention
     */
    protected String createJavaName(String dbName,
            boolean firstCharToUpperCase,
            boolean hasPrefix,
            boolean hasPostFix) {
        String[] sArray = dbName.toLowerCase().split("_");
        String result = "";
        for (int i = 0; i < sArray.length; i++) {
            String s = sArray[i];
            if (hasPrefix && i == 0) {
                // skip first chunk
                continue;
            }
            if (hasPostFix && i == sArray.length - 1) {
                // skip last chunk
                continue;
            }
            if (i > 0) {
                // upper case chunks' first character
                String firstChar = s.substring(0, 1).toUpperCase();
                s = firstChar + s.substring(1, s.length());
            }
            result += s;
        }
        // handling first character
        if (firstCharToUpperCase) {
            // upper case
            String firstChar = result.substring(0, 1).toUpperCase();
            result = firstChar + result.substring(1, result.length());
        } else {
            // lower case
            String firstChar = result.substring(0, 1).toLowerCase();
            result = firstChar + result.substring(1, result.length());
        }
        return result;
    }

    /**
     * Return java type from a hashmap by dbtype string.
     * @param dbType database type string
     * @return java class type
     */
    protected Class getJavaType(String dbType) throws IllegalArgumentException {
        Class clazz = null;
        boolean found = false;
        for (Entry<String, Class> e : PostgresJdbcType.MAP.entrySet()) {
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
}
