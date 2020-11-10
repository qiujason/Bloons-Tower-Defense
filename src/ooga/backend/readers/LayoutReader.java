package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.backend.layout.Layout;

public class LayoutReader extends Reader{

  @Override
  public List<List<String>> getDataFromFile(String fileName){
    List<String[]> csvData = readFile(fileName);
    List<List<String>> layoutData = new ArrayList<>();
    for(String[] row : csvData){
      layoutData.add(Arrays.asList(row));
    }
    return layoutData;
  }

  public Layout generateLayout(String fileName){
    List<List<String>> layoutConfig = getDataFromFile(fileName);
    return new Layout(layoutConfig);
  }





}
