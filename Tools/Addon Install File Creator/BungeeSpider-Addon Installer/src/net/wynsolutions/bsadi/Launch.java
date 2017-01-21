package net.wynsolutions.bsadi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Copyright (C) 2017  Sw4p
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Sw4p
 *
 */
public class Launch extends Application{
	
	private Button createButton, addAddonBtn, clearListBtn;
	private TextArea addonList;
	private TextField addonTf;
	private Text createdText;
	private EventHandler<ActionEvent> createEvent, addAddonEvent, clearListEvent;
	private Stage stage;
	

	@Override public void start(Stage primaryStage) {

		this.createEvents();
		
		this.stage = primaryStage;
		
		primaryStage.setTitle("BungeeSpider - Addon install file creator");
		primaryStage.getIcons().add(new Image("http://i.imgur.com/Xi5BPos.png"));
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 500, 400);
		scene.getStylesheets().add
		 (Launch.class.getResource("stylesheet.css").toExternalForm());
		primaryStage.setScene(scene);
		
		
		// Addon List
		Text scenetitle = new Text("List of Addons:");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setId("title");
		this.addonList = new TextArea();
		this.addonList.setEditable(false);
		this.addonList.textProperty().addListener(new ChangeListener<Object>() {
		    @Override public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
		        addonList.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
		    }
		});
		
		grid.add(this.addonList, 0, 4, 4, 1);
		grid.add(scenetitle, 0, 0, 4, 3);
		
		
		// Add an addon to file
		HBox ahb = new HBox(20);
		this.addAddonBtn = new Button("Add");
		this.addAddonBtn.setOnAction(this.addAddonEvent);
		this.clearListBtn = new Button("Clear");
		this.clearListBtn.setOnAction(this.clearListEvent);
		this.addonTf = new TextField();
		this.addonTf.setScaleShape(true);
		this.addonTf.setScaleX(1.4);
		
		ahb.setAlignment(Pos.CENTER_LEFT);
		ahb.getChildren().add(this.addAddonBtn);
		ahb.getChildren().add(0, this.addonTf);
		ahb.getChildren().add(this.clearListBtn);
		
		grid.add(ahb, 0, 5, 4, 1);
		
		// Create File button
		HBox hb = new HBox(10);
		this.createButton = new Button("Create file");
		this.createButton.setOnAction(this.createEvent);
		hb.setAlignment(Pos.BOTTOM_RIGHT);
		hb.getChildren().add(this.createButton);
		
		grid.add(hb, 3, 8);
		
		// Create File text
		HBox fhb = new HBox(10);
		this.createdText = new Text("");
		this.createdText.setId("tag");
		fhb.setAlignment(Pos.BOTTOM_RIGHT);
		fhb.getChildren().add(this.createdText);
		grid.add(fhb, 3, 9);
		
		primaryStage.show();
	}
	
	private void createEvents(){
		
		// Create the BSUP file
		
		this.createEvent = new EventHandler<ActionEvent>() {
			 
		    @Override public void handle(ActionEvent e) {
		    	
		    	if(addonList.getText().equals("")){
		    		createdText.setText("List is empty!");
		    		startTextFade(createdText);
		    		return;
		    	}
		    	
                FileChooser directoryChooser = new FileChooser();
                directoryChooser.setInitialDirectory(
                        new File(System.getProperty("user.home"))
                    );                 
                directoryChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BSADI", "*.bsadi"));
                File selectedDirectory = 
                        directoryChooser.showSaveDialog(stage);
                 
                if(selectedDirectory == null){
                    createdText.setText("No file selected");
                    startTextFade(createdText);
                    return;
                }
		    	
		    	// Create File
		    	
		    	String list = addonList.getText();
		    	
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(selectedDirectory))) {

					bw.write(list);
				} catch (IOException e3) {
					e3.printStackTrace();
				}
		    	
		    	createdText.setText("File created.");
		    	startTextFade(createdText);

		    }
		};
		
		// Add and addon url to text area
		
		this.addAddonEvent = new EventHandler<ActionEvent>() {
			 
		    @Override public void handle(ActionEvent e) {
		    	
		    	String url = addonTf.getText();
		    	if(url.startsWith("http://") && url.endsWith(".jar")){
			    	addonList.appendText(addonTf.getText() + "\n");
			    	addonTf.setText("");
			    	createdText.setText("Added url");
			    	startTextFade(createdText);
		    	}else{
			    	createdText.setText("Addon path is not a URL!");
			    	startTextFade(createdText);
		    	}
		    }
		};
		
		// Clear List of Addons
		
		this.clearListEvent = new EventHandler<ActionEvent>(){

			@Override public void handle(ActionEvent arg0) {
				addonList.setText("");
				createdText.setText("Cleared addon list.");
				startTextFade(createdText);
			}
			
		};
		
	}
	
	private void startTextFade(Text t){
    	new Thread(new Runnable(){

			@Override public void run() {
				try {
					Thread.sleep(5000);
					t.setText("");
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				
			}
    		
    	}).start();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
