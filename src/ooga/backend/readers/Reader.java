package ooga.backend.readers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import ooga.backend.ConfigurationException;

/**
 * Abstract class that reads in data from a CSV file. Parent class for all Reader subclasses.
 */
public abstract class Reader {

  protected static InputStream getFileInputStream(String dataSource) {
    InputStream textFile = null;
    try {
      textFile = Objects
          .requireNonNull(LayoutReader.class.getClassLoader().getResource(dataSource))
          .openStream();
    } catch (IOException e) {
    }
    return textFile;
  }

  protected List<String[]> readFile(String fileName) {
    List<String[]> fileData = null;
    InputStream dataStream = getFileInputStream(fileName);
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataStream))) {
      fileData = csvReader.readAll();
    } catch (CsvException | IOException e) {
      return fileData;
    }
    return fileData;
  }

  /**
   * Returns the data from the file in the desired format
   * @param fileName the directory of the file to be read in
   * @return a List of List of Strings representing the data that is read in
   * @throws ConfigurationException if the data read in is unable to be translated and initialized into
   * the appropriate backend object.
   */
  public abstract List<List<String>> getDataFromFile(String fileName) throws ConfigurationException;


}
