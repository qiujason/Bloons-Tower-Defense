package ooga.visualization;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public interface Window {

  void displayWindow(List<Button> windowChangeButtons);

  void setupWindowButtons();

}
