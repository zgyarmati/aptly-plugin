package org.jenkinsci.plugins.aptly;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import org.apache.commons.lang.StringUtils;
import java.io.PrintStream;
import java.io.IOException;
import java.io.Console;





/**
 * This class implements a subset of the Aptly REST client calls
 * as documented at
 * http://www.aptly.info/doc/api/
 *
 * @author $Author: zgyarmati <mr.zoltan.gyarmati@gmail.com>
 */
public class AptlyRestClient {


    private String hostname;
    private static int portnum;
    private static int timeout;
    private String username;
    private String password;

    public AptlyRestClient(String hostname, int portnum, int timeout,
                          String username, String password ){
        this.hostname = hostname;
        this.portnum  = portnum;
        this.timeout  = timeout;
        this.username = username;
        this.password = password;
    }

    public String getAptlyServerVersion() throws AptlyRestException {
        String retval = "";
        try {
            // http://localhost:1080/api/version
            GetRequest req = Unirest.get("http://" + hostname + ":" + portnum +
                                         "/api/version");
            req = req.header("accept", "application/json");
            HttpResponse<JsonNode> jsonResponse = req.asJson();

            System.console().printf("Response: " + jsonResponse.getBody().toString() + "\n");
            retval = jsonResponse.getBody().getObject().getString("Version");
            System.console().printf("Version: " + retval + "\n");

        } catch (UnirestException ex) {
            System.console().printf("Failed to get version: %s\n", ex.toString());
            throw new AptlyRestException(ex.toString());
        }
        return retval;
    }


    /**
     * @return the portnum
     */
    public static int getPortnum() {
        return portnum;
    }

    /**
     * @param aPortnum the portnum to set
     */
    public static void setPortnum(int aPortnum) {
        portnum = aPortnum;
    }

    /**
     * @return the timeout
     */
    public static int getTimeout() {
        return timeout;
    }

    /**
     * @param aTimeout the timeout to set
     */
    public static void setTimeout(int aTimeout) {
        timeout = aTimeout;
        Unirest.setTimeouts(timeout * 1000, timeout * 1000);
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }





}
