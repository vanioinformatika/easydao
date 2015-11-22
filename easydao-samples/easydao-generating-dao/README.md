easydao-generating-dao
======================

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
    <version>VERSION_NUMBER</version>
    <configuration>
        <dbName>sampledb</dbName>
        <dbType>POSTGRESQL9</dbType>
        <!-- docker inspect sampledb | grep IPAddress -->
        <dbUrl>jdbc:postgresql://172.17.0.2/sampledb</dbUrl>
        <dbUsername>postgres</dbUsername>
        <dbPassword>sample</dbPassword>
        <packageOfJavaModel>hu.vanio.easydao.sample.model</packageOfJavaModel>
        <packageOfJavaDao>hu.vanio.easydao.sample.dao</packageOfJavaDao>
        
        <!-- optional -->
        <tablePrefix>true</tablePrefix>
        <fieldPrefix>true</fieldPrefix>
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
