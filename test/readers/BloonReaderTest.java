package readers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ooga.backend.bloons.collection.BloonsCollection;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.collections.Iterator;
import ooga.backend.layout.Layout;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.LayoutReader;
import ooga.backend.readers.Reader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BloonReaderTest {

  @Test
  void getFromFileTest(){
    Reader reader = new BloonReader();
    List<List<String>> layout = reader.getDataFromFile("tests/test_bloon_waves/level1_test.csv");
    String[][] expectedLayout =
        {{"1","1","1","1","1","1","1","1","1","1","1","1"},
            {"=","=","=","=","=","=","=","=","=","=","=","="},
            {"0","0","0","0","0","0","0","0","0","0","0","0"},
            {"=","=","=","=","=","=","=","=","=","=","=","="},
            {"2","2","2","2","2","2","2","2","2","2","2","2"}};
    List<List<String>> expectedLayoutList = Arrays.stream(expectedLayout)
        .map(Arrays::asList)
        .collect(Collectors.toList());
    assertEquals(layout, expectedLayoutList);
  }

  @Test
  void generateBloonsCollectionMapTest(){
    LayoutReader layoutReader = new LayoutReader();
    BloonReader reader = new BloonReader();
    Layout layout = layoutReader.generateLayout("layouts/level1.csv");
    BloonsTypeChain chain = new BloonsTypeChain("tests.test_bloonstype_reader.ValidBloons");
    List<BloonsCollection> list = reader.generateBloonsCollectionMap(chain, "tests/test_bloon_waves/level1_test.csv", layout);
    String[][] expectedWaves =
        {{"1","1","1","1","1","1","1","1","1","1","1","1"},
            {"0","0","0","0","0","0","0","0","0","0","0","0"},
            {"2","2","2","2","2","2","2","2","2","2","2","2"}};
    assertTrue(generateBloonsCollectionMapTestHelper(expectedWaves,list));
  }

  private boolean generateBloonsCollectionMapTestHelper(String[][] expectedWaves, List<BloonsCollection> list){
    for (int i = 0; i < expectedWaves.length; i++){
      Iterator iterate = list.get(i).createIterator();
      for (int j = 0; j < expectedWaves[0].length; j++){
        if (!expectedWaves[i][j].equals(iterate.getNext().toString())){
          return false;
        }
      }
    }
    return true;
  }
}