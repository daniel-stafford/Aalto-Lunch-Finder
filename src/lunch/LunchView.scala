package lunch

import scala.collection.mutable.Buffer

//This class contains most the variables and methods used for the textUI, 
//including available preferences, converting user input to preference buffers, and stock dialogue

class LunchView {

  //Available restaurants
  val dipoli: Fazer = new Fazer("https://www.fazerfoodco.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=3101&language=en")
  val alvari: Fazer = new Fazer("https://www.fazerfoodco.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=0190&language=en")
  val valimo: Sodexo = new Sodexo("https://www.sodexo.fi/ruokalistat/rss/weekly_rss/13918/en")
  val kvarkki: Sodexo = new Sodexo("https://www.sodexo.fi/ruokalistat/rss/weekly_rss/26521/en")

  //Preset buffers containing lists of available restaurants, allergies, favorite dishes, favorite restaurants
  val availableRestaurants: Buffer[Restaurant] = Buffer(dipoli, alvari, valimo, kvarkki)
  val availableAllergies: Buffer[String] = Buffer("fish", "peanuts", "wheat", "shellfish")
  val availableDishes: Buffer[String] = Buffer("mexican", "asian", "american", "indian")

  //Empty preference buffers for allergies, favorite dishes, and restaurants that user will populate
  var userRestaurants: Buffer[Restaurant] = Buffer()
  var userAllergies: Buffer[String] = Buffer()
  var userDishes: Buffer[String] = Buffer()

  //Test user input
  val userRestaurantsTest: Buffer[Restaurant] = Buffer(dipoli, alvari)
  val userAllergiesTest: Buffer[String] = Buffer("fish", "peanuts")
  val userDishesTest: Buffer[String] = Buffer("pizza")

  //Method that deals with user input (turns it into a buffer, populate user preference buffers)
  def userInputToBuffer(userInput: String): Buffer[String] = {
    userInput.filterNot((x: Char) => x.isWhitespace)
      .split(",")
      .toBuffer
  }

  //take user input and populate the corresponding preference buffers
  def addUserRestaurants(userInput: String): Buffer[Restaurant] = {
    var result: Buffer[Restaurant] = Buffer()
    for (command ← userInputToBuffer(userInput)) {
      result = command.toLowerCase() match {
        case "alvari"  ⇒ Buffer(alvari)
        case "dipoli"  ⇒ Buffer(dipoli)
        case "valimo"  ⇒ Buffer(valimo)
        case "kvarkki" ⇒ Buffer(kvarkki)
        case _         ⇒ Buffer(alvari, dipoli, valimo, kvarkki) //default input if user types nothing or incorrectly
      }
      userRestaurants = userRestaurants ++ result
    }
    userRestaurants = userRestaurants.sortWith(_.name > _.name).distinct //kills any duplicates
    userRestaurants
  }

  def addUserAllergy(userInput: String): Buffer[String] = {
    var result: Buffer[String] = Buffer()
    for (command ← userInputToBuffer(userInput)) {
      result = command.toLowerCase() match {
        case "fish"    ⇒ Buffer("fish")
        case "peanuts" ⇒ Buffer("peanuts")
        case "wheat" ⇒ Buffer("wheat")
        case "shellfish" ⇒ Buffer("shellfish")
        case _         ⇒ Buffer("") //default input if user types nothing or incorrectly
      }
      userAllergies = userAllergies ++ result
    }
    userAllergies = userAllergies.sortWith(_ > _).distinct //kills any duplicates
    userAllergies
  }

  def addUserDish(userInput: String): Buffer[String] = {
    var result: Buffer[String] = Buffer()
    for (command ← userInputToBuffer(userInput)) {
      result = command.toLowerCase() match {
        case "mexican"  ⇒ Buffer("mexican")
        case "asian"    ⇒ Buffer("asian")
        case "american" ⇒ Buffer("american")
        case "indian"   ⇒ Buffer("indian")
        case _          ⇒ Buffer("pizza") ////default input if user types nothing or incorrectly
      }
      userDishes = userDishes ++ result
    }
    userDishes = userDishes.sortWith(_ > _).distinct //kills any duplicates
    userDishes
  }

  //Reusable phrases for the UI
  val dash: String = "\n------------\n"

  //stringify available restaurants, allergies, dishes, etc.
  val availableRestaurantsString: String = availableRestaurants.map(_.name).mkString("\n")
  val availableAllergiesString: String = availableAllergies.mkString("\n")
  val availableDishesString: String = availableDishes.mkString("\n")

  //Dialogue in chronological order
  val welcomeMessage: String = s"You look hungry..."
  val introducePart1: String = s"\n\nLet's help you get some lunch.\n\nTo briefly explain, I'm a simple Scala application that collects real-time menu data\nfrom student restaurants around Otaniemi."
  val introducePart2: String = s"\n\nTo find you the perfect menu, I need to get some of your preferences first. You will be typing out your answers. \nNote: type carefully, as any typos will cause me to delete your entire command and record you as merely hitting \nthe enter key."
  
  val askRest: String = s"\nDo you have a favorite place to eat? From which of the below restaurants would you like to see menus? \nTell me by simply typing the restaurant name (e.g. Dipoli).\nIf you would like to see menus from more than one restaurant, seperate the restaurant names by a common (e.g. Dipoli, Valimo)\nIf you are OK with the current list, simply press the enter key.\n"
  val listRest: String = s"$dash$availableRestaurantsString$dash"
  
  val askAllergies: String = s"\nNext question: do you have any allergies to the below foods? If so, write it down!\nIf you have more than one allergy, please seperate them by a comma (e.g. chicken, fish)\nIf you don't have any allergies, just hit enter.\n"
  val listAllergies: String = s"$dash$availableAllergiesString$dash"
  
  val askDishes: String = s"\nFinally! Last question! What's your favorite food from the below list? Don't be shy, write it down!\nIf you have more than one favorite food, please seperate them by a comma (e.g. pizza, pasta)\nIf you are strange and don't like any of below foods, just hit enter (I'm going to look for \npizza anyway because who doesn't like pizza :).\n"
  val listDishes: String = s"$dash$availableDishesString$dash"
  
  val conclude: String = s"\n\nOK, that's it! If you are unhappy with your result, run this application again and try again!"
  //
}
