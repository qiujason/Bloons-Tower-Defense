/**
 * RoadItemType enum to represent the types of road items
 */
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
    for (RoadItemType type : values()) {
      stringToEnum.put(type.name(), type);
    }
  }

  /**
   * check if a string key is an enum name
   * @param key
   * @return if key is an enum name
   */
  public static boolean isEnumName(String key) {
    for (RoadItemType type : values()) {
      if (key.equals(type.name())) {
        return true;
      }
    }
    return false;
  }

  /**
   * get set of enum keys
   * @return set of enum keys
   */
  public static Set<String> getEnumStrings() {
    return stringToEnum.keySet();
  }

  /**
   * RoadItemType corresponding to a string
   * @param name
   * @return RoadItemType corresponding to the parameter name
   */
  public static RoadItemType fromString(String name) {
    return stringToEnum.get(name);
  }
}
