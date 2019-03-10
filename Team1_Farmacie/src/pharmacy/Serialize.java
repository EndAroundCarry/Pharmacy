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
    System.out.println(pharmacy);
  }

  public Pharmacy readPharmacy() throws FileNotFoundException, IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("database.ser")));
    Pharmacy pharmacy = (Pharmacy) ois.readObject();
    ois.close();
    System.out.println(pharmacy);
    return pharmacy;
  }

  public static void main(String[] args) throws FileNotFoundException, IOException, IllegalArgumentException, BarcodeAlreadyExistsException, exceptions.IllegalArgumentException {
    Serialize ser = new Serialize();
    Pharmacy pharmacy = new Pharmacy(new Configuration("config.xml"));
    pharmacy.addMedicine("111", "asdsadsa", "adasd", new Box(1, 12, 10), 0, "intreg");
    pharmacy.addMedicine("123", "sd", "sd", new Box(1, 12, 10), 0, "intreg");
    pharmacy.addMedicine("324", "as", "d", new Box(1, 1, 10), 0, "intreg");
    pharmacy.addMedicine("312", "fa", "ad", new Box(1, 1, 5), 0, "intreg");
    pharmacy.addToSuggestedDrawer("A1", "111");
    pharmacy.addToSuggestedDrawer("A1", "123");
    pharmacy.addToSuggestedDrawer("A1", "324");
    pharmacy.addToSuggestedDrawer("A1", "312");
    ser.writePharmacy(pharmacy);

  }
}
