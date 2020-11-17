package ooga.backend.bloons.special;

import java.lang.reflect.Constructor;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

public abstract class SpecialBloon extends Bloon {

  public SpecialBloon(BloonsType bloonsType, double xPosition, double yPosition,
      double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  @Override
  public Bloon[] shootBloon() {
    int numBloonsToProduce = getBloonsType().chain().getNumNextBloons(super.getBloonsType());
    Bloon[] bloons = new Bloon[numBloonsToProduce];
    for (Specials special : getBloonsType().specials()) {
      for (int i = 0; i < numBloonsToProduce; i++) {
        try {
          String specialName = special.toString();
          specialName = specialName.substring(0,1).toUpperCase() +
              specialName.substring(1).toLowerCase();
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
    }
    return bloons;
  }

}
