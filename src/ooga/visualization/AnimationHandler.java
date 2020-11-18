package ooga.visualization;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.projectile.factory.ProjectileFactory;
import ooga.backend.projectile.factory.SingleProjectileFactory;
import ooga.backend.layout.Layout;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.nodes.BloonNode;
import ooga.visualization.nodes.ProjectileNode;
import ooga.visualization.nodes.TowerNode;


public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;

  private Timeline myAnimation;
  private Layout myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;

  private BloonsCollection myBloons;


  private TowersCollection myTowers;
  private ProjectilesCollection myProjectiles;

  private Map<Bloon, BloonNode> myBloonsInGame;
  private Map<Tower, TowerNode> myTowersInGame;
  private Map<Projectile, ProjectileNode> myProjectilesInGame;
  private Map<Tower, Bloon> myShootingTargets;
  private ProjectileFactory myProjectileFactory = new SingleProjectileFactory();

  private Map<BloonNode, Double> myCircleSidesX;
  private Map<BloonNode, Double> myCircleSidesY;


  public AnimationHandler(Layout layout, Group levelLayout, BloonsCollection bloons,
      TowersCollection towers, ProjectilesCollection projectiles, double startingX,
      double startingY, double blockSize, Timeline animation) {
    myAnimation = animation;
    myAnimation.setCycleCount(Timeline.INDEFINITE); //why is this done twice?
//    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
//    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;

    myBloons = bloons;
    myTowers = towers;
    myProjectiles = projectiles;

    myBloonsInGame = new HashMap<>();
    myTowersInGame = new HashMap<>();
    myProjectilesInGame = new HashMap<>();

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
        System.out.println("BLOONNODE: " + bloonNode.getXPosition() + " " + bloonNode.getYPosition());

        myBloonsInGame.put(bloonToSpawn, bloonNode);
        myLevelLayout.getChildren().add(bloonNode);
      }
    }
  }

  public void addProjectilestoGame(){
    GamePieceIterator<Projectile> iterator = myProjectiles.createIterator();
    while(iterator.hasNext()) {

      Projectile projectileToSpawn = iterator.next();
      System.out.println("Projectile coords: " + projectileToSpawn.getXPosition() + " " +
          projectileToSpawn.getYPosition());

      if (!myProjectilesInGame.containsKey(projectileToSpawn)) {
        ProjectileNode projectileNode = new ProjectileNode(projectileToSpawn.getType(),
            projectileToSpawn.getXPosition()*myBlockSize, projectileToSpawn.getYPosition()*myBlockSize, myBlockSize / 8);
        projectileNode.setRotate(projectileToSpawn.getAngle());

        System.out.println("PROJECTILENODE: " + projectileNode.getXPosition() + " " + projectileNode.getYPosition());

        myProjectilesInGame.put(projectileToSpawn, projectileNode);
        myLevelLayout.getChildren().add(projectileNode);

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
    while (towersIterator.hasNext()) {
      Tower currentTower = towersIterator.next();
      if (myShootingTargets.get(currentTower) != null){
        rotateTower(currentTower, myShootingTargets.get(currentTower));
      }
    }

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


  private void rotateTower(Tower tower, Bloon bloon) {
    System.out.println(tower.getDistance(bloon)*myBlockSize);
    Node towerInGame = myTowersInGame.get(tower);
    double angle = Math.toDegrees(
        Math.asin((bloon.getXPosition() - tower.getXPosition()) / tower.getDistance(bloon)));
    if (bloon.getYPosition() < tower.getYPosition()) {
      towerInGame.setRotate(angle);
    } else {
      towerInGame.setRotate(180 - angle);
    }
  }

//  // TODO: use shoot method
//  private void attemptToFire(Bloon bloon, Tower tower) {
//    if (tower.canShoot()) {
//      double projectileXSpeed =
//          tower.getShootingSpeed() * (bloon.getXPosition() - tower.getXPosition()) / tower
//              .getDistance(bloon);
//      double projectileYSpeed =
//          tower.getShootingSpeed() * (bloon.getYPosition() - tower.getYPosition()) / tower
//              .getDistance(bloon);
//      Projectile newProjectile = myProjectileFactory
//          .createDart(ProjectileType.SingleTargetProjectile, tower.getXPosition(),
//              tower.getYPosition(), projectileXSpeed, projectileYSpeed);
//      Circle projectileInGame = new Circle(newProjectile.getXPosition(),
//          newProjectile.getYPosition(), myBlockSize / 8);
//      myLevelLayout.getChildren().add(projectileInGame);
//      myProjectiles.add(newProjectile);
//      myProjectilesInGame.put(newProjectile, projectileInGame);
//    }
//  }

  private void animateProjectiles() {
    addProjectilestoGame();
    for(Projectile projectile : myProjectilesInGame.keySet()){
      ProjectileNode projectileNode = myProjectilesInGame.get(projectile);
     //todo: REMOVE NONEXISITNG PROJECTIELS
      projectileNode.setXPosition(projectile.getXPosition()*myBlockSize);
      projectileNode.setYPosition(projectile.getYPosition()*myBlockSize);
    }

//
//    GamePieceIterator<Bloon> bloonsIterator = myBloons.createIterator();
//    while (projectilesIterator.hasNext()) {
//      Projectile projectile = projectilesIterator.next();
//      Circle projectileInGame = (Circle) myProjectilesInGame.get(projectile);
//      projectileInGame.setCenterX(projectileInGame.getCenterX() + projectile.getXVelocity());
//      projectileInGame.setCenterY(projectileInGame.getCenterY() + projectile.getYVelocity());
//      while (bloonsIterator.hasNext()) {
//        Bloon bloon = bloonsIterator.next();
//        if (checkBloonCollision(projectile, bloon)) {
//          myLevelLayout.getChildren().remove(myProjectilesInGame.remove(projectile));
//          myLevelLayout.getChildren().remove(myBloonsInGame.remove(bloon));
//          myProjectiles.remove(projectile);
//          myBloons.remove(bloon);
//        }
//      }
//      if (checkOutOfBoundsProjectile(projectileInGame)) {
//        myLevelLayout.getChildren().remove(projectileInGame);
//      }
//      bloonsIterator.reset();
//    }
//    myProjectiles.updateAll();
  }

  private boolean checkOutOfBoundsProjectile(Circle projectile) {
    return projectile.getCenterX() <= 0
        || projectile.getCenterX() >= BloonsApplication.GAME_WIDTH
        || projectile.getCenterY() <= 0
        || projectile.getCenterY() >= BloonsApplication.GAME_HEIGHT;
  }

  private boolean checkBloonCollision(Projectile projectile, Bloon bloon) {
    Circle projectileInGame = (Circle) myProjectilesInGame.get(projectile);
    Circle bloonInGame = myBloonsInGame.get(bloon);
    return projectileInGame.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
  }

  public void addTower(Tower tower, TowerNode towerInGame) {
    System.out.println("Tower: " + tower.getRadius());
    System.out.println("RANGE: " + towerInGame.getRangeDisplay().getRadius());
    System.out.println("tower coordinates: " + tower.getXPosition() + " " + tower.getYPosition());
    System.out.println("tower rest :" + tower.getShootingRestRate());
    myTowers.add(tower);
    myTowersInGame.put(tower, towerInGame);
  }

  public TowerNode getNodeFromTower(Tower tower){
    return myTowersInGame.get(tower);
  }

  public Tower getTowerFromNode(TowerNode towerInGame){
    Tower tower = null;
    for(Entry<Tower, TowerNode> entry: myTowersInGame.entrySet()) {
      if (entry.getValue().equals(towerInGame)) {
        tower = entry.getKey();
      }
    }
    return tower;
  }

  public void removeTower(Tower tower, TowerNode towerInGame) {
    myTowers.remove(tower);
    myTowersInGame.remove(tower, towerInGame);
  }

  public void setBloonWave(BloonsCollection bloonWave) {
    myBloons = bloonWave;
  }

  public void setTowers(TowersCollection towers) {
    this.myTowers = towers;
  }

  public void setProjectiles(ProjectilesCollection projectiles) {
    this.myProjectiles = projectiles;
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

  public TowersCollection getTowers() {
    return myTowers;
  }

  public ProjectilesCollection getProjectiles() {
    return myProjectiles;
  }

  public void setShootingTargets(
      Map<Tower, Bloon> shootingTargets) {
    this.myShootingTargets = shootingTargets;
  }
}
