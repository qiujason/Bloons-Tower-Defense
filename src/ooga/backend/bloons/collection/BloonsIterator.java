package ooga.backend.bloons.collection;

import java.util.List;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.collections.Iterator;

public class BloonsIterator implements Iterator {

  private int index;
  private final List<Bloon> bloons;

  public BloonsIterator(List<Bloon> bloons) {
    index = 0;
    this.bloons = bloons;
  }

  @Override
  public GamePiece getNext() {
    if (hasMore()) {
      GamePiece gamePiece = bloons.get(index);
      index++;
      return gamePiece;
    }
    return null;
  }

  @Override
  public boolean hasMore() {
    return index < bloons.size();
  }

  @Override
  public void reset() {
    index = 0;
  }

}
