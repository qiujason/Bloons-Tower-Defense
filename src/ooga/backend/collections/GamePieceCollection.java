package ooga.backend.collections;

import ooga.backend.GamePiece;

public interface GamePieceCollection {

  boolean add(GamePiece gamePiece);

  boolean remove(GamePiece gamePiece);

  void updateAll();

  void clear();

  Iterator createIterator();

}
