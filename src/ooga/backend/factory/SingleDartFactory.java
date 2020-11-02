package ooga.backend.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.darts.Dart;

public class SingleDartFactory implements DartFactory {

  private static String DART_PATH = "ooga.backend.darts.SingleDart";

  @Override
  public Dart createDart(int xPosition, int yPosition, int xVelocity, int yVelocity) {
    try {
      Class<?> dartClass = Class.forName(DART_PATH);
      Constructor<?> cellConstructor = dartClass
          .getDeclaredConstructor(int.class, int.class, int.class, int.class);
      return (Dart) cellConstructor.newInstance(xPosition, yPosition, xVelocity, yVelocity);
    } catch (Exception e) {
      throw new ConfigurationException("No dart class found for created dart.");
    }
  }
}
