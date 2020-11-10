package ooga.backend.bloons.decorator;

import java.util.ResourceBundle;
import ooga.backend.bloons.Bloon;

public class RegenDecorator extends BaseDecorator {

  private static final String RESOURCE_BUNDLE_PATH = "src/ooga/backend/resources/GameMechanics.properties";
  private final ResourceBundle gameMechanics;
  private final int fullTimer;
  private int timer;

  public RegenDecorator(Bloon bloon) {
    super(bloon);
    gameMechanics = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
    fullTimer = Integer.parseInt(gameMechanics.getString("RegrowthTimer"));
    timer = fullTimer;
  }

  @Override
  public void update() {
    if (timer <= 0) {

      timer = fullTimer;
    } else {
      timer--;
    }
    bloonWrappee.update();
  }

}
