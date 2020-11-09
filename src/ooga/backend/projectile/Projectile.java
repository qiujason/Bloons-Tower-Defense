package ooga.backend.projectile;
import ooga.backend.API.GamePiece;

public abstract class Projectile implements GamePiece {
  private double xPosition;
  private double yPosition;
  private double xVelocity;
  private double yVelocity;
  private ProjectileType type;

  public Projectile(ProjectileType type, double xPosition, double yPosition, double xVelocity, double yVelocity){
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.type = type;
  }

  public ProjectileType getType(){
    return type;
  }

  public double getRadius(){
    return type.getRadius();
  }

  public void updatePosition() {
    xPosition += xVelocity;
    yPosition += yVelocity;
  }

  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
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

  @Override
  public void update() {

  }

  public double getXVelocity(){
    return xVelocity;
  }

  public double getYVelocity(){
    return yVelocity;
  }

}
