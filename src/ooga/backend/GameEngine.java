package ooga.backend;

import ooga.backend.API.GameEngineAPI;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.collections.Iterator;
import ooga.backend.towers.TowersCollection;

public class GameEngine implements GameEngineAPI {

  private final BloonsCollection bloons;
  private final Iterator bloonsIterator;
  private final TowersCollection towers;
  private final Iterator towersIterator;

  private int wave;

  public GameEngine() {
    bloons = new BloonsCollection();
    bloonsIterator = bloons.createIterator();
    towers = new TowersCollection();
    towersIterator = towers.createIterator();
    wave = 1;
  }

  @Override
  public void moveBloons() {
    bloons.updateAll();
  }

  @Override
  public void shootBloons() {
    towers.updateAll();
  }

  @Override
  public void nextWave() {
    wave++;
  }

  @Override
  public void resetGame() {
    wave = 1;
    bloons.clear();
    towers.clear();
  }

}
