
package pharmacy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Drawer implements Comparable<Drawer>, Serializable, Cloneable {

  private static final long serialVersionUID = 1L;
  private final String name;
  private final List<Medicine> medicinesInDrawer = new ArrayList<>();

  public List<Medicine> getMedicinesInDrawer() {
    return medicinesInDrawer;
  }

  private final Configuration config;
//
  public Drawer(String name, Configuration config) {
    this.name = name;
    this.config = config;
  }

  public boolean containsMedicines(Medicine medicine) {
    if (medicinesInDrawer.contains(medicine)) {
      return true;
    }
    return false;
  }

  public List<Medicine> getMedicineByBrand(String brand) {
    List<Medicine> brandMeds = new ArrayList<>();
    for (Medicine medicine : medicinesInDrawer) {
      if (medicine.getBrand().contains(brand)) {
        brandMeds.add(medicine);
      }
    }
    return brandMeds;
  }

  public void addMedicineToList(Medicine medicine) {
    medicinesInDrawer.add(medicine);
  }

  private double getDrawerVolume() {
    return config.getHeight() * config.getLength() * config.getWidth();
  }

  public double getLoad() {
    double boxesVolume = 0;
    for (Medicine med : medicinesInDrawer) {
      Box boxSize = med.getBoxDetails();
      boxesVolume += boxSize.getVolumeBox();
    }
    double drawerVolume = getDrawerVolume();
    double maxVolume = (double) config.getMaxFillPercent() / 100 * drawerVolume;
    if (boxesVolume > maxVolume) {
      return -1;
    }
    return (boxesVolume / drawerVolume) * 100;

  }

  public boolean barcodeExistsInDrawer(String barcode) {
    for (Medicine med : medicinesInDrawer) {
      if (med.getBarcode().equals(barcode)) {
        return true;
      }
    }
    return false;
  }

  public boolean brandExistsInDrawer(String brand) {
    for (Medicine med : medicinesInDrawer) {
      if (med.getBrand().equals(brand)) {
        return true;
      }
    }
    return false;
  }

  public boolean isEmpty() {
    if (medicinesInDrawer.size() == 0) {
      return true;
    }
    return false;
  }

  public int getBrandNumber() {
    Set<String> brands = new HashSet<>();
    for (Medicine med : medicinesInDrawer) {
      brands.add(med.getBrand());
    }
    return brands.size();
  }

  public int getStockQuantity(Medicine medicine) {
    int medicineInDrawer = 0;
    for (Medicine med : medicinesInDrawer) {
      if (med.getBrand().equals(medicine.getBrand())) {
        medicineInDrawer += med.getQuantity();
      }
    }
    return medicineInDrawer;
  }

  @Override
  public int compareTo(Drawer drawer) {
    if (this.getBrandNumber() > drawer.getBrandNumber()) {
      return 1;
    }
    if (this.getBrandNumber() < drawer.getBrandNumber()) {
      return -1;
    } else {
      return 0;
    }
  }

  public String getName() {
    return name;
  }

  public void removeLastMedicine() {
    medicinesInDrawer.remove(medicinesInDrawer.size() - 1);
  }

  public String getMedsFromDrawers() {
    for (Medicine medicine : medicinesInDrawer) {
      return medicine.getBrand();
    }
    return null;
  }

  public int getNumberOfSameMeds(Medicine medicine) {
    return Collections.frequency(medicinesInDrawer, medicine);
  }

  public Map createMap() {
    Map<Medicine, Integer> medsCounter = new HashMap<>();
    for (Medicine medicine : medicinesInDrawer) {
      medsCounter.put(medicine, getNumberOfSameMeds(medicine));
    }
    return medsCounter;
  }

  public void removeMedicine(Medicine medicine) {
    Iterator<Medicine> iterator = medicinesInDrawer.iterator();
    while (iterator.hasNext()) {
      if (medicine.equals((iterator.next()))) {
        iterator.remove();
        break;
      }
    }
  }

  public void removeDivision(Medicine medicine, int divisons) {
    for (Medicine med : medicinesInDrawer) {
      if (medicine.equals(med)) {
        for (int i = 0; i < divisons; i++) {
        	if(((DivisibleMedicine)med).getQuantity()==0){
        		break;
        	}
          ((DivisibleMedicine) med).removeDivision();
        }
      }
    }
    Iterator<Medicine> iterator = medicinesInDrawer.iterator();
    while (iterator.hasNext()) {
      Medicine medicineToBeRemoved = (Medicine) iterator.next();
      if ((medicine.equals(medicineToBeRemoved) && medicineToBeRemoved.getQuantity() == 0)) {
        iterator.remove();
        break;
      }
    }
  }

  //Returnez medicamentele scanate
  public List<Medicine> scanDrawer() {
    List<Medicine> medicines = new ArrayList<>();
    Scanner in = new Scanner(System.in);
    Medicine clona = null;
    for (Medicine med : medicinesInDrawer) {
      if (med instanceof DivisibleMedicine) {
        clona = new DivisibleMedicine(med.getBarcode(), med.getBrand(), med.getDetails(), med.getBoxDetails(), med.getQuantity());
        System.out.print("Cantitate subdiviziuni: ");
        ((DivisibleMedicine) clona).setQuantity(Integer.parseInt(in.next())); // Aveam nevoie de un set pt a da nr de subdiviziuni
        // BTW am fost nevoit sa scot final de la nrSubdiviziuni din DivizibleMedicine
        medicines.add(clona);
      } else {
        medicines.add(med);
      }
    }
    //in.close();
    ///////////////////////////aparent nu merge corect apelarea a doua daca inchid citirea la prima apelare
    return medicines;
  }

  //Verific corectitudinea valorilor date de mine
  public boolean valuesAreCorrect(List<Medicine> medicines) {
    for (int i = 0; i < medicinesInDrawer.size(); i++) {

      if (medicines.get(i) instanceof DivisibleMedicine && //
        medicinesInDrawer.get(i) instanceof DivisibleMedicine) {

        if (medicines.get(i).getQuantity() != medicinesInDrawer.get(i).getQuantity()) {
          return false;
        }
      }
    }

    return true;
  }

  //Golesc lista in caz ca vreau sa schimb valorile pe care le am
  public void clearDrawer() {
    medicinesInDrawer.clear();
  }

  public void updateMedicines(List<Medicine> medicines) {
    clearDrawer();
    medicinesInDrawer.addAll(medicines); // Schimbarea propriu zisa
  }

}