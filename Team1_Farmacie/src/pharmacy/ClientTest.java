/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.BarcodeAlreadyExistsException;
import exceptions.NoSpaceAvailableInDrawerException;

/**
 * @author <a href="mailto:rmisara@ssi-schaefer-noell.com">rmisara</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class ClientTest {
  Client client = new Client();
  private final Configuration conf = new Configuration("config.xml");
  Pharmacy pharmacy = null;

  @Before
  public void setup() {
    pharmacy = new Pharmacy(conf);
    pharmacy.createDrawers();
  }

  @Test(expected = NoSpaceAvailableInDrawerException.class)
  public void testBoxVolumeTooBig() throws IllegalArgumentException, BarcodeAlreadyExistsException, NoSpaceAvailableInDrawerException {
    pharmacy.addMedicine("111", "asd", "asd", new Box(70, 60, 30), 0, "intreg");
    pharmacy.getRecommendedDrawer("111");
  }

  /* @Test
  public void testRightDrawer() throws IllegalArgumentException, BarcodeAlreadyExistsException {
    pharmacy.addMedicine("111", "asd", "asd", new Box(30, 30, 30), 0, "intreg");
    pharmacy.addToSuggestedDrawer("A1", "111");
    assertEquals("Sertar: A1 EntireBoxMedicine [barcode=111, brand=asd, details=asd, Volumul cutiei: 27000]", (pharmacy.getDrawerByName("A1").showMed(new EntireBoxMedicine("111", "asd", "asd", new Box(30, 30, 30)), "A1")));
  }*/

  @Test
  public void barcodeDoesntExist() throws IllegalArgumentException, BarcodeAlreadyExistsException {
    pharmacy.addMedicine("111", "asd", "asd", new Box(30, 30, 30), 0, "intreg");
    assertEquals(null, pharmacy.getMedicineByBarcode("112111"));

  }

  @Test
  public void barcodeExists() throws IllegalArgumentException, BarcodeAlreadyExistsException {
    pharmacy.addMedicine("111", "asd", "asd", new Box(30, 30, 30), 0, "intreg");
    assertEquals(new EntireBoxMedicine("111", "asd", "asd", new Box(30, 30, 30)), pharmacy.getMedicineByBarcode("111"));

  }

}
