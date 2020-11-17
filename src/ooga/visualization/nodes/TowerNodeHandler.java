package ooga.visualization.nodes;

public class TowerNodeHandler {

  //  private void makeTower(TowerType towerType) {
//    WeaponNodeFactory nodeFactory = new TowerNodeFactory();
//    TowerNode towerInGame = nodeFactory.createTowerNode(towerType, gameWidth/2,
//        gameHeight/2, blockSize/2);
//    towerInGame.makeTowerMenu(this);
//    WeaponRange towerRange = towerInGame.getRangeDisplay();
//    layoutRoot.getChildren().add(towerRange);
//    layoutRoot.getChildren().add(towerInGame);
//    placeTower(towerType, towerInGame, towerRange);
//  }
//
//  private void placeTower(TowerType type, TowerNode towerNode, WeaponRange range){
//    layoutRoot.setOnMouseMoved(e -> {
//      if(e.getX() >= 0 && e.getX() <= gameWidth){
//        if(e.getY() >= 0 && e.getY() <= gameHeight){
//          towerNode.setXPosition(e.getX());
//          towerNode.setYPosition(e.getY());
//          range.setCenterX(e.getX());
//          range.setCenterY(e.getY());
//        }
//      }
//    });
//    TowerFactory towerFactory = new SingleTowerFactory();
//    towerNode.setOnMouseClicked(e -> {
//      layoutRoot.setOnMouseMoved(null);
//      range.makeInvisible();
//      animationHandler.addTower(towerFactory
//          .createTower(type, layout.getHeight() * (towerNode.getCenterX() / gameWidth),
//              layout.getWidth() * (towerNode.getCenterY() / gameHeight)), towerNode);
//      towerNode.setOnMouseClicked(null);
//      towerNode.setOnMouseClicked(h -> selectTower(towerNode));
//    });
//  }
//
//  private void openMenu(TowerNode towerNode){
//    WeaponMenu towerMenu = towerNode.getTowerMenu();
//    if(!menuPane.getChildren().contains(towerMenu)){
//      menuPane.getChildren().add(towerMenu);
//    }
//  }

}
