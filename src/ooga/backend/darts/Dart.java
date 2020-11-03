package ooga.backend.darts;
import ooga.backend.API.Movable;

public abstract class Dart implements Movable {
  private int xPosition;
  private int yPosition;
  private double xVelocity;
  private double yVelocity;

  public Dart(int xPosition, int yPosition, double xVelocity, double yVelocity){
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
  }

  @Override
  public void updatePosition() {
    xPosition += xVelocity;
    yPosition += yVelocity;
  }

  @Override
  public void setXVelocity(int newXVelocity) {
    xVelocity = newXVelocity;
  }

  @Override
  public void setYVelocity(int newYVelocity) {
    yVelocity = newYVelocity;
  }

  @Override
  public int getXPosition() {
    return xPosition;
  }

  @Override
  public int getYPosition() {
    return yPosition;
  }

  public double getXVelocity(){
    return xVelocity;
  }

  public double getYVelocity(){
    return yVelocity;
  }
}
