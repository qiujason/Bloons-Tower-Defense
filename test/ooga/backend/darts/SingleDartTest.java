package ooga.backend.darts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SingleDartTest {

  @Test
  void updatePosition() {
    Dart dart = new SingleDart(10,15,-5,5);
    dart.updatePosition();
    assertEquals(5, dart.getXPosition());
    assertEquals(20, dart.getYPosition());
  }

  @Test
  void setXVelocity() {
    Dart dart = new SingleDart(10,15,-5,5);
    dart.setXVelocity(0);
    assertEquals(0, dart.getXVelocity());
  }

  @Test
  void setYVelocity() {
    Dart dart = new SingleDart(10,15,-5,5);
    dart.setYVelocity(0);
    assertEquals(0, dart.getYVelocity());
  }

  @Test
  void getXPosition() {
    Dart dart = new SingleDart(10,15,-5,5);
    assertEquals(10, dart.getXPosition());
  }

  @Test
  void getYPosition() {
    Dart dart = new SingleDart(10,15,-5,5);
    assertEquals(15, dart.getYPosition());
  }

  @Test
  void getXVelocity() {
    Dart dart = new SingleDart(10,10,-5,5);
    assertEquals(-5, dart.getXVelocity());
  }

  @Test
  void getYVelocity() {
    Dart dart = new SingleDart(10,10,-5,5);
    assertEquals(5, dart.getYVelocity());
  }
}