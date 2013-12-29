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

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.RowId;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Oracle 11 configuration unit test.
 * Test database type string - java type conversations.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
@RunWith(Parameterized.class)
public class Oracle11ModelBuilderConfigTest {

    private TestParams testParam;

    public Oracle11ModelBuilderConfigTest(TestParams testParam) {
        this.testParam = testParam;
    }

    /**
     * Test parameters
     */
    static class TestParams {

        public TestParams(String dbType, Class expectedJavaType) {
            this.dbType = dbType;
            this.expectedJavaType = expectedJavaType;
        }

        public String dbType;
        public Class expectedJavaType;
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
     * Test data: database type as string and expected java type.
     * @return 
     */
    @Parameters
    static public Collection<Object[]> data() {
        Object[][] data = {
            {new TestParams("boolean", Boolean.class)},
            {new TestParams("char", String.class)},
            {new TestParams("long", String.class)},
            {new TestParams("string", String.class)},
            {new TestParams("varchar", String.class)},
            {new TestParams("varchar2", String.class)},
            {new TestParams("bytea", byte[].class)},
            {new TestParams("raw", byte[].class)},
            {new TestParams("long raw", byte[].class)},
            {new TestParams("binary_integer", Integer.class)},
            {new TestParams("natural", Integer.class)},
            {new TestParams("naturaln", Integer.class)},
            {new TestParams("pls_integer", Integer.class)},
            {new TestParams("positive", Integer.class)},
            {new TestParams("positiven", Integer.class)},
            {new TestParams("signtype", Integer.class)},
            {new TestParams("int", Integer.class)},
            {new TestParams("integer", Integer.class)},
            {new TestParams("smallint", Integer.class)},
            {new TestParams("dec", BigDecimal.class)},
            {new TestParams("decimal", BigDecimal.class)},
            {new TestParams("float", Double.class)},
            {new TestParams("real", Float.class)},
            {new TestParams("rowid", RowId.class)},
            {new TestParams("clob", Clob.class)},
            {new TestParams("blob", Blob.class)},
            {new TestParams("date", Timestamp.class)},
            {new TestParams("double precision", Double.class)},
            {new TestParams("float", Double.class)},
            {new TestParams("numeric", Integer.class)},
            {new TestParams("numeric(,)", Integer.class)},
            {new TestParams("numeric(1)", Integer.class)},
            {new TestParams("numeric(2)", Integer.class)},
            {new TestParams("numeric(3)", Integer.class)},
            {new TestParams("numeric(4)", Integer.class)},
            {new TestParams("numeric(5)", Integer.class)},
            {new TestParams("numeric(6)", Integer.class)},
            {new TestParams("numeric(7)", Integer.class)},
            {new TestParams("numeric(8)", Integer.class)},
            {new TestParams("numeric(9)", Integer.class)},
            {new TestParams("numeric(1,0)", Integer.class)},
            {new TestParams("numeric(2,0)", Integer.class)},
            {new TestParams("numeric(3,0)", Integer.class)},
            {new TestParams("numeric(4,0)", Integer.class)},
            {new TestParams("numeric(5,0)", Integer.class)},
            {new TestParams("numeric(6,0)", Integer.class)},
            {new TestParams("numeric(7,0)", Integer.class)},
            {new TestParams("numeric(8,0)", Integer.class)},
            {new TestParams("numeric(9,0)", Integer.class)},
            {new TestParams("numeric(11)", Long.class)},
            {new TestParams("numeric(18)", Long.class)},
            {new TestParams("numeric(19)", String.class)},
            {new TestParams("numeric(20)", String.class)},
            {new TestParams("numeric(100)", String.class)},
            {new TestParams("numeric(11,0)", Long.class)},
            {new TestParams("numeric(18,0)", Long.class)},
            {new TestParams("numeric(19,0)", String.class)},
            {new TestParams("numeric(20,0)", String.class)},
            {new TestParams("numeric(100,0)", String.class)},
            {new TestParams("numeric(11,1)", Double.class)},
            {new TestParams("numeric(11,3)", Double.class)},
            {new TestParams("numeric(19,1)", Double.class)},
            
            {new TestParams("number", Integer.class)},
            {new TestParams("number(,)", Integer.class)},
            {new TestParams("number(1)", Integer.class)},
            {new TestParams("number(2)", Integer.class)},
            {new TestParams("number(3)", Integer.class)},
            {new TestParams("number(4)", Integer.class)},
            {new TestParams("number(5)", Integer.class)},
            {new TestParams("number(6)", Integer.class)},
            {new TestParams("number(7)", Integer.class)},
            {new TestParams("number(8)", Integer.class)},
            {new TestParams("number(9)", Integer.class)},
            {new TestParams("number(1,0)", Integer.class)},
            {new TestParams("number(2,0)", Integer.class)},
            {new TestParams("number(3,0)", Integer.class)},
            {new TestParams("number(4,0)", Integer.class)},
            {new TestParams("number(5,0)", Integer.class)},
            {new TestParams("number(6,0)", Integer.class)},
            {new TestParams("number(7,0)", Integer.class)},
            {new TestParams("number(8,0)", Integer.class)},
            {new TestParams("number(9,0)", Integer.class)},
            {new TestParams("number(11)", Long.class)},
            {new TestParams("number(18)", Long.class)},
            {new TestParams("number(19)", String.class)},
            {new TestParams("number(20)", String.class)},
            {new TestParams("number(100)", String.class)},
            {new TestParams("number(11,0)", Long.class)},
            {new TestParams("number(18,0)", Long.class)},
            {new TestParams("number(19,0)", String.class)},
            {new TestParams("number(20,0)", String.class)},
            {new TestParams("number(100,0)", String.class)},
            {new TestParams("number(11,1)", Double.class)},
            {new TestParams("number(11,3)", Double.class)},
            {new TestParams("number(19,1)", Double.class)},
            
            {new TestParams("date", Timestamp.class)},
            {new TestParams("timestamp with tz", Timestamp.class)},
            {new TestParams("timestamp with local tz", Timestamp.class)},
            {new TestParams("timestamp", Timestamp.class)},
            {new TestParams("varchar2", String.class)},
            {new TestParams("varchar2(100)", String.class)},
            {new TestParams("varchar2(4000)", String.class)},
        };
        return Arrays.asList(data);
    }

    /**
     * Test of getSelectForPrimaryKeyFieldNameList method, of class Oracle11ModelBuilderConfig.
     */
    @Test
    public void testGetJavaType() {
        Oracle11ModelBuilderConfig instance = new Oracle11ModelBuilderConfig();
        Class expResult = testParam.expectedJavaType;
        Class result = instance.getJavaType(testParam.dbType);
        System.out.println("testGetJavaType: " + testParam.dbType + " " + result);
        assertTrue(result.equals(expResult));
    }

}
