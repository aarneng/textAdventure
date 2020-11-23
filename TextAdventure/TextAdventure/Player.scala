package TextAdventure

class Player(val name: String, private var currentLocation: Area) extends Inventory {

  def enterArea(areaToEnter: Area) = {
    this.currentLocation = areaToEnter
  }

  var perks = new Perks()
  var money = 100
  var skill: Skills = _

  def initSkill(newSkill: Skills) = {
    this.skill = newSkill
    if (this.skill.name == "Rich bastard") money = 3500
  }
  private var homeroom = new Area("Your bedroom, at home")

  def die() = {
    this.perks.livesLeft -= 1
    println(s"You lost a life. Lives left: ${perks.livesLeft}")
    println(perks.lifeMessages(perks.livesLeft))
    this.perks.health = (20, this.perks.health._2)
    this.perks.livesLeft == 0
  }

  def canEnter(room: Room) = {
    var ret = true
    for (i <- room.keysRequired) if (!this.items.contains(i)) ret = false
    ret
  }

  def removeItems(item: Item) = this.items = this.items.filter(_ != item)

  def removeItems(items: Vector[Item]) = for (i <- this.items) this.items = this.items.filter(_ != i)

  def takeDamage(damage: Int) = this.perks.health = (perks.health._1 - damage, perks.health._2)
}



