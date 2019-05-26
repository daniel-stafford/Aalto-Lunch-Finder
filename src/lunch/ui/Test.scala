package lunch.ui

import scala.collection.mutable.Buffer

import lunch._

object Test extends App {

  val dipoli = new Fazer("https://www.fazerfoodco.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=3101&language=en")
  val alvari = new Fazer("https://www.fazerfoodco.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=0190&language=en")
  val aBloc = new Fazer("https://www.fazerfoodco.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=3087&language=en")
  val valimo = new Sodexo("https://www.sodexo.fi/ruokalistat/rss/weekly_rss/13918/en")
  val kvarkki = new Sodexo("https://www.sodexo.fi/ruokalistat/rss/weekly_rss/26521/en")
  val csBuilding = new Sodexo("https://www.sodexo.fi/ruokalistat/rss/weekly_rss/142/en")
  val availableAllergiesTest = Buffer("fish", "peanuts")
  val userAllergiesTest = Buffer("fish")
  val userDishesTest = Buffer("asian")
  val favFoods = dipoli.filterFavs(userDishesTest)
  val filterAllergy = dipoli.filterAllergies(userAllergiesTest)
  val finalmenus = dipoli.finalMenus(userAllergiesTest, userDishesTest)
//  println(dipoli.filterFavs(userDishesTest))
  println(valimo.getRawWeek)

}