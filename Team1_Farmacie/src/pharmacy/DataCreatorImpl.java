/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import com.ssn.common.DataCreator;
import com.ssn.common.DatabaseOperations;

/**
 * @author <a href="mailto:bgantu@ssi-schaefer-noell.com">bgantu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class DataCreatorImpl implements DataCreator {

  @Override
  public void createInitialData(DatabaseOperations dataCreator) {
    dataCreator.addIndivisibleDrug("111", "nurofen", "asd", 3, 3, 3);
    dataCreator.addStock("111", "A1", 3);
    dataCreator.addStock("111", "A2", 3);
  }

}
