/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.dao.jpa;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.business.domain.dao.ResolutionDao;
import com.artsoft.stat.business.domain.model.Constants;
import com.artsoft.stat.business.domain.model.ResolutionEntity;


/**
 * The JPA DAO for need of Resolution object.
 *
 * @author Marcin Olejarczyk
 */
public class ResolutionJpaDao extends AbstractJpaDao<ResolutionEntity, Integer> implements ResolutionDao
{
    private static final Log logger = LogFactory.getLog(ResolutionJpaDao.class);
    private static final String FIND_BY_WIDTH_HEIGHT_QUERY = "Resolution.findByWidthAndHeight";


    @Override
    public ResolutionEntity findByWidthAndHeight(final int width, final int height)
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start searching resolution instance with 'width'x'height': " + width + "x" + height + ".");
        }

        // create named query
        TypedQuery<ResolutionEntity> query = em.createNamedQuery(FIND_BY_WIDTH_HEIGHT_QUERY,
            ResolutionEntity.class);
        try {
            // get resolution entity by use named query
            ResolutionEntity resolution = query.setParameter(Constants.Query.WIDTH_PARAM, width)
                .setParameter(Constants.Query.HEIGHT_PARAM, height).getSingleResult();

            if (logger.isDebugEnabled()) {
                logger.debug("Resolution instance found: " + resolution);
            }

            return resolution;
        }
        catch (NoResultException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Resolution instance not found with 'width'x'height': " + width + "x" + height + ".");
            }
            return null;
        }
    }
}
