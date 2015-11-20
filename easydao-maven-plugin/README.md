easydao-maven-plugin
====================

EasyDao Maven Plugin. Automatic Dao and Model class generation from database with Maven plugin. 

Runs on Java 7, **generated source compatible with Java 5.**

> If you want to generate java model from database, then use this maven plugin.

> You can find the results of the code generation in **&lt;generatedSourcePath&gt;/metadata.txt.** It contains several useful information about your database, **and you can easily define replacement files based on its content.**

See the example application, that uses this maven plugin: https://github.com/vanioinformatika/easydao-demo-database-model

Project's maven repository: https://bintray.com/vanioinformatika/releases/

Add to settings.xml:
```xml
<?xml version='1.0' encoding='UTF-8'?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
    <profiles>
    ...
    	 <profile>
            <id>vanio-bintray-releases</id>
            <repositories>
                <repository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintraycentral</id>
                    <name>bintray</name>
                    <url>http://dl.bintray.com/vanioinformatika/releases/</url>
                </repository>
            </repositories>
            <pluginRepositories>
                <pluginRepository>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                    <id>bintraycentral</id>
                    <name>bintray-plugins</name>
                    <url>http://dl.bintray.com/vanioinformatika/releases/</url>
                </pluginRepository>
            </pluginRepositories>
        </profile>
    <activeProfiles>
    ...
    	<activeProfile>vanio-bintray-releases</activeProfile>
    </activeProfiles>
</settings>
```

# Configuration parameters

Configuration parameters are required or optional. All optional parameters have default value, these **default values are recommended.**

Required parameters are project dependent.

Configuration example:
```xml
<plugin>
    <groupId>hu.vanio.maven.plugins</groupId>
    <artifactId>easydao-maven-plugin</artifactId>
    <version>1.0.15</version>
    <configuration>
        <dbName>callisto</dbName>
        <dbType>POSTGRESQL9</dbType>
        <dbUrl>jdbc:postgresql://localhost/callistof</dbUrl>
        <dbUsername>callisto</dbUsername>
        <dbPassword>callisto</dbPassword>
        <!-- optional -->
        <tablePrefix>true</tablePrefix>
        <tableSuffix>false</tableSuffix>
        <fieldPrefix>true</fieldPrefix>
        <fieldSuffix>false</fieldSuffix>
        <!-- optional, please do not use: generatedSourcePath>/tmp/easydaodemo-database_model</generatedSourcePath-->
        <packageOfJavaModel>hu.vanio.easydaodemo.model</packageOfJavaModel>
        <packageOfJavaDao>hu.vanio.easydaodemo.dao</packageOfJavaDao>
        <!-- optional, please use default: daoSuffix>Dao</daoSuffix -->
        <replacementTableFilename>replacement-table</replacementTableFilename>
        <replacementFieldFilename>replacement-field</replacementFieldFilename>
        <enumFieldFilename>enum-field</enumFieldFilename>
        <sequenceNameConvention>PREFIXED_TABLE_NAME</sequenceNameConvention>
        <!-- optional -->
        <generateModelToString>false</generateModelToString>
        <!-- optional -->
        <licenseFilename>${baesdir}/src/myLicense.txt</licenseFilename>
        <!-- optional -->
        <language>hu</language>
        <!-- optional -->
        <tableNameIncludes>
            <tableNameInclude>CAL_.+</tableNameInclude>
        </tableNameIncludes>
        <!-- optional -->
        <encoding>utf-8</encoding>
        <!-- optional -->
        <silent>true</silent>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
## dbName
Database name, required. If you are using more than one database in your code, then you need namespaces. 
The value of the dbName parameter will act as a namespace, it will be the last element of the package name of the generated classes.
If you set to e.g: callisto, the package name of the generated model and dao classes will be: **&lt;packageOfJavaModel&gt;.callisto** and **&lt;packageOfJavaDao&gt;.callisto** respectively.

> Generated dao classes are based on Spring Framework! If you are using more than one database, then the generated models will use different DataSources. 
The **callisto** will be set in the dao classes as data source name with a @Qualifier annotation.

## dbType
Database type, required. You must set to **ORACLE11** or **POSTGRESQL9**

## dbUrl, dbusername, dbPassword
Database connection parameters, required.

## tablePrefix
Database table prefix: true or false, optional. Default is true.
If true, then the first part of the table names will be removed before generating Java names. Parts are separated by underscore characters. e.g: SHP_CUSTOMER_ORDER -> CustormerOrder.java and CustomerOrderDao.java

## tableSuffix
Database table suffix: true or false, optional. Default is false.
If true, then the last part of the table names will be removed before generating Java names. Parts are separated by underscore characters. e.g: CUSTOMER_ORDER_SHP -> CustomerOrder.java and CustomerOrderDao.java

## fieldPrefix
Database field prefix: true or false, optional. Default is true.
If true, then the first part of the field names will be removed before generating Java names. Parts are separated by underscore characters. e.g: CUS_CUSTOMER table field is CUS_PK -> pk

## fieldSuffix
If true, then the last part of the field names will be removed before generating Java names. Parts are separated by underscore characters. e.g: CUSTOMER_CUS table field is PK_CUS -> pk

## generatedSourcePath
Optional, changing its value is not recommended! Generate source codes to this directory. Default value is *target/generated-source/easydao-classes/*

## packageOfJavaModel
Package name of the generated Java model classes, required. Java model codes will contain this package name.

## packageOfJavaDao
Package name of the generated Java dao classes, required. Java dao codes will contain this package name.

## daoSuffix
Generated Java dao class name suffix, optional. Default value is Dao.

## generateModelToString
If true, a toString method that outputs data in JSON format will be generated for all model classes

## replacementTableFilename
Replacement file name for tables, required. The name of the _properties_ file in src/main/resources *without* the properties extension, e.g: replacement-table
 
If you want to ignore a table from the Java code generation, put it into the file, e.g:
```
CUS_CUSTOMER_ORDER =
```

If you want to generate Java code with a special name (e.g: Order.java), then:
```
CUS_CUSTOMER_ORDER = Order
```
> You can find the results of the code generation in **&lt;generatedSourcePath&gt;/metadata.txt.** It contains several useful information about your database, **and you can easily define replacement files based on its content.**

## replacementFieldFilename
Replacement file name for fields. Resource bundle file in src/main/resources without file extension, e.g: replacement-field
 
If you want to ignore a field from java code generation, then put it into the file as TABLENAME.FIELDNAME, e.g:
```
CUS_CUSTOMER_ORDER.SECRET_CODE =
```

If you want to generate with a special name (e.g: orderType), then:
```
CUS_CUSTOMER_ORDER.ORDER_MODE = orderType
```

> You can find the results of the code generation in **&lt;generatedSourcePath&gt;/metadata.txt.** It contains several useful information about your database, **and you can easily define replacement files based on its content.**

## enumFieldFilename
Map file name for fields with enumerated values. Resource bundle file in src/main/resources without file extension, e.g: enum-field
 
If you want to use Java enum for a database field, put it into the file as TABLENAME.FIELDNAME = <fully qualified classname of the enum>, e.g:

```
CUS_CUSTOMER_ORDER.ORDER_MODE = hu.vanio.myapp.model.OrderMode
```

## sequenceNameConvention
Defines database sequence naming convention with **SEQ** string.

* **recommended** Generate sequence names by table name's suffix with _SEQ (e.g.: MY_TABLE_NAME -> MY_TABLE_NAME_SEQ)
    SUFFIXED_TABLE_NAME
    
*  Generate sequence names by table name's prefix with SEQ_ (e.g.: MY_TABLE_NAME -> SEQ_MY_TABLE_NAME):
    PREFIXED_TABLE_NAME
    
* Generate sequence names by field's prefix with SEQ_ (e.g.: MY_FIELD_NAME -> SEQ_MY_FIELD_NAME)
    PREFIXED_FIELD_NAME
    
* Generate sequence names by field's names suffix with _SEQ (e.g.: MY_FIELD_NAME -> MY_FIELD_NAME_SEQ)
    SUFFIXED_FIELD_NAME
    
* Generate sequence names by table name's prefix with SEQ_ and with field name (e.g.: MY_TABLE_NAME.MY_FIELD_NAME -> SEQ_MY_TABLE_NAME_MY_FIELD_NAME)
    PREFIXED_TABLE_NAME_WITH_FIELD_NAME
    
* Generate sequence names by table name's suffix with field name and _SEQ (e.g.: MY_TABLE_NAME.MY_FIELD_NAME -> MY_TABLE_NAME_MY_FIELD_NAME_SEQ)
    SUFFIXED_TABLE_NAME_WITH_FIELD_NAME;

## licenseFilename
Defines your license file, default: null -> no license. It will be inserted in your generated sources. Text must be in java comment format.

## language
Defines the language of the comments in the generated Java sources.
Possible values: 'hu' or 'en' ('en' is the defult)

## tableNameIncludes
Defines a list of regex patterns to specify the table names to be included in Java code generation

## encoding
Defines the encoding of the generated Java sources. Default: ${project.build.sourceEncoding}

## silent
Indicates whether normal output should be suppressed during code generation. If set, only warnings will be printed.). Default: true
