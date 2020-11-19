package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.TowerType;

public class TowerValueReader {

  private final Map<TowerType, Integer> towerValueMap;

  public TowerValueReader(String propertiesFilename) throws IOException, ConfigurationException {
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
    properties.load(input);
    towerValueMap = new HashMap<>();
    for(Object key : properties.keySet()){
      towerValueMap.put(getTowerType((String) key), Integer.valueOf((String)properties.get(key)));
    }
    PropertyFileValidator towerNameValidator = new PropertyFileValidator(
        propertiesFilename,
        new HashSet<>(Arrays.asList("SingleProjectileShooter", "MultiProjectileShooter",
            "SpreadProjectileShooter", "UnlimitedRangeProjectileShooter",
            "SuperSpeedProjectileShooter",
            "MultiFrozenShooter", "CamoProjectileShooter")));
    if(!towerNameValidator.containsNeededKeys()){
      throw new ConfigurationException("MissingTowerValue");
    }
  }

  public Map<TowerType, Integer> getMap(){
    return towerValueMap;
  }


  // Map property file key to TowerType enum
  public TowerType getTowerType(String key) throws ConfigurationException {
    if(!TowerType.isEnumName(key)){
      throw new ConfigurationException("InvalidTowerName");
    }
    return TowerType.fromString(key);
  }
}
