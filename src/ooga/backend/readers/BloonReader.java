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
    int offset = 0;
    for (List<String> row : bloonWaves){
      if (row.get(0).equals("=")){
        listOfBloons.add(currentCollection);
        currentCollection = new BloonsCollection();
        continue;
      }
      else{
        for (String bloonInfo : row){
          Bloon bloon = createBloon(chain, bloonInfo, layout, offset);
          offset++;
          currentCollection.add(bloon);
        }
      }
    }
    listOfBloons.add(currentCollection);
    return listOfBloons;
  }

  private Bloon createBloon(BloonsTypeChain chain, String bloon, Layout layout, int offset) {
    int bloonLives = Integer.parseInt(bloon);
    BloonsType bloonType = chain.getBloonsTypeRecord(bloonLives);
    double startRow = layout.getStartBlockCoordinates()[0];
    double startCol = layout.getStartBlockCoordinates()[1];
    double dx = layout.getBlock((int)startRow, (int)startCol).getDx() * bloonType.relativeSpeed()/1000;
    double dy = layout.getBlock((int)startRow, (int)startCol).getDy() * bloonType.relativeSpeed()/1000;
    startRow = startRow - offset;
    return new BasicBloonsFactory().createBloon(chain, bloonType, startRow, startCol, dx, dy);
  }

}
