/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Entity class represents resolution entity with JPA capabilities. It is quoted as width × height,
 * with the units in pixels: for example, "1024 × 768".
 */
@Entity
@Table(name = "resolution",
    uniqueConstraints = @UniqueConstraint(name = "uniqueResolution",
        columnNames = {"height", "width"}))
@NamedQueries({
    @NamedQuery(name = "Resolution.findByWidthAndHeight",
        query = "SELECT res FROM ResolutionEntity AS res WHERE "
            + "res.width = :" + Constants.Query.WIDTH_PARAM + " AND "
            + "res.height = :" + Constants.Query.HEIGHT_PARAM)})
public class ResolutionEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    // @SequenceGenerator(name = GENERATOR_NAME, sequenceName =
    // Schema.RESOLUTION_GENERATOR_TABLE_NAME)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, updatable = false)
    @Min(1)
    private int width;
    @Column(nullable = false, updatable = false)
    @Min(1)
    private int height;


    /**
     * Instantiates a new resolution entity.
     */
    public ResolutionEntity()
    {
        // Default entity constructor is needed.
    }


    /**
     * Instantiates a new resolution JPA by providing resolution values.
     *
     * @param width the resolution width
     * @param height the resolution height
     */
    public ResolutionEntity(final int width, final int height)
    {
        init(width, height);
    }


    /**
     * Instantiates a new resolution JPA by use existing resolution entity.
     *
     * @param resolution the resolution entity
     */
    public ResolutionEntity(final ResolutionEntity resolution)
    {
        if (resolution != null) {
            init(resolution.getWidth(), resolution.getHeight());
        }
    }


    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        if (obj instanceof ResolutionEntity) {
            ResolutionEntity other = (ResolutionEntity)obj;
            return new EqualsBuilder().
                append(getHeight(), other.getHeight()).
                append(getWidth(), other.getWidth()).
                isEquals();
        }

        return super.equals(obj);
    }


    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }


    /**
     * Gets the id of this resolution.
     *
     * @return the identifier
     */
    public int getId()
    {
        return id;
    }


    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }


    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(Constants.INIT_ODD_NUMBER, Constants.MULTI_ODD_NUMBER).
            append(getHeight()).
            append(getWidth()).
            toHashCode();
    }


    /**
     * Sets the height.
     *
     * @param height the new height
     */
    public void setHeight(final int height)
    {
        this.height = height;
    }


    /**
     * Sets the width.
     *
     * @param width the new width
     */
    public void setWidth(final int width)
    {
        this.width = width;
    }


    @Override
    public String toString()
    {
        return "ResolutionEntity[id=" + id + ", width=" + width + ", height=" + height + ']';
    }


    private void init(final int aWidth, final int aHeight)
    {
        width = aWidth;
        height = aHeight;
    }
}
