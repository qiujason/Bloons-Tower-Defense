package ooga.backend.bloons.special;

import java.util.HashSet;
import java.util.ResourceBundle;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;

public class RegenBloon extends Bloon {

  private static final String RESOURCE_BUNDLE_PATH = "bloon_resources/GameMechanics";
  private final int fullTimer;
  private int timer;

  public RegenBloon(BloonsTypeChain chain, BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(chain, bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newCamoType = new BloonsType(bloonsType.name(), bloonsType.RBE(), bloonsType.relativeSpeed(), new HashSet<>());
    newCamoType.specials().add(Specials.REGEN);
    setBloonsType(newCamoType);
    ResourceBundle gameMechanics = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
    fullTimer = Integer.parseInt(gameMechanics.getString("RegrowthTimer"));
    timer = fullTimer;
  }

  @Override
  public void update() {
    if (timer <= 0) {
      setBloonsType(super.getChain().getPrevBloonsType(getBloonsType()));
      timer = fullTimer;
    } else {
      timer--;
    }
    super.update();
  }

}
