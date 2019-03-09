/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package exceptions;

/**
 * @author <a href="mailto:bgantu@ssi-schaefer-noell.com">bgantu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class NoSpaceAvailableInDrawerException extends Exception {

  private static final long serialVersionUID = 1L;

  public NoSpaceAvailableInDrawerException() {
    super("No available space");
  }

}
