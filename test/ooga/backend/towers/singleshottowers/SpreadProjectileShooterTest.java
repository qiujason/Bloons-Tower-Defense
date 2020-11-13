package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpreadProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
  }

  @Test
  void testShootSpreadProjectile() {
    TowerFactory towerFactory = new SingleTowerFactory();
    SingleShotTower testTower = (SingleShotTower)
        towerFactory.createTower(TowerType.SpreadProjectileShooter, 0,0);
    Bloon target = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Projectile> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-12, dart.get(0).getXVelocity());
    assertEquals(-16, dart.get(0).getYVelocity());
    assertEquals(ProjectileType.SpreadProjectile, dart.get(0).getType());
  }
}