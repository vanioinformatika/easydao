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
package hu.vanio.easydao.core;

import java.io.Serializable;

/**
 * Generic DAO interface
 * 
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 * 
 * @param <T> Domain object type
 * @param <P> Primary key type
 */
public interface Dao<T extends Model , P extends Serializable> {
    
    /**
     * Reads a domain object with the specified primary key from the datastore 
     * @param pk The primary key
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return The read domain object
     */
    public T read(P pk, boolean readLobFields);
    
    /**
     * Reads all instances of the domain object from the datastore
     * @param readLobFields Specifies whether BLOB/CLOB fields has to be read from the datastore
     * @return All instances of the domain object
     */
    public java.util.List<T> readAll(boolean readLobFields);
    
    /**
     * Creates a new primary key instance on the specified domain object instance
     * @param instance The domain object instance
     */
    public void createPk(T instance);
    
    /**
     * Creates a new domain object in the datastore based on the specified instance
     * @param instance The domain object instance to create
     * @param createPk Indicates whether a new primary key needs to be created
     * @return The re-read updated domain object instance (it needs to be re-read because of the computed fields)
     */
    public T create(T instance, boolean createPk);
    
    /**
     * Updates the specified domain object instance
     * @param instance The domain object instance to update
     * @param updateLobFields Specifies whether BLOB/CLOB fields has to be updated
     * @return The re-read updated domain object instance (it needs to be re-read because of the computed fields)
     */
    public T update(T instance, boolean updateLobFields);
    
    /**
     * Deletes the domain object instance specified with its primary key
     * @param pk The primary key
     */
    public void delete(P pk);
    
}