package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.factory.BasicBloonsFactory;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.layout.Layout;
import ooga.visualization.AnimationHandler;

public class BloonReader extends Reader{

  @Override
  public List<List<String>> getDataFromFile(String fileName) {
    List<String[]> csvData = readFile(fileName);
    List<List<String>> bloonWave = new ArrayList<>();
    for(String[] row : csvData){
      bloonWave.add(Arrays.asList(row));
    }
    return bloonWave;
  }

  public List<BloonsCollection> generateBloonsCollectionMap(BloonsTypeChain chain, String fileName, Layout layout){ //TODO: create test for seeing if bloon is in right position (negative x)
    List<BloonsCollection> listOfBloons = new ArrayList<>();
    List<List<String>> bloonWaves = getDataFromFile(fileName);
    BloonsCollection currentCollection = new BloonsCollection();
    for (List<String> row : bloonWaves){
      if (row.get(0).equals("=")){
        listOfBloons.add(currentCollection);
        currentCollection = new BloonsCollection();
        continue;
      }
      else{
        for (String bloonInfo : row){
          Bloon bloon = createBloon(chain, bloonInfo, layout);
          currentCollection.add(bloon);
        }
      }
    }
    listOfBloons.add(currentCollection);
    return listOfBloons;
  }

  private Bloon createBloon(BloonsTypeChain chain, String bloon, Layout layout) {
    int bloonLives = Integer.parseInt(bloon);
    BloonsType bloonType = chain.getBloonsTypeRecord(bloonLives);

    double dx = layout.getStartBlock().getDx() * bloonType.relativeSpeed();
    double dy = layout.getStartBlock().getDx() * bloonType.relativeSpeed();
    return new Bloon(bloonType, layout.getStartCoordinates()[1] + 0.5, layout.getStartCoordinates()[0] + 0.5, dx, dy);
  }

}
