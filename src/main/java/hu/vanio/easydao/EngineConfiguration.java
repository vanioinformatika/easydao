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

import java.util.HashMap;
import java.util.Map;

import hu.vanio.easydao.model.Database;

/**
 * Source code generator engine configuration.
 * 
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class EngineConfiguration {

    /**
     * Valid database types.
     */
    public static enum DATABASE_TYPE {

        POSTGRESQL9, ORACLE11;
    }

    /**
     * Valid sequence name conventions.
     */
    static public enum SEQUENCE_NAME_CONVENTION {
        /** Generate sequence names by prefixing table names with SEQ_ (e.g.: MY_TABLE_NAME -> SEQ_MY_TABLE_NAME) */
        PREFIXED_TABLE_NAME,
        /** Generate sequence names by prefixing field names with SEQ_ (e.g.: MY_FIELD_NAME -> SEQ_MY_FIELD_NAME) */
        PREFIXED_FIELD_NAME,
        /** Generate sequence names by suffixing table names with _SEQ (e.g.: MY_TABLE_NAME -> MY_TABLE_NAME_SEQ) */
        SUFFIXED_TABLE_NAME,
        /** Generate sequence names by suffixing field names with _SEQ (e.g.: MY_FIELD_NAME -> MY_FIELD_NAME_SEQ) */
        SUFFIXED_FIELD_NAME,
        /** Generate sequence names by prefixing table names with SEQ_ and suffixing them with field name (e.g.: MY_TABLE_NAME.MY_FIELD_NAME -> SEQ_MY_TABLE_NAME_MY_FIELD_NAME) */
        PREFIXED_TABLE_NAME_WITH_FIELD_NAME, 
        /** Generate sequence names by suffixing table names with field name and _SEQ (e.g.: MY_TABLE_NAME.MY_FIELD_NAME -> MY_TABLE_NAME_MY_FIELD_NAME_SEQ) */
        SUFFIXED_TABLE_NAME_WITH_FIELD_NAME;
    }
    
    /** Database name and other metadata */
    private final Database database;
    /** Database type */
    private final DATABASE_TYPE databaseType;
    /** Sequence naming convention of the database */
    private final SEQUENCE_NAME_CONVENTION sequenceNameConvention;
    /** Database connection URL */
    private final String url;
    /** Database connection user name */
    private final String username;
    /** Database connection password */
    private final String password;
    /** If true, then table name will be parsed after the first _ underscore */
    private final boolean tablePrefix;
    /** If true, then the last part of the table name will be ignored after the last underscore */
    private final boolean tableSuffix;
    /** If true, then field name will be parsed after the first _ underscore */
    private final boolean fieldPrefix;
    /** If true, then last part of the field name will be ignored after the last underscore */
    private final boolean fieldSuffix;
    /** File system directory for generated source files */
    private final String generatedSourcePath;
    /** Package name of the generated source code of the model classes */
    private final String packageOfJavaModel;
    /** Package name of the generated source code of the dao classes */
    private final String packageOfJavaDao;
    /** Suffix used for DAO class names, e.g.: Dao -> UserDao.java */
    private final String daoSuffix;
    /** Map of Java class names and database table names. Names not included in this list will be auto-generated.
     *  e.g.: APPUSERS = User -> (User and UserDao) instead of (Appusers and AppusersDao)
     *  You can disable Java source generation for a certain table by putting the table name in the list with no Java class name.
     *  e.g.: APPUSERS = */
    private final String replacementTableFilename;
    /** Map of Java field names and database field names. Names not included in this list will be auto-generated.
     *  e.g.: USER.FNAME = firstName -> User.firstName instead of User.fname
     *  You can disable Java source generation for a certain field by putting the field name in the list with no Java field name.
     *  e.g.: USER.FNAME = */
    private final String replacementFieldFilename;
    
    /** Replacement map for tables. Empty string value means it has been skipped from the model. */
    private Map<String, String> replacementTableMap = new HashMap<>();

    /** Replacement map for fields. Empty string value means it has been skipped from the model. */
    private Map<String, String> replacementFieldMap = new HashMap<>();
    
    /**
     * Engine configuration init.
     * @param databaseName database name: generated Dao DataSource name in @Qualifier annotation
     * @param databaseType database type: ORACLE11, POSTGRESQL9
     * @param url Database connection URL
     * @param username Database connection user name
     * @param password Database connection password
     * @param tablePrefix If true, then table name will be parsed after first _ underscore
     * @param tableSuffix If true, then the last part of the table name will be ignored after the last underscore
     * @param fieldPrefix If true, then field name will be parsed after the first _ underscore
     * @param fieldSuffix If true, then last part of the field name will be ignored after the last underscore
     * @param generatedSourcePath File system directory for generated source files
     * @param packageOfJavaModel Package name of the generated source code of the model classes
     * @param packageOfJavaDao Package name of the generated source code of the dao classes
     * @param daoSuffix Suffix used for DAO class names, e.g.: Dao -> UserDao.java
     * @param sequenceNameConvention Sequence naming convention of the database
     * @param replacementTableFilename Map of Java class names and database table names. Names not included in this list will be auto-generated.
     *                                 e.g.: APPUSERS = User -> (User and UserDao) instead of (Appusers and AppusersDao)
     *                                 You can disable Java source generation for a certain table by putting the table name in the list with no Java class name.
     *                                 e.g.: APPUSERS =
     * @param replacementFieldFilename Map of Java field names and database field names. Names not included in this list will be auto-generated.
     *                                 e.g.: USER.FNAME = firstName -> User.firstName instead of User.fname
     *                                 You can disable Java source generation for a certain field by putting the field name in the list with no Java field name.
     *                                 e.g.: USER.FNAME =
     */
    public EngineConfiguration(
            String databaseName, DATABASE_TYPE databaseType, String url, String username, String password,
            boolean tablePrefix, boolean tableSuffix, boolean fieldPrefix, boolean fieldSuffix,
            String generatedSourcePath, String packageOfJavaModel,
            String packageOfJavaDao, String daoSuffix,
            SEQUENCE_NAME_CONVENTION sequenceNameConvention,
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
        this.sequenceNameConvention = sequenceNameConvention;
        this.replacementTableFilename = replacementTableFilename;
        this.replacementFieldFilename = replacementFieldFilename;
    }

    /**
     * Database name and other metadata
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * Database type
     * @return the databaseType
     */
    public DATABASE_TYPE getDatabaseType() {
        return databaseType;
    }

    /**
     * Sequence naming convention of the database
     * @return the sequenceNameConvention
     */
    public SEQUENCE_NAME_CONVENTION getSequenceNameConvention() {
        return sequenceNameConvention;
    }

    /**
     * Database connection URL
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Database connection user name
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Database connection password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * If true, then table name will be parsed after the first _ underscore
     * @return the tablePrefix
     */
    public boolean isTablePrefix() {
        return tablePrefix;
    }

    /**
     * If true, then the last part of the table name will be ignored after the last underscore
     * @return the tableSuffix
     */
    public boolean isTableSuffix() {
        return tableSuffix;
    }

    /**
     * If true, then field name will be parsed after the first _ underscore
     * @return the fieldPrefix
     */
    public boolean isFieldPrefix() {
        return fieldPrefix;
    }

    /**
     * If true, then last part of the field name will be ignored after the last underscore
     * @return the fieldSuffix
     */
    public boolean isFieldSuffix() {
        return fieldSuffix;
    }

    /**
     * File system directory for generated source files
     * @return the generatedSourcePath
     */
    public String getGeneratedSourcePath() {
        return generatedSourcePath;
    }

    /**
     * Package name of the generated source code of the model classes
     * @return the packageOfJavaModel
     */
    public String getPackageOfJavaModel() {
        return packageOfJavaModel;
    }

    /**
     * Package name of the generated source code of the dao classes
     * @return the packageOfJavaDao
     */
    public String getPackageOfJavaDao() {
        return packageOfJavaDao;
    }

    /**
     * Suffix used for DAO class names, e.g.: Dao -> UserDao.java
     * @return the daoSuffix
     */
    public String getDaoSuffix() {
        return daoSuffix;
    }

    /**
     * Map of Java class names and database table names. Names not included in this list will be auto-generated.
     * e.g.: APPUSERS = User -> (User and UserDao) instead of (Appusers and AppusersDao)
     * You can disable Java source generation for a certain table by putting the table name in the list with no Java class name.
     * e.g.: APPUSERS =
     * @return the replacementTableFilename
     */
    public String getReplacementTableFilename() {
        return replacementTableFilename;
    }

    /**
     * Map of Java field names and database field names. Names not included in this list will be auto-generated.
     * e.g.: USER.FNAME = firstName -> User.firstName instead of User.fname
     * You can disable Java source generation for a certain field by putting the field name in the list with no Java field name.
     * e.g.: USER.FNAME =
     * @return the replacementFieldFilename
     */
    public String getReplacementFieldFilename() {
        return replacementFieldFilename;
    }

    /**
     * Replacement map for tables. Empty string value means it has been skipped from the model.
     * @return the replacementTableMap
     */
    public Map<String, String> getReplacementTableMap() {
        return replacementTableMap;
    }

    /**
     * Replacement map for tables. Empty string value means it has been skipped from the model.
     * @param replacementTableMap the replacementTableMap to set
     */
    public void setReplacementTableMap(Map<String, String> replacementTableMap) {
        this.replacementTableMap = replacementTableMap;
    }

    /**
     * Replacement map for fields. Empty string value means it has been skipped from the model.
     * @return the replacementFieldMap
     */
    public Map<String, String> getReplacementFieldMap() {
        return replacementFieldMap;
    }

    /**
     * Replacement map for fields. Empty string value means it has been skipped from the model.
     * @param replacementFieldMap the replacementFieldMap to set
     */
    public void setReplacementFieldMap(Map<String, String> replacementFieldMap) {
        this.replacementFieldMap = replacementFieldMap;
    }


}
