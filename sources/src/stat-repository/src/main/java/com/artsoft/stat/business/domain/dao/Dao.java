/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.dao;


/**
 * This interface enable typical persistence operations of the T type. It contains common operations
 * like persist, read, update, etc.
 *
 * @param <T> the generic type
 * @param <K> the primary key type
 */
public interface Dao<T, K>
{
    /**
     * Create an new entity object by persisting it in data source.
     *
     * @param object instance to persist
     * @return the persisted object
     *
     * @throws EntityExistsDaoException if the entity already exists in data source
     * @throws TransactionRequiredDaoException if there is no transaction but it is required
     * @throws IllegalArgumentException if the instance is not an entity
     */
    T create(T object) throws EntityExistsDaoException, TransactionRequiredDaoException, IllegalArgumentException;


    /**
     * Delete the entity object by remove it form data source.
     *
     * @param object the object to remove
     *
     * @throws TransactionRequiredDaoException if there is no transaction but it is required
     * @throws IllegalArgumentException if the instance is not an entity or is a removed entity
     */
    void delete(T object) throws TransactionRequiredDaoException, IllegalArgumentException;


    /**
     * Find an persisted object in the data source by primary key.
     *
     * @param id the primary key
     * @return the persisted object for given id
     */
    T find(K id);


    /**
     * Force synchronize all entity objects state with the underlying data source.
     */
    void flush();


    /**
     * Update the state of the entity object into the data source.
     *
     * @param object entity object to update
     * @return the updated persistence object
     *
     * @throws TransactionRequiredDaoException if there is no transaction but it is required
     * @throws IllegalArgumentException if the instance is not an entity or is a removed entity
     */
    T update(T object) throws TransactionRequiredDaoException, IllegalArgumentException;
}
