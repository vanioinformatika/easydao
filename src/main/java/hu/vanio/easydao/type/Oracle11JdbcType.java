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

import java.util.HashMap;

/**
 * Oracle 11g JDBC Database - Java type mapping.
 * @see http://docs.oracle.com/cd/B28359_01/server.111/b28286/sql_elements001.htm#SQLRF50950
 * @see http://docs.oracle.com/cd/B19306_01/java.102/b14188/datamap.htm
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
final public class Oracle11JdbcType {

    public static final HashMap<String, Class> MAP = new HashMap<>();

    static {
        MAP.put("bigint|int8", Long.class);
        MAP.put("boolean", Boolean.class);
        MAP.put("boolean\\[\\]", Boolean[].class);
        MAP.put("bytea", Byte[].class);
        MAP.put("char", String.class);
        MAP.put("character", String.class);
        MAP.put("character\\([\\d]*\\)", String.class);
        MAP.put("character\\([\\d]*\\)\\[\\]", String[].class);
        MAP.put("character\\[\\]", String[].class);
        MAP.put("character varying", String.class);
        MAP.put("character varying\\([\\d]*\\)", String.class);
        MAP.put("character varying\\([\\d]*\\)\\[\\]", String[].class);
        MAP.put("date", java.sql.Timestamp.class);
        MAP.put("double precision|float8", Double.class);
        MAP.put("integer|int|int4", Integer.class);
        MAP.put("json", String.class);
        MAP.put("number", Integer.class);
        MAP.put("number\\([1-9]\\)", Integer.class);
        MAP.put("number\\([1-9],[0]*\\)", Integer.class);
        MAP.put("number\\([1][0-9]\\)", Long.class);
        MAP.put("number\\([1][0-9]*,[0]\\)", Long.class);
        MAP.put("number\\([\\d]*,[1-9]*\\)", Double.class);

        MAP.put("number\\[\\]", Integer[].class);
        MAP.put("number\\([1][0-9]\\)\\[\\]", Long[].class);
        MAP.put("number\\([1-9]\\)\\[\\]", Integer[].class);
        MAP.put("number\\([\\d]*,[\\d]*\\)\\[\\]", Double[].class);

        MAP.put("timestamp without time zone", java.sql.Timestamp.class);
        MAP.put("timestamp with time zone", java.sql.Timestamp.class);
        MAP.put("timestamp", java.sql.Timestamp.class);
        MAP.put("varchar2", String.class);
        MAP.put("varchar2\\([\\d]*\\)", String.class);
        MAP.put("uuid", String.class);
        MAP.put("xml", String.class);
    }
}
