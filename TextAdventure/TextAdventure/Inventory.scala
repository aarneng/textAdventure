package TextAdventure

class Inventory {
  var items = Vector[Item]()

  def addItem(item: Item) = {
    if (!items.contains(item)) {
      this.items :+= item
      println(s"Added ${item.name} to inventory! Do /inventory to view all your items")
    }
    else println(s"You already have ${item.name}. The dev must've done an oopsie, as this shouldn't happen")
  }

  def remove(item: Item) = {
    if (items.contains(item)) {
      this.items = items.filter(_ != item)
      println(s"Removed ${item.name} from inventory! Do /inventory to view all your items")
    }
    else println(s"You never had ${item.name}. Did you type it in correctly?\nDo /inventory to view all your items")
  }
}
