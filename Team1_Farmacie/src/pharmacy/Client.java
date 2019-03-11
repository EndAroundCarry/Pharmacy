
package pharmacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.InsufficientStockException;
import exceptions.NoSpaceAvailableInDrawerException;

public class Client {

  private static final String EXIT = "0";
  private final Keyboard kb = new Keyboard();
  private Configuration conf = null;
  private Pharmacy pharmacy = null;
  private final Language languageProp = new Language();
  private final Serialize serialize = new Serialize();

  public Pharmacy getPharmacy() {
    return pharmacy;
  }

  public static void main(String[] args) throws IllegalArgumentException {
    Client client = new Client();
    client.initConfig();
    // client.loadSomeData(); // load some data iti pune niste date in farmacie sa nu mai bagi manual

    client.run();

  }

  /*private void loadSomeData() throws IllegalArgumentException {
    pharmacy.addMedicine("111", "nurofen", "asd", new Box(3, 3, 3), 0, "intreg");
    pharmacy.addMedicine("121", "nurofen", "fsgkdghsk", new Box(10, 3, 1), 0, "intreg");
    pharmacy.addMedicine("1221", "paracetamol", "asd", new Box(1, 3, 4), 0, "intreg");
    pharmacy.addMedicine("22", "algocalmin", "asd", new Box(3, 3, 3), 3, "divizibil");
    pharmacy.addMedicine("222", "Strepsils", "asd", new Box(3, 3, 3), 0, "intreg");
    pharmacy.addToSuggestedDrawer("A1", "111");
    pharmacy.addToSuggestedDrawer("A4", "111");
    pharmacy.addToSuggestedDrawer("A2", "22");
    pharmacy.addToSuggestedDrawer("C2", "22");
    pharmacy.addToSuggestedDrawer("A1", "111");
    //		pharmacy.addToSuggestedDrawer("A1", "222");
    //		pharmacy.addToSuggestedDrawer("A3", "1221");
    //		pharmacy.addToSuggestedDrawer("B2", "111");
    //		pharmacy.addToSuggestedDrawer("B2", "121");
    //		pharmacy.addToSuggestedDrawer("C3", "222");
    //		pharmacy.addToSuggestedDrawer("C3", "222");
    //		pharmacy.addToSuggestedDrawer("C4", "121");
    //		pharmacy.addToSuggestedDrawer("C4", "121");
    //		pharmacy.addToSuggestedDrawer("C4", "22");
    //		pharmacy.addToSuggestedDrawer("C4", "222");
    //		pharmacy.addToSuggestedDrawer("C4", "22");
  }*/

  public void initConfig() {
    if (!hasDatabaseFile()) {
      System.out.println(languageProp.getMessage("/message/conf") + languageProp.getMessage("/message/daNu"));
      String response = kb.readLine();
      if (response.equalsIgnoreCase(languageProp.getMessage("/message/da"))) {
        conf = new Configuration("config.xml");
        pharmacy = new Pharmacy(conf);
        Reflection ref = new Reflection();
        Pharmacy pharm = pharmacy;
        try {
          try {
            ref.addDataThroughReflection(pharm);
          } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | NullPointerException e) {
            System.out.println("Class not found");
          }
        } catch (NoSuchMethodException e) {
          System.out.println(e);
        }
      } else {
        System.exit(0);
      }
      try {
        serialize.writePharmacy(pharmacy);
      } catch (IOException e) {
        System.out.println("Serialization error");
      }
    } else {
      Serialize ser = new Serialize();
      try {
        pharmacy = ser.readPharmacy();
      } catch (ClassNotFoundException | IOException e) {
        System.out.println("file does not exists");
      }
    }
  }

  //verifica daca exista baza de date, daca nu exista trebuie creat
  private static boolean hasDatabaseFile() {
    return (new File("database.ser")).exists();
  }

  private void run() {
    while (true) {
      showMenu();
      String inputLine = kb.readLine();
      if (inputLine.equals(EXIT)) {
        try {
          serialize.writePharmacy(pharmacy);
        } catch (IOException e) {
          System.out.println("Serializam");
        }
        return;
      }
      execute(inputLine);
    }
  }

  private void showMenu() {
    System.out.println();
    System.out.println("1. " + languageProp.getMessage("/message/AdaugareMedicamenteSistem"));
    System.out.println("2. " + languageProp.getMessage("/message/AdaugareMedicamenteStoc"));
    System.out.println("3. " + languageProp.getMessage("/message/CautareMedicamente"));
    System.out.println("4. " + languageProp.getMessage("/message/ScoatereMedicamente"));
    System.out.println("5. " + languageProp.getMessage("/message/Inventar"));
    System.out.println("6. " + languageProp.getMessage("/message/GenereazaComandaStoc"));
    System.out.println("7. " + languageProp.getMessage("/message/Istoric"));
    System.out.println("8. " + languageProp.getMessage("/message/VizualizareStoc"));
    System.out.println("9. " + languageProp.getMessage("/message/Setari"));
    System.out.println("0. " + languageProp.getMessage("/message/Iesire"));
  }

  private void execute(String inputLine) {

    switch (inputLine) {
      case "1":
        addMedicineInSystem();
        break;
      case "2":
        addInStoc();
        break;
      case "3":
        searchMeds();
        break;
      case "4":
        removeMeds();
        break;
      case "5":
        doInventory();
        break;
      case "6":
        //generare comanda
        break;
      case "7":
        //vizualizare istoric
        break;
      case "8":
        showSubmenuDisplay();
        break;
      case "9":
        showSubmenuLang();
        break;
      default:
        System.out.println("invalid output");
    }
  }

  /**
   * 
   */
  private void doInventory() {
    List<Medicine> medicines = new ArrayList<>();
    System.out.println(languageProp.getMessage("/message/idSertar"));
    Drawer drawer = new Drawer(kb.readLine(), conf);
    int i = 0;
    scanItems(medicines, drawer, i);

  }

  private void scanItems(List<Medicine> medicines, Drawer drawer, int i) {
    String barcode = kb.readLine();
    while (!barcode.equals("stop")) {
      Medicine med;
      med = drawer.getMedicineByBarcode(barcode);
      if (med instanceof DivisibleMedicine) {
        System.out.println(languageProp.getMessage("/message/nrSubdiv"));
        ((DivisibleMedicine) med).setQuantity(kb.readInt());
      }
      medicines.add(med);
      barcode = kb.readLine();
    }
    i++;
    compareInventory(medicines, drawer, i);
  }

  private void compareInventory(List<Medicine> medicines, Drawer drawer, int i) {
    if (i < 2) {
      if (!drawer.isInDrawer(medicines)) {
        scanItems(medicines, drawer, i + 1);
      }
      System.out.println("Inventar corect.");
    }
    pharmacy.changeMedicineInDrawer(medicines, drawer.getName());
  }

  private void showSubmenuLang() {
    System.out.println(languageProp.getMessage("/message/selecteazaLimba"));
    int index = 1;

    for (String language : languageProp.getConf().getLanguages()) {
      System.out.println(index + ". " + language);
      index++;
    }
    System.out.println("0. " + languageProp.getMessage("/message/Inapoi"));
    int languageChoosen = kb.readInt();
    if (languageChoosen == 0) {
      return;
    }
    if (languageChoosen > languageProp.getConf().getLanguages().size()) {
      System.out.println("invalid output");
      return;
    }
    String numeFisier = "message_" + languageProp.getConf().getLanguages().get(languageChoosen - 1).toLowerCase() + ".txt";
    for (String lang : languageProp.getConf().getLanguages()) {
      if (languageChoosen == (languageProp.getConf().getLanguages().indexOf(lang) + 1)) {
        try {
          languageProp.getProp().load(new FileInputStream(numeFisier));
          System.out.println(languageProp.getMessage("/message/confirmareSchimbareLimba") + lang);
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  private void showSubmenuDisplay() {

    System.out.println("1. " + languageProp.getMessage("/message/complet"));
    System.out.println("2. " + languageProp.getMessage("/message/detaliiLocatie"));
    System.out.println("0. " + languageProp.getMessage("/message/Inapoi"));

    String raspuns = kb.readLine();

    if (raspuns.equals("0")) {
      return;
    }

    else if (raspuns.equals("1")) {
      pharmacy.printCompleteStock();
    } else if (raspuns.equals("2")) {
      System.out.println(languageProp.getMessage("/message/idSertar"));
      String drawerName = kb.readLine();
      pharmacy.printSpecificDrawer(drawerName);
    } else {
      System.out.println("invalid output");
    }
  }

  private void removeMeds() {
    System.out.println(languageProp.getMessage("/message/parteDinBrand"));
    String brandSequence = kb.readLine();
    List<Medicine> medsForChoosing = new ArrayList<Medicine>();
    try {
      medsForChoosing = pharmacy.getAllMedicineBySequence(brandSequence);
    } catch (IllegalArgumentException e) {
      System.out.println(languageProp.getMessage("/message/notFound"));
      return;
    }
    for (int i = 1; i <= medsForChoosing.size(); i++) {
      System.out.println(i + ". " + medsForChoosing.get(i - 1));
    }
    System.out.println(languageProp.getMessage("/message/indexMedicament"));
    int numberIndex = kb.readInt();
    if (numberIndex == 0) {
      return;
    }
    Medicine medToRemove = null;
    try {
      medToRemove = pharmacy.getRequestedProduct(numberIndex, medsForChoosing);
    } catch (IllegalArgumentException e1) {
      System.out.println("invalid output");
      return;
    }
    System.out.println(languageProp.getMessage("/message/nrDeScos"));
    int howManyToRemove = kb.readInt();
    try {
      Map<String, Integer> map = pharmacy.getProductLocationForRequiredMedicine(medToRemove, howManyToRemove);
      System.out.println(languageProp.getMessage("/message/putetiScoate"));
      for (Entry<String, Integer> entry : map.entrySet()) {
        System.out.println(languageProp.getMessage("/message/dinSertarul") + entry.getKey() + " --> " + entry.getValue() + " buc.");
      }
      System.out.println(languageProp.getMessage("/message/confirmareScoatere") + languageProp.getMessage("/message/daNu"));
      String response = kb.readLine();
      if (response.equalsIgnoreCase(languageProp.getMessage("/message/da"))) {
        pharmacy.removeFromDrawer(pharmacy.getRequestedQuantity(howManyToRemove, map, medToRemove), medToRemove);
        System.out.println(languageProp.getMessageParam("/message/params3", howManyToRemove));
      } else if (response.equalsIgnoreCase(languageProp.getMessage("/message/nu"))) {
        return;
      } else {
        System.out.println("Invalid input");
        return;
      }
    } catch (InsufficientStockException e) {
      System.out.println(e.getMessage());
    }
  }

  public void searchMeds() {
    System.out.println(languageProp.getMessage("/message/textCautare"));
    String cheie = kb.readLine();
    System.out.println(pharmacy.searchForMedicine(cheie));
  }

  public void addInStoc() {
    System.out.println(languageProp.getMessage("/message/verificaBarcode"));
    String barcode = kb.readLine();
    if (barcode.equals("")) {
      return;
    }
    if (!pharmacy.hasBarcode(barcode)) {
      System.out.println(languageProp.getMessage("/message/medicamentBarcodeInexistent") + languageProp.getMessage("/message/daNu"));
      String response = kb.readLine();
      if (response.equalsIgnoreCase(languageProp.getMessage("/message/da"))) {
        addMedicineInSystem();
      }
    }
    if (pharmacy.hasBarcode(barcode)) {
      try {
        String suggestedDrawer = pharmacy.getRecommendedDrawer(barcode);
        System.out.println(languageProp.getMessageParam("/message/params", suggestedDrawer) + languageProp.getMessage("/message/daNu"));
        String response = kb.readLine();
        if (response.equalsIgnoreCase(languageProp.getMessage("/message/nu"))) {
          return;
        }
        if (response.equalsIgnoreCase(languageProp.getMessage("/message/da"))) {
          pharmacy.addToSuggestedDrawer(suggestedDrawer, barcode);
          System.out.println(languageProp.getMessageParam("/message/params2", suggestedDrawer));
          addInStoc();
        }
      } catch (NoSpaceAvailableInDrawerException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public boolean checkBarcode(String barcode) {
    for (Medicine medicinee : pharmacy.getMedicines()) {
      if (medicinee.getBarcode().equals(barcode)) {
        return true;
      }
    }
    return false;
  }

  public void addMedicineInSystem() {
    String barcode = "";
    while (true) {
      System.out.println("Barcode:");
      barcode = kb.readLine();
      if (!checkBarcode(barcode)) {
        break;
      } else {
        System.out.println(languageProp.getMessage("/message/barcodeQuestion") + languageProp.getMessage("/message/daNu"));
        String response = kb.readLine();
        if (response.equalsIgnoreCase(languageProp.getMessage("/message/nu"))) {
          return;
        }
      }
    }

    System.out.println("Brand:");
    String brand = kb.readLine();
    System.out.println(languageProp.getMessage("/message/detalii"));
    String details = kb.readLine();
    System.out.println(languageProp.getMessage("/message/eDivizibil") + languageProp.getMessage("/message/daNu"));
    String typeRasp = kb.readLine();
    String type = "";
    int subdivisions = 0;
    if (typeRasp.equalsIgnoreCase(languageProp.getMessage("/message/da"))) {
      type += "divizibil";
      System.out.println(languageProp.getMessage("/message/nrSubdiv"));
      subdivisions += kb.readInt();
    } else {
      type += "intreg";
    }
    System.out.println(languageProp.getMessage("/message/detaliiCutie"));
    System.out.println(languageProp.getMessage("/message/lungime"));
    int lenght = kb.readInt();
    System.out.println(languageProp.getMessage("/message/latime"));
    int width = kb.readInt();
    System.out.println(languageProp.getMessage("/message/inaltime"));
    int height = kb.readInt();
    try {
      Box box = new Box(lenght, width, height);
      pharmacy.addMedicine(barcode, brand, details, box, subdivisions, type);
      System.out.println(languageProp.getMessage("/message/confirmareAdaugare") + barcode);
    } catch (IllegalArgumentException e) {
      System.out.println(languageProp.getMessage("/message/respingereAdaugare"));
    }
  }

}
