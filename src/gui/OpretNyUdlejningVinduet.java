package gui;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Ordre;
import model.Ordrelinje;
import model.Prisliste;
import model.Udlejning;

import java.time.LocalDate;

public class OpretNyUdlejningVinduet extends Stage {

    private final Udlejning udlejning;
    private final Prisliste prisliste;

    private final DatePicker startDatoDatePicker = new DatePicker();
    private final DatePicker slutDatoDatePicker = new DatePicker();
    private final TextField foedseldsdagTextField = new TextField();
    private final TextField telefonNrTextField = new TextField();
    private final TextField navnTextField = new TextField();

    private final TextField textFieldAdresse = new TextField();
    private final CheckBox checkBoxLevering = new CheckBox();

    private final TextField textSamletPrisEKS = new TextField();
    private final TextField textSamletPris = new TextField();

    private final Button buttonTilfoejAnlaeg = new Button("Tilføj anlæg");
    private final Button buttonTilfoejFustage = new Button("Tilføj fustage");
    private final Button buttonTilfoejKulsyre = new Button("Tilføj kulsyre");
    private final Button buttonTilfoejKrus = new Button("Tilføj krus");
    private final Button buttonFjernOL = new Button("Fjern linje");

    private final ListView<Ordrelinje> ordrelinjeListView = new ListView<>();

    public OpretNyUdlejningVinduet(String title) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        this.setOnCloseRequest(event -> this.vinduetLukkesMetod());
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
        foedseldsdagLabel.setText("Fødseldsdag");
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
        startDatoDatePicker.setValue(LocalDate.now());
        startDatoDatePicker.setEditable(false);
        pane.add(startDatoDatePicker, 1, 0);
        //
        slutDatoDatePicker.setValue(LocalDate.now());
        slutDatoDatePicker.setEditable(false);
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
        pane.add(checkBoxLevering, 3, 0);
        //
        textFieldAdresse.setEditable(false);
        pane.add(textFieldAdresse, 3, 1);
        //
        pane.add(buttonTilfoejAnlaeg, 2, 3);

        //
        pane.add(buttonTilfoejFustage, 2, 4);

        //
        pane.add(buttonTilfoejKulsyre, 2, 5);

        //
        pane.add(buttonTilfoejKrus, 2, 6);

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

        //Button buttonTilfoejFustage
        buttonTilfoejFustage.setOnAction(event -> this.buttonTilfoejFustageKnapMetod());
        buttonTilfoejFustage.setMaxWidth(Double.MAX_VALUE);

        //Button buttonTilfoejKulsyre
        buttonTilfoejKulsyre.setOnAction(event -> this.buttonTilfoejKulsyreKnapMetod());
        buttonTilfoejKulsyre.setMaxWidth(Double.MAX_VALUE);

        //Button buttonTilfoejKrus
        buttonTilfoejKrus.setOnAction(event -> this.buttonTilfoejKrusKnapMetod());
        buttonTilfoejKrus.setMaxWidth(Double.MAX_VALUE);

        //Button buttonFjernOL
        buttonFjernOL.setOnAction(event -> this.buttonFjernOLKnapMetod());
        buttonFjernOL.setMaxWidth(Double.MAX_VALUE);
        //Button buttonTilfoejUdlejning
        Button buttonTilfoejUdlejning = new Button();
        buttonTilfoejUdlejning.setText("Tilføj udlejning");
        buttonTilfoejUdlejning.setOnAction(event -> this.tilfoejUdlejningKnapMetod());
        buttonTilfoejUdlejning.setMaxWidth(Double.MAX_VALUE);
        pane.add(buttonTilfoejUdlejning, 3, 10);

        //----------------------- ListView ----------------------
        //
        ordrelinjeListView.setPrefSize(200, 200);
        pane.add(ordrelinjeListView, 3, 3, 1, 5);

        //-----------------------COL4_TF_OG_LABEL----------------------

        //
        ChangeListener<Boolean> listenerChB = (o, ol, n) -> this.chBListenerMetod();
        checkBoxLevering.selectedProperty().addListener(listenerChB);

    }

    private void chBListenerMetod() {
        if (checkBoxLevering.isSelected()) {
            textFieldAdresse.setEditable(true);
        } else {
            textFieldAdresse.clear();
            textFieldAdresse.setEditable(false);
        }
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

        AnlaegVinduet dialog = new AnlaegVinduet("Tilføj anlaeg", udlejning);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonTilfoejFustageKnapMetod() {
        TilfoejFustageVinduet dialog = new TilfoejFustageVinduet("Tilføj fustage", udlejning, prisliste);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonTilfoejKulsyreKnapMetod() {
        TilfoejEkstreKulsyreVinduet2 dialog = new TilfoejEkstreKulsyreVinduet2("Tilføj kulsyre", udlejning, prisliste);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonTilfoejKrusKnapMetod() {
        TilfoejEkstraKrus dialog = new TilfoejEkstraKrus("Tilføj ekstra krus", udlejning, prisliste);
        dialog.showAndWait();

        opdaterListView();
    }

    private void buttonFjernOLKnapMetod() {
        udlejning.fjernOrdrelinje(ordrelinjeListView.getSelectionModel().getSelectedItem());
        opdaterListView();
    }

    private void opdaterListView() {
        this.opdaterPriser();
        ordrelinjeListView.getItems().setAll(udlejning.hentOrdrelinjer());
    }

    private void opdaterPriser() {
        textSamletPris.setText("" + udlejning.totalPrisMedPant());
        textSamletPrisEKS.setText("" + udlejning.totalPris());
    }

    private void vinduetLukkesMetod() {
        Controller.fjernOrdre(udlejning);
    }

}

class TilfoejEkstraKrus extends Stage {

    private final Ordre ordre;
    private final Prisliste prisliste;
    private final TextField textFieldAntal = new TextField();

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
        Button buttonTilfoej = new Button("Tilføj");
        buttonTilfoej.setOnAction(event -> this.buttonTilfoejKnapMetod());
        pane.add(buttonTilfoej, 2, 1);
    }

    /**
     * Antal kruser skal ikke overstige antal kruser på lageren
     */
    private void buttonTilfoejKnapMetod() {

        ordre.opretOrdrelinje(Integer.parseInt(textFieldAntal.getText()), Controller.hentProduktFraNavn("Anlæg", "Krus"), prisliste);
        this.hide();
    }
}
