package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertyFileValidator {

  private boolean ifValid;

  public PropertyFileValidator(String propertyFile, Set<String> requiredKeys) {
    ifValid = true;
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertyFile);
    try {
      properties.load(input);
      for (String requiredKey : requiredKeys) {
        if (!properties.containsKey(requiredKey)) {
          ifValid = false;
        }
      }
    } catch (IOException e) {
      ifValid = false;
    }
  }

  public boolean containsNeededKeys() {
    return ifValid;
  }
}
