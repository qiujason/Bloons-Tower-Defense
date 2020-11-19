package ooga.backend.bloons;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceCollection;
import ooga.backend.collections.GamePieceIterator;

public class BloonsCollection implements GamePieceCollection<Bloon> {

  private static final String FACTORY_FILE_PATH = "ooga.backend.bloons.factory.";
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
    bloons.sort(
        (a, b) -> (int) (b.getDistanceTraveled() * 100) - (int) (a.getDistanceTraveled() * 100));
  }

  public Bloon get(int index) {
    return bloons.get(index);
  }

  @Override
  public int size() {
    return bloons.size();
  }

  @Override
  public boolean contains(Bloon bloon) {
    return bloons.contains(bloon);
  }

  @Override
  public boolean isEmpty() {
    return bloons.isEmpty();
  }

  public BloonsCollection copyOf(BloonsCollection collection) throws ConfigurationException {
    BloonsCollection copy = new BloonsCollection();
    GamePieceIterator<Bloon> iterator = collection.createIterator();
    while (iterator.hasNext()) {
      Bloon copyBloon = createCopy(iterator.next());
      copy.add(copyBloon);
    }
    return copy;
  }

  private Bloon createCopy(Bloon bloon) throws ConfigurationException {
    if (bloon.getBloonsType().specials() != Specials.None) {
      try {
        Class<?> specialBloonClass = Class.forName(
            FACTORY_FILE_PATH + bloon.getBloonsType().specials().toString() + "BloonsFactory");
        Constructor<?> specialBloonConstructor = specialBloonClass.getConstructor();
        BloonsFactory specialFactory = (BloonsFactory) specialBloonConstructor.newInstance();
        return specialFactory.createBloon(bloon);
      } catch (ClassNotFoundException e) {
        throw new ConfigurationException("SpecialNotFound");
      } catch (Exception e) {
        throw new ConfigurationException("OtherSpecialBloonErrors");
      }
    }
    return new BasicBloonsFactory()
        .createBloon(bloon.getBloonsType(), bloon.getXPosition(), bloon.getYPosition(),
            bloon.getXVelocity(), bloon.getYVelocity());
  }


}
