package ooga.backend.roaditems;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public enum RoadItemType {
  PopBloonsItem,
  SlowBloonsItem,
  ExplodeBloonsItem;

  private static final Map<String, RoadItemType> stringToEnum = new ConcurrentHashMap<>();
  static {
    for (RoadItemType type: values()){
      stringToEnum.put(type.name(), type);
    }
  }

  public static boolean isEnumName(String key){
    for (RoadItemType type: values()){
      if(key.equals(type.name())){
        return true;
      }
    }
    return false;
  }

  public static Set<String> getEnumStrings(){
    return stringToEnum.keySet();
  }

  public static RoadItemType fromString(String name){
    return stringToEnum.get(name);
  }
}
