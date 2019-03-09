
package pharmacy;

import java.io.Serializable;

public class EntireBoxMedicine extends Medicine implements Serializable {

  private static final long serialVersionUID = 1L;

  public EntireBoxMedicine(String barcode, String brand, String details, Box box) {
    this.barcode = barcode;
    this.brand = brand;
    this.details = details;
    this.box = box;
  }

  @Override
  public String toString() {
    return "Medicament nedivizabil: barcode:" + barcode + ", brand: " + brand + ", detalii: " + details;
  }

  @Override
  public int getQuantity() {

    return 1;
  }

}
