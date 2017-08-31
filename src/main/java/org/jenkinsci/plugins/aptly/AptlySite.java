/*
 * The MIT License
 *
 * Copyright (c) 2017 Zoltan Gyarmati (https://zgyarmati.de)
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

package org.jenkinsci.plugins.aptly;

import hudson.FilePath;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;
/**
 * This class represents an Aptly site's connection and authentication details
 * @author $Author: zgyarmati <mr.zoltan.gyarmati@gmail.com>
 */
public class AptlySite {

    /** The Constant DEFAULT_HTTP_PORT. */
    private static final int DEFAULT_HTTP_PORT = 80;

    /** The profile name. */
    private String profileName;

    /** The hostname. */
    private String url;

    /** The time out. */
    private int timeOut;

    /** The port. */
    private boolean enableSelfSigned;

    /** The username. */
    private String username;

    /** The password. */
    private String password;


    /**
    * Instantiates a new Aptly site.
    */
    public AptlySite() {

    }

    /**
    * Instantiates a new Aptly site.
    *
    * @param profileName
    *          the profile name
    * @param url
    *          http[s]://hostname:port
    * @param enableSelfSigned
    *          sets whether self-signed SSL cert is allowed
    * @param timeOut
    *          the time out
    * @param username
    *          the username
    * @param password
    *          the password
    */
    @DataBoundConstructor
    public AptlySite(String profileName, String url, boolean enableSelfSigned, int timeOut, String username, String password) {
        this.profileName = profileName;
        this.url = url;
        this.enableSelfSigned = enableSelfSigned;
        this.timeOut = timeOut;
        this.username = username;
        this.password = password;
    }

    /**
    * Instantiates a new Aptly site.
    *
    * @param hostname the hostname (or IP) of the Aptly site
    * @param port     the port
    * @param timeOut  the time out
    * @param username the username
    * @param password the password
    */
    public AptlySite(String url, String enableSelfSigned, String timeOut, String username, String password) {
        this.url = url;
        try {
            this.enableSelfSigned = Boolean.parseBoolean(enableSelfSigned);
        } catch (Exception e) {
            this.enableSelfSigned = false;
        }
        this.timeOut = Integer.parseInt(timeOut);
        this.username = username;
        this.password = password;
    }

    /**
    * Gets the time out.
    *
    * @return the time out
    */
    public int getTimeOut() {
        return timeOut;
    }

    /**
    * Sets the time out.
    *
    * @param timeOut
    *          the new time out
    */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
    * Gets the display name.
    *
    * @return the display name
    */
    public String getDisplayName() {
        if (StringUtils.isEmpty(profileName)) {
            return url;
        } else {
            return profileName;
        }
    }

    /**
    * Gets the profile name.
    *
    * @return the profile name
    */
    public String getProfileName(){
        return profileName;
    }

    /**
    * Sets the profile name.
    *
    * @param profileName
    *          the new profile name
    */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
    * Gets the hostname.
    *
    * @return the hostname
    */
    public String getUrl() {
        return url;
    }

    /**
    * Sets the hostname.
    *
    * @param hostname
    *          the new hostname
    */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
    * Gets whether self signed cert enabled
    *
    * @return the value
    */
    public String getEnableSelfSigned() {
        return  "" + this.enableSelfSigned;
    }

    /**
    * Sets the port.
    *
    * @param port
    *          the new port
    */
    public void setEnableSelfSigned(String enabled) {
        if (enabled != null) {
            try {
                this.enableSelfSigned = Boolean.parseBoolean(enabled);
            } catch (Exception e) {
                this.enableSelfSigned = false;
            }
        } else {
            this.enableSelfSigned = false;
        }

    }


    /**
    * Gets the username.
    *
    * @return the username
    */
    public String getUsername() {
        return username;
    }

    /**
    * Sets the username.
    *
    * @param username
    *          the new username
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
    * Gets the password.
    *
    * @return the password
    */
    public String getPassword() {
        return password;
    }

    /**
    * Sets the password.
    *
    * @param password
    *          the new password
    */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
    * Gets the name.
    *
    * @return the name
    */
    public String getName() {
        return this.profileName;
    }

}
