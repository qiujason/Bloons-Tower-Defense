package ooga.backend.bloons.special;

import java.lang.reflect.Constructor;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.BloonsType;

public abstract class SpecialBloon extends Bloon {

  private static final String FACTORY_FILE_PATH = "ooga.backend.bloons.factory.";

  public SpecialBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  @Override
  public Bloon[] shootBloon() {
    int numBloonsToProduce = getBloonsType().chain().getNumNextBloons(super.getBloonsType());
    Bloon[] bloons = new Bloon[numBloonsToProduce];
    for (int i = 0; i < numBloonsToProduce; i++) {
      try {
        String specialName = getBloonsType().specials().toString();
        Class<?> specialBloonClass = Class.forName(FACTORY_FILE_PATH + specialName + "BloonsFactory");
        Constructor<?> specialBloonConstructor = specialBloonClass.getConstructor();
        BloonsFactory specialFactory = (BloonsFactory)specialBloonConstructor.newInstance();
        if (bloons[i] != null) {
          bloons[i] = specialFactory.createBloon(bloons[i]);
        } else {
          bloons[i] = specialFactory.createNextBloon(this);
        }
      } catch (ClassNotFoundException e) {
        //TODO: handle
      } catch (Exception e) {
        //TODO: handle
      }
    }
    return bloons;
  }

}
