package ooga.backend.towers.spreadshottowers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.projectile.Projectile;
import ooga.backend.projectile.ProjectileType;
import ooga.backend.projectile.ProjectilesCollection;
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
    chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
  }

  @Test
  void testGetTowerType() {
    assertEquals(TowerType.FrozenSpreadShooter, testTower.getTowerType());
  }

  @Test
  void shoot() {
    Bloon target = new Bloon(new BloonsType(chain, "RED", 1, 1, new HashSet<>()), 0.4,0.4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    ProjectilesCollection projectilesCollection = new ProjectilesCollection();
    testTower.shoot(bloonsCollection, projectilesCollection);
    GamePieceIterator<Projectile> iterator = projectilesCollection.createIterator();
    Projectile dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(1, dart.getXVelocity());
    assertEquals(0, dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(45), dart.getXVelocity());
    assertEquals(Math.sin(45), dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(90), dart.getXVelocity());
    assertEquals(Math.sin(90), dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(135), dart.getXVelocity());
    assertEquals(Math.sin(135), dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(180), dart.getXVelocity());
    assertEquals(Math.sin(180), dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(225), dart.getXVelocity());
    assertEquals(Math.sin(225), dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(270), dart.getXVelocity());
    assertEquals(Math.sin(270), dart.getYVelocity());

    dart = iterator.next();
    assertEquals(ProjectileType.FreezeTargetProjectile, dart.getType());
    assertEquals(0, dart.getXPosition());
    assertEquals(0, dart.getYPosition());
    assertEquals(Math.cos(315), dart.getXVelocity());
    assertEquals(Math.sin(315), dart.getYVelocity());
  }
}