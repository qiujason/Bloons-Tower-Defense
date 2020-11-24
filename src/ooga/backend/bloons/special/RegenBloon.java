package ooga.backend.bloons.special;

import java.util.ResourceBundle;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;
import ooga.visualization.animationhandlers.AnimationHandler;

public class RegenBloon extends SpecialBloon {

  private final BloonsType originalType;
  private final int fullTimer;
  private int timer;

  public RegenBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity,
      double yVelocity) {
    this(bloonsType, bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  public RegenBloon(BloonsType bloonsType, BloonsType originalType, double xPosition,
      double yPosition, double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newRegenType = new BloonsType(bloonsType.chain(), bloonsType.name(),
        bloonsType.RBE(), bloonsType.relativeSpeed(), Specials.Regen);
    setBloonsType(newRegenType);
    this.originalType = originalType;
    ResourceBundle gameMechanics = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
    this.fullTimer = Integer.parseInt(gameMechanics.getString("RegrowthTimer"));
    this.timer = fullTimer * (int)AnimationHandler.FRAMES_PER_SECOND;
  }

  @Override
  public void update() {
    if (!getBloonsType().equals(originalType)) {
      if (timer <= 0) {
        BloonsType prevType = getBloonsType().chain().getPrevBloonsType(getBloonsType());
        prevType = new BloonsType(prevType.chain(), prevType.name(), prevType.RBE(),
            prevType.relativeSpeed(), Specials.Regen);
        setBloonsType(prevType);
        timer = fullTimer;
      } else {
        timer--;
      }
    }
    super.update();
  }

  public BloonsType getOriginalType() {
    return originalType;
  }

}
