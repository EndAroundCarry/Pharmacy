/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import java.lang.reflect.Method;

import com.ssn.common.DataCreator;
import com.ssn.common.DatabaseOperations;

/**
 * @author <a href="mailto:bgantu@ssi-schaefer-noell.com">bgantu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class Reflection {

  public static void main(String[] args) throws NoSuchMethodException, SecurityException {
    //    Method met = DataCreator.class.getDeclaredMethod("createInitialData", DataCreator.class);
    //    Method met = Class.forName("DataCreatorImpl").getMethod("createInitialData", DatabaseOperations.class);
    //    DatabaseOperations data = new DatabaseOperationsImpl();
    //    try {
    //      met.invoke(data, new DataCreatorImpl());
    //    } catch (Exception e) {
    //      System.out.println(e.getCause().getMessage());
    //    }
    Method met = DataCreator.class.getMethod("createInitialData", DatabaseOperations.class);
    try {
      DataCreator data = new DataCreatorImpl();
      //  met.invoke(data, new DatabaseOperationsImpl()));
    } catch (Exception e) {
      System.out.println(e.getCause().getMessage());
    }
  }

}
