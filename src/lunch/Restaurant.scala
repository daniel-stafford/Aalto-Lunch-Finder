package lunch

import scala.xml._
import scala.collection.mutable.Buffer

//This trait represents all the common methods and variables shared between Sodexo and
//Fazer classes, which are types of restaurants.

trait Restaurant {

  val name: String
  val nameAndDate: String
  def getDay(dayOfWeek: String): Buffer[String]

  //get entire's week menu's without any filtering
  def getRawWeek: Buffer[String] = {
    getDay("Monday") ++
      getDay("Tuesday") ++
      getDay("Wednesday") ++
      getDay("Thursday") ++
      getDay("Friday")
  }

  //for the following filter methods, nested loops would be cleaner code, couldn't get the syntax correct.
  def filterAllergies(allergies: Buffer[String]): Buffer[String] = {
    var filteredWeek: Buffer[String] = Buffer()
    for (allergy ← allergies)
      filteredWeek = allergy.toLowerCase() match {
        case "fish" ⇒ getRawWeek.filterNot(dish => dish.toLowerCase().contains("fish")
          || dish.contains("salmon")
          || dish.toLowerCase().contains("trout")
          || dish.toLowerCase().contains("tuna")
          || dish.toLowerCase().contains("cod"))
        case "peanuts" ⇒ getRawWeek.filterNot(dish => dish.toLowerCase().contains("peanuts")
          || dish.toLowerCase().contains("peanut"))
        case "wheat" ⇒ getRawWeek.filterNot(dish => dish.toLowerCase().contains("wheat")
          || dish.toLowerCase().contains("barley")
          || dish.toLowerCase().contains("buckwehat")
          || dish.toLowerCase().contains("maize")
          || dish.toLowerCase().contains("corn")
          || dish.toLowerCase().contains("oat")
          || dish.toLowerCase().contains("rice")
          || dish.toLowerCase().contains("rye"))
        case "shellfish" ⇒ getRawWeek.filterNot(dish => dish.toLowerCase().contains("shellfish")
          || dish.toLowerCase().contains("lobster")
          || dish.toLowerCase().contains("oyster")
          || dish.toLowerCase().contains("shrimp")
          || dish.toLowerCase().contains("snail")
          || dish.toLowerCase().contains("squid")
          || dish.toLowerCase().contains("crab")
          || dish.toLowerCase().contains("scallops"))
        case _ ⇒ this.getRawWeek //default value if user types nothing or incorrectly
      }
    filteredWeek
  }

  def filterFavs(dishes: Buffer[String]): Buffer[String] = {
    var filteredWeek: Buffer[String] = Buffer()
    var result: Buffer[String] = Buffer()
    for (dish ← dishes) {
      result = dish.toLowerCase() match {
        case "mexican" ⇒ this.getRawWeek.filter(dish => dish.toLowerCase().contains("mexican")
          || dish.toLowerCase().contains("burrito")
          || dish.toLowerCase().contains("taco")
          || dish.toLowerCase().contains("latin"))
        case "asian" ⇒ this.getRawWeek.filter(dish => dish.toLowerCase().contains("asia")
          || dish.toLowerCase().contains("chinese")
          || dish.toLowerCase().contains("thai")
          || dish.toLowerCase().contains("japaense")
          || dish.toLowerCase().contains("tofu")
          || dish.toLowerCase().contains("asia")
          || dish.toLowerCase().contains("wok"))
        case "american" ⇒ this.getRawWeek.filter(dish => dish.toLowerCase().contains("hamburger")
          || dish.toLowerCase().contains("fries")
          || dish.toLowerCase().contains("american"))
        case "indian" ⇒ this.getRawWeek.filter(dish => dish.toLowerCase().contains("indian")
          || dish.toLowerCase().contains("curry")
          || dish.toLowerCase().contains("tandoori")
          || dish.toLowerCase().contains("naan"))

        case _ ⇒ this.getRawWeek.filter(dish => dish.toLowerCase().contains("pizza")) //default value if user types nothing or incorrectly

      }
      result = result.length match {
        case 0 ⇒ Buffer(s"\n$name has no $dish dishes this week.")
        case _ ⇒ Buffer(s"\n$name has the following $dish dishes this week:\n") ++ result
      }
      filteredWeek = filteredWeek ++
        result
    }
    filteredWeek
  }

  //outputs the final filtered menu 
  def finalMenus(userAllergies: Buffer[String], userDishes: Buffer[String]): Buffer[String] = {
    this.filterFavs(userDishes) ++ this.filterAllergies(userAllergies)
  }

}