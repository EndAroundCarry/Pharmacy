///*
// * Copyright (c) 2019 SSI Schaefer Noell GmbH
// *
// * $Header: $
// */
//
//package meniu;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import exceptions.BarcodeAlreadyExistsException;
//import exceptions.NoSpaceAvailableInDrawerException;
//import pharmacy.Box;
//import pharmacy.Configuration;
//import pharmacy.Keyboard;
//import pharmacy.Pharmacy;
//
//public class MenuMethodsOption {
//  private Keyboard kb = new Keyboard();
//  private final Configuration conf = new Configuration("config.xml");
//  private final Pharmacy pharmacy = new Pharmacy(conf);
//  private Language languages;
//  private PropertiesLanguages propLg = new PropertiesLanguages();
//  private Map<Language, Properties> languagesProperties = new HashMap<>();
//
//  public void addMedicineInSystem() {
//
//    System.out.println(propLg.getMessageByKey("/message/NumeBarcode"));
//    String barcode = kb.readLine();
//    System.out.println(propLg.getMessageByKey("/message/NumeBrand"));
//    String brand = kb.readLine();
//    System.out.println(propLg.getMessageByKey("/message/Detalii"));
//    String detalii = kb.readLine();
//    System.out.println(propLg.getMessageByKey("/message/AlegeTip"));
//    String tip = kb.readLine();
//    int subdiviziuni = 0;
//    if (tip.equalsIgnoreCase(propLg.getMessageByKey("/message/Divizabil"))) {
//      System.out.println(propLg.getMessageByKey("/message/NumarSubdiviziuni"));
//      subdiviziuni += kb.readInt();
//    }
//    System.out.println(propLg.getMessageByKey("/message/DetaliiCutie"));
//    System.out.println(propLg.getMessageByKey("/message/LungimeCutie"));
//    int lungime = kb.readInt();
//    System.out.println(propLg.getMessageByKey("/message/LatimeCutie"));
//    int latime = kb.readInt();
//    System.out.println(propLg.getMessageByKey("/message/InaltimeCutie"));
//    int inaltime = kb.readInt();
//    try {
//
//      Box box = new Box(lungime, latime, inaltime);
//      pharmacy.addMedicine(barcode, brand, detalii, box, subdiviziuni, tip);
//
//      System.out.println(propLg.getMessageByKey("/message/AfostIntrodusBarcode") + barcode.toString());
//      //System.out.println(barcode);
//
//    } catch (IllegalArgumentException e) {
//      System.out.println(propLg.getMessageByKey("/message/SpecificatiiGresit"));
//    } catch (BarcodeAlreadyExistsException e) {
//      System.out.println(e.getMessage());
//    }
//  }
//
//  public void addInStoc() {
//    System.out.println("Scanati produsul introducand barcod-ul");
//    String barcode = kb.readLine();
//    if (!pharmacy.hasBarcode(barcode)) {
//      System.out.println("Nu exista nici un medicament cu acest barcode.Doriti sa introduceti un nou tip de medicament?(Da/Nu");
//      String raspuns = kb.readLine();
//      if (raspuns.equalsIgnoreCase("da")) {
//        addMedicineInSystem();
//      }
//      if (pharmacy.hasBarcode(barcode)) {
//        try {
//          String sugereazaLocatieSertar = pharmacy.getRecommendedDrawer(barcode);
//          System.out.println("Sertarul potrivit este" + sugereazaLocatieSertar + "Adaugati medicamentul aici?(Da/Nu)");
//          String raspuns1 = kb.readLine();
//          if (raspuns1.equalsIgnoreCase("Nu")) {
//            return;
//          }
//
//          if (raspuns1.equalsIgnoreCase("Da")) {
//            pharmacy.addToSuggestedDrawer(sugereazaLocatieSertar, barcode);
//            System.out.println("Medicamentul cu barcode-ul" + barcode + "a fost adaugat in sertarul" + sugereazaLocatieSertar);
//
//          }
//        } catch (NoSpaceAvailableInDrawerException e) {
//          System.out.println(e.getMessage());
//        }
//
//      }
//    }
//  }
//
//  public void searchMeds() {
//    System.out.println("/message/BarcodeSauNumeMedicament");
//    String cheie = kb.readLine();
//    System.out.println(pharmacy.searchForMedicine(cheie));
//  }
//
//  public void seteazaLimba() {
//    System.out.println(propLg.getMessageByKey("/message/SetatiLimbaDorita"));
//    System.out.println("1." + propLg.getMessageByKey("/message/Romana"));
//    System.out.println("2." + propLg.getMessageByKey("/message/Engleza"));
//    int raspunsLimba = kb.readInt();
//    Language limba = Language.values()[raspunsLimba - 1];
//
//    /*   propLg.seteazaLimbaCurenta(limba);
//    propLg.getMessageByKey("/message/ro");*/
//    if (raspunsLimba == 1) {
//      languagesProperties.get(pharmacy);
//
//    }
//  }
//
//  /* public void printCompleteStock() {
//    display = new Display(config, drawers);
//    display.setCellWidth(getMedicineMaxBrandLength());
//    display.createHeader();
//    display.createRows();
//    display.show();
//  }*/
//}
