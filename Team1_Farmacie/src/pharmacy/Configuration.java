
package pharmacy;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Configuration implements Serializable {
  private static final long serialVersionUID = 1L;
  private int lines;
  private int column;
  private int length;
  private int width;
  private int height;
  private int maxFillPercent;
  private int previousDays;
  private int nextDays;
  private String className;
  private List <String> languages= new ArrayList<>();
 

public List<String> getLanguages() {
	return languages;
}

public Configuration(String fileName) {
    readXml(fileName);
  }

  public int getLines() {
    return lines;
  }

  public int getColumn() {
    return column;
  }

  public int getLength() {
    return length;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getMaxFillPercent() {
    return maxFillPercent;
  }

  public int getPreviousDays() {
    return previousDays;
  }

  public int getNextDays() {
    return nextDays;
  }

  public String getClassName() {
    return className;
  }

  @Override
  public String toString() {
    return "Configuration [lines=" + lines + ", column=" + column + ", length=" + length + ", width=" + width + ", height=" + height + ", maxFillPercent=" + maxFillPercent + ", previousDays=" + previousDays + ", nextDays=" + nextDays + "]";
  }

  public void readXml(String fileName) {
    try {
      File fXmlFile = new File(fileName);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(fXmlFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("config");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) nNode;
          lines = Integer.parseInt(element.getElementsByTagName("maxLines").item(0).getTextContent());
          column = Integer.parseInt(element.getElementsByTagName("maxColumns").item(0).getTextContent());
          length = Integer.parseInt(element.getElementsByTagName("length").item(0).getTextContent());
          width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
          height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());
          maxFillPercent = Integer.parseInt(element.getElementsByTagName("maxOccupancyPercent").item(0).getTextContent());
          previousDays = Integer.parseInt(element.getElementsByTagName("previousDays").item(0).getTextContent());
          nextDays = Integer.parseInt(element.getElementsByTagName("nextDays").item(0).getTextContent());
          className = element.getElementsByTagName("initClassName").item(0).getTextContent();
          String lang = element.getElementsByTagName("languages").item(0).getTextContent();
          String[] splits = lang.split(",");
          for (String s : splits) {
            languages.add(s.trim());
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
