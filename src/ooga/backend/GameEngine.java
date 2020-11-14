package ooga.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.backend.API.GameEngineAPI;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.layout.Layout;
import ooga.backend.layout.LayoutBlock;
import ooga.visualization.AnimationHandler;
import ooga.visualization.nodes.BloonNode;

public class GameEngine implements GameEngineAPI {

  private static final int FIRST_WAVE = 0;
  public static final int SPAWN_DELAY = (int) (1 * AnimationHandler.FRAMES_PER_SECOND);


  private final Layout layout;
  private final List<BloonsCollection> allBloonWaves;
  private BloonsCollection currentBloonWave;
  private BloonsCollection queuedBloons;
  private int spawnTimer;
  private double myBlockSize;
//  private final TowersCollection towers;
  private GamePieceIterator<Bloon> towersIterator;
  private Map<Bloon, Double > myBloonSidesX;
  private Map<Bloon, Double> myBloonSidesY;

  private int wave;

  public GameEngine(Layout layout, List<BloonsCollection> allBloonWaves, double blockSize) {
    this.layout = layout;
    this.allBloonWaves = allBloonWaves;
    queuedBloons = allBloonWaves.get(FIRST_WAVE);
    currentBloonWave = new BloonsCollection();
//    towers = towersCollection;
//    towersIterator = towers.createIterator();
    myBlockSize = blockSize;
    wave = FIRST_WAVE;
    spawnTimer = SPAWN_DELAY;
    System.out.println(queuedBloons.get(0).getBloonsType().name());
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
      //System.out.println((int) (bloon.getXPosition()) + " " + (int) bloon.getYPosition());
      currentBlock = layout.getBlock((int) (bloon.getYPosition() + myBloonSidesY.get(bloon))
          ,(int) (bloon.getXPosition() + myBloonSidesX.get(bloon)));

      bloon.setXVelocity((bloon.getBloonsType().relativeSpeed() * currentBlock.getDx()/myBlockSize));
      bloon.setYVelocity((bloon.getBloonsType().relativeSpeed() * currentBlock.getDy()/myBlockSize));

      setBloonSides(bloon, currentBlock);
    }

    currentBloonWave.updateAll();
  }

  private void setBloonSides(Bloon bloon, LayoutBlock block) {
    myBloonSidesX.put(bloon, -0.5 * block.getDx());
    myBloonSidesY.put(bloon, -0.5 * block.getDy());
  }

  public boolean isMiddleOfBlock(double x, double y){
    double xDecimal = x - (int) x;
    double yDecimal = y - (int) y;
    return (xDecimal > 0.45 && xDecimal < 0.55) && (yDecimal > 0.45 && yDecimal < 0.55);

  }

  @Override
  public void shootBloons() {
//    towers.updateAll();
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

  public BloonsCollection getCurrentBloonWave() {
    return currentBloonWave;
  }

  @Override
  public void update(){
    if (spawnTimer == SPAWN_DELAY){
      addQueuedBloon();
      spawnTimer = 0;
    }
    moveBloons();
    spawnTimer++;
  }

}
