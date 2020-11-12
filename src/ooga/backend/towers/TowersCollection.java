package ooga.backend.towers;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.GamePiece;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.Iterator;

public class TowersCollection implements GamePieceCollection {

  List<Tower> towers;

  public TowersCollection(){
    towers = new ArrayList<>();
  }

  @Override
  public boolean add(GamePiece gamePiece) {
    if (gamePiece instanceof Tower) {
      towers.add((Tower)gamePiece);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(GamePiece gamePiece) {
    if (gamePiece instanceof Tower) {
      return towers.remove(gamePiece);
    }
    return false;
  }

  @Override
  public void updateAll() {
    for (Tower tower : towers) {
      tower.update();
    }
  }

  @Override
  public void clear() {
    towers = new ArrayList<>();
  }

  @Override
  public Iterator createIterator() {
    return new TowersIterator(towers);
  }
}
