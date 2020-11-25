/**
 * Should be used to validate property files when passed in a property file director and required keys
 * @author Annshine
 */
package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertyFileValidator {

  private boolean ifValid;

  /**
   * Constructor of PropertyFileValidator
   * @param propertyFile directory of property file
   * @param requiredKeys required keys to check
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
   * Used to check if a property file is valid
   * @return if the property file contains all required keys
   */
  public boolean containsNeededKeys() {
    return ifValid;
  }
}
