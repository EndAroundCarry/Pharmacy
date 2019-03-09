///*
// * Copyright (c) 2019 SSI Schaefer Noell GmbH
// *
// * $Header: $
// */
//
//package meniu;
//
//import java.io.IOException;
//
//import pharmacy.Keyboard;
//
//public class SubmeniuChangeLanguage {
//  private final Keyboard kb = new Keyboard();
//  PropertiesLanguages lg = new PropertiesLanguages();
//
//  public static void main(String[] args) throws IOException {
//    new SubmeniuChangeLanguage().run();
//  }
//
//  public void run() throws IOException {
//    loop: while (true) {
//      showMeniuLanguage();
//      String input = kb.readLine();
//      switch (input) {
//        case "1":
//          schimbaLimba();
//          break;
//        case "0":
//          break loop;
//
//      }
//
//    }
//  }
//
//  public void schimbaLimba() {
//    while (true) {
//      showMeniuChangeLanguage();
//
//      String input = kb.readLine();
//      if ("0".equals(input)) {
//        return;
//      }
//
//      try {
//        int languageId = Integer.parseInt(input);
//        Language limba = Language.values()[languageId - 1];
//
//        lg.seteazaLimbaCurenta(limba);
//        String limbaString = "";
//
//        switch (limba) {
//          case RO:
//            limbaString = lg.getMessageByKey("/message/ro");
//            break;
//          case EN:
//            limbaString = lg.getMessageByKey("/message/en");
//            break;
//        }
//      } catch (NumberFormatException exception) {
//        System.out.println(lg.getMessageByKey("/message/AceastaLimbaNuExista"));
//      }
//
//    }
//  }
//
//  public void showMeniuChangeLanguage() {
//
//    for (Language languages : Language.values()) {
//      System.out.println((languages.ordinal() + 1) + "." + lg.getMessageByKey(("/message/") + languages.name().toLowerCase()));
//    }
//    System.out.println("0. " + lg.getMessageByKey("/message/Inapoi"));
//
//  }
//
//  public void showMeniuLanguage() {
//    System.out.println("1. " + lg.getMessageByKey("/message/SchimbaLimba"));
//    System.out.println("0. " + lg.getMessageByKey("/message/Iesire"));
//
//  }
//
//}
