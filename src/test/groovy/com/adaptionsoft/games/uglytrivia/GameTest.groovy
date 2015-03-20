package com.adaptionsoft.games.uglytrivia

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by darrenlai on 25/11/14.
 */
class GameTest extends Specification {

  // write test for roll method:
  // there is a bug so capture it.
  // fix it.

  // in penalty box, odd or even number, answered correctly
  @Unroll
  def "is in penalty box is #inPenaltyBox and rollNumber is #rollNumber"() {
    given:
    Game game = new Game()
    game.add("Chet")
    game.inPenaltyBox[0] = inPenaltyBox

    when:
    game.roll(rollNumber)
    game.wasCorrectlyAnswered()

    then:
    game.inPenaltyBox[0] == resultPenaltyBox
    game.purses[0] == purses

    where:
    inPenaltyBox | rollNumber || resultPenaltyBox | purses
    false        | 1          || false            | 1
    false        | 2          || false            | 1
    true         | 1          || false            | 1 // this is bug case!
    true         | 2          || true             | 0
  }
}
