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

import java.util.Arrays;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModelException;

/**
 *
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
public class LocalisedMessagesTest {

    public LocalisedMessagesTest() {
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
     * Test of getMessage method, of class LocalisedMessages.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        LocalisedMessages instance = new LocalisedMessages("messages", Locale.ENGLISH);
        String expResult = "Generated from TESZT database table.";
        String result = instance.getMessage("generatedFrom", "TESZT");
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessage method, of class LocalisedMessages.
     */
    @Test
    public void testExec() throws TemplateModelException {
        System.out.println("exec");
        LocalisedMessages instance = new LocalisedMessages("messages", Locale.ENGLISH);
        String expResult = "Generated from TESZT database table.";
        String result = (String) instance.exec(Arrays.asList(new SimpleScalar("generatedFrom"), new SimpleScalar("TESZT")));
        assertEquals(expResult, result);
    }

}
