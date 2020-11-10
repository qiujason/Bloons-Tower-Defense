package ooga.visualization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ooga.backend.API.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsIterator;
import ooga.backend.bloons.BloonsType;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.ProjectilesIterator;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.TowersIterator;

public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double SPEED = 3;
  public static final double PROJECTILE_SPEED = 10;

  private Timeline myAnimation = new Timeline();
  private List<List<String>> myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private Circle myTestCircle;

  private TowersCollection myTowers = new TowersCollection();
  private Map<Tower, Node> myTowersInGame = new HashMap<>();
  private BloonsCollection myBloons = new BloonsCollection();
  private Map<Bloon, Node> myBloonsInGame = new HashMap<>();
  private BloonsFactory myBloonsFactory;
  private ProjectilesCollection myProjectiles = new ProjectilesCollection();
  private Map<Projectile, Node> myProjectilesInGame = new HashMap<>();
  private ProjectileFactory myProjectileFactory = new SingleProjectileFactory();

  private double myCircleSideX;
  private double myCircleSideY;

  public AnimationHandler(List<List<String>> layout, Group levelLayout, double startingX,
      double startingY, double blockSize) {
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;
    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myTestCircle = new Circle(myStartingX, myStartingY, myBlockSize / 2.5, Color.RED);
    myTestCircle.setId("TestCircle");
    myLevelLayout.getChildren().add(myTestCircle);

    Bloon testBloon = new Bloon(BloonsType.RED, myTestCircle.getCenterX(),
        myTestCircle.getCenterY(), 0, 0);
    myBloons.add(testBloon);
    myBloonsInGame.put(testBloon, myTestCircle);
  }

  private void animate() {
    animateBloons();
    animateTowers();
    animateProjectiles();
  }

  // TODO: Refactor
  private void animateBloons() {
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    String currentBlockString = myLayout
        .get((int) ((myTestCircle.getCenterY() + myCircleSideY) / myBlockSize))
        .get((int) ((myTestCircle.getCenterX() + myCircleSideX) / myBlockSize));
    while (bloonsIterator.hasMore()) {
      Bloon currentBloon = (Bloon) bloonsIterator.getNext();

      switch(currentBlockString) {
        case "*", ">" -> {
          currentBloon.setXVelocity(SPEED);
          currentBloon.setYVelocity(0);
          myTestCircle.setCenterX(myTestCircle.getCenterX() + SPEED);
          myCircleSideX = -myBlockSize / 2;
          myCircleSideY = 0;
        }
        case "<" -> {
          currentBloon.setXVelocity(-SPEED);
          currentBloon.setYVelocity(0);
          myTestCircle.setCenterX(myTestCircle.getCenterX() - SPEED);
          myCircleSideX = myBlockSize / 2;
          myCircleSideY = 0;
        }
        case "v" -> {
          currentBloon.setXVelocity(0);
          currentBloon.setYVelocity(SPEED);
          myTestCircle.setCenterY(myTestCircle.getCenterY() + SPEED);
          myCircleSideX = 0;
          myCircleSideY = -myBlockSize / 2;
        }
        case "^" -> {
          currentBloon.setXVelocity(0);
          currentBloon.setYVelocity(-SPEED);
          myTestCircle.setCenterY(myTestCircle.getCenterY() - SPEED);
          myCircleSideX = 0;
          myCircleSideY = myBlockSize / 2;
        }
        case "@" -> myLevelLayout.getChildren().remove(myTestCircle);
      }
    }
    myBloons.updateAll();
  }

  private void animateTowers() {
    TowersIterator towersIterator = (TowersIterator) myTowers.createIterator();
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    while (towersIterator.hasMore()) {
      Tower currentTower = (Tower) towersIterator.getNext();
      while (bloonsIterator.hasMore()) {
        Bloon currentBloon = (Bloon) bloonsIterator.getNext();
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
      double projectileXSpeed = PROJECTILE_SPEED * tower.getDistance(bloon) / (bloon.getXPosition() - tower.getXPosition());
      double projectileYSpeed = PROJECTILE_SPEED * tower.getDistance(bloon) / (bloon.getYPosition() - tower.getYPosition());
      Projectile newProjectile = myProjectileFactory
          .createDart(ProjectileType.SingleTargetProjectile, tower.getXPosition(),
              tower.getYPosition(), projectileXSpeed, projectileYSpeed);
      Circle projectileInGame = new Circle(newProjectile.getXPosition(), newProjectile.getYPosition(), myBlockSize / 3);
      myLevelLayout.getChildren().add(projectileInGame);
      myProjectilesInGame.put(newProjectile, projectileInGame);
    }
  }

  private void animateProjectiles() {
    ProjectilesIterator projectilesIterator = (ProjectilesIterator) myProjectiles.createIterator();
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    while(projectilesIterator.hasMore()){
      Projectile projectile = (Projectile) projectilesIterator.getNext();
      Circle projectileInGame = (Circle) myProjectilesInGame.get(projectile);
      projectileInGame.setCenterX(projectileInGame.getCenterX() + projectile.getXVelocity());
      projectileInGame.setCenterY(projectileInGame.getCenterY() + projectile.getYVelocity());
      while(bloonsIterator.hasMore()){
        Bloon bloon = (Bloon) bloonsIterator.getNext();
        if(checkBloonCollision(projectile, bloon)){
          myLevelLayout.getChildren().remove(myProjectilesInGame.remove(projectile));
          myProjectiles.remove(projectile);
        }
      }
      bloonsIterator.reset();
    }
    myProjectiles.updateAll();
  }

  private boolean checkBloonCollision(Projectile projectile, Bloon bloon) {
    return false;
  }

  public void addTower(GamePiece tower, Node towerInGame) {
    myTowers.add(tower);
    myTowersInGame.put((Tower) tower, towerInGame);
    myLevelLayout.getChildren().add(towerInGame);
  }

  public void removeTower(Node towerInGame) {
    myLevelLayout.getChildren().remove(towerInGame);
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

}
