package ooga.backend.bloons;

public enum BloonsType {

  DEAD(0), RED(1, DEAD), BLUE(2, RED), GREEN(3, BLUE),
  YELLOW(4, GREEN), PINK(5, YELLOW), BLACK(11, PINK, PINK),
  WHITE( 11, PINK, PINK), ZEBRA(23, BLACK, WHITE), RAINBOW(47, ZEBRA, ZEBRA);

  private final int redBloonEquivalent;
  private final BloonsType[] nextBloons;

  BloonsType(int RBE, BloonsType... bloons) {
    redBloonEquivalent = RBE;
    nextBloons = bloons;
  }

  int getRBE() {
    return redBloonEquivalent;
  }

  BloonsType[] getNextBloons() {
    return nextBloons;
  }

}
