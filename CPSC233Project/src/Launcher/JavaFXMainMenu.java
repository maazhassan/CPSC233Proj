package Launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that creates the main menu, and creates a JavaFXApp instance.
 */
public class JavaFXMainMenu extends Application {
	
	private char p1Color = 'z';
	private char p2Type = 'z';
	private int aiDifficulty = 0;

	//Start method
	@Override
	public void start(Stage menuStage) {
		menuStage.setTitle("Main Menu");

		//Setup
		VBox vbox = new VBox();
		vbox.setBackground(Background.EMPTY);
		Scene scene = new Scene(vbox, 600, 400);
		scene.setFill(Color.BURLYWOOD);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(40);

		// Title
		Label title = new Label("CHESS");
		title.setFont(Font.font("Copperplate Gothic Light", FontWeight.NORMAL, 35));
		vbox.getChildren().add(title);

		// Buttons
		//Start
		Button start = new Button("Start");
		start.setMinWidth(100);
		start.setFont(Font.font("Copperplate Gothic Light", FontWeight.NORMAL, 25));

		//Load
		Button load = new Button("Load");
		load.setMinWidth(100);
		load.setFont(Font.font("Copperplate Gothic Light", FontWeight.NORMAL, 25));

		//Hidden label in case of loading when no game is saved
		Label noLoad = new Label("There is no saved game!");
		noLoad.setFont(Font.font("Copperplate Gothic Light", FontWeight.NORMAL, 10));
		noLoad.setTextFill(Color.RED);
		noLoad.setVisible(false);

		//End
		Button exit = new Button("Exit");
		exit.setMinWidth(100);
		exit.setFont(Font.font("Copperplate Gothic Light", FontWeight.NORMAL, 25));

		//Add nodes to vbox
		VBox.setMargin(noLoad, new Insets(-20, 0, 0, 0));
		VBox.setMargin(title, new Insets(20, 0, 0 ,0));
		vbox.getChildren().addAll(start, load, exit, noLoad);
		

		// Button events
		// Start
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				//Setup
				Stage settingStage = new Stage();
				settingStage.setTitle("Settings");
				GridPane settingBox = new GridPane();
				settingBox.setBackground(Background.EMPTY);
				settingBox.setAlignment(Pos.BASELINE_CENTER);
				settingBox.setVgap(10);
				settingBox.setHgap(10);

				Scene scene2 = new Scene(settingBox, 600, 500);
				scene2.setFill(Color.BURLYWOOD);

				Text settingTitle = new Text("Settings");
				settingTitle.setFont(Font.font("Copperplate Gothic Light", FontWeight.NORMAL, 20));

				settingBox.add(settingTitle, 0, 0);

				// Buttons and labels

				//Play button
				//Label
				Label startGame = new Label("Ready...");
				settingBox.add(startGame, 0, 10);
				//Creating button
				Button play = new Button("PLAY");
				//Button settings
				play.setMinWidth(150);
				play.setDisable(true);
				settingBox.add(play,1,10);
				// Play event handling (starts the game and opens the main screen)
				play.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						settingStage.close();
						menuStage.close();
						displayGame(false);
					}
				});

				//Creating toggle groups and settings buttons
				
				//Color selection
				//Label
				Label colour = new Label("Choose colour to play as: ");
				settingBox.add(colour, 0, 3);
				//Creating toggle group and buttons
				ToggleGroup colorGroup = new ToggleGroup();
				ToggleButton black = new ToggleButton("Black");
				ToggleButton white = new ToggleButton("White");
				//Button settings
				black.setToggleGroup(colorGroup);
				white.setToggleGroup(colorGroup);
				black.setMinWidth(150);
				white.setMinWidth(150);
				settingBox.add(black, 1, 3);
				settingBox.add(white, 1, 4);
				//Event listener for toggle group
				colorGroup.selectedToggleProperty().addListener(
					(observable, oldToggle, newToggle) -> {
						if (newToggle == black) {
							p1Color = 'b';
							updatePlayButton(play);
						}
						else if (newToggle == white) {
							p1Color = 'w';
							updatePlayButton(play);
						}
						else {
							p1Color = 'z';
							updatePlayButton(play);
						}
					}
				);

				//Difficulty
				//Label
				Label difficulty = new Label("Choose difficulty level: ");
				settingBox.add(difficulty, 0, 7);
				//Creating toggle group and buttons
				ToggleGroup difficultyGroup = new ToggleGroup();
				ToggleButton easy = new ToggleButton("Easy");
				ToggleButton medium = new ToggleButton("Medium");
				ToggleButton hard = new ToggleButton("Hard");
				//Have buttons intitially disabled
				easy.setDisable(true);
				medium.setDisable(true);
				hard.setDisable(true);
				//Button settings
				easy.setToggleGroup(difficultyGroup);
				medium.setToggleGroup(difficultyGroup);
				hard.setToggleGroup(difficultyGroup);
				easy.setMinWidth(150);
				medium.setMinWidth(150);
				hard.setMinWidth(150);
				settingBox.add(easy, 1, 7);
				settingBox.add(medium, 1, 8);
				settingBox.add(hard, 1, 9);
				//Event listener for toggle group
				difficultyGroup.selectedToggleProperty().addListener(
					(observable, oldToggle, newToggle) -> {
						if (newToggle == easy) {
							aiDifficulty = 1;
							updatePlayButton(play);
						}
						else if (newToggle == medium) {
							aiDifficulty = 2;
							updatePlayButton(play);
						}
						else if (newToggle == hard) {
							aiDifficulty = 3;
							updatePlayButton(play);
						}
						else {
							aiDifficulty = 0;
							updatePlayButton(play);
						}
					}
				);

				//Opponent: computer or human
				//Label
				Label player = new Label("Choose opponent type: ");
				settingBox.add(player, 0, 5);
				//Creating toggle group and buttons
				ToggleGroup opponentGroup = new ToggleGroup();
				ToggleButton human = new ToggleButton("Human");
				ToggleButton computer = new ToggleButton("Computer");
				//Button settings
				human.setToggleGroup(opponentGroup);
				computer.setToggleGroup(opponentGroup);
				human.setMinWidth(150);
				computer.setMinWidth(150);
				settingBox.add(human, 1, 5);
				settingBox.add(computer, 1, 6);
				//Event listener for toggle group
				opponentGroup.selectedToggleProperty().addListener(
					(observable, oldToggle, newToggle) -> {
						if (newToggle == human) {
							p2Type = 'h';
							easy.setDisable(true);
							medium.setDisable(true);
							hard.setDisable(true);
							updatePlayButton(play);
						}
						else if (newToggle == computer) {
							p2Type = 'c';
							easy.setDisable(false);
							medium.setDisable(false);
							hard.setDisable(false);
							updatePlayButton(play);
						}
						else {
							p2Type = 'z';
							easy.setDisable(true);
							medium.setDisable(true);
							hard.setDisable(true);
							updatePlayButton(play);
						}
					}
				);

				settingStage.setScene(scene2);
				settingStage.show();

			}

		});

		// Load
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File saveFile;
				try {
					saveFile = new File("CPSC233Project/save.dat");
				}
				catch (Exception e) {
					try {
						saveFile = new File("save.dat");
					}
					catch (Exception a) {
						throw new RuntimeException("File not found.");
					}
				}
				if (saveFile.length() == 0) {
					noLoad.setVisible(true);
				}
				else {
					menuStage.close();
					displayGame(true);
				}
			}
		});

		// Exit
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}

		});

		menuStage.setScene(scene);
		menuStage.show();
	}

	/**
	 * Checks and updates the status of the play button on the main menu.
	 * @param play The play button.
	 */

	public void updatePlayButton(Button play) {
		if (p1Color != 'z' && p2Type == 'h') play.setDisable(false);
		else if (p1Color != 'z' && p2Type == 'c') {
			if (aiDifficulty != 0) play.setDisable(false);
			else play.setDisable(true);
		}
		else play.setDisable(true);
	}

	/**
	 * Creates an instance of JavaFXApp, where the game is run from.
	 */

	public void displayGame(boolean load) {
		if (!load) new JavaFXApp(p1Color, p2Type, aiDifficulty, false).run();
		else {
			Scanner fileScanner;
			try {
				fileScanner = new Scanner(new File("CPSC233Project/save.dat"));
			}
			catch (FileNotFoundException e) {
				try {
					fileScanner = new Scanner(new File("save.dat"));
				}
				catch (FileNotFoundException a) {
					throw new RuntimeException("File not found.");
				}
			}
			this.p1Color = fileScanner.nextLine().charAt(0);
			this.p2Type = fileScanner.nextLine().charAt(0);
			this.aiDifficulty = Integer.parseInt(fileScanner.nextLine());
			new JavaFXApp(p1Color, p2Type, aiDifficulty, true).run();
		}
	}

	/**
	 * 
	 * @return The color of player 1, chosen by the user.
	 */

	public char getP1Color() {
		return this.p1Color;
	}

	/**
	 * 
	 * @return The type of player 2, chosen by the user.
	 */

	public char getP2Type() {
		return this.p2Type;
	}

	/**
	 * 
	 * @return The difficult of the AI, chosen by the user.
	 */

	public int getAIDifficulty() {
		return this.aiDifficulty;
	}

	/**
	 * Main method, entry point of the application.
	 * @param args
	 */

	public static void main(String[] args) {
		launch(args);
	}
}
