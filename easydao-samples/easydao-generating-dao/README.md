easydao-demo-database-model
===========================

Generate java model and dao from database. Dependency of the EasyDao demo project.

Define your pom.xml dependency - change version number to the latest:
```xml       
<!-- dependency of all projects that uses EasyDao generated classes -->
<dependency>
    <groupId>hu.vanio.easydao</groupId>
    <artifactId>easydao-core</artifactId>
    <version>1.0.4</version>
    <scope>compile</scope>
</dependency>

<!-- oracle -->
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc5</artifactId>
    <version>11.1.0.7.0</version>
</dependency>
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>orai18n</artifactId>
    <version>11.1.0.7.0</version>
</dependency>

<!--postgresql -->
<dependency>
    <groupId>postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>9.1-901-1.jdbc4</version>
</dependency>
```

Choose one of the Oracle or the PostgreSQL database dependency.

You must set easydao-maven-plugin (example data!) - change version number to the latest:
```xml
<plugin>
    <groupId>hu.vanio.maven.plugins</groupId>
    <artifactId>easydao-maven-plugin</artifactId>
    <version>1.0.16</version>
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
        <sequenceNameConvention>SUFFIXED_TABLE_NAME</sequenceNameConvention>
        <!-- optional -->
        <licenseFilename>${basedir}/src/myLicense.txt</licenseFilename>
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
