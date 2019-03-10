package pharmacy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Language {

	private Properties prop = new Properties();

	public Language() {
		try {
			prop.load(new FileInputStream("message_ro.txt"));
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
