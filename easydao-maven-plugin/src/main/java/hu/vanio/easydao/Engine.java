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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import hu.vanio.easydao.model.Table;
import hu.vanio.easydao.modelbuilder.IModelBuilderConfig;
import hu.vanio.easydao.modelbuilder.ModelBuilder;
import hu.vanio.easydao.modelbuilder.Oracle10ModelBuilderConfig;
import hu.vanio.easydao.modelbuilder.Oracle11ModelBuilderConfig;
import hu.vanio.easydao.modelbuilder.PostgreSql9ModelBuilderConfig;

/**
 * Generate domain object and Dao classes from database based on configuration.
 * 
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Engine {

    static final private String fileSeparator = System.getProperty("file.separator");
    
    /** Engine configuration */
    private EngineConfiguration engineConfiguration;
    /** Database model builder configuration */
    private IModelBuilderConfig modelBuilderConfig;
    /** Freemarker configuration */
    private Configuration freemarkerConfig;
    /** Handles localised messages */
    private LocalisedMessages messages;
    
    /**
     * Constructs a new instance with the specified configuration
     * 
     * @param engineConfiguration Engine configuration
     * @throws java.sql.SQLException If registering the JDBC driver fails
     */
    public Engine(EngineConfiguration engineConfiguration) throws SQLException {
        this.engineConfiguration = engineConfiguration;
        if (!engineConfiguration.isSilent()) {
            System.out.println("EngineConfiguration.databaseType = " + engineConfiguration.getDatabaseType());
        }
        switch (engineConfiguration.getDatabaseType()) {
            case POSTGRESQL9:
                DriverManager.registerDriver(new org.postgresql.Driver());
                modelBuilderConfig = new PostgreSql9ModelBuilderConfig();
                break;
            case ORACLE10:
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                modelBuilderConfig = new Oracle10ModelBuilderConfig();
                break;
            case ORACLE11:
                DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                modelBuilderConfig = new Oracle11ModelBuilderConfig();
                break;
        }
        this.messages = new LocalisedMessages("messages", this.engineConfiguration.getLocale());
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
                engineConfiguration.getUrl(),
                engineConfiguration.getUsername(),
                engineConfiguration.getPassword());) {
            // Building java model from database metadata
            ModelBuilder modelBuilder = new ModelBuilder(con,
                    engineConfiguration,
                    modelBuilderConfig, messages);
            modelBuilder.build();
        }

        // create helpers and meta data file
        generateMetadataFile();
        // create java source codes: model and dao
        generateModelClasses();
        generateDaoClasses();
    }

    private void generateMetadataFile() throws IOException, TemplateException {
        Path dir = Paths.get(engineConfiguration.getGeneratedSourcePath());
        Files.createDirectories(dir);
        System.out.println("\nGenerate meta data about database: " + dir.toAbsolutePath().toString() + fileSeparator + "metadata.txt");

        List<Table> tableList = engineConfiguration.getDatabase().getTableList();
        Template temp = freemarkerConfig.getTemplate("metadata.ftl");
        Map<String, Object> m = new HashMap<>();
        m.put("tList", tableList);
        m.put("e", engineConfiguration);
        m.put("messages", messages);
        //temp.process(m, out);
        try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(new File(dir.toAbsolutePath().toString() + fileSeparator + "metadata.txt")), engineConfiguration.getEncoding())) {
            temp.process(m, fileWriter);
        }
    }

    /**
     * Generate model classes.
     * @throws IOException
     * @throws TemplateException
     */
    private void generateModelClasses() throws IOException, TemplateException {
        List<Table> tableList = engineConfiguration.getDatabase().getTableList();
        // create directory for java package
        Path dir = Paths.get(engineConfiguration.getGeneratedSourcePath()
                + fileSeparator
                + engineConfiguration.getPackageOfJavaModel().replace(".", fileSeparator)
                + fileSeparator
                + engineConfiguration.getDatabase().getName());
        Files.createDirectories(dir);
        if (!engineConfiguration.isSilent()) {
            System.out.println("\nWrite model classes into " + dir.toAbsolutePath());
        }
        for (Table table : tableList) {
            Template temp = freemarkerConfig.getTemplate("model.ftl");
            Writer out = new OutputStreamWriter(System.out);
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConfiguration);
            m.put("messages", messages);
            //temp.process(m, out);
            try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(new File(dir.toAbsolutePath().toString() + fileSeparator + table.getJavaName() + ".java")), engineConfiguration.getEncoding())) {
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
        List<Table> tableList = engineConfiguration.getDatabase().getTableList();
        // create directory for java package
        Path dir = Paths.get(engineConfiguration.getGeneratedSourcePath()
                + fileSeparator
                + engineConfiguration.getPackageOfJavaDao().replace(".", fileSeparator)
                + fileSeparator
                + engineConfiguration.getDatabase().getName());
        Files.createDirectories(dir);
        if (!engineConfiguration.isSilent()) {
            System.out.println("\nWrite dao classes into " + dir.toAbsolutePath());
        }
        for (Table table : tableList) {
            if (table.getPkFields().size() > 0) {
                Template temp = freemarkerConfig.getTemplate("dao.ftl");
                Writer out = new OutputStreamWriter(System.out);
                Map<String, Object> m = new HashMap<>();
                m.put("t", table);
                m.put("e", engineConfiguration);
                m.put("messages", messages);
                //temp.process(m, out);
                try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(new File(dir.toAbsolutePath().toString() + fileSeparator + table.getJavaName() + engineConfiguration.getDaoSuffix() + ".java")), engineConfiguration.getEncoding())) {
                    temp.process(m, fileWriter);
                }
            } else {
                System.out.printf("*** WARNING: No primary key on table %s, DAO generation skipped\n", table.getDbName());
            }
        }
    }

    /**
     * @see http://freemarker.org/docs/pgui_quickstart_createconfiguration.html
     * @return Freemarker configuration
     */
    private void initFreemarkerConfiguration() {
        freemarkerConfig = new Configuration();
        freemarkerConfig.setClassForTemplateLoading(Engine.class, "/hu/vanio/easydao/templates");
        freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerConfig.setIncompatibleImprovements(new Version(2, 3, 20));
    }

    /**
     * Engine configuration
     * @return the engineConfiguration
     */
    public EngineConfiguration getEngineConfiguration() {
        return engineConfiguration;
    }

}
