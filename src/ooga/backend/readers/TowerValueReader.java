/**
 * TowerValueReader should be used to read in the prices of towers
 * @author Annshine
 */
package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.TowerType;

public class TowerValueReader {

  private final Map<TowerType, Integer> towerValueMap;

  /**
   * Constructor of TowerValueReader
   * @param propertiesFilename
   * @throws IOException
   * @throws ConfigurationException
   */
  public TowerValueReader(String propertiesFilename) throws IOException, ConfigurationException {
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
    properties.load(input);
    towerValueMap = new HashMap<>();
    for (Object key : properties.keySet()) {
      towerValueMap.put(getTowerType((String) key), Integer.valueOf((String) properties.get(key)));
    }
    PropertyFileValidator towerNameValidator = new PropertyFileValidator(
        propertiesFilename,
        new HashSet<>(Arrays.asList("SingleProjectileShooter", "MultiProjectileShooter",
            "SpreadProjectileShooter", "UnlimitedRangeProjectileShooter",
            "SuperSpeedProjectileShooter",
            "MultiFrozenShooter", "CamoProjectileShooter")));
    if (!towerNameValidator.containsNeededKeys()) {
      throw new ConfigurationException("MissingTowerValue");
    }
  }

  /**
   *
   * @return the price map of tower types mapped to its price
   */
  public Map<TowerType, Integer> getMap() {
    return towerValueMap;
  }

  /**
   * Return the TowerType from a string
   * @param key
   * @return corresponding TowerType for string
   * @throws ConfigurationException
   */
  public TowerType getTowerType(String key) throws ConfigurationException {
    if (!TowerType.isEnumName(key)) {
      throw new ConfigurationException("InvalidTowerName");
    }
    return TowerType.fromString(key);
  }
}
