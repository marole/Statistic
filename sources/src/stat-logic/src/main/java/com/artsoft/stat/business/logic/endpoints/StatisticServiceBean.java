/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.endpoints;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artsoft.stat.business.domain.dao.ResolutionDao;
import com.artsoft.stat.business.domain.dao.StatisticDao;
import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.stat.business.logic.api.InputDataViolationException;
import com.artsoft.stat.business.logic.api.StatisticServiceRemote;


/**
 * Session Bean implementation class StatisticServiceImpl.
 *
 * @author Marcin Olejarczyk
 */
@Stateless
@Remote(value = StatisticServiceRemote.class)
@EJBExceptionHandler
public class StatisticServiceBean implements StatisticServiceRemote
{
    private static final Log logger = LogFactory.getLog(StatisticServiceBean.class);

    @Inject
    private StatisticDao statisticDao;
    @Inject
    private ResolutionDao resolutionDao;
    @Resource
    private Validator validator; // Injection according to JSR-000349 Java EE 7


    @Override
    public void newStatistic(final StatisticEntity statistic) throws InputDataViolationException
    {
        if (logger.isDebugEnabled()) {
            logger.debug("New statistic entity received: " + statistic);
        }

        validateStatIntegrity(statistic);
        storeStatisticEntity(statistic);

        if (logger.isInfoEnabled()) {
            logger.info("New statistic stored: " + statistic);
        }
    }


    private void storeStatisticEntity(final StatisticEntity statistic)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("Start storing statistic entity: " + statistic);
        }

        int width = statistic.getResolution().getWidth();
        int height = statistic.getResolution().getHeight();

        // find resolution entity by use width and height
        ResolutionEntity res = resolutionDao.findByWidthAndHeight(width, height);
        if (res != null) {
            statistic.setResolution(res);
        }
        statisticDao.create(statistic);
        statisticDao.flush();

        logger.debug("Statistic entity stored successfully.");
    }


    /**
     * Validate statistic integrity.
     *
     * @param statistic the statistic object
     * @throws InputDataViolationException if statistic entity object is invalid
     */
    private void validateStatIntegrity(final StatisticEntity statistic) throws InputDataViolationException
    {
        if (logger.isTraceEnabled()) {
            logger.trace("Start validation of statistic entity: " + statistic);
        }

        if (statistic == null) {
            logger.debug("Statistic entity object is null.");
            throw new InputDataViolationException("Statistic entity object is null.");
        }

        Set<ConstraintViolation<StatisticEntity>> constraintViolations = validator.validate(statistic);
        if (!constraintViolations.isEmpty()) {
            // Statistic entity object not validated successfully
            StringBuilder buffer = new StringBuilder("List of constraint violations [");
            for (ConstraintViolation<?> violation : constraintViolations) {
                buffer.append(IOUtils.LINE_SEPARATOR);
                buffer.append("    ");
                buffer.append(violation);
            }
            buffer.append(IOUtils.LINE_SEPARATOR);
            buffer.append("]");

            String message = "Failed validating the Statistic object integrity." + " " + IOUtils.LINE_SEPARATOR
                + buffer;
            logger.info(message);

            throw new InputDataViolationException("Statistic entity is invalid.",
                new ConstraintViolationException(message, new HashSet<ConstraintViolation<?>>(constraintViolations)));
        }

        logger.debug("Statistic entity validated successfully.");
    }
}
