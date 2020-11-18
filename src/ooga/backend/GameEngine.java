package ooga.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.backend.API.GameEngineAPI;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.layout.Layout;
import ooga.backend.layout.LayoutBlock;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.AnimationHandler;

public class GameEngine implements GameEngineAPI {

  private static final int STARTING_ROUND = 0;
  private static final int STARTING_LIVES = 100;
  private static final int SPAWN_DELAY = (int) (1 * AnimationHandler.FRAMES_PER_SECOND);
  private static final int SPEED_ADJUSTER = 50;


  private final Layout layout;
  private final List<BloonsCollection> allBloonWaves;
  private BloonsCollection currentBloonWave;
  private BloonsCollection queuedBloons;
  private Map<Tower, Bloon> shootingTargets;
  private int spawnTimer;
  private TowersCollection towers;
  private RoadItemsCollection roadItems;

  private ProjectilesCollection projectiles;
  private Map<Bloon, Double > myBloonSidesX;
  private Map<Bloon, Double> myBloonSidesY;

  private int round;
  private int lives = STARTING_LIVES;



  public GameEngine(Layout layout, List<BloonsCollection> allBloonWaves) {
    this.layout = layout;
    this.allBloonWaves = allBloonWaves;
    this.queuedBloons = allBloonWaves.get(STARTING_ROUND);
    this.currentBloonWave = new BloonsCollection();
    this.towers = new TowersCollection();
    this.roadItems = new RoadItemsCollection();
    this.projectiles = new ProjectilesCollection();
    spawnTimer = SPAWN_DELAY;
    this.shootingTargets = new HashMap<>();
    myBloonSidesX = new HashMap<>();
    myBloonSidesY = new HashMap<>();
    this.round = STARTING_ROUND;

  }

  public void addQueuedBloon(){
    GamePieceIterator<Bloon> queueIterator = queuedBloons.createIterator();
    if (queueIterator.hasNext()){
      Bloon queuedBloon = queueIterator.next();
      if (queuedBloon.getBloonsType().name().equals("DEAD")){
        queuedBloons.remove(queuedBloon);
        return;
      }
      currentBloonWave.add(queuedBloon);
      queuedBloons.remove(queuedBloon);
    }
  }

  public void moveBloons() {
    removeDeadBloons();
    GamePieceIterator<Bloon> waveIterator = currentBloonWave.createIterator();

    //updatevelocity -- extract helper
    while (waveIterator.hasNext()) {
      Bloon bloon = waveIterator.next();

      myBloonSidesX.putIfAbsent(bloon, 0.0);
      myBloonSidesY.putIfAbsent(bloon, 0.0);

      LayoutBlock currentBlock;
      currentBlock = layout.getBlock((int) (bloon.getYPosition() + myBloonSidesY.get(bloon))
          ,(int) (bloon.getXPosition() + myBloonSidesX.get(bloon)));

      if (currentBlock.isEndBlock()){
        lives -= bloon.getBloonsType().RBE();
        bloon.setDead();
      }

      bloon.setXVelocity((bloon.getBloonsType().relativeSpeed() * currentBlock.getDx())/SPEED_ADJUSTER);
      bloon.setYVelocity((bloon.getBloonsType().relativeSpeed() * currentBlock.getDy())/SPEED_ADJUSTER);

      setBloonSides(bloon, currentBlock);
    }

    currentBloonWave.updateAll();
  }

  private void setBloonSides(Bloon bloon, LayoutBlock block) {
    myBloonSidesX.put(bloon, -0.5* block.getDx());
    myBloonSidesY.put(bloon, -0.5* block.getDy());
  }

  private void removeDeadBloons(){
    GamePieceIterator<Bloon> bloonsIterator = currentBloonWave.createIterator();
    while (bloonsIterator.hasNext()) {
      Bloon bloon = bloonsIterator.next();
      if(bloon.isDead()){
        currentBloonWave.remove(bloon);
      }
    }
  }

  public void nextWave() {
    round++;
    queuedBloons = allBloonWaves.get(round);
  }

  public void resetGame() {
    round = STARTING_ROUND;
    queuedBloons = allBloonWaves.get(STARTING_ROUND);

  }

  private void shootBloons(){
    GamePieceIterator<Tower> towerIterator = towers.createIterator();
    while(towerIterator.hasNext()){
      Tower currentTower = towerIterator.next();
      currentTower.update();
      if(!currentTower.isIfRestPeriod()){
        shootingTargets.put(currentTower,currentTower.shoot(currentBloonWave, projectiles));
      }
    }
  }

  /**
   * assumes all projectiles on screen should be moved
   */
  private void moveProjectiles() {
    removeOffScreenProjectiles();
    projectiles.updateAll();
  }

  private void removeOffScreenProjectiles(){
    GamePieceIterator<Projectile> projectileIterator = projectiles.createIterator();
    while (projectileIterator.hasNext()){
      Projectile currentProjectile = projectileIterator.next();

      if (currentProjectile.getXPosition() < 0 || currentProjectile.getXPosition() > layout.getWidth()
          || currentProjectile.getYPosition() < 0 || currentProjectile.getYPosition() > layout.getHeight()){
        projectiles.remove(currentProjectile);
      }
    }
  }

  @Override
  public BloonsCollection getCurrentBloonWave() {
    return currentBloonWave;
  }

  @Override
  public TowersCollection getTowers() {
    return towers;
  }

  @Override
  public ProjectilesCollection getProjectiles() {
    return projectiles;
  }

  @Override
  public void setTowers(TowersCollection towers) {
    this.towers = towers;
  }

  @Override
  public void setProjectiles(ProjectilesCollection projectiles) {
    this.projectiles = projectiles;
  }

  @Override
  public void update(){
    //TODO: get projectile AND tower list from front end (maybe bloons? idk)
    if (isRoundEnd()){

      nextWave();
    }
    spawnBloons();
    moveBloons();
    moveProjectiles();
    shootBloons();
  }

  private void spawnBloons() {
    if (spawnTimer == SPAWN_DELAY){
      addQueuedBloon();
      spawnTimer = 0;
    }
    spawnTimer++;
  }


  public boolean isRoundEnd(){
    return queuedBloons.isEmpty() && currentBloonWave.isEmpty() && projectiles.isEmpty();
  }

  public boolean isGameEnd(){
    return isRoundEnd() && round >= (allBloonWaves.size() - 1);
  }

  @Override
  public int getRound() {
    return round;
  }

  @Override
  public Map<Tower, Bloon> getShootingTargets() {
    return shootingTargets;
  }

  @Override
  public RoadItemsCollection getRoadItems() {
    return roadItems;
  }

  public void setRoadItems(RoadItemsCollection update){
    roadItems = update;
  }

}
