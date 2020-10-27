/**
 * Use case demonstrating a game over
 */
public class GameOver {

  public void endOfGame(){
    if (myHealth <= 0){
      Scene gameOverDisplay = initializeScene();
      myStage.setScene(gameOverDisplay);
      myState.show();
    }
  }

}