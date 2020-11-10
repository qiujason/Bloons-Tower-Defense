package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.collection.BloonsCollection;
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

  public List<BloonsCollection> generateBloonsCollectionMap(BloonsTypeChain chain, String fileName, Layout layout){
    List<BloonsCollection> listOfBloons = new ArrayList<>();
    List<List<String>> bloonWaves = getDataFromFile(fileName);
    BloonsCollection currentCollection = new BloonsCollection();
    for (List<String> row : bloonWaves){
      if (row.get(0).equals("=")){
        listOfBloons.add(currentCollection);
        currentCollection = new BloonsCollection();
      }
      for (String bloon : row){
        Bloon bloons = createBloon(chain, bloon, layout);
        currentCollection.add(bloons);
      }
    }
    return listOfBloons;
  }

  private Bloon createBloon(BloonsTypeChain chain, String bloon, Layout layout) {
    int bloonLives = Integer.parseInt(bloon);
    BloonsType bloonType = chain.getBloonsTypeRecord(bloonLives);
    double startRow = layout.getStartBlockCoordinates()[0];
    double startCol = layout.getStartBlockCoordinates()[1];
    double dx = layout.getBlock((int)startRow, (int)startCol).getDx();
    double dy = layout.getBlock((int)startRow, (int)startCol).getDy();
    return new Bloon(bloonType, startRow, startCol, dx, dy);
  }

}
