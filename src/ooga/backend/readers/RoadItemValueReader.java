package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import ooga.backend.ConfigurationException;
import ooga.backend.roaditems.RoadItemType;

public class RoadItemValueReader {

  private Map<RoadItemType, Integer> valueMap;

  public RoadItemValueReader(String propertiesFilename) throws IOException {
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
    properties.load(input);
    valueMap = new HashMap<>();
    for(Object key : properties.keySet()){
      valueMap.put(getRoadItemType((String) key), Integer.valueOf((String)properties.get(key)));
    }
    // add error checking for keys
  }

  public Map<RoadItemType, Integer> getMap(){
    return valueMap;
  }

  public RoadItemType getRoadItemType(String key){
    if(!RoadItemType.isEnumName(key)){
      throw new ConfigurationException("Properties file includes invalid road item type name");
    }
    return RoadItemType.fromString(key);
  }
}
