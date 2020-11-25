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

/**
 * Bloons collection class that represents a collection of bloons
 *
 * @author Jason Qiu
 */
public class BloonsCollection implements GamePieceCollection<Bloon> {

  private static final String FACTORY_FILE_PATH = "ooga.backend.bloons.factory.";
  private List<Bloon> bloons;

  /**
   * creates a new empty bloons collection
   */
  public BloonsCollection() {
    bloons = new ArrayList<>();
  }

  /**
   * creates a new bloons collection containing bloons found in a preexisting list
   * @param bloonsList preexisting list of bloons
   */
  public BloonsCollection(List<Bloon> bloonsList) {
    bloons = bloonsList;
  }

  /**
   * adds a new bloon to colletion
   * @param bloon bloon to be added
   * @return true if bloon is successfully added
   */
  @Override
  public boolean add(Bloon bloon) {
    if (bloon != null) {
      bloons.add(bloon);
      sort();
      return true;
    }
    return false;
  }

  /**
   * removes a bloon in colletion
   * @param bloon bloon to be removed
   * @return true if bloon is successfully removed
   */
  @Override
  public boolean remove(Bloon bloon) {
    if (bloon != null) {
      return bloons.remove(bloon);
    }
    return false;
  }

  /**
   * updates all bloons in collection
   */
  @Override
  public void updateAll() {
    for (Bloon bloon : bloons) {
      bloon.update();
    }
    sort();
  }

  /**
   * clears bloon collection
   */
  @Override
  public void clear() {
    bloons = new ArrayList<>();
  }

  /**
   * returns an iterator that can iterate through the collection
   * @return GamePieceIterator that can iterate through the bloons collection
   */
  @Override
  public GamePieceIterator<Bloon> createIterator() {
    return new GamePieceIterator<>(bloons);
  }

  private void sort() {
    bloons.sort(
        (a, b) -> (int) (b.getDistanceTraveled() * 100) - (int) (a.getDistanceTraveled() * 100));
  }

  /**
   * returns bloon at an index
   * @param index index of bloon to be retrieved
   * @return bloon at index
   */
  public Bloon get(int index) {
    return bloons.get(index);
  }

  /**
   * gets the size of the bloons collection
   * @return int representing size of bloon collection
   */
  @Override
  public int size() {
    return bloons.size();
  }

  /**
   * returns true if bloon is contained in bloons collection
   * @param bloon bloon contained in bloons collection
   * @return true if bloons collection contains bloon
   */
  @Override
  public boolean contains(Bloon bloon) {
    return bloons.contains(bloon);
  }

  /**
   * returns true if collection is empty
   * @return true if empty
   */
  @Override
  public boolean isEmpty() {
    return bloons.isEmpty();
  }

  /**
   * creates copy of bloons collection
   * @param collection bloons collection to make copy of
   * @return copy of bloons collection
   * @throws ConfigurationException if special bloon types can not be made
   */
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
