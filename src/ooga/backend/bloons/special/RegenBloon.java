package ooga.backend.bloons.special;

import java.util.ResourceBundle;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;

public class RegenBloon extends Bloon {

  private static final String RESOURCE_BUNDLE_PATH = "src/ooga/backend/resources/GameMechanics.properties";
  private final int fullTimer;
  private int timer;

  public RegenBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    ResourceBundle gameMechanics = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
    fullTimer = Integer.parseInt(gameMechanics.getString("RegrowthTimer"));
    timer = fullTimer;
  }

  @Override
  public void update() {
    if (timer <= 0) {
      BloonsType nextType = getBloonsType().;
      timer = fullTimer;
    } else {
      timer--;
    }
    super.update();
  }

}
