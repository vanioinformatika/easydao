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
package hu.vanio.maven.plugins.easydao;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Generator - Maven plugin unit test.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class GeneratorTest {

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
     * Test of validateDbName method, of class Generator.
     */
    @Test
    public void testValidateDbName_GOOD1() throws Exception {
        System.out.println("testValidateDbName_GOOD1");
        Generator instance = new Generator();
        instance.dbName = "callisto";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test
    public void testValidateDbName_GOOD2() throws Exception {
        System.out.println("testValidateDbName_GOOD2");
        Generator instance = new Generator();
        instance.dbName = "callisto1111";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbName_WRONG1() throws Exception {
        System.out.println("testValidateDbName_WRONG1");
        Generator instance = new Generator();
        instance.dbName = "CALLISTO";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbName_WRONG2() throws Exception {
        System.out.println("testValidateDbName_WRONG2");
        Generator instance = new Generator();
        instance.dbName = "111callisto";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbName_WRONG3() throws Exception {
        System.out.println("testValidateDbName_WRONG3");
        Generator instance = new Generator();
        instance.dbName = "cal-listo";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbName_WRONG4() throws Exception {
        System.out.println("testValidateDbName_WRONG4");
        Generator instance = new Generator();
        instance.dbName = "cal listo";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbName_WRONG5() throws Exception {
        System.out.println("testValidateDbName_WRONG5");
        Generator instance = new Generator();
        instance.dbName = "    ";
        instance.validateDbName();
    }

    /**
     * Test of validateDbName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbName_NULL() throws Exception {
        System.out.println("testValidateDbName_NULL");
        Generator instance = new Generator();
        instance.dbName = null;
        instance.validateDbName();
    }

    /**
     * Test of validatePackageName method, of class Generator.
     */
    @Test
    public void testValidatePackageName_GOOD1() throws Exception {
        System.out.println("testValidatePackageName_GOOD1");
        Generator instance = new Generator();
        instance.packageOfJavaModel = "hu.vanio.easydao.demo";
        instance.validatePackageName("packageOfJavaModel", instance.packageOfJavaModel);
    }

    /**
     * Test of validatePackageName method, of class Generator.
     */
    @Test
    public void testValidatePackageName_GOOD2() throws Exception {
        System.out.println("testValidatePackageName_GOOD2");
        Generator instance = new Generator();
        instance.packageOfJavaModel = "hu";
        instance.validatePackageName("packageOfJavaModel", instance.packageOfJavaModel);
    }

    /**
     * Test of validatePackageName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidatePackageName_NULL() throws Exception {
        System.out.println("testValidatePackageName_NULL");
        Generator instance = new Generator();
        instance.packageOfJavaModel = null;
        instance.validatePackageName("packageOfJavaModel", instance.packageOfJavaModel);
    }

    /**
     * Test of validatePackageName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidatePackageName_WRONG1() throws Exception {
        System.out.println("testValidatePackageName_WRONG1");
        Generator instance = new Generator();
        instance.packageOfJavaModel = "...";
        instance.validatePackageName("packageOfJavaModel", instance.packageOfJavaModel);
    }

    /**
     * Test of validatePackageName method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidatePackageName_WRONG2() throws Exception {
        System.out.println("testValidatePackageName_WRONG1");
        Generator instance = new Generator();
        instance.packageOfJavaModel = "    ";
        instance.validatePackageName("packageOfJavaModel", instance.packageOfJavaModel);
    }

    /**
     * Test of validateBoolean method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateBoolean_WRONG1() throws Exception {
        System.out.println("testValidateBoolean_WRONG1");
        Generator instance = new Generator();
        instance.tablePrefix = "tuer";
        instance.validateBoolean("tablePrefix", instance.tablePrefix);
    }

    /**
     * Test of validateBoolean method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateBoolean_WRONG2() throws Exception {
        System.out.println("testValidateBoolean_WRONG2");
        Generator instance = new Generator();
        instance.tablePrefix = "   ";
        instance.validateBoolean("tablePrefix", instance.tablePrefix);
    }

    /**
     * Test of validateBoolean method, of class Generator.
     */
    @Test
    public void testValidateBoolean_TRUE() throws Exception {
        System.out.println("testValidateBoolean_TRUE");
        Generator instance = new Generator();
        instance.tablePrefix = "TRUE";
        instance.validateBoolean("tablePrefix", instance.tablePrefix);
    }

    /**
     * Test of validateBoolean method, of class Generator.
     */
    @Test
    public void testValidateBoolean_true() throws Exception {
        System.out.println("testValidateBoolean_true");
        Generator instance = new Generator();
        instance.tablePrefix = "true";
        instance.validateBoolean("tablePrefix", instance.tablePrefix);
    }

    /**
     * Test of validateBoolean method, of class Generator.
     */
    @Test
    public void testValidateBoolean_FALSE() throws Exception {
        System.out.println("testValidateBoolean_FALSE");
        Generator instance = new Generator();
        instance.tablePrefix = "FALSE";
        instance.validateBoolean("tablePrefix", instance.tablePrefix);
    }

    /**
     * Test of validateBoolean method, of class Generator.
     */
    @Test
    public void testValidateBoolean_false() throws Exception {
        System.out.println("testValidateBoolean_false");
        Generator instance = new Generator();
        instance.tablePrefix = "false";
        instance.validateBoolean("tablePrefix", instance.tablePrefix);
    }

    /**
     * Test of validateDbType method, of class Generator.
     */
    @Test
    public void testValidateDbType_ORACLE11() throws Exception {
        System.out.println("testValidateDbType_ORACLE11");
        Generator instance = new Generator();
        instance.dbType = "ORACLE11";
        instance.validateDbType();
    }

    /**
     * Test of validateDbType method, of class Generator.
     */
    @Test
    public void testValidateDbType_POSTGRESQL9() throws Exception {
        System.out.println("testValidateDbType_POSTGRESQL9");
        Generator instance = new Generator();
        instance.dbType = "POSTGRESQL9";
        instance.validateDbType();
    }

    /**
     * Test of validateDbType method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbType_WRONG_postgresql9() throws Exception {
        System.out.println("WRONG_");
        Generator instance = new Generator();
        instance.dbType = "postgresql9";
        instance.validateDbType();
    }

    /**
     * Test of validateDbType method, of class Generator.
     */
    @Test(expected = MojoExecutionException.class)
    public void testValidateDbType_WRONG_postgres() throws Exception {
        System.out.println("testValidateDbType_WRONG_postgres");
        Generator instance = new Generator();
        instance.dbType = "postgres";
        instance.validateDbType();
    }

}
