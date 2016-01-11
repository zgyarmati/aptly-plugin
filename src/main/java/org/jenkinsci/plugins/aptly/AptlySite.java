package org.jenkinsci.plugins.aptly;

import hudson.FilePath;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents an Aptly site's connection and authentication details
 *
 * @author $Author: zgyarmati <mr.zoltan.gyarmati@gmail.com>
 */
public class AptlySite {

    /** The Constant DEFAULT_HTTP_PORT. */
    private static final int DEFAULT_HTTP_PORT = 80;

    /** The profile name. */
    private String profileName;

    /** The hostname. */
    private String hostname;

    /** The time out. */
    private int timeOut;

    /** The port. */
    private int port;

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
    * @param hostname
    *          the hostname
    * @param port
    *          the port
    * @param timeOut
    *          the time out
    * @param username
    *          the username
    * @param password
    *          the password
    */
    public AptlySite(String profileName, String hostname, int port, int timeOut, String username, String password) {
        this.profileName = profileName;
        this.hostname = hostname;
        this.port = port;
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
    public AptlySite(String hostname, String port, String timeOut, String username, String password) {
        this.hostname = hostname;
        System.console().printf(">>>>>>>>> AptlySite() port: %s\n: ", port);
        try {
            this.port = Integer.parseInt(port);
            this.timeOut = Integer.parseInt(timeOut);
        } catch (Exception e) {
            this.port = DEFAULT_HTTP_PORT;
        }
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
            return hostname;
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
    public String getHostname() {
        return hostname;
    }

    /**
    * Sets the hostname.
    *
    * @param hostname
    *          the new hostname
    */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
    * Gets the port.
    *
    * @return the port
    */
    public String getPort() {
        return  "" + port;
    }

    /**
    * Sets the port.
    *
    * @param port
    *          the new port
    */
    public void setPort(String port) {
        System.console().printf(">>>>>>>>> setPort() port: %s\n: ", port);
        if (port != null) {
            try {
                this.port = Integer.parseInt(port);
            } catch (NumberFormatException e) {
                this.port = DEFAULT_HTTP_PORT;
            }
        } else {
            this.port = DEFAULT_HTTP_PORT;
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
        return hostname;
    }

}
