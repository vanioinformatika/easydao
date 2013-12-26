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
    private DATABASE_TYPE databaseType;
    // postgres
    private String url = "jdbc:postgresql://localhost/callistof";
    private String username = "callisto";
    private String password = "callisto";
    // oracle
//    private String url = "jdbc:oracle:thin:@10.128.2.82:1521:HOTDEV";
//    private String username = "idtvt1";
//    private String password = "idtvt1";
    private boolean tablePrefix = true;
    private boolean tablePostfix = false;
    private boolean fieldPrefix = true;
    private boolean fieldPostfix = false;
    private String generatedSourcePath = "/tmp/database_model";
    private String packageOfJavaModel = "hu.vanio.easydao.model";
    private String packageOfJavaDao = "hu.vanio.easydao.dao";
    private String daoPostfix = "Dao";
    private Database database;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the tablePrefix
     */
    public boolean isTablePrefix() {
        return tablePrefix;
    }

    /**
     * @param tablePrefix the tablePrefix to set
     */
    public void setTablePrefix(boolean tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    /**
     * @return the tablePostfix
     */
    public boolean isTablePostfix() {
        return tablePostfix;
    }

    /**
     * @param tablePostfix the tablePostfix to set
     */
    public void setTablePostfix(boolean tablePostfix) {
        this.tablePostfix = tablePostfix;
    }

    /**
     * @return the fieldPrefix
     */
    public boolean isFieldPrefix() {
        return fieldPrefix;
    }

    /**
     * @param fieldPrefix the fieldPrefix to set
     */
    public void setFieldPrefix(boolean fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
    }

    /**
     * @return the fieldPostfix
     */
    public boolean isFieldPostfix() {
        return fieldPostfix;
    }

    /**
     * @param fieldPostfix the fieldPostfix to set
     */
    public void setFieldPostfix(boolean fieldPostfix) {
        this.fieldPostfix = fieldPostfix;
    }

    /**
     * @return the generatedSourcePath
     */
    public String getGeneratedSourcePath() {
        return generatedSourcePath;
    }

    /**
     * @param generatedSourcePath the generatedSourcePath to set
     */
    public void setGeneratedSourcePath(String generatedSourcePath) {
        this.generatedSourcePath = generatedSourcePath;
    }

    /**
     * @return the packageOfJavaModel
     */
    public String getPackageOfJavaModel() {
        return packageOfJavaModel;
    }

    /**
     * @param packageOfJavaModel the packageOfJavaModel to set
     */
    public void setPackageOfJavaModel(String packageOfJavaModel) {
        this.packageOfJavaModel = packageOfJavaModel;
    }

    /**
     * @return the packageOfJavaDao
     */
    public String getPackageOfJavaDao() {
        return packageOfJavaDao;
    }

    /**
     * @param packageOfJavaDao the packageOfJavaDao to set
     */
    public void setPackageOfJavaDao(String packageOfJavaDao) {
        this.packageOfJavaDao = packageOfJavaDao;
    }

    /**
     * @return the daoPostfix
     */
    public String getDaoPostfix() {
        return daoPostfix;
    }

    /**
     * @param daoPostfix the daoPostfix to set
     */
    public void setDaoPostfix(String daoPostfix) {
        this.daoPostfix = daoPostfix;
    }

    /**
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * @return the databaseType
     */
    public DATABASE_TYPE getDatabaseType() {
        return databaseType;
    }

    /**
     * @param databaseType the databaseType to set
     */
    public void setDatabaseType(DATABASE_TYPE databaseType) {
        this.databaseType = databaseType;
    }

}
