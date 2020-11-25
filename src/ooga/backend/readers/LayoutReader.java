package ooga.backend.readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.backend.layout.Layout;

/**
 * Reader class to read in a layout data file and initializes a Layout object based on the data
 */

public class LayoutReader extends Reader {

  /**
   * Returns a List of List of Strings that represent the data read in from the given CSV file
   * @param fileName the directory of the CSV file to be read
   * @return a List of List of Strings of the data in the CSV file
   */
  @Override
  public List<List<String>> getDataFromFile(String fileName) {
    List<List<String>> layoutData = new ArrayList<>();
    List<String[]> csvData = readFile(fileName);
    for (String[] row : csvData) {
      layoutData.add(Arrays.asList(row));
    }
    return layoutData;
  }

  /**
   * Generates a layout based on the CSV data given.
   * @param fileName the directory of the CSV file to be read
   * @return a Layout based on the read in information
   */
  public Layout generateLayout(String fileName) {
    List<List<String>> layoutConfig = getDataFromFile(fileName);
    return new Layout(layoutConfig);
  }
}
