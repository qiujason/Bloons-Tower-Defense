package ooga.visualization.animationhandlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import ooga.backend.bank.Bank;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.gameengine.GameMode;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.roaditems.RoadItem;
import ooga.backend.roaditems.RoadItemType;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.BloonsApplication;
import ooga.visualization.nodes.BloonNode;
import ooga.visualization.nodes.GamePieceNode;
import ooga.visualization.nodes.ProjectileNode;
import ooga.visualization.nodes.RoadItemNode;
import ooga.visualization.nodes.TowerNode;

public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;

  private Timeline myAnimation;
  private Group myLevelLayout;
  private double myBlockSize;
  private GameMode myGameMode;

  private BloonsCollection myBloons;
  private TowersCollection myTowers;
  private ProjectilesCollection myProjectiles;
  private RoadItemsCollection myRoadItems;
  private Bank bank;

  private Map<Bloon, BloonNode> myBloonsInGame;
  private Map<Tower, TowerNode> myTowersInGame;
  private Map<Projectile, ProjectileNode> myProjectilesInGame;
  private Map<RoadItem, RoadItemNode> myRoadItemsInGame;
  private Map<Tower, Bloon> myShootingTargets;

  public AnimationHandler(Group levelLayout, BloonsCollection bloons,
      TowersCollection towers, ProjectilesCollection projectiles, RoadItemsCollection roadItems,
      Bank bank, GameMode gameMode, double blockSize, Timeline animation) {
    myAnimation = animation;
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myLevelLayout = levelLayout;
    myBloons = bloons;
    myTowers = towers;
    myProjectiles = projectiles;
    myRoadItems = roadItems;
    myBloonsInGame = new HashMap<>();
    myTowersInGame = new HashMap<>();
    myProjectilesInGame = new HashMap<>();
    myRoadItemsInGame = new HashMap<>();
    myBlockSize = blockSize;
    myGameMode = gameMode;
    this.bank = bank;
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
    animateRoadItemCollisions();
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
        || projectile.getCenterY() >= BloonsApplication.GAME_HEIGHT-70;
  }

  private void animateShotBloon(){
    BloonsCollection bloonsToRemove = new BloonsCollection();
    ProjectilesCollection projectilesToRemove = new ProjectilesCollection();
    BloonsCollection bloonsToAdd = new BloonsCollection();
    for(Projectile projectile : myProjectilesInGame.keySet()){
      for(Bloon bloon : myBloonsInGame.keySet()){
        collisionHandler(projectile, bloon, bloonsToRemove, bloonsToAdd, projectilesToRemove);
        }
      }
    spawnBloons(bloonsToAdd);
    addMoneyPerPop(bloonsToRemove);
    removeShotBloon(bloonsToRemove);
    removeShotProjectiles(projectilesToRemove);
  }

  private void addMoneyPerPop(BloonsCollection bloonsToRemove){
    GamePieceIterator<Bloon> iterator = bloonsToRemove.createIterator();
    while(iterator.hasNext() && myGameMode != GameMode.Sandbox){
      iterator.next();
      bank.addPoppedBloonValue();
    }
  }


  private void collisionHandler(Projectile projectile, Bloon bloon, BloonsCollection bloonsToRemove, BloonsCollection bloonsToAdd, ProjectilesCollection projectilesToRemove) {
    if (shouldExplode(projectile, bloon)) {
      popBloon(bloon, projectile, bloonsToRemove, bloonsToAdd, projectilesToRemove, false);
    } else if (checkBloonCollision(bloon, myProjectilesInGame.get(projectile))) {
      if (shouldPop(projectile, bloon)) {
        popBloon(bloon, projectile, bloonsToRemove, bloonsToAdd, projectilesToRemove, false);
      } else if (shouldFreeze(projectile, bloon)) {
        bloon.freeze();
        projectilesToRemove.add(projectile);
      }
    }
  }

  private void animateRoadItemCollisions(){
    BloonsCollection bloonsToRemove = new BloonsCollection();
    BloonsCollection bloonsToAdd = new BloonsCollection();
    RoadItemsCollection itemsToRemove = new RoadItemsCollection();
    for(RoadItem roadItem : myRoadItemsInGame.keySet()){
      for(Bloon bloon : myBloonsInGame.keySet()){
        if(roadItem.shouldRemove()){
          itemsToRemove.add(roadItem);
          if(roadItem.getType() == RoadItemType.ExplodeBloonsItem &&
              checkSpreadProjectileCollision(bloon, myRoadItemsInGame.get(roadItem), 20)){
            popBloon(bloon, null, bloonsToRemove, bloonsToAdd, null, true);
          }
          continue;
        }
        updateRoadItems(roadItem, bloon, bloonsToRemove, bloonsToAdd);
      }
    }
    spawnBloons(bloonsToAdd);
    removeShotBloon(bloonsToRemove);
    removeExpiredRoadItems(itemsToRemove);
  }

  private void updateRoadItems(RoadItem roadItem, Bloon bloon, BloonsCollection bloonsToRemove, BloonsCollection bloonsToAdd){
    if(roadItem.getType() == RoadItemType.ExplodeBloonsItem){
      roadItem.update();
    } else if(checkBloonCollision(bloon, myRoadItemsInGame.get(roadItem))){
      if(roadItem.getType() == RoadItemType.PopBloonsItem){
        popBloon(bloon, null, bloonsToRemove, bloonsToAdd, null, true);
        roadItem.update();
      } else if(roadItem.getType() == RoadItemType.SlowBloonsItem && !bloon.isSlowDownActive()){
        bloon.slowDown();
        roadItem.update();
      }
    }
  }

  private boolean shouldExplode(Projectile projectile, Bloon bloon){
    return projectile.getType() == ProjectileType.SpreadProjectile &&
        checkSpreadProjectileCollision(bloon, myProjectilesInGame.get(projectile),
            projectile.getRadius());
  }

  private boolean shouldPop(Projectile projectile, Bloon bloon){
    return (projectile.getType() == ProjectileType.SingleTargetProjectile &&
        bloon.getBloonsType().specials() != (Specials.Camo))
        || projectile.getType() == ProjectileType.CamoTargetProjectile;
  }

  private boolean shouldFreeze(Projectile projectile, Bloon bloon){
    return projectile.getType() == ProjectileType.FreezeTargetProjectile && !bloon.isFreezeActive();
  }

  private void popBloon(Bloon bloon, Projectile projectile, BloonsCollection bloonsToRemove, BloonsCollection bloonsToAdd,
      ProjectilesCollection projectilesToRemove, boolean roadItemCheck){
    Bloon[] spawnedBloons = bloon.shootBloon();
    for(Bloon spawn : spawnedBloons) {
      bloonsToAdd.add(spawn);
    }
    bloon.setDead();
    bloonsToRemove.add(bloon);
    if(!roadItemCheck){
      projectilesToRemove.add(projectile);
    }
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

  private void removeExpiredRoadItems(RoadItemsCollection toRemove){
    GamePieceIterator<RoadItem> iterator = toRemove.createIterator();
    while(iterator.hasNext()){
      RoadItem removed = iterator.next();
      myLevelLayout.getChildren().remove(myRoadItemsInGame.get(removed));
      myRoadItemsInGame.remove(removed);
      myRoadItems.remove(removed);
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

  private boolean checkBloonCollision(Bloon bloon, GamePieceNode weapon){
    GamePieceNode bloonInGame = myBloonsInGame.get(bloon);
    return weapon.getBoundsInParent().intersects(bloonInGame.getBoundsInParent());
  }

  private boolean checkSpreadProjectileCollision(Bloon bloon, GamePieceNode weapon, double radius){
    GamePieceNode bloonInGame = myBloonsInGame.get(bloon);
    BoundingBox bounds = new BoundingBox(weapon.getBoundsInParent().getMinX()-radius,
        weapon.getBoundsInParent().getMinY()-radius,
        weapon.getBoundsInParent().getWidth() + 2 * radius,
        weapon.getBoundsInParent().getHeight() + 2 * radius);
    return bounds.intersects(bloonInGame.getBoundsInParent());
  }

  public void addTower(Tower tower, TowerNode towerInGame) {
    myTowers.add(tower);
    myTowersInGame.put(tower, towerInGame);
  }

  public void addRoadItem(RoadItem item, RoadItemNode roadInGame) {
    myRoadItems.add(item);
    myRoadItemsInGame.put(item, roadInGame);
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

  public TowersCollection getTowers() {
    return myTowers;
  }

  public ProjectilesCollection getProjectiles() {
    return myProjectiles;
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

  public void setShootingTargets(
      Map<Tower, Bloon> shootingTargets) {
    this.myShootingTargets = shootingTargets;
  }

  public RoadItemsCollection getRoadItems(){
    return myRoadItems;
  }

  public void setRoadItems(RoadItemsCollection roadItems){
    myRoadItems = roadItems;
  }
}
