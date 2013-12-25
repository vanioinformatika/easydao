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
 * PostgreSQL 9 configuration unit test.
 * Test database type string - java type conversations.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
@RunWith(Parameterized.class)
public class PostgreSql9ConfigTest {

    private TestParams testParam;

    public PostgreSql9ConfigTest(TestParams testParam) {
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
            {new TestParams("boolean[]", Boolean[].class)},
            {new TestParams("bytea", Byte[].class)},
            {new TestParams("character", String.class)},
            {new TestParams("character(20)", String.class)},
            {new TestParams("character(20)[]", String[].class)},
            {new TestParams("character[]", String[].class)},
            {new TestParams("character varying", String.class)},
            {new TestParams("character varying(200)", String.class)},
            {new TestParams("character varying(200)[]", String[].class)},
            {new TestParams("date", Timestamp.class)},
            {new TestParams("double precision", Double.class)},
            {new TestParams("float8", Double.class)},
            {new TestParams("integer", Integer.class)},
            {new TestParams("int", Integer.class)},
            {new TestParams("int4", Integer.class)},
            {new TestParams("smallint", Integer.class)},
            {new TestParams("smallserial", Integer.class)},
            {new TestParams("serial", Integer.class)},
            {new TestParams("bigint", Long.class)},
            {new TestParams("int8", Long.class)},
            {new TestParams("bigserial", Long.class)},
            {new TestParams("money", Long.class)},
            {new TestParams("json", String.class)},
            {new TestParams("numeric", Integer.class)},
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
            {new TestParams("numeric(19)", Long.class)},
            {new TestParams("numeric(11,0)", Long.class)},
            {new TestParams("numeric(19,0)", Long.class)},
            {new TestParams("numeric(11,1)", Double.class)},
            {new TestParams("numeric(11,3)", Double.class)},
            {new TestParams("numeric(19,1)", Double.class)},
            {new TestParams("numeric[]", Integer[].class)},
            {new TestParams("numeric(10)[]", Long[].class)},
            {new TestParams("numeric(19)[]", Long[].class)},
            {new TestParams("numeric(9)[]", Integer[].class)},
            {new TestParams("numeric(11,3)[]", Double[].class)},
            {new TestParams("numeric(19,2)[]", Double[].class)},
            {new TestParams("timestamp without time zone", Timestamp.class)},
            {new TestParams("timestamp with time zone", Timestamp.class)},
            {new TestParams("timestamp", Timestamp.class)},
            {new TestParams("text", String.class)},
            {new TestParams("uuid", String.class)},
            {new TestParams("xml", String.class)}
        };
        return Arrays.asList(data);
    }

    /**
     * Test of getSelectForPrimaryKeyFieldNameList method, of class PostgreSql9Config.
     */
    @Test
    public void testGetJavaType() {
        PostgreSql9Config instance = new PostgreSql9Config();
        Class expResult = testParam.expectedJavaType;
        Class result = instance.getJavaType(testParam.dbType);
        System.out.println("testGetJavaType: " + testParam.dbType + " " + result);
        assertTrue(result.equals(expResult));
    }

}
