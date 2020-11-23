package TextAdventure

import scala.io.StdIn._
import scala.util.Random

class Game {

  var colorBlindMode = false  // you never know
  private var calledDevilAlready = false
  private val player = initGame()
  val gameMap = new GameMap(player)

  initCharacter()
  playGame()
  def initGame() = {
    val name = readLine("You're having a dream. In the dream, your name was...\n")
    println(s"\n\nHello ${name}!\nWelcome to my text adventure!")

    println("The point of the game will be revealed to in later quests, go with the flow from now on (press enter to continue dialogue)")
    readLine()
    println("Oh, and you're just a poor farmer boy in rural texas in the 1700s, trying to learn about life")
    readLine()
    println("There are a few things you've learned about your life.")
    println("For whatever reason, you can type commands, even though you don't computers haven't been invented yet.")
    println("They don't seem to do much yet, though. Maybe you'll learn more some day")
    readLine()
    println("Destined to learn, you want to go explore the world.")
    println("Good luck!\n\n\n\n")
    readLine()
    new Player(name, new Area("Home"))
  }

  def notANumber() = {
    println("Crap, i must've forgotten what numbers are. Why did i never go to school?")
    println("Let me try again, i'll try to type in one of the shapes associated to the skills i dreamt of. " +
            "I think they looked like 1, 2, 3 and 4.")
  }

  def typed69() = println("This might've been one of the most boring numbers i could've thought of. Let's try again")

  def typed420() = {
    println("For whatever reason, my eyes are red now, and spongebob cartoons are so much funnier. I'm also very tired and hungry")
    println("I'll watch some cartoons and eat these pretzel sticks that i found lying around here, in my very historically accurate timeline")
    for (i <- 1 to 20) {
      println(s"Watching episode ${i} out of 20")
      readLine()
    }
    println("Oh boy, that took a while. Glad my eyes aren't red anymore")
    println("Anyway, i should probably try typing a new number")
  }

  def summonTheDevil() = {
    println("The ground begins to crumble right in front of my eyes")
    if (!calledDevilAlready) {
      println("I can't believe what i'm seeing")
      readLine()
      println("This is impossible")
      readLine()
      println("Texas isn't on a tectonic plate. How could the ground be shaking? And why is it localised entirely in my bedroom?")
      readLine()
      println("Suddenly, the devil himself appears")
      println("At least you think so, because you hear a deep voice calling your name.")
      println("You look around the room, but can't see anything. Must've been my imagination")
      readLine()
      println("Then the voice says: \"Um, i'm down here dude. Hi, i'm the devil. Nice to meet you. Why'd you summon me?\"")
      readLine()
      println("The devil's much shorter than you imagined. It also looks like he's a wearing a bad halloween costume, " +
              "and a badly tailored suit. Not very scary")

      println("You tell the devil that you just wanted to learn some skills, and must've accidentally done a satanic ritual. Oops")
      readLine()
      println("The devil replies: \"Don't worry about it, happens all the time. Look, i can't do much, but i'll make you a bit more evil. \n" +
              "If you ever need to reach me, here's my number. Don't hesitate to call me. However, remember that i only do my first visit for free.\n" +
              "Next time, i'll require... payment")
      readLine()
      println("You then see the devil smile evil-y, and leave. Maybe you shouldn't call that number")
      readLine()
      calledDevilAlready = true

      println("Should probably try typing a number associated with a skill now")
    }
    else {
      println("Here comes the devil. Yep, we've all seen this trick. Come on, any day now")
      println("The devil appears in front of you")
      readLine()
      println("\"Do you have the payment? Oh, and i forgot to clarify, by payment i mean a wombat. Sorr if this caused any confusion\"")
      readLine()
      println("I only brought a goat. And what even is a wombat? I don't think those have been invented yet")
      readLine()
      println("No what do you mean haven't been invected yet? It's like a small money, kind of. Ugh, nevermind. \n" +
              "Anyway. My point is, you don't have the payment i wanted. Too bad, you seemed like a nice kid")
      println("Uh oh")
      if (player.die()) gameOver()
    }
  }

  def initCharacter() = {
    println("You wake up from the weird dream.")
    println("In the dream, you kept thinking to yourself of how cool it would be to bocome a ...")
    val skillsAndNumbers = Map(1 -> new FireBreather, 2 ->new RockStar, 3 -> new ExpertCoffeeMaker, 4 -> new RichBastard)

    skillsAndNumbers.keys.foreach( number => println(s"${number}: ${skillsAndNumbers(number)}") )
    println("\nYou think to yourself: Weird, why was i thinking about numbers attached to skills in my dream?")
    println("Maybe the numbers mean something. Let me open up my console...")

    var numberConfirmed = false
    var number = readLine(">>> ").toIntOption

    while (!numberConfirmed) {
      while (number.isEmpty || !(1 <= number.get && number.get <= 4)) {
        if      (number.isEmpty)    notANumber()
        else if (number.get == 69)  typed69()
        else if (number.get == 420) typed420()
        else if (number.get == 666) summonTheDevil()
        else println("ah. let me try again")

        number = readLine(">>> ").toIntOption
      }

      println(s"I'm about to choose ${skillsAndNumbers(number.get).name}")
      numberConfirmed = readLine("Let me enter \"y\" to confirm\n>>> ").toLowerCase.head == 'y'
      if (!numberConfirmed) number = readLine("Let me input a new number...\n").toIntOption
    }
    player.initSkill(skillsAndNumbers(number.get))
    println(s"Even though that was just a dream, it seems like i'm now a ${skillsAndNumbers(number.get).name.toLowerCase} in real life. Huh.\n")
    println(s"You find ${player.money} coins in ye pocket")
    readLine()
  }

  def playGame() = {
    var wonGame = ""
    println(s"You wake up at ${gameMap.currentArea.name}.")
    println("You've just learnt the /help command! great!\nAreas are subdivided into rooms. move between rooms using \"/gotoroom <roomname>\". You can find the roiom names using \"/exploreroom\"")

    while (wonGame == "") {
      val command = excecuteCommand()
      wonGame = gameMap.won
    }
    gameOver()
  }

  private def excecuteCommand() = {
    val input = readLine(">>> ").toLowerCase.split(" ")
    var command = input(0)
    var rest = input.tail.mkString(" ").toLowerCase
    var commands = Vector("/explore", "/explorearea", "/exploreroom", "/movetoarea", "/gotoarea", "/gotoroom", "/movetoroom", "/pickup", "/wander", "/help", "/shop", "/inventory", "/drink", "/eat")
    if (gameMap.canEnchant) commands :+= "/turnintogold"
    if (!commands.contains(command)) {
      command = returnCorrect(command, commands)
    }
    command match {
      // it could be better if commands such as /movetoarea called a single function , todo?
      case "/explore"      => gameMap.exploreArea()
      case "/explorearea"  => gameMap.exploreArea()
      case "/exploreroom"  => gameMap.currentArea.currentRoom.exploreRoom()
      case "/movetoarea"   => {
        if (player.items.exists(_.name == "map with path to town")) {
          val filteredAreas = gameMap.discovered.filter(_ != gameMap.currentArea)
          if (!filteredAreas.map(_.name.toLowerCase).contains(rest)) {
            rest = returnCorrect(rest, filteredAreas.map(_.name))
          }
          val succ = gameMap.moveToArea(rest)
          if (!succ) println("couldn't find area named "+ rest)
        } else println("you can't go to other areas yet. Try to find a map")
      }
      case "/gotoarea"   => {
        if (player.items.exists(_.name == "map with path to town")) {
          val filteredAreas = gameMap.discovered.filter(_ != gameMap.currentArea)
          if (!filteredAreas.map(_.name.toLowerCase).contains(rest)) {
            rest = returnCorrect(rest, filteredAreas.map(_.name))
          }
          val succ = gameMap.moveToArea(rest)
          if (!succ) println("couldn't find area named "+ rest)
        } else println("you can't go to other areas yet. Try to find a map")
      }
      case "/movetoroom"   => {
        val rooms = gameMap.currentArea.currentRoom.doorsLeadTo
        if (!rooms.map(_.name.toLowerCase).contains(rest)) {
          rest = returnCorrect(rest, rooms.map(_.name))
        }
        val succ = gameMap.enterRoom(rest)
        if (!succ) println("couldn't find room named "+ rest)
      }
      case "/gotoroom"   => {
        val rooms = gameMap.currentArea.currentRoom.doorsLeadTo
        if (!rooms.map(_.name.toLowerCase).contains(rest)) {
          rest = returnCorrect(rest, rooms.map(_.name))
        }
        val succ = gameMap.enterRoom(rest)
        if (!succ) println("couldn't find room named "+ rest)
      }
      case "/pickup"       => {
        if (!gameMap.currentArea.currentRoom.itemsInRoom.map(_.name).contains(rest)) {
          rest = returnCorrect(rest, gameMap.currentArea.currentRoom.itemsInRoom.map(_.name))
        }
        gameMap.pickUpItem(rest)
      }
      case "/shop"         => {
        for (i <- gameMap.currentArea.currentRoom.NPCs) i.shop(player)
      }
      case "/wander"       => gameMap.wander()
      case "/help"         => help()
      case "/settings"     => settings()
      case "/inventory"    => {
        for (i <- player.items) println(s"${i.name}: ${if (i.canSell) "worth" + i.worth else "can't be sold" }")
      }
      case "/turnintogold"      => {
        if (gameMap.canEnchant) {
          if (!player.items.map(_.name).contains(rest)) rest = returnCorrect(rest, player.items.map(_.name))
          gameMap.enchant(rest)
        } else println("command not recognised.")
      }
      case "/drink"        => {
        if (!player.items.exists(_.name == rest)) rest = returnCorrect(rest, player.items.map(_.name))
        if (player.items.exists(_.name == rest)) {
          val item = player.items.filter(_.name == rest)(0)
          val success = item.consume(player)
          player.items = player.items.filterNot(_.name == rest)
          if (success) println(s"consumed ${rest}! ${item.buffType} increased by ${item.buffAmt}!")
          else println("you've eaten too much today. you can't eat this :(")

        } else println(s"drink ${rest} not recognised")
      }
      case "/eat"        => {

        if (!player.items.exists(_.name == rest)) rest = returnCorrect(rest, player.items.map(_.name))
        if (player.items.exists(_.name == rest)) {
          val item = player.items.filter(_.name == rest)(0)
          val success = item.consume(player)
          player.items = player.items.filterNot(_.name == rest)
          if (success) println(s"consumed ${rest}! ${item.buffType} increased by ${item.buffAmt}!")
          else println("you've drank too much today. you can't drink this :(")
        } else println(s"drink ${rest} not recognised")
      }
      case _               => println("command not recognised.")
    }
  }

  def help() = {
    println("/explore & /explorearea: reminds you of the nearby areas (like a map, but without graphics)")
    println("/exploreroom: tells you about the items and doors in the room")
    println("/movetoarea <area> & /gotoarea <area>: moves you to the specifies area, if possible")
    println("/movetoroom <room> & /gotoroom <room>: moves you to the specifies room, is possible")
    println("/pickup <item>: picks up the specified item, if possible")
    println("/wander: wandder around nearby areas. You might discover something new...")
    println("/help: helps you with commands if you ever feel lost")
    println("/settings: open settings")
    println("/inventory: see all your items")
    println("/shop: if you're in an area with an NPC that sells items, you can use this item to buy/sell items")
    if (gameMap.canEnchant) println("/turnintogold <item>: turn specifies item into gold")
  }

  def settings() = {
    val word = readLine("Turn color bling mode on? (y/n)\n>>> ")
    if (word.head.toLower == 'y') {
      println("color bling mode is now on")
      colorBlindMode = true
    }
    else if (word.head.toLower == 'n') {
      println("color bling mode is now off")
      colorBlindMode = false
    }
    else println("input not recognised")
  }

  def gameOver() = {
    println("\n" * 10)
    var messages = Vector[String]()
    if (gameMap.won == "false") messages = Vector("oh jollywonkers, looks like i lost")  // TODO: add more messages
    else messages = Vector("congratulations! you beat the evil bartender")
    println(messages(Random.nextInt(messages.length)))
    println("\nThank you for playing\n\n")
    println("Developer: Aarni Haapaniemi")
    println("Designer: Aarni Haapaniemi")
    println("Artist: Aarni Haapaniemi")
    println("Lead programmer: Aarni Haapaniemi")
    println("QA: Aarni Haapaniemi")
    println("Gameplay: Aarni Haapaniemi")
    println("Physics: Aarni Haapaniemi")
    println("AI: Aarni Haapaniemi")
  }
}


