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

/**
 * Field meta data
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class Field {

    private boolean primaryKey;
    private boolean nullable;
    private boolean array;
    private String dbName;
    private String dbType;
    private String comment;
    private String javaName;
    private Class javaType;

    public Field(boolean primaryKey, boolean nullable, boolean array, String dbName, String dbType, String comment, String javaName, Class javaType) {
        this.primaryKey = primaryKey;
        this.nullable = nullable;
        this.array = array;
        this.dbName = dbName;
        this.dbType = dbType;
        this.comment = comment;
        this.javaName = javaName;
        this.javaType = javaType;
    }

    @Override
    public String toString() {
        return "Field{" + ", dbName=" + dbName + ", javaName=" + javaName + ", dbType=" + dbType + ", javaType=" + javaType
                + ", primaryKey=" + primaryKey + ", nullable=" + nullable + ", array=" + array
                + ", comment=" + comment + "}";
    }
    
    /**
     * Return javaType as string.
     * @return javaType as string 
     */
    public String getJavaTypeAsString() {
        return this.javaType.getSimpleName();
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * @return the array
     */
    public boolean isArray() {
        return array;
    }

    /**
     * @param array the array to set
     */
    public void setArray(boolean array) {
        this.array = array;
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
     * @return the dbType
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * @param dbType the dbType to set
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
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

    /**
     * @return the javaType
     */
    public Class getJavaType() {
        return javaType;
    }

    /**
     * @param javaType the javaType to set
     */
    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

}
