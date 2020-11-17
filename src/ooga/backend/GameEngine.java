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
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.visualization.AnimationHandler;

public class GameEngine implements GameEngineAPI {

  private static final int FIRST_WAVE = 0;
  public static final int SPAWN_DELAY = (int) (1 * AnimationHandler.FRAMES_PER_SECOND);


  private final Layout layout;
  private final List<BloonsCollection> allBloonWaves;
  private BloonsCollection currentBloonWave;
  private BloonsCollection queuedBloons;



  private Map<Tower, Bloon> shootingTargets;
  private int spawnTimer;
  private double myBlockSize;
  private TowersCollection towers;

  private ProjectilesCollection projectiles;
  private Map<Bloon, Double > myBloonSidesX;
  private Map<Bloon, Double> myBloonSidesY;

  private int wave;

  public GameEngine(Layout layout, List<BloonsCollection> allBloonWaves, TowersCollection towers,
      ProjectilesCollection projectiles, double blockSize) {
    this.layout = layout;
    this.allBloonWaves = allBloonWaves;
    this.queuedBloons = allBloonWaves.get(FIRST_WAVE);
    this.currentBloonWave = new BloonsCollection();
    this.towers = towers;
    this.projectiles = projectiles;
    myBlockSize = blockSize;
    wave = FIRST_WAVE;
    spawnTimer = SPAWN_DELAY;
    this.shootingTargets = new HashMap<>();
    myBloonSidesX = new HashMap<>();
    myBloonSidesY = new HashMap<>();
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

  @Override
  public void moveBloons() {
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
        bloon.setDead();
      }

      bloon.setXVelocity((bloon.getBloonsType().relativeSpeed() * currentBlock.getDx())/myBlockSize);
      bloon.setYVelocity((bloon.getBloonsType().relativeSpeed() * currentBlock.getDy())/myBlockSize);

      setBloonSides(bloon, currentBlock);
    }

    currentBloonWave.updateAll();
  }

  private void setBloonSides(Bloon bloon, LayoutBlock block) {
    myBloonSidesX.put(bloon, -0.5 * block.getDx());
    myBloonSidesY.put(bloon, -0.5 * block.getDy());
  }


  @Override
  public void nextWave() {
    wave++;
    currentBloonWave = allBloonWaves.get(wave);
  }

  @Override
  public void resetGame() {
    wave = FIRST_WAVE;
    currentBloonWave = allBloonWaves.get(FIRST_WAVE);
//    towers.clear();
//    towersIterator = towers.createIterator();
  }
  @Override
  public void shootBloons(){
    GamePieceIterator<Tower> towerIterator = towers.createIterator();
    while(towerIterator.hasNext()){
      Tower currentTower = towerIterator.next();
      currentTower.update();
      if(!currentTower.isRestPeriodActive()){
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

  public BloonsCollection getCurrentBloonWave() {
    return currentBloonWave;
  }

  public TowersCollection getTowers() {
    return towers;
  }

  public ProjectilesCollection getProjectiles() {
    return projectiles;
  }

  public void setTowers(TowersCollection towers) {
    this.towers = towers;
  }

  public void setProjectiles(ProjectilesCollection projectiles) {
    this.projectiles = projectiles;
  }

  @Override
  public void update(){
    //TODO: get projectile AND tower list from front end (maybe bloons? idk)

    if (spawnTimer == SPAWN_DELAY){
      addQueuedBloon();
      spawnTimer = 0;
    }
    moveBloons();
    moveProjectiles();

    shootBloons();
    spawnTimer++;
  }

  public Map<Tower, Bloon> getShootingTargets() {
    return shootingTargets;
  }



}
