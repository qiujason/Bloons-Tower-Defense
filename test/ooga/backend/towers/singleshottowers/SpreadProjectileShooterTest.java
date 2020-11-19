package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpreadProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testShootSpreadProjectile() throws ConfigurationException {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SpreadProjectileShooter, 0,0);
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, Specials.None), 0.3,0.4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    ProjectilesCollection projectilesCollection = new ProjectilesCollection();
    testTower.shoot(bloonsCollection, projectilesCollection);
    GamePieceIterator<Projectile> iterator = projectilesCollection.createIterator();
    Projectile dart = iterator.next();
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(0.12, dart.getXVelocity());
    assertEquals(0.16, dart.getYVelocity());
    assertEquals(ProjectileType.SpreadProjectile, dart.getType());
  }
}