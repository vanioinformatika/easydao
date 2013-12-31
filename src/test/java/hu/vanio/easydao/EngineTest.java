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

import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Engine unit test.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class EngineTest {

    public EngineTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of testInitEngineConfiguration method, of class Engine.
     */
    @Test
    public void testInitEngineConfiguration() throws Exception {
        System.out.println("testInitEngineConfiguration");
        
        Properties props = new Properties();
        props.put("database.name", "callisto");
        props.put("databaseType", "POSTGRESQL9");
        props.put("url", "jdbc:postgresql://localhost/callistof");
        props.put("username", "callisto");
        props.put("password", "callisto");
        props.put("tablePrefix", "true");
        props.put("tableSuffix", "false");
        props.put("fieldPrefix", "true");
        props.put("fieldSuffix", "false");
        props.put("generatedSourcePath", "/tmp/database");
        props.put("packageOfJavaModel", "hu.vanio.easydaodemo.model");
        props.put("packageOfJavaDao", "hu.vanio.easydaodemo.dao");
        props.put("daoSuffix", "Dao");
        props.put("sequenceNameConvention", "PREFIXED_TABLE_NAME");
        props.put("replacementTableFilename", "replacement-table");
        props.put("replacementFieldFilename", "replacement-field");
        //props.put("licenseFilename", null);
        
        EngineConfiguration engineConf = EngineConfiguration.createFromProperties(props);
        
        Engine instance = new Engine(engineConf);
        
        assertEquals("callisto", instance.getEngineConfiguration().getDatabase().getName());
        assertEquals("POSTGRESQL9", instance.getEngineConfiguration().getDatabaseType().name());
        assertEquals(EngineConfiguration.MISSING_LICENSE_TEXT, instance.getEngineConfiguration().getLicenseText());

        java.net.URL url = Thread.currentThread().getContextClassLoader().getResource("license.txt");
        
        props.put("licenseFilename", url.getFile());
        engineConf = EngineConfiguration.createFromProperties(props);
        instance = new Engine(engineConf);
        
        assertEquals("callisto", instance.getEngineConfiguration().getDatabase().getName());
        assertEquals("POSTGRESQL9", instance.getEngineConfiguration().getDatabaseType().name());
        
        assertNotNull(instance.getEngineConfiguration().getLicenseText());
        assertEquals("DUMMY LICENSE", instance.getEngineConfiguration().getLicenseText());
    }

}
