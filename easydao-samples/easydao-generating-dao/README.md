easydao-demo-database-model
===========================

Generate java model and dao from database. Dependency of the EasyDao demo project.

Define your pom.xml dependency - change version number to the latest:

```xml       
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

<!-- ...or postgresql -->
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
    <version>2.0.0-SNAPSHOT</version>
    <configuration>
        <dbName>sampledb</dbName>
        <dbType>POSTGRESQL9</dbType>
        <!-- docker inspect sampledb | grep IPAddress -->
        <dbUrl>jdbc:postgresql://172.17.0.2/sampledb</dbUrl>
        <dbUsername>postgres</dbUsername>
        <dbPassword>sample</dbPassword>
        <!-- optional -->
        <tablePrefix>true</tablePrefix>
        <tableSuffix>false</tableSuffix>
        <fieldPrefix>true</fieldPrefix>
        <fieldSuffix>false</fieldSuffix>
        <!-- optional, please do not use: generatedSourcePath>/tmp/easydaodemo-database_model</generatedSourcePath-->
        <packageOfJavaModel>hu.vanio.easydao.sample.model</packageOfJavaModel>
        <packageOfJavaDao>hu.vanio.easydao.sample.dao</packageOfJavaDao>
        <!-- optional, please use default: daoSuffix>Dao</daoSuffix -->
        <replacementTableFilename>replacement-table</replacementTableFilename>
        <replacementFieldFilename>replacement-field</replacementFieldFilename>
        <sequenceNameConvention>PREFIXED_TABLE_NAME</sequenceNameConvention>
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
