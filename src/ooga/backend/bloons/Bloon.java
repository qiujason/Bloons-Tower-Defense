package ooga.backend.bloons;


import ooga.backend.API.BloonsAPI;
import ooga.backend.GamePiece;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;

public class Bloon extends GamePiece implements BloonsAPI {

  private final BloonsTypeChain chain;
  private BloonsType bloonsType;
  private double xVelocity;
  private double yVelocity;
  private double distanceTraveled;
  private double relativeSpeed;

  public Bloon(BloonsTypeChain chain, BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(xPosition, yPosition);
    this.chain = chain;
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
  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  @Override
  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
  }

  @Override
  public Bloon[] shootBloon() {
    BloonsType nextBloonsType = chain.getNextBloonsType(bloonsType);
    int numBloonsProduced = chain.getNumNextBloons(bloonsType);

    Bloon[] bloons = new Bloon[numBloonsProduced];
    BasicBloonsFactory factory = new BasicBloonsFactory();
    for (int i = 0; i < numBloonsProduced; i++) {
      bloons[i] = factory.createBloon(chain, nextBloonsType, getXPosition(), getYPosition(), xVelocity, yVelocity);
    }
    return bloons;
  }

  @Override
  public void update() {
    updatePosition();
  }

  public double getDistanceTraveled() {
    return distanceTraveled;
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public String toString(){
    return "" + bloonsType.name();
  }

  protected void setBloonsType(BloonsType type) {
    bloonsType = type;
  }

  protected BloonsTypeChain getChain() {
    return chain;
  }

  private void updateDistanceTraveled() {
    distanceTraveled += (Math.abs(xVelocity) + Math.abs(yVelocity)) * relativeSpeed;
  }

  private void updatePosition() {
    setXPosition(getXPosition() + xVelocity * relativeSpeed);
    setYPosition(getYPosition() + yVelocity * relativeSpeed);
    updateDistanceTraveled();
  }

}
