package ooga.backend;

import ooga.backend.API.GamePieceAPI;

public abstract class GamePiece implements GamePieceAPI {

  private double xPosition;
  private double yPosition;

  public GamePiece(double xPosition, double yPosition) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
  }

  @Override
  public void setXPosition(double updateXPos) {
    xPosition = updateXPos;
  }

  @Override
  public void setYPosition(double updateYPos) {
    yPosition = updateYPos;
  }

  @Override
  public double getXPosition() {
    return xPosition;
  }

  @Override
  public double getYPosition() {
    return yPosition;
  }

}
