package TextAdventure

class Item(var name: String, var worth: Int = 0, val canSell: Boolean = false, val consumable: Boolean = false) {

  var buffAmt: Int = 0
  var buffType: String = ""
  var drink = false

  def consume(player: Player): Boolean  = {
    if (!((drink && player.perks.mouthAriditySpotsLeft._1 > 0) || (!drink && player.perks.apetite._1 > 0))) return false

      val toCheck = buffType.split (" ")
      if (toCheck.contains("damage")) player.perks.damage = (player.perks.damage._1 + buffAmt, player.perks.damage._2)
      if (toCheck.contains("resistance")) player.perks.resistance = (player.perks.resistance._1 + buffAmt, player.perks.resistance._2)
      if (toCheck.contains("health")) player.perks.health = (player.perks.health._1 + buffAmt, player.perks.health._2)

    if (drink) player.perks.mouthAriditySpotsLeft = (player.perks.mouthAriditySpotsLeft._1 - 1, player.perks.mouthAriditySpotsLeft._2)
    else player.perks.apetite = (player.perks.apetite._1 - 1, player.perks.apetite._2)
    true
  }

}


