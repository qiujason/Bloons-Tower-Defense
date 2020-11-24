package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.types.BloonsType;
import ooga.backend.bloons.types.Specials;

public class BloonNode extends GamePieceNode {

  private BloonsType bloonType;
  public static String BLOON_IMAGES_PATH = "bloon_resources/BloonImages";

  private final ResourceBundle myBloonImages = ResourceBundle
      .getBundle(BLOON_IMAGES_PATH);

  public BloonNode(BloonsType bloonType, double xPosition, double yPosition, double radius) {
    super(xPosition, yPosition, radius);
    this.bloonType = bloonType;
    this.setFill(findImage());
  }

  @Override
  public ImagePattern findImage() {
    Image towerImage = null;
    try {
      StringBuilder imageName = new StringBuilder(bloonType.name());
      if (bloonType.specials() != Specials.None) {
        imageName.append("_").append(bloonType.specials().name().toUpperCase());
      }
      towerImage = new Image(String.valueOf(getClass().getResource(myBloonImages.getString(
          imageName.toString())).toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler("Image Not Found", bloonType.name() + " image not found.");
    }
    assert towerImage != null;
    return new ImagePattern(towerImage);
  }

  public void updateBloonType(BloonsType bloonsType) {
    if (bloonsType != bloonType) {
      bloonType = bloonsType;
      if (!bloonType.name().equals("DEAD")) {
        setImage();
      }
    }
  }

  private void setImage() {
    this.setFill(findImage());
  }

}
