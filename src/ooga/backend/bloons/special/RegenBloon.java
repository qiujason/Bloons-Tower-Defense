package ooga.backend.bloons.special;

import java.util.ResourceBundle;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;
import ooga.visualization.animationhandlers.AnimationHandler;

/**
 * represents a regen bloon that regenerates to high bloon types after being hit
 *
 * @author Jason Qiu
 */
public class RegenBloon extends SpecialBloon {

  private final BloonsType originalType;
  private final int fullTimer;
  private int timer;

  /**
   * creates a regen bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   */
  public RegenBloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity,
      double yVelocity) {
    this(bloonsType, bloonsType, xPosition, yPosition, xVelocity, yVelocity);
  }

  /**
   * creates a regen bloon
   * @param bloonsType bloon type of new bloon
   * @param originalType original bloon type that represents the max bloon type that the regen bloon can
   *                     regen to
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   */
  public RegenBloon(BloonsType bloonsType, BloonsType originalType, double xPosition,
      double yPosition, double xVelocity, double yVelocity) {
    super(bloonsType, xPosition, yPosition, xVelocity, yVelocity);
    BloonsType newRegenType = new BloonsType(bloonsType.chain(), bloonsType.name(),
        bloonsType.RBE(), bloonsType.relativeSpeed(), Specials.Regen);
    setBloonsType(newRegenType);
    this.originalType = originalType;
    ResourceBundle gameMechanics = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
    this.fullTimer = Integer.parseInt(gameMechanics.getString("RegrowthTimer"))
        * (int)AnimationHandler.FRAMES_PER_SECOND;
    this.timer = fullTimer;
  }

  /**
   * updates the timer of the regen bloon in addition to the standard update done in bloon class
   */
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

  /**
   * gets the original bloon type that the regen bloon can regen to
   * @return bloon type that is the max bloon type the bloon can regen to
   */
  public BloonsType getOriginalType() {
    return originalType;
  }

}
