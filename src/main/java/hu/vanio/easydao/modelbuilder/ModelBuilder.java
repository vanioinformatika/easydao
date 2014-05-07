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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import hu.vanio.easydao.EngineConfiguration;
import hu.vanio.easydao.LocalisedMessages;
import hu.vanio.easydao.model.Field;
import hu.vanio.easydao.model.Index;
import hu.vanio.easydao.model.Table;

/**
 * Abstract class for model builders.
 * These methods build java model of database: tables, fields and so on.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class ModelBuilder {

    /* There is no comment in database */
    protected String EMPTY_COMMENT;
    /* It has been skipped from the modell */
    protected String SKIPPED_FROM_MODEL = "**************************** SKIPPED FROM MODEL! Check replacement lists. ****************************";

    /* Engine configuration */
    protected EngineConfiguration engineConf;
    /* Database connection */
    protected Connection con;
    /* Database configuration for model builder */
    protected IModelBuilderConfig config;
    /** Handles localisation */
    protected LocalisedMessages messages;
    
    /* primary keys for tables */
    private final Map<String, List<String>> pkFieldsOfTabels = new HashMap<>();
    
    private List<Pattern> tableNameIncludesPatternList;

    /**
     * Model builder constructor.
     * @param con database connection
     * @param engineConf
     * @param config Database configuration for model builder
     * @param messages Handles localisation
     */
    public ModelBuilder(Connection con,
            EngineConfiguration engineConf,
            IModelBuilderConfig config,
            LocalisedMessages messages) {
        this.con = con;
        this.engineConf = engineConf;
        this.config = config;
        this.messages = messages;
        this.EMPTY_COMMENT = messages.getMessage("emptyComment");
        
        List<String> tableNameIncludes = engineConf.getTableNameIncludes();
        if (tableNameIncludes != null && !tableNameIncludes.isEmpty()) {
            this.tableNameIncludesPatternList = new ArrayList<>(tableNameIncludes.size());
            for (String patternStr  : engineConf.getTableNameIncludes()) {
                Pattern pattern = Pattern.compile(patternStr);
                tableNameIncludesPatternList.add(pattern);
            }
        }
    }

    /**
     * Build database java model from database.
     * @throws SQLException
     */
    final public void build() throws SQLException {
        List<Table> tableList = this.getTableList();
        for (Table table : tableList) {
            table.setFieldList(this.getFieldList(table));
            table.setIndexList(this.getIndexListForTable(table));
            engineConf.getDatabase().addTable(table);
        }
    }

    /**
     * Load all tables from database.
     * @return table list
     * @throws SQLException
     */
    public List<Table> getTableList() throws SQLException {
        System.out.println("getTableList");

        List<Table> tableList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(config.getSelectForTableList())) {
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    if (isTableIncluded(tableName)) {
                        String tableComment = rs.getString("COMMENTS");
                        if (tableComment == null) {
                            tableComment = EMPTY_COMMENT;
                        }
                        String javaName = ((ModelBuilderConfig) config).getReplacementName(engineConf.getReplacementTableMap(), tableName);
                        if ("".equals(javaName)) {
                            // if replacement name is empty string, then table has been skipped from the model
                            System.out.println("table name: " + tableName + " " + SKIPPED_FROM_MODEL);
                            continue;
                        }
                        if (javaName == null) {
                            javaName = createJavaName(tableName, true, engineConf.isTablePrefix(), engineConf.isTableSuffix());
                        }
                        Table table = new Table(tableName, tableComment, javaName, null);

                        System.out.println("table name: " + tableName + " - " + javaName + " = " + tableComment);

                        tableList.add(table);
                    }
                }
            }
        }
        return tableList;
    }
    
    /**
     * Checks whether the specified tablename needs to be included in Java code generation, based on the value of the tableNameIncludes property
     * @param tableName The table name
     * @return true if the specified tablename needs to be included in Java code generation, based on the value of the tableNameIncludes property
     */
    private boolean isTableIncluded(String tableName) {
        boolean retVal = false;
        if (this.tableNameIncludesPatternList == null || this.tableNameIncludesPatternList.isEmpty()) {
            retVal = true;
        } else {
            for (Pattern pattern : this.tableNameIncludesPatternList) {
                retVal = pattern.matcher(tableName).matches();
                if (retVal) {
                    break;
                }
            }
        }
        return retVal;
    }
    
    /**
     * Loads all indexes for the specified table from database.
     * @param table The table
     * @return index list
     * @throws SQLException
     */
    public List<Index> getIndexListForTable(Table table) throws SQLException {
        System.out.println("getIndexListForTable: " + table.getDbName());

        List<Index> indexList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(config.getSelectForIndexList())) {
            ps.setString(1, table.getDbName());
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    String uniqueness = rs.getString("UNIQUENESS");
                    boolean unique = "t".equalsIgnoreCase(uniqueness);
                    String indexDbName = rs.getString("INDEX_NAME");
                    String fieldNamesStr = rs.getString("COLUMN_NAMES");
                    List<String> fieldNames = Arrays.asList(fieldNamesStr.split(","));
                    Index index = createIndexInstance(table, indexDbName, unique, fieldNames);
                    indexList.add(index);
                }
            }
        }
        return indexList;
    }

    /**
     * Creates a new Index instance for the specified table
     * 
     * @param table The table
     * @param dbName The name of the index in the database
     * @param unique Indicates whether the index is unique or not
     * @param fieldDbNames The names of the indexed fields
     * @return The new Index instance
     */
    protected Index createIndexInstance(Table table, String dbName, boolean unique, List<String> fieldDbNames) {
        List<Field> fieldList = new ArrayList<>(fieldDbNames.size());
        for (String fieldDbName : fieldDbNames) {
            fieldList.add(table.getField(fieldDbName));
        }
        String javaName = createJavaName(dbName, true, true, false);
        Index index = new Index(dbName, javaName, unique, fieldList);
        System.out.printf("Index for table %s: %s\n", table.getDbName(), index.toString());
        return index;
    }
    
    /**
     * Load primary key field names for a table.
     * @param tableName table name
     * @return List of name of primary keys.
     * @throws java.sql.SQLException
     */
    protected List<String> getPrimaryKeyFieldNameList(String tableName) throws SQLException {
        List<String> pkFieldNameList = pkFieldsOfTabels.get(tableName);
        if (pkFieldNameList == null) {
            pkFieldNameList = new ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement(config.getSelectForPrimaryKeyFieldNameList())) {
                ps.setString(1, tableName);
                try (ResultSet rs = ps.executeQuery();) {
                    while (rs.next()) {
                        String fieldName = rs.getString("COLUMN_NAME");
                        pkFieldNameList.add(fieldName);
                    }
                }
            }
            pkFieldsOfTabels.put(tableName, pkFieldNameList);
        }
        return pkFieldNameList;
    }

    /**
     * Load field data for a table, and set its data.
     * @param table table
     * @return list of fields of table
     * @throws SQLException
     */
    protected List<Field> getFieldList(Table table) throws SQLException {
        String tableName = table.getDbName();
        System.out.println("\ngetFieldList of " + table.getJavaName());
        List<Field> fieldList = new ArrayList<>();
        Map<String, String> enumFieldMap = this.engineConf.getEnumFieldMap();
        try (PreparedStatement ps = con.prepareStatement(config.getSelectForFieldList())) {
            ps.setString(1, tableName);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    boolean nullable = !rs.getBoolean("NOT_NULL");
                    boolean array = rs.getInt("ARRAY_DIM_SIZE") > 0;
                    String fieldName = rs.getString("COLUMN_NAME");
                    boolean primaryKey = getPrimaryKeyFieldNameList(tableName).indexOf(fieldName) >= 0;
                    String dbType = rs.getString("DATA_TYPE");
                    String comment = rs.getString("COMMENTS");
                    if (comment == null) {
                        comment = EMPTY_COMMENT;
                    }
                    String javaName = ((ModelBuilderConfig) config).getReplacementName(engineConf.getReplacementFieldMap(), tableName + "." + fieldName);
                    if ("".equals(javaName)) {
                        // if replacement name is empty string, then field has been skipped from the model
                        System.out.println("field name: " + tableName + "." + fieldName + " " + SKIPPED_FROM_MODEL);
                        continue;
                    }
                    if (javaName == null) {
                        javaName = createJavaName(fieldName, false, engineConf.isFieldPrefix(), engineConf.isFieldSuffix());
                    }
                    String javaType = config.getJavaType(dbType);
                    String tableAndFieldName = tableName + "." + fieldName;
                    boolean enumerated = false;
                    if (enumFieldMap.containsKey(tableAndFieldName)) {
                        javaType = enumFieldMap.get(tableAndFieldName);
                        enumerated = true;
                    }
                    
                    Field field = new Field(primaryKey, nullable, array, enumerated, fieldName, dbType, comment, javaName, javaType);

                    System.out.println(field.toString());

                    fieldList.add(field);
                }
            }
        }
        return fieldList;
    }

    /**
     * Create Java name from database's name
     * @param dbName database's name
     * @param firstCharToUpperCase set first result char to uppercase, i.e: class name
     * @param hasPrefix fieldName has prefix
     * @param hasPostFix fieldName has postfix
     * @return Java name based on java convention
     */
    final protected String createJavaName(String dbName,
            boolean firstCharToUpperCase,
            boolean hasPrefix,
            boolean hasPostFix) {

        String result = "";
        if (dbName.contains("_")) {
            String[] sArray = dbName.toLowerCase().split("_");
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
                    s = changeFirstCharTo(CHAR_CASE_TYPE.UPPERCASE, s);
                }
                result += s;
            }
        } else {
            // fieldName does not contain _ character
            result = dbName.toLowerCase();
        }
        
        if (Character.isDigit(result.charAt(0))) {
            result = "X" + result;
        }
        
        if (isRestrictedWord(result.toLowerCase())) {
            result = "X" + result;
        }
        
        // handling first character
        if (firstCharToUpperCase) {
            // upper case
            result = changeFirstCharTo(CHAR_CASE_TYPE.UPPERCASE, result);
        } else {
            // lower case
            result = changeFirstCharTo(CHAR_CASE_TYPE.LOWERCASE, result);
        }
        return result;
    }

    /**
     * Character uppercase or lowercase.
     */
    enum CHAR_CASE_TYPE {

        UPPERCASE, LOWERCASE;
    }

    /**
     * UpperCase first char.
     * @param s input
     * @return "" if input is null, and uppercased first char if not
     */
    @SuppressWarnings("AssignmentToMethodParameter")
    private String changeFirstCharTo(CHAR_CASE_TYPE c, String s) {
        String result = "";
        s = s.trim();
        if (s != null && s.length() > 0) {
            if (CHAR_CASE_TYPE.UPPERCASE.equals(c)) {
                result = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
            } else {
                result = s.substring(0, 1).toLowerCase() + s.substring(1, s.length());
            }
        }
        return result;
    }

    protected boolean isRestrictedWord(String word) {
        return word.matches("class|interface|public|private|protected|package|this|int|long|byte|short|float|double|boolean|for|while|goto|if|do");
    }
    
}
