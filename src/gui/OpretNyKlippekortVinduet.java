package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Klippekort;


public class OpretNyKlippekortVinduet extends Stage {

    private Klippekort klippekort = null;
    private TextField textFieldKundenavn = new TextField();

    OpretNyKlippekortVinduet(String title){
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        //

        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        //klippekort slettes hvis vinduet er blive lukket
        this.setOnCloseRequest(event -> Controller.fjernKlippekort(klippekort));
    }

    private void initContentPane(GridPane pane){
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //-------------------------LABEL----------------------------------------
        // labelKundenavn
        Label labelKundenavn = new Label("Kundenavn:");
        pane.add(labelKundenavn, 0, 0);

        //-------------------------TEXT_FIELD----------------------------------------
        // labelKundenavn
        pane.add(textFieldKundenavn, 1, 0);

        //-------------------------BUTTON----------------------------------------
        // buttonOpret
        Button buttonOpret = new Button("Opret");
        buttonOpret.setMaxWidth(Double.MAX_VALUE);
        buttonOpret.setOnAction(event -> this.opretKnapMetod());

        //-------------------------HBox----------------------------------------
        // hbox
        HBox hBox = new HBox(buttonOpret);
        pane.add(hBox, 1, 2);


    }

    private void opretKnapMetod(){
        Controller.opretKlippekort(textFieldKundenavn.getText());
        this.hide();
    }

}
