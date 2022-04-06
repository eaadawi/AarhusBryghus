package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Klippekort;
import model.Ordre;


public class OpretNyKlippekortVinduet extends Stage {

    private Ordre ordre;
    private Klippekort klippekort = null;
    private TextField textFieldKundenavn = new TextField();
    private ListView<Klippekort> klippekortListViewValgte = null;
    private TextField tf = null;
    private double samletKKP;
    OpretNyKlippekortVinduet(String title, ListView<Klippekort> k, TextField t, double samletKKP, Ordre ordre){
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        //
        this.klippekortListViewValgte = k;
        this.setTitle(title);
        this.tf = t;
        this.samletKKP = samletKKP;
        this.ordre = ordre;

        //
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
        klippekort = Controller.opretKlippekort(textFieldKundenavn.getText());
        klippekortListViewValgte.getItems().add(klippekort);
        double pris = Double.parseDouble(tf.getText())+klippekort.hentPris();
        samletKKP+=klippekort.hentPris();
        ordre.TilfoejTilTotalPris(klippekort.hentPris());
        ordre.tilfoejKlippekort(klippekort);
        tf.setText(""+pris);
        this.hide();
    }

}
