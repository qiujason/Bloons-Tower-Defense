package ooga.backend.towers.spreadshottowers;

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
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowerType;
import ooga.backend.towers.factory.SingleTowerFactory;
import ooga.backend.towers.factory.TowerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FrozenSpreadShooterTest {

  TowerFactory towerFactory = new SingleTowerFactory();
  Tower testTower = towerFactory.createTower(TowerType.FrozenSpreadShooter, 0, 0);

  private BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
  }

  @Test
  void testGetTowerType() {
    assertEquals(TowerType.FrozenSpreadShooter, testTower.getTowerType());
  }

  @Test
  void shoot() {
    Bloon target = new Bloon(chain, new BloonsType("RED", 1, 1, new HashSet<>()), 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Projectile> dart = testTower.shoot(bloonsCollection);
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(0).getType());
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(1, dart.get(0).getXVelocity());
    assertEquals(0, dart.get(0).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(1).getType());
    assertEquals(0, dart.get(1).getXPosition());
    assertEquals(0, dart.get(1).getYPosition());
    assertEquals(Math.cos(45), dart.get(1).getXVelocity());
    assertEquals(Math.sin(45), dart.get(1).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(2).getType());
    assertEquals(0, dart.get(2).getXPosition());
    assertEquals(0, dart.get(2).getYPosition());
    assertEquals(Math.cos(90), dart.get(2).getXVelocity());
    assertEquals(Math.sin(90), dart.get(2).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(3).getType());
    assertEquals(0, dart.get(3).getXPosition());
    assertEquals(0, dart.get(3).getYPosition());
    assertEquals(Math.cos(135), dart.get(3).getXVelocity());
    assertEquals(Math.sin(135), dart.get(3).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(4).getType());
    assertEquals(0, dart.get(4).getXPosition());
    assertEquals(0, dart.get(4).getYPosition());
    assertEquals(Math.cos(180), dart.get(4).getXVelocity());
    assertEquals(Math.sin(180), dart.get(4).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(5).getType());
    assertEquals(0, dart.get(5).getXPosition());
    assertEquals(0, dart.get(5).getYPosition());
    assertEquals(Math.cos(225), dart.get(5).getXVelocity());
    assertEquals(Math.sin(225), dart.get(5).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(6).getType());
    assertEquals(0, dart.get(6).getXPosition());
    assertEquals(0, dart.get(6).getYPosition());
    assertEquals(Math.cos(270), dart.get(6).getXVelocity());
    assertEquals(Math.sin(270), dart.get(6).getYVelocity());

    assertEquals(ProjectileType.FreezeTargetProjectile, dart.get(7).getType());
    assertEquals(0, dart.get(7).getXPosition());
    assertEquals(0, dart.get(7).getYPosition());
    assertEquals(Math.cos(315), dart.get(7).getXVelocity());
    assertEquals(Math.sin(315), dart.get(7).getYVelocity());
  }
}