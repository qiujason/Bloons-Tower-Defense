# OOGA Lab Discussion
## Names and NetIDs
Ann-Shine Wu (aw378), Franklin Wu (fyw4), Eddie Kong(dk255), Jason Qiu (jq39), Jack Ellwood(jce22)


## Fluxx

### High Level Design Ideas

Rules abstraction
 - make it easy to implement a new rule

Card abstract class 
 - since there are different types of cards, this can act as a superclass
 - 4 types of cards: Keepers, Goals, Actions, and New Rules

Player class
 - CPU player (or actual player)

Game class
 - implements basic rules of the game (i.e draw one card, play one card)

### CRC Card Classes

This class's purpose or value is to manage something:
```java
 public abstract class Rule {
 }
```

```java
 public class BaseRule extends Rule {
     public 
 }
```

This class's purpose or value is to be useful:
```java
 public abstract class Card {
     public void read()
     public void perform()
 }
```

```java
public class GoalCard extends Card {
     public void read()
     public void perform()
 }
```

```java
public class ActionCard extends Card {
     public void read()
     public void perform()
 }
```


```java
public abstract class Player{
     public Card drawCard()
     public Card playCard()  
     public void updateScore()
 }
```

### Use Cases

- A player plays a Goal card, changing the current goal, and wins the game.

When a player plays a Goal card, the game class would update the current Goal card that is being used as the current goal. The Game class would contain instances of a player as well as the current card that is played. To win the game, the game class would check for whether the goal is completed, and if so, the game should set a boolean variable to true to indicate that the game has been won by a specific player. 

- A player plays an Action card, allowing him to choose cards from another player's hand and play them.

When a player plays an Action card, the game will update the game allowing the player to choose cards from another player's hand. Each player should have a method that can reveal their current hand. This will be revealed to the other player by calling that method and passing it into the first player's method to read the 2nd player's cards. Once it is passed into that method, the player can choose a card and play it in the game. This, in turn, will again update the game class based on whatever card is played.

- A player plays a Rule card, adding to the current rules to set a hand-size limit, requiring all players to immediately drop cards from their hands if necessary.

When a player draws a card that reduces the hand-size limit, the rule will change to the limit hand size rule. Then,
it goes through each players hands and eliminates cards as needed.

- A player plays a Rule card, changing the current rule from Play 1 to Play All, requiring the player to play more cards this turn.

When the player plays this card, the current rule changes to the rule on the card. Afterwards, this new rule
is called every time a player's turn comes up.

- A player plays a card, fulfilling the current Ungoal, and everyone loses the game.

When the card is played, the ungoal on the card is checked against the current ungoal in the game.
If they match, then everyone loses. Otherwise, the game goes on.



### Use Cases

* A player plays a Goal card, changing the current goal, and wins the game.
 ```java
 Goal goal = GoalCard.getGoal();
 player.checkWin();
 
 ```

* A player plays an Action card, allowing him to choose cards from another player's hand and play them.
 ```java
 player.play(ActionCard action);
 this.hand.add(action.choose(player2));
 playCards();
 
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 player1.play(Card)
 player2.play(Card)
 player3.play(Card)
 winner.updateScore()
 ```
 
