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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generate model and Dao from database based on configuration.
 *
 */
public class Engine {

    /* Application name */
    private String name = "EasyDao";

    /* Application version */
    private String version = "0.0.0-SNAPSHOT";

    private EngineConfiguration engineConf;

    private IModelBuilderConfig mdc;

    /** Freemarker configuration */
    private Configuration cfg;

    /**
     * Init engine configuration.
     * @throws SQLException
     */
    public Engine() throws SQLException {
        this.initFreemarkerConfiguration();
        // FIXME: read from configuration
        this.initEngineConfiguration();
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
                    engineConf.isTablePrefix(),
                    engineConf.isTablePostfix(),
                    engineConf.isFieldPrefix(),
                    engineConf.isFieldPostfix(),
                    mdc);
            engineConf.setDatabase(modelBuilder.build());
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
        for (Table table : tableList) {
            Template temp = cfg.getTemplate("model.ftl");
            Writer out = new OutputStreamWriter(System.out);
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConf);
            m.put("appname", name);
            m.put("appversion", version);
            temp.process(m, out);
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
        for (Table table : tableList) {
            Template temp = cfg.getTemplate("dao.ftl");
            Writer out = new OutputStreamWriter(System.out);
            Map<String, Object> m = new HashMap<>();
            m.put("t", table);
            m.put("e", engineConf);
            m.put("appname", name);
            m.put("appversion", version);
            temp.process(m, out);
        }
    }

    /**
     * Engine configuration initialization.
     * @throws SQLException
     */
    private void initEngineConfiguration() throws SQLException {
        engineConf = new EngineConfiguration();

        // FIXME: load data from config file!
        engineConf.setDatabaseType(EngineConfiguration.DATABASE_TYPE.POSTGRESQL9);

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
}
