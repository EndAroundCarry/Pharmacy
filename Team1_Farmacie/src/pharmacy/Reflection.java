/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ssn.common.DatabaseOperations;

/**
 * @author <a href="mailto:bgantu@ssi-schaefer-noell.com">bgantu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class Reflection {

  public void addDataThroughReflection(DatabaseOperations pharmacy) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
    Class<?> className = Class.forName(((Pharmacy) pharmacy).getConfig().getClassName());
    Object dataCreator = className.newInstance();
    Method[] met = className.getMethods();
    met[0].invoke(dataCreator, pharmacy);

  }

}
