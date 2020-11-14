package ooga.backend.bloons.special;

import java.util.ResourceBundle;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

public class RegenBloon extends Bloon {

  private static final String RESOURCE_BUNDLE_PATH = "bloon_resources/GameMechanics";
  private final BloonsType originalType;
  private final int fullTimer;
  private int timer;

  public RegenBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newCamoType = new BloonsType(bloonsType.chain(), bloonsType.name(), bloonsType.RBE(), bloonsType.relativeSpeed(), bloonsType.specials());
    newCamoType.specials().add(Specials.REGEN);
    setBloonsType(newCamoType);
    ResourceBundle gameMechanics = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
    this.originalType = bloonsType;
    this.fullTimer = Integer.parseInt(gameMechanics.getString("RegrowthTimer"));
    this.timer = fullTimer;
  }

  @Override
  public void update() {
    if (!getBloonsType().equals(originalType)) {
      if (timer <= 0) {
        setBloonsType(getBloonsType().chain().getPrevBloonsType(getBloonsType()));
        timer = fullTimer;
      } else {
        timer--;
      }
    }
    super.update();
  }

}
