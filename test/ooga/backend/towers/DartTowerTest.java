package ooga.backend.towers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.darts.Dart;
import org.junit.jupiter.api.BeforeEach;

class DartTowerTest {

  BloonsTypeChain chain;

  @BeforeEach
  void initializeBloonsTypes() {
    chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsTrue() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 10,10,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 15,30,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 12,22,5,5));
    assertTrue(testTower.checkBalloonInRange(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testCheckBalloonInRangeReturnsFalse() {
    DartTower testTower = new DartTower(0,0,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 10,10,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 15,30,5,5));
    assertFalse(testTower.checkBalloonInRange(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testFindClosestBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 10,10,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 15,30,5,5));
    Bloon expected = new Bloon(chain.getBloonsTypeRecord("RED"), 12,22,5,5);
    bloonsList.add(expected);
    assertEquals(expected, testTower.findClosestBloon(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testFindStrongestBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 10,21,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 10,22,5,5));
    Bloon expected = new Bloon(chain.getBloonsTypeRecord("BLACK"), 13,23,5,5);
    bloonsList.add(expected);
    assertEquals(expected, testTower.findStrongestBloon(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testFindFirstBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    Bloon expected = new Bloon(chain.getBloonsTypeRecord("RED"), 11,22,5,5);
    bloonsList.add(expected);
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 13,23,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 14,24,5,5));
    assertEquals(expected, testTower.findFirstBloon(bloonsList));
  }

  @org.junit.jupiter.api.Test
  void testFindLastBloon() {
    DartTower testTower = new DartTower(10,20,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 11,22,5,5));
    bloonsList.add(new Bloon(chain.getBloonsTypeRecord("RED"), 13,24,5,5));
    Bloon expected = new Bloon(chain.getBloonsTypeRecord("RED"), 14,23,5,5);
    bloonsList.add(expected);
    assertEquals(expected, testTower.findLastBloon(bloonsList));
  }


  @org.junit.jupiter.api.Test
  void testGetDistance() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(chain.getBloonsTypeRecord("RED"), 3,4,5,5);
    assertEquals(5, testTower.getDistance(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootXVelocity() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(chain.getBloonsTypeRecord("RED"), 3,4,5,5);
    assertEquals(-12, testTower.findShootXVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testFindShootYVelocity() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(chain.getBloonsTypeRecord("RED"), 3,4,5,5);
    assertEquals(-16, testTower.findShootYVelocity(target));
  }

  @org.junit.jupiter.api.Test
  void testShootAtBloon() {
    DartTower testTower = new DartTower(0,0,5);
    Bloon target = new Bloon(chain.getBloonsTypeRecord("RED"), 3,4,5,5);
    List<Bloon> bloonsList = new ArrayList<>();
    bloonsList.add(target);
    List<Dart> dart = testTower.shoot(bloonsList);
    assertEquals(0, dart.get(0).getXPosition());
    assertEquals(0, dart.get(0).getYPosition());
    assertEquals(-12, dart.get(0).getXVelocity());
    assertEquals(-16, dart.get(0).getYVelocity());
  }
}