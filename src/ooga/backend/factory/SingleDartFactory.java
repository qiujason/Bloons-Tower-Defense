package ooga.backend.factory;

import java.lang.reflect.Constructor;
import ooga.backend.ConfigurationException;
import ooga.backend.darts.Dart;

public class SingleDartFactory implements DartFactory {

  @Override
  public Dart createDart(int xPosition, int yPosition, double xVelocity, double yVelocity) {
    String DART_PATH = "ooga.backend.darts.SingleDart";
    try {
      Class<?> dartClass = Class.forName(DART_PATH);
      Constructor<?> cellConstructor = dartClass
          .getDeclaredConstructor(int.class, int.class, double.class, double.class);
      return (Dart) cellConstructor.newInstance(xPosition, yPosition, xVelocity, yVelocity);
    } catch (Exception e) {
      throw new ConfigurationException("No dart class found for created dart.");
    }
  }
}
