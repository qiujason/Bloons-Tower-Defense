package ooga.visualization.nodes;

import java.net.URISyntaxException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.towers.TowerType;

public class BloonNode extends GamePieceNode{

  private BloonsType bloonType;

  private static final String TOWER_IMAGE = "/gamePhotos/dartmonkey.png";



  public BloonNode(BloonsType bloonType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.bloonType = bloonType;
    this.setFill(findTowerImage());
  }



  @Override
  public ImagePattern findTowerImage() {
    Image towerImage = null;
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(TOWER_IMAGE).toURI()));
    } catch (
        URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }
}
