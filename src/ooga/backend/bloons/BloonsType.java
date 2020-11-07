package ooga.backend.bloons;

public enum BloonsType {

  DEAD, RED(1, 1, DEAD), BLUE(2, 1.4, RED), GREEN(3, 1.8, BLUE),
  YELLOW(4, 3.2, GREEN), PINK(5, 3.5, YELLOW), BLACK(11, 1.8, PINK, PINK),
  WHITE( 11, 2, PINK, PINK), ZEBRA(23, 1.8, BLACK, WHITE), RAINBOW(47, 2.2, ZEBRA, ZEBRA);

  private final int redBloonEquivalent;
  private final double relativeSpeed;
  private final BloonsType[] nextBloons;

  BloonsType() {
    redBloonEquivalent = 0;
    relativeSpeed = 0;
    nextBloons = new BloonsType[0];
  }

  BloonsType(int RBE, double relSpeed, BloonsType... bloons) {
    redBloonEquivalent = RBE;
    relativeSpeed = relSpeed;
    nextBloons = bloons;
  }

  public int getRBE() {
    return redBloonEquivalent;
  }

  double getRelativeSpeed() {
    return relativeSpeed;
  }

  BloonsType[] getNextBloons() {
    return nextBloons;
  }

}
