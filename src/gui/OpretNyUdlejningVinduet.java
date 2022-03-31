package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Ordrelinje;
import model.Prisliste;
import model.Produkt;
import model.Udlejning;


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

    private ListView<Ordrelinje> listViewFustager = new ListView<>();
    private ListView<Ordrelinje> listViewKulsyre = new ListView<>();
    private TextField textFieldAdresse = new TextField();
    private CheckBox checkBoxLevering = new CheckBox();

    private TextField textSamletPrisEKS = new TextField();
    private TextField textSamletPris = new TextField();
    private TextField textFieldKrus = new TextField();
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

        //-------------------COL_2_TEXTFIELD-----------------

        //

        pane.add(startDatoDatePicker, 1, 0);
        //

        pane.add(slutDatoDatePicker, 1, 1);
        //

        pane.add(navnTextField, 1, 2);
        //

        pane.add(telefonNrTextField, 1, 3);
        //

        //
        foedseldsdagTextField.setPromptText("yyyy-mm-dd");
        pane.add(foedseldsdagTextField, 1, 4);

        //-----------------------COL3_LABEL----------------------

        //
        Label labelLevering = new Label();
        labelLevering.setText("Levering");
        pane.add(labelLevering, 2, 0);
        //
        Label labelAdresseForLevering = new Label();
        labelAdresseForLevering.setText("Adresse for levering");
        pane.add(labelAdresseForLevering, 2, 1);
        //
        Label labelAntalHaner = new Label();
        labelAntalHaner.setText("Antal haner");
        pane.add(labelAntalHaner, 2, 2);
        //
        Button buttonTilfoejFustage = new Button();
        buttonTilfoejFustage.setText("Tilfoej fustage");
        buttonTilfoejFustage.setOnAction(event -> this.tilfoejFustageMetodeKnap());
        //
        Label labelFustager = new Label();
        labelFustager.setText("Fustager:");
        //
        HBox hBoxFustager = new HBox(labelFustager,buttonTilfoejFustage);
        hBoxFustager.setSpacing(10);
        pane.add(hBoxFustager, 2, 3);

        //ListView<?> listViewFustager
        int x = 200;
        listViewFustager.setPrefSize(x, x);
        //mangler items i listView
        listViewFustager.getItems().setAll(hentFustageOLListView());
        pane.add(listViewFustager, 2, 4);

        //
        Label labelKrus = new Label();
        labelKrus.setText("Krus(stk):");
        //
        textFieldKrus.setText("0");
        textFieldKrus.setEditable(true);

//        textFieldKrus.textProperty().addListener((observable, oldValue, newValue) -> this.textFieldKrusListenerMetod());

        //
        HBox hBoxK = new HBox();
        hBoxK.getChildren().addAll(labelKrus, textFieldKrus);
        pane.add(hBoxK, 2, 5);

        //-----------------------COL4_TF_OG_LABEL----------------------

        //
        ChangeListener<Boolean> listenerChB = ( o, ol, n ) -> this.chBListenerMetod();
        checkBoxLevering.selectedProperty().addListener(listenerChB);
        pane.add(checkBoxLevering, 3, 0);
        //
        pane.add(textFieldAdresse, 3, 1);
        //
        TextField textFieldAntalHaner = new TextField();
        pane.add(textFieldAntalHaner, 3, 2);
        //
        Button buttonEkstraKulsyre = new Button();
        buttonEkstraKulsyre.setText("Ekstrea kulsyre");
        buttonEkstraKulsyre.setOnAction(event -> this.buttonEkstraKulsyreMetodeKnap());
        //
        Label labelKulsyre = new Label();
        labelKulsyre.setText("Kulsyre");
        //
        HBox hBoxKulsyre = new HBox(labelKulsyre,buttonEkstraKulsyre);
        hBoxKulsyre.setSpacing(10);
        pane.add(hBoxKulsyre, 3, 3);

        //ListView<Ordrelinje> listViewKulsyre
        listViewKulsyre.setPrefSize(x, x);
        listViewKulsyre.getItems().setAll(this.hentEkstraKulsyreListView());
        pane.add(listViewKulsyre, 3, 4);

        //
        Label labelSamletPrisEKS = new Label();
        labelSamletPrisEKS.setText("Samlet pris (eks. pant):");
        pane.add(labelSamletPrisEKS, 3, 5);
        //
        Label labelSamletPris = new Label();
        labelSamletPris.setText("Samlet pris");
        pane.add(labelSamletPris, 3, 6);
        //
        Button buttonTilfoejUdlejning = new Button();
        buttonTilfoejUdlejning.setText("Tilfoej udlejning");
        buttonTilfoejUdlejning.setOnAction(event -> this.tilfoejUdlejningKnapMetod());
        pane.add(buttonTilfoejUdlejning, 3, 7);

        //-------------------COL5_textfields----------------

        //
        pane.add(textSamletPrisEKS, 4, 5);
        //
        pane.add(textSamletPris, 4, 6);
    }


    private void tilfoejFustageMetodeKnap() {
        TilfoejFustageVinduet dialog = new TilfoejFustageVinduet("Tilfoej fustage vinduet", udlejning, prisliste);
        dialog.showAndWait();

        this.hentFustageOLListView();
    }

    private List<Ordrelinje> hentFustageOLListView() {

        List<Ordrelinje> copy = new ArrayList<Ordrelinje>(udlejning.hentOrdrelinjer());
        copy.removeIf(o -> !o.hentProdukt().hentProduktGruppe().hentNavn().equals("fustage"));

        listViewFustager.getItems().setAll(copy);

        udregnPris(textSamletPris);
        udregnPrisUdenPant(textSamletPrisEKS);

        return copy;
    }


    private void buttonEkstraKulsyreMetodeKnap() {
        TilfoejEkstreKulsyreVinduet2 dialog = new TilfoejEkstreKulsyreVinduet2("Tilfoej ekstra kulsyre vinduet", udlejning, prisliste);
        dialog.showAndWait();

        this.hentEkstraKulsyreListView();
    }

    private List<Ordrelinje> hentEkstraKulsyreListView() {

        List<Ordrelinje> copy = new ArrayList<Ordrelinje>(udlejning.hentOrdrelinjer());
        copy.removeIf(o -> !o.hentProdukt().hentProduktGruppe().hentNavn().equals("Kulsyre"));

        //List

        listViewKulsyre.getItems().setAll(copy);

        udregnPris(textSamletPris);
        udregnPrisUdenPant(textSamletPrisEKS);
        return copy;
    }


    private void chBListenerMetod(){
        if(checkBoxLevering.isSelected()){
            textFieldAdresse.setEditable(true);
        }else {
            textFieldAdresse.clear();
            textFieldAdresse.setEditable(false);
        }
    }

    private void udregnPris(TextField tf){
        double pris = 0;
        for(Ordrelinje ol : udlejning.hentOrdrelinjer()){
            pris+=ol.samletPris();
        }

        double krusPris = 0;
//        if(antal>0) {
//            Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
//            Produkt krus = Controller.hentProduktFraNavn("Anlæg", "Krus");
//            krusPris = prisliste.hentPris(krus)*antal;
//        }

        tf.setText(""+pris+krusPris);
    }

    private void udregnPrisUdenPant(TextField tf){
        double pris = 0;
        for(Ordrelinje ol : udlejning.hentOrdrelinjer()){
            if(!ol.hentProdukt().hentNavn().equals("Pant"))
            pris+=ol.samletPris();
        }
        tf.setText(""+pris);
    }

    private void tilfoejUdlejningKnapMetod(){
        udlejning.tilfoejStartDato(startDatoDatePicker.getValue());
        udlejning.tilfoejSlutDato(slutDatoDatePicker.getValue());
        udlejning.tilfoejKundeNavn(navnTextField.getText());
        udlejning.tilfoejKundeTlfNr(telefonNrTextField.getText());

        if(textFieldAdresse.getText().length()>0) {
            udlejning.tilfoejLevering();
        }
        udlejning.tilfoejKundeFoedselsdag(LocalDate.parse(foedseldsdagTextField.getText()));

        this.hide();
    }


//
//    private void textFieldKrusListenerMetod() {
//        if(intUdAfTf(textFieldKrus)>0){
//            textSamletPris.setText(""+intUdAfTf(textSamletPris)+
//    intUdAfTf(textFieldKrus)*Controller.hentPrislisteFraNavn("Butik").hentProdukter().);
//        }
//    }
//
//    private int intUdAfTf(TextField tf){
//        return Integer.parseInt(tf.getText());
//    }
}
