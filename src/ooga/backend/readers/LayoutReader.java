package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LayoutReader extends Reader{

  @Override
  public List<List<String>>getDataFromFile(String fileName){
    List<String[]> csvData = readFile(fileName);
    List<List<String>> layoutData = new ArrayList<>();
    for(String[] row : csvData){
      layoutData.add(Arrays.asList(row));
    }
    return layoutData;
  }



}
