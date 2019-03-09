
package pharmacy;

import java.io.Serializable;

public abstract class Medicine implements Serializable, Cloneable {

  private static final long serialVersionUID = 1L;
  protected String barcode;
  protected String brand;
  protected String details;
  protected Box box;

  public String getBarcode() {
    return barcode;
  }

  public String getDetails() {
    return details;
  }

  public String getBrand() {
    return brand.toLowerCase();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Medicine other = (Medicine) obj;
    if (barcode == null) {
      if (other.barcode != null)
        return false;
    } else if (!barcode.equals(other.barcode))
      return false;
    return true;
  }

  public Box getBoxDetails() {
    return box;
  }

  abstract public int getQuantity();
}
