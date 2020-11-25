/**
 * RoadItemsCollection to represent a collection of RoadItems
 * Implements GamePieceCollection
 * @author Annshine
 */
package ooga.backend.roaditems;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.GamePieceIterator;

public class RoadItemsCollection implements GamePieceCollection<RoadItem> {

  private List<RoadItem> roadItems;

  /**
   * Constructor for RoadItemsCollection
   */
  public RoadItemsCollection() {
    roadItems = new ArrayList<>();
  }

  @Override
  public boolean add(RoadItem gamePiece) {
    if (gamePiece != null) {
      roadItems.add(gamePiece);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(RoadItem gamePiece) {
    if (gamePiece != null) {
      return roadItems.remove(gamePiece);
    }
    return false;
  }

  @Override
  public void updateAll() {
    for (RoadItem roadItem : roadItems) {
      roadItem.update();
    }
  }

  @Override
  public void clear() {
    roadItems = new ArrayList<>();
  }

  @Override
  public GamePieceIterator<RoadItem> createIterator() {
    return new GamePieceIterator<>(roadItems);
  }

  @Override
  public int size() {
    return roadItems.size();
  }

  @Override
  public boolean contains(RoadItem roadItem) {
    return roadItems.contains(roadItem);
  }

  @Override
  public boolean isEmpty() {
    return roadItems.isEmpty();
  }
}
