package ooga.backend.bloons;

public enum BloonsType {
  DEAD, RED(1, 1, DEAD), BLUE(1, 2, RED), GREEN(1, 3, BLUE),
  YELLOW(1, 4, GREEN), PINK(1, 5, YELLOW), BLACK(2, 11, PINK),
  WHITE(2, 11, PINK);

  private final int numBloonsInside;
  private final int redBloonEquivalent;
  private final BloonsType nextBloon;

  BloonsType() {
    numBloonsInside = 0;
    redBloonEquivalent = 0;
    nextBloon = null;
  }

  BloonsType(int num, int RBE, BloonsType bloon) {
    numBloonsInside = num;
    redBloonEquivalent = RBE;
    nextBloon = bloon;
  }

}
