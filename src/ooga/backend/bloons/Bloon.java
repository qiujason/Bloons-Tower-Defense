package ooga.backend.bloons;


import java.util.ResourceBundle;
import ooga.backend.API.BloonsAPI;
import ooga.backend.ConfigurationException;
import ooga.backend.GamePiece;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.visualization.animationhandlers.AnimationHandler;

/**
 * Represents a Bloon within the game
 *
 * @author Jason Qiu
 */
public class Bloon extends GamePiece implements BloonsAPI {

  public static final String RESOURCE_BUNDLE_PATH = "bloon_resources/GameMechanics";
  private static final ResourceBundle GAME_MECHANICS = ResourceBundle
      .getBundle(RESOURCE_BUNDLE_PATH);

  private BloonsType bloonsType;
  private double xVelocity;
  private double yVelocity;
  private double distanceTraveled;
  private final double relativeSpeed;

  private final int freezeTimePeriod;
  private int freezeTimer;
  private boolean freezeActive;
  private final int slowDownTimePeriod;
  private int slowDownTimer;
  private boolean slowDownActive;
  private double speedEffectFactor;

  /**
   * creates a new bloon
   * @param bloonsType bloon type of new bloon
   * @param xPosition x position of new bloon
   * @param yPosition y position of new bloon
   * @param xVelocity x velocity of new bloon
   * @param yVelocity y velocity of new bloon
   * @return new bloon containing all input parameters in its initial state
   */
  public Bloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity,
      double yVelocity) {
    super(xPosition, yPosition);
    this.bloonsType = bloonsType;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.distanceTraveled = 0;
    this.relativeSpeed = bloonsType.relativeSpeed();

    this.freezeTimePeriod = Integer.parseInt(GAME_MECHANICS.getString("FreezeTimePeriod"))
        * (int) AnimationHandler.FRAMES_PER_SECOND;
    this.freezeTimer = 0;
    this.freezeActive = false;
    this.slowDownTimePeriod = Integer.parseInt(GAME_MECHANICS.getString("SlowDownTimePeriod"))
        * (int) AnimationHandler.FRAMES_PER_SECOND;
    this.slowDownTimer = 0;
    this.slowDownActive = false;
    this.speedEffectFactor = 1;
  }

  /**
   * updates the bloon
   */
  @Override
  public void update() {
    updatePosition();
    updateEffects();
  }

  /**
   * sets x velocity
   * @param newXVelocity to update the x velocity
   */
  @Override
  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  /**
   * sets y velocity
   * @param newYVelocity to update the y velocity
   */
  @Override
  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
  }

  /**
   * returns current x velocity
   * @return x velocity
   */
  public double getXVelocity() {
    return xVelocity;
  }

  /**
   * returns current y velocity
   * @return y velocity
   */
  public double getYVelocity() {
    return yVelocity;
  }

  /**
   * Spawns its next bloons when shot at
   * @return an array of bloons that contains all the bloons to be spawned after bloon is hit
   * @throws ConfigurationException if a special bloon type is not recognized
   */
  @Override
  public Bloon[] shootBloon() throws ConfigurationException {
    int numBloonsToProduce = getBloonsType().chain().getNumNextBloons(bloonsType);

    Bloon[] bloons = new Bloon[numBloonsToProduce];
    BasicBloonsFactory factory = new BasicBloonsFactory();
    for (int i = 0; i < numBloonsToProduce; i++) {
      bloons[i] = factory.createNextBloon(this);
    }
    return bloons;
  }

  /**
   * sets bloon to be dead
   */
  @Override
  public void setDead() {
    setBloonsType(bloonsType.chain().getBloonsTypeRecord("DEAD"));
  }

  /**
   * checks if bloon is dead
   * @return true if the bloon is dead
   */
  @Override
  public boolean isDead() {
    return bloonsType.name().equals("DEAD");
  }

  /**
   * slows bloon down
   */
  public void slowDown() {
    slowDownActive = true;
    speedEffectFactor = Math.min(speedEffectFactor,
        Double.parseDouble(GAME_MECHANICS.getString("SlowDownSpeedFactor")));
  }

  /**
   * freezes bloon
   */
  public void freeze() {
    freezeActive = true;
    freezeTimer = 0;
    speedEffectFactor = 0;
  }

  /**
   * bloon type of bloon
   * @return string representing the bloon's bloontype
   */
  @Override
  public String toString() {
    return bloonsType.name();
  }

  /**
   * returns total distance traveled by bloon
   * @return double representing total distance traveled by bloon
   */
  public double getDistanceTraveled() {
    return distanceTraveled;
  }

  /**
   * sets distance traveled of bloon
   * @param distanceTraveled distance traveled
   */
  public void setDistanceTraveled(double distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
  }

  /**
   * gets bloon type of bloon
   * @return bloon type of bloon
   */
  public BloonsType getBloonsType() {
    return bloonsType;
  }

  /**
   * gets if freeze effect is active
   * @return true if freeze is active
   */
  public boolean isFreezeActive() {
    return freezeActive;
  }

  /**
   * gets if slow down effect is active
   * @return true if slow down is active
   */
  public boolean isSlowDownActive() {
    return slowDownActive;
  }

  /**
   * sets bloonstype of bloon
   * @param type bloonstype to be set to
   */
  protected void setBloonsType(BloonsType type) {
    bloonsType = type;
  }

  private void updateDistanceTraveled() {
    distanceTraveled +=
        (Math.abs(xVelocity) + Math.abs(yVelocity)) * relativeSpeed * speedEffectFactor;
  }

  private void updatePosition() {
    setXPosition(getXPosition() + xVelocity * relativeSpeed * speedEffectFactor);
    setYPosition(getYPosition() + yVelocity * relativeSpeed * speedEffectFactor);
    updateDistanceTraveled();
  }

  private void updateEffects() {
    updateSlowDownEffect();
    updateFreezeEffect();
  }

  private void updateSlowDownEffect() {
    if (slowDownActive) {
      slowDownTimer++;
      if (slowDownTimer >= slowDownTimePeriod) {
        slowDownActive = false;
        slowDownTimer = 0;
        speedEffectFactor = 1;
      }
    }
  }

  private void updateFreezeEffect() {
    if (freezeActive) {
      freezeTimer++;
      if (freezeTimer >= freezeTimePeriod) {
        freezeActive = false;
        speedEffectFactor = 1;
      }
    }
  }


}
