package ooga.backend.factory;

import ooga.backend.darts.Dart;

public interface DartFactory {
  Dart createDart(int xPosition, int yPosition, int xVelocity, int yVelocity);
}
