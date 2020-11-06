package ooga.backend.darts;
import ooga.backend.API.GamePiece;

public abstract class Dart implements GamePiece {
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

  public void updatePosition() {
    xPosition += xVelocity;
    yPosition += yVelocity;
  }

  public void setXVelocity(int newXVelocity) {
    xVelocity = newXVelocity;
  }

  public void setYVelocity(int newYVelocity) {
    yVelocity = newYVelocity;
  }

  @Override
  public void setXPosition(int updateXPos) {

  }

  @Override
  public void setYPosition(int updateYPos) {

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
