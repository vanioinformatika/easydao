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

    /** Indicates whether this field is a (or part of a) primary key */
    private boolean primaryKey;
    /** Indicates whether the value of this field can be null */
    private boolean nullable;
    /** Indicates whether this field is an array */
    private boolean array;
    /** Indicates whether the value of this field is enumerated */
    private boolean enumerated;
    /** Indicates whether the value of this field is enumerated and the Java enum that this field is mapped to is not a regular enum. @see hu.vanio.easydao.core.IrregularEnum */
    private boolean irregularEnum;
    /** The name of this field in the database */
    private String dbName;
    /** The type of this field in the database */
    private String dbType;
    /** The comment of this field in the database */
    private String comment;
    /** The name of this field in Java code */
    private String javaName;
    /** The type of this field in Java code */
    private String javaType;

    /**
     * Constructor
     * 
     * @param primaryKey Indicates whether this field is a (or part of a) primary key
     * @param nullable Indicates whether the value of this field can be null
     * @param array Indicates whether this field is an array
     * @param enumerated Indicates whether the value of this field is enumerated
     * @param irregularEnum Indicates whether the value of this field is enumerated and the Java enum that this field is mapped to is not a regular enum. @see hu.vanio.easydao.core.IrregularEnum 
     * @param dbName The name of this field in the database
     * @param dbType The type of this field in the database
     * @param comment The comment of this field in the database
     * @param javaName The name of this field in Java code
     * @param javaType The type of this field in Java code
     */
    public Field(boolean primaryKey, boolean nullable, boolean array, boolean enumerated, boolean irregularEnum, String dbName, String dbType, String comment, String javaName, String javaType) {
        this.primaryKey = primaryKey;
        this.nullable = nullable;
        this.array = array;
        this.enumerated = enumerated;
        this.irregularEnum = irregularEnum;
        this.dbName = dbName;
        this.dbType = dbType;
        this.comment = comment;
        this.javaName = javaName;
        this.javaType = javaType;
    }

    @Override
    public String toString() {
        return "Field{" + "dbName: \"" + dbName + "\", javaName: \"" + javaName + "\", dbType: \"" + dbType + "\", javaType: \"" + getJavaType()
                + "\", primaryKey: " + primaryKey + ", nullable: " + nullable + ", array: " + array + ", enumerated: " + enumerated
                + ", comment: \"" + comment + "\"}";
    }
    
    /**
     * Return javaType as string.
     * @return javaType as string 
     */
    public String getJavaTypeAsString() {
        int lastDotIdx = this.javaType.lastIndexOf('.');
        return this.javaType.substring(lastDotIdx+1);
    }

    /**
     * Indicates whether this field is a (or part of a) primary key
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * Indicates whether this field is a (or part of a) primary key
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Indicates whether the value of this field can be null
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * Indicates whether the value of this field can be null
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * Indicates whether this field is an array
     * @return the array
     */
    public boolean isArray() {
        return array;
    }

    /**
     * Indicates whether this field is an array
     * @param array the array to set
     */
    public void setArray(boolean array) {
        this.array = array;
    }

    /**
     * Indicates whether the value of this field is enumerated
     * @return the enumerated
     */
    public boolean isEnumerated() {
        return enumerated;
    }

    /**
     * Indicates whether the value of this field is enumerated
     * @param enumerated the enumerated to set
     */
    public void setEnumerated(boolean enumerated) {
        this.enumerated = enumerated;
    }

    /**
     * Indicates whether the value of this field is enumerated and the Java enum that this field is mapped to is not a regular enum. @see hu.vanio.easydao.core.IrregularEnum
     * @return the irregularEnum
     */
    public boolean isIrregularEnum() {
        return irregularEnum;
    }

    /**
     * Indicates whether the value of this field is enumerated and the Java enum that this field is mapped to is not a regular enum. @see hu.vanio.easydao.core.IrregularEnum
     * @param irregularEnum the irregularEnum to set
     */
    public void setIrregularEnum(boolean irregularEnum) {
        this.irregularEnum = irregularEnum;
    }

    /**
     * Indicates whether this field is a CLOB
     * @return the clob
     */
    public boolean isClob() {
        return this.javaType.equals(java.sql.Clob.class.getName());
    }

    /**
     * Indicates whether this field is a BLOB
     * @return the blob
     */
    public boolean isBlob() {
        return this.javaType.equals(java.sql.Blob.class.getName());
    }

    /**
     * Indicates whether the type of this field is an integer type (Integer or Long)
     * @return true if the type of this field is Integer or Long
     */
    public boolean isInteger() {
        return this.javaType.equals(java.lang.Integer.class.getName()) || this.javaType.equals(java.lang.Long.class.getName());
    }
    
    /**
     * The name of this field in the database
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * The name of this field in the database
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * The type of this field in the database
     * @return the dbType
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * The type of this field in the database
     * @param dbType the dbType to set
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * The comment of this field in the database
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * The comment of this field in the database
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * The name of this field in Java code
     * @return the javaName
     */
    public String getJavaName() {
        return javaName;
    }

    /**
     * The name of this field in Java code
     * @param javaName the javaName to set
     */
    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    /**
     * The type of this field in Java code
     * @return the javaType
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * The type of this field in Java code
     * @param javaType the javaType to set
     */
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    /**
     * indicates whether the value of this field sould be read from the resultset as a String
     * @return true if this field should be read from the resultset as a String
     */
    public boolean isReadAsString() {
        boolean retVal = false;
        
        Class javaClass;
        try {
            javaClass = Class.forName(this.javaType);
        } catch (ClassNotFoundException e) {
            javaClass = Object.class;
        }
        
        if (    javaClass != String.class 
                && javaClass != java.util.Date.class
                && javaClass != java.sql.Timestamp.class
                && javaClass != java.sql.Clob.class
                && javaClass != java.sql.Blob.class
                && !javaClass.isArray()
                && !this.enumerated) {
            retVal = true;
        }
        return retVal;
    }

}
