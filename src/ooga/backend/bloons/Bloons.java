package ooga.backend.bloons;


import ooga.backend.API.BloonsAPI;
import ooga.backend.API.GamePiece;

public class Bloons implements BloonsAPI, GamePiece {

  private int lives;
  private double xPosition;
  private double yPosition;
  private double xVelocity;
  private double yVelocity;
  private double distanceTraveled;

  public Bloons(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    this.lives = lives;
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    distanceTraveled = 0;
  }

  @Override
  public void decrementLives(int hits) {
    lives -= hits;
    if (lives <= 0) {
      handleDeath();
    }
  }

  public void handleDeath() {

  }

  @Override
  public void updateLivesLeft(int update) {
    lives = update;
  }

  public int getLives() {
    return lives;
  }

  @Override
  public void updatePosition() {
    xPosition += xVelocity;
    yPosition += yVelocity;
    updateDistanceTraveled();
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

  @Override
  public void setXPosition(int updateXPos) {
    xPosition = updateXPos;
  }

  @Override
  public void setYPosition(int updateYPos) {
    yPosition = updateYPos;
  }

  public int getDistanceTraveled() {
    return distanceTraveled;
  }

  private void updateDistanceTraveled() {
    distanceTraveled += Math.abs(xVelocity) + Math.abs(yVelocity);
  }

}
