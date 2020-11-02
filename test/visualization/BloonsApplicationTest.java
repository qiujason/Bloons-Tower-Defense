package visualization;

import javafx.stage.Stage;
import ooga.visualization.BloonsApplication;
import util.DukeApplicationTest;

public class BloonsApplicationTest extends DukeApplicationTest {

  @Override
  public void start(Stage testStage){
    BloonsApplication myBloonsApplication = new BloonsApplication();
    myBloonsApplication.start(testStage);
  }

}
