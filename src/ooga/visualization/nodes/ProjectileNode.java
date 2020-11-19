package ooga.visualization.nodes;

import java.net.URISyntaxException;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import ooga.AlertHandler;
import ooga.backend.projectile.ProjectileType;

public class ProjectileNode extends GamePieceNode {

  private ProjectileType projectileType;
  public static final String PROJECTILE_IMAGES_PATH = "projectile_resources/ProjectileImages";

  private final ResourceBundle myProjectileImages = ResourceBundle
      .getBundle(PROJECTILE_IMAGES_PATH);


  public ProjectileNode(ProjectileType projectileType, double xPosition, double yPosition, double radius){
    super(xPosition, yPosition, radius);
    this.projectileType = projectileType;
    this.setFill(findImage());
  }

  @Override
  public ImagePattern findImage() {
    Image projectileImage = null;
    try {
      projectileImage = new Image(String.valueOf(getClass().getResource(myProjectileImages.getString(
          projectileType.name())).toURI()));
    } catch (
        URISyntaxException e) {
      new AlertHandler("Image Not Found", projectileType.name() + " image not found.");
    }
    assert projectileImage != null;
    return new ImagePattern(projectileImage);  }
}
