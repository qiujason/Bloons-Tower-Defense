package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsType;
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

  public List<BloonsCollection> generateBloonsCollectionMap(String fileName, Layout layout){
    List<BloonsCollection> listOfBloons = new ArrayList<>();
    List<List<String>> bloonWaves = getDataFromFile(fileName);
    BloonsCollection currentCollection = new BloonsCollection();
    for (List<String> row : bloonWaves){
      if (row.get(0).equals("=")){
        listOfBloons.add(currentCollection);
        currentCollection = new BloonsCollection();
      }
      for (String bloon : row){
        Bloon bloons = createBloon(bloon, layout);
        currentCollection.add(bloons);
      }
    }
    return listOfBloons;
  }

  private Bloon createBloon(String bloon, Layout layout) {
    int bloonLives = Integer.parseInt(bloon);
    BloonsType bloonType = BloonsType.values()[bloonLives];
    double startRow = layout.getStartBlockCoordinates()[0];
    double startCol = layout.getStartBlockCoordinates()[1];
    double dx = layout.getBlock((int)startRow, (int)startCol).getDx();
    double dy = layout.getBlock((int)startRow, (int)startCol).getDy();
    return new Bloon(bloonType, startRow, startCol, dx, dy);
  }

}
