package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsType;
import ooga.backend.darts.Dart;

class DartTowerTest {

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsTrue() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 10,10,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 15,30,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 12,22,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertTrue(testTower.checkBalloonInRange(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsFalse() {
    DartTower testTower = new DartTower(0,0,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 10,10,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 15,30,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertFalse(testTower.checkBalloonInRange(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindClosestBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 10,10,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 15,30,5,5));
    Bloon expected = new Bloon(BloonsType.RED, 12,22,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findClosestBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindStrongestBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 10,21,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 10,22,5,5));
    Bloon expected = new Bloon(BloonsType.BLACK, 13,23,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findStrongestBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindFirstBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    Bloon expected = new Bloon(BloonsType.RED, 11,22,5,5);
    bloonsList.add(expected);
    bloonsList.add(new Bloon(BloonsType.RED, 13,23,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 14,24,5,5));
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findFirstBloon(bloonsCollection));
  }

  @org.junit.jupiter.api.Test
  void testFindLastBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(BloonsType.RED, 11,22,5,5));
    bloonsList.add(new Bloon(BloonsType.RED, 13,24,5,5));
    Bloon expected = new Bloon(BloonsType.RED, 14,23,5,5);
    bloonsList.add(expected);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    assertEquals(expected, testTower.findLastBloon(bloonsCollection));
  }


  @org.junit.jupiter.api.Test
  void testGetDistance() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(BloonsType.RED, 3,4,5,5);
    assertEquals(5, testTower.getDistance(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootXVelocity() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(BloonsType.RED, 3,4,5,5);
    assertEquals(-12, testTower.findShootXVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootYVelocity() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(BloonsType.RED, 3,4,5,5);
    assertEquals(-16, testTower.findShootYVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testShootAtBloon() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(BloonsType.RED, 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    BloonsCollection bloonsCollection = new BloonsCollection(bloonsList);
    List<Dart> dart = testTower.shoot(bloonsCollection);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-12, dart.get(0).getXVelocity());
    assertEquals(-16, dart.get(0).getYVelocity());
  }
}