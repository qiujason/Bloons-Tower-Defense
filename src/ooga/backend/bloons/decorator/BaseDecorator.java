package ooga.backend.bloons.decorator;

import ooga.backend.API.BloonsAPI;
import ooga.backend.API.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.towers.TowerType;

public class BaseDecorator implements BloonsAPI, GamePiece {

  protected final Bloon bloonWrappee;

  public BaseDecorator(Bloon bloon) {
    bloonWrappee = bloon;
  }

  @Override
  public void setXPosition(double updateXPos) {
    bloonWrappee.setXPosition(updateXPos);
  }

  @Override
  public void setYPosition(double updateYPos) {
    bloonWrappee.setYVelocity(updateYPos);
  }

  @Override
  public double getXPosition() {
    return bloonWrappee.getXPosition();
  }

  @Override
  public double getYPosition() {
    return bloonWrappee.getYPosition();
  }

  @Override
  public void update() {
    bloonWrappee.update();
  }

  @Override
  public void setXVelocity(double newXVelocity) {
    bloonWrappee.setXPosition(newXVelocity);
  }

  @Override
  public void setYVelocity(double newYVelocity) {
    bloonWrappee.setYVelocity(newYVelocity);
  }

  @Override
  public Bloon[] shootBloon(BloonsTypeChain bloonsTypeChain, TowerType towerCaller, int hits) {
    return bloonWrappee.shootBloon(bloonsTypeChain, towerCaller, hits);
  }

}
