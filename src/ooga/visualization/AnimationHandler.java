package ooga.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import ooga.backend.API.GamePiece;
import ooga.backend.bloons.Bloon;
import ooga.backend.bloons.BloonsCollection;
import ooga.backend.bloons.BloonsIterator;
import ooga.backend.bloons.BloonsType;
import ooga.backend.towers.Tower;
import ooga.backend.towers.TowersCollection;
import ooga.backend.towers.TowersIterator;

public class AnimationHandler {

  public static final double FRAMES_PER_SECOND = 60;
  public static final double ANIMATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double SPEED = 3;

  private Timeline myAnimation;
  private List<List<String>> myLayout;
  private Group myLevelLayout;
  private double myStartingX;
  private double myStartingY;
  private double myBlockSize;
  private Circle myTestCircle;

  private TowersCollection myTowers;
  private Map<Tower, Node> myTowersInGame;
  private BloonsCollection myBloons;
  private Map<Bloon, Node> myBloonsInGame;

  private double myCircleSideX;
  private double myCircleSideY;

  public AnimationHandler(List<List<String>> layout, Group levelLayout, double startingX,
      double startingY, double blockSize) {
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame movement = new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> animate());
    myAnimation.getKeyFrames().add(movement);

    myLayout = layout;
    myLevelLayout = levelLayout;
    myStartingX = startingX;
    myStartingY = startingY;
    myBlockSize = blockSize;

    myTestCircle = new Circle(myStartingX, myStartingY, myBlockSize / 2.5, Color.RED);
    myTestCircle.setId("TestCircle");
    myLevelLayout.getChildren().add(myTestCircle);

    myTowers = new TowersCollection();
    myTowersInGame = new HashMap<>();
    myBloons = new BloonsCollection();
    myBloonsInGame = new HashMap<>();
    Bloon testBloon = new Bloon(BloonsType.RED, myTestCircle.getCenterX(), myTestCircle.getCenterY(), 0, 0);
    myBloons.add(testBloon);
    myBloonsInGame.put(testBloon, myTestCircle);
  }

  private void animate() {
    animateBloons();
    animateTowers();
  }

  // TODO: Refactor
  private void animateBloons() {
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    String currentBlockString = myLayout
        .get((int) ((myTestCircle.getCenterY() + myCircleSideY) / myBlockSize))
        .get((int) ((myTestCircle.getCenterX() + myCircleSideX) / myBlockSize));

    while(bloonsIterator.hasMore()) {
      Bloon currentBloon = (Bloon) bloonsIterator.getNext();

      switch(currentBlockString) {
        case "*", ">" -> {
          currentBloon.setXVelocity(SPEED);
          currentBloon.setYVelocity(0);
          myTestCircle.setCenterX(myTestCircle.getCenterX() + SPEED);
          myCircleSideX = -myBlockSize / 2;
          myCircleSideY = 0;
        }
        case "<" -> {
          currentBloon.setXVelocity(-SPEED);
          currentBloon.setYVelocity(0);
          myTestCircle.setCenterX(myTestCircle.getCenterX() - SPEED);
          myCircleSideX = myBlockSize / 2;
          myCircleSideY = 0;
        }
        case "v" -> {
          currentBloon.setXVelocity(0);
          currentBloon.setYVelocity(SPEED);
          myTestCircle.setCenterY(myTestCircle.getCenterY() + SPEED);
          myCircleSideX = 0;
          myCircleSideY = -myBlockSize / 2;
        }
        case "^" -> {
          currentBloon.setXVelocity(0);
          currentBloon.setYVelocity(-SPEED);
          myTestCircle.setCenterY(myTestCircle.getCenterY() - SPEED);
          myCircleSideX = 0;
          myCircleSideY = myBlockSize / 2;
        }
        case "@" -> myLevelLayout.getChildren().remove(myTestCircle);
      }
    }
    myBloons.updateAll();
  }

  private void animateTowers() {
    TowersIterator towersIterator = (TowersIterator) myTowers.createIterator();
    BloonsIterator bloonsIterator = (BloonsIterator) myBloons.createIterator();
    while(towersIterator.hasMore()){
      Tower currentTower = (Tower) towersIterator.getNext();
      while(bloonsIterator.hasMore()) {
        Bloon currentBloon = (Bloon) bloonsIterator.getNext();
        if (currentTower.getDistance(currentBloon) <= currentTower.getRadius()){
          rotateBloon(currentBloon, currentTower);
        }
      }
      bloonsIterator.reset();
    }
  }

  private void rotateBloon(Bloon bloon, Tower tower){
    Node towerInGame = myTowersInGame.get(tower);
    double angle = Math.toDegrees(Math.asin((bloon.getXPosition() - tower.getXPosition()) / tower.getDistance(bloon)));
    if(bloon.getYPosition() < tower.getYPosition()){
      towerInGame.setRotate(angle);
    }
    else{
      towerInGame.setRotate(180 - angle);
    }
  }

  public void addTower(GamePiece tower, Node towerInGame){
    myTowers.add(tower);
    myTowersInGame.put((Tower) tower, towerInGame);
    myLevelLayout.getChildren().add(towerInGame);
  }

  public void removeTower(Node towerInGame){
    myLevelLayout.getChildren().remove(towerInGame);
  }

  public Timeline getAnimation() {
    return myAnimation;
  }

}
