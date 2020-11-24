package ooga.visualization.nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class is the weapon range of the tower seen in the front-end
 */
public class WeaponRange extends Circle {

  private static final Color OPAQUE = Color.rgb(255, 255, 255, 0.5);
  private static final Color OPAQUE_STROKE = Color.rgb(0, 0, 0, 0.5);
  private static final Color HIDDEN = Color.rgb(255, 255, 255, 0);
  private static final Color HIDDEN_STROKE = Color.rgb(0, 0, 0, 0);
  private static final Color INVALID = Color.rgb(255, 77, 77, 0.5);

  /**
   * Constructor for the WeaponRange which is made with the given radius at the same x and y position
   * as the tower it is displaying the range for
   *
   * @param xPosition x position of the tower
   * @param yPosition y position of the tower
   * @param hitRadius the radius of the tower's range
   */
  public WeaponRange(double xPosition, double yPosition, double hitRadius) {
    super(xPosition, yPosition, hitRadius);
    this.setFill(OPAQUE);
    this.setStroke(OPAQUE_STROKE);
  }

  /**
   * Makes the range invisible by setting the opacity to 0
   */
  public void makeInvisible() {
    this.setFill(HIDDEN);
    this.setStroke(HIDDEN_STROKE);
  }

  /**
   * Makes the range visible by setting the opacity to 0.5
   */
  public void makeVisible() {
    this.setFill(OPAQUE);
    this.setStroke(OPAQUE_STROKE);
  }

  /**
   * Makes the weapon range red for when the tower is being hovered on a part of the map that it can't
   * be placed on
   */
  public void invalidPlacement() {
    this.setFill(INVALID);
  }

  /**
   * Makes the weapon range normally visible for when the tower is hovered on a part of the map that
   * it can be placed on
   */
  public void validPlacement() {
    makeVisible();
  }

}
