package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * This class is used to validate properties files based on a passed in set of required keys
 */

public class PropertyFileValidator {

  private boolean ifValid;

  /**
   * Creates an instance of the class based on a properties file and a Set of required keys
   * @param propertyFile
   * @param requiredKeys
   */
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

  /**
   * Returns true if all necessary keys are present.
   * @return true if all necessary keys are present
   */
  public boolean containsNeededKeys() {
    return ifValid;
  }
}
