package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {

  private Controller controller;

  @BeforeEach
  void startController(){
    controller = new Controller();
    controller.start(new Stage());
  }




}