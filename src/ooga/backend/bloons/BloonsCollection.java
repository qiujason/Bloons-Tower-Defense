package ooga.backend.bloons;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.GamePieceIterator;

public class BloonsCollection implements GamePieceCollection<Bloon> {

  private List<Bloon> bloons;

  public BloonsCollection() {
    bloons = new ArrayList<>();
  }

  public BloonsCollection(List<Bloon> bloonsList) {
    bloons = bloonsList;
  }

  @Override
  public boolean add(Bloon bloon) {
    if (bloon != null) {
      bloons.add(bloon);
      sort();
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(Bloon bloon) {
    if (bloon != null) {
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
  public GamePieceIterator<Bloon> createIterator() {
    return new GamePieceIterator<>(bloons);
  }

  private void sort() {
    bloons.sort((a,b) -> (int)(b.getDistanceTraveled()*100) - (int)(a.getDistanceTraveled() * 100));
  }

  public Bloon get(int index){
    return bloons.get(index);
  }

  @Override
  public int size(){
    return bloons.size();
  }

  @Override
  public boolean contains(Bloon bloon) {
    return bloons.contains(bloon);
  }

  @Override
  public boolean isEmpty(){
    return bloons.isEmpty();
  }

  public BloonsCollection copyOf(BloonsCollection collection){
    BloonsCollection copy = new BloonsCollection();
    GamePieceIterator<Bloon> iterator = collection.createIterator();
    while(iterator.hasNext()){
      Bloon copyBloon = createCopy(iterator.next());
      copy.add(copyBloon);
    }
    return copy;
  }

  private Bloon createCopy(Bloon bloon){
    return new Bloon(bloon.getBloonsType(), bloon.getXPosition(), bloon.getYPosition(), bloon.getXVelocity(), bloon.getYVelocity());
  }


}
