package ooga.backend.collections;


import ooga.backend.API.GamePiece;

public interface Iterator {

  GamePiece getNext();

  boolean hasMore();

  void reset();

}
