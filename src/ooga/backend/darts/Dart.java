package ooga.backend.darts;

import ooga.backend.API.Movable;

public abstract class Dart implements Movable {
  private int xPosition;
  private int yPosition;
  private int xVelocity;
  private int yVelocity;

  public Dart(int xPosition, int yPosition, int xVelocity, int yVelocity){
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
}
