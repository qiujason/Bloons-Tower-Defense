package ooga.backend.collections;


import ooga.backend.GamePiece;

public interface Iterator {

  GamePiece getNext();

  boolean hasMore();

  void reset();

}
