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

import java.util.Map;

/**
 * Database model builder configuration for model builder
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
class ModelBuilderConfig {

    /**
     * Return java type of field.
     * If you need, then override in descendants.
     * @param typeMap database specific type map database type string (regular expression) -> java type.
     * @param dbType database type string, i.e: numeric(10,2), date, timestamp
     * @return Class java class of db field type
     */
    final protected String convertToJavaType(Map<String, String> typeMap, String dbType) throws IllegalArgumentException {
        String className = null;
        boolean found = false;
        for (Map.Entry<String, String> e : typeMap.entrySet()) {
            if (dbType.matches(e.getKey())) {
                // found type
                className = e.getValue();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("There is no Java type definiton for " + dbType + " database type!");
        }
        return className;
    }

    /**
     * Return replacement name of table or field name.
     * You must use it, when database table names does not always use table or field name convention,
     * i.e.: CUS_CUSTOMER (good), CUS_ORDER (good), CSTATISTIC (it is a wrong table name declaration).
     * @param dbName database table or field name
     * @return replacementMap name map for the table or field
     */
    final protected String getReplacementName(Map<String, String> replacementMap, String dbName) {
        String rn = replacementMap.get(dbName);
        if (rn == null) {
            rn = replacementMap.get(dbName.toLowerCase());
            if (rn == null) {
                replacementMap.get(dbName.toUpperCase());
            }
        }
        return rn;
    }

}
