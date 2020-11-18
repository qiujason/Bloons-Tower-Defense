package ooga.backend.bloons;


import ooga.backend.API.BloonsAPI;
import ooga.backend.GamePiece;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

public class Bloon extends GamePiece implements BloonsAPI {

  private BloonsType bloonsType;
  private double xVelocity;
  private double yVelocity;
  private double distanceTraveled;
  private final double relativeSpeed;

  public Bloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(xPosition, yPosition);
    this.bloonsType = bloonsType;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.distanceTraveled = 0;
    this.relativeSpeed = bloonsType.relativeSpeed();
  }

  public BloonsType getBloonsType(){
    return bloonsType;
  }

  @Override
  public void update() {
    updatePosition();
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

  @Override
  public String toString(){
    return "" + bloonsType.name();
  }

  protected double getDistanceTraveled() {
    return distanceTraveled;
  }

  protected void setBloonsType(BloonsType type) {
    bloonsType = type;
  }

  private void updateDistanceTraveled() {
    distanceTraveled += (Math.abs(xVelocity) + Math.abs(yVelocity)) * relativeSpeed;
  }

  private void updatePosition() {
    setXPosition(getXPosition() + xVelocity * relativeSpeed);
    setYPosition(getYPosition() + yVelocity * relativeSpeed);
    updateDistanceTraveled();
  }

  public boolean isCamo(){
    return getBloonsType().specials().contains(Specials.CAMO);
  }

}
