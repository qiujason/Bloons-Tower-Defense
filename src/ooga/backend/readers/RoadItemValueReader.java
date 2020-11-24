package ooga.backend.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import ooga.backend.ConfigurationException;
import ooga.backend.roaditems.RoadItemType;

public class RoadItemValueReader {

  private final Map<RoadItemType, Integer> valueMap;

  public RoadItemValueReader(String propertiesFilename) throws IOException, ConfigurationException {
    Properties properties = new Properties();
    InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
    properties.load(input);
    valueMap = new HashMap<>();
    for (Object key : properties.keySet()) {
      valueMap.put(getRoadItemType((String) key), Integer.valueOf((String) properties.get(key)));
    }
    if (!RoadItemType.getEnumStrings().equals(properties.keySet())) {
      throw new ConfigurationException("Missing road item value from properties file");
    }
  }

  public Map<RoadItemType, Integer> getMap() {
    return valueMap;
  }

  public RoadItemType getRoadItemType(String key) throws ConfigurationException {
    if (!RoadItemType.isEnumName(key)) {
      throw new ConfigurationException("InvalidDartName");
    }
    return RoadItemType.fromString(key);
  }
}
