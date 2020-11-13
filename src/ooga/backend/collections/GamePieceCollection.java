package ooga.backend.collections;

import ooga.backend.GamePiece;

public interface GamePieceCollection<T extends GamePiece> {

  boolean add(T gamePiece);

  boolean remove(T gamePiece);

  void updateAll();

  void clear();

  GamePieceIterator<T> createIterator();

}
