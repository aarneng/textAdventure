package TextAdventure

class Perks {
  var damage = (5, "Your ability to show the ladies (or gentlemen, or people beyons a binary scale) how tough you are. Mostly used to not get killed by monsters though")
  var resistance = (2, "The more resistant i am, the less damage i take. It's like wearing armour, except armour's heavier")
  var health = (20, "your current health. I mean, it's pretty self explanatory")
  var apetite = (1, "Your ability to monch on fine (and not so fine) cuisine")
  var mouthAriditySpotsLeft = (1, "The amount of coctails and drinks you can gulp down")
  var livesLeft = 3
  var lifeMessages = Map(
    3 -> "nothing to worry about",
    2 -> "could be worse",
    1 -> "ohgodohgodohgod i'm going to lose this",
    0 -> "The afterlife's not too bad."
  )
}
