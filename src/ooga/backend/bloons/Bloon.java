package ooga.backend.bloons;


import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;
import ooga.backend.API.BloonsAPI;
import ooga.backend.GamePiece;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

public class Bloon extends GamePiece implements BloonsAPI {

  private static final String FACTORY_FILE_PATH = "ooga.backend.bloons.factory.";

  private BloonsType bloonsType;
  private double xVelocity;
  private double yVelocity;
  private double distanceTraveled;
  private final double relativeSpeed;

  public Bloon(BloonsType bloonsType, double xPosition, double yPosition, double xVelocity, double yVelocity) {
    super(xPosition, yPosition);
    this.bloonsType = bloonsType;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.distanceTraveled = 0;
    this.relativeSpeed = bloonsType.relativeSpeed();
  }

  public BloonsType getBloonsType(){
    return bloonsType;
  }

  @Override
  public void setXVelocity(double newXVelocity) {
    xVelocity = newXVelocity;
  }

  @Override
  public void setYVelocity(double newYVelocity) {
    yVelocity = newYVelocity;
  }

  @Override
  public Bloon[] shootBloon() {
    int numBloonsToProduce = getBloonsType().chain().getNumNextBloons(bloonsType);

    if (getBloonsType().specials().size() == 0) {
      Bloon[] bloons = new Bloon[numBloonsToProduce];
      BasicBloonsFactory factory = new BasicBloonsFactory();
      for (int i = 0; i < numBloonsToProduce; i++) {
        bloons[i] = factory.createNextBloon(this);
      }
      return bloons;
    }

    return makeNextSpecialBloons(numBloonsToProduce);
  }

  @Override
  public void update() {
    updatePosition();
  }

  public double getDistanceTraveled() {
    return distanceTraveled;
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public String toString(){
    return "" + bloonsType.name();
  }

  protected void setBloonsType(BloonsType type) {
    bloonsType = type;
  }

  private void updateDistanceTraveled() {
    distanceTraveled += (Math.abs(xVelocity) + Math.abs(yVelocity)) * relativeSpeed;
  }

  private void updatePosition() {
    setXPosition(getXPosition() + xVelocity * relativeSpeed);
    setYPosition(getYPosition() + yVelocity * relativeSpeed);
    updateDistanceTraveled();
  }

  public void setDead(){
    setBloonsType(bloonsType.chain().getBloonsTypeRecord("DEAD"));
  }

  public boolean isDead(){
    return bloonsType == bloonsType.chain().getBloonsTypeRecord("DEAD");
  }

  public boolean isCamo(){
    return getBloonsType().specials().contains(Specials.CAMO);
  }

  private Bloon[] makeNextSpecialBloons(int numBloonsToProduce) {
    Bloon[] bloons = new Bloon[numBloonsToProduce];
    for (Specials special : getBloonsType().specials()) {
      for (int i = 0; i < numBloonsToProduce; i++) {
        try {
          String specialName = special.toString();
          specialName = specialName.substring(0,1).toUpperCase() +
              specialName.substring(1).toLowerCase();
          Class<?> specialBloonClass = Class.forName(FACTORY_FILE_PATH + specialName + "BloonsFactory");
          Constructor<?> specialBloonConstructor = specialBloonClass.getConstructor();
          BloonsFactory specialFactory = (BloonsFactory)specialBloonConstructor.newInstance();
          if (bloons[i] != null) {
            bloons[i] = specialFactory.createBloon(bloons[i]);
          } else {
            bloons[i] = specialFactory.createNextBloon(this);
          }
        } catch (ClassNotFoundException e) {
          //TODO: handle
        } catch (Exception e) {
          //TODO: handle
        }
      }
    }
    return bloons;
  }
}
