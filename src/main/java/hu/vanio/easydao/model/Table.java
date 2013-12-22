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
 * Database's table meta data
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Table {

    /* Table name in database */
    private String dbName;
    /* Table comment */
    private String comment;
    /* Table's java name */
    private String javaName;
    /* Table's fields */
    private List<Field> fieldList;

    public Table(String dbName, String comment, String javaName, List<Field> fieldList) {
        this.dbName = dbName;
        this.comment = comment;
        this.javaName = javaName;
        this.fieldList = fieldList;
    }

//<editor-fold defaultstate="collapsed" desc="gettersetter">
    /**
     * @return the fieldList
     */
    public List<Field> getFieldList() {
        return fieldList;
    }

    /**
     * @param fieldList the fieldList to set
     */
    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the javaName
     */
    public String getJavaName() {
        return javaName;
    }

    /**
     * @param javaName the javaName to set
     */
    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    //</editor-fold>
}
