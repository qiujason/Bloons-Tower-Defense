package ooga.backend.collections;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ooga.backend.GamePiece;

/**
 * an iterator of a GamePiece collection containing GamePiece T
 * @param <T> generic type that represents a game piece
 *
 * @author Jason Qiu
 */
public class GamePieceIterator<T extends GamePiece> implements Iterator<T> {

  private int index;
  private final List<T> collection;

  /**
   * creates an iterator of a collection
   * @param existingCollection list containing all the game pieces
   */
  public GamePieceIterator(List<T> existingCollection) {
    this.index = 0;
    this.collection = new ArrayList<>(existingCollection);
  }

  /**
   * returns if there are more game pieces to iterate over
   * @return true if there are more game pieces to iterator over
   */
  @Override
  public boolean hasNext() {
    return index < collection.size();
  }


  /**
   * returns next game piece in iterator
   * @return T game piece that is next in the iterator
   */
  @Override
  public T next() {
    if (hasNext()) {
      T gamePiece = collection.get(index);
      index++;
      return gamePiece;
    }
    return null;
  }

  /**
   * resets game piece iterator to start at the beginning
   */
  public void reset() {
    index = 0;
  }

}
