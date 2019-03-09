
package pharmacy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.BarcodeAlreadyExistsException;
import exceptions.InsufficientStockException;
import exceptions.NoSpaceAvailableInDrawerException;

public class Pharmacy implements Serializable {
  private static final long serialVersionUID = 1L;
  private final List<Drawer> drawers = new ArrayList<>();
  private final List<Medicine> medicines = new ArrayList<>();

  private final Configuration config;

  public Pharmacy(Configuration config) {
    this.config = config;
    createDrawers();
  }

  public List<Medicine> getMedicines() {
    return medicines;
  }

  public List<Medicine> getAllMedicineBySequence(String brandSequence) {
    List<Medicine> allMedsBySeq = new ArrayList<>();
    for (Drawer drawer : drawers) {
      if (drawer.getMedicineByBrand(brandSequence) != null) {
        for (Medicine med : drawer.getMedicineByBrand(brandSequence)) {
          if (!allMedsBySeq.contains(med)) {
            allMedsBySeq.add(med);
          }
        }
      }
      //TODO metoda pentru punctul 1-2 de la 5
    }
    return allMedsBySeq;
  }

  public Medicine getRequestedProduct(int chosenMedicine, List<Medicine> allMedsBySeq) {
    if (chosenMedicine > allMedsBySeq.size()) {
      throw new IllegalArgumentException();
    }
    return allMedsBySeq.get(chosenMedicine - 1);
    //TODO metoda pt punctul 2 de la 5
  }

  public Map<String, Integer> getProductLocationForRequiredMedicine(Medicine chosenMedicine, int stockNeeded) throws InsufficientStockException {
    Map<String, Integer> drawersWithChosenMedicine = new TreeMap<String, Integer>();

    if (availableInStock(chosenMedicine, stockNeeded)) {
      for (Drawer drawer : drawers) {
        if (drawer.brandExistsInDrawer(chosenMedicine.getBrand()) && drawer.barcodeExistsInDrawer(chosenMedicine.getBarcode())) {
          drawersWithChosenMedicine.put(drawer.getName(), drawer.getStockQuantity(chosenMedicine));
        }
      }
    } else {
      throw new InsufficientStockException();
    }
    return getRequestedQuantity(stockNeeded, drawersWithChosenMedicine, chosenMedicine);
  }

  public Map<String, Integer> getRequestedQuantity(int stockNeeded, Map<String, Integer> drawersWithChosenMedicine, Medicine chosenMedicine) throws InsufficientStockException {
    Map<String, Integer> drawerWithStockNeeded = new TreeMap<String, Integer>();
    int stock = 0;
    if (availableInStock(chosenMedicine, stockNeeded)) {
      for (String drawerName : drawersWithChosenMedicine.keySet()) {
        int previous = stock;
        stock += drawersWithChosenMedicine.get(drawerName);
        if (stock > stockNeeded) {
          if (previous == 0) {
            drawerWithStockNeeded.put(drawerName, drawersWithChosenMedicine.get(drawerName) - (stock - stockNeeded));
            break;
          }
          drawerWithStockNeeded.put(drawerName, stockNeeded - previous);
          break;
        } else {
          drawerWithStockNeeded.put(drawerName, drawersWithChosenMedicine.get(drawerName));
        }
        if (stock == stockNeeded) {
          break;
        }
      }
    } else {
      throw new InsufficientStockException();
    }
    return drawerWithStockNeeded;
  }

  public void removeFromDrawer(Map<String, Integer> drawerWithStockNeeded, Medicine chosenMedicine) {
    List<String> drawerNames = new ArrayList<>();
    for (String name : drawerWithStockNeeded.keySet()) {
      drawerNames.add(name);
    }

    for (int i = 0; i < drawerNames.size(); i++) {
      Drawer drawer = getDrawerByName(drawerNames.get(i));
      if (chosenMedicine instanceof EntireBoxMedicine) {
        for (int j = 0; j < drawerWithStockNeeded.get(drawerNames.get(i)); j++) {
          drawer.removeMedicine(chosenMedicine);
        }
      } else {
        drawer.removeDivision(chosenMedicine, drawerWithStockNeeded.get(drawerNames.get(i)));
      }
    }
  }

  private boolean availableInStock(Medicine chosenMedicine, int stockNeeded) {
    int inStock = 0;
    for (Drawer drawer : drawers) {
      inStock += drawer.getStockQuantity(chosenMedicine);
    }
    if (inStock >= stockNeeded) {
      return true;
    }
    return false;
    //TODO method may be usefull for point 3.a
  }

  public void addMedicine(String barcode, String brand, String details, Box box, int subdivisions, String type) throws IllegalArgumentException, BarcodeAlreadyExistsException {
    if (getMedicineByBarcode(barcode) != null) {
      throw new BarcodeAlreadyExistsException(barcode);
    }
    if (subdivisions < 0) {
      throw new IllegalArgumentException();
    }
    if (type.equalsIgnoreCase("divizibil")) {
      medicines.add(new DivisibleMedicine(barcode, brand, details, box, subdivisions));
    } else if (type.equalsIgnoreCase("intreg")) {
      medicines.add(new EntireBoxMedicine(barcode, brand, details, box));
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void showMedicines() {
    for (Medicine medicine : medicines) {
      System.out.println(medicine);
    }
  }

  public Medicine getMedicineByBarcode(String barcode) {
    for (Medicine medicine : medicines) {
      if (medicine.getBarcode().equals(barcode)) {
        return medicine;
      }
    }
    return null;
  }

  /**
   * 
   * @param barcode
   * @return String or null if no medicine with the given barcode is found
   * @throws NoSpaceAvailableInDrawerException
   */
  public String getRecommendedDrawer(String barcode) throws NoSpaceAvailableInDrawerException {
    Medicine medicine = getMedicineByBarcode(barcode);
    String brand = medicine.getBrand();
    if (!isSpaceAvailable(medicine)) {
      throw new NoSpaceAvailableInDrawerException();
    }
    return getDrawerWithSameBrandOrEmpty(brand);
  }

  public void addToSuggestedDrawer(String name, String barcode) {
    Medicine medicine = getMedicineByBarcode(barcode);
    for (Drawer drawer : drawers) {
      if (drawer.getName().equals(name)) {
        drawer.addMedicineToList(medicine);
      }
    }
  }

  public boolean checkIfDivisible(Medicine med) {
    if (med instanceof DivisibleMedicine) {
      return true;
    } else {
      return false;
    }
  }

  private boolean isSpaceAvailable(Medicine medicine) {
    for (Drawer drawer : drawers) {
      if (drawer.getLoad() == -1) {
        continue;
      } else {
        drawer.addMedicineToList(medicine);
        if (drawer.getLoad() == -1) {
          drawer.removeLastMedicine();
        } else {
          drawer.removeLastMedicine();
          return true;
        }
      }
    }
    return false;
  }

  private String getDrawerWithSameBrandOrEmpty(String brand) {
    for (Drawer drawerWithBrand : drawers) {
      if (drawerWithBrand.brandExistsInDrawer(brand)) {
        return drawerWithBrand.getName();
      }
    }
    return getEmptyDrawerOrLeastBrands();
  }

  private String getEmptyDrawerOrLeastBrands() {
    for (Drawer emptyDrawer : drawers) {
      if (emptyDrawer.isEmpty()) {
        return emptyDrawer.getName();
      }
    }
    Collections.sort(drawers);
    return drawers.get(0).getName();

  }

  public void createDrawers() {
    for (char letter = 'A'; letter < 'A' + config.getLines(); letter++) {
      for (int j = 0; j < config.getColumn(); j++) {
        String name = "" + letter + (j + 1);
        drawers.add(new Drawer(name, config));
      }
    }
  }

  public boolean hasBarcode(String barcode) {
    for (Medicine medicine : medicines) {
      if (medicine.barcode.equals(barcode)) {
        return true;
      }
    }
    return false;
  }

  public String searchForMedicine(String barcodeOrBrand) {
    String result = "";
    boolean found = false;
    for (Drawer drawer : drawers) {
      for (Medicine medicine : drawer.getMedicinesInDrawer()) {
        if (medicine.getBarcode().toLowerCase().contains(barcodeOrBrand.toLowerCase()) || //
          medicine.getBrand().toLowerCase().contains(barcodeOrBrand.toLowerCase())) {
          result += medicine + " /message/NuEsteMedicamentul " + drawer.getName() + System.lineSeparator();
          found = true;
        }
      }
    }
    if (!found) {
      return "/message/NuEsteMedicamentul"; //TODO need to find a better solution for the ciobaneala
    }
    return result;
  }

  //used for unit testing use with care if necessary
  //author Bogdan the great
  public Drawer getDrawerByName(String name) {
    for (Drawer drawer : drawers) {
      if (drawer.getName().equals(name)) {
        return drawer;
      }
    }
    return null;
  }

  public void printCompleteStock() {
    Display display = new Display(config, drawers);
    display.setCellWidth(getMedicineMaxBrandLength());
    display.createHeader();
    display.createRows();
    display.show();
  }

  public int getMedicineMaxBrandLength() {
    int maxLength = 0;
    for (Medicine medicine : medicines) {
      int length = medicine.getBrand().length();
      if (length > maxLength) {
        maxLength = length;
      }
    }
    return maxLength;
  }

  public void printSpecificDrawer(String drawerName) {
    Map<Medicine, Integer> medsCounter = new HashMap<>();
    boolean found = false;
    for (Drawer drawer : drawers) {
      if (drawer.getName().equalsIgnoreCase(drawerName)) {
        found = true;
        if (drawer.getMedicinesInDrawer().size() == 0) {
          System.out.println("Sertar gol");
          break;
        }
        for (Medicine meds : drawer.getMedicinesInDrawer()) {
          medsCounter.put(meds, drawer.getNumberOfSameMeds(meds));
        }
      }
    }
    if (found == false) {
      System.out.println("Sertarul nu exista");
    }
    for (Map.Entry<Medicine, Integer> entry : medsCounter.entrySet()) {
      System.out.println(entry.getKey() + ". Numar de cutii : " + entry.getValue());
    }
  }

  //Nu stiu exact daca e nevoie de metoda, dar la inceput aveam impresia ca voi avea nevoie de ea
  public void emptyDrawer(String drawerId) {
    Drawer drawer = getDrawerByName(drawerId);
    drawer.clearDrawer();
  }

  //Verific daca am scanat totul din sertar
  public boolean drawerIsEmpty() {
    // Keyboard kb = new Keyboard();

    //System.out.println("Este gol sertarul? (Da/Nu)");
    // String response = kb.readLine();

    // if (response.equalsIgnoreCase("da")) {
    return true;
    // }

    // return false;
  }

  //Aici ajung daca am introdus de 2 ori valori diferite fata de ceea ce aveam
  public void changeMedicineInDrawer(List<Medicine> medicine, String drawerId) {
    System.out.println("Setati acestea ca noile valori? (Da/Nu)");
    //if (response.equalsIgnoreCase("da")) {
    Drawer drawer = getDrawerByName(drawerId);
    drawer.updateMedicines(medicine);
  }
  //}

  //Metoda de inventar in sine
  public String doInventory(String drawerId) {
    Drawer drawer = getDrawerByName(drawerId);
    List<Medicine> medicine = drawer.scanDrawer(); // Aici stochez ceea ce scanez

    if (drawerIsEmpty()) {
      if (!drawer.valuesAreCorrect(medicine)) { // Daca valorile nu sunt egale, mai verific o data
        medicine = drawer.scanDrawer();

        if (!drawer.valuesAreCorrect(medicine)) {
          changeMedicineInDrawer(medicine, drawerId); // Verific daca doresc ca valorile introduse sa fie noile valori din sertar
          return "Schimbat";
        }
      }
    }
    return "Identic";
  }

  public void test() {
    String result = doInventory("A1");
    System.out.println(result);
  }
}
