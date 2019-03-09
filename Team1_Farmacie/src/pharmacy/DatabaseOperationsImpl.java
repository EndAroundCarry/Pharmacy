/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import java.util.List;

import com.ssn.common.DatabaseOperations;

/**
 * @author <a href="mailto:aalbu@ssi-schaefer-noell.com">aalbu</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class DatabaseOperationsImpl implements DatabaseOperations {

  private final Pharmacy pharmacy;// new Pharmacy(new Configuration("config.xml"));

  public DatabaseOperationsImpl(Pharmacy pharmacy) {
    this.pharmacy = pharmacy;
  }

  //  private void loadSomeData() throws IllegalArgumentException, BarcodeAlreadyExistsException {
  //    pharmacy.addMedicine("111", "nurofen", "asd", new Box(3, 3, 3), 0, "intreg");
  //    pharmacy.addMedicine("121", "nurofen", "fsgkdghsk", new Box(10, 3, 1), 0, "intreg");
  //    pharmacy.addMedicine("1221", "paracetamol", "asd", new Box(1, 3, 4), 0, "intreg");
  //    pharmacy.addMedicine("22", "algocalmin", "asd", new Box(3, 3, 3), 0, "intreg");
  //    pharmacy.addMedicine("222", "Strepsils", "asd", new Box(3, 3, 3), 0, "intreg");
  //    pharmacy.addToSuggestedDrawer("A1", "111");
  //    pharmacy.addToSuggestedDrawer("A1", "22");
  //    pharmacy.addToSuggestedDrawer("B2", "121");
  //    pharmacy.addToSuggestedDrawer("A3", "1221");
  //    pharmacy.addToSuggestedDrawer("C4", "222");
  //  }

  @Override
  public void addIndivisibleDrug(String barcode, String brand, String details, int width, int height, int length) {
    //  List<Medicine> medicines = pharmacy.getMedicines();
    Box box = new Box(length, width, height);
    pharmacy.getMedicines().add(new EntireBoxMedicine(barcode, brand, details, box));
  }

  @Override
  public void addDivisibleDrug(String barcode, String brand, String details, int width, int height, int length, int numberOfSubdivisions) {
    List<Medicine> medicines = pharmacy.getMedicines();
    Box box = new Box(length, width, height);
    medicines.add(new DivisibleMedicine(barcode, brand, details, box, numberOfSubdivisions));
  }

  @Override
  public void addStock(String barcode, String drawerId, int numberOfBoxes) {
    /* try {
      loadSomeData();
    } catch (IllegalArgumentException | BarcodeAlreadyExistsException e) {
      // TODO Auto-generated catch block
    }*/
    for (int i = 0; i < numberOfBoxes; i++) {
      pharmacy.addToSuggestedDrawer(drawerId, barcode);
    }
  }

}
