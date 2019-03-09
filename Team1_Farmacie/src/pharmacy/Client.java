
package pharmacy;

import java.io.IOException;

import com.ssn.common.DataCreatorExample;
import com.ssn.common.DatabaseOperations;

import exceptions.BarcodeAlreadyExistsException;
import exceptions.InsufficientStockException;
import exceptions.NoSpaceAvailableInDrawerException;

public class Client {

  private static final Object EXIT = "0";
  private final Keyboard kb = new Keyboard();
  private final Configuration conf = new Configuration("config.xml");
  private final Pharmacy pharmacy = new Pharmacy(conf);

  public Pharmacy getPharmacy() {
    return pharmacy;
  }

  public static void main(String[] args) throws IllegalArgumentException, BarcodeAlreadyExistsException, InsufficientStockException, IOException {
    Client client = new Client();

    //doar pentru testarea metodelor din DatabaseOperations
    DatabaseOperations db = new DatabaseOperationsImpl(client.getPharmacy());
    DataCreatorExample cr = new DataCreatorExample();
    cr.createInitialData(db);

    client.loadSomeData(); // load some data iti pune niste date in farmacie sa nu mai bagi manual

    //  client.run();

    //metode Bogdan:

    //client.pharmacy.getNumberOfProducts(new EntireBoxMedicine("222", "Strepsils", "asd", new Box(3, 3, 3)), 3);
    // Medicine entireMed = new EntireBoxMedicine("111", "nurofen", "asd", new Box(3, 3, 3));
    /* Medicine divMed = new DivisibleMedicine("111", "algocalmin", "asd", new Box(3, 3, 3), 3);
    Map<String, Integer> suggestions = client.pharmacy.getProductLocationForRequiredMedicine(divMed, 4);
    Medicine entireMed = new EntireBoxMedicine("111", "Nurofen", "asd", new Box(3, 3, 3));
    Medicine divMed = new DivisibleMedicine("22", "algocalmin", "asd", new Box(3, 3, 3), 3);
    Map<String, Integer> suggestions = client.pharmacy.getProductLocationForRequiredMedicine(divMed, 3);
    for (String drawerName : suggestions.keySet()) {
      System.out.println(drawerName + " " + suggestions.get(drawerName));
    }
    client.pharmacy.removeFromDrawer(suggestions, divMed);*/
  }

  private void loadSomeData() throws IllegalArgumentException, BarcodeAlreadyExistsException {
    pharmacy.addMedicine("111", "nurofen", "asd", new Box(3, 3, 3), 0, "intreg");
    pharmacy.addMedicine("121", "nurofen", "fsgkdghsk", new Box(10, 3, 1), 0, "intreg");
    pharmacy.addMedicine("1221", "paracetamol", "asd", new Box(1, 3, 4), 0, "intreg");
    pharmacy.addMedicine("22", "algocalmin", "asd", new Box(3, 3, 3), 3, "divizibil");
    pharmacy.addMedicine("222", "Strepsils", "asd", new Box(3, 3, 3), 0, "intreg");
    pharmacy.addToSuggestedDrawer("A1", "111");
    pharmacy.addToSuggestedDrawer("A1", "111");
    pharmacy.addToSuggestedDrawer("B2", "121");
    pharmacy.addToSuggestedDrawer("C4", "121");
    pharmacy.addToSuggestedDrawer("C5", "121");
    pharmacy.addToSuggestedDrawer("C5", "121");
    pharmacy.addToSuggestedDrawer("B2", "121");
    pharmacy.addToSuggestedDrawer("C4", "121");
    pharmacy.addToSuggestedDrawer("C4", "22");
    pharmacy.addToSuggestedDrawer("C2", "22");
    pharmacy.addToSuggestedDrawer("A2", "22");
    pharmacy.addToSuggestedDrawer("A3", "1221");
    pharmacy.addToSuggestedDrawer("C3", "222");
    pharmacy.addToSuggestedDrawer("C4", "222");
    pharmacy.addToSuggestedDrawer("C3", "222");
    pharmacy.addToSuggestedDrawer("C4", "22");
    pharmacy.addToSuggestedDrawer("C4", "222");
    ////////////////////////////////////////////////////////////////////////////
    pharmacy.test();
  }

  private void run() {
    while (true) {
      showMenu();
      String inputLine = kb.readLine();
      if (inputLine.equals(EXIT)) {
        return;
      }
      execute(inputLine);
    }
  }

  private void execute(String inputLine) {
    if (inputLine.equals("1")) {
      addMedicineInSystem();
    }
    if (inputLine.equals("2")) {
      addInStoc();
    }
    if (inputLine.equals("3")) {
      searchMeds();
    }
    if (inputLine.equals("9")) {
      pharmacy.printCompleteStock();
    }
    if (inputLine.equals("99")) {
      System.out.println("ID sertar: ");
      String drawerName = kb.readLine();
      pharmacy.printSpecificDrawer(drawerName);
    }

  }

  public void searchMeds() {
    System.out.println("Introduceti cheie: ");
    String cheie = kb.readLine();
    System.out.println(pharmacy.searchForMedicine(cheie));
  }

  public void addInStoc() {
    System.out.println("Barcode:");
    String barcode = kb.readLine();
    if (!pharmacy.hasBarcode(barcode)) {
      System.out.println("Nu exista medicament cu acest barcode. Doriti sa introduceti? (Da/Nu)");
      String response = kb.readLine();
      if (response.equalsIgnoreCase("da")) {
        addMedicineInSystem();
      }
    }
    if (pharmacy.hasBarcode(barcode)) {
      try {
        String suggestedDrawer = pharmacy.getRecommendedDrawer(barcode);
        System.out.println("Sertarul potrivit este " + suggestedDrawer + ". Adaugati medicamentul aici? (da/nu)");
        String response = kb.readLine();
        if (response.equalsIgnoreCase("nu")) {
          return;
        }
        if (response.equalsIgnoreCase("da")) {
          pharmacy.addToSuggestedDrawer(suggestedDrawer, barcode);
          System.out.println("Medicamentul cu barcode-ul " + barcode + " a fost adaugat in sertarul " + suggestedDrawer);
        }
      } catch (NoSpaceAvailableInDrawerException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void addMedicineInSystem() {
    System.out.println("Barcode:");
    String barcode = kb.readLine();
    System.out.println("Brand:");
    String brand = kb.readLine();
    System.out.println("Detalii:");
    String details = kb.readLine();
    System.out.println("Tipul(divizibil/intreg):");
    String type = kb.readLine();
    int subdivisions = 0;
    if (type.equalsIgnoreCase("divizibil")) {
      System.out.println("Nr subdiviziuni:");
      subdivisions += kb.readInt();
    }
    System.out.println("Detalii cutie:");
    System.out.println("Lungime: ");
    int lenght = kb.readInt();
    System.out.println("Latime: ");
    int width = kb.readInt();
    System.out.println("Inaltime:");
    int height = kb.readInt();
    try {
      Box box = new Box(lenght, width, height);
      pharmacy.addMedicine(barcode, brand, details, box, subdivisions, type);
      System.out.println("A fost introdus in stoc medicamentul cu barcode: " + barcode);
    } catch (IllegalArgumentException e) {
      System.out.println("Specificatiile au fost introduse gresit");
    } catch (BarcodeAlreadyExistsException e) {
      System.out.println(e.getMessage());
    }
  }

  private void showMenu() {
    System.out.println("1. Adaugare nou tip medicament");
    System.out.println("2. Adaugare medicamente in sertare");
    System.out.println("3. Cautare medicament");
    System.out.println("9. Afisare stoc");
    System.out.println("10.SchimbaLimba");
    System.out.println("0. Iesire");
  }

}
