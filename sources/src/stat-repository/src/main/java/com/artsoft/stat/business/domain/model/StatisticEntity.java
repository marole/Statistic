/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.business.domain.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Entity class represents statistic entity with JPA capabilities.
 */
@Entity
@Table(name = "statistic")
public class StatisticEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    // @SequenceGenerator(name = GENERATOR_NAME, sequenceName =
    // Schema.STATISTIC_GENERATOR_TABLE_NAME)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String value;
    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @NotNull
    @Valid
    private ResolutionEntity resolution;


    /**
     * Instantiates a new statistic JPA.
     */
    public StatisticEntity()
    {
    }


    /**
     * Instantiates a new statistic JPA by existing statistic object.
     *
     * @param statistic the statistic
     */
    public StatisticEntity(final StatisticEntity statistic)
    {
        if (statistic != null) {
            init(statistic.getName(), statistic.getValue(), statistic.getResolution());
        }
    }


    /**
     * Instantiates a new statistic JPA by providing statistic values.
     *
     * @param name the name
     * @param value the value
     * @param resolution the resolution
     */
    public StatisticEntity(final String name, final String value, final ResolutionEntity resolution)
    {
        init(name, value, resolution);
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

        if (obj instanceof StatisticEntity) {
            StatisticEntity other = (StatisticEntity)obj;
            return new EqualsBuilder().append(getName(), other.getName())
                .append(getResolution(), other.getResolution()).append(getValue(), other.getValue()).isEquals();
        }

        return super.equals(obj);
    }


    /**
     * Gets the id of this statistic.
     *
     * @return the identifier
     */
    public int getId()
    {
        return id;
    }


    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Gets the resolution.
     *
     * @return the resolution
     */
    public ResolutionEntity getResolution()
    {
        return resolution;
    }


    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue()
    {
        return value;
    }


    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(Constants.INIT_ODD_NUMBER, Constants.MULTI_ODD_NUMBER).append(getName())
            .append(getResolution()).append(getValue()).toHashCode();
    }


    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name)
    {
        this.name = name;
    }


    /**
     * Sets the resolution.
     *
     * @param resolution the new resolution
     */
    public void setResolution(final ResolutionEntity resolution)
    {
        this.resolution = resolution;
    }


    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final String value)
    {
        this.value = value;
    }


    @Override
    public String toString()
    {
        return "StatisticEntity[id=" + id + ", name=" + name + ", value=" + value + ", resolution=" + resolution + "]";
    }


    private void init(final String aName, final String aValue, final ResolutionEntity aResolution)
    {
        name = aName;
        value = aValue;
        resolution = aResolution;
    }
}
