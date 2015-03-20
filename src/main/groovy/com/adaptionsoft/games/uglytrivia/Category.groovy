package com.adaptionsoft.games.uglytrivia

class Category {

  String name
  LinkedList<Question> questions = new LinkedList<>()

  Category(String name) {
    this.name = name
    50.times { def i ->
      questions.addLast("${name} Question " + i)
    }
  }
}
