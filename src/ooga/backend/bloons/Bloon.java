package ooga.backend.bloons;


import java.util.ResourceBundle;
import ooga.backend.API.BloonsAPI;
import ooga.backend.GamePiece;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.types.BloonsType;

public class Bloon extends GamePiece implements BloonsAPI {

  public static final String RESOURCE_BUNDLE_PATH = "bloon_resources/GameMechanics";
  private static final ResourceBundle GAME_MECHANICS = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);

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

  public Bloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(xPosition, yPosition);
    this.bloonsType = bloonsType;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.distanceTraveled = 0;
    this.relativeSpeed = bloonsType.relativeSpeed();

    this.freezeTimePeriod = Integer.parseInt(GAME_MECHANICS.getString("FreezeTimePeriod"));
    this.freezeTimer = 0;
    this.freezeActive = false;
    this.slowDownTimePeriod = Integer.parseInt(GAME_MECHANICS.getString("SlowDownTimePeriod"));
    this.slowDownTimer = 0;
    this.slowDownActive = false;
    this.speedEffectFactor = 1;
  }

  public BloonsType getBloonsType(){
    return bloonsType;
  }

  @Override
  public void update() {
    updatePosition();
    updateSlowDownEffect();
    updateFreezeEffect();
  }

  @Override
  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  @Override
  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public Bloon[] shootBloon() {
    int numBloonsToProduce = getBloonsType().chain().getNumNextBloons(bloonsType);

    Bloon[] bloons = new Bloon[numBloonsToProduce];
    BasicBloonsFactory factory = new BasicBloonsFactory();
    for (int i = 0; i < numBloonsToProduce; i++) {
      bloons[i] = factory.createNextBloon(this);
    }
    return bloons;
  }

  @Override
  public void setDead(){
    setBloonsType(bloonsType.chain().getBloonsTypeRecord("DEAD"));
  }

  @Override
  public boolean isDead(){
    return bloonsType == bloonsType.chain().getBloonsTypeRecord("DEAD");
  }

  public void slowDown() {
    slowDownActive = true;
    speedEffectFactor = Math.min(speedEffectFactor, Double.parseDouble(GAME_MECHANICS.getString("SlowDownSpeedFactor")));
  }

  public void freeze() {
    freezeActive = true;
    speedEffectFactor = 0;
  }

  @Override
  public String toString(){
    return bloonsType.name();
  }

  public void setDistanceTraveled(double distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
  }

  public double getDistanceTraveled() {
    return distanceTraveled;
  }

  protected void setBloonsType(BloonsType type) {
    bloonsType = type;
  }

  private void updateDistanceTraveled() {
    distanceTraveled += (Math.abs(xVelocity) + Math.abs(yVelocity)) * relativeSpeed * speedEffectFactor;
  }

  private void updatePosition() {
    setXPosition(getXPosition() + xVelocity * relativeSpeed * speedEffectFactor);
    setYPosition(getYPosition() + yVelocity * relativeSpeed * speedEffectFactor);
    updateDistanceTraveled();
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
        freezeTimer = 0;
        speedEffectFactor = 1;
      }
    }
  }

}
