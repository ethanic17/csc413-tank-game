# csc413-tankgame


| Student Information |                  |
|:-------------------:|------------------|
|  Student Name       | Ethan Zheng      |
|  Student Email      | ezheng2@sfsu.edu |


## Purpose of jar Folder 
The jar folder will be used to store the built jar of your term-project.

`NO SOURCE CODE SHOULD BE IN THIS FOLDER. DOING SO WILL CAUSE POINTS TO BE DEDUCTED`

`THIS FOLDER CAN NOT BE DELETED OR MOVED`

# Required Information when Submitting Tank Game

## Version of Java Used: Java 22 

## IDE used: IntelliJ IDEA Ultimate 2024.01

## Steps to Import project into IDE:
1) Clone repository to your local machine by clicking on the Green code button and copying the URL from HTTPS
2) Open Your Terminal and run git clone [URL] (replace [URL] with the URL you copied)
3) Right click the repo folder you just cloned and select "Open Folder Using IntelliJ IDEA"(assuming you are on Windows)

## Steps to Build your Project:
1) After Project import, navigate to the top left corner and click on “File” then go to “Project Structure”.
2) Find Artifacts and click the “+” icon and select the type to be “JAR” and then “From modules with dependencies”.
3) Select the Main class to be Launcher.java and click OK and exit out of the menu. 
4) Then click on the "Build" button in the top menu and select “Build Artifacts”.
5) The JAR File created will be located under the out folder and inside the subfolder ‘artifacts’.
 
## Steps to run your Project:
1) Using File Explorer (Finder on Mac), navigate to the folder where the JAR file was built and double-click the JAR file to run the program.
2) Alternatively, you can open a terminal and navigate to the folder where the JAR file was built and run the command `java -jar [JAR_FILE_NAME]` (replace [JAR_FILE_NAME] with the name of the JAR file)
3) And you can also run the program from the IDE by right-clicking on the Launcher.java file and selecting "Run Launcher.main()"

## Controls to play your Game:

|               | Player 1 | Player 2 |
|---------------|----------|----------|
|  Forward      | W        | UP       |
|  Backward     | S        | DOWN     |
|  Rotate left  | A        | LEFT     |
|  Rotate Right | D        | RIGHT    |
|  Shoot        | SPACE    | ENTER    |

<!-- you may add more controls if you need to. -->