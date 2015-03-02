package com.adaptionsoft.games.uglytrivia

import spock.lang.Ignore
import spock.lang.Specification

class GameUnderTest extends Game{
  List msgs = []

  @Override
  void printMessage(Object arg) {
    msgs << arg
  }
}

class GameTest extends Specification {


  def "correct answer get a coin"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.roll(1)
    game.wasCorrectlyAnswered()
    expect:
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Answer was corrent!!!!', 'Player 1 now has 1 Gold Coins.']
  }

  def "go into penalty box"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.roll(1)
    game.wrongAnswer()
    expect:
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Question was incorrectly answered', 'Player 1 was sent to the penalty box']
  }

  def "after in penalty box, roll even number cannot get out of penalty box"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.roll(1)
    game.wrongAnswer()
    game.roll(2)
    expect:
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Question was incorrectly answered', 'Player 1 was sent to the penalty box', 'Player 1 is the current player', 'They have rolled a 2', 'Player 1 is not getting out of the penalty box']
  }

  def "after in penalty box, roll odd number to get out of penalty box"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.roll(1)
    game.wrongAnswer()
    game.roll(1)
    expect:
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Question was incorrectly answered', 'Player 1 was sent to the penalty box', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1 is getting out of the penalty box', 'Player 1\'s new location is 2', 'The category is Sports', 'Sports Question 0']
  }

  def "change to next player after answer question"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.add("Player 2")
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    game.wasCorrectlyAnswered()
    expect:
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 2 was added', 'They are player number 2', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Answer was corrent!!!!', 'Player 1 now has 1 Gold Coins.', 'Player 2 is the current player', 'They have rolled a 1', 'Player 2\'s new location is 1', 'The category is Science', 'Science Question 1', 'Answer was corrent!!!!', 'Player 2 now has 1 Gold Coins.']
  }
@Ignore
  def "player is out of penalty box after roll odd number and answer question correctly"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.roll(1)
    game.wrongAnswer()
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    expect:
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Question was incorrectly answered', 'Player 1 was sent to the penalty box', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1 is getting out of the penalty box', 'Player 1\'s new location is 2', 'The category is Sports', 'Sports Question 0', 'Answer was correct!!!!', 'Player 1 now has 1 Gold Coins.', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 3', 'The category is Rock', 'Rock Question 0']
  }

  def "player wins after accumulated 6 coins and game ends"() {
    given:
    GameUnderTest game = new GameUnderTest()
    game.add("Player 1")
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    game.wasCorrectlyAnswered()
    game.roll(1)
    expect:
    game.wasCorrectlyAnswered() == false
    System.err.println("${game.msgs.inspect()}")
    game.msgs == ['Player 1 was added', 'They are player number 1', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 1', 'The category is Science', 'Science Question 0', 'Answer was corrent!!!!', 'Player 1 now has 1 Gold Coins.', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 2', 'The category is Sports', 'Sports Question 0', 'Answer was corrent!!!!', 'Player 1 now has 2 Gold Coins.', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 3', 'The category is Rock', 'Rock Question 0', 'Answer was corrent!!!!', 'Player 1 now has 3 Gold Coins.', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 4', 'The category is Pop', 'Pop Question 0', 'Answer was corrent!!!!', 'Player 1 now has 4 Gold Coins.', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 5', 'The category is Science', 'Science Question 1', 'Answer was corrent!!!!', 'Player 1 now has 5 Gold Coins.', 'Player 1 is the current player', 'They have rolled a 1', 'Player 1\'s new location is 6', 'The category is Sports', 'Sports Question 1', 'Answer was corrent!!!!', 'Player 1 now has 6 Gold Coins.']
  }
}
