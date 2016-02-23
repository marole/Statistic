/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.dao.jpa;


import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.business.domain.dao.Dao;
import com.artsoft.stat.business.domain.dao.EntityExistsDaoException;
import com.artsoft.stat.business.domain.dao.TransactionRequiredDaoException;


/**
 * The JPA implementation of persistence T type for needs of DAO.
 *
 * @param <T> the generic type
 * @param <K> the primary key type
 */
abstract class AbstractJpaDao<T, K> implements Dao<T, K>
{
    private static final Log logger = LogFactory.getLog(AbstractJpaDao.class);

    /** The entity manager. */
    @PersistenceContext(name = "default")
    protected EntityManager em;

    /** The T entity class. */
    protected Class<T> entityClass;


    /**
     * Instantiates a new JPA DAO object.
     */
    @SuppressWarnings("unchecked")
    AbstractJpaDao()
    {
        ParameterizedType genericSuperclass = (ParameterizedType)getClass().getGenericSuperclass();
        entityClass = (Class<T>)genericSuperclass.getActualTypeArguments()[0];

        if (logger.isTraceEnabled()) {
            logger.trace("Jpa Dao object initiated with entity class set to " + "'" + entityClass.getName() + "'.");
        }
    }


    @Override
    public T create(final T object)
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start persisting entity object: " + object);
        }

        try {
            // use JPA persist() method
            em.persist(object);

            if (logger.isDebugEnabled()) {
                logger.debug("Entity object persisted: " + object);
            }

            return object;
        }
        catch (EntityExistsException e) {
            throw new EntityExistsDaoException(e);
        }
        catch (TransactionRequiredException e) {
            // wrapping specific exception
            throw new TransactionRequiredDaoException(e);
        }
    }


    @Override
    public void delete(final T object)
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start deleting entity object: " + object);
        }

        // use JPA remove() method
        em.remove(object);

        if (logger.isDebugEnabled()) {
            logger.debug("Entity object deleted: " + object);
        }
    }


    @Override
    public T find(final K id)
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start reading entity object for id = " + "'" + id + "'.");
        }

        // use JPA find() method
        T object = em.find(entityClass, id);

        if (logger.isDebugEnabled()) {
            logger.debug("Entity object read: " + object);
        }

        return object;
    }


    @Override
    public void flush()
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Synchronizing the persistence context to the underlying data source.");
        }

        try {
            // use JPA flush() method
            em.flush();

            if (logger.isDebugEnabled()) {
                logger.debug("Persistence context synchronized.");
            }
        }
        catch (TransactionRequiredException e) {
            throw new TransactionRequiredDaoException(e);
        }
    }


    @Override
    public T update(final T object)
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start updating entity object: " + object);
        }

        try {
            // use JPA merge() method
            T obj = em.merge(object);

            if (logger.isDebugEnabled()) {
                logger.debug("Entity object updated: " + obj);
            }

            return obj;
        }
        catch (TransactionRequiredException e) {
            throw new TransactionRequiredDaoException(e);
        }
    }
}
