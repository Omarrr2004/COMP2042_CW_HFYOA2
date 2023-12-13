# COMP2042_CW_HFYOA2
• Compilation Instructions: Provide a clear, step-by-step guide on how to compile the code to produce the application. Include any dependencies or special settings required.

1.Prerequisites:
-Java Development Kit (JDK): Make sure you have the JDK installed. JDK 11 or later is recommended forJavaFX applications.

-JavaFX SDK: If you are using JDK 11 or later, you'll also need to download JavaFX separately, as it's no longer included with the JDK. You can download JavaFX SDK from OpenJFX.

-IDE (Optional): While not strictly necessary, using an Integrated Development Environment like Eclipse, IntelliJ IDEA, or NetBeans can simplify the process of compiling and running Java applications.

2.Setting Up Your Project:
-Create a Project: Open the IDE and create a new Java project. If you're not using an IDE, create a directory on your computer to house the project.

-Add Source Files: Copy the Java source files into the project's src directory. Ensure you maintain the package structure as defined in your code.

-Add JavaFX to the Project:
In your IDE, add the JavaFX SDK as a library to your project. This process will vary depending on the IDE you are using.
If using the command line, remember the path to the JavaFX lib folder, as you'll need it to compile and run the application.

3.Dependencies:
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>

<groupId>org.example</groupId>
<artifactId>LabTest2</artifactId>
<version>1.0-SNAPSHOT</version>

   <properties>
       <maven.compiler.source>19</maven.compiler.source>
       <maven.compiler.target>19</maven.compiler.target>
       <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
   </properties>







• Implemented and Working Properly: List the features that have been successfully implemented and are functioning as expected. Provide a brief description of each.

Several features have been implemented like:

- Paddle mechanics: this function activates when the ball hits either block 1 or block 2 which makes the paddle either increase in width or decrease for 9 seconds with no interruptions.


-Added an mp3 audio player to the game: this adds a theme song to the game improving the experience of the player because it goes smoothly with the actual horror theme of the game. There is also an option in the menu panel that gives you the choice to mute before you start playing.

- Added an M case: pressing the key ‘M’ mutes the background theme audio and then when pressed again it continues, depending on the player’s will.

- Added a P case: pressing the key “P” pauses the game at the player’s will and then pressing it again resumes the game whenever the player is ready again.

-Added a menu page: it includes a background theme to the game, a “start” button for the user to press to start playing the game and a sound option whether or not to mute the music before starting the game.

-User Interface and Visuals: The user interface, including the main menu and in-game visuals, is well-implemented. The game provides a visually appealing and user-friendly interface, complete with buttons, labels, and game scenes.

-Game Over Handling: Upon losing the game, the application displays a game over screen, offering a visual cue to the player about the game's end.

-Gold ball: made one of the features of the gold ball is to go through blocks like an arrow and decreased its time to 3 or 4 seconds

-Heart block: made the heart block appear just once every level starting from level 6 making the game a little harder.




• Implemented but Not Working Properly: List any features that have been implemented but are not working correctly. Explain the issues you encountered, and if possible, the steps you took to address them.

The game ball: the collision between the ball and the LEFT side of the blocks is not fully correct. The ball phases through the block destroying it without bouncing off.


• Features Not Implemented: Identify any features that you were unable to implement and provide a clear explanation for why they were left out.

Making unbreakable blocks towards the last 8 levels to make the game even harder. The placement of these blocks would have been specific, making it harder as the levels increase. This feature is not implemented because the collision with the left side of the block was full of bugs I couldn't fix, which made the whole block glitchy and impossible for me to implement.

-Couldn't refactor the code into packages because it started deleting classes of its own and ruining the code.

• New Java Classes: Enumerate any new Java classes that you introduced for the assignment. Include a brief description of each class's purpose and its location in the code.

-BallPhysics: The BallPhysics class handles the physics and movement of the ball in the game.
It includes methods for setting the physics of the ball and resetting its position.

-DrawBlock: The DrawBlock class is responsible for drawing the visual representation of blocks in the game. It handles the positioning, sizing, and texturing of block elements based on their attributes.


-GameAudio: The GameAudio class handles audio functionalities in the game.It includes methods to manage audio settings like muting, playing, and stopping game sounds.


-GameHandling: The GameHandling class manages key events and movements within the game. It provides static methods to handle keyboard inputs and control game elements like the paddle.

-GameVisuals: The GameVisuals class handles the creation and setup of the game's visual elements, including the main menu and audio controls.


-GamWon: The GameWon class contains methods to handle game-winning scenarios and display winning messages.
-Initialize: The Initialize class contains static methods for initializing various game elements, such as the ball, the break (player-controlled block), and the game board.

-LevelManagement: The LevelManagement class contains methods to manage level transitions and game restarts.


-OnUpdates: The OnUpdates class contains methods for updating the game state and processing physics. These methods are called during the game loop to update game elements and handle interactions.

-Paddle: The Paddle class contains methods for managing the paddle's size and state in the game. It includes functionality for changing the paddle size and resetting it to the default state.


-StartGame: The StartGame class contains methods for starting a new game and setting up the game scene.

They are all in the brickGame package.

• Modified Java Classes: List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.

-main:  refactored the whole main class to make the code cleaner and more readable by adding java doc comments.

-game engine: the issue of the games crashing and the ball freezing might be related t…o the multi-threading aspects of the code. This version uses 'Platform.runLater' to ensure that the UI updates in 'onInit()', 'onUpdate()', 'onPhysicsUpdate()', and 'onTime()' are executed on the JavaFX Application Thread.

• Unexpected Problems: Communicate any unexpected challenges or issues you encountered during the assignment. Describe how you addressed or attempted to resolve them.

I faced unexpected challenges when I was refactoring the code. At first I thought it was easy then when I started the refactoring I got a lot of errors which I couldn't fix. Moreover, the collision with the blocks was also one of the unexpected problems that took me days trying to fix but it was of no use.  

