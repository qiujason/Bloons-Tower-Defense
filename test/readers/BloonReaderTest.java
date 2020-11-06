package readers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ooga.backend.readers.BloonReader;
import ooga.backend.readers.Reader;
import org.junit.jupiter.api.Test;

public class BloonReaderTest {

  @Test
  void getFromFileTest(){
    Reader reader = new BloonReader();
    List<List<String>> layout = reader.getDataFromFile("bloon_waves/level1.csv");
    String[][] expectedLayout =
        {{"1","1","1","1","1","1","1","1","1","1","1","1"},
            {"0","0","0","0","0","0","0","0","0","0","0","0"},
            {"2","2","2","2","2","2","2","2","2","2","2","2"}};
    List<List<String>> expectedLayoutList = Arrays.stream(expectedLayout)
        .map(Arrays::asList)
        .collect(Collectors.toList());
    assertEquals(layout, expectedLayoutList);
  }

  void generateBloonsCollectionMapTest(){
    Reader reader = new BloonReader();

  }
}