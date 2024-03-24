package com.example.demo.factory;

import com.example.demo.handler.InfoPopupOKhandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupFactory {
    public Stage createWaintingPopup(String text){
        Stage stage =  new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);

        Label label = new Label(text);
        ProgressBar progressBar = new ProgressBar();
        pane.getChildren().add(label);
        pane.getChildren().add(progressBar);

        stage.setScene(new Scene(pane,200,100));
        stage.initModality(Modality.APPLICATION_MODAL);
        //style do popup
        pane.setStyle(waitingStyle());
        label.setStyle(labelStyle());
        return stage;
    }

    private String labelStyle() {
        return "-fx-text-fill: #003366;";
    }

    private String waitingStyle() {
        return "-fx-background-color: #c7c7c7; -fx-border-color: #003366;";
    }
    private String okButtonStyle(){
        return "-fx-text-fill: #003366 ;\n" +
                "        -fx-background-color:#c7c7c7 ;\n" +
                "        -fx-border-color:#003366 ;\n" +
                "        -fx-background-radius: 5px;";
    }
    private String okButtonHoverStyle(){
        return "-fx-text-fill: #003366 ;\n" +
                "        -fx-background-color:#e1e1e1 ;\n" +
                "        -fx-border-color:#003366 ;\n" +
                "        -fx-background-radius: 5px;";
    }


    public Stage createInfoPopup(String text, InfoPopupOKhandler handler) {
        Stage stage =  new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);

        Label label = new Label(text);
        Button okButton=new Button("OK");
        okButton.setStyle(okButtonStyle());
        okButton.setOnMouseEntered(x->{
            okButton.setStyle(okButtonHoverStyle());
        });
        okButton.setOnMouseExited(x->{
            okButton.setStyle(okButtonStyle());
        });
        okButton.setOnAction(x->{
            stage.close();
            handler.handle();
        });
        pane.getChildren().add(label);
        pane.getChildren().add(okButton);

        stage.setScene(new Scene(pane,200,100));
        stage.initModality(Modality.APPLICATION_MODAL);
        //style do popup
        pane.setStyle(waitingStyle());
        label.setStyle(labelStyle());
        return stage;
    }
}
