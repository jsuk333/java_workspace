package com.sds.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainClass extends Application{


	
	public void start(Stage stage) throws Exception {
		stage.setTitle("������ ����!");
		
		Button btn=new Button();
		btn.setText("�����ÿ�");
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Alert alert=new Alert(AlertType.INFORMATION);
				alert.setTitle("�˸�");
				alert.setContentText("�ȳ�");
				alert.setHeaderText(null);
				alert.show();
				
			}
		});
		
		StackPane sp=new StackPane();
		sp.getChildren().addAll(btn);
		
		stage.setScene(new  Scene(sp, 500, 300));
		stage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
