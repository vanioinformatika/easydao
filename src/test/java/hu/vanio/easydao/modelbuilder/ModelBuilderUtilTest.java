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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class ModelBuilderUtilTest {

    public ModelBuilderUtilTest() {
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
     * Test of createJavaName method with prefix, of class ModelBuilderUtil.
     */
    @Test
    public void testCreateJavaName_StringWithPrefix() {
        System.out.println("testCreateJavaName_SimpleString");
        String dbName = "CUS_CUSTOMER";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, true, false);
        System.out.println("testCreateJavaName_SimpleString = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with postfix, of class ModelBuilderUtil.
     */
    @Test
    public void testCreateJavaName_StringWithPostfix() {
        System.out.println("testCreateJavaName_StringWithPostfix");
        String dbName = "CUSTOMER_CUS";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, false, true);
        System.out.println("testCreateJavaName_StringWithPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilderUtil.
     */
    @Test
    public void testCreateJavaName_StringWithPrefixAndPostfix() {
        System.out.println("testCreateJavaName_StringWithPrefixAndPostfix");
        String dbName = "CUS_CUSTOMER_XXX";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, true, true);
        System.out.println("testCreateJavaName_StringWithPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method without prefix and postfix, of class ModelBuilderUtil.
     */
    @Test
    public void testCreateJavaName_StringWithoutPrefixAndPostfix() {
        System.out.println("testCreateJavaName_StringWithoutPrefixAndPostfix");
        String dbName = "CUSTOMER";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "Customer";
        String result = instance.createJavaName(dbName, true, false, false);
        System.out.println("testCreateJavaName_StringWithoutPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method without prefix and postfix with 2 string, of class ModelBuilderUtil.
     */
    @Test
    public void testCreateJavaName_MoreStringWithoutPrefixAndPostfix() {
        System.out.println("testCreateJavaName_MoreStringWithoutPrefixAndPostfix");
        String dbName = "CUSTOMER_ORDERS";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "CustomerOrders";
        String result = instance.createJavaName(dbName, true, false, false);
        System.out.println("testCreateJavaName_MoreStringWithoutPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilderUtil.
     */
    @Test
    public void testCreateJavaName_MoreStringWithPrefixAndPostfix() {
        System.out.println("testCreateJavaName_MoreStringWithPrefixAndPostfix");
        String dbName = "CUS_CUSTOMER_ORDERS_ORD";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "CustomerOrders";
        String result = instance.createJavaName(dbName, true, true, true);
        System.out.println("testCreateJavaName_MoreStringWithPrefixAndPostfix = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createJavaName method with prefix and postfix, of class ModelBuilderUtil.
     * This test for table's field name.
     */
    @Test
    public void testCreateJavaName_ThreeStringWithPrefixAndPostfixAndLowercaseFirstChar() {
        System.out.println("testCreateJavaName_ThreeStringWithPrefixAndPostfixAndLowercaseFirstChar");
        String dbName = "CUS_CUSTOMER_ORDERS_LIST_ORD";
        ModelBuilderUtil instance = new ModelBuilderUtil();
        String expResult = "customerOrdersList";
        String result = instance.createJavaName(dbName, false, true, true);
        System.out.println("testCreateJavaName_ThreeStringWithPrefixAndPostfixAndLowercaseFirstChar = " + dbName + " -> " + result);
        assertEquals(expResult, result);
    }

}
