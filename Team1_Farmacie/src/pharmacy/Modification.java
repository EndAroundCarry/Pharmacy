/*
 * Copyright (c) 2019 SSI Schaefer Noell GmbH
 *
 * $Header: $
 */

package pharmacy;

/**
 * @author <a href="mailto:mpintea@ssi-schaefer-noell.com">mpintea</a>
 * @version $Revision: $, $Date: $, $Author: $
 */

public class Modification {
  String data;
  String id;
  String operatie;
  String brand;
  int nrBucati;

  public Modification(String data, String id, String operatie, String brand, int nrBucati) {
    super();
    this.data = data;
    this.id = id;
    this.operatie = operatie;
    this.brand = brand;
    this.nrBucati = nrBucati;
  }

  public String getData() {
    return data;
  }

  public String getId() {
    return id;
  }

  public String getOperatie() {
    return operatie;
  }

  public String getBrand() {
    return brand;
  }

  public int getNrBucati() {
    return nrBucati;
  }

  @Override
  public String toString() {
    if (getNrBucati() == 0) {
      return data + " | " + id + " | " + operatie + " | " + brand;
    } else {
      return data + " | " + id + " | " + operatie + " | " + brand + " | " + nrBucati;
    }
  }

}
