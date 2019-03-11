
package pharmacy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class Language implements Serializable {

  private static final long serialVersionUID = 1L;
  private final Properties prop = new Properties();
  private final Configuration conf = new Configuration("config.xml");

  public Configuration getConf() {
    return conf;
  }

  public Language()

  {
    try {
      String numeFisier = "message_" + conf.getLanguages().get(0).toLowerCase() + ".txt";
      prop.load(new FileInputStream(numeFisier));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public String getMessage(String key) {
    if (prop.getProperty(key) != null) {
      return prop.getProperty(key);
    }
    return "!" + key;
  }

  public Properties getProp() {
    return prop;
  }

  public String getMessageParam(String key, Object param) {

    String result = getMessage(key);
    result = result.replace("{" + 0 + "}", "" + param);
    return result;
  }

}
