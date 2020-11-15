package ooga.visualization.nodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public abstract class GamePieceNode extends Circle {

  private double xPosition;
  private double yPosition;
  private double radius;

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

  public abstract ImagePattern findTowerImage();

  public double getXPosition() {
    return xPosition;
  }

  public double getYPosition() {
    return yPosition;
  }
}
