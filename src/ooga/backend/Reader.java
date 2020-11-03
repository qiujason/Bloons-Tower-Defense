package ooga.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public abstract class Reader {

  protected static InputStream getFileInputStream(String dataSource) {
    InputStream textFile = null;
    try {
      textFile = Objects
          .requireNonNull(LayoutReader.class.getClassLoader().getResource(dataSource))
          .openStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return textFile;
  }

  protected List<String[]> readFile(String fileName) {
    List<String[]> fileData = null;
    InputStream dataStream = getFileInputStream(fileName);
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataStream))) {
      fileData = csvReader.readAll();
    } catch (CsvException | IOException e) {
      e.printStackTrace();
      return fileData;
    }
    return fileData;
  }

  public abstract List<List<String>> getLayoutFromFile(String fileName);


}
