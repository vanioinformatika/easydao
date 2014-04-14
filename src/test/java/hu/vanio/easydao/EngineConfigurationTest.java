package hu.vanio.easydao;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */


public class EngineConfigurationTest {

    private String propertiesName = "test-config-oracle11.properties";
    
    public EngineConfigurationTest() {
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
     * Test of createFromProperties method, of class EngineConfiguration.
     */
    @Test
    public void testCreateFromProperties_String() throws Exception {
        System.out.println("createFromProperties");
        
        EngineConfiguration result = EngineConfiguration.createFromProperties(propertiesName);
        assertProperties(result);
    }

    /**
     * Test of createFromProperties method, of class EngineConfiguration.
     */
    @Test
    public void testCreateFromProperties_Properties() throws Exception {
        System.out.println("createFromProperties");
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName));
        EngineConfiguration result = EngineConfiguration.createFromProperties(props);
        assertProperties(result);
    }
    
    private void assertProperties(EngineConfiguration conf) {
        assertEquals("idtv", conf.getDatabase().getName());
        assertEquals(EngineConfiguration.DATABASE_TYPE.ORACLE11, conf.getDatabaseType());
        assertEquals("jdbc:oracle:thin:@10.128.2.82:1521:HOTDEV", conf.getUrl());
        assertEquals("idtvt1", conf.getUsername());
        assertEquals("idtvt1", conf.getPassword());
        assertEquals(true, conf.isTablePrefix());
        assertEquals(false, conf.isTableSuffix());
        assertEquals(true, conf.isFieldPrefix());
        assertEquals(false, conf.isFieldSuffix());
        assertEquals("/tmp/database_model", conf.getGeneratedSourcePath());
        assertEquals("hu.vanio.easydaodemo.model", conf.getPackageOfJavaModel());
        assertEquals("hu.vanio.easydaodemo.dao", conf.getPackageOfJavaDao());
        assertEquals("Dao", conf.getDaoSuffix());
        assertEquals(true, conf.isGenerateModelToString());
        assertEquals("replacement-table", conf.getReplacementTableFilename());
        assertEquals("replacement-field", conf.getReplacementFieldFilename());
        assertEquals(EngineConfiguration.SEQUENCE_NAME_CONVENTION.SUFFIXED_TABLE_NAME, conf.getSequenceNameConvention());
        assertEquals(Locale.getDefault(), conf.getLocale());
        assertEquals(Arrays.asList("IR_.+", "KR_.+"), conf.getTableNameIncludes());
    }

    /**
     * Test of getPropertyAsList method, of class EngineConfiguration.
     */
    @Test
    public void testGetPropertyAsList() {
        System.out.println("getPropertyAsList");
        Properties props = new Properties();
        String key = "testProp";
        props.setProperty(key, "alpha,   beta, gamma");
        List<String> expResult = Arrays.asList("alpha", "beta", "gamma");
        List<String> result = EngineConfiguration.getPropertyAsList(props, key);
        assertEquals(expResult, result);
    }

    
}
