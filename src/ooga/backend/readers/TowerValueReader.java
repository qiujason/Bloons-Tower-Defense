package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.TowerType;

public class TowerValueReader {

  private Map<TowerType, Integer> towerValueMap;

  public TowerValueReader(String propertiesFilename){
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
    try {
      properties.load(input);
    } catch (IOException e) {
      throw new ConfigurationException("Selected properties file does not exist");
    }
    towerValueMap = new HashMap<>();
    for(Object key : properties.keySet()){
      towerValueMap.put(getTowerType((String) key), Integer.valueOf((String)properties.get(key)));
    }
  }

  public Map<TowerType, Integer> getMap(){
    return towerValueMap;
  }


  // Map property file key to TowerType enum
  public TowerType getTowerType(String key){
    if(!TowerType.isEnumName(key)){
      throw new ConfigurationException("Properties file includes invalid tower type name");
    }
    return TowerType.fromString(key);
  }
}
