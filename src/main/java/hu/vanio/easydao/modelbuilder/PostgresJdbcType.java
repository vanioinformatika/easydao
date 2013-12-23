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

import java.util.HashMap;

/**
 * PostgreSQL JDBC Database - Java type mapping.
 * @see http://www.postgresql.org/docs/current/static/datatype.html
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
final public class PostgresJdbcType {

    public static final HashMap<String, Class> MAP = new HashMap<>();

    static {
        MAP.put("bigint|int8", Long.class);
        MAP.put("boolean", Boolean.class);
        MAP.put("boolean\\[\\]", Boolean[].class);
        MAP.put("bytea", Byte[].class);
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
        MAP.put("numeric", Integer.class);
        MAP.put("numeric\\([1-9]\\)", Integer.class);
        MAP.put("numeric\\([1-9],[0]*\\)", Integer.class);
        MAP.put("numeric\\([1][0-9]\\)", Long.class);
        MAP.put("numeric\\([1][0-9]*,[0]*\\)", Long.class);
        MAP.put("numeric\\([\\d]*,[1-9]*\\)", Double.class);

        MAP.put("numeric\\[\\]", Integer[].class);
        MAP.put("numeric\\([1][0-9]\\)\\[\\]", Long[].class);
        MAP.put("numeric\\([1-9]\\)\\[\\]", Integer[].class);
        MAP.put("numeric\\([\\d]*,[\\d]*\\)\\[\\]", Double[].class);

        MAP.put("timestamp without time zone", java.sql.Timestamp.class);
        MAP.put("timestamp with time zone", java.sql.Timestamp.class);
        MAP.put("timestamp", java.sql.Timestamp.class);
        MAP.put("text", String.class);
        MAP.put("uuid", String.class);
        MAP.put("xml", String.class);
    }
}
