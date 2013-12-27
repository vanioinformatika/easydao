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

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        
        Map<String, String> configMap = new HashMap<>();
        configMap.put("database.name", "callisto");
        configMap.put("databaseType", "POSTGRESQL9");
        configMap.put("url", "jdbc:postgresql://localhost/callistof");
        configMap.put("username", "callisto");
        configMap.put("password", "callisto");
        configMap.put("tablePrefix", "true");
        configMap.put("tableSuffix", "false");
        configMap.put("fieldPrefix", "true");
        configMap.put("fieldSuffix", "false");
        configMap.put("generatedSourcePath", "/tmp/database");
        configMap.put("packageOfJavaModel", "hu.vanio.easydaodemo.model");
        configMap.put("packageOfJavaDao", "hu.vanio.easydaodemo.dao");
        configMap.put("daoSuffix", "Dao");
        configMap.put("replacementTableFilename", "replacement-table");
        configMap.put("replacementFieldFilename", "replacement-field");
        
        Engine instance = new Engine(configMap);
        
        assertEquals("callisto", instance.getEngineConf().getDatabase().getName());
        assertEquals("POSTGRESQL9", instance.getEngineConf().getDatabaseType().name());
    }

}
