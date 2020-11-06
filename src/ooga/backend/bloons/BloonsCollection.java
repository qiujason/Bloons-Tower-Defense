package ooga.backend.bloons;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.Iterator;

public class BloonsCollection implements GamePieceCollection {

  List<Bloons> bloons;

  public BloonsCollection() {
    bloons = new ArrayList<>();
  }

  @Override
  public boolean add(GamePiece bloon) {
    if (bloon instanceof Bloons) {
      bloons.add((Bloons)bloon);
      sort();
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(GamePiece bloon) {
    if (bloon instanceof Bloons) {
      return bloons.remove(bloon);
    }
    return false;
  }

  @Override
  public Iterator createIterator() {
    return new BloonsIterator(bloons);
  }

  public void sort() {
    bloons.sort((a,b) -> b.getDistanceTraveled() - a.getDistanceTraveled());
  }

}
