package ooga.visualization.weapons;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class WeaponRange extends Circle {

  private static final Color OPAQUE = Color.rgb(255, 255, 255, 0.5);
  private static final Color OPAQUE_STROKE = Color.rgb(0, 0, 0, 0.5);
  private static final Color HIDDEN = Color.rgb(255, 255, 255, 0);
  private static final Color HIDDEN_STROKE = Color.rgb(0, 0, 0, 0);

  public WeaponRange(double xPosition, double yPosition, double hitRadius){
    super(xPosition, yPosition, hitRadius);
    this.setFill(OPAQUE);
    this.setStroke(OPAQUE_STROKE);
  }

  public void makeInvisible(){
    this.setFill(HIDDEN);
    this.setStroke(HIDDEN_STROKE);
  }

  public void makeVisible(){
    this.setFill(OPAQUE);
    this.setStroke(OPAQUE_STROKE);
  }

}
