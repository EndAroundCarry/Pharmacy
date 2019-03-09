
package pharmacy;

import java.io.Serializable;

public class DivisibleMedicine extends Medicine implements Serializable {

  private static final long serialVersionUID = 1L;
  private int subdivisions;

  public DivisibleMedicine(String barcode, String brand, String details, Box box, int subdivisions) {
    this.barcode = barcode;
    this.brand = brand;
    this.details = details;
    this.box = box;
    this.subdivisions = subdivisions;
  }

  @Override
  public String toString() {
    return "Medicament divizibil: barcode: " + barcode + ", brand: " + brand + ", detalii: " + details + ", subdiviziuni: " + subdivisions;
  }

  @Override
  public int getQuantity() {

    return subdivisions;
  }

  public void removeDivision() {
    subdivisions--;
  }

  public void setQuantity(int subdiv) {
    subdivisions = subdiv;
  }

}
