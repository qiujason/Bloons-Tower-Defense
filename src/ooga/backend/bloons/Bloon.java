package ooga.backend.bloons;


import ooga.backend.API.BloonsAPI;
import ooga.backend.API.GamePiece;

public class Bloon implements BloonsAPI, GamePiece {

  private BloonsType bloonsType;
  private double xPosition;
  private double yPosition;

  private double xVelocity;
  private double yVelocity;
  private double distanceTraveled;
  private double relativeSpeed;

  public Bloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    this.bloonsType = bloonsType;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    distanceTraveled = 0;
    relativeSpeed = bloonsType.getRelativeSpeed();
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
  public double getXPosition() {
    return xPosition;
  }

  @Override
  public double getYPosition() {
    return yPosition;
  }

  @Override
  public void update() {
    updatePosition();
  }

  @Override
  public void setXPosition(double updateXPos) {
    xPosition = updateXPos;
  }

  @Override
  public void setYPosition(double updateYPos) {
    yPosition = updateYPos;
  }

  public double getDistanceTraveled() {
    return distanceTraveled;
  }

  private void updateDistanceTraveled() {
    distanceTraveled += Math.abs(xVelocity) + Math.abs(yVelocity);
  }

  private void updatePosition() {
    xPosition += xVelocity;
    yPosition += yVelocity;
    updateDistanceTraveled();
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public String toString(){
    return "" + bloonsType.ordinal();
  }

}
