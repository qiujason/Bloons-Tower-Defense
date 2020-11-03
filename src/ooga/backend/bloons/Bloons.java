package ooga.backend.bloons;

import ooga.backend.API.Hittable;
import ooga.backend.API.Moveable;

public abstract class Bloons implements Moveable, Hittable {

  private int lives;
  private int xPosition;
  private int yPosition;
  private int xVelocity;
  private int yVelocity;

  public Bloons(int lives, int xPosition, int yPosition, int xVelocity, int yVelocity) {
    this.lives = lives;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
  }

  @Override
  public void decrementLives(int hits) {
    lives -= hits;
    if (lives <= 0) {
      handleDeath();
    }
  }

  public abstract void handleDeath();

  @Override
  public void updateLivesLeft(int update) {
    lives = update;
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
