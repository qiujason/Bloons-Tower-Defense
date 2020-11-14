package ooga.backend;

import java.util.List;
import ooga.backend.API.GameEngineAPI;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.layout.Layout;
import ooga.backend.layout.LayoutBlock;

public class GameEngine implements GameEngineAPI {

  private static final int FIRST_WAVE = 0;

  private final Layout layout;
  private final List<BloonsCollection> allBloonWaves;
  private GamePieceIterator<Bloon> currentBloonsIterator;
  private BloonsCollection currentBloonWave;
//  private final TowersCollection towers;
  private GamePieceIterator<Bloon> towersIterator;

  private int wave;

  public GameEngine(Layout layout, List<BloonsCollection> allBloonWaves) {
    this.layout = layout;
    this.allBloonWaves = allBloonWaves;
    currentBloonWave = allBloonWaves.get(FIRST_WAVE);
    System.out.println(currentBloonWave.get(0).getXPosition());
    currentBloonsIterator = currentBloonWave.createIterator();
//    towers = towersCollection;
//    towersIterator = towers.createIterator();
    wave = FIRST_WAVE;
  }

  @Override
  public void moveBloons() {
//    System.out.println("Bloon: " + currentBloonWave.get(0).getXPosition());
//    System.out.println("Bloon XVel: " + currentBloonWave.get(0).getXVelocity());

    while (currentBloonsIterator.hasNext()) {
      Bloon bloon = (Bloon)currentBloonsIterator.next();
      LayoutBlock currentBlock;
      try{
        currentBlock = layout.getBlock(((int) (bloon.getXPosition()))
            ,((int) (bloon.getYPosition()) ));
      }catch(IndexOutOfBoundsException e){
        currentBlock = layout.getStartingBlock();
      }


      if (currentBlock.isEndBlock()) {
        currentBloonWave.remove(bloon);
      }

      bloon.setXVelocity(bloon.getBloonsType().relativeSpeed() * currentBlock.getDx());
      bloon.setYVelocity(bloon.getBloonsType().relativeSpeed() * currentBlock.getDy());

      bloon.setXPosition(bloon.getXPosition() + bloon.getXVelocity());
      bloon.setYPosition(bloon.getYPosition() + bloon.getYVelocity());
    }

    currentBloonWave.updateAll();
  }

  @Override
  public void shootBloons() {
//    towers.updateAll();
  }

  @Override
  public void nextWave() {
    wave++;
    currentBloonWave = allBloonWaves.get(wave);
    currentBloonsIterator = currentBloonWave.createIterator();
  }

  @Override
  public void resetGame() {
    wave = FIRST_WAVE;
    currentBloonWave = allBloonWaves.get(FIRST_WAVE);
    currentBloonsIterator = currentBloonWave.createIterator();
//    towers.clear();
//    towersIterator = towers.createIterator();
  }

  public BloonsCollection getCurrentBloonWave() {
    return currentBloonWave;
  }

  @Override
  public void update(){
    moveBloons();
  }

}
