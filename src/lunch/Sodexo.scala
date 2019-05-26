package lunch

import scala.collection.JavaConverters._
import org.jsoup.Jsoup
import scala.xml._
import scala.collection.mutable.Buffer
import scala.util.matching.Regex

//This class parses all Sodexo restaurants which share the same XML data format.
//For an example of XML data from Fazer, go to
//https://www.sodexo.fi/ruokalistat/rss/weekly_rss/13918/en

class Sodexo(val url: String) extends Restaurant {

  val parsed: Elem = XML
    .load(url) //using the Scala XML library to actively load the XML from the interwebs

  val name: String = (parsed \\ "title")(1)
    .text
    .split(" ")
    .toBuffer(1) //roundabout but works as the XML doesn't isolate the restaurant name anywhere

  val nameAndDate: String = (parsed \\ "title")(1)
    .text

  //Sodexo's XML data is poorly formatted and a large portion is in CDATA form. CDATA is non-traversable using normal XML methods. Regex was my solution.
  def getDay(dayOfWeek: String): Buffer[String] = {
    val dayHeader: Buffer[String] = Buffer(s"\n ---- $nameAndDate ----  \n \n${dayOfWeek.toUpperCase}:\n")
    
    val targetCDATA: String = ((parsed \\ "description")(1)).text + "stop" //get entire CDATA string (Monday-Friday menu, "stop" aids in writing regex pattern for Friday's food)

    val pattern = dayOfWeek.toLowerCase match {
      case "monday"    ⇒ """(?s)Monday(.*?)Tuesday""".r
      case "tuesday"   ⇒ """(?s)Tuesday(.*?)Wednesday""".r
      case "wednesday" ⇒ """(?s)Wednesday(.*?)Thursday""".r
      case "thursday"  ⇒ """(?s)Thursday(.*?)Friday""".r
      case "friday"    ⇒ """(?s)Friday(.*?)Stop""".r //
    }

    val menu: Buffer[String] = (Jsoup
      .parse(pattern.findAllIn(targetCDATA).mkString)) //outputs CDATA between regex values (e.g. Monday's menu)
      .select("strong") //make a list of Jsoup elements for each <strong>..text..</strong>
      .asScala //allows me to go from Jsoup (essentially Java) back to Scala
      .map(_.text()) //map each Jsoup element as a Scalla buffer element
      .zipWithIndex //get index values
      .filter(_._2 % 2 == 1) //filter out even indexes (even indexes are descriptions in Finnish)
      .map(_._1) //odd indexes are in English
    dayHeader ++ menu
  }

}
