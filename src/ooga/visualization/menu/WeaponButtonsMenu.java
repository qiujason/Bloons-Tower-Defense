package ooga.visualization.menu;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.TowerType;
import ooga.controller.TowerMenuInterface;

public class WeaponButtonsMenu extends FlowPane {

  private TowerMenuInterface controller;
  private List<TowerType> weaponTypeList = Arrays.asList(TowerType.values());

  //later make this read in what package from a overall game properties file
  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";
  private static final String BUTTON_TAG = "Button";

  private static final Double BUTTON_WIDTH = 150.0;

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  public WeaponButtonsMenu(TowerMenuInterface controller){
    this.controller = controller;
    makeAllWeaponButtons();
    this.setOrientation(Orientation.VERTICAL);
  }

  private void makeAllWeaponButtons(){
    for(TowerType type : weaponTypeList){
      this.getChildren().add(makeWeaponButton(typeToName.getString(type.name()),
          event -> controller.buyTower(type)));
    }
  }

  private Button makeWeaponButton(String towerName, EventHandler<ActionEvent> handler){
    String imageDirectory = nameToPicture.getString(towerName + BUTTON_TAG);
    Image towerImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(towerImage);
    imageView.setFitHeight(40); //magic
    imageView.setFitWidth(40); //magic
    Button button = new Button(towerName,imageView);
    button.setOnAction(handler);
    button.setId(towerName);
    button.setMinWidth(BUTTON_WIDTH);
    button.setMaxHeight(50);
    return button;
  }

  private Image makeImage(String directory){
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(directory).toURI()));
    } catch (URISyntaxException e) {
      e.printStackTrace();  //duvall no like
    }
    assert towerImage != null;
    return towerImage;
  }

}
