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

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import hu.vanio.easydao.model.Table;
import hu.vanio.easydao.modelbuilder.IModelBuilderConfig;
import hu.vanio.easydao.modelbuilder.ModelBuilder;
import hu.vanio.easydao.modelbuilder.Oracle11ModelBuilderConfig;
import hu.vanio.easydao.modelbuilder.PostgreSql9ModelBuilderConfig;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Generate model and Dao from database based on configuration.
 *
 */
public class Engine {

    /* Application name */
    private String name = "EasyDao";

    /* Application version */
    private String version = "0.0.0-SNAPSHOT";

    /* Engine configuration file name */
    private String configFileName;
    private Map<String, String> configMap;
    private EngineConfiguration engineConf;

    private IModelBuilderConfig mdc;

    /** Freemarker configuration */
    private Configuration cfg;

    static final private String fileSeparator = System.getProperty("file.separator");

    /**
     * Engine configuration from configuration file.
     * @param configFileName configuration file name.
     * @throws SQLException
     */
    public Engine(String configFileName) throws SQLException {
        // load configuration file to config map
        configMap = new HashMap<>();
        loadResourceBundleToMap(configFileName, configMap);
        this.configFileName = configFileName;
        this.initEngineConfiguration();
        this.initFreemarkerConfiguration();
    }

    /**
     * Engine configuration by Map. Used by maven plugin.
     * @param configMap configuration map.
     * @throws java.sql.SQLException
     */
    public Engine(Map<String, String> configMap) throws SQLException {
        this.configMap = configMap;
        this.initEngineConfiguration();
        this.initFreemarkerConfiguration();
    }

    /**
     * Execute java model generator engine.
     * @throws IOException
     * @throws java.sql.SQLException
     * @throws TemplateException
     */
    public void execute() throws IOException, SQLException, TemplateException {

        // build java model of database
        try (Connection con = DriverManager.getConnection(
                engineConf.getUrl(),
                engineConf.getUsername(),
                engineConf.getPassword());) {
            // Building java model from database metadata
            ModelBuilder modelBuilder = new ModelBuilder(con,
                    engineConf,
                    mdc);
            modelBuilder.build();
        }

        // create java source codes: model and dao
        generateModelClasses();
        generateDaoClasses();
    }

    /**
     * Generate model classes.
     * @throws IOException
     * @throws TemplateException
     */
    private void generateModelClasses() throws IOException, TemplateException {
        // TODO: generateLicence();
        List<Table> tableList = engineConf.getDatabase().getTableList();
        // create directory for java package
        Path dir = Paths.get(engineConf.getGeneratedSourcePath() + fileSeparator + engineConf.getPackageOfJavaModel().replace(".", fileSeparator));
        Files.createDirectories(dir);
        System.out.println("Write model classes into " + dir.toAbsolutePath());
        for (Table table : tableList) {
            Template temp = cfg.getTemplate("model.ftl");
            Writer out = new OutputStreamWriter(System.out);
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConf);
            m.put("appname", name);
            m.put("appversion", version);
            temp.process(m, out);
            try (Writer fileWriter = new FileWriter(new File(dir.toAbsolutePath().toString() + fileSeparator + table.getJavaName() + ".java"))) {
                temp.process(m, fileWriter);
            }
        }
    }

    /**
     * Generate Dao classes.
     * @throws IOException
     * @throws TemplateException
     */
    private void generateDaoClasses() throws IOException, TemplateException {
        // TODO: generateLicence();
        List<Table> tableList = engineConf.getDatabase().getTableList();
        // create directory for java package
        Path dir = Paths.get(engineConf.getGeneratedSourcePath() + fileSeparator + engineConf.getPackageOfJavaDao().replace(".", fileSeparator));
        Files.createDirectories(dir);
        for (Table table : tableList) {
            Template temp = cfg.getTemplate("dao.ftl");
            Writer out = new OutputStreamWriter(System.out);
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConf);
            m.put("appname", name);
            m.put("appversion", version);
            temp.process(m, out);
            try (Writer fileWriter = new FileWriter(new File(dir.toAbsolutePath().toString() + fileSeparator + table.getJavaName() + engineConf.getDaoSuffix() + ".java"))) {
                temp.process(m, fileWriter);
            }
        }
    }

    /**
     * Engine configuration initialization.
     * @throws SQLException db connection error
     */
    private void initEngineConfiguration() throws SQLException {
        engineConf = new EngineConfiguration(
                configMap.get("database.name"),
                EngineConfiguration.DATABASE_TYPE.valueOf(configMap.get("databaseType")),
                configMap.get("url"),
                configMap.get("username"),
                configMap.get("password"),
                Boolean.valueOf(configMap.get("tablePrefix")),
                Boolean.valueOf(configMap.get("tableSuffix")),
                Boolean.valueOf(configMap.get("fieldPrefix")),
                Boolean.valueOf(configMap.get("fieldSuffix")),
                configMap.get("generatedSourcePath"),
                configMap.get("packageOfJavaModel"),
                configMap.get("packageOfJavaDao"),
                configMap.get("daoSuffix"),
                configMap.get("replacementTableFilename"),
                configMap.get("replacementFieldFilename"));

        // load table and field replacement files into maps
        loadResourceBundleToMap(engineConf.getReplacementTableFilename(), engineConf.getReplacementTableMap());
        engineConf.getReplacementTableMap().put("", "ERROR_EMPTY_TABLE_NAME"); // error name in model if empty table name
        loadResourceBundleToMap(engineConf.getReplacementFieldFilename(), engineConf.getReplacementFieldMap());
        engineConf.getReplacementFieldMap().put("", "ERROR_EMPTY_FIELD_NAME"); // error name in model if empty field name

        System.out.println("EngineConfiguration.DATABASE_TYPE.valueOf(configMap.get(\"databaseType\")) = " + EngineConfiguration.DATABASE_TYPE.valueOf(configMap.get("databaseType")));
        switch (engineConf.getDatabaseType()) {
            case POSTGRESQL9:
                DriverManager.registerDriver(new org.postgresql.Driver());
                mdc = new PostgreSql9ModelBuilderConfig();
                break;
            case ORACLE11:
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                mdc = new Oracle11ModelBuilderConfig();
                break;
        }
    }

    /**
     * Load resource bundle to map.
     * @param fileName replacement file name
     * @param map replacement map
     */
    private void loadResourceBundleToMap(String fileName, Map<String, String> map) {
        ResourceBundle resource = ResourceBundle.getBundle(fileName);
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, resource.getString(key));
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @see http://freemarker.org/docs/pgui_quickstart_createconfiguration.html
     * @return Freemarker configuration
     */
    private void initFreemarkerConfiguration() {
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(Engine.class, "/hu/vanio/easydao/templates");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
    }

    /**
     * @return the engineConf
     */
    public EngineConfiguration getEngineConf() {
        return engineConf;
    }

    /**
     * @param engineConf the engineConf to set
     */
    public void setEngineConf(EngineConfiguration engineConf) {
        this.engineConf = engineConf;
    }

    /**
     * @return the configFileName
     */
    public String getConfigFileName() {
        return configFileName;
    }

}
