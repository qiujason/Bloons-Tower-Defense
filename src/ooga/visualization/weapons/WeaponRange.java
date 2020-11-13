package ooga.visualization.weapons;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class WeaponRange extends Circle {

  private double xPosition;
  private double yPosition;
  private double hitRadius;
  private static final Color OPAQUE = Color.rgb(255, 255, 255, 0.5);
  private static final Color HIDDEN = Color.rgb(255, 255, 255, 0);

  public WeaponRange(double xPosition, double yPosition, double hitRadius){
    super(xPosition, yPosition, hitRadius);
    this.setFill(OPAQUE);

  }

  public double getXPosition() {
    return xPosition;
  }

  public void setXPosition(double xPosition) {
    this.xPosition = xPosition;
  }
  public double getYPosition() {
    return yPosition;
  }

  public void setYPosition(double yPosition) {
    this.yPosition = yPosition;
  }

  public double getHitRadius() {
    return hitRadius;
  }

  public void setHitRadius(double hitRadius) {
    this.hitRadius = hitRadius;
  }

  public void makeInvisible(){
    this.setFill(HIDDEN);
  }

  public void makeVisible(){
    this.setFill(OPAQUE);
  }

}
