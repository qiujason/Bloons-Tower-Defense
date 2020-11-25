package ooga.backend.bloons.special;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.BloonsType;

/**
 * abstract class that all special bloons have to extend
 * overrides the bloon's shoot bloon method so that special bloons of the same special type
 * are spawned when hit
 *
 * @author Jason Qiu
 */
public abstract class SpecialBloon extends Bloon {

  private static final String FACTORY_FILE_PATH = "ooga.backend.bloons.factory.";

  /**
   * creates a special bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   */
  public SpecialBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  /**
   * spawns next generation of special bloons after bloon is hit
   * @return array of bloons that represents special bloons that are spawned after bloon is hit
   * @throws ConfigurationException if special bloon type is found or cannot be made
   */
  @Override
  public Bloon[] shootBloon() throws ConfigurationException {
    int numBloonsToProduce = getBloonsType().chain().getNumNextBloons(super.getBloonsType());
    Bloon[] bloons = new Bloon[numBloonsToProduce];
    for (int i = 0; i < numBloonsToProduce; i++) {
      try {
        String specialName = getBloonsType().specials().toString();
        Class<?> specialBloonClass = Class
            .forName(FACTORY_FILE_PATH + specialName + "BloonsFactory");
        Constructor<?> specialBloonConstructor = specialBloonClass.getConstructor();
        BloonsFactory specialFactory = (BloonsFactory) specialBloonConstructor.newInstance();
        bloons[i] = specialFactory.createNextBloon(this);
      } catch (ClassNotFoundException e) {
        throw new ConfigurationException("SpecialNotFound");
      } catch (Exception e) {
        throw new ConfigurationException("OtherSpecialBloonErrors");
      }
    }
    return bloons;
  }

}
