package ooga.backend.API;

/**
 * API that allows for communication with game pieces
 *
 * @author Jason Qiu
 */
public interface GamePieceAPI {

  /**
   * updates x position of game piece
   * @param updateXPos x position to be updated to
   */
  void setXPosition(double updateXPos);

  /**
   * updates y position of game piece
   * @param updateYPos y position to be updated to
   */
  void setYPosition(double updateYPos);

  /**
   * return game piece's x position
   * @return double representing the game piece's current x position
   */
  double getXPosition();

  /**
   * return game piece's y position
   * @return double representing the game piece's current y position
   */
  double getYPosition();

  /**
   * updates the game piece
   */
  void update();

}
