package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produkt;
import model.ProduktGruppe;
import storage.Storage;

public class ProduktGruppeTab extends GridPane {

    private final TextArea txaProduktIndhold = new TextArea();
    private final ListView<ProduktGruppe> produktGruppeListView = new ListView<>();
    private final ListView<Produkt> produktListView = new ListView<>();


    public ProduktGruppeTab(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(true);
        //----------------------------------------PRODUKT GRUPPE LIST VIEW

        //label produkt grp oprettes
        Label labelProduktGrupper = new Label();
        labelProduktGrupper.setText("Produkt grupper");

        this.add(labelProduktGrupper, 0, 0);

        this.add(produktGruppeListView, 0, 1, 2, 4);
        produktGruppeListView.setPrefWidth(200);
        produktGruppeListView.setPrefHeight(200);
        produktGruppeListView.getItems().setAll(Storage.hentInstans().hentProduktGrupper());

        //Knappe tilfoej til produktGrupper oprettes
        Button tiljoejProduktGrupperKnappe = new Button();
        tiljoejProduktGrupperKnappe.setText("Tilfoej");
        tiljoejProduktGrupperKnappe.setOnAction(event -> this.opretProduktGruppe());

        //Knappe fjern til produktGrupper oprettes
        Button fjernProduktGruppeKnappe = new Button();
        fjernProduktGruppeKnappe.setText("Fjern");

        //HBox til knapper oprettes
        HBox hbPGt = new HBox();
        hbPGt.getChildren().add(tiljoejProduktGrupperKnappe);
        //hbPG.getChildren().add(fjernProduktGruppeKnappe);

        //HBox til knapper oprettes
        HBox hbPGf = new HBox();
        hbPGf.getChildren().add(fjernProduktGruppeKnappe);


        //HBox produktGrupper tilfoejes til pane
        this.add(hbPGt, 0, 5);
        this.add(hbPGf, 1, 5);

        //--------------------------------------------PRODUKT LIST VIEW
        //oprettes label produkt
        Label labelProdukt = new Label();
        labelProdukt.setText("Produkter");
        //tilfoejes label og produktListView
        this.add(labelProdukt, 2, 0);
        this.add(produktListView, 2, 1,2,4);

        //plw stoerelse
        produktListView.setPrefWidth(200);
        produktListView.setPrefHeight(200);
        // mangles produktlistwiew indhold
        //produktListView.getItems().setAll(Storage.hentProduktGrupper());

        //Knapper til produkt oprettes
        Button tiljoejProduktKnappe = new Button();
        tiljoejProduktKnappe.setText("Tilfoej");

        Button fjernProduktKnappe = new Button();
        fjernProduktKnappe.setText("Fjern");

        //HBox til knappeT oprettes
        HBox hbPt = new HBox();
        hbPt.getChildren().add(tiljoejProduktKnappe);

        //HBox til knappeF oprettes
        HBox hbPf = new HBox();
        hbPf.getChildren().add(fjernProduktKnappe);

        //HBox produkt tilfoejes til pane
        this.add(hbPt, 2, 5);
        this.add(hbPf, 3, 5);

        //--------------------------------------------TEXT AREA
        //oprettes label til txaProduktIndhold
        Label labelTxaProduktIndhold = new Label();
        labelTxaProduktIndhold.setText("Produkt info");

        //labelTxaProduktIndhold tilfoejes til pane
        this.add(labelTxaProduktIndhold, 4, 0);

        //textArea produktIndhold tilfoejes til pane
        this.add(txaProduktIndhold, 4, 1);

        //txaProduktIndhold edition disables
        txaProduktIndhold.setEditable(false);

        //txaProduktIndhold size
        txaProduktIndhold.setMaxSize(150, 20);

        //tf oprettes til produkt antal tilfoejelse
        TextField textFieldProduktAntal = new TextField();

        //tf tilf til pane
        this.add(textFieldProduktAntal, 4, 2);

        // oprettes knap tilfoej antal
        Button produktAntalTilfoejButton = new Button();
        produktAntalTilfoejButton.setText("Tilfoej antal");

        // oprettes knap fjern antal
        Button produktAntalFjernButton = new Button();
        produktAntalFjernButton.setText("Fjern antal");

        //hbox til knapper antal oprettes
        VBox vbAntal = new VBox();
        vbAntal.getChildren().add(produktAntalTilfoejButton);
        vbAntal.getChildren().add(produktAntalFjernButton);

        //hbox tolfoejes til pane
        this.add(vbAntal, 4, 3);


    }

    private void opretProduktGruppe(){
        ProduktGruppeOpret dialog = new ProduktGruppeOpret();
    }

}
