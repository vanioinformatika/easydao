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
package hu.vanio.maven.plugins.easydao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

import freemarker.template.TemplateException;

import hu.vanio.easydao.Engine;
import hu.vanio.easydao.EngineConfiguration;

/**
 *
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        threadSafe = true, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class Generator extends AbstractMojo {

    /**
     * Maven related source generation path.
     */
    final static protected String GENERATED_SOURCE_PATH = "/target/generated-sources/easydao-classes";

    /* Default values */
    final static protected String DEFAULT_DAOSUFFIX = "Dao";
    final static protected String DEFAULT_TABLE_PREFIX = "true";
    final static protected String DEFAULT_TABLE_SUFFIX = "false";
    final static protected String DEFAULT_FIELD_PREFIX = "true";
    final static protected String DEFAULT_FIELD_SUFFIX = "false";

    /* Validation for dbname name */
    final static protected String DBNAME_PATTERN = "^[a-z_$]+[a-z0-9_$]*";
    /* Validation for package name */
    final static protected String PACKAGE_PATTERN = "([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*";
    /* calcualted source root */
    private String genSourceRoot;

    /* maven plugin */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    protected MavenProject project;
    @Parameter(defaultValue = "${run.classpath}")
    protected String classpath;

    /**
     * Database name. Maximum 16 characters, valid package name.
     * <br>It will be used as namespace of generated source, i.e if set to callisto, then
     * hu.vanio.easydaodemo.model.callisto, hu.vanio.easydaodemo.dao.callisto
     * <br>In this way, you can generate several database model and place them into one application.
     * <b>This is the name of the DataSource in a Spring Dao class.</b>
     */
    @Parameter(required = true)
    protected String dbName;
    /**
     * Database type. Supported value: ORACLE10, ORACLE11, POSTGRESQL9
     */
    @Parameter(required = true)
    protected String dbType;
    /**
     * Database connection URL. e.g.:
     * <br>PostgreSql: jdbc:postgresql://localhost/callistof
     * <br>Oracle: jdbc:oracle:thin:@localhost:1521:MYSID
     */
    @Parameter(required = true)
    protected String dbUrl;
    /**
     * Database connection username.
     */
    @Parameter(required = true)
    protected String dbUsername;
    /**
     * Database connection password.
     */
    @Parameter(required = true)
    protected String dbPassword;
    /**
     * Optional, default: true. If true, then table name will trim for first underscore.
     * <br>true, e.g: SHP_CUSTOMER_ORDER -> CustomerOrder
     * <br>false, e.g: SHP_CUSTOMER_ORDER -> ShpCustomerOrder
     */
    @Parameter(required = false, defaultValue = "true")
    protected String tablePrefix;
    /**
     * Optional, default: false. If true, then table name will trim from last underscore. (Not recommended.)
     * <br>true, e.g: CUSTOMER_ORDER_SHP -> CustomerOrder
     * <br>false, e.g: CUSTOMER_ORDER_SHP -> CustomerOrderShp
     */
    @Parameter(required = false, defaultValue = "false")
    protected String tableSuffix;
    /**
     * Optional, default: true. If true, then field name will trim for first underscore.
     * <br>true, e.g: CO_PK -> pk
     * <br>false, e.g: CO_PK -> coPk
     */
    @Parameter(required = false, defaultValue = "true")
    protected String fieldPrefix;
    /**
     * Optional, default: false. If true, then field name will trim from last underscore. (Not recommended.)
     * <br>true, e.g: PK_CO -> pk
     * <br>false, e.g: PK_CO -> pkCo
     */
    @Parameter(required = false, defaultValue = "false")
    protected String fieldSuffix;
    /**
     * Optional, default: target/generated-source/easydao-classes/. You can generate java code into this path. (Not recommended.)
     */
    @Parameter(required = false)
    protected String generatedSourcePath;
    /**
     * Fully qualified package name of java <b>model</b> classes, e.g:
     * hu.vanio.easydao.model
     * <br><b>Do not forget that, database name (i.e: callisto) will be the namespace: hu.vanio.easydao.model.callisto</b>
     */
    @Parameter(required = true)
    protected String packageOfJavaModel;
    /** 
     * Optional, default: empty. Table name (regex) patterns to be included during generating Java code 
     */
    @Parameter(property = "tableNameIncludes", defaultValue = "")
    protected List<String> tableNameIncludes;
    /**
     * Fully qualified package name of java <b>dao</b> classes, e.g:
     * hu.vanio.easydao.dao
     * <br><b>Do not forget that, database name (i.e: callisto) will be the namespace: hu.vanio.easydao.dao.callisto</b>
     */
    @Parameter(required = true)
    protected String packageOfJavaDao;
    /**
     * Optional, default: Dao. Generated dao classes suffix, e.g: CustomerDao.java if this is "Dao"
     */
    @Parameter(required = false, defaultValue = "Dao")
    protected String daoSuffix;
    /**
     * Optional, default: true. If true, a toString method that outputs data in JSON format will be generated for all model classes
     */
    @Parameter(required = false, defaultValue = "true")
    protected String generateModelToString;
    /**
     * Optional, default: null. ISO language code for localising comments in the generated Java classes.
     * So far only en and hu is supported)
     */
    @Parameter(required = false)
    protected String language;
        
    /**
     * Sequence naming convention of the database. Valid values are:
     * <br><b>PREFIXED_TABLE_NAME</b>: Generate sequence names by prefixing table names with SEQ_ (e.g.: MY_TABLE_NAME -> SEQ_MY_TABLE_NAME)
     * <br><b>PREFIXED_FIELD_NAME</b>: Generate sequence names by prefixing field names with SEQ_ (e.g.: MY_FIELD_NAME -> SEQ_MY_FIELD_NAME)
     * <br><b>SUFFIXED_TABLE_NAME</b>: Generate sequence names by suffixing table names with _SEQ (e.g.: MY_TABLE_NAME -> MY_TABLE_NAME_SEQ)
     * <br><b>SUFFIXED_FIELD_NAME</b>: Generate sequence names by suffixing field names with _SEQ (e.g.: MY_FIELD_NAME -> MY_FIELD_NAME_SEQ)
     * <br><b>PREFIXED_TABLE_NAME_WITH_FIELD_NAME</b>: Generate sequence names by prefixing table names with SEQ_ and suffixing them with field name (e.g.: MY_TABLE_NAME.MY_FIELD_NAME -> SEQ_MY_TABLE_NAME_MY_FIELD_NAME)
     * <br><b>SUFFIXED_TABLE_NAME_WITH_FIELD_NAME</b>: Generate sequence names by suffixing table names with field name and _SEQ (e.g.: MY_TABLE_NAME.MY_FIELD_NAME -> MY_TABLE_NAME_MY_FIELD_NAME_SEQ)
     */
    @Parameter(required = true)
    protected EngineConfiguration.SEQUENCE_NAME_CONVENTION sequenceNameConvention;
    /**
     * Replacement file name for tables.
     * Resource bundle file in src/main/resources without file extension, e.g: replacement-table
     * <br>If you ignore a table from java code generation, then put into the file, e.g:
     * <br>CUS_CUSTOMER_ORDER =
     * <br>If you want to generate with a special name (Order.java), then:
     * <br>CUS_CUSTOMER_ORDER = Order
     */
    @Parameter(required = true)
    protected String replacementTableFilename;
    /**
     * Replacement file name for fields.
     * Resource bundle file in src/main/resources without file extension, e.g: replacement-field
     * <br>If you ignore a field from java code generation, then put into the file as TABLENAME.FIELDNAME, e.g:
     * <br>CUS_CUSTOMER_ORDER.SECRET_CODE =
     * <br>If you want to generate with a special name (orderType), then:
     * <br>CUS_CUSTOMER_ORDER.ORDER_MODE = orderType
     */
    @Parameter(required = true)
    protected String replacementFieldFilename;
    
    /**
     * Enum file name for fields.
     * Resource bundle file in src/main/resources without file extension, e.g: enum-field
     * <br>If you want to use a specific enum type for a certain field use the following:
     * <br>CUS_CUSTOMER_ORDER.ORDER_MODE = hu.vanio.myapp.model.OrderMode
     */
    @Parameter(required = false)
    protected String enumFieldFilename;
    
    /**
     * License file name. It will be inserted to source.
     * @throws MojoExecutionException
     */
    @Parameter(required = false)
    protected String licenseFilename;
    
    /**
     * Encoding of the generated source files.
     * Optional, default: ${project.build.sourceEncoding}. 
     */
    @Parameter(required = false, defaultValue = "${project.build.sourceEncoding}")
    protected String encoding;
    
    /**
     * Optional, default: true. Indicates whether normal output should be suppressed during code generation. If set, only warnings will be printed.)
     */
    @Parameter(required = false, defaultValue = "true")
    protected String silent;

    public void execute() throws MojoExecutionException {
        getLog().info("easydao-maven-plugin has started. Generating classes from database.");

        // generatedSourcePath default value
        genSourceRoot = generatedSourcePath;
        if (generatedSourcePath == null || generatedSourcePath.trim().length() == 0) {
            genSourceRoot = project.getBasedir().toString() + GENERATED_SOURCE_PATH;
        }

        // create source path dir
        FileUtils.mkdir(genSourceRoot);
        project.addCompileSourceRoot(genSourceRoot);

        // validate config params
        validateDbName();
        validateDbType();
        validateBoolean("tablePrefix", tablePrefix);
        validateBoolean("tableSuffix", tableSuffix);
        validateBoolean("fieldPrefix", fieldPrefix);
        validateBoolean("fieldSuffix", fieldSuffix);
        validatePackageName("packageOfJavaDao", packageOfJavaDao);
        validatePackageName("packageOfJavaModel", packageOfJavaModel);
        replacementTableFilename = project.getBasedir().toString() + "/src/main/resources/" + replacementTableFilename + ".properties";
        getLog().debug("Resource file for table = " + replacementTableFilename);
        replacementFieldFilename = project.getBasedir().toString() + "/src/main/resources/" + replacementFieldFilename + ".properties";
        getLog().debug("Resource file for field = " + replacementFieldFilename);
        if (this.enumFieldFilename != null) {
            enumFieldFilename = project.getBasedir().toString() + "/src/main/resources/" + enumFieldFilename + ".properties";
            getLog().debug("Resource file for enum fields = " + enumFieldFilename);
        }

        // setting configuration
        try {
            EngineConfiguration ec = new EngineConfiguration(
                    dbName,
                    EngineConfiguration.DATABASE_TYPE.valueOf(dbType),
                    dbUrl, dbUsername, dbPassword,
                    Boolean.parseBoolean(tablePrefix),
                    Boolean.parseBoolean(tableSuffix),
                    Boolean.parseBoolean(fieldPrefix),
                    Boolean.parseBoolean(fieldSuffix),
                    genSourceRoot,
                    packageOfJavaModel,
                    packageOfJavaDao,
                    daoSuffix,
                    Boolean.parseBoolean(generateModelToString),
                    sequenceNameConvention,
                    replacementTableFilename,
                    replacementFieldFilename,
                    enumFieldFilename,
                    licenseFilename,
                    tableNameIncludes,
                    encoding,
                    Boolean.parseBoolean(silent)
            );
            if (language != null) {
                ec.setLocale(new Locale(language));
            }
            Engine e = new Engine(ec);
            e.execute();
        } catch (TemplateException e) {
            throw new MojoExecutionException("EasyDao engine runtime exception: freemarker template error.", e);
        } catch (IOException e) {
            throw new MojoExecutionException("EasyDao engine runtime exception.", e);
        } catch (SQLException e) {
            throw new MojoExecutionException("EasyDao engine runtime exception: database connection error.", e);
        }
    }

    /**
     * Validate dbName.
     * @throws MojoExecutionException wrong configuration parameters.
     */
    protected void validateDbName() throws MojoExecutionException {
        if (dbName == null || dbName.trim().length() == 0 || !dbName.matches(DBNAME_PATTERN)) {
            throw new MojoExecutionException("EasyDao engine configuration exception: wrong dbName. Required field, max. 16 characters, regExp: " + DBNAME_PATTERN);
        } else {
            if (dbName.length() > 16) {
                dbName = dbName.substring(0, 15);
            }
        }
    }

    /**
     * Validate dbType.
     * @throws MojoExecutionException wrong configuration parameters.
     */
    protected void validateDbType() throws MojoExecutionException {
        // dbType
        if (dbType == null || dbType.trim().length() == 0) {
            throw new MojoExecutionException("EasyDao engine configuration exception: wrong dbType. Valid values are: ORACLE11, POSTGRESQL9");
        } else {
            try {
                EngineConfiguration.DATABASE_TYPE.valueOf(dbType);
            } catch (IllegalArgumentException ex) {
                throw new MojoExecutionException("EasyDao engine configuration exception: wrong dbType. Valid values are: ORACLE11, POSTGRESQL9");
            }
        }
    }

    /**
     * Validate boolean values.
     * @param name name of field
     * @param value value of field
     * @throws MojoExecutionException wrong configuration parameters.
     */
    protected void validateBoolean(String name, String value) throws MojoExecutionException {
        if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
            throw new MojoExecutionException("EasyDao engine configuration exception: wrong " + name + " . Valid value is true or false.");
        }
    }

    /**
     * Validate package name.
     * @param name name of field
     * @param value value of field
     * @throws MojoExecutionException wrong configuration parameters.
     */
    protected void validatePackageName(String name, String value) throws MojoExecutionException {
        if (value == null
                || value.trim().length() == 0
                || !value.matches(PACKAGE_PATTERN)) {
            throw new MojoExecutionException("EasyDao engine configuration exception: wrong " + name + ". Required field, regExp: " + PACKAGE_PATTERN);
        }
    }
}
