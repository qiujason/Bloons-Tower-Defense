package ooga.visualization.nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public abstract class GamePieceNode extends Circle {

  private double xPosition;
  private double yPosition;
  private double radius;

  public static String BLOON_IMAGES_PATH = "bloon_resources/BloonImages";
  public static final String PROJECTILE_IMAGES_PATH = "projectile_resources/ProjectileImages";
  private static final String ROADITEM_IMAGES_PATH = "btd_towers/RoadItems";

  private Map<String, ResourceBundle> imageResourceMap;

  public GamePieceNode(double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.radius = radius;
  }

  public void setXPosition(double xPos){
    this.setCenterX(xPos);
    this.xPosition = xPos;
  }

  public void setYPosition(double yPos){
    this.setCenterY(yPos);
    this.yPosition = yPos;
  }

  public abstract ImagePattern findImage();

  public double getXPosition() {
    return xPosition;
  }

  public double getYPosition() {
    return yPosition;
  }

  private void initializeImageResourceMap(){
    imageResourceMap = new HashMap<>();
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));
    imageResourceMap.put("", ResourceBundle.getBundle(BLOON_IMAGES_PATH));

  }
}
