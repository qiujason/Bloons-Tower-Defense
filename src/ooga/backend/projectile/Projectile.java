package ooga.backend.projectile;
import ooga.backend.GamePiece;

public abstract class Projectile extends GamePiece {

  private double xVelocity;
  private double yVelocity;
  private double angle;
  private ProjectileType type;



  public Projectile(ProjectileType type, double xPosition, double yPosition, double xVelocity, double yVelocity, double angle){
    super(xPosition, yPosition);
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.angle = angle;
    this.type = type;
  }

  public ProjectileType getType(){
    return type;
  }

  public double getRadius(){
    return type.getRadius();
  }

  private void updatePosition() {
    setXPosition(getXPosition() + xVelocity);
    setYPosition(getYPosition() + yVelocity);
  }

  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
  }

  @Override
  public void update() {
    updatePosition();
  }

  public double getXVelocity(){
    return xVelocity;
  }

  public double getYVelocity(){
    return yVelocity;
  }

  public double getAngle() {
    return angle;
  }

}
