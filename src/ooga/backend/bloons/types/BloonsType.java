package ooga.backend.bloons.types;


import java.util.Set;

public record BloonsType(BloonsTypeChain chain, String name, int RBE, double relativeSpeed, Set<Specials> specials) {

  @Override
  public boolean equals(Object bloonsType) {
    if (bloonsType instanceof BloonsType otherBloonsType) {
      return this.chain == otherBloonsType.chain && this.name.equals(otherBloonsType.name) && this.RBE == otherBloonsType.RBE &&
          this.relativeSpeed == otherBloonsType.relativeSpeed;
    }
    return false;
  }

}

