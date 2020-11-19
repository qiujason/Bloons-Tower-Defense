package ooga.backend.readers;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.factory.BloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.layout.Layout;

public class BloonReader extends Reader{

  private static final ResourceBundle ERROR_RESOURCES = ResourceBundle.getBundle("ErrorResource");
  private static final String FACTORY_FILE_PATH = "ooga.backend.bloons.factory.";
  private static final String RESOURCE_BUNDLE_PATH = "bloon_resources/BloonReaderKey";
  private static final ResourceBundle BLOON_READER_KEY = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);

  @Override
  public List<List<String>> getDataFromFile(String fileName) {
    List<String[]> csvData = readFile(fileName);
    List<List<String>> bloonWave = new ArrayList<>();
    for(String[] row : csvData){
      bloonWave.add(Arrays.asList(row));
    }
    return bloonWave;
  }

  public List<BloonsCollection> generateBloonsCollectionMap(BloonsTypeChain chain, String fileName, Layout layout)
      throws ConfigurationException { //TODO: create test for seeing if bloon is in right position (negative x)
    List<BloonsCollection> listOfBloons = new ArrayList<>();
    List<List<String>> bloonWaves = getDataFromFile(fileName);
    BloonsCollection currentCollection = new BloonsCollection();
    List<String> specialKeys = getSpecials();
    for (List<String> row : bloonWaves){
      if (row.get(0).equals(BLOON_READER_KEY.getString("NextWave"))){
        listOfBloons.add(currentCollection);
        currentCollection = new BloonsCollection();
      }
      else{
        for (String bloonInfo : row){
          Bloon bloon = null;
          if (bloonInfo.length() > 1) {
            for (String specialKey: specialKeys) {
              if (bloonInfo.contains(BLOON_READER_KEY.getString(specialKey))) {
                bloon = createSpecialBloon(chain, bloonInfo.replaceAll("[^0-9]", ""), layout, specialKey);
              }
            }
          } else {
            bloon = createBloon(chain, bloonInfo, layout);
          }
          currentCollection.add(bloon);
        }
      }
    }
    listOfBloons.add(currentCollection);
    return listOfBloons;
  }

  private List<String> getSpecials() {
    Enumeration<String> keys = BLOON_READER_KEY.getKeys();
    List<String> specialKeys = new ArrayList<>();
    while (keys.hasMoreElements()) {
      String specialKey = keys.nextElement();
      if (!specialKey.equals("NextWave")) {
        specialKeys.add(specialKey);
      }
    }
    return specialKeys;
  }

  private Bloon createBloon(BloonsTypeChain chain, String bloon, Layout layout) {
    int bloonLives = Integer.parseInt(bloon);
    BloonsType bloonType = chain.getBloonsTypeRecord(bloonLives);

    double dx = layout.getStartBlock().getDx() * bloonType.relativeSpeed();
    double dy = layout.getStartBlock().getDx() * bloonType.relativeSpeed();
    return new BasicBloonsFactory().createBloon(bloonType, layout.getStartCoordinates()[1] + 0.5, layout.getStartCoordinates()[0] + 0.5, dx, dy);
  }

  private Bloon createSpecialBloon(BloonsTypeChain chain, String bloon, Layout layout, String special) throws ConfigurationException {
    Bloon specialBloon = createBloon(chain, bloon, layout);

    BloonsFactory specialFactory;
    try {
      Class<?> specialBloonClass = Class.forName(FACTORY_FILE_PATH + special + "BloonsFactory");
      Constructor<?> specialBloonConstructor = specialBloonClass.getConstructor();
      specialFactory = (BloonsFactory) specialBloonConstructor.newInstance();
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException("SpecialNotFound");
    } catch (Exception e) {
      throw new ConfigurationException("OtherSpecialBloonErrors");
    }
    return specialFactory.createBloon(specialBloon);
  }
}
