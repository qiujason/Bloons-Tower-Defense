package ooga.backend.bloons.types;


import java.util.Set;

public record BloonsType(BloonsTypeChain chain, String name, int RBE, double relativeSpeed, Set<Specials> specials) {}

