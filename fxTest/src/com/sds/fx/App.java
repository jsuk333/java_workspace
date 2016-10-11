package com.sds.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application{

	
	public void start(Stage stage) throws Exception {
		VBox root=new VBox();
		Button b1=new Button("첫번째 버튼");
		Button b2=new Button("두번째 버튼");
		Button b3=new Button("세번째 버튼");
		root.getChildren().addAll(b1,b2,b3);
		
		Scene scene=new Scene(root, 300, 100);
		
		stage.setTitle("Stage-Scene-Node구조");
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
