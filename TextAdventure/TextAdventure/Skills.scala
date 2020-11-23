package TextAdventure

abstract class Skills {
  var name: String
}

class NoSkill extends Skills {
  override var name: String = _
}

class FireBreather extends Skills {
  override var name = "Firebreather"

  val masteredFire = true
  val fireDamage = 5
  override def toString: String = "Master firebreather. Like a dragon, but in human form (you deal area damage after each attack)"
}

class RockStar extends Skills {
  override var name = "Rockstar"

  val guitar = new Item("The most epic looking guitar you've seen. It was probably forged inside a dying star by Thor himself")

  val earPiercingMusicAreaDamage = 2

  override def toString: String = "Rockstar. Rock and Roll is the new country, babeyyy (you deal area damage after each attack)"
}

class ExpertCoffeeMaker extends Skills {
  override var name = "Expert coffee maker"
  override def toString: String = "Exper coffee maker. This coffee is actually really good (thanks to your caffeine boost,  you can attack twice)"
}

class RichBastard extends Skills {
  override var name = "Rich bastard"

  override def toString: String = "Rich bastard. You just have a lot of money, that's about it (you have, well, lots of money)"


}

