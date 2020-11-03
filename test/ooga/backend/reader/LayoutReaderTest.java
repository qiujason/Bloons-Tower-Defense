package ooga.backend.reader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ooga.backend.LayoutReader;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LayoutReaderTest {

  @Test
  void getLayoutFromFileTest(){
    LayoutReader reader = new LayoutReader();
    List<List<String>> layout = reader.getLayoutFromFile("layouts/level1.csv");
    String[][] expectedLayout =
        {{"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {">",">",">",">",">",">",">",">",">",">",">",">",">",">"},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {"0","0","0","0","0","0","0","0","0","0","0","0","0","0",}};
    List<List<String>> expectedLayoutList = Arrays.stream(expectedLayout)
        .map(Arrays::asList)
        .collect(Collectors.toList());
    assertEquals(layout, expectedLayoutList);
  }

}
