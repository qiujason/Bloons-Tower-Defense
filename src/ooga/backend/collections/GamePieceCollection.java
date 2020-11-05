package ooga.backend.collections;

import ooga.backend.API.GamePiece;

public interface GamePieceCollection {

  boolean add(GamePiece gamePiece);

  boolean remove(GamePiece gamePiece);

  Iterator createIterator();

}
