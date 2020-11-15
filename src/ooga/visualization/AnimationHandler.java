package ooga.visualization;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.layout.Layout;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.nodes.BloonNode;
import ooga.visualization.nodes.GamePieceNode;
import ooga.visualization.nodes.TowerNode;


public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double BLOON_SPAWN_DELAY = 1 * FRAMES_PER_SECOND;

  private Timeline myAnimation;
  private Layout myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;

  private TowersCollection myTowers = new TowersCollection();
  private Map<Tower, TowerNode> myTowersInGame = new HashMap<>();
  private BloonsCollection myBloons;
  private Map<Bloon, BloonNode> myBloonsInGame = new HashMap<>();
  private ProjectilesCollection myProjectiles = new ProjectilesCollection();
  private Map<Projectile, Node> myProjectilesInGame = new HashMap<>();
  private ProjectileFactory myProjectileFactory = new SingleProjectileFactory();

  private Map<BloonNode, Double> myCircleSidesX;
  private Map<BloonNode, Double> myCircleSidesY;


  public AnimationHandler(Layout layout, Group levelLayout, BloonsCollection bloons, double startingX,
      double startingY, double blockSize, Timeline animation) {
    myAnimation = animation;

    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;

    myBloons = bloons;

    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myCircleSidesX = new HashMap<>();
    myCircleSidesY = new HashMap<>();

  }

  public void addBloonstoGame(){
    GamePieceIterator<Bloon> iterator = myBloons.createIterator();
    while(iterator.hasNext()) {
      Bloon bloonToSpawn = iterator.next();
      if (!myBloonsInGame.containsKey(bloonToSpawn)) {
        BloonNode bloonNode = new BloonNode(bloonToSpawn.getBloonsType(), myStartingX, myStartingY, myBlockSize / 2.5);
        myBloonsInGame.put(bloonToSpawn, bloonNode);
        myLevelLayout.getChildren().add(bloonNode);
      }
    }
  }

  public void animate() {
    animateTowers();
    animateProjectiles();
    animateBloons();
  }


  private void animateBloons() {
    addBloonstoGame();
    for(Bloon bloon : myBloonsInGame.keySet()){
      BloonNode bloonNode = myBloonsInGame.get(bloon);
      if (bloon.isDead()){
        myLevelLayout.getChildren().remove(bloonNode);
      }
      myCircleSidesX.putIfAbsent(bloonNode, 0.0);
      myCircleSidesY.putIfAbsent(bloonNode, 0.0);
      bloonNode.setXPosition(bloon.getXPosition() * myBlockSize);
      bloonNode.setYPosition(bloon.getYPosition() * myBlockSize);
    }
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
    if (tower.canShoot()) {
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
    return projectileInGame.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
  }

  public void addTower(Tower tower, TowerNode towerInGame) {
    myTowers.add(tower);
    myTowersInGame.put(tower, towerInGame);
    System.out.println(tower.getXPosition());
    System.out.println(tower.getYPosition());
    System.out.println(towerInGame.getCenterX());
    System.out.println(towerInGame.getCenterY());
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

  public String toString(){
    return "YUH";
  }

}
