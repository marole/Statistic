/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.presentation.login;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * The Class UserBean.
 */
@Named("user")
@SessionScoped
public class UserBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;


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
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }


    /**
     * Sets the name.
     *
     * @param newValue the new name
     */
    public void setName(final String newValue)
    {
        name = newValue;
    }


    /**
     * Sets the password.
     *
     * @param newValue the new password
     */
    public void setPassword(final String newValue)
    {
        password = newValue;
    }
}
