package ooga.visualization.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import ooga.backend.towers.TowerType;
import ooga.controller.TowerMenuInterface;

public class WeaponsMenu extends FlowPane {

  private TowerMenuInterface controller;
  private List<Enum> weaponTypeList = Arrays.asList(TowerType.values());
  private List<Button> buttonList;

  public WeaponsMenu(TowerMenuInterface controller){
    this.controller = controller;
  }

  private void makeWeaponButtonList(){
    for(Enum type : weaponTypeList){

    }
  }

  private Button makeWeaponButton(TowerType towerType, EventHandler<ActionEvent> handler){
    return null;
  }

}
