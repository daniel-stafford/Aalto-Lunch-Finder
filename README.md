The program is a simple text based UI.  Directions on use:
1.	Import the lunch application zip file into Eclipse.
2.	You will see errors regarding the external libraries.  Proceed to the next step. 
3.	Add the following jar files to the build path.  You can find them in the zip’s JAR folder or you can download them yourself at the following links (choose the .jar file)
a)	JSoup: https://mvnrepository.com/artifact/org.jsoup/jsoup/1.11.3
b)	Scala XML https://mvnrepository.com/artifact/org.scala-lang.modules/scala-xml_2.12/1.2.0
c)    Once you’ve added the external JARs, delete the old duplicates(JSoup and Scala XML) with the red error marks next to them.
4.	 Click apply and the errors should be gone.
5.	Ensure you are connected to the internet, as this project gathers real-time data from the internet.
6.	Within the newly imported project go to Lunch: src: lunch.ui. Right click on ‘TextUI.scala’ and choose ‘Run as Scala Application’
7.	A console box should appear and after a few seconds, text should appear explaining how the program works.
8.	The user is asked three questions about favorite restaurants, allergies, and favorite foods.  
9.	User input is by keyboard only.  The user can only write nouns from a displayed list.  No other commands are allowed.
10.	If the user doesn’t follow directions properly (e.g. commits typos, omits commas, etc.) default values, as the text UI dialogue explains, are used.
11.	Upon submitting each answer, the program will tell the user how their answer has been recorded.
12.	After answering the three questions, a summary of the user’s preferences are displayed. 
13.	After hitting enter, the current week’s menu is printed, which includes only favorite restaurants, removes dishes with the specified allergy(s), and indicates whether the user’s favorite dishes are served that week and lists any of those favorited dishes.
14.	Finally, the program concludes and asks the user to run it again if different results are desired.
