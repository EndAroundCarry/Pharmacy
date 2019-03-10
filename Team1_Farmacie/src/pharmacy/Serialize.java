/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exceptions.BarcodeAlreadyExistsException;

/**
 * @author <a href="mailto:bgantu@ssi-schaefer-noell.com">bgantu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class Serialize {

  public void writePharmacy(Pharmacy pharmacy) throws FileNotFoundException, IOException {
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("database.ser")));
    oos.writeObject(pharmacy);
    oos.close();

  }

  public Pharmacy readPharmacy() throws FileNotFoundException, IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("database.ser")));
    Pharmacy pharmacy = (Pharmacy) ois.readObject();
    ois.close();
    return pharmacy;
  }

}
