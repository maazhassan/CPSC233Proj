package Launcher;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Separate Class for Main Menu: can be put together with rest of code later.

public class JavaFxMainMenu extends Application {

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
				
				// Buttons
				// Colour: Black or White
				
				Label colour = new Label("Choose colour to play as: ");
				settingBox.add(colour, 0, 3);

				Button blackPiece = new Button("Black");
				blackPiece.setMinWidth(150);
				settingBox.add(blackPiece, 1, 3);
				
				blackPiece.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						char c1color = 'b';
					}
					
				});
				
				Button whitePiece = new Button("White");
				whitePiece.setMinWidth(150);
				settingBox.add(whitePiece, 1, 4);
				
				whitePiece.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						char c1color = 'w';
					}
					
				});
				
				// Player: computer or human
				Label player = new Label("Choose player: ");
				settingBox.add(player, 0, 5);
				
				Button humanPlayer = new Button("Human Player");
				humanPlayer.setMinWidth(150);
				settingBox.add(humanPlayer, 1, 5);
				
				humanPlayer.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						char p2Type = 'h';
					}
					
				});
				
				Button computerPlayer = new Button("Computer Player");
				computerPlayer.setMinWidth(150);
				settingBox.add(computerPlayer, 1, 6);
				
				computerPlayer.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						char p2Type = 'c';
					}
					
				});
						
				// Difficulty: 1,2, or 3				
				Label difficulty = new Label("Choose difficulty level: ");
				settingBox.add(difficulty, 0, 7);
								
				Button easy = new Button("Easy: 1");
				easy.setMinWidth(150);
				settingBox.add(easy, 1, 7);
				
				easy.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						int aiDifficulty = 1;
					}
					
				});
														
				Button medium = new Button("Medium: 2");
				medium.setMinWidth(150);
				settingBox.add(medium, 1, 8);
				
				medium.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						int aiDifficulty = 2;
					}
					
				});
								
				Button hard = new Button("Hard: 3");
				hard.setMinWidth(150);
				settingBox.add(hard, 1, 9);
				
				hard.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						int aiDifficulty = 3;
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
						
						JavaFXApp run = new JavaFXApp();
						try {
							run.start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					
				});
				
				settingStage.setScene(scene2);
				settingStage.show();
			}
			
		});
		
		// Exit
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.exit(0);
			}
			
		});
		
		menuStage.setScene(scene);		
		menuStage.show();
	}
	
	 public static void main(String[] args) {
			launch(args);
	 }
}
