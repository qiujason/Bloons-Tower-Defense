package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
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
  public TowerType getTowerType(String key){ //TODO: use reflection soon
    if(key.equals(TowerType.SingleProjectileShooter.name())){
      return TowerType.SingleProjectileShooter;
    } else if(key.equals(TowerType.MultiProjectileShooter.name())){
      return TowerType.MultiProjectileShooter;
    } else if(key.equals(TowerType.SpreadProjectileShooter.name())){
      return TowerType.SpreadProjectileShooter;
    } else if(key.equals(TowerType.UnlimitedRangeProjectileShooter.name())){
      return TowerType.UnlimitedRangeProjectileShooter;
    }
   throw new ConfigurationException("Properties file includes invalid tower type name");
  }
}
