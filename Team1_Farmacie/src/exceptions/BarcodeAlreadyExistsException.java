/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package exceptions;

/**
 * @author <a href="mailto:aalbu@ssi-schaefer-noell.com">aalbu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class BarcodeAlreadyExistsException extends Exception {

  private static final long serialVersionUID = 1L;

  public BarcodeAlreadyExistsException(String barcode) {
    super("Medicament existent cu barcode: " + barcode);
  }

}
