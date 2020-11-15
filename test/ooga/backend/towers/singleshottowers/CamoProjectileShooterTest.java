package ooga.backend.towers.singleshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.CamoBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CamoProjectileShooterTest {

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testShootAt(){
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    Bloon target = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 14, 23, 0, 0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Projectile> dart = testTower.shoot(bloonsCollection);
    assertEquals(10, dart.get(0).getXPosition());
    assertEquals(20, dart.get(0).getYPosition());
    assertEquals(-16, dart.get(0).getXVelocity());
    assertEquals(-12, dart.get(0).getYVelocity());
  }

  @Test
  void testCheckBalloonInRangeWithCamo() {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    Bloon target = new CamoBloonsFactory().createBloon(chain.getBloonsTypeRecord("RED"), 14, 23, 0, 0);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }

  @Test
  void testGetProjectileType() {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    assertEquals(ProjectileType.CamoTargetProjectile, testTower.getProjectileType());
  }

  @Test
  void testGetTowerType() {
    TowerFactory towerFactory = new SingleTowerFactory();
    CamoProjectileShooter testTower = (CamoProjectileShooter)
        towerFactory.createTower(TowerType.CamoProjectileShooter, 10,20);
    assertEquals(TowerType.CamoProjectileShooter, testTower.getTowerType());
  }
}