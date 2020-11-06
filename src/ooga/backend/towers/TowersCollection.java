package ooga.backend.towers;

import ooga.backend.API.GamePiece;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.Iterator;

public class TowersCollection implements GamePieceCollection {

  @Override
  public boolean add(GamePiece gamePiece) {
    return false;
  }

  @Override
  public boolean remove(GamePiece gamePiece) {
    return false;
  }

  @Override
  public void updateAll() {

  }

  @Override
  public void clear() {

  }

  @Override
  public Iterator createIterator() {
    return null;
  }
}
