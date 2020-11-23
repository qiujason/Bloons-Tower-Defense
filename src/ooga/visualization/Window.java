package ooga.visualization;

import java.util.List;
import javafx.scene.control.Button;

/**
 * This interface is used to define a general "window" or "splashscreen" in the game.  Because
 * windows have certain elements in common, such as using GUI buttons to make changes on screen or
 * navigate to different windows, the Window interface can be implemented to specify how the window
 * should be displayed and what buttons it should include.
 */
public interface Window {

  /**
   * This method displays the Window by setting up its various JavaFX objects, such as Groups,
   * BorderPanes, etc. An application can call this method to display a certain Window without
   * needed to create new JavaFX objects itself. Because a Window on its own won't necessarily know
   * which Window to change to, the calling class will pass in a list of Buttons that can be used to
   * change windows.  This way, the calling class (typically an application) can inform this Window
   * what type of Window to open or change to on this action.
   *
   * @param windowChangeButtons a list of Buttons used to change Windows.
   */
  void displayWindow(List<Button> windowChangeButtons);

  /**
   * This method is used to set up Window-specific buttons, since Buttons have functions that are a
   * bit more complicated than simply displaying a Window.  This method will typically be called
   * from inside displayWindow(), but it can also be called by the calling class separately
   * depending on the implementation.
   */
  void setupWindowButtons();

}
