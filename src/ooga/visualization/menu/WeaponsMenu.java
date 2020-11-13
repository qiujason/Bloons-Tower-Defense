package ooga.visualization.menu;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.TowerType;
import ooga.controller.TowerMenuInterface;

public class WeaponsMenu extends FlowPane {

  private TowerMenuInterface controller;
  private List<Enum> weaponTypeList = Arrays.asList(TowerType.values());

  //later make this read in what package from a overall game properties file
  private static final String PACKAGE = "btd_towers/";
  private static final String NAMES = "TowerMonkey";
  private static final String PICTURES = "MonkeyPics";

  private ResourceBundle typeToName = ResourceBundle.getBundle(PACKAGE + NAMES);
  private ResourceBundle nameToPicture = ResourceBundle.getBundle(PACKAGE + PICTURES);

  public WeaponsMenu(TowerMenuInterface controller){
    this.controller = controller;
    makeAllWeaponButtons();
  }

  private void makeAllWeaponButtons(){
    for(Enum type : weaponTypeList){
      this.getChildren().add(makeWeaponButton(typeToName.getString(type.name()),
          event -> controller.buyTower()));
    }
  }

  private Button makeWeaponButton(String towerName, EventHandler<ActionEvent> handler){
    String imageDirectory = nameToPicture.getString(towerName);
    Image towerImage = makeImage(imageDirectory);
    ImageView imageView = new ImageView(towerImage);
    imageView.setFitHeight(60); //magic
    imageView.setFitWidth(60); //magic
    Button button = new Button(towerName,imageView);
    button.setOnAction(handler);
    button.setId(towerName);
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
