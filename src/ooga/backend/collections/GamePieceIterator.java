package ooga.backend.collections;


import java.util.Iterator;
import java.util.List;
import ooga.backend.GamePiece;

public class GamePieceIterator<T extends GamePiece> implements Iterator<T> {

  private int index;
  private final List<T> collection;

  public GamePieceIterator(List<T> existingCollection) {
    this.index = 0;
    this.collection = existingCollection;
  }

  @Override
  public boolean hasNext() {
    return index < collection.size();
  }

  @Override
  public T next() {
    if (hasNext()) {
      T gamePiece = collection.get(index);
      index++;
      return gamePiece;
    }
    return null;
  }

  public void reset() {
    index = 0;
  }

}
