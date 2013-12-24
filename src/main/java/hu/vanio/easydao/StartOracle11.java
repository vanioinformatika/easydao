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

import hu.vanio.easydao.model.Database;
import hu.vanio.easydao.modelbuilder.ModelBuilder;
import hu.vanio.easydao.modelbuilder.Oracle11Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Application start
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class StartOracle11 {
    
    

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());

        // FIXME: read from configuration and move to Engine class
        String url = "jdbc:oracle:thin:@10.128.2.82:1521:HOTDEV";
        String username = "idtvt1";
        String password = "idtvt1";
        boolean hasTablePrefix = false;
        boolean hasTablePostfix = false;
        boolean hasFieldPrefix = false;
        boolean hasFieldPostfix = false;

        try (Connection con = DriverManager.getConnection(url, username, password);) {
            // Building java model from database metadata
            ModelBuilder modelBuilder = new ModelBuilder(con, hasTablePrefix, hasTablePostfix, hasFieldPrefix, hasFieldPostfix, new Oracle11Config());
            // FIXME: hasPostfix and hasPrefix comes from configuration 
            Database database = modelBuilder.build();
        } finally {
            //dataSource.close();
        }
    }
}
