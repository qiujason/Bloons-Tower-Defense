package ooga.visualization.nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Abstract class that initializes a node for a each game piece in the game. The class extends the
 * JavaFX Circle class to visualize the game pieces. Acts as the parent class for a game piece node
 * subclasses.
 */

public abstract class GamePieceNode extends Circle {

  private double xPosition;
  private double yPosition;
  private final double radius;

  public static String BLOON_IMAGES_PATH = "bloon_resources/BloonImages";
  public static final String PROJECTILE_IMAGES_PATH = "projectile_resources/ProjectileImages";
  private static final String ROADITEM_IMAGES_PATH = "btd_towers/RoadItems";

  private Map<String, ResourceBundle> imageResourceMap;

  /**
   * Creates an instance of the GamePieceNode class
   * @param xPosition the x coordinate of the node
   * @param yPosition the y coordinate of the node
   * @param radius the radius of the ndoe
   */
  public GamePieceNode(double xPosition, double yPosition, double radius) {
    super(xPosition, yPosition, radius);
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.radius = radius;
  }

  /**
   * Sets the x-coordinate of the node to the given x-coordinate
   * @param xPos the new x-coordinate to be set
   */
  public void setXPosition(double xPos) {
    this.setCenterX(xPos);
    this.xPosition = xPos;
  }

  /**
   * Sets the y-coordinate of the node to the given y-coordinate
   * @param yPos the new x-coordinate to be set
   */
  public void setYPosition(double yPos) {
    this.setCenterY(yPos);
    this.yPosition = yPos;
  }

  /**
   * Abstract method that finds the image for each type of game node and returns an
   * ImagePattern to be set on the Node.
   * @return an ImagePattern based on the appropriate image.
   */
  public abstract ImagePattern findImage();

  /**
   * Returns the x-coordinate of the node
   * @return
   */
  public double getXPosition() {
    return xPosition;
  }

  /**
   * Returns the y-coordinate of the node
   * @return
   */
  public double getYPosition() {
    return yPosition;
  }

  //not used, ignore
  private void initializeImageResourceMap() {
    imageResourceMap = new HashMap<>();
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));

  }
}
