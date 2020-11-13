package ooga.backend.bloons.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;

public class BloonsTypeChain {

  private static class BloonsTypeNode {

    private final BloonsType type;
    private BloonsTypeNode prev;
    private BloonsTypeNode next;
    private int numNext;

    private BloonsTypeNode(BloonsType type) {
      this.type = type;
    }

    private BloonsType getType() {
      return type;
    }

  }

  private final Map<String, BloonsTypeNode> bloonsTypeBloonMap;
  private BloonsTypeNode head;

  public BloonsTypeChain(String propertyFilePath) {
    bloonsTypeBloonMap = new HashMap<>();
    ResourceBundle bundle = ResourceBundle.getBundle(propertyFilePath);
    String[] bloonTypes = bundle.getString("Order").split(",");

    if (bloonTypes.length <= 0) {
      //TODO: throw exception (empty property file)
    }

    if (bloonTypes[0].equals("DEAD")) {
      head = new BloonsTypeNode(new BloonsType("DEAD", 0, 0, new HashSet<>()));
      bloonsTypeBloonMap.put("DEAD", head); // dead bloon
    } else {
      //TODO: throw exception (dead is not the first bloon)
    }

    for (int i = 1; i < bloonTypes.length; i++) {
      String[] attributes = bundle.getString(bloonTypes[i]).split(",", 3);
      BloonsTypeNode currentBloon = new BloonsTypeNode(
          new BloonsType(bloonTypes[i], Integer.parseInt(attributes[0]),
              Double.parseDouble(attributes[1]), new HashSet<>()));
      initializeNextBloons(currentBloon, attributes[2]);
      if (!bloonsTypeBloonMap.containsKey(bloonTypes[i])) {
        bloonsTypeBloonMap.put(bloonTypes[i], currentBloon);
      } else {
        // TODO: throw exception (duplicate bloon information)
      }
    }
  }

  private void initializeNextBloons(BloonsTypeNode node, String nextBloonsData) {
    String[] nextBloonsDataArray = nextBloonsData.split(",");
    if (bloonsTypeBloonMap.containsKey(nextBloonsDataArray[0])) {
      BloonsTypeNode nextBloonType = bloonsTypeBloonMap.get(nextBloonsDataArray[0]);
      if (nextBloonType.prev == null) {
        nextBloonType.prev = node;
        node.next = nextBloonType;
        node.numNext = Integer.parseInt(nextBloonsDataArray[1]);
        //TODO: throw exception for non integers
      } else {
        // TODO: throw exception (conflicting previous bloons)
      }
    } else {
      //TODO: throw exception (unordered bloons list in properties file)
    }
  }

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

  public BloonsType getBloonsTypeRecord(String bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType)) {
      //TODO: throw exception invalid bloons name
    }
    return bloonsTypeBloonMap.get(bloonsType).getType();
  }

  public BloonsType getNextBloonsType(BloonsType bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType.name())) {
      //TODO: throw exception invalid bloons name
    }
    if (bloonsTypeBloonMap.get(bloonsType.name()).next == null) {
      return bloonsTypeBloonMap.get(bloonsType.name()).getType();
    }
    return bloonsTypeBloonMap.get(bloonsType.name()).next.getType();
  }

  public BloonsType getPrevBloonsType(BloonsType bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType.name())) {
      //TODO: throw exception invalid bloons name
    }
    if (bloonsTypeBloonMap.get(bloonsType.name()).prev == null) {
      return null;
    }
    return bloonsTypeBloonMap.get(bloonsType.name()).prev.getType();
  }

  public int getNumNextBloons(BloonsType bloonsType) {
    if (!bloonsTypeBloonMap.containsKey(bloonsType.name())) {
      //TODO: throw exception invalid bloons name
    }
    return bloonsTypeBloonMap.get(bloonsType.name()).numNext;
  }

}
