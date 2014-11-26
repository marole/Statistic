/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.dao;


import com.artsoft.stat.business.domain.model.ResolutionEntity;


/**
 * Specific DAO interface for needs of Resolution model object.
 */
public interface ResolutionDao extends Dao<ResolutionEntity, Integer>
{

    /**
     * Find a resolution entity for given width and height.
     *
     * @param width the width
     * @param height the height
     * @return the resolution entity
     */
    ResolutionEntity findByWidthAndHeight(int width, int height);
}
