package ooga.backend.API;


public interface GamePiece {

  void setXPosition(double updateXPos);

  void setYPosition(double updateYPos);

  double getXPosition();

  double getYPosition();

  void update();

}
