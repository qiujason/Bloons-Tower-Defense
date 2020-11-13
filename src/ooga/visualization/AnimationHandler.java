package ooga.visualization;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ooga.backend.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.bloons.collection.BloonsIterator;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.ProjectilesIterator;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.layout.Layout;
import ooga.backend.layout.LayoutBlock;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.TowersIterator;

public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double BLOON_SPAWN_DELAY = 1 * FRAMES_PER_SECOND;

  private Timeline myAnimation = new Timeline();
  private Layout myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;

  private TowersCollection myTowers = new TowersCollection();
  private Map<Tower, Node> myTowersInGame = new HashMap<>();
  private BloonsCollection myBloons;
  private BloonsCollection myWaitingBloons;
  private Map<Bloon, Node> myBloonsInGame = new HashMap<>();
  private ProjectilesCollection myProjectiles = new ProjectilesCollection();
  private Map<Projectile, Node> myProjectilesInGame = new HashMap<>();
  private ProjectileFactory myProjectileFactory = new SingleProjectileFactory();

  private Map<Bloon, Double> myCircleSidesX;
  private Map<Bloon, Double> myCircleSidesY;

  private int bloonSpawnDelay = 0;

  public AnimationHandler(Layout layout, Group levelLayout, BloonsCollection bloons,
      double startingX,
      double startingY, double blockSize) {
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;

    myBloons = new BloonsCollection();

    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myCircleSidesX = new HashMap<>();
    myCircleSidesY = new HashMap<>();

    // The following code is merely to test a starting chain of 5 balloons
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    myWaitingBloons = new BloonsCollection();
    for (int i = 0; i < 10; i++) {
      myWaitingBloons
          .add(new Bloon(chain.getBloonsTypeRecord("RED"), myStartingX, myStartingY, 0, 0));
    }

  }

  public void animate() {
    animateBloons();
    animateTowers();
    animateProjectiles();
    if (bloonSpawnDelay == BLOON_SPAWN_DELAY) {
      spawnBloon();
      bloonSpawnDelay = 0;
    } else {
      bloonSpawnDelay++;
    }
  }

  private void spawnBloon() {
    BloonsIterator bloonsIterator = (BloonsIterator) myWaitingBloons.createIterator();
    if (bloonsIterator.hasMore()) {
      Bloon bloonToSpawn = (Bloon) bloonsIterator.getNext();
      myBloons.add(bloonToSpawn);
      Circle myBloonInGame = new Circle(myStartingX, myStartingY, myBlockSize / 2.5, Color.RED);
      myBloonsInGame.put(bloonToSpawn, myBloonInGame);
      myLevelLayout.getChildren().add(myBloonInGame);
      myWaitingBloons.remove(bloonToSpawn);
    }
  }

  // TODO: Refactor
  private void animateBloons() {
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();

    while (bloonsIterator.hasMore()) {

      Bloon currentBloon = (Bloon) bloonsIterator.getNext();
      Circle currentBloonInGame = (Circle) myBloonsInGame.get(currentBloon);

      myCircleSidesX.putIfAbsent(currentBloon, 0.0);
      myCircleSidesY.putIfAbsent(currentBloon, 0.0);

      LayoutBlock currentBlock =
          myLayout.getBlock(
              ((int) ((currentBloonInGame.getCenterY() + myCircleSidesY.get(currentBloon))
                  / myBlockSize)),
              ((int) ((currentBloonInGame.getCenterX() + myCircleSidesX.get(currentBloon))
                  / myBlockSize)));
      if (currentBlock.isEndBlock()) {
        myLevelLayout.getChildren().remove(currentBloonInGame);
        myBloons.remove(currentBloon);
        myBloonsInGame.remove(currentBloon);
      }
      currentBloon
          .setXVelocity(currentBloon.getBloonsType().relativeSpeed() * currentBlock.getDx());
      currentBloon
          .setYVelocity(currentBloon.getBloonsType().relativeSpeed() * currentBlock.getDy());

      currentBloonInGame.setCenterX(currentBloonInGame.getCenterX() + currentBloon.getXVelocity());
      currentBloonInGame.setCenterY(currentBloonInGame.getCenterY() + currentBloon.getYVelocity());

      setCircleSides(currentBlock, currentBloon);
    }
    myBloons.updateAll();
  }

  private void setCircleSides(LayoutBlock currentBlock, Bloon currentBloon) {
    myCircleSidesX.put(currentBloon, -myBlockSize * currentBlock.getDx() / 2);
    myCircleSidesY.put(currentBloon, -myBlockSize * currentBlock.getDy() / 2);
  }

  private void animateTowers() {
    TowersIterator towersIterator = (TowersIterator) myTowers.createIterator();
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    while (towersIterator.hasMore()) {
      Tower currentTower = (Tower) towersIterator.getNext();
      while (bloonsIterator.hasMore()) {
        Bloon currentBloon = (Bloon) bloonsIterator.getNext();
        if (currentTower.getDistance(currentBloon) <= currentTower.getTowerType().getRadius()) {
          rotateTower(currentBloon, currentTower);
          attemptToFire(currentBloon, currentTower);
          break;
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

  // TODO: use shoot method
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
    ProjectilesIterator projectilesIterator = (ProjectilesIterator) myProjectiles.createIterator();
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    while (projectilesIterator.hasMore()) {
      Projectile projectile = (Projectile) projectilesIterator.getNext();
      Circle projectileInGame = (Circle) myProjectilesInGame.get(projectile);
      projectileInGame.setCenterX(projectileInGame.getCenterX() + projectile.getXVelocity());
      projectileInGame.setCenterY(projectileInGame.getCenterY() + projectile.getYVelocity());
      while (bloonsIterator.hasMore()) {
        Bloon bloon = (Bloon) bloonsIterator.getNext();
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
    return projectileInGame.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
    //return false;
  }

  public void addTower(GamePiece tower, Node towerInGame) {
    myTowers.add(tower);
    myTowersInGame.put((Tower) tower, towerInGame);
//    myLevelLayout.getChildren().add(towerInGame);
  }

  public void removeTower(Node towerInGame) {
    myLevelLayout.getChildren().remove(towerInGame);
  }

  public void setBloonWave(BloonsCollection bloonWave) {
    //myWaitingBloons = bloonWave;
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

}
