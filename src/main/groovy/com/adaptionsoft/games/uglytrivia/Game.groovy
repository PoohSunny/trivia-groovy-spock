package com.adaptionsoft.games.uglytrivia

public class Game {
  ArrayList players = new ArrayList()
  int[] places = new int[6]
  int[] purses  = new int[6]
  boolean[] inPenaltyBox  = new boolean[6]

  LinkedList popQuestions = new LinkedList()
  LinkedList scienceQuestions = new LinkedList()
  LinkedList sportsQuestions = new LinkedList()
  LinkedList rockQuestions = new LinkedList()

  int currentPlayer = 0
  boolean isGettingOutOfPenaltyBox

  public  Game(){
    50.times { def i ->
      popQuestions.addLast("Pop Question " + i)
      scienceQuestions.addLast(("Science Question " + i))
      sportsQuestions.addLast(("Sports Question " + i))
      rockQuestions.addLast(createRockQuestion(i))
    }
  }

  public String createRockQuestion(int index){
    return "Rock Question " + index
  }

  public boolean isPlayable() {
    return (howManyPlayers() >= 2)
  }

  public boolean add(String playerName) {
    players.add(playerName)
    places[howManyPlayers()] = 0
    purses[howManyPlayers()] = 0
    inPenaltyBox[howManyPlayers()] = false

    println playerName + " was added"
    println "They are player number " + players.size()
    return true
  }

  public int howManyPlayers() {
    return players.size()
  }

  public void roll(int roll) {
    println players.get(currentPlayer) + " is the current player"
    println "They have rolled a " + roll

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true

        println players.get(currentPlayer) + " is getting out of the penalty box"
        movePlace(roll)
        askQuestion()
      } else {
        println players.get(currentPlayer) + " is not getting out of the penalty box"
        isGettingOutOfPenaltyBox = false
      }
    } else {
      movePlace(roll)
      askQuestion()
    }
  }

  private void askQuestion() {
    println "The category is " + currentCategory()
    if (currentCategory() == "Pop")
      println popQuestions.removeFirst()
    if (currentCategory() == "Science")
      println scienceQuestions.removeFirst()
    if (currentCategory() == "Sports")
      println sportsQuestions.removeFirst()
    if (currentCategory() == "Rock")
      println rockQuestions.removeFirst()
  }

  private String currentCategory() {
    def currentPlace = places[currentPlayer]
    if ([0, 4, 8].contains(currentPlace)) return QuestionCategory.POP
    if ([1, 5, 9].contains(currentPlace)) return QuestionCategory.SCIENCE
    if ([2, 6, 10].contains(currentPlace)) return QuestionCategory.SPORTS
    return QuestionCategory.ROCK
  }

  public boolean wasCorrectlyAnswered() {
    if (inPenaltyBox[currentPlayer]){
      if (isGettingOutOfPenaltyBox) {
        println "Answer was correct!!!!"
        purses[currentPlayer]++
        println "${players.get(currentPlayer)} now has ${purses[currentPlayer]} Gold Coins."

        boolean winner = didPlayerWin()
        changePlayer()

        return winner
      } else {
        changePlayer()
        return true
      }
    } else {
      println "Answer was corrent!!!!"
      purses[currentPlayer]++
      println "${players.get(currentPlayer)} now has ${purses[currentPlayer]} Gold Coins."

      boolean winner = didPlayerWin()
      changePlayer()

      return winner
    }
  }

  public boolean wrongAnswer(){
    println "Question was incorrectly answered"
    println players.get(currentPlayer)+ " was sent to the penalty box"
    inPenaltyBox[currentPlayer] = true

    changePlayer()
    return true
  }

  private boolean didPlayerWin() {
    return !(purses[currentPlayer] == 6)
  }

  private void changePlayer() {
    currentPlayer++
    if (currentPlayer == players.size()) currentPlayer = 0
  }

  private void movePlace(int roll) {
    places[currentPlayer] = places[currentPlayer] + roll
    if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12
    println "${players.get(currentPlayer)}'s new location is ${places[currentPlayer]}"
  }

  enum QuestionCategory {
    POP('Pop'), SCIENCE('Science'), SPORTS('Sports'), ROCK('Rock')

    private final String value

    QuestionCategory(String value) {
      this.value = value
    }
  }
}
