package TextAdventure


import scala.util.Random._
import scala.io.StdIn._
import scala.util.control.Breaks.break

class EmptyNPC(val name: String) {

}

class NPC(name: String, var money: Int = 1000, hasQuest: Boolean = false) extends EmptyNPC(name) {
  var dialogue = ""
  var commands = Vector[String]()
  var items = Vector[Item]()
  var requiredItems = Vector[Item]()
  var questSuccessVoiceline = ""
  var reward: Item = _

  def addReward(toAdd: Item) = reward = toAdd

  def addDialogue(dialog: String) = {
    this.dialogue += dialog
  }

  def addVictorySpeech(speech: String) = questSuccessVoiceline += speech

  def addItem(item: Item) = items :+= item

  def addCommand(command: String) = commands :+= command

  def talk() = {
    val dia = dialogue.split("\n\n")
    for (i <- dia.init) {
      println(i)
      readLine()
    }
    println(dia.last)
  }

  def initQuestItems(items: Vector[Item]) = requiredItems ++= items

  def checkItems(player: Player) = {
    var hasItem = true
    for (i <- items) if (!player.items.contains(i)) hasItem = false
    hasItem
  }

  def questSuccessful() = {
    println(questSuccessVoiceline)
    reward
  }

  def shop(player: Player) = {
    def printItems() = {
      println(s"${name} shows you his items")
      if (items.isEmpty || items.forall(!_.canSell)) {
        println("Unfortunately, they don't have any items")
      }
      for (i <- items) {
        if (i.canSell) println(s"${i.name}: ${i.worth} gold")
      }
      println("\nyour items")
      if (player.items.isEmpty || player.items.forall(!_.canSell)) {
        println("Unfortunately, you don't have any items you can sell")
      }
      for (i <- player.items) {
        if (i.canSell) println(s"${i.name}: ${i.worth} gold")
      }
      println(s"\nyour gold: ${player.money}")
      println(s"${name} gold: ${this.money}")

    }

    println("type /buy <item> to buy item, /sell <item> to sell item, /items to see available items, or \"n\" to exit the shop")
    printItems()
    val commands = Vector("/buy", "/sell", "/items")
    var whatTodo = readLine(">>> ")
    while (whatTodo.nonEmpty && whatTodo.toLowerCase.head != 'n' && whatTodo != "\"n\"")  {
      var myItemName = whatTodo.split(" ").tail.mkString(" ")
      var command = whatTodo.split(" ")(0).toLowerCase
      if (!commands.contains(command)) command = returnCorrect(command, commands)
      command match {
        case "/buy" => {
          if (!items.map(_.name).contains(myItemName)) myItemName = returnCorrect(myItemName, items.map(_.name))
          if (items.map(_.name).contains(myItemName)) {
            val item = items.filter(myItemName == _.name)(0)
            if (player.money >= item.worth) {
              println(s"bought item ${item.name} for ${item.worth}")
              player.addItem(item)

              this.items   =  items.filter(_ != item)
              player.money -= item.worth
              this.money   += item.worth
            } else println("you don't have enough money")
          } else println(s"item ${myItemName} not found")
        }
        case "/sell" => {
          if (!player.items.map(_.name).contains(myItemName)) myItemName = returnCorrect(myItemName, player.items.map(_.name))
          if (player.items.map(_.name).contains(myItemName)) {
            val item = player.items.filter(myItemName == _.name)(0)
            if (this.money >= item.worth) {
              println(s"sold item ${item.name} for ${item.worth}")
              player.removeItems(item)
              this.items :+= item
              player.money += item.worth
              this.money -= item.worth
            } else println(s"${name} doesn't have enough money!")
          } else println(s"item ${whatTodo.split(" ").tail.mkString(" ")} not found")
        }
        case "/items" => {
          printItems()
        }
        case _ => println(s"command $command not recognised. type /buy <item> to buy item, /sell <item> to sell item, /items to see available items, or " + "\"" + "n" + "\"" + " to exit the shop")
      }
      println(s"\nyour gold: ${player.money}")
      println(s"${name} gold: ${this.money}\n")
      whatTodo = readLine(">>> ")
    }
    println("you exit the shop")
  }
}

class Enemy(name: String, val defaultHP: Int, val defaultDL: Int, val defaultDU: Int) extends EmptyNPC(name) {
  var items = Vector[Item]()

  var hitpoints = defaultHP
  var damageLower = defaultDL
  var damageUpper = defaultDU

  def giveallItemsTo(player: Player) = {
    for (i <- items) player.addItem(i)
  }

  def addItem(item: Item) = items :+= item

  def reset() = {
    hitpoints = defaultHP
    damageLower = defaultDL
    damageUpper = defaultDU
  }
}


