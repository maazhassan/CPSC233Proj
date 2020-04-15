# Chess Project
This is a project based on the classic game of Chess written in Java. It will follow the normal rules of the original Chess game.
If you do not know how to play chess, you can click [here](https://www.chess.com/learn-how-to-play-chess) to learn how to play.
# Downloading/Compiling
1. Download the project from git by clicking "Clone or Download" (it should be a .zip file)
2. In Eclipse, at the top, click on File -> Import -> General -> Existing Projects into Workspace
3. Check the "Select archive file" option, click on "Browse", and select the downloaded .zip file
4. Click on "Finish"
5. Afterwards, please ensure that you are using jdk-1.8 (you can change by right clicking the project, selecting "Properties", then "Java Build Path", then go to the "Libraries" tab, selecting "JRE System Library" and clicking on the "Edit" button.)
6. Once the project is imported and you have made sure you are using jdk-1.8, then you can run the project as described in the readme file, by running the JavaFXMainMenu class located in the src/Launcher package.
# Playing the Game Through the Command Line
To run the game:
1. Under the Launcher package, you will see the launcher class called CommandLineApp.
2. Run the program through this class

The game should now be running in the console.

You will first be prompted with a few questions in order to set up the options for the game.

![Options](https://imgur.com/a/rPd8Ojf)

You play the game by typing in the coordinate of the piece you want to move (a letter and a number), and then typing in the coordinate of the square that you want to move your selected piece to. There are numbers and letters along the top and side to help with finding the locations of the squares.

For example, in order to make the move:

![Move](https://imgur.com/a/gpzY2wn)

You would input:

![Input](https://imgur.com/a/hP1EYAw)

# Playing the Game With GUI (Through JavaFX)
To run the game:
1. Under the Launcher package, you will see the launcher class called JavaFXMainMenu.
2. Run the program through this class.

The main menu of the game should now pop up. If a previous game is saved, the load button can be clicked. Otherwise, clicking the start button opens a new window containing the settings to be used for the game. When the appropriate settings are selected, the play button will unlock.

You play this version of the game by clicking on the piece that you want to move, and then clicking on the square that you want to move it to. There are buttons at the top that can be used to undo/redo moves, save the game, or change the difficulty of the AI on the fly.

