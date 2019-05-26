package lunch

import scala.xml._
import org.jsoup.Jsoup
import scala.collection.JavaConverters._
import scala.util.matching.Regex
import scala.collection.mutable.Buffer

//This class parses all Fazer restaurants which share the same XML data format.  
//For an example of XML data from Fazer, go to
//https://www.fazerfoodco.fi/modules/MenuRss/MenuRss/CurrentWeek?costNumber=3101&language=en

class Fazer(val url: String) extends Restaurant {
  
  val parsed: Elem = XML.load(url)
  
  val name: String = (parsed \\ "description")(0).text
  
  val nameAndDate: String = s"$name ${ 
    (parsed \\ "title")(1)
      .text
      .split(",")
      .toBuffer(1)
  } -${
    (parsed \\ "title")(5).text.split(",")
      .toBuffer(1)
  }"

  def getDay(dayOfWeek: String): Buffer[String] = {
    val dayHeader: Buffer[String] = Buffer(s"\n ---- $nameAndDate ----  \n \n${dayOfWeek.toUpperCase}:\n")
    val dayIndex: Int = dayOfWeek.toLowerCase() match {
      case "monday"    ⇒ 1
      case "tuesday"   ⇒ 2
      case "wednesday" ⇒ 3
      case "thursday"  ⇒ 4
      case "friday"    ⇒ 5
    }
    def stripTags(str: String) = {
      str.replaceAll("\\<.*?\\>", "")
    }
    val menuElems: Buffer[String] = stripTags((parsed \\ "description")(dayIndex).text)
      .split("\n")
      .toBuffer
      .filter(_.length > 1)
     dayHeader ++ menuElems
  }

}