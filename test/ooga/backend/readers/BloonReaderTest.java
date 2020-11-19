package ooga.backend.readers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ooga.backend.ConfigurationException;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.special.RegenBloon;
import ooga.backend.bloons.types.BloonsTypeChain;
import ooga.backend.bloons.types.Specials;
import ooga.backend.collections.GamePieceIterator;
import ooga.backend.layout.Layout;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BloonReaderTest {

  @Test
  void getFromFileTest() throws ConfigurationException {
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
  void generateBloonsCollectionMapTest() throws ConfigurationException {
    LayoutReader layoutReader = new LayoutReader();
    BloonReader reader = new BloonReader();
    Layout layout = layoutReader.generateLayout("layouts/level1.csv");
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    List<BloonsCollection> list = reader.generateBloonsCollectionMap(chain, "tests/test_bloon_waves/level1_test.csv", layout);
    String[][] expectedWaves =
        {{"RED","RED","RED","RED","RED","RED","RED","RED","RED","RED","RED","RED"},
            {"DEAD","DEAD","DEAD","DEAD","DEAD","DEAD","DEAD","DEAD","DEAD","DEAD","DEAD","DEAD"},
            {"BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE"}};
    assertTrue(generateBloonsCollectionMapTestHelper(expectedWaves,list));
  }

  private boolean generateBloonsCollectionMapTestHelper(String[][] expectedWaves, List<BloonsCollection> list){
    for (int i = 0; i < expectedWaves.length; i++){
      GamePieceIterator<Bloon> iterate = list.get(i).createIterator();
      for (int j = 0; j < expectedWaves[i].length; j++){
        Bloon bloon = iterate.next();
        if (!expectedWaves[i][j].equals(bloon.toString())){
          return false;
        }
      }
    }
    return true;
  }

  @Test
  void generateBloonsCollectionMapWithSpecialsTest() throws ConfigurationException {
    LayoutReader layoutReader = new LayoutReader();
    BloonReader reader = new BloonReader();
    Layout layout = layoutReader.generateLayout("layouts/level1.csv");
    BloonsTypeChain chain = new BloonsTypeChain("tests/test_bloonstype_reader/ValidBloons");
    List<BloonsCollection> list = reader.generateBloonsCollectionMap(chain, "tests/test_bloon_waves/specials.csv", layout);
    String[][] expectedWaves =
        {{"RED","RED","RED","RED","RED","RED","RED","RED","RED","RED","RED","RED"},
            {"BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE","BLUE"}};
    assertTrue(generateBloonsCollectionMapTestHelper(expectedWaves,list));
    GamePieceIterator<Bloon> iterate = list.get(0).createIterator();
    for (int i = 0; i < expectedWaves.length; i++) {
      Bloon bloon = iterate.next();
      assertTrue(bloon.getBloonsType().specials().contains(Specials.Camo));
    }
    iterate = list.get(1).createIterator();
    for (int i = 0; i < expectedWaves.length; i++) {
      Bloon bloon = iterate.next();
      assertTrue(bloon instanceof RegenBloon);
      assertTrue(bloon.getBloonsType().specials().contains(Specials.Regen));
    }
  }



}