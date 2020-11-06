package ooga.backend.bloons;

import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.collections.Iterator;

public class BloonsIterator implements Iterator {

  private int index;
  private final List<Bloons> bloons;

  public BloonsIterator(List<Bloons> bloons) {
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
