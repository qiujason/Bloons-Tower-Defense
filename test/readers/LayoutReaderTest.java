package readers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ooga.backend.layout.Layout;
import ooga.backend.readers.LayoutReader;
import static org.junit.jupiter.api.Assertions.*;

import ooga.backend.readers.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LayoutReaderTest {

  private LayoutReader layoutReader;

  @BeforeEach
  void createLayoutReader(){
    layoutReader = new LayoutReader();
  }

  @Test
  void getLayoutFromFileTest(){
    List<List<String>> layout = layoutReader.getDataFromFile("tests/test_layouts/straightRight.csv");
    String[][] expectedLayout =
        {{"0","0","0","0","0","0","0","0","0","0","0","0","0","0",},
            {"*",">",">",">",">",">",">",">",">",">",">",">",">",">"},
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

  @Test
  void generateLayoutTest(){
    Layout layout = layoutReader.generateLayout("tests/test_layouts/straightRight.csv");
    assertEquals(Arrays.toString(layout.getStartBlockCoordinates()), Arrays.toString(new int[]{1,0}));
  }
}
