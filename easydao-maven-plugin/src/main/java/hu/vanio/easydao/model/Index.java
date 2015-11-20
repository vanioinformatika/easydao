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
package hu.vanio.easydao.model;

import java.util.List;

/**
 * Index metadata
 *
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
public class Index {

    /** The name of this index in the database */
    protected String dbName;
    /** The name of this index in Java code */
    protected String javaName;
    /** Indicates whether this index is unique or not */
    protected boolean unique;
    /** The indexed fields */
    protected List<Field> fields;

    /**
     * Construicts a new instance
     * @param dbName The name of this index in the database
     * @param javaName The name of this index in Java code
     * @param unique Indicates whether this index is unique or not
     * @param fields The indexed fields
     */
    public Index(String dbName, String javaName, boolean unique, List<Field> fields) {
        this.dbName = dbName;
        this.javaName = javaName;
        this.unique = unique;
        this.fields = fields;
    }

    /**
     * The name of this index in the database
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }
    
    /**
     * The name of this index in Java code
     * @return the javaName
     */
    public String getJavaName() {
        return javaName;
    }

    /**
     * Indicates whether this index is unique or not
     * @return the unique
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * The indexed fields
     * @return the fields
     */
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Index{" + "dbName: \"" + dbName + "\", javaName: \"" + javaName + "\", unique: " + unique + ", fields: " + fields + '}';
    }
    
}
