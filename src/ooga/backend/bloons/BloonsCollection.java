package ooga.backend.bloons;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.Iterator;

public class BloonsCollection implements GamePieceCollection {

  List<Bloon> bloons;

  public BloonsCollection() {
    bloons = new ArrayList<>();
  }

  public BloonsCollection(List<Bloon> bloonsList){
    bloons = bloonsList;
  }

  @Override
  public boolean add(GamePiece bloon) {
    if (bloon instanceof Bloon) {
      bloons.add((Bloon)bloon);
      sort();
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(GamePiece bloon) {
    if (bloon instanceof Bloon) {
      return bloons.remove(bloon);
    }
    return false;
  }

  @Override
  public void updateAll() {
    for (Bloon bloon : bloons) {
      bloon.update();
    }
    sort();
  }

  @Override
  public void clear() {
    bloons = new ArrayList<>();
  }

  @Override
  public Iterator createIterator() {
    return new BloonsIterator(bloons);
  }

  public void sort() {
    bloons.sort((a,b) -> (int)(b.getDistanceTraveled()*10) - (int)(a.getDistanceTraveled() * 10));
  }

}
