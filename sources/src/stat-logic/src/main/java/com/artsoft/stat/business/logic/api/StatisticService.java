/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.logic.api;


import com.artsoft.stat.business.domain.model.StatisticEntity;


/**
 * The Interface StatisticService.
 */
public interface StatisticService
{

    /**
     * New statistic provided.
     *
     * @param statistic the statistic item
     * @throws InputDataViolationException the input data violation exception
     */
    void newStatistic(final StatisticEntity statistic) throws InputDataViolationException;
}
