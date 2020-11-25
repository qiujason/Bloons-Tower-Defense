/**
 * RoundBonusReader should be able to read in round bonuses for the game and returns a list
 * @Author annshine
 */
package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.backend.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

public class RoundBonusReader extends Reader {

  @Override
  public List<List<String>> getDataFromFile(String fileName) throws ConfigurationException {
    List<String[]> csvData = readFile(fileName);
    List<List<String>> roundBonusReader = new ArrayList<>();
    for (String[] row : csvData) {
      for (String item : row) {
        if (!StringUtils.isNumeric(item)) {
          throw new ConfigurationException("RoundBonusNonIntegers");
        }
      }
      roundBonusReader.add(Arrays.asList(row));
    }
    if (roundBonusReader.size() == 0) {
      throw new ConfigurationException("RoundBonusCSVEmpty");
    }
    return roundBonusReader;
  }
}
