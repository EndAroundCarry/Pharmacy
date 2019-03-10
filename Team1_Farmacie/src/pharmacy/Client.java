
package pharmacy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ssn.common.DataCreatorExample;
import com.ssn.common.DatabaseOperations;

import exceptions.BarcodeAlreadyExistsException;
import exceptions.InsufficientStockException;
import exceptions.NoSpaceAvailableInDrawerException;

public class Client {

	private static final String EXIT = "0";
	private final Keyboard kb = new Keyboard();
	private final Configuration conf = new Configuration("config.xml");
	private final Pharmacy pharmacy = new Pharmacy(conf);
	private Language languageProp = new Language();

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public static void main(String[] args) throws IllegalArgumentException, BarcodeAlreadyExistsException, InsufficientStockException, IOException {
		Client client = new Client();

		// doar pentru testarea metodelor din DatabaseOperations
	/*	DatabaseOperations db = new DatabaseOperationsImpl(client.getPharmacy());
		DataCreatorExample cr = new DataCreatorExample();
		cr.createInitialData(db);*/

		client.loadSomeData(); // load some data iti pune niste date in farmacie sa nu mai bagi manual
		client.run();
	}

	private void loadSomeData() throws IllegalArgumentException, BarcodeAlreadyExistsException {
		pharmacy.addMedicine("111", "nurofen", "asd", new Box(3, 3, 3), 0, "nu");
		pharmacy.addMedicine("121", "nurofen", "fsgkdghsk", new Box(10, 3, 1), 0, "nu");
		pharmacy.addMedicine("1221", "paracetamol", "asd", new Box(1, 3, 4), 0, "nu");
		pharmacy.addMedicine("22", "algocalmin", "asd", new Box(3, 3, 3), 3, "da");
		pharmacy.addMedicine("222", "Strepsils", "asd", new Box(3, 3, 3), 0, "nu");
		pharmacy.addToSuggestedDrawer("A1", "111");
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
	}
	
	
	//verifica daca exista baza de date, daca nu exista trebuie creat
	/*private static boolean hasDatabaseFile() {
	    return (new File("database.ser")).exists();
	  }*/

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

	// de aici incepe meniul

	private void showMenu() {
		System.out.println();
		System.out.println("1. " + languageProp.getMessage("/message/AdaugareMedicamenteSistem"));
		System.out.println("2. " + languageProp.getMessage("/message/AdaugareMedicamenteStoc"));
		System.out.println("3. " + languageProp.getMessage("/message/CautareMedicamente"));
		System.out.println("4. " + languageProp.getMessage("/message/ScoatereMedicamente"));
//		System.out.println("5. " + languageProp.getMessage("/message/Inventar"));
//		System.out.println("6. " + languageProp.getMessage("/message/GenereazaComandaStoc"));
//		System.out.println("7. " + languageProp.getMessage("/message/Istoric"));
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
		case "4" :
			removeMeds();
			break;
		case "5":
			// inventar;
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

	private void showSubmenuLang() {
		System.out.println("Selectati limba");
		int index = 1;
		for (String language : conf.getLanguages()) {
			System.out.println(index + ". " + language);
			index++;
		}
		int languageChoosen = kb.readInt();
		String numeFisier = "message_" + conf.getLanguages().get(languageChoosen-1).toLowerCase() + ".txt";
		for (String lang : conf.getLanguages()) {
			if(languageChoosen == (conf.getLanguages().indexOf(lang)+1)) {
				try {
					languageProp.getProp().load(new FileInputStream(numeFisier));
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private void showSubmenuDisplay() {
		
		System.out.println("1. Complet");
		System.out.println("2. Detalii locatie");
		
		String raspuns = kb.readLine();
		
		if (raspuns.equals("1")) {
			pharmacy.printCompleteStock();
		}
		else if (raspuns.equals("2")) {
			System.out.println("ID sertar: ");
			String drawerName = kb.readLine();
			pharmacy.printSpecificDrawer(drawerName);
		}
		else {
			System.out.println("invalid output");
		}
	}

	private void removeMeds() {
		System.out.println("Introduceti o parte din numele brandului :");
		String brandSequence = kb.readLine();
		List <Medicine> medsForChoosing = pharmacy.getAllMedicineBySequence(brandSequence);
		for(int i=1;i<=medsForChoosing.size();i++) {
			System.out.println(i+ ". " + medsForChoosing.get(i-1));
		}
		System.out.println("Introduceti indexul medicamentului de scos: (0=abort)");
		int numberIndex = kb.readInt();
		if(numberIndex==0) {
			return;
		}
		Medicine medToRemove = pharmacy.getRequestedProduct(numberIndex, medsForChoosing);
		System.out.println("Introduceti numarul de cutii/subdiviziuni de scos:");
		int howManyToRemove = kb.readInt();
		try {
			Map <String, Integer> map = pharmacy.getProductLocationForRequiredMedicine(medToRemove, howManyToRemove);
			System.out.println("Puteti scoate: ");
			for (Entry<String, Integer> entry : map.entrySet()) {
			      System.out.println("Din sertarul " + entry.getKey() + " " + entry.getValue() + " buc.");
			    }
			System.out.println("Confirmati scoaterea?(da/nu) ");
			String response = kb.readLine();
			switch (response.toLowerCase()) {
			case "da":
				pharmacy.removeFromDrawer(pharmacy.getRequestedQuantity(howManyToRemove, map, medToRemove), medToRemove);
				System.out.println("S-au scos " + howManyToRemove + " buc de " + medToRemove);
				break;
			case "nu":
				return;
			default:
				System.out.println("Invalid input");
				
			}
		} catch (InsufficientStockException e) {
			System.out.println(e.getMessage());
		}
	}

	public void searchMeds() {
		System.out.println("Introduceti cheie: ");
		String cheie = kb.readLine();
		System.out.println(pharmacy.searchForMedicine(cheie));
	}

	public void addInStoc() {
		System.out.println("Verificare barcode:");
		String barcode = kb.readLine();
		if(barcode.equals("")) {
			return;
		}
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
					addInStoc();
				}
			} catch (NoSpaceAvailableInDrawerException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public boolean checkBarcode(String barcode) {	
		for (Medicine medicinee : pharmacy.getMedicines()) {
			if(medicinee.getBarcode().equals(barcode)) {
				return true;
			}
		}
		return false;
	}
	
	public void addMedicineInSystem() {
		String barcode = "";
		while(true) {
			System.out.println("Barcode:");
			barcode = kb.readLine();
			if(!checkBarcode(barcode)) {
				break;
			}else {
				System.out.println("Barcode existent. Introduceti din nou?(da/nu)");
				String response = kb.readLine();
				if(response.equals("nu")) {
					return;
				}
			}
		}
		
		System.out.println("Brand:");
		String brand = kb.readLine();
		System.out.println("Detalii:");
		String details = kb.readLine();
	//	System.out.println("Tipul(divizibil/intreg):");
		System.out.println("Divizibil? (da/nu)");
		String type = kb.readLine();
		int subdivisions = 0;
		if (type.equalsIgnoreCase("da")) {
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
		} 
	}

}
