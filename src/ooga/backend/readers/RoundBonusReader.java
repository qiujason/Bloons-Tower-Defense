package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoundBonusReader extends Reader{
  @Override
  public List<List<String>> getDataFromFile(String fileName){
    List<String[]> csvData = readFile(fileName);
    List<List<String>> roundBonusReader = new ArrayList<>();
    for(String[] row : csvData){
      roundBonusReader.add(Arrays.asList(row));
    }
    return roundBonusReader;
  }
}
