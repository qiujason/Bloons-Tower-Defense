package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsType;
import ooga.backend.darts.Dart;
import org.junit.jupiter.api.Test;

class TackShooterTest {

  @Test
  void testShootBalloonInRange() {
    TackShooter testTower = new TackShooter(0,0,5);
    Bloon target = new Bloon(BloonsType.RED, 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Dart> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(1, dart.get(0).getXVelocity());
    assertEquals(0, dart.get(0).getYVelocity());

    assertEquals(0, dart.get(1).getXPosition());
    assertEquals(0, dart.get(1).getYPosition());
    assertEquals(Math.cos(45), dart.get(1).getXVelocity());
    assertEquals(Math.sin(45), dart.get(1).getYVelocity());

    assertEquals(0, dart.get(2).getXPosition());
    assertEquals(0, dart.get(2).getYPosition());
    assertEquals(Math.cos(90), dart.get(2).getXVelocity());
    assertEquals(Math.sin(90), dart.get(2).getYVelocity());

    assertEquals(0, dart.get(3).getXPosition());
    assertEquals(0, dart.get(3).getYPosition());
    assertEquals(Math.cos(135), dart.get(3).getXVelocity());
    assertEquals(Math.sin(135), dart.get(3).getYVelocity());

    assertEquals(0, dart.get(4).getXPosition());
    assertEquals(0, dart.get(4).getYPosition());
    assertEquals(Math.cos(180), dart.get(4).getXVelocity());
    assertEquals(Math.sin(180), dart.get(4).getYVelocity());

    assertEquals(0, dart.get(5).getXPosition());
    assertEquals(0, dart.get(5).getYPosition());
    assertEquals(Math.cos(225), dart.get(5).getXVelocity());
    assertEquals(Math.sin(225), dart.get(5).getYVelocity());

    assertEquals(0, dart.get(6).getXPosition());
    assertEquals(0, dart.get(6).getYPosition());
    assertEquals(Math.cos(270), dart.get(6).getXVelocity());
    assertEquals(Math.sin(270), dart.get(6).getYVelocity());

    assertEquals(0, dart.get(7).getXPosition());
    assertEquals(0, dart.get(7).getYPosition());
    assertEquals(Math.cos(315), dart.get(7).getXVelocity());
    assertEquals(Math.sin(315), dart.get(7).getYVelocity());
  }

  @Test
  void testShootNoBalloonInRange() {
    TackShooter testTower = new TackShooter(0,0,5);
    Bloon target = new Bloon(BloonsType.RED, 20,20,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Dart> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.size());
  }
}