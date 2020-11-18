package ooga.visualization.menu;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import ooga.visualization.AnimationHandler;

public class ImageChooser extends HBox {

  public ImageChooser(AnimationHandler animationHandler){
    this.getChildren().add(imageChooserButton("Bloon Image Chooser", event -> {FileChooser fileChooser = new FileChooser();

      //Set extension filter
      FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
      FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
      fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

      //Show open file dialog
      File file = fileChooser.showOpenDialog(null);

      if (file != null){
        animationHandler.changeBloonImage(file);
      }

    }));




  }

  private Button imageChooserButton(String name, EventHandler<ActionEvent> handler){
      Button button = new Button();
      button.setText(name);
      button.setOnAction(handler);
      button.setId(name);
      return button;
  }


}
