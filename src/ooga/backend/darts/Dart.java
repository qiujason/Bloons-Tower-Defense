package ooga.backend.darts;
import ooga.backend.API.GamePiece;

public abstract class Dart implements GamePiece {
  private double xPosition;
  private double yPosition;
  private double xVelocity;
  private double yVelocity;

  public Dart(double xPosition, double yPosition, double xVelocity, double yVelocity){
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
  }

  public void updatePosition() {
    xPosition += xVelocity;
    yPosition += yVelocity;
  }

  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
  }

  @Override
  public void setXPosition(double updateXPos) {

  }

  @Override
  public void setYPosition(double updateYPos) {

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

  }

  public double getXVelocity(){
    return xVelocity;
  }

  public double getYVelocity(){
    return yVelocity;
  }

}
