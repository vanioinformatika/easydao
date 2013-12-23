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

package hu.vanio.easydao.type;

import java.util.Map;
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
public class Oracle11JdbcTypeTest {
    
    public Oracle11JdbcTypeTest() {
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
     * Test date type
     */
    @Test
    public void testDate() {
        String dbType = "date";
        Class clazz = null;
        boolean found = false;
        for (Map.Entry<String, Class> e : Oracle11JdbcType.MAP.entrySet()) {
            if (dbType.matches(e.getKey())) {
                // found type
                clazz = e.getValue();
                found = true;
                break;
            }
        }
        assertEquals(java.sql.Timestamp.class, clazz);
    }

    @Test
    public void testNumber10_0() {
        String dbType = "number(10,0)";
        Class clazz = null;
        boolean found = false;
        for (Map.Entry<String, Class> e : Oracle11JdbcType.MAP.entrySet()) {
            if (dbType.matches(e.getKey())) {
                // found type
                clazz = e.getValue();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("There is no Java type definiton for " + dbType + " database type!");
        }
        assertEquals(Long.class, clazz);
    }
    
}
