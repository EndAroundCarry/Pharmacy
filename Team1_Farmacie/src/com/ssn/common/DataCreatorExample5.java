/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package com.ssn.common;

/**
 * @author <a href="mailto:rveina@ssi-schaefer-noell.com">rveina</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class DataCreatorExample5 implements DataCreator {

  @Override
  public void createInitialData(DatabaseOperations dataCreator) {
    dataCreator.addDivisibleDrug("123", "Nurofen", "500mg", 40, 40, 40, 20);
    dataCreator.addIndivisibleDrug("124", "Nurofen", "Sirop", 10, 10, 10);
    dataCreator.addDivisibleDrug("125", "Paracetamol", "500mg", 10, 10, 10, 20);
    dataCreator.addDivisibleDrug("126", "Vitamina C", "500mg", 10, 10, 10, 20);
    dataCreator.addStock("123", "A1", 1);
    dataCreator.addStock("123", "A2", 1);
    dataCreator.addStock("123", "B1", 1);
    dataCreator.addStock("123", "B2", 1);
  }

}
