package ooga.backend.API;

import java.util.Map;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.roaditems.RoadItemsCollection;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;

public interface GameEngineAPI {

  void update();

  BloonsCollection getCurrentBloonWave();

  ProjectilesCollection getProjectiles();

  TowersCollection getTowers();

  RoadItemsCollection getRoadItems();

  boolean isRoundEnd();

  boolean isGameEnd();

  int getRound();

  Map<Tower, Bloon> getShootingTargets();

  void setTowers(TowersCollection towers);

  void setProjectiles(ProjectilesCollection projectiles);

  void setRoadItems(RoadItemsCollection roadItems);
}
