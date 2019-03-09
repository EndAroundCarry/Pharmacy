/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package exceptions;

/**
 * @author <a href="mailto:mpintea@ssi-schaefer-noell.com">mpintea</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class InsufficientStockException extends Exception {
  private static final long serialVersionUID = 1L;

  public InsufficientStockException() {
    super("Stock insuficient");
  }

}
