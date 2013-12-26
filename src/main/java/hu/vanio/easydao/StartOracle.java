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

import freemarker.template.TemplateException;
import java.io.IOException;
import java.sql.SQLException;

import hu.vanio.easydao.model.Database;

/**
 * Application start
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class StartOracle {

    public static void main(String[] args) throws SQLException, IOException, TemplateException {

        EngineConfiguration conf = new EngineConfiguration();
        conf.setTablePrefix(true);
        conf.setTablePostfix(false);
        conf.setUrl("jdbc:oracle:thin:@10.128.2.82:1521:HOTDEV");
        conf.setUsername("idtvt1");
        conf.setPassword("idtvt1");
        conf.setFieldPrefix(true);
        conf.setFieldPostfix(false);
        conf.setGeneratedSourcePath("/tmp/database_model");
        conf.setPackageOfJavaModel("hu.vanio.easydaodemo.model");
        conf.setPackageOfJavaDao("hu.vanio.easydaodemo.dao");
        conf.setDaoPostfix("Dao");
        conf.setDatabaseType(EngineConfiguration.DATABASE_TYPE.ORACLE11);
        Database database = new Database();
        database.setName("idtv");
        conf.setDatabase(database);
        Engine e = new Engine();
        e.setEngineConf(conf);
        e.execute();

    }
}
