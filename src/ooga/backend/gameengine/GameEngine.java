package ooga.backend.gameengine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ooga.AlertHandler;
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
import ooga.visualization.animationhandlers.AnimationHandler;

public class GameEngine implements GameEngineAPI {

  private static final int STARTING_ROUND = 0;
  public static final int STARTING_LIVES = 100;
  private static final int SPAWN_DELAY = (int) (1 * AnimationHandler.FRAMES_PER_SECOND);
  private static final int SPEED_ADJUSTER = 50;


  private final Layout layout;
  private final List<BloonsCollection> allBloonWaves;
  private final BloonsCollection currentBloonWave;
  private BloonsCollection queuedBloons;
  private final Map<Tower, Bloon> shootingTargets;
  private int spawnTimer;
  private TowersCollection towers;
  private RoadItemsCollection roadItems;

  private ProjectilesCollection projectiles;
  private final Map<Bloon, Double> myBloonSidesX;
  private final Map<Bloon, Double> myBloonSidesY;

  private int round;

  private int lives = STARTING_LIVES;
  private final GameMode gameMode;

  /**
   * Creates an instance of the GameEngine class based on the game mode, layout, bloon waves
   * @param gameMode
   * @param layout
   * @param allBloonWaves
   * @throws ConfigurationException if bloon waves are not read in properly
   */
  public GameEngine(GameMode gameMode, Layout layout, List<BloonsCollection> allBloonWaves)
      throws ConfigurationException {
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

  /**
   * Adds a queued bloon onto the map.
   */
  private void addQueuedBloon(){
    GamePieceIterator<Bloon> queueIterator = queuedBloons.createIterator();
    if (queueIterator.hasNext()) {
      Bloon queuedBloon = queueIterator.next();
      if (queuedBloon.getBloonsType().name().equals("DEAD")) {
        queuedBloons.remove(queuedBloon);
        return;
      }
      currentBloonWave.add(queuedBloon);
      queuedBloons.remove(queuedBloon);
    }
  }

  /**
   * Moves all existing bloons on the map. This method reads the direction in which the bloon should
   * move from the LayoutBlock that the Bloon is currently on, sets the x and y velocities accordingly,
   * and then calls the update function from the GamePiece API.
   *
   * Part of the GameEngine API.
   */
  public void moveBloons() {
    removeDeadBloons();
    GamePieceIterator<Bloon> waveIterator = currentBloonWave.createIterator();
    while (waveIterator.hasNext()) {
      Bloon bloon = waveIterator.next();
      myBloonSidesX.putIfAbsent(bloon, 0.0);
      myBloonSidesY.putIfAbsent(bloon, 0.0);
      LayoutBlock currentBlock;
      currentBlock = layout.getBlock((int) (bloon.getYPosition() + myBloonSidesY.get(bloon))
          , (int) (bloon.getXPosition() + myBloonSidesX.get(bloon)));
      if (currentBlock.isEndBlock()) {
        lives -= bloon.getBloonsType().RBE();
        bloon.setDead();
      }
      bloon.setXVelocity(
          (bloon.getBloonsType().relativeSpeed() * currentBlock.getDx()) / SPEED_ADJUSTER);
      bloon.setYVelocity(
          (bloon.getBloonsType().relativeSpeed() * currentBlock.getDy()) / SPEED_ADJUSTER);
      setBloonSides(bloon, currentBlock);
    }
    currentBloonWave.updateAll();
  }

  /**
   * Helper method that helps to ensure that a bloon only changes direction in the middle of the block
   * @param bloon the bloon that is being moved
   * @param block the block that the bloon is currently on
   */
  private void setBloonSides(Bloon bloon, LayoutBlock block) {
    myBloonSidesX.put(bloon, -0.5 * block.getDx());
    myBloonSidesY.put(bloon, -0.5 * block.getDy());
  }

  /**
   * helper method to remove dead bloons from the current BloonsCollection
   */
  private void removeDeadBloons() {
    GamePieceIterator<Bloon> bloonsIterator = currentBloonWave.createIterator();
    while (bloonsIterator.hasNext()) {
      Bloon bloon = bloonsIterator.next();
      if (bloon.isDead()) {
        currentBloonWave.remove(bloon);
      }
    }
  }

  /**
   * The nextwave method for the Normal game mode
   * @throws ConfigurationException
   */
  private void nextWaveNormal() throws ConfigurationException {
    round++;
    queuedBloons = allBloonWaves.get(round).copyOf(allBloonWaves.get(round));
  }

  /**
   * Nextwave method for the Infinite game mode
   * @throws ConfigurationException if the new bloon wave round cannot be found and loaded in
   */
  private void nextWaveInfinite() throws ConfigurationException {
    round++;
    Random rand = new Random();
    int randomRound = rand.nextInt(allBloonWaves.size());
    queuedBloons = allBloonWaves.get(randomRound).copyOf(allBloonWaves.get(randomRound));
  }

  /**
   * Next wave method for the Sandbox game mode
   * @throws ConfigurationException
   */
  private void nextWaveSandbox() throws ConfigurationException {
    nextWaveNormal();
  }

  public void resetGame() {
    round = STARTING_ROUND;
    queuedBloons = allBloonWaves.get(STARTING_ROUND);
  }

  /**
   * Helper method to iterate through the TowersCollections and have each tower shoot at a bloon
   * if it can.
   * @throws ConfigurationException if, when shooting, the correct dart type is not found.
   */
  private void shootBloons() throws ConfigurationException {
    GamePieceIterator<Tower> towerIterator = towers.createIterator();
    while (towerIterator.hasNext()) {
      Tower currentTower = towerIterator.next();
      currentTower.update();
      if (!currentTower.isIfRestPeriod()) {
        shootingTargets.put(currentTower, currentTower.shoot(currentBloonWave, projectiles));
      }
    }
  }

  /**
   * Moves all of the current on-screen projectiles.
   * Assumes all projectiles on screen should be moved
   */
  private void moveProjectiles() {
    removeOffScreenProjectiles();
    projectiles.updateAll();
  }

  //removes projectiles that have gone beyond the boundaries of the layout
  private void removeOffScreenProjectiles() {
    GamePieceIterator<Projectile> projectileIterator = projectiles.createIterator();
    while (projectileIterator.hasNext()) {
      Projectile currentProjectile = projectileIterator.next();

      if (currentProjectile.getXPosition() < 0 || currentProjectile.getXPosition() > layout
          .getWidth()
          || currentProjectile.getYPosition() < 0 || currentProjectile.getYPosition() > layout
          .getHeight()) {
        projectiles.remove(currentProjectile);
      }
    }
  }

  //calls the corrent next wave method based on the game mode
  private void nextWave() {
    try {
      Method method = this.getClass()
          .getDeclaredMethod("nextWave" + this.gameMode);
      method.invoke(this);
    } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      new AlertHandler("Missing Functionality", "No nextWave method for this game type found");
    }
  }

  /**
   * Returns a BloonsCollection object containing the current bloons that exist on the map.
   * Does not include not spawned bloons or dead bloons.
   * @return the current bloons on screen as a BloonsCollection
   *
   * Part of the GameEngine API
   */
  @Override
  public BloonsCollection getCurrentBloonWave() {
    return currentBloonWave;
  }

  /**
   * Returns a TowersCollection object containing the current existing towers on the map.
   * @return the current TowersCollection
   *
   * Part of the GameEngine API
   */
  @Override
  public TowersCollection getTowers() {
    return towers;
  }

  /**
   * Returns a ProjectilesCollection containing the current existing projectiles on the map.
   * Does not include Projectiles that have gone off the game window screen
   * @return the current on screen projectiles as a ProjectilesCollection
   *
   * Part of the GameEngine API
   */
  @Override
  public ProjectilesCollection getProjectiles() {
    return projectiles;
  }

  /**
   * Updates the current TowersCollection to the passed through TowersCollection object.
   * @param towers the updated TowersCollection to be set as the current one
   *
   * Part of the GameEngine API
   */
  @Override
  public void setTowers(TowersCollection towers) {
    this.towers = towers;
  }

  /**
   * Updates the current ProjectilesCollection to the passed through ProjectilesCollection object.
   * @param projectiles the updated ProjectilesCollection to be set as the current one
   *
   * Part of the GameEngine API
   */
  @Override
  public void setProjectiles(ProjectilesCollection projectiles) {
    this.projectiles = projectiles;
  }

  /**
   * Updates all existing game objects on-screen and checks for the game status.
   * This includes moving objects, shooting objects, removing objects, and checking for
   * round-over/win/loss.
   * @throws ConfigurationException if the current dart type is not found when a tower shoots at a bloon
   *
   * Part of the GameEngine API
   */
  @Override
  public void update() throws ConfigurationException {
    checkRoundStatus();
    spawnBloons();
    moveBloons();
    moveProjectiles();
    shootBloons();
  }

  //if the round has ended, the next wave of bloons is loaded in
  private void checkRoundStatus() {
    if (isRoundEnd()) {
      nextWave();
    }
  }

  //spawns queued bloons once every second
  private void spawnBloons() {
    if (spawnTimer == SPAWN_DELAY) {
      addQueuedBloon();
      spawnTimer = 0;
    }
    spawnTimer++;
  }

  /**
   * Returns true if the round has ended.
   * Specific conditions for round ending are that there are no more bloons to be spawned,
   * there are no more bloons on the screen, and there are no more projectiles on the screen.
   * @return true if the round has ended
   *
   * Part of the GameEngine API
   */
  @Override
  public boolean isRoundEnd() {
    return queuedBloons.isEmpty() && currentBloonWave.isEmpty() && projectiles.isEmpty();
  }

  /**
   * Returns true if the game is ended.
   * Specific conditions for round ending are that either lives drops below 0 or all
   * of the rounds have been run through and ended.
   * Will always return false for infinite mode.
   * @return true if the game has ended.
   *
   * Part of the GameEngine API
   */
  @Override
  public boolean isGameEnd() {
    return lives <= 0 || (gameMode != GameMode.Infinite && (isRoundEnd() && round >= (
        allBloonWaves.size() - 1)));
  }

  /**
   * Returns the current round number
   * @return the current round
   *
   * Part of the GameEngine API
   */
  @Override
  public int getRound() {
    return round;
  }

  /**
   * Returns a Map mapping the tower to the bloon it is targeting.
   * @return the current Map of shooting targets
   *
   * Part of the GameEngine API
   */
  @Override
  public Map<Tower, Bloon> getShootingTargets() {
    return shootingTargets;
  }

  /**
   * Returns a RoadItemsCollection containing the existing RoadItems on the screen
   * @return the current RoadItemsCollection
   *
   * Part of the GameEngine API
   */
  @Override
  public RoadItemsCollection getRoadItems() {
    return roadItems;
  }

  /**
   * Updates the current RoadItemsCollection to the passed through RoadItemsCollection object.
   * @param update the updated RoadItemsCollection to be set as the current oen
   *
   * Part of the GameEngine API
   */
  @Override
  public void setRoadItems(RoadItemsCollection update) {
    roadItems = update;
  }

  /**
   * Returns the current number of lives remaining.
   * @return the current number of lives
   *
   * Part of the GameEngine API
   */
  @Override
  public int getLives() {
    return lives;
  }

}
