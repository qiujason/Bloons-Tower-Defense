package ooga.backend.collections;

import ooga.backend.GamePiece;

/**
 * interface representing a game piece collection
 * @param <T> generic game piece type
 *
 * @author Jason Qiu
 */
public interface GamePieceCollection<T extends GamePiece> {

  /**
   * adds a game piece into the collection
   * @param gamePiece game piece to be added into the collection
   * @return true if game piece was successfully added in
   */
  boolean add(T gamePiece);

  /**
   * removes a game piece in the collection
   * @param gamePiece game piece that may be in the collection
   * @return true if the game piece was successfulyy removed
   */
  boolean remove(T gamePiece);

  /**
   * updates all in the game piece collection
   */
  void updateAll();

  /**
   * clears the game piece collection
   */
  void clear();

  /**
   * creates an iterator that cna iterate over the game piece collection
   * @return GamePieceIterator that iterates over the collection
   */
  GamePieceIterator<T> createIterator();

  /**
   * returns size of the collection
   * @return int size of the collection
   */
  int size();

  /**
   * returns if the collection contains a specific game piece object
   * @param gamePiece game piece that may or may not be contained in collection
   * @return true if collection contains the game piece
   */
  boolean contains(T gamePiece);

  /**
   * returns if collection is empty
   * @return true if collection is empty
   */
  boolean isEmpty();


}
