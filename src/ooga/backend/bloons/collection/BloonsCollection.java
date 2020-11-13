package ooga.backend.bloons.collection;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.Iterator;

public class BloonsCollection implements GamePieceCollection {

  private List<Bloon> bloons;

  public BloonsCollection() {
    bloons = new ArrayList<>();
  }

  public BloonsCollection(List<Bloon> bloonsList){
    bloons = bloonsList;
  }

  @Override
  public boolean add(GamePiece bloon) {
    if (bloon instanceof Bloon) {
      bloons.add((Bloon)bloon);
      sort();
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(GamePiece bloon) {
    if (bloon instanceof Bloon) {
      return bloons.remove(bloon);
    }
    return false;
  }

  @Override
  public void updateAll() {
    for (Bloon bloon : bloons) {
      bloon.update();
    }
    sort();
  }

  @Override
  public void clear() {
    bloons = new ArrayList<>();
  }

  @Override
  public Iterator createIterator() {
    return new BloonsIterator(bloons);
  }

  private void sort() {
    bloons.sort((a,b) -> (int)(b.getDistanceTraveled()*100) - (int)(a.getDistanceTraveled() * 100));
  }

//  @Override
//  public String toString(){
//    return ""+ bloons.size();
//  }

}
