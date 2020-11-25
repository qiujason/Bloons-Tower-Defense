package ooga.backend.bloons.types;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.AlertHandler;
import ooga.backend.ConfigurationException;

/**
 * Class that reads in bloon types and builds a doubly linked list style chain that allows
 * for bloon types to easily get its next and previous bloon type
 *
 * @author Jason Qiu
 */
public class BloonsTypeChain {

  private static final ResourceBundle ERROR_RESOURCES = ResourceBundle.getBundle("ErrorResource");

  /**
   * private class that represents a bloon type node in the doubly linked list chain
   */
  private static class BloonsTypeNode {

    private final BloonsType type;
    private BloonsTypeNode prev;
    private BloonsTypeNode next;
    private int numNext;

    /**
     * creates a bloonstype node
     * @param type bloonstype of the node
     */
    private BloonsTypeNode(BloonsType type) {
      this.type = type;
    }

    /**
     * returns the bloonstype of the node
     * @return bloonstype of node
     */
    private BloonsType getType() {
      return type;
    }

  }

  private final Map<String, BloonsTypeNode> bloonsTypeBloonMap;
  private final BloonsTypeNode head;

  /**
   * creates a bloons type chain
   * @param propertyFilePath String containing the filepath leading to the bloons type property file
   * @throws ConfigurationException if there are errors in the bloons type property file
   */
  public BloonsTypeChain(String propertyFilePath) throws ConfigurationException {
    bloonsTypeBloonMap = new HashMap<>();
    ResourceBundle bundle = ResourceBundle.getBundle(propertyFilePath);
    String[] bloonTypes = bundle.getString("Order").split(",");

    if (bloonTypes.length <= 0) {
      throw new ConfigurationException(ERROR_RESOURCES.getString("NoOrderFoundInBloonsType"));
    }

    if (bloonTypes[0].equals("DEAD")) {
      head = new BloonsTypeNode(new BloonsType(this, "DEAD", 0, 0, Specials.None));
      bloonsTypeBloonMap.put("DEAD", head);
    } else {
      throw new ConfigurationException(ERROR_RESOURCES.getString("DeadNotFirstBloon"));
    }
    readPropertyFile(bundle, bloonTypes);
  }

  private void readPropertyFile(ResourceBundle bundle, String[] bloonTypes)
      throws ConfigurationException {
    for (int i = 1; i < bloonTypes.length; i++) {
      String[] attributes = bundle.getString(bloonTypes[i]).split(",", 3);
      BloonsTypeNode currentBloon = new BloonsTypeNode(
          new BloonsType(this, bloonTypes[i], Integer.parseInt(attributes[0]),
              Double.parseDouble(attributes[1]), Specials.None));
      initializeNextBloons(currentBloon, attributes[2]);
      if (!bloonsTypeBloonMap.containsKey(bloonTypes[i])) {
        bloonsTypeBloonMap.put(bloonTypes[i], currentBloon);
      } else {
        throw new ConfigurationException(ERROR_RESOURCES.getString("ConflictingBloonsTypes"));
      }
    }
  }

  private void initializeNextBloons(BloonsTypeNode node, String nextBloonsData)
      throws ConfigurationException {
    String[] nextBloonsDataArray = nextBloonsData.split(",");
    if (bloonsTypeBloonMap.containsKey(nextBloonsDataArray[0])) {
      BloonsTypeNode nextBloonType = bloonsTypeBloonMap.get(nextBloonsDataArray[0]);
      if (nextBloonType.prev == null) {
        nextBloonType.prev = node;
        node.next = nextBloonType;
        try {
          node.numNext = Integer.parseInt(nextBloonsDataArray[1]);
        } catch (NumberFormatException e) {
          throw new ConfigurationException(ERROR_RESOURCES.getString("NonIntegerNumNext"));
        }
      } else {
        throw new ConfigurationException(ERROR_RESOURCES.getString("ConflictingBloonsTypes"));
      }
    } else {
      throw new ConfigurationException(ERROR_RESOURCES.getString("UnorderedBloonsTypeList"));
    }
  }

  /**
   * returns bloonstype represented by an index
   * @param index index of bloons type in doubly linked list chain
   * @return bloonstype at that index
   */
  public BloonsType getBloonsTypeRecord(int index) {
    BloonsTypeNode retNode = head;
    for (int i = 0; i < index; i++) {
      retNode = retNode.prev;
      if (retNode == null) {
        return null;
      }
    }
    return retNode.getType();
  }

  /**
   * returns bloonstype of a certain name
   * @param bloonsType String representing the name of the bloonstype
   * @return bloonstype represented by the bloonstype string
   */
  public BloonsType getBloonsTypeRecord(String bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType)) {
      new AlertHandler(ERROR_RESOURCES.getString("BloonsTypeError"),
          String.format(ERROR_RESOURCES.getString("InvalidBloonsTypeName"), bloonsType));
    }
    return bloonsTypeBloonMap.get(bloonsType).getType();
  }

  /**
   * returns next bloonstype of a bloonstype
   * @param bloonsType Bloonstype to get the next bloonstype of
   * @return next Bloonstype
   */
  public BloonsType getNextBloonsType(BloonsType bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType.name())) {
      new AlertHandler(ERROR_RESOURCES.getString("BloonsTypeError"),
          String.format(ERROR_RESOURCES.getString("InvalidBloonsTypeName"), bloonsType));
    }
    if (bloonsTypeBloonMap.get(bloonsType.name()).next == null) {
      return bloonsTypeBloonMap.get(bloonsType.name()).getType();
    }
    return bloonsTypeBloonMap.get(bloonsType.name()).next.getType();
  }

  /**
   * returns previous bloonstype of a bloonstype
   * @param bloonsType Bloonstype to get the previous bloonstype of
   * @return previous Bloonstype
   */
  public BloonsType getPrevBloonsType(BloonsType bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType.name())) {
      new AlertHandler(ERROR_RESOURCES.getString("BloonsTypeError"),
          String.format(ERROR_RESOURCES.getString("InvalidBloonsTypeName"), bloonsType));
    }
    if (bloonsTypeBloonMap.get(bloonsType.name()).prev == null) {
      return null;
    }
    return bloonsTypeBloonMap.get(bloonsType.name()).prev.getType();
  }

  /**
   * returns the number of next bloonstype of a certain bloonstype
   * @param bloonsType Bloonstype to get the num of next bloons of
   * @return int representing number of next bloonstype
   */
  public int getNumNextBloons(BloonsType bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType.name())) {
      new AlertHandler(ERROR_RESOURCES.getString("BloonsTypeError"),
          String.format(ERROR_RESOURCES.getString("InvalidBloonsTypeName"), bloonsType));
    }
    return bloonsTypeBloonMap.get(bloonsType.name()).numNext;
  }

}
