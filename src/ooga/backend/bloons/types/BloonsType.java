package ooga.backend.bloons.types;



public record BloonsType(BloonsTypeChain chain, String name, int RBE, double relativeSpeed, Specials specials) {

  @Override
  public boolean equals(Object other) {
    if (other instanceof BloonsType otherBloonsType) {
      return this.chain == otherBloonsType.chain && this.name.equals(otherBloonsType.name) &&
          this.RBE == otherBloonsType.RBE && this.relativeSpeed == otherBloonsType.relativeSpeed;
    }
    return false;
  }

}

