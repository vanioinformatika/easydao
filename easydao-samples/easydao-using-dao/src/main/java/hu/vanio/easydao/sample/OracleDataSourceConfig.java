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
package hu.vanio.easydao.sample;

import java.sql.SQLException;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Oracle 11 data source configuration.
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
//@Configuration
public class OracleDataSourceConfig {

    // docker inspect sampledb | grep IPAddress
    @Value("#{environment.DB_IP_ADDRESS}")
    String host = "172.17.0.2.";
    String databaseName = "sampledb";
    int portNumber = 0;
    String user = "postgres";
    String password = "sample";

    @Bean(name = "sampledb")
    public DataSource dataSource() throws SQLException {
        OracleDataSource d = new OracleDataSource();
        d.setServerName(host);
        d.setDatabaseName(databaseName);
        d.setPortNumber(portNumber);
        d.setUser(user);
        d.setPassword(password);
        return d;
    }

}
