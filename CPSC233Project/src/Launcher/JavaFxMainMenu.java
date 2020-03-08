package Launcher;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

// Separate Class for Main Menu: can be put together with rest of code later.


public class JavaFXMainMenu extends Application {
	
	private char p1Color;
	private char p2Type;
	private int aiDifficulty;

	public JavaFXMainMenu() {
		this.start(new Stage());
	}

	@Override
	public void start(Stage menuStage) {
		menuStage.setTitle("Main Menu");
				
		VBox vbox = new VBox();
		vbox.setBackground(Background.EMPTY);
		Scene scene = new Scene(vbox,600,400);
		scene.setFill(Color.BURLYWOOD);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(40);
		
		
		// Title
		Label title = new Label("CHESS");
		title.setFont(Font.font("Copperplate Gothic Light",FontWeight.NORMAL, 35));
		vbox.getChildren().add(title);
		
		// Buttons
		Button start = new Button("Start");
		start.setMinWidth(100);
		start.setFont(Font.font("Copperplate Gothic Light",FontWeight.NORMAL, 25));
		//start.setBackground(new Background(new BackgroundFill(Color.CORNSILK, null, null)));
	
		
		Button exit = new Button("Exit");
		exit.setMinWidth(100);
		exit.setFont(Font.font("Copperplate Gothic Light",FontWeight.NORMAL, 25));
		
		vbox.getChildren().addAll(start,exit);
		
		// Button events
		// Start
		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//Setup
				Stage settingStage = new Stage();
				settingStage.setTitle("Settings");
				GridPane settingBox = new GridPane();
				settingBox.setBackground(Background.EMPTY);
				settingBox.setAlignment(Pos.BASELINE_CENTER);
				settingBox.setVgap(10);
				settingBox.setHgap(10);
				Scene scene2 = new Scene(settingBox,600,500);
				scene2.setFill(Color.BURLYWOOD);
				
				
				Text settingTitle = new Text("Settings");
				settingTitle.setFont(Font.font("Copperplate Gothic Light",FontWeight.NORMAL, 20));
				settingBox.add(settingTitle,0,0);
				
				// Buttons and labels

				//Creating toggle groups and buttons
				//Colors
				ToggleGroup colorGroup = new ToggleGroup();
				ToggleButton black = new ToggleButton("Black");
				ToggleButton white = new ToggleButton("White");
				//Opponent
				ToggleGroup opponentGroup = new ToggleGroup();
				ToggleButton human = new ToggleButton("Human");
				ToggleButton computer = new ToggleButton("Computer");
				//Difficulty
				ToggleGroup difficultyGroup = new ToggleGroup();
				ToggleButton easy = new ToggleButton("Easy");
				ToggleButton medium = new ToggleButton("Medium");
				ToggleButton hard = new ToggleButton("Hard");

				//Color selection
				Label colour = new Label("Choose colour to play as: ");
				settingBox.add(colour, 0, 3);

				//Button settings
				black.setToggleGroup(colorGroup);
				white.setToggleGroup(colorGroup);
				black.setMinWidth(150);
				white.setMinWidth(150);
				settingBox.add(black, 1, 3);
				settingBox.add(white, 1, 4);

				//Black event handling
				black.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						p1Color = 'b';
					}
				});

				//White event handling
				white.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						p1Color = 'w';
					}
				});
				
				//Opponent: computer or human
				Label player = new Label("Choose opponent type: ");
				settingBox.add(player, 0, 5);
				
				//Button settings
				human.setToggleGroup(opponentGroup);
				computer.setToggleGroup(opponentGroup);
				human.setMinWidth(150);
				computer.setMinWidth(150);
				settingBox.add(human, 1, 5);
				settingBox.add(computer, 1, 6);

				//Human event handling
				human.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						p2Type = 'h';
						//Disable difficulty buttons if player has selected human opponent
						easy.setDisable(true);
						medium.setDisable(true);
						hard.setDisable(true);
						aiDifficulty = 0;
					}
					
				});

				//Computer event handling
				computer.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						p2Type = 'c';
						//Enable difficulty buttons if player has selected computer opponent
						easy.setDisable(false);
						medium.setDisable(false);
						hard.setDisable(false);
					}
					
				});
						
				// Difficulty: 1,2, or 3				
				Label difficulty = new Label("Choose difficulty level: ");
				settingBox.add(difficulty, 0, 7);
				
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

				//Easy event handling
				easy.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						aiDifficulty = 1;
					}
					
				});

				//Medium event handling
				medium.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						aiDifficulty = 2;
					}
					
				});

				//Hard event handling
				hard.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						aiDifficulty = 3;
					}
					
				});
									
				// Play (starts the game and opens the main screen)
				Label startGame = new Label("Ready...");
				settingBox.add(startGame, 0, 10);
				
				Button play = new Button("PLAY");
				play.setMinWidth(150);
				settingBox.add(play,1,10);
				
				play.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						settingStage.close();
						menuStage.close();
					}
					
				});
				
				settingStage.setScene(scene2);
				settingStage.showAndWait();
				
			}
			
		});
		
		// Exit
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
			
		});
		
		menuStage.setScene(scene);		
		menuStage.showAndWait();
	}

	public char getP1Color() {
		return this.p1Color;
	}

	public char getP2Type() {
		return this.p2Type;
	}

	public int getAIDifficulty() {
		return this.aiDifficulty;
	}
}
