package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import ooga.backend.ConfigurationException;
import ooga.backend.towers.TowerType;

public class TowerValueReader {

  private Map<TowerType, Integer> towerValueMap;

  public TowerValueReader(String propertiesFilename) throws IOException {
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
    properties.load(input);
    towerValueMap = new HashMap<>();
    for(Object key : properties.keySet()){
      towerValueMap.put(getTowerType((String) key), Integer.valueOf((String)properties.get(key)));
    }
//    ResourceBundle towerMonkeys = ResourceBundle.getBundle("btd_towers/TowerMonkey");
//    if(!properties.keySet().equals(towerMonkeys.keySet())){
//      throw new ConfigurationException("Missing tower value from properties file");
//    }
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
