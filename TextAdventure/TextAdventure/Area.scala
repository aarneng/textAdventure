package TextAdventure
import scala.io.StdIn._

class Area(val name: String) {

  var connections = Vector[Area]()
  var rooms = Vector[Room]()
  var currentRoom: Room = _
  var defaultRoom: Room = _  //should be a val but i can't figure out how to have it configured without refractoring my code so a var it is


  def reset() = {
    currentRoom = defaultRoom
  }

  def addRoom(room: Room) = this.rooms :+= room

  def addConnection(otherArea: Area) = this.connections :+= otherArea
}

class Room(val name: String, var locked: Boolean, val belongsTo: Area, val player: Player) {
    // todo refractor code st. room isn't dependdant on player


  var doorsLeadTo = Vector[Room]()
  var itemsInRoom = Vector[Item]()
  var keysRequired = Vector[Item]()
  var NPCs = Vector[NPC]()
  var enemies = Vector[Enemy]()


  def initKey(key: Item) = this.keysRequired :+= key

  def initKey(key: Vector[Item]) = this.keysRequired ++= key

  def open() = {
    if (player.canEnter(this)) {
      for (i <- keysRequired) println(s"You used your ${i.name} to enter the room")
      if (locked) println("The door is now unlocked! Hooray !")
      locked = false
      entersRoom()
      true
    }
    else {
      if (keysRequired.length == 1) println(s"The door has a lock. You need a ${keysRequired(0).name} to enter")
      else println(s"The door has ${keysRequired.length} locks. " +
                   s"You'll need: ${for (i <- keysRequired) "\na " + i.name}\n to enter")
      false
    }
  }

  def exploreRoom() = {
    println(s"You explore: ${name}")
    for (i <- itemsInRoom) println(s"You see ${i.name}")
    if (this.itemsInRoom.isEmpty) println("There is nothing interesting in this room")
    readLine()
    println("There are doors leading to:")
    for (i <- this.doorsLeadTo) println(i.name)
  }

  def buildDoor(otherRoom: Room) = {
    if (!doorsLeadTo.contains(otherRoom)) this.doorsLeadTo :+= otherRoom
    if (!otherRoom.doorsLeadTo.contains(this)) otherRoom.doorsLeadTo :+= this
  }

  def entersRoom() = {
    if (!locked) println(s"You enter ${name}, located in ${belongsTo.name}.")
  }

  def addItem(itemToAdd: Item) = this.itemsInRoom :+= itemToAdd

  def addNPC(toAdd: NPC) = {
    this.NPCs :+= toAdd
  }

  def addEnemy(toAdd: Enemy) = {
    this.enemies :+= toAdd
  }

  def reset() = enemies.foreach(_.reset())

}
