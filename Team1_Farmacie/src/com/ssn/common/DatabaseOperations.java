/*
 * Copyright (c) 2016 SSI Schaefer Noell GmbH
 *
 * $Header: /data/cvs/Scolarizare/Team2/Implementation/Farmacie/src/com/ssn/common/DataCreator.java,v 1.4 2017/05/17 09:32:21 cveina Exp $
 */

package com.ssn.common;

/**
 * @author <a href="mailto:ASAS@ssi-schaefer-noell.com">ASAS</a>
 * @version $Revision: 1.4 $, $Date: 2017/05/17 09:32:21 $, $Author: cveina $
 */

public interface DatabaseOperations {

  public void addIndivisibleDrug(String barcode, String brand, String details, int width, int height, int length);

  public void addDivisibleDrug(String barcode, String brand, String details, int width, int height, int length, int numberOfSubdivisions);

  public void addStock(String barcode, String drawerId, int numberOfBoxes);
}
