package org.jenkinsci.plugins.aptly;

public class AptlyRestException extends Exception {
  public AptlyRestException() { super(); }
  public AptlyRestException(String message) { super(message); }
  public AptlyRestException(String message, Throwable cause) { super(message, cause); }
  public AptlyRestException(Throwable cause) { super(cause); }
}
