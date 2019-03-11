/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

public class Meniu {

  private static final String BACK = "0";
  private final Keyboard kb = new Keyboard();

  public void run() {
    while (true) {
      String inputLine = kb.readLine();
      if (inputLine.equals(BACK)) {
        return;
      }
      execute(inputLine);
    }

  }

  public void execute(String inputLine) {

    if (inputLine.equals("1")) {
      System.out.println("Adaugare de noi tipuri de medicamente:");
      System.out.println("1.Medicamente care se vand doar la cutie ");
      System.out.println("2.Madicamente care se vand la bucata ");
      System.out.println("0.Back");
      String option = kb.readLine();

      switch (option) {
        case "1":
          System.out.println("1.Medicamente care se vand doar la bucata ");
          medicamenteBucata();

          break;
        case "2":
          System.out.println("2.Medicamente care se vand doar la cutie ");
          medicamenteCutie();
          break;
        case "0":
          showMeniu();
          break;
      }
    }

    if (inputLine.equals("2")) {
      System.out.println("Introducerea de stoc nou de medicamente");
      System.out.println("1.Scaneaza produsul");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          System.out.println("1.Scaneaza produsul");
          break;
        case "0":
          showMeniu();
          break;

      }
    }

    if (inputLine.equals("3")) {
      System.out.println("1.Introduceti numele de barcode");
      System.out.println("2.Introduceti numele medicamentului");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          String barcode = kb.readLine();
          break;
        case "2":
          String numeMedicament = kb.readLine();
          break;
        case "0":
          showMeniu();
          break;

      }

    }

    if (inputLine.equals("4")) {
      System.out.println("1.Introduceti numele brandului");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          String numeBrand = kb.readLine();
          break;
        case "0":
          showMeniu();
          break;
      }

    }

    if (inputLine.equals("5")) {
      System.out.println("1.Introduceti id-ul sertarului");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          String id = kb.readLine();
          break;
        case "0":
          showMeniu();
          break;
      }
    }

    if (inputLine.equals("6")) {
      System.out.println("1.Introduceti tipul medicamentelor");
      System.out.println("2.Introduceti cantitatea dorita");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          String tipMedicament = kb.readLine();
          break;
        case "2":
          String cantitate = kb.readLine();
          break;
        case "0":
          showMeniu();
          break;

      }
    }

    if (inputLine.equals("7")) {
      System.out.println("1.Istoricul ajustarilor de stoc");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          istoricAjustariStoc();
          break;
        case "0":
          showMeniu();
          break;

      }
    }

    if (inputLine.equals("8")) {
      System.out.println("1.Vizualizati starea intregii farmacii");
      System.out.println("2.Vizualizati detalii despre o singura locatie");
      System.out.println("0.Back");
      String option = kb.readLine();
      switch (option) {
        case "1":
          System.out.println("Starea intregii farmacii");
          break;
        case "2":
          System.out.println("Vizualizati detalii despre o singura locatie");
          break;
        case "0":
          showMeniu();
      }
    }

    if (inputLine.equals("9")) {
      System.out.println("1.Setati limba romana");
      System.out.println("2.Setati limba engleza");
      System.out.println("3.Alegeti alta limba");
      System.out.println("0.Back");
      String option = kb.readLine();

      switch (option) {
        case "1":
          System.out.println("Setati limba romana");

          break;
        case "2":
          System.out.println("Setati limba engleza");
          break;
        case "3":
          System.out.println("Alegeti alta limba ");
          break;
        case "0":
          showMeniu();
      }
    }

  }

  private void istoricAjustariStoc() {
    System.out.println("Metoda istoric ajutari stoc");

  }

  private void medicamenteBucata() {
    String medicamenteBucata = readString("Adaugati medicamente care se vand doar la bucata");

  }

  private void medicamenteCutie() {
    String medicamenteBucata = readString("Adaugati medicamente care se vand doar la cutie");

  }

  private String readString(String text) {
    System.out.print(text);
    return kb.readLine();
  }

  public void showMeniu() {
    System.out.println("1.Adaugare de noi tipuri de medicamente in sistem ");
    System.out.println("2.Introducerea de stoc nou de medicament ");
    System.out.println("3.Cautare de medicamente ");
    System.out.println("4.Scoaterea de medicamente ");
    System.out.println("5.Inventar ");
    System.out.println("6.Genereaza comanda stoc ");
    System.out.println("7.Istoric ajustari stoc ");
    System.out.println("8.Vizualizare stoc ");
    System.out.println("9.Setari");

  }
}