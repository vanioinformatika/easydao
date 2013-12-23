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

import hu.vanio.easydao.modelbuilder.ModelBuilder;
import hu.vanio.easydao.modelbuilder.ModelBuilderUtil;
import hu.vanio.easydao.modelbuilder.PostgreSqlModelBuilderImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Application start
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Start {

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());

        String url = "jdbc:postgresql://localhost/callistof";
        String username = "callisto";
        String password = "callisto";

        try (Connection con = DriverManager.getConnection(url, username, password);) {
//            BeanGenerator bg = new BeanGenerator(
//                    simpleJdbcTemplate,
//                    "/tmp/callistogen/", // a generált Java kód package gyökere a filerendszerben
//                    "", // az osztálynevek előtagja (pl. a kezelt db. sémára utalhat)
//                    "hu.jura.callisto.model", // a modell osztályok package-e
//                    null, // a modell osztályok nevének utótagja
//                    "hu.jura.callisto.dao", // a dao/service osztályok package-e
//                    "Dao", // a dao/service osztályok nevének utótagja
//                    "cal_", // a feldolgozni kívánt táblák nevének eleje (szűrő)
//                    "utf-8", // a generált Java kód kódolása
//                    SequenceNameStrategy.PREFIXED_TABLE_NAME_WITH_FIELD_NAME
//            );
//            bg.generate();
            
            // Building java model from database metadata
            ModelBuilder g = new PostgreSqlModelBuilderImpl(new ModelBuilderUtil());
            // FIXME: hasPostfix and hasPrefix comes from configuration 
            g.getTableList(con, true, true);
        } finally {
            //dataSource.close();
        }
    }
}
