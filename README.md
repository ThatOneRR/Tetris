# Tetris

Woah, another one? This one preceds SlayOne and was sort of a testament for me to see whether or not SlayOne was a possible thing to do in my capability. This is also the first standalone project that I've did.

Contains some bugs (heck, the pieces can go out of screen occasionally) and some very inefficient code but the concept is there :D.

### Uh, cool.... how'd I run it doe?

That is a *great* question. Java's mildly annoying point is that everything compiles as a runnable .jar file. There's a quick and dirty way to compile the jar file though:
1. Zip up the following contents:
  - 'me' folder (direct child of src)
  - All the contents of the 'resources' folder (namely sprites and audio folders)
  - 'META-INF' folder (direct child of src)
2. Rename the zip file to Tetris.jar

From here you just need to run the jar file, either through the command prompt:
1. Open up a command prompt and navigate to the directory containing the jar
2. Execute `java -jar Tetris.jar` (you can add jvm arguments if you want!)
or through a .bat file
1. Create a text document and write `java -jar Tetris.jar` inside
2. Save the file as a .bat file and run it. (Make sure the .bat file is in the same folder as the jar!)
