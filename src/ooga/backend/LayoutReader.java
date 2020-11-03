package ooga.backend;

import java.util.List;

public class LayoutReader extends Reader{

  @Override
  public String[][] getStatesFromFile(String fileName){
    List<String[]> gridData = readFile(fileName);
    String[] dimensions = gridData.remove(0);
    String[][] dataArray = new String[gridData.size()][];
    return gridData.toArray(dataArray);
  }


}
