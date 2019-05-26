package lunch.ui

import lunch._
import scala.io.StdIn._

object TextUI extends App {
  private val lunch = new LunchView
  this.run()
  private def run() = {

    //intro
    println(this.lunch.welcomeMessage + this.lunch.introducePart1 + this.lunch.introducePart2)

    //display restaurant list and ask for user input about restaurants
    println(this.lunch.askRest + this.lunch.listRest)
    this.lunch.userRestaurants = this.lunch.addUserRestaurants(readLine("Your favorite restaurants (type your answer): "))

    //confirmation
    println("OK, I'm saving your favorite resaurants as: " + this.lunch.userRestaurants.map(_.name).mkString(", "))

    //ask user about allergies (same pattern as above)
    println(this.lunch.askAllergies + this.lunch.listAllergies)
    this.lunch.addUserAllergy(readLine("your allergies:"))

    //confirmation
    println("Thanks, I'm saving your allergies as: " + this.lunch.userAllergies.mkString(", "))

    //ask user about favorite dishes (same pattern as above)
    println(this.lunch.askDishes + this.lunch.listDishes)
    this.lunch.addUserDish(readLine("your favorite dishes: "))

    //confirmation
    println("Great. I'm saving your favorite dishes as: " + this.lunch.userDishes.mkString(", "))
    
    //summarize the user's preferences and ask them to hit enter
    readLine(s"\nOk, I will get menus from ${this.lunch.userRestaurants.map(_.name).mkString(" and ")} restaurant(s), will note that your allergies are the following: '${this.lunch.userAllergies.mkString(" and ")}', \nand I know that your favorite food is: ${this.lunch.userDishes.mkString(" and ")}. \n\nNow press enter to get the menu!")

    //display menus with allergic food removed and favorites at the top
    println(this.lunch.userRestaurants.map(_.finalMenus(this.lunch.userAllergies, this.lunch.userDishes).mkString("\n")).mkString("\n"))

    //instruct the user to restart the porgram
    println(this.lunch.conclude)
  }
}