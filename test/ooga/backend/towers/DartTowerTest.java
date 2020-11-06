package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloons;
import ooga.backend.darts.Dart;

class DartTowerTest {

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsTrue() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloons> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloons(1, 10,10,5,5));
    bloonsList.add(new Bloons(1, 15,30,5,5));
    bloonsList.add(new Bloons(1, 12,22,5,5));
    assertTrue(testTower.checkBalloonInRange(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsFalse() {
    DartTower testTower = new DartTower(0,0,5);
    List<Bloons> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloons(1, 10,10,5,5));
    bloonsList.add(new Bloons(1, 15,30,5,5));
    assertFalse(testTower.checkBalloonInRange(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testFindClosestBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloons> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloons(1, 10,10,5,5));
    bloonsList.add(new Bloons(1, 15,30,5,5));
    Bloons expected = new Bloons(1, 12,22,5,5);
    bloonsList.add(expected);
    assertEquals(expected, testTower.findClosestBloon(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testGetDistance() {
    DartTower testTower = new DartTower(0,0,5);
    Bloons target = new Bloons(1, 3,4,5,5);
    assertEquals(5, testTower.getDistance(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootXVelocity() {
    DartTower testTower = new DartTower(0,0,5);
    Bloons target = new Bloons(1, 3,4,5,5);
    assertEquals(-12, testTower.findShootXVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootYVelocity() {
    DartTower testTower = new DartTower(0,0,5);
    Bloons target = new Bloons(1, 3,4,5,5);
    assertEquals(-16, testTower.findShootYVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void shoot() {
    DartTower testTower = new DartTower(0,0,5);
    Bloons target = new Bloons(1, 3,4,5,5);
    List<Bloons> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    List<Dart> dart = testTower.shoot(bloonsList);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-12, dart.get(0).getXVelocity());
    assertEquals(-16, dart.get(0).getYVelocity());
  }
}