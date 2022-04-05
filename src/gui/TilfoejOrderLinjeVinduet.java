package gui;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import storage.Storage;


public class TilfoejOrderLinjeVinduet extends Stage {

    private final Ordre ordre;
    private final Ordrelinje ordrelinje;

    private ComboBox<Prisliste> comboBoxPrisliste = new ComboBox();
    private ComboBox<ProduktGruppe> comboBoxProduktGruppe = new ComboBox();
    private ComboBox<Produkt> comboBoxProdukte = new ComboBox();

    private TextField textFieldAntal = new TextField();


    public TilfoejOrderLinjeVinduet(String title, Ordrelinje ordrelinje,Ordre ordre) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        //
        this.ordrelinje = ordrelinje;
        this.setTitle(title);
        this.ordre = ordre;

        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }

    private void initContentPane(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setMinHeight(200);
        pane.setMinWidth(400);
        pane.setGridLinesVisible(false);

        //---------------------LABELS---------------------------------

        //Label labelPrisliste
        Label labelPrisliste = new Label();
        labelPrisliste.setText("Prisliste");
        pane.add(labelPrisliste, 0, 0);

        //Label labelProduktGruppe
        Label labelProduktGruppe = new Label();
        labelProduktGruppe.setText("Produkt gruppe");
        pane.add(labelProduktGruppe, 0, 1);

        //Label labelProdukt
        Label labelProdukt = new Label();
        labelProdukt.setText("Produkt");
        pane.add(labelProdukt, 0, 2);

        //Label labelAntal
        Label labelAntal = new Label();
        labelAntal.setText("Antal");
        pane.add(labelAntal, 0, 3);

        //---------------------COBOBOXES--------------------

        //ComboBox comboBoxPrisliste
        comboBoxPrisliste.getItems().setAll(Controller.hentPrislister());
        pane.add(comboBoxPrisliste, 1, 0);

        ChangeListener<Prisliste> listener = (ov, o, n) -> this.valgPrisliste();
        comboBoxPrisliste.getSelectionModel().selectedItemProperty().addListener(listener);


        //ComboBox comboBoxProduktGruppe
        if(comboBoxPrisliste.getSelectionModel().getSelectedItem()!=null) {
            comboBoxProduktGruppe.getItems().setAll(Controller.hentProduktGrupper());
        }
        pane.add(comboBoxProduktGruppe, 1, 1);

        ChangeListener<ProduktGruppe> listenerPG = (ov, o, n) -> this.valgProduktGruppe(comboBoxProduktGruppe.getSelectionModel().getSelectedItem());
        comboBoxProduktGruppe.getSelectionModel().selectedItemProperty().addListener(listenerPG);


        //ComboBox comboBoxProdukte
        if(comboBoxPrisliste.getSelectionModel().getSelectedItem()!=null&&comboBoxProduktGruppe.getSelectionModel().getSelectedItem()!=null) {
            comboBoxProdukte.getItems().setAll(Controller.hentFaellesProdukter(comboBoxProduktGruppe.getSelectionModel().getSelectedItem(), comboBoxPrisliste.getSelectionModel().getSelectedItem()));
        }
        pane.add(comboBoxProdukte, 1, 2);

        //-------------------------TEXT_FIELD----------------

        pane.add(textFieldAntal, 1, 3);

        //--------------BUTTON-OPRET--------------------------
        Button buttonOpret = new Button();
        buttonOpret.setText("Opret");
        buttonOpret.setOnAction(event -> this.oprtOrderLinje());
        pane.add(buttonOpret, 2, 3);

    }

    private void oprtOrderLinje(){
        //int antal = Integer.parseInt(textFieldAntal.getText());
        ordre.opretOrdrelinje(Integer.parseInt(textFieldAntal.getText()),
                comboBoxProdukte.getSelectionModel().getSelectedItem(),
                comboBoxPrisliste.getSelectionModel().getSelectedItem());
        //
        //
        this.hide();
    }

    //


    private void valgPrisliste(){
        comboBoxProduktGruppe.getItems().setAll(Controller.hentProduktGrupper());
    }

    private void valgProduktGruppe(ProduktGruppe pg){
        comboBoxProdukte.getItems().setAll(pg.hentProdukter());
    }


}

