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
package hu.vanio.easydao.modelbuilder;

import hu.vanio.easydao.Engine;
import hu.vanio.easydao.EngineConfiguration;
import hu.vanio.easydao.model.Field;
import hu.vanio.easydao.model.Table;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import hu.vanio.easydao.LocalisedMessages;

/**
 * ModelBuilder unit test.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class ModelBuilderTest {

    /* Engine test configuration */
    static EngineConfiguration ENGINE_CONF;

    public ModelBuilderTest() {
    }

    /**
     * Init EngineConfiguration ENGINE_CONF.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        EngineConfiguration engineConf = EngineConfiguration.createFromProperties("test-config-postgresql9.properties");
        Engine engine = new Engine(engineConf);
        ENGINE_CONF = engine.getEngineConfiguration();
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
     * We don't test here. We test implementation!
     * @throws java.lang.Exception
     */
    public void testBuild() throws Exception {
    }

    /**
     * We don't test here. We test implementation!
     * @throws java.lang.Exception
     */
    public void testGetTableList() throws Exception {
    }

    /**
     * Test of createJavaName method with prefix, of class ModelBuilder.
     */
    @Test
    public void testCreateJavaName_StringWithPrefix() {
        System.out.println("testCreateJavaName_SimpleString");
        String dbName = "CUS_CUSTOMER";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, true, false);
        System.out.println("testCreateJavaName_SimpleString = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with postfix, of class ModelBuilder.
     */
    @Test
    public void testCreateJavaName_StringWithPostfix() {
        System.out.println("testCreateJavaName_StringWithPostfix");
        String dbName = "CUSTOMER_CUS";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, false, true);
        System.out.println("testCreateJavaName_StringWithPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilder.
     */
    @Test
    public void testCreateJavaName_StringWithPrefixAndPostfix() {
        System.out.println("testCreateJavaName_StringWithPrefixAndPostfix");
        String dbName = "CUS_CUSTOMER_XXX";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, true, true);
        System.out.println("testCreateJavaName_StringWithPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateJavaName_StringWithPrefixAndPostfix_Bug() {
        System.out.println("testCreateJavaName_StringWithPrefixAndPostfix");
        String dbName = "CUS_CUSTOMER";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        instance.createJavaName(dbName, true, true, true);
    }
    
    /**
     * Test of createJavaName method without prefix and postfix, of class ModelBuilder.
     */
    @Test
    public void testCreateJavaName_StringWithoutPrefixAndPostfix() {
        System.out.println("testCreateJavaName_StringWithoutPrefixAndPostfix");
        String dbName = "CUSTOMER";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, false, false);
        System.out.println("testCreateJavaName_StringWithoutPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method without prefix and postfix with 2 string, of class ModelBuilder.
     */
    @Test
    public void testCreateJavaName_MoreStringWithoutPrefixAndPostfix() {
        System.out.println("testCreateJavaName_MoreStringWithoutPrefixAndPostfix");
        String dbName = "CUSTOMER_ORDERS";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "CustomerOrders";
        String result = instance.createJavaName(dbName, true, false, false);
        System.out.println("testCreateJavaName_MoreStringWithoutPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilder.
     */
    @Test
    public void testCreateJavaName_MoreStringWithPrefixAndPostfix() {
        System.out.println("testCreateJavaName_MoreStringWithPrefixAndPostfix");
        String dbName = "CUS_CUSTOMER_ORDERS_ORD";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "CustomerOrders";
        String result = instance.createJavaName(dbName, true, true, true);
        System.out.println("testCreateJavaName_MoreStringWithPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilder.
     * This test for table's field name.
     */
    @Test
    public void testCreateJavaName_ThreeStringWithPrefixAndPostfixAndLowercaseFirstChar() {
        System.out.println("testCreateJavaName_ThreeStringWithPrefixAndPostfixAndLowercaseFirstChar");
        String dbName = "CUS_CUSTOMER_ORDERS_LIST_ORD";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "customerOrdersList";
        String result = instance.createJavaName(dbName, false, true, true);
        System.out.println("testCreateJavaName_ThreeStringWithPrefixAndPostfixAndLowercaseFirstChar = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix BUT ther is no prefix in database name, of class ModelBuilder.
     * This test for table's field name.
     */
    @Test
    public void testCreateJavaName_PrefixAndPostfixButNoPrefixInDatabaseName() {
        System.out.println("testCreateJavaName_PrefixAndPostfixButNoPrefixInDatabaseName");
        String dbName = "CUSTOMER";
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, true, true);
        System.out.println("testCreateJavaName_PrefixAndPostfixButNoPrefixInDatabaseName = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * FIXME: Test of getPrimaryKeyFieldNameList method, of class ModelBuilder.
     */
    public void testGetPrimaryKeyFieldNameList() throws Exception {
        System.out.println("getPrimaryKeyFieldNameList");
        String tableName = "";
        ModelBuilder instance = null;
        List<String> expResult = null;
        List<String> result = instance.getPrimaryKeyFieldNameList(tableName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * FIXME: Test of getFieldList method, of class ModelBuilder.
     */
    public void testGetFieldList() throws Exception {
        System.out.println("getFieldList");
        ModelBuilder instance = null;
        List<Field> expResult = null;
        Table table = new Table("TE_TEST", "Test table data.", "Test", null);

        List<Field> result = instance.getFieldList(table);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of createJavaName method with prefix BUT ther is no prefix in database name, of class ModelBuilder.
     * This test for table's field name.
     */
    @Test
    public void testIsRestrictedWord() {
        System.out.println("testIsRestrictedWord");
        ModelBuilder instance = new ModelBuilder(null, ENGINE_CONF, new PostgreSql9ModelBuilderConfig(), new LocalisedMessages("messages", null));
//        class|this|int|long|byte|short|float|double|boolean|for|while|goto|if|do
        assertTrue(instance.isRestrictedWord("class"));
        assertTrue(instance.isRestrictedWord("interface"));
        assertTrue(instance.isRestrictedWord("public"));
        assertTrue(instance.isRestrictedWord("private"));
        assertTrue(instance.isRestrictedWord("protected"));
        assertTrue(instance.isRestrictedWord("package"));
        assertTrue(instance.isRestrictedWord("this"));
        assertTrue(instance.isRestrictedWord("int"));
        assertTrue(instance.isRestrictedWord("long"));
        assertTrue(instance.isRestrictedWord("short"));
        assertTrue(instance.isRestrictedWord("byte"));
        assertTrue(instance.isRestrictedWord("float"));
        assertTrue(instance.isRestrictedWord("double"));
        assertTrue(instance.isRestrictedWord("boolean"));
        assertTrue(instance.isRestrictedWord("for"));
        assertTrue(instance.isRestrictedWord("while"));
        assertTrue(instance.isRestrictedWord("goto"));
        assertTrue(instance.isRestrictedWord("if"));
        assertTrue(instance.isRestrictedWord("do"));
    }
    
    @Test
    public void testGetSequenceName() throws IOException {
        System.out.println("testGetSequenceName");
        EngineConfiguration engineConf = EngineConfiguration.createFromProperties("test-config-oracle11.properties");
        ModelBuilder instance = new ModelBuilder(null, engineConf, new Oracle11ModelBuilderConfig(), new LocalisedMessages("messages", null));
        
        Field pkField = new Field(true, false, false, false, false, false, false, null, "CUS_ID", "NUMBER(10)", "ID field", "id", "java.lang.Long");
        Field nameField = new Field(false, false, false, false, false, false, false, null, "CUS_NAME", "VARCHAR2", "Name field", "name", "java.lang.String");
        Table table = new Table("TST_CUSTOMER", "Test comment", "Customer", Arrays.asList(pkField, nameField));

        engineConf.setSequenceNameConvention(EngineConfiguration.SEQUENCE_NAME_CONVENTION.PREFIXED_TABLE_NAME);
        assertEquals("SEQ_TST_CUSTOMER", instance.getSequenceName(table));
        
        engineConf.setSequenceNameConvention(EngineConfiguration.SEQUENCE_NAME_CONVENTION.PREFIXED_FIELD_NAME);
        assertEquals("SEQ_CUS_ID", instance.getSequenceName(table));
        
        engineConf.setSequenceNameConvention(EngineConfiguration.SEQUENCE_NAME_CONVENTION.SUFFIXED_TABLE_NAME);
        assertEquals("TST_CUSTOMER_SEQ", instance.getSequenceName(table));
        
        engineConf.setSequenceNameConvention(EngineConfiguration.SEQUENCE_NAME_CONVENTION.SUFFIXED_FIELD_NAME);
        assertEquals("CUS_ID_SEQ", instance.getSequenceName(table));
        
        engineConf.setSequenceNameConvention(EngineConfiguration.SEQUENCE_NAME_CONVENTION.PREFIXED_TABLE_NAME_WITH_FIELD_NAME);
        assertEquals("SEQ_TST_CUSTOMER_CUS_ID", instance.getSequenceName(table));
        
        engineConf.setSequenceNameConvention(EngineConfiguration.SEQUENCE_NAME_CONVENTION.SUFFIXED_TABLE_NAME_WITH_FIELD_NAME);
        assertEquals("TST_CUSTOMER_CUS_ID_SEQ", instance.getSequenceName(table));
    }
    
}