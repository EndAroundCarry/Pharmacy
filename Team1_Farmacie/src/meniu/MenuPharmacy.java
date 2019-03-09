//
//package meniu;
//
//import pharmacy.Configuration;
//import pharmacy.Keyboard;
//import pharmacy.Pharmacy;
//
//public class MenuPharmacy {
//  private static final String EXIT = "0";
//  private final Keyboard kb = new Keyboard();
//  private final Configuration conf = new Configuration("config.xml");
//  private final Pharmacy pharmacy = new Pharmacy(conf);
//  private MenuMethodsOption sbm = new MenuMethodsOption();
//  private PropertiesLanguages prop = new PropertiesLanguages();
//
//  public Pharmacy getPharmagy() {
//    return pharmacy;
//  }
//
//  private void run() {
//    while (true) {
//      String inputLine = kb.readLine();
//      if (inputLine.equals(EXIT)) {
//        return;
//      }
//      execute(inputLine);
//    }
//  }
//
//  private void execute(String inputLine) {
//    int choice = kb.readInt();
//    switch (choice) {
//      case 1:
//        sbm.addMedicineInSystem();
//        break;
//      case 2:
//        sbm.addInStoc();
//        break;
//      case 3:
//        sbm.searchMeds();
//        break;
//
//    }
//    /*  case 9:   sbm. */
//
//    /*  if (inputLine.equals("9")) {
//        pharmacy.printCompleteStock();
//      }*/
//    /*
//    if (inputLine.equals("8")) {
//      pharmacy.printCompleteStock();
//    }
//    
//    if (inputLine.equals("9")) {
//    
//      //prop.getMessageByKey("/message/ro");
//    
//    }*/
//
//  }
//
//  public void showMenu() {
//    System.out.println("1." + prop.getMessageByKey("/message/AdaugareMedicamenteSistem"));
//    System.out.println("2.Adaugare de medicamente in stoc ");
//    System.out.println("3.Cautare de medicamente");
//    System.out.println("4.Scoatearea de medicamente");
//    System.out.println("5.Inventar");
//    System.out.println("6.Genereaza comanda stoc");
//    System.out.println("7.Istoric ajustari stoc");
//    System.out.println("8.Vizualizare stoc");
//    System.out.println("9.Setari");
//
//  }
//
//  public static void main(String[] args) {
//
//    MenuPharmacy men = new MenuPharmacy();
//    men.showMenu();
//    men.run();
//  }
//}
