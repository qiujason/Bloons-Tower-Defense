/**
 * @author Annshine
 * This class should be used to represent a collection of Towers
 */
package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.GamePieceIterator;

public class TowersCollection implements GamePieceCollection<Tower> {

  private List<Tower> towers;

  public TowersCollection() {
    towers = new ArrayList<>();
  }

  @Override
  public boolean add(Tower tower) {
    if (tower != null) {
      towers.add(tower);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(Tower tower) {
    if (tower != null) {
      return towers.remove(tower);
    }
    return false;
  }

  @Override
  public void updateAll() {
    for (Tower tower : towers) {
      tower.update();
    }
  }

  @Override
  public void clear() {
    towers = new ArrayList<>();
  }

  @Override
  public GamePieceIterator<Tower> createIterator() {
    return new GamePieceIterator<>(towers);
  }

  @Override
  public int size() {
    return towers.size();
  }

  @Override
  public boolean contains(Tower tower) {
    return towers.contains(tower);
  }

  @Override
  public boolean isEmpty() {
    return towers.isEmpty();
  }

}
