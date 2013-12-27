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
package hu.vanio.easydao;

import hu.vanio.easydao.model.Database;
import java.util.HashMap;
import java.util.Map;

/**
 * Source code generator model.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class EngineConfiguration {

    /**
     * Valid database types.
     */
    public static enum DATABASE_TYPE {

        POSTGRESQL9, ORACLE11;
    }

    private Database database;
    private DATABASE_TYPE databaseType;
    private String url;
    private String username;
    private String password;
    private boolean tablePrefix;
    private boolean tableSuffix;
    private boolean fieldPrefix;
    private boolean fieldSuffix;
    private String generatedSourcePath;
    private String packageOfJavaModel;
    private String packageOfJavaDao;
    private String daoSuffix;
    private String replacementTableFilename;
    private String replacementFieldFilename;

    /**
     * Engine configuration init.
     * @param databaseName database name: generated Dao DataSource name in @Qualifier annotation
     * @param databaseType database type: ORACLE11, POSTGRESQL9
     * @param url db connection url
     * @param username db connection user name
     * @param password db connection password
     * @param tablePrefix if true, then table name will be parsed after first _ underscore
     * @param tableSuffix if true, then table name last part will be ignored after last underscore
     * @param fieldPrefix if true, then field name will be parsed after first _ underscore
     * @param fieldSuffix if true, then field name last part will be ignored after last underscore
     * @param generatedSourcePath file system directory for generated source files
     * @param packageOfJavaModel package name of the generated source code of the model classes
     * @param packageOfJavaDao package name of the generated source code of the dao classes
     * @param daoSuffix dao class's suffix, i.e.: Dao -> UserDao.java
     * @param replacementTableFilename database table name will be replaced to given java class
     * name in model and dao, i.e.: APPUSERS = User. APPUSERS: database table name.
     * User: java name (and not AppUsers). If not set java name, then table will be skipped from
     * source generation, i.e: APPUSERS =
     * @param replacementFieldFilename field name will be replaced to given member name,
     * i.e.: USERS.FNAME = firstName. USERS.FNAME: database table.field name.
     * firstName: member name (and not fName). If not set java name, then field will be skipped from
     * source generation, i.e: USERS.FNAME =
     */
    public EngineConfiguration(String databaseName, DATABASE_TYPE databaseType, String url, String username, String password,
            boolean tablePrefix, boolean tableSuffix, boolean fieldPrefix, boolean fieldSuffix,
            String generatedSourcePath, String packageOfJavaModel,
            String packageOfJavaDao, String daoSuffix,
            String replacementTableFilename, String replacementFieldFilename) {
        this.database = new Database(databaseName);
        this.databaseType = databaseType;
        this.url = url;
        this.username = username;
        this.password = password;
        this.tablePrefix = tablePrefix;
        this.tableSuffix = tableSuffix;
        this.fieldPrefix = fieldPrefix;
        this.fieldSuffix = fieldSuffix;
        this.generatedSourcePath = generatedSourcePath;
        this.packageOfJavaModel = packageOfJavaModel;
        this.packageOfJavaDao = packageOfJavaDao;
        this.daoSuffix = daoSuffix;
        this.replacementTableFilename = replacementTableFilename;
        this.replacementFieldFilename = replacementFieldFilename;
    }

    /* Replacement map for tables. Empty string value means it has been skipped from the model. */
    private Map<String, String> replacementTableMap = new HashMap<>();

    /* Replacement map for fields. Empty string value means it has been skipped from the model. */
    private Map<String, String> replacementFieldMap = new HashMap<>();

    /**
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * @return the databaseType
     */
    public DATABASE_TYPE getDatabaseType() {
        return databaseType;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the tablePrefix
     */
    public boolean isTablePrefix() {
        return tablePrefix;
    }

    /**
     * @return the tableSuffix
     */
    public boolean isTableSuffix() {
        return tableSuffix;
    }

    /**
     * @return the fieldPrefix
     */
    public boolean isFieldPrefix() {
        return fieldPrefix;
    }

    /**
     * @return the fieldSuffix
     */
    public boolean isFieldSuffix() {
        return fieldSuffix;
    }

    /**
     * @return the generatedSourcePath
     */
    public String getGeneratedSourcePath() {
        return generatedSourcePath;
    }

    /**
     * @return the packageOfJavaModel
     */
    public String getPackageOfJavaModel() {
        return packageOfJavaModel;
    }

    /**
     * @return the packageOfJavaDao
     */
    public String getPackageOfJavaDao() {
        return packageOfJavaDao;
    }

    /**
     * @return the daoSuffix
     */
    public String getDaoSuffix() {
        return daoSuffix;
    }

    /**
     * @return the replacementTableFilename
     */
    public String getReplacementTableFilename() {
        return replacementTableFilename;
    }

    /**
     * @return the replacementFieldFilename
     */
    public String getReplacementFieldFilename() {
        return replacementFieldFilename;
    }

    /**
     * @return the replacementTableMap
     */
    public Map<String, String> getReplacementTableMap() {
        return replacementTableMap;
    }

    /**
     * @param replacementTableMap the replacementTableMap to set
     */
    public void setReplacementTableMap(Map<String, String> replacementTableMap) {
        this.replacementTableMap = replacementTableMap;
    }

    /**
     * @return the replacementFieldMap
     */
    public Map<String, String> getReplacementFieldMap() {
        return replacementFieldMap;
    }

    /**
     * @param replacementFieldMap the replacementFieldMap to set
     */
    public void setReplacementFieldMap(Map<String, String> replacementFieldMap) {
        this.replacementFieldMap = replacementFieldMap;
    }
}
