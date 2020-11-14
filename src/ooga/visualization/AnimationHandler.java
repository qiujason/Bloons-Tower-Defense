package ooga.visualization;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
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
import ooga.visualization.nodes.BloonNode;
import ooga.visualization.nodes.BloonNodeFactory;


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
  private Map<Tower, Node> myTowersInGame = new HashMap<>();
  private BloonsCollection myBloons;
  private BloonsCollection myWaitingBloons;
  private Map<Bloon, BloonNode> myBloonsInGame = new HashMap<>();
  private ProjectilesCollection myProjectiles = new ProjectilesCollection();
  private Map<Projectile, Node> myProjectilesInGame = new HashMap<>();
  private ProjectileFactory myProjectileFactory = new SingleProjectileFactory();

  private Map<Bloon, Double> myCircleSidesX;
  private Map<Bloon, Double> myCircleSidesY;

  private int bloonSpawnDelay = 0;

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

    readyUpBloons();

//    // The following code is merely to test a starting chain of 5 balloons
//    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
//    myWaitingBloons = new BloonsCollection();
//    for(int i = 0; i < 10; i++){
//      myWaitingBloons.add(new Bloon(chain.getBloonsTypeRecord("RED"), myStartingX, myStartingY, 0, 0));
    //}

  }

  public void readyUpBloons(){
    GamePieceIterator<Bloon> iterator = myBloons.createIterator();
    if(iterator.hasNext()) {
      Bloon bloonToSpawn = (Bloon) iterator.next();
      if (bloonToSpawn.getXPosition() >= 1) {
        BloonNode bloonNode = new BloonNode(myStartingX, myStartingY, myBlockSize / 2.5);
        myBloonsInGame.putIfAbsent(bloonToSpawn, bloonNode);
        myLevelLayout.getChildren().add(bloonNode);
      }

    }
  }

  public void animate() {
    animateTowers();
    animateProjectiles();
    animateBloons();

  }

//  private void spawnBloon() {
//    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
//    if(bloonsIterator.hasMore()) {
//      Bloon bloonToSpawn = (Bloon) bloonsIterator.getNext();
//
//      Circle myBloonInGame = new Circle(myStartingX, myStartingY, myBlockSize / 2.5, Color.RED);
//      System.out.println(myBloonInGame);
//      System.out.println("ADSF");
//
//      myBloonsInGame.put(bloonToSpawn, myBloonInGame);
//      myLevelLayout.getChildren().add(myBloonInGame);
//      myBloons.remove(bloonToSpawn);
//    }
//  }

  // TODO: Refactor
  private void animateBloons() {

    for(Bloon bloon : myBloonsInGame.keySet()){
      BloonNode bnode = myBloonsInGame.get(bloon);
      System.out.println("Node: "+ bnode.getXPosition());

      myCircleSidesX.putIfAbsent(bloon, 0.0);
      myCircleSidesY.putIfAbsent(bloon, 0.0);
//System.out.println("circlesideX" + myCircleSidesX);
//      System.out.println("circlesideY" +myCircleSidesX);
//      System.out.println(myBlockSize);


      LayoutBlock currentBlock =
          myLayout.getBlock(((int) ((bnode.getCenterY() + myCircleSidesY.get(bloon)) / myBlockSize)),
              ((int) ((bnode.getCenterX() + myCircleSidesX.get(bloon)) / myBlockSize)));


      if (currentBlock.isEndBlock()) {
        myLevelLayout.getChildren().remove(bnode);
        myBloons.remove(bloon);
        myBloonsInGame.remove(bloon);
      }
      bloon.setXVelocity(bloon.getBloonsType().relativeSpeed() * currentBlock.getDx());
      bloon.setYVelocity(bloon.getBloonsType().relativeSpeed() * currentBlock.getDy());

      bnode.setXPosition(bnode.getCenterX() + bloon.getXVelocity());
      bnode.setYPosition(bnode.getCenterY() + bloon.getYVelocity());

      setCircleSides(currentBlock, bloon);

    }

    myBloons.updateAll();
  }

  private void setCircleSides(LayoutBlock currentBlock, Bloon currentBloon) {
    myCircleSidesX.put(currentBloon, -myBlockSize * currentBlock.getDx() / 2);
    myCircleSidesY.put(currentBloon, -myBlockSize * currentBlock.getDy() / 2);
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
    return projectileInGame.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
    //return false;
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
    //myWaitingBloons = bloonWave;
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

  public String toString(){
    return "YUH";
  }

}
