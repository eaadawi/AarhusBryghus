package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class OpretNyUdlejningVinduet extends Stage {

    private Udlejning udlejning;
    private Prisliste prisliste;

    private DatePicker startDatoDatePicker = new DatePicker();
    private DatePicker slutDatoDatePicker = new DatePicker();
    private TextField foedseldsdagTextField = new TextField();
    private TextField telefonNrTextField = new TextField();
    private TextField navnTextField = new TextField();

    private TextField textFieldAdresse = new TextField();
    private CheckBox checkBoxLevering = new CheckBox();

    private TextField textSamletPrisEKS = new TextField();
    private TextField textSamletPris = new TextField();
    private TextField textFieldKrus = new TextField();

    private Button buttonTilfoejAnlaeg = new Button("Tilfoej anlaeg");
    private Button buttonTilfoejFustage = new Button("Tilfoej fustage");
    private Button buttonTilfoejKulsyre = new Button("Tilfoej kulsyre");
    private Button buttonTilfoejKrus = new Button("Tilfoej krus");
    private Button buttonFjernOL = new Button("Fjern linje");

    private ListView<Ordrelinje> ordrelinjeListView = new ListView<>();

    public OpretNyUdlejningVinduet(String title) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        //
        udlejning = Controller.opretUdlejning();
        this.setTitle(title);
        prisliste = Controller.hentPrislisteFraNavn("Butik");

        //
        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContentPane(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //
        Label startDatoLabel = new Label();
        startDatoLabel.setText("Start dato");
        pane.add(startDatoLabel, 0, 0);
        //
        Label slutDatoLabel = new Label();
        slutDatoLabel.setText("Slut dato");
        pane.add(slutDatoLabel, 0, 1);
        //
        Label navnLabel = new Label();
        navnLabel.setText("Navn");
        pane.add(navnLabel, 0, 2);
        //
        Label telefonNrLabel = new Label();
        telefonNrLabel.setText("Telefon nr");
        pane.add(telefonNrLabel, 0, 3);
        //

        //
        Label foedseldsdagLabel = new Label();
        foedseldsdagLabel.setText("Foedseldsdag");
        pane.add(foedseldsdagLabel, 0, 4);

        //
        Label labelLevering = new Label();
        labelLevering.setText("Levering");
        pane.add(labelLevering, 2, 0);
        //
        Label labelAdresseForLevering = new Label();
        labelAdresseForLevering.setText("Adresse for levering");
        pane.add(labelAdresseForLevering, 2, 1);

        Label labelProdukter = new Label("Produkter");
        pane.add(labelProdukter, 3, 2);
        //
        Label labelSamletPrisEKS = new Label();
        labelSamletPrisEKS.setText("Samlet pris (eks. pant):");
        labelSamletPrisEKS.setMaxWidth(Double.MAX_VALUE);
        labelSamletPrisEKS.setAlignment(Pos.BASELINE_RIGHT);
        pane.add(labelSamletPrisEKS, 3, 8);
        //
        Label labelSamletPris = new Label();
        labelSamletPris.setText("Samlet pris");
        labelSamletPris.setMaxWidth(Double.MAX_VALUE);
        labelSamletPris.setAlignment(Pos.BASELINE_RIGHT);
        pane.add(labelSamletPris, 3, 9);

        //------------------- DatePicker -----------------

        //
        pane.add(startDatoDatePicker, 1, 0);
        //
        pane.add(slutDatoDatePicker, 1, 1);

        //-------------------- TextField ------------------------------

        //
        pane.add(navnTextField, 1, 2);

        //
        pane.add(telefonNrTextField, 1, 3);

        //
        foedseldsdagTextField.setPromptText("yyyy-mm-dd");
        pane.add(foedseldsdagTextField, 1, 4);

        //
        pane.add(textFieldAdresse, 3, 1);

        //
        textSamletPrisEKS.setEditable(false);
        pane.add(textSamletPrisEKS, 4, 8);

        //
        textSamletPris.setEditable(false);
        pane.add(textSamletPris, 4, 9);

        //-------------- Button ------------
        //Button buttonTilfoejAnlaeg
        buttonTilfoejAnlaeg.setOnAction(event -> this.buttonTilfoejAnlaegKnapMetod());
        buttonTilfoejAnlaeg.setMaxWidth(Double.MAX_VALUE);
        pane.add(buttonTilfoejAnlaeg, 2, 3);
        //Button buttonTilfoejFustage
        buttonTilfoejFustage.setOnAction(event -> this.buttonTilfoejFustageKnapMetod());
        buttonTilfoejFustage.setMaxWidth(Double.MAX_VALUE);
        pane.add(buttonTilfoejFustage, 2, 4);
        //Button buttonTilfoejKulsyre
        buttonTilfoejKulsyre.setOnAction(event -> this.buttonTilfoejKulsyreKnapMetod());
        buttonTilfoejKulsyre.setMaxWidth(Double.MAX_VALUE);
        pane.add(buttonTilfoejKulsyre, 2, 5);
        //Button buttonTilfoejKrus
        buttonTilfoejKrus.setOnAction(event -> this.buttonTilfoejKrusKnapMetod());
        buttonTilfoejKrus.setMaxWidth(Double.MAX_VALUE);
        pane.add(buttonTilfoejKrus, 2, 6);
        //Button buttonFjernOL
        buttonFjernOL.setOnAction(event -> this.buttonFjernOLKnapMetod(ordrelinjeListView.getSelectionModel().getSelectedItem()));
        buttonFjernOL.setMaxWidth(Double.MAX_VALUE);
        //Button buttonTilfoejUdlejning
        Button buttonTilfoejUdlejning = new Button();
        buttonTilfoejUdlejning.setText("Tilfoej udlejning");
        buttonTilfoejUdlejning.setOnAction(event -> this.tilfoejUdlejningKnapMetod());
        buttonTilfoejUdlejning.setMaxWidth(Double.MAX_VALUE);
        pane.add(buttonTilfoejUdlejning, 3, 10);

        //----------------------- ListView ----------------------
        //
        ordrelinjeListView.setPrefSize(200, 200);
        pane.add(ordrelinjeListView, 3, 3,1,5);

        //-----------------------COL4_TF_OG_LABEL----------------------

        //
        ChangeListener<Boolean> listenerChB = (o, ol, n) -> this.chBListenerMetod();
        checkBoxLevering.selectedProperty().addListener(listenerChB);
        pane.add(checkBoxLevering, 3, 0);


        //------------------- VBox ----------------
        //
//        VBox vBox = new VBox();
//        vBox.setSpacing(10);
//        vBox.getChildren().add(buttonTilfoejAnlaeg);
//        vBox.getChildren().add(buttonTilfoejFustage);
//        vBox.getChildren().add(buttonTilfoejKulsyre);
//        vBox.getChildren().add(buttonTilfoejKrus);
//        pane.add(vBox, 2, 3);

    }


    private void tilfoejFustageMetodeKnap() {
        TilfoejFustageVinduet dialog = new TilfoejFustageVinduet("Tilfoej fustage vinduet", udlejning, prisliste);
        dialog.showAndWait();


    }


    private void buttonEkstraKulsyreMetodeKnap() {
        TilfoejEkstreKulsyreVinduet2 dialog = new TilfoejEkstreKulsyreVinduet2("Tilfoej ekstra kulsyre vinduet", udlejning, prisliste);
        dialog.showAndWait();


    }


    private void chBListenerMetod() {
        if (checkBoxLevering.isSelected()) {
            textFieldAdresse.setEditable(true);
        } else {
            textFieldAdresse.clear();
            textFieldAdresse.setEditable(false);
        }
    }

    private void udregnPris(TextField tf) {
        double pris = 0;
        for (Ordrelinje ol : udlejning.hentOrdrelinjer()) {
            pris += ol.samletPris();
        }

    }

    private void udregnPrisUdenPant(TextField tf) {
        double pris = 0;
        for (Ordrelinje ol : udlejning.hentOrdrelinjer()) {
            if (!ol.hentProdukt().hentNavn().equals("Pant"))
                pris += ol.samletPris();
        }
        tf.setText("" + pris);
    }

    private void tilfoejUdlejningKnapMetod() {
        udlejning.tilfoejStartDato(startDatoDatePicker.getValue());
        udlejning.tilfoejSlutDato(slutDatoDatePicker.getValue());
        udlejning.tilfoejKundeNavn(navnTextField.getText());
        udlejning.tilfoejKundeTlfNr(telefonNrTextField.getText());

        if (textFieldAdresse.getText().length() > 0) {
            udlejning.tilfoejLevering();
        }
        udlejning.tilfoejKundeFoedselsdag(LocalDate.parse(foedseldsdagTextField.getText()));

        this.hide();
    }

    private void buttonTilfoejAnlaegKnapMetod() {
        //TODO items
        AnlaegVinduet dialog = new AnlaegVinduet("Tilfoej anlaeg", udlejning);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonTilfoejFustageKnapMetod() {
        TilfoejFustageVinduet dialog = new TilfoejFustageVinduet("Tilfoej fustage", udlejning, prisliste);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonTilfoejKulsyreKnapMetod() {
        TilfoejEkstreKulsyreVinduet2 dialog = new TilfoejEkstreKulsyreVinduet2("Tilfoej kulsyre", udlejning, prisliste);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonTilfoejKrusKnapMetod() {
        TilfoejEkstraKrus dialog = new TilfoejEkstraKrus("Tilfoej ekstra krus", udlejning, prisliste);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonFjernOLKnapMetod(Ordrelinje ol) {
        udlejning.fjernOrdrelinje(ordrelinjeListView.getSelectionModel().getSelectedItem());
        opdaterListView();
    }

    private void opdaterListView() {
        ordrelinjeListView.getItems().setAll(udlejning.hentOrdrelinjer());
    }

}

class TilfoejEkstraKrus extends Stage {

    private Ordre ordre;
    private Prisliste prisliste;
    private TextField textFieldAntal = new TextField();

    public TilfoejEkstraKrus(String title, Ordre ordre, Prisliste pl) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        //

        this.setTitle(title);
        this.ordre = ordre;
        this.prisliste = pl;
        //

        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContentPane(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //---------------------LABELS---------------------------------

        //Label labelPrisliste
        Label labelAntal = new Label();
        labelAntal.setText("Antal");
        pane.add(labelAntal, 0, 0);
        //---------------------TEXTFIELDS---------------------------------
        //
        pane.add(textFieldAntal, 1, 0);
        //-------------------- Button ------------------------------
        //
        Button buttonTilfoej = new Button("Tilfoej");
        buttonTilfoej.setOnAction(event -> this.buttonTilfoejKnapMetod());
    }

    private void buttonTilfoejKnapMetod() {
        ordre.opretOrdrelinje(Integer.parseInt(textFieldAntal.getText()), Controller.hentProduktFraNavn("Anlæg", "Krus"), prisliste);
        this.hide();
    }
}
