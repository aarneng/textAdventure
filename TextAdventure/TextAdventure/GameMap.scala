package TextAdventure

import scala.io.StdIn._
import scala.util.Random._


class GameMap(val player: Player) {
  var canWander = false
  var canEnchant = false
  var won: String = ""
  val home = new Area("Home")
  val homeBedRoom = new Room("bedroom", false, home, player)
  val homeLivingRoom = new Room("living room", false, home, player)
  val parentsRoom = new Room("parents room", true, home, player)
  val parentsRoomKey = new Item("Key to parents room")
  parentsRoom.initKey(parentsRoomKey)
  homeLivingRoom.addItem(parentsRoomKey)
  homeLivingRoom.buildDoor(homeBedRoom)
  homeLivingRoom.buildDoor(parentsRoom)

  val slimyCucumber = new Item("A slimy cucumber. There's probably a very slimy snail living here somewhere. Better not touch it, there's no sink in this room", canSell = true, worth = 20)
  parentsRoom.addItem(slimyCucumber)

  val outsideHome = new Room("home garden", false, home, player)
  home.addRoom(homeBedRoom)
  home.addRoom(homeLivingRoom)
  home.addRoom(parentsRoom)
  home.addRoom(outsideHome)

  outsideHome.buildDoor(homeLivingRoom)
  outsideHome.addItem(new Item("map with path to town"))

  home.currentRoom = homeBedRoom
  home.defaultRoom = homeBedRoom

  var currentArea = home

  val town = new Area("town")

  val bar = new Room("bar", false, town, player)
  val sheriffOffice = new Room("sheriff's office", false, town, player)
  val outsideTown = new Room("outside town", false, town, player)
  outsideTown.buildDoor(bar)
  outsideTown.buildDoor(sheriffOffice)

  val pokerroom = new Room ("poker room", false, town, player)
  pokerroom.buildDoor(bar)

  town.addRoom(bar)
  town.addRoom(sheriffOffice)
  town.addRoom(outsideTown)
  town.addRoom(pokerroom)

  town.currentRoom = outsideTown
  town.defaultRoom = outsideTown

  val barQuestItem = new Item("golden shovel")
  var bartender = new NPC("bartender", 650, true)
  bartender.addDialogue("bartender: Hi there, adventurer. Listen, before you buy anything, i have a quest for you\n" +
    s"Can you bring me a ${barQuestItem.name}? I'll give you something cool in return.\nAnyway, what can i do for you? (type /shop to shop).\n"+
      "Also, you learned to wander around. try typing /wander to discover new area, and help me find a golden shovel!")



  bar.addNPC(bartender)




  val cave = new Area("Cave")
  val caveOutside = new Room("outside cave", false, cave, player)
  val caveInside = new Room("inside cave", false, cave, player)
  val spider = new Enemy("cave spider", 20, 0, 2)
  caveInside.addEnemy(spider)
  val trasureKey = new Item("treasure room key")
  spider.addItem(trasureKey)
  val treasureRoom = new Room("treasure room", true, cave, player)
  treasureRoom.initKey(trasureKey)

  cave.addRoom(caveInside)
  cave.addRoom(caveOutside)
  cave.addRoom(treasureRoom)
  caveInside.buildDoor(caveOutside)
  caveInside.buildDoor(treasureRoom)
  treasureRoom.addItem(new Item("bag of money", 300, true))
  cave.currentRoom = caveOutside
  cave.defaultRoom = caveOutside

  val banditHideout = new Area("bandit hideout")
  val outsideHideout = new Room("outside bandit hideout", false, banditHideout, player)
  val hideoutRoom1 = new Room("room inside hideout", false, banditHideout, player)
  val hideoutRoom2 = new Room("another room inside hideout", false, banditHideout, player)
  val banditTreasureRoom = new Room("dusty treasure room", false, banditHideout, player)

  banditHideout.addRoom(outsideHideout)
  banditHideout.addRoom(hideoutRoom1)
  banditHideout.addRoom(hideoutRoom2)
  banditHideout.addRoom(banditTreasureRoom)

  outsideHideout.buildDoor(hideoutRoom1)
  hideoutRoom1.buildDoor(hideoutRoom2)
  hideoutRoom2.buildDoor(banditTreasureRoom)

  var banditTreasure = new Item("dusty shovel")
  banditTreasureRoom.addItem(banditTreasure)
  banditHideout.currentRoom = outsideHideout
  banditHideout.defaultRoom = outsideHideout

  hideoutRoom1.addEnemy(new Enemy("evil man", 10, 1, 2))
  hideoutRoom1.addEnemy(new Enemy("evil cow", 2, 3, 4))
  hideoutRoom1.addEnemy(new Enemy("another evil man", 7, 1, 2))


  hideoutRoom2.addEnemy(new Enemy("posessed statue", 10, 1, 5))
  hideoutRoom2.addEnemy(new Enemy("evil leader", 15, 3, 6))
  hideoutRoom2.addEnemy(new Enemy("a third evil man", 7, 1, 2))


  val wizardTower = new Area("wizard tower")
  val outsideTower = new Room("outside tower", false, wizardTower, player)
  val insideTower = new Room("inside tower", false, wizardTower, player)
  val wizardRoom = new Room("wizard room", false, wizardTower, player)
  outsideTower.buildDoor(insideTower)
  insideTower.buildDoor(wizardRoom)
  wizardTower.currentRoom = outsideTower
  wizardTower.defaultRoom = outsideTower

  wizardTower.addRoom(insideTower)
  wizardTower.addRoom(outsideTower)
  wizardTower.addRoom(wizardRoom)
  val wizard = new NPC("wizard")
  wizard.addDialogue("thank you for seeing me. Even though i have never met you, i'm blessing you with the ability to turn items into gold. also i sell stuff, type /shop")


  val attackPot = new Item("attack potion, +4 damage", worth = 200,consumable = true, canSell = true)
  attackPot.buffType = "damage"
  attackPot.buffAmt = 4
  wizard.addItem(attackPot)

  val healthkPot = new Item("health pot, +15 hp", worth = 200,consumable = true, canSell = true)
  healthkPot.buffType = "health"
  healthkPot.buffAmt = 15
  wizard.addItem(healthkPot)

  val defenceBurger = new Item("defence burger, +3 defence", worth = 200,consumable = true, canSell = true)
  defenceBurger.buffType = "defence"
  defenceBurger.buffAmt = 3
  wizard.addItem(defenceBurger)


  wizardRoom.addNPC(wizard)

  var discovered = Vector(home)

  val goblinHideout = new Area("goblin hideout")
  val goblinOutside = new Room("outside goblin hideout", false, goblinHideout, player)
  goblinHideout.defaultRoom = goblinOutside
  goblinHideout.currentRoom = goblinOutside
  val goblinFightRoom = new Room("goblin fightclub", false, goblinHideout, player)
  goblinOutside.buildDoor(goblinFightRoom)
  goblinFightRoom.addEnemy(new Enemy("goblin boxer", 10, 2, 6))
  goblinFightRoom.addEnemy(new Enemy("a bigger goblin boxer", 18, 2, 5))
  goblinFightRoom.addEnemy(new Enemy("goblin referee", 10, 1, 1))


  goblinFightRoom.addItem(new Item("small pouch of coins", canSell =  true, worth = 50))

  val thor = new NPC("thor")
  thor.addDialogue("hey there, adventurer. I'm selling the most powerful potion known to man, which will turn you into a god. it'll cost ya, though")
  val godPotion = new Item("god potion (+100 of health, damage and defence)", worth = 5000, canSell = true, consumable  = true)
  godPotion.buffAmt = 100
  godPotion.buffType = "health damage defence"
  thor.addItem(godPotion)

  val thorHideout = new Area("thor's hideout")
  val thorRoom = new Room("thor's room", false, thorHideout, player)
  val outsideThor = new Room("outside thor's hideout", false, thorHideout, player)
  thorHideout.currentRoom = outsideThor
  thorHideout.defaultRoom = outsideThor
  thorRoom.addNPC(thor)

  var gamblersDen = new Area("gambler's den")
  var gamblerOutside = new Room("outside gamberl's den", false, gamblersDen, player)
  var gamblerInside  = new Room("inside gamberl's den", false, gamblersDen, player)
  gamblerOutside.buildDoor(gamblerInside)
  gamblersDen.currentRoom = gamblerOutside
  gamblersDen.defaultRoom = gamblerOutside
  val gambler = new NPC("gambler", money = 0)
  gambler.addDialogue("psst, i'm selling a lucky horseshoe for 500 coins. It'll give you more luck when gambling (type /shop)")
  val luckyHorseshoe = new Item("lucky horseshoe", worth = 500, canSell = true)
  gambler.addItem(luckyHorseshoe)
  gamblerInside.addNPC(gambler)



  val areasMap = Map(
    "home" -> home,
    "town" -> town,
    "cave" -> cave,
    "wizard tower" -> wizardTower,
    "bandit hideout" -> banditHideout,
    goblinHideout.name -> goblinHideout,
    thorHideout.name -> thorHideout,
    gamblersDen.name -> gamblersDen
  )

  def playPoker(): Unit = {
    var betAmt = readLine(s"Enter bet amount, or anything else to quit (current money: ${player.money})\n>>> ").toIntOption
    var quit = betAmt.isEmpty
    var skipRound = false
    if (!quit && betAmt.get > player.money) {
      println("you can't afford that amount of money. Please leave, this is a very serious game.")
      skipRound = true
    }
    if (!quit && betAmt.get < 0) {
      println("We only accept positive bets. If you're not going to play, please leave, this is a very serious game.")
      skipRound = true
    }

    while (!quit) {
      if(!skipRound) {
        println("you start playing a round of poker")

        val oddsOfWinning = if (player.items.contains(luckyHorseshoe)) math.max((nextFloat() * 970).floor/10.0, (nextFloat() * 970).floor/10.0) else (nextFloat() * 970).floor/10.0

        if (oddsOfWinning < 15) {
          println(s"you draw 3 terrible cards. odds of winning: $oddsOfWinning%")
        }
        else if (oddsOfWinning < 35) {
          println(s"you draw 2 terrible cards and 1 good card. odds of winning: $oddsOfWinning%")
        }
        else if (oddsOfWinning < 55) {
          println(s"you draw 3 fairly okay cards. odds of winning: $oddsOfWinning%")
        }
        else if (oddsOfWinning < 75) {
          println(s"you draw 3 pretty good cards. odds of winning: $oddsOfWinning%")
        }
        else if (oddsOfWinning < 95) {
          println(s"you draw 3 good cards. odds of winning: $oddsOfWinning%")
        }
        else {
          println(s"you draw 3 amazing cards. odds of winning: $oddsOfWinning%")
        }
        var doubleDown = false
        if (player.money >= betAmt.get * 2 ){
          val ans = readLine("double down? (y/n)\n>>> ")
          doubleDown = ans.nonEmpty && ans.head.toLower == 'y'
          if (doubleDown) println("okay. doubling down on bet")
          else println("not doubling")
        } else println("you can't afford doubling down. continuing..")

        var wonAmt = betAmt.get * (if (!doubleDown) 1 else 2)
        val wonRes = nextFloat() * 100

        if (wonRes <= oddsOfWinning) {
          println(s"the cards are revealed and they're in your favour! you won $wonAmt coins")
        }
        else {
          println(s"the cards are revealed and they're against you! you lost $wonAmt coins")
          wonAmt = -wonAmt
        }

        player.money += wonAmt
      }
      betAmt = readLine(s"Enter bet amount, or anything else to quit (current money: ${player.money})\n>>> ").toIntOption
      quit = betAmt.isEmpty
      skipRound = false
      if (!quit && betAmt.get > player.money) {
        println("you can't afford that amount of money. Please leave, this is a very serious game.")
        skipRound = true
      }
      if (!quit && betAmt.get < 0) {
        println("We only accept positive bets. If you're not going to play, please leave, this is a very serious game.")
        skipRound = true
      }

    }
    if (readLine("confirm leaving? (y)\n>>> ")(0) != 'y') playPoker()
    else {
      println("you leave the room")
      enterRoom(bar)
    }
  }

  def fightEnemy(enemies: Vector[Enemy]): String = {
    println(s"The room ${currentArea.currentRoom.name} contains enemies! \nYou see:\n ${enemies.map(_.name).mkString(", ")}")
    val actions = Vector("attack", "flee", "autoattack")
    var success: Any = None
    while(success == None) {
      val action = getAction.toLowerCase

      action match {
        case "attack" => {
          success = attack()
        }
        case "flee" => {
          println(s"you flee ${this.currentArea.currentRoom.name}")
          currentArea.currentRoom.reset()
          return "flee"
        }
        case "autoattack" => {
          success = attack()
          while (success == None) success = attack()
        }
      }
    }

    def attack(): Any = {
      var toAttack = enemies.filter(_.hitpoints > 0).head
      println(s"attacking ${toAttack.name}")
      var damage = nextInt(player.perks.damage._1) + 2
      toAttack.hitpoints -= damage
      println(s"You deal ${damage} damage! ${toAttack.name} now has ${toAttack.hitpoints} hp left!")

      player.skill match {
        case breather: FireBreather => {
          toAttack.hitpoints -= breather.fireDamage
          println(s"${toAttack.name} took ${breather.fireDamage} hitpoints of fire damage thanks to your fire breather perk.")
        }

        case star: RockStar => {
          for (i <- enemies.filter(_.hitpoints > 0)) {
            i.hitpoints -= star.earPiercingMusicAreaDamage
            println(s"${i.name} took ${star.earPiercingMusicAreaDamage} hitpoints of music damage thanks to your rock star perk.")
            if (i.hitpoints <= 0) println(s"${i.name} died")
          }
        }

        case maker: ExpertCoffeeMaker => {
          if (enemies.exists(_.hitpoints > 0)) {
            if (toAttack.hitpoints <= 0) println(s"${toAttack.name} died")
            println("thanks to your coffee maker perk, you feel energetic, and can attack another time")
            toAttack = enemies.filter(_.hitpoints > 0).head
            println(s"attacking ${toAttack.name}")
            damage = nextInt(player.perks.damage._1) + 2
            toAttack.hitpoints -= damage
            println(s"You deal ${damage} damage! ${toAttack.name} now has ${toAttack.hitpoints} hp left!")
          }
        }
        case _ => {}
      }
      if (toAttack.hitpoints <= 0) println(s"${toAttack.name} died")

      for (i <- enemies.filter(_.hitpoints > 0)) {
        val enemydamge = ((nextFloat()*(i.damageUpper - i.damageLower) + i.damageLower) / scala.math.sqrt(player.perks.resistance._1)).toInt
        player.takeDamage(enemydamge)
        println(s"${i.name} hit you for ${enemydamge}. You have ${player.perks.health._1} hp left.")
        if (player.perks.health._1 <= 0) {
          if (player.die()) won = "false"
          return false
        }
      }
      if (!enemies.exists(_.hitpoints > 0)) true
      else None
    }

    def getAction: String = {
      var input = readLine(s"Select action (${actions.mkString("/")})\n>>> ")
      if (!actions.contains(input)) {
        val correct = returnCorrect(input, actions)
        if (actions.contains(correct)) correct
        else {
          println("please enter new action")
          getAction
        }
      } else input
    }

    if (success == true) {
      println("You beat the level!")
      for (i <- enemies) i.giveallItemsTo(player)
      return "win"
    }
    else {
      println("you didn't beat the level. enemy hp reset")
      return "death"
    }
  }

  def enterRoom(room: Room): Unit = {
    try {
      if (room.open()) {
        val oldRoom = currentArea.currentRoom
        currentArea.currentRoom = room

        var toFight = room.enemies.exists(_.hitpoints > 0)
        room.name match {
            case "poker room"                     => playPoker()
            case "bar"                            => if (player.items.contains(barQuestItem)) {
              finalFight()
              return ()
            }
            case _ => {}
        }

        if (toFight) {
          var fightOutput = fightEnemy(room.enemies)
          if (won == "false") return ()
          if (fightOutput != "win") {
            currentArea.reset()
            currentArea.currentRoom = oldRoom
            if (fightOutput == "death") {
              println("you wake up at home")
              currentArea = home
              currentArea.currentRoom = homeBedRoom
            }
            return ()
          }
        }

        for (i <- room.NPCs) i.talk()
        if (room.NPCs.contains(wizard)) {
          canEnchant = true
          println("You learned to enchant! type /turnintogold <item> to turn the selected item into gold")
        }
      } else println(s"You can't enter ${room.name} from ${currentArea.currentRoom.name}!")

    }
    catch {case ex: Exception => println() }
  }

  def enterRoom(room: String): Boolean = {
    var found = false
    for (i <- currentArea.currentRoom.doorsLeadTo) if (i.name == room && !found) {
      if (i.NPCs.contains(bartender)) canWander = true
      enterRoom(i)
      found = true
    }
    if (!found) {
      println(s"room ${room} not found")
    }
    found
  }

  def finalFight(): Unit =  {
    val evilBartender = new Enemy("evil bartender", 100, 5, 8)
    val fightOutput = fightEnemy(Vector(evilBartender))
    if (fightOutput == "win") won = "true"
    else {
      if (won == "false") return ()
      currentArea.reset()
      if (fightOutput == "death") {
        println("you wake up at home")
        currentArea = home
        currentArea.currentRoom = homeBedRoom
      }
    }
  }

  def wander() = {
    if (canWander) {
      val notDiscovered = areasMap.values.toVector.filter(!discovered.contains(_))
      if (notDiscovered.isEmpty) println("You've already discovered all discoverable areas!")
      else addDiscovery(notDiscovered(nextInt(notDiscovered.length)))
    }
    else println("You can't wander just yet")
  }

  def exploreArea() = {
    println(s"You decide to explore: ${currentArea.name}'s discorvered nearby areas")
    readLine()
    for (i <- discovered.filter(_ != currentArea)) {
      println(s"You see a road leading to: ${i.name}")
    }
    if (discovered.isEmpty) println("it seems like you've found no neighbouring areas. Try wandering around?")
  }

  def moveToArea(name: String) = {
    try {
      val area = areasMap(name.toLowerCase)
      if (discovered.contains(area)) {
        currentArea.reset()
        currentArea = area
        println(s"You moved to: ${area.name}")
        true
      } else false
    }
    catch {
      case ex: Exception => {
        println("This seems to be an area full of errors. I shouldn't go here. Let's try a new place name")
        false
      }
    }
  }

  def addDiscovery(area: Area) = {
    this.discovered :+= area
    println(s"discovered new area: ${area.name}! type " + "\"" +s"/gotoarea ${area.name}" + "\"" + " to move there")
    // the ugly +"\"":s in the middle are because escape doesn't work with string interpolation in scala
  }

  def enchant(itemName: String) = {
    val temp = banditTreasure.name
    itemName match {
      case temp => {
        println(s"turned ${banditTreasure.name} into ${barQuestItem.name}")
        player.items = player.items.map( i => if (i == banditTreasure) barQuestItem else i)
      }
      case _ => {
        if (!itemName.contains("golden") && player.items.map(_.name).contains(itemName)) {
          val item = player.items(player.items.map(_.name).indexOf(itemName))
          if (item.canSell) {

            item.worth = (item.worth * 1.4.toInt)
            println(s"turned ${item.name} into ${"golden" + item.name}")
            item.name = "golden" + item.name
          }
        }
        else (println("you can't turn this item into gold"))
      }
    }
  }

  def pickUpItem(itemName: String) = {

    if (currentArea.currentRoom.itemsInRoom.exists(_.name.toLowerCase == itemName.toLowerCase)) {
      player.addItem(currentArea.currentRoom.itemsInRoom(currentArea.currentRoom.itemsInRoom.map(_.name.toLowerCase).indexOf(itemName)))
      println(s"Picked up ${itemName.toLowerCase}")
      itemName.toLowerCase match {
        case "map with path to town" => {
          this.addDiscovery(this.town)
        }
        case _ => {}
      }
    }
    else println(s"No item named ${itemName.toLowerCase}")
  }
}
