package ooga.backend.towers;

import java.util.List;
import ooga.backend.API.GamePiece;
import ooga.backend.collections.Iterator;

public class TowersIterator implements Iterator {

  private int index;
  private final List<Tower> towers;

  public TowersIterator(List<Tower> towers){
    this.towers = towers;
    index = 0;
  }

  @Override
  public GamePiece getNext() {
    if (hasMore()) {
      GamePiece gamePiece = towers.get(index);
      index++;
      return gamePiece;
    }
    return null;
  }

  @Override
  public boolean hasMore() {
    return index < towers.size();
  }

  @Override
  public void reset() {
    index = 0;
  }
}
