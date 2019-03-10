
package pharmacy;

import java.io.Serializable;

public class Box implements Serializable {

  private static final long serialVersionUID = 1L;
  private final int lenght;
  private final int width;
  private final int height;

  public Box(int lenght, int width, int height) throws IllegalArgumentException {
    if (lenght < 0 || width < 0 || height < 0) {
      throw new IllegalArgumentException();
    }
    this.lenght = lenght;
    this.width = width;
    this.height = height;
  }

  public int getVolumeBox() {
    return lenght * width * height;
  }

  @Override
  public String toString() {
    return "Volumul cutiei: " + getVolumeBox();
  }

  public int getLenght() {
	return lenght;
}

public int getWidth() {
	return width;
}

public int getHeight() {
	return height;
}

  
}
