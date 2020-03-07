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
				Scene scene2 = new Scene(settingBox,400,300);
				scene2.setFill(Color.BURLYWOOD);
				
				
				Text settingTitle = new Text("Settings");
				settingTitle.setFont(Font.font("Copperplate Gothic Light",FontWeight.NORMAL, 20));
				settingBox.add(settingTitle,0,0);
				
				// Buttons
				// Player: vs Ai or vs Human
				
				
				// Difficulty: 1,2, or 3
				
				
				
				// Colour: Black or White

				
				// Play (starts the game and opens the main screen)
				Button play = new Button("PLAY");
				play.setMinWidth(100);
				settingBox.add(play,0,4);
				
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
