package ooga.backend.gameengine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ooga.backend.API.GameEngineAPI;
import ooga.backend.ConfigurationException;
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
  public static final int STARTING_LIVES = 100;
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
  private String gameMode;


  public GameEngine(String gameMode, Layout layout, List<BloonsCollection> allBloonWaves) {
    this.layout = layout;
    this.allBloonWaves = allBloonWaves;
    this.queuedBloons = allBloonWaves.get(STARTING_ROUND).copyOf(allBloonWaves.get(STARTING_ROUND));
    this.currentBloonWave = new BloonsCollection();
    this.towers = new TowersCollection();
    this.roadItems = new RoadItemsCollection();
    this.projectiles = new ProjectilesCollection();
    spawnTimer = SPAWN_DELAY;
    this.shootingTargets = new HashMap<>();
    myBloonSidesX = new HashMap<>();
    myBloonSidesY = new HashMap<>();
    this.round = STARTING_ROUND;
    this.gameMode = gameMode;

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

  public void nextWaveNormal() {
    round++;
    queuedBloons = allBloonWaves.get(round).copyOf(allBloonWaves.get(round));
  }

  public void nextWaveInfinite() {
    System.out.println("here");
    round++;
    Random rand = new Random();
    int randomRound = rand.nextInt(allBloonWaves.size());
    System.out.println(randomRound);
    queuedBloons = allBloonWaves.get(randomRound).copyOf(allBloonWaves.get(randomRound));
  }

  public void nextWaveSandbox() {
    nextWaveNormal();
  }

  public void resetGame() {
    round = STARTING_ROUND;
    queuedBloons = allBloonWaves.get(STARTING_ROUND);

  }

  private void shootBloons() throws ConfigurationException {
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

  private void nextWave() {
    try {
      Method method = this.getClass()
          .getDeclaredMethod("nextWave" + this.gameMode);
      method.invoke(this);
    } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
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
  public void update() throws ConfigurationException {
    checkRoundStatus();
    spawnBloons();
    moveBloons();
    moveProjectiles();
    shootBloons();
  }

  private void checkRoundStatus() {
    if (isRoundEnd()){
      nextWave();
    }
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
    if(gameMode != GameMode.Infinite.name()) {
      return lives <= 0 || (isRoundEnd() && round >= (allBloonWaves.size() - 1));
    }
    return false;
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

  @Override
  public int getLives() {
    return lives;
  }

}
