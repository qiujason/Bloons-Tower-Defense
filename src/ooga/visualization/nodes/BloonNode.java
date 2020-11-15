package ooga.visualization.nodes;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.towers.TowerType;

public class BloonNode extends GamePieceNode{

  private BloonsType bloonType;
  public static final String BLOON_IMAGES_PATH = "bloon_resources/BloonImages";

  private final ResourceBundle myBloonImages = ResourceBundle
      .getBundle(BLOON_IMAGES_PATH);



  public BloonNode(BloonsType bloonType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.bloonType = bloonType;
    this.setFill(findImage());
  }

  @Override
  public ImagePattern findImage() {
    Image towerImage = null;
    System.out.println(bloonType.name());
    try {
      towerImage = new Image(String.valueOf(getClass().getResource(myBloonImages.getString(
          bloonType.name())).toURI()));
    } catch (
        URISyntaxException e) {
      e.printStackTrace();
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

}
