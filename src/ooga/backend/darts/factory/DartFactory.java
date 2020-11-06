package ooga.backend.darts.factory;

import ooga.backend.darts.Dart;

public interface DartFactory {
  Dart createDart(double xPosition, double yPosition, double xVelocity, double yVelocity);
}
