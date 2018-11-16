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
import java.util.Arrays;

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
        generateDaoInterfaces();
        generateDaoClasses();
        // copy static java classes
        copyInterfacesAndClasses();
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
        String dirPath = engineConfiguration.getGeneratedSourcePath()
                + fileSeparator
                + engineConfiguration.getPackageOfJavaModel().replace(".", fileSeparator)
                + ( engineConfiguration.isAddDbNameToPackageNames() ? fileSeparator + engineConfiguration.getDatabase().getName() : "" );
        Path dir = Paths.get(dirPath);
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
     * Generate Dao interfaces
     * @throws IOException
     * @throws TemplateException
     */
    private void generateDaoInterfaces() throws IOException, TemplateException {
        List<Table> tableList = engineConfiguration.getDatabase().getTableList();
        // create directory for java package
        String dirPath = engineConfiguration.getGeneratedSourcePath()
                + fileSeparator
                + engineConfiguration.getPackageOfJavaDao().replace(".", fileSeparator)
                + ( engineConfiguration.isAddDbNameToPackageNames() ? fileSeparator + engineConfiguration.getDatabase().getName() : "" );
        Path dir = Paths.get(dirPath);
        Files.createDirectories(dir);
        if (!engineConfiguration.isSilent()) {
            System.out.println("\nWrite dao interfaces into " + dir.toAbsolutePath());
        }
        for (Table table : tableList) {
            if (!table.isHasPkField()) {
                System.out.printf("*** WARNING: No primary key on table %s, generating restricted DAO\n", table.getDbName());
            }
            Template temp = freemarkerConfig.getTemplate("dao_interface.ftl");
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConfiguration);
            m.put("messages", messages);
            try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(new File(dir.toAbsolutePath().toString() + fileSeparator + table.getJavaName() + engineConfiguration.getDaoSuffix() + ".java")), engineConfiguration.getEncoding())) {
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
        String dirPath = engineConfiguration.getGeneratedSourcePath()
                + fileSeparator
                + engineConfiguration.getPackageOfJavaDao().replace(".", fileSeparator)
                + ( engineConfiguration.isAddDbNameToPackageNames() ? fileSeparator + engineConfiguration.getDatabase().getName() : "" );
        Path dir = Paths.get(dirPath);
        Files.createDirectories(dir);
        if (!engineConfiguration.isSilent()) {
            System.out.println("\nWrite dao classes into " + dir.toAbsolutePath());
        }
        for (Table table : tableList) {
            Template temp = freemarkerConfig.getTemplate("dao.ftl");
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConfiguration);
            m.put("messages", messages);
            try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(new File(dir.toAbsolutePath().toString() + fileSeparator + table.getJavaName() + engineConfiguration.getDaoSuffix() + "Impl.java")), engineConfiguration.getEncoding())) {
                temp.process(m, fileWriter);
            }
        }
    }

    /**
     * Copy core interfaces and classes.
     * @throws IOException
     * @throws TemplateException
     */
    private void copyInterfacesAndClasses() throws IOException, TemplateException {
        Configuration javaCopyConfig = new Configuration();
        javaCopyConfig.setClassForTemplateLoading(Engine.class, "/hu/vanio/easydao/core");
        javaCopyConfig.setObjectWrapper(new DefaultObjectWrapper());
        javaCopyConfig.setDefaultEncoding("UTF-8");
        javaCopyConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        javaCopyConfig.setIncompatibleImprovements(new Version(2, 3, 23));

        // copy core dir
        final Path dir = Paths.get(engineConfiguration.getGeneratedSourcePath(),
                "hu", "vanio", "easydao", "core");
        Files.createDirectories(dir);
        if (!engineConfiguration.isSilent()) {
            System.out.println("\nWrite interfaces and classes into " + dir.toAbsolutePath());
        }

        final List<String> staticFileList = Arrays.asList(
                "DaoComp.java",
                "Model.java");
        for (String file : staticFileList) {
            Template temp = javaCopyConfig.getTemplate(file);
            this.copy(dir, temp);
        }

        // copy core/postgresql dir
        final String subdir = "postgresql/";

        Files.createDirectories(Paths.get(engineConfiguration.getGeneratedSourcePath(),
                "hu", "vanio", "easydao", "core", subdir));

        if (!engineConfiguration.isSilent()) {
            System.out.println("\nWrite interfaces and classes into " + dir.toAbsolutePath());
        }

        final List<String> staticFileList2 = Arrays.asList(
                "PostgreSqlArrayFactory.java",
                "PostgreSqlDoubleArray.java",
                "PostgreSqlFloatArray.java",
                "PostgreSqlInt4Array.java",
                "PostgreSqlTextArray.java");
        for (String file : staticFileList2) {
            Template temp = javaCopyConfig.getTemplate(subdir + file);
            this.copy(dir, temp);
        }

    }

    /**
     * Copy template file to directory.
     * @param dir
     * @param temp
     * @throws IOException
     * @throws TemplateException
     */
    protected void copy(Path dir, Template temp) throws IOException, TemplateException {
        Map<String, Object> m = new HashMap<>();
        m.put("e", engineConfiguration);
        try (Writer fileWriter = new OutputStreamWriter(
                new FileOutputStream(
                        new File(dir.toAbsolutePath().toString() + fileSeparator + temp.getName())), engineConfiguration.getEncoding())) {
            temp.process(m, fileWriter);
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
        freemarkerConfig.setIncompatibleImprovements(new Version(2, 3, 23));
    }

    /**
     * Engine configuration
     * @return the engineConfiguration
     */
    public EngineConfiguration getEngineConfiguration() {
        return engineConfiguration;
    }

}
