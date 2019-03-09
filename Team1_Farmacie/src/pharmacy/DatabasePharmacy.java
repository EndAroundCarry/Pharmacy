/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mpintea@ssi-schaefer-noell.com">mpintea</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class DatabasePharmacy {
  protected Map<Integer, Pharmacy> dbPharmacy = new HashMap<>();

  public DatabasePharmacy(int day, Pharmacy pharmacy) {
    dbPharmacy.put(day, pharmacy);
  }
}
