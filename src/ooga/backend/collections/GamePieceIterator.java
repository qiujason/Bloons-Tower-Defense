package ooga.backend.collections;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import ooga.backend.GamePiece;

public class GamePieceIterator<T extends GamePiece> implements Iterator<T> {

  private int index;
  private final List<T> collection;
  private int totalElements;

  public GamePieceIterator(List<T> existingCollection) {
    this.index = 0;
    this.collection = existingCollection;
    this.totalElements = collection.size();
  }

  @Override
  public boolean hasNext() {
    return index < collection.size();
  }

  @Override
  public T next() {
    if (collection.size() != totalElements) {
      throw new ConcurrentModificationException();
    }
    if (hasNext()) {
      T gamePiece = collection.get(index);
      index++;
      return gamePiece;
    }
    return null;
  }

  public void remove(T element) {
    for (int i = 0; i < collection.size(); i++) {
      if (collection.get(i) == element) {
        collection.remove(collection.get(i));
        if (i < index) {
          index = Math.max(1, index)-1; // ignore warning: floors index at 0
        }
        totalElements--;
        break;
      }
    }
  }

  public void reset() {
    index = 0;
  }

}
