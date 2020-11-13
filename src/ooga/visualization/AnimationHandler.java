package ooga.visualization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.layout.Layout;
import ooga.backend.layout.LayoutBlock;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;

public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double SPEED = 3;

  private Timeline myAnimation = new Timeline();
  private Layout myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private Circle myTestCircle;

  private TowersCollection myTowers = new TowersCollection();
  private Map<Tower, Node> myTowersInGame = new HashMap<>();
  private BloonsCollection myBloons;
  private Map<Bloon, Node> myBloonsInGame = new HashMap<>();
  private BloonsFactory myBloonsFactory;
  private ProjectilesCollection myProjectiles = new ProjectilesCollection();
  private Map<Projectile, Node> myProjectilesInGame = new HashMap<>();
  private ProjectileFactory myProjectileFactory = new SingleProjectileFactory();

  private double myCircleSideX;
  private double myCircleSideY;

  public AnimationHandler(Layout layout, Group levelLayout, BloonsCollection bloons, double startingX,
      double startingY, double blockSize) {
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;
    myBloons = bloons;
    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myTestCircle = new Circle(myStartingX, myStartingY, myBlockSize / 2.5, Color.RED);
    myTestCircle.setId("TestCircle");
    myLevelLayout.getChildren().add(myTestCircle);

//    Bloon testBloon = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), myTestCircle.getCenterX(),
//        myTestCircle.getCenterY(), 0, 0);
//    myBloons.add(testBloon);
//    myBloonsInGame.put(testBloon, myTestCircle);
  }

  public void animate() {
    animateBloons();
    animateTowers();
    animateProjectiles();
  }

  // TODO: Refactor
  private void animateBloons() {
    GamePieceIterator<Bloon> bloonsIterator = myBloons.createIterator();


    while(bloonsIterator.hasNext()) {
      Bloon currentBloon = bloonsIterator.next();
      LayoutBlock currentBlock = myLayout.getBlock(((int) ((myTestCircle.getCenterY() + myCircleSideY) / myBlockSize))
          ,((int) ((myTestCircle.getCenterX() + myCircleSideX) / myBlockSize)));
      if (currentBlock.isEndBlock()) {
        myLevelLayout.getChildren().remove(myTestCircle);
        myBloons.remove(currentBloon);
      }
      currentBloon.setXVelocity(currentBloon.getBloonsType().relativeSpeed() * currentBlock.getDx()/10);
      currentBloon.setYVelocity(currentBloon.getBloonsType().relativeSpeed() * currentBlock.getDy()/10);

      myTestCircle.setCenterX(myTestCircle.getCenterX() + currentBloon.getXVelocity());
      myTestCircle.setCenterY(myTestCircle.getCenterY() + currentBloon.getYVelocity());

      setCircleSides(currentBlock);

      myBloons.updateAll();
    }
  }

  private void setCircleSides(LayoutBlock currentBlock) {
    myCircleSideX = -myBlockSize * currentBlock.getDx() / 2;
    myCircleSideY = -myBlockSize * currentBlock.getDy() / 2;
  }

  private void animateTowers() {
    GamePieceIterator<Tower> towersIterator = myTowers.createIterator();
    GamePieceIterator<Bloon> bloonsIterator = myBloons.createIterator();
    while (towersIterator.hasNext()) {
      Tower currentTower = towersIterator.next();
      while (bloonsIterator.hasNext()) {
        Bloon currentBloon = bloonsIterator.next();
        if (currentTower.getDistance(currentBloon) <= currentTower.getRadius() * myBlockSize) {
          rotateTower(currentBloon, currentTower);
          attemptToFire(currentBloon, currentTower);
        }
      }
      bloonsIterator.reset();
    }
    myTowers.updateAll();
  }

  private void rotateTower(Bloon bloon, Tower tower) {
    Node towerInGame = myTowersInGame.get(tower);
    double angle = Math.toDegrees(
        Math.asin((bloon.getXPosition() - tower.getXPosition()) / tower.getDistance(bloon)));
    if (bloon.getYPosition() < tower.getYPosition()) {
      towerInGame.setRotate(angle);
    } else {
      towerInGame.setRotate(180 - angle);
    }
  }

  private void attemptToFire(Bloon bloon, Tower tower) {
    if (tower.getCanShoot()) {
      double projectileXSpeed =
          tower.getShootingSpeed() * (bloon.getXPosition() - tower.getXPosition()) / tower
              .getDistance(bloon);
      double projectileYSpeed =
          tower.getShootingSpeed() * (bloon.getYPosition() - tower.getYPosition()) / tower
              .getDistance(bloon);
      Projectile newProjectile = myProjectileFactory
          .createDart(ProjectileType.SingleTargetProjectile, tower.getXPosition(),
              tower.getYPosition(), projectileXSpeed, projectileYSpeed);
      Circle projectileInGame = new Circle(newProjectile.getXPosition(),
          newProjectile.getYPosition(), myBlockSize / 8);
      myLevelLayout.getChildren().add(projectileInGame);
      myProjectiles.add(newProjectile);
      myProjectilesInGame.put(newProjectile, projectileInGame);
    }
  }

  private void animateProjectiles() {
    GamePieceIterator<Projectile> projectilesIterator = myProjectiles.createIterator();
    GamePieceIterator<Bloon> bloonsIterator = myBloons.createIterator();
    while (projectilesIterator.hasNext()) {
      Projectile projectile = projectilesIterator.next();
      Circle projectileInGame = (Circle) myProjectilesInGame.get(projectile);
      projectileInGame.setCenterX(projectileInGame.getCenterX() + projectile.getXVelocity());
      projectileInGame.setCenterY(projectileInGame.getCenterY() + projectile.getYVelocity());
      while (bloonsIterator.hasNext()) {
        Bloon bloon = bloonsIterator.next();
        if (checkBloonCollision(projectile, bloon)) {
          myLevelLayout.getChildren().remove(myProjectilesInGame.remove(projectile));
          myLevelLayout.getChildren().remove(myBloonsInGame.remove(bloon));
          myProjectiles.remove(projectile);
          myBloons.remove(bloon);
        }
      }
      if (checkOutOfBoundsProjectile(projectileInGame)) {
        myLevelLayout.getChildren().remove(projectileInGame);
      }
      bloonsIterator.reset();
    }
    myProjectiles.updateAll();
  }

  private boolean checkOutOfBoundsProjectile(Circle projectile) {
    return projectile.getCenterX() <= 0
        || projectile.getCenterX() >= BloonsApplication.GAME_WIDTH
        || projectile.getCenterY() <= 0
        || projectile.getCenterY() >= BloonsApplication.GAME_HEIGHT;
  }

  private boolean checkBloonCollision(Projectile projectile, Bloon bloon) {
    Circle projectileInGame = (Circle) myProjectilesInGame.get(projectile);
    Circle bloonInGame = (Circle) myBloonsInGame.get(bloon);
    //return projectileInGame.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
    return false;
  }

  public void addTower(Tower tower, Node towerInGame) {
    myTowers.add(tower);
    myTowersInGame.put(tower, towerInGame);
//    myLevelLayout.getChildren().add(towerInGame);
  }

  public void removeTower(Node towerInGame) {
    myLevelLayout.getChildren().remove(towerInGame);
  }

  public void setBloonWave(BloonsCollection bloonWave) {
    myBloons = bloonWave;
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

}
