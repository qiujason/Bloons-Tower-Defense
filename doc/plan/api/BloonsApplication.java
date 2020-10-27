/**
 * The class used for running a game of Bloons Tower Defense.  Includes methods to initialize a display,
 * update the state of the layout, and animate towers/bloons.
 */
public interface BloonsApplication extends Application {

  Layout myLayout;

  /**
   * Starts the application
   *
   * @param stage to display
   */
  @Override
  public void start(Stage stage);

  /**
   * Sets up the stage with a certain scene (and appropriate buttons)
   *
   * @return the new scene to be displayed by the stage
   */
  public Scene initializeStage();

  /**
   * Used to load in the layout for a new level and display the changes in the application
   *
   * @return a new layout representing the current state of the map/towers
   */
  public Layout loadLevel();

  /**
   * Updates the display when a tower is placed/upgraded
   */
  public void updateDisplay();

  /**
   * Handles the animations for the bloons and towers
   */
  public void animate();

}