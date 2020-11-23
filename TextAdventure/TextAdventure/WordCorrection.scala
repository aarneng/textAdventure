import scala.io.StdIn.readLine

package object TextAdventure {
  def returnCorrect(input: String, words: Vector[String]): String = {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * *
    *                                                    *
    *      Naive implementation of text correction       *
    *      comparing levenstein distances of input       *
    *      and all logical/possible inputs               *
    *                                                    *
    * * * * * * * * * * * * * * * * * * * * * * * * *  * */
    if (input.isBlank || words.isEmpty) return input

    if (words.length == 1){
      val ans = readLine(s"input ${input} not recognised. Did you mean ${words(0)}? (y/n)\n>>> ")
      if (!ans.isBlank && ans.head.toLower == 'y') return words(0)
      else return input
    }

    var minWord1 = "placeholder value"
    val substrings = input.split(" ")

    for (i <- words) {
      for (j <- substrings) if (i.contains(j)) minWord1 = i
      // slight optimisation using substring comparison
    }

    if (minWord1 != "placeholder value"){
      val ans = readLine(s"input ${input} not recognised. Did you mean ${minWord1}? (y/n)\n>>> ")
      if (!ans.isBlank && ans.head.toLower == 'y') return minWord1
    }
    // if substring comparison doesn't work find smallest levenstein distance
    var minWord = words(0)
    var min = levenshtein(words(0), input)
    for (i <- words.tail.map(_.toLowerCase)) {
      val temp = levenshtein(i, input)
      if (temp < min) {
        min = temp
        minWord = i
      }
    }
    if (minWord == minWord1) return input
    else if (minWord1 != "placeholder value") println("okay. trying another guess")

    if (min < 5) {
      println(s"input ${input} not recognised. Did you mean ${minWord}? (y/n)")
      val typoAnswer = readLine(">>> ")
      if (!typoAnswer.isBlank && typoAnswer.head.toLower == 'y') minWord
      else input
    } else {
      println("input not recognised.")
      input
    }
  }

  def levenshtein(text1: String, text2: String): Int = {
    //shamelessly stolen from o1's 12.1 example
    if (text1.isEmpty || text2.isEmpty) {
      math.max(text1.length, text2.length)
    } else if (text1.head == text2.head) {
      levenshtein(text1.tail, text2.tail)
    } else {
      val deletion     = levenshtein(text1.tail, text2     )
      val insertion    = levenshtein(text1,      text2.tail)
      val substitution = levenshtein(text1.tail, text2.tail)
      Vector(deletion, insertion, substitution).min + 1
    }
  }
}
