package ooga.visualization;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.nodes.BloonNode;
import ooga.visualization.nodes.ProjectileNode;
import ooga.visualization.nodes.TowerNode;


public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;

  private Timeline myAnimation;
  private Group myLevelLayout;
  private double myBlockSize;

  private BloonsCollection myBloons;
  private TowersCollection myTowers;
  private ProjectilesCollection myProjectiles;

  private Map<Bloon, BloonNode> myBloonsInGame;
  private Map<Tower, TowerNode> myTowersInGame;
  private Map<Projectile, ProjectileNode> myProjectilesInGame;
  private Map<Tower, Bloon> myShootingTargets;

  public AnimationHandler(Group levelLayout, BloonsCollection bloons,
      TowersCollection towers, ProjectilesCollection projectiles, double blockSize, Timeline animation) {
    myAnimation = animation;
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myLevelLayout = levelLayout;
    myBloons = bloons;
    myTowers = towers;
    myProjectiles = projectiles;
    myBloonsInGame = new HashMap<>();
    myTowersInGame = new HashMap<>();
    myProjectilesInGame = new HashMap<>();
    myBlockSize = blockSize;
  }

  public void addBloonstoGame(){
    GamePieceIterator<Bloon> iterator = myBloons.createIterator();
    while(iterator.hasNext()) {
      Bloon bloonToSpawn = iterator.next();
      if (!myBloonsInGame.containsKey(bloonToSpawn) && !bloonToSpawn.isDead()) {
        BloonNode bloonNode = new BloonNode(bloonToSpawn.getBloonsType(), bloonToSpawn.getXPosition()*myBlockSize, bloonToSpawn.getYPosition()*myBlockSize, myBlockSize / 2.5);
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
            projectileToSpawn.getXPosition()*myBlockSize,
            projectileToSpawn.getYPosition()*myBlockSize,
            projectileToSpawn.getRadius()*myBlockSize/5);
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
    animateShotBloon();
  }


  private void animateBloons() {
    addBloonstoGame();
    for(Bloon bloon : myBloonsInGame.keySet()){
      BloonNode bloonNode = myBloonsInGame.get(bloon);
      if (bloon.isDead()){
        myLevelLayout.getChildren().remove(bloonNode);
      }
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
    ProjectilesCollection projectileToRemove = new ProjectilesCollection();
    for(Projectile projectile : myProjectilesInGame.keySet()){
      ProjectileNode projectileNode = myProjectilesInGame.get(projectile);
      projectileNode.setXPosition(projectile.getXPosition()*myBlockSize);
      projectileNode.setYPosition(projectile.getYPosition()*myBlockSize);
      //todo: REMOVE NONEXISITNG PROJECTILES: DONE
      if(checkOutOfBoundsProjectile(projectileNode)){
        myProjectiles.remove(projectile);
        projectileToRemove.add(projectile);
        myLevelLayout.getChildren().remove(projectileNode);
      }
    }
    removeShotProjectiles(projectileToRemove);
  }


  private boolean checkOutOfBoundsProjectile(Circle projectile) {
    return projectile.getCenterX() <= 0
        || projectile.getCenterX() >= BloonsApplication.GAME_WIDTH
        || projectile.getCenterY() <= 0
        || projectile.getCenterY() >= BloonsApplication.GAME_HEIGHT;
    // TODO: cant detect the bottom out of bounds
  }

  private void animateShotBloon(){
    BloonsCollection bloonsToRemove = new BloonsCollection();
    ProjectilesCollection projectilesToRemove = new ProjectilesCollection();
    BloonsCollection bloonsToAdd = new BloonsCollection();
    for(Projectile projectile : myProjectilesInGame.keySet()){
      for(Bloon bloon : myBloonsInGame.keySet()){
        if(shouldExplode(projectile, bloon)){
          popBloon(bloon, projectile, bloonsToRemove, bloonsToAdd, projectilesToRemove);
        } else if(checkBloonCollision(projectile, bloon)){
          if(shouldPop(projectile, bloon)){
              popBloon(bloon, projectile, bloonsToRemove, bloonsToAdd, projectilesToRemove);
          } else if(shouldFreeze(projectile, bloon)){
              bloon.freeze();
              projectilesToRemove.add(projectile);
          }
        }
      }
    }
    spawnBloons(bloonsToAdd);
    removeShotBloon(bloonsToRemove);
    removeShotProjectiles(projectilesToRemove);
  }

  private boolean shouldExplode(Projectile projectile, Bloon bloon){
    return projectile.getType() == ProjectileType.SpreadProjectile &&
        checkSpreadProjectileCollision(projectile, bloon);
  }

  private boolean shouldPop(Projectile projectile, Bloon bloon){
    return (projectile.getType() == ProjectileType.SingleTargetProjectile &&
        !bloon.getBloonsType().specials().contains(Specials.Camo))
        || projectile.getType() == ProjectileType.CamoTargetProjectile;
  }

  private boolean shouldFreeze(Projectile projectile, Bloon bloon){
    return projectile.getType() == ProjectileType.FreezeTargetProjectile && !bloon.isFreezeActive();
  }

  private void popBloon(Bloon bloon, Projectile projectile, BloonsCollection bloonsToRemove, BloonsCollection bloonsToAdd,
      ProjectilesCollection projectilesToRemove){
    Bloon[] spawnedBloons = bloon.shootBloon();
    System.out.println("original bloon: " + bloon.getXPosition() + " " + bloon.getYPosition());
    for(Bloon spawn : spawnedBloons) {
      System.out.println("spawned bloon: " + spawn.getXPosition() + " " + spawn.getYPosition());
      bloonsToAdd.add(spawn);
    }
    bloon.setDead();
    bloonsToRemove.add(bloon);
    projectilesToRemove.add(projectile);
  }

  private void spawnBloons(BloonsCollection bloonsToAdd){
    GamePieceIterator<Bloon> iterator = bloonsToAdd.createIterator();
    while(iterator.hasNext()){
      Bloon toAdd = iterator.next();
      myBloons.add(toAdd);
    }
    addBloonstoGame();
  }

  private void removeShotBloon(BloonsCollection bloonsToRemove){
    GamePieceIterator<Bloon> iterator = bloonsToRemove.createIterator();
    while(iterator.hasNext()){
      Bloon toRemove = iterator.next();
      myLevelLayout.getChildren().remove(myBloonsInGame.get(toRemove));
      myBloonsInGame.remove(toRemove);
      myBloons.remove(toRemove);
    }
  }

  private void removeShotProjectiles(ProjectilesCollection projectilesToRemove){
    GamePieceIterator<Projectile> projectileIterator = projectilesToRemove.createIterator();
    while(projectileIterator.hasNext()){
      Projectile toRemove = projectileIterator.next();
      myLevelLayout.getChildren().remove(myProjectilesInGame.get(toRemove));
      myProjectilesInGame.remove(toRemove);
      myProjectiles.remove(toRemove);
    }
  }

  private boolean checkBloonCollision(Projectile projectile, Bloon bloon) {
    Circle projectileInGame = myProjectilesInGame.get(projectile);
    Circle bloonInGame = myBloonsInGame.get(bloon);
    return projectileInGame.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
  }

  private boolean checkSpreadProjectileCollision(Projectile projectile, Bloon bloon){
    Circle projectileInGame = myProjectilesInGame.get(projectile);
    Circle bloonInGame = myBloonsInGame.get(bloon);
    double radius = projectileInGame.getRadius();
    BoundingBox bounds = new BoundingBox(projectileInGame.getCenterX()-radius,
        projectileInGame.getCenterY()-radius, projectileInGame.getCenterX()+radius,
        projectileInGame.getCenterY()+radius);
    return bounds.intersects(bloonInGame.getBoundsInParent());
  }

  public void addTower(Tower tower, TowerNode towerInGame) {
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
