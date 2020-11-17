package ooga.visualization;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
    myAnimation.setCycleCount(Timeline.INDEFINITE);
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

        myBloonsInGame.put(bloonToSpawn, bloonNode);
        myLevelLayout.getChildren().add(bloonNode);
      }
    }
  }

  public void addProjectilestoGame(){
    GamePieceIterator<Projectile> iterator = myProjectiles.createIterator();
    while(iterator.hasNext()) {

      Projectile projectileToSpawn = iterator.next();

      if (!myProjectilesInGame.containsKey(projectileToSpawn)) {
        ProjectileNode projectileNode = new ProjectileNode(projectileToSpawn.getType(),
            projectileToSpawn.getXPosition()*myBlockSize, projectileToSpawn.getYPosition()*myBlockSize, myBlockSize / 8);
        projectileNode.setRotate(projectileToSpawn.getAngle());

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
  }


  private void rotateTower(Tower tower, Bloon bloon) {
    Node towerInGame = myTowersInGame.get(tower);
    double angle = Math.toDegrees(
        Math.asin((bloon.getXPosition() - tower.getXPosition()) / tower.getDistance(bloon)));
    if (bloon.getYPosition() < tower.getYPosition()) {
      towerInGame.setRotate(angle);
    } else {
      towerInGame.setRotate(180 - angle);
    }
  }

  private void animateProjectiles() {
    addProjectilestoGame();
    removeDeadProjectiles();
    for(Projectile projectile : myProjectilesInGame.keySet()){
      ProjectileNode projectileNode = myProjectilesInGame.get(projectile);
     //todo: REMOVE NONEXISITNG PROJECTIELS
      projectileNode.setXPosition(projectile.getXPosition()*myBlockSize);
      projectileNode.setYPosition(projectile.getYPosition()*myBlockSize);
      myLevelLayout.getChildren().add(projectileNode);

    }
  }

  private void removeDeadProjectiles(){
    removeProjectileNodes();
    recreateProjectileMap();
  }

  public void removeProjectileNodes(){
    for (ProjectileNode projectileNode : myProjectilesInGame.values()){
      myLevelLayout.getChildren().remove(projectileNode);
    }
  }

  private void recreateProjectileMap(){
    Map<Projectile, ProjectileNode> updatedProjectilesInGame = new HashMap<>();
    GamePieceIterator<Projectile> projectileIterator= myProjectiles.createIterator();
    while(projectileIterator.hasNext()){
      Projectile currentProjectile = projectileIterator.next();
      updatedProjectilesInGame.put(currentProjectile, myProjectilesInGame.get(currentProjectile));
    }
    myProjectilesInGame = updatedProjectilesInGame;
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
    myTowers.add(tower);
    myTowersInGame.put(tower, towerInGame);
  }

  public void removeTower(TowerNode towerInGame) {
    for(Entry<Tower, TowerNode> entry: myTowersInGame.entrySet()){
      if(entry.getValue().equals(towerInGame)){
        Tower tower = entry.getKey();
        myTowers.remove(tower);
        myTowersInGame.remove(tower, towerInGame);
        break;
      }
    }
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
