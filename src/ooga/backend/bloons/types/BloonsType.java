package ooga.backend.bloons.types;

/**
 * Record class that creates a new bloon type
 * @param chain BloonsTypeChain that is the chain for all bloon types
 * @param name name of the bloon type
 * @param RBE red bloon equivalent that represents its relative strength to a unit bloon
 * @param relativeSpeed speed relative to a unit bloon
 * @param specials Specials enum that represents a special type of the bloon
 *
 * @author Jason Qiu
 */
public record BloonsType(BloonsTypeChain chain, String name, int RBE, double relativeSpeed,
                         Specials specials) {

  /**
   * overrides equals method so that equivalent bloons do not need equivalent special types
   * @param other other bloon type
   * @return boolean representing if the bloontype's chain, name, red bloon equivalent, and relative
   * speed are equal
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof BloonsType otherBloonsType) {
      return this.chain == otherBloonsType.chain && this.name.equals(otherBloonsType.name) &&
          this.RBE == otherBloonsType.RBE && this.relativeSpeed == otherBloonsType.relativeSpeed;
    }
    return false;
  }

}

