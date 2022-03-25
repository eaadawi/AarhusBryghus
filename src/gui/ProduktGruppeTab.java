package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProduktGruppeTab extends GridPane {

    private final TextArea txaProduktIndhold = new TextArea();
    private final ListView<ProduktGruppe> produktGruppeListView = new ListView<>();
    private final ListView<Produkt> produktListView = new ListView<>();


    public ProduktGruppeTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(true);
        //----------------------------------------PRODUKT GRUPPE LIST VIEW

        //label produkt grp oprettes
        Label labelProduktGrupper = new Label();
        labelProduktGrupper.setText("Produkt grupper");

        this.add(labelProduktGrupper, 0, 0);


        //tilfoejes produktGruppeListView til pane
        this.add(produktGruppeListView, 0, 1, 2, 4);
        produktGruppeListView.setPrefWidth(200);
        produktGruppeListView.setPrefHeight(200);

        //valger den oeverste element hvis listen er ikke tom
        if (!produktGruppeListView.getItems().isEmpty()) {
            produktGruppeListView.getSelectionModel().select(0);
        }

        //viser produktGrupper i ListView
        hentOgVisProduktGrupper(produktGruppeListView);

        //tilfoejes listener til produktGruppeListView
        ChangeListener<ProduktGruppe> listener = (ov, o, n) -> this.selectedProduktGruppeChanged();
        produktGruppeListView.getSelectionModel().selectedItemProperty().addListener(listener);

        //Knappe tilfoej til produktGrupper oprettes
        Button tiljoejProduktGrupperKnappe = new Button();
        tiljoejProduktGrupperKnappe.setText("Tilfoej");
        tiljoejProduktGrupperKnappe.setOnAction(event -> this.opretProduktGruppe());

        //Knappe fjern til produktGrupper oprettes
        Button fjernProduktGruppeKnappe = new Button();
        fjernProduktGruppeKnappe.setText("Fjern");

        //metode til fjernProduktGruppeKnappe laves
        fjernProduktGruppeKnappe.setOnAction(event -> this.sletPGbekraeftelse());

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

        //-------------------------------------------- PRODUKT LIST VIEW
        //oprettes label produkt
        Label labelProdukt = new Label();
        labelProdukt.setText("Produkter");
        //tilfoejes label og produktListView
        this.add(labelProdukt, 2, 0);
        this.add(produktListView, 2, 1, 2, 4);

        //plw stoerelse
        produktListView.setPrefWidth(200);
        produktListView.setPrefHeight(200);

        //henter og viser produkter af selected produktGruppe
        hentOgVisProdukter(produktGruppeListView);

        //Knapper til produkt oprettes
        Button tiljoejProduktKnappe = new Button();
        tiljoejProduktKnappe.setText("Tilfoej");

        //metoden til tiljoejProduktKnappe oprettes
        //med valgteProduktGruppe
        tiljoejProduktKnappe.setOnAction(event -> this.opretProdukt(produktGruppeListView.getSelectionModel().getSelectedItem()));

        Button fjernProduktKnappe = new Button();
        fjernProduktKnappe.setText("Fjern");

        //methode til fjernProduktKnappe laves her
        fjernProduktKnappe.setOnAction(event -> this.fjernProdukt(
                produktGruppeListView.getSelectionModel().getSelectedItem(),produktListView.getSelectionModel().getSelectedItem()));

        //HBox til knappeT oprettes
        HBox hbPt = new HBox();
        hbPt.getChildren().add(tiljoejProduktKnappe);

        //HBox til knappeF oprettes
        HBox hbPf = new HBox();
        hbPf.getChildren().add(fjernProduktKnappe);

        //HBox produkt tilfoejes til pane
        this.add(hbPt, 2, 5);
        this.add(hbPf, 3, 5);

        //----------------------------------------------- TEXT AREA
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

        //knappe produktAntalTilfoejButton width ind i VBox
        produktAntalTilfoejButton.setMaxWidth(Double.MAX_VALUE);

        // oprettes knap fjern antal
        Button produktAntalFjernButton = new Button();
        produktAntalFjernButton.setText("Fjern antal");

        //botton produktAntalFjernButton max width ind i VBox
        produktAntalFjernButton.setMaxWidth(Double.MAX_VALUE);

        //VBox til knapper antal oprettes
        VBox vbAntal = new VBox();

        //knapperne tilfoejes til vbox
        vbAntal.getChildren().addAll(produktAntalTilfoejButton, produktAntalFjernButton);

        //hbox tolfoejes til pane
        this.add(vbAntal, 4, 3);


    }


    private void opretProduktGruppe() {
        ProduktGruppeOpret dialog = new ProduktGruppeOpret("Opret Produkt gruppe", null);
        dialog.showAndWait();

        // Wait for the modal dialog to close

        //hentes produkgGrupper fra Controller
        produktGruppeListView.getItems().setAll(Controller.hentProduktGrupper());
        int index = produktGruppeListView.getItems().size() - 1;
        produktListView.getSelectionModel().select(index);

    }

    /**
     * Metode viser de produkter som er tilhoerer til valgte produktGrupper
     */
    private void selectedProduktGruppeChanged() {

        opdatereProdukterListView();
    }


    private void opdatereProdukterListView() {
        //pg er den valgte element fra listView
        ProduktGruppe pg = produktGruppeListView.getSelectionModel().getSelectedItem();

        //opdateres produkt listView
        if (pg != null) {

            //oprettes list for at samle produkter
            List<Produkt> produktList = new ArrayList<>();

            for (Produkt p : pg.hentProdukter()) {
                produktList.add(p);
            }

            // produktListView opdateres med de produkter som passer til den valgte produktGruppe
            produktListView.getItems().setAll(produktList);
        }
    }

    /**
     * Metod srpoerger om bekraeftelse at slette produktGruppe
     */

    private void sletPGbekraeftelse() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //navn på alert vinduet
        alert.setTitle("Baekreftelse vinduet");

        //anden linje text
        alert.setHeaderText("Oensker at slette produkt gruppe?");
        //tredje linje text
        alert.setContentText("Er du sikkert på at produkt gruppe skal slettes?");

        //oprettes reaktion på knappe truk
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            //den valgte produkt fra listView produktGruppe fjernes
            Controller.fjernProduktGruppe(produktGruppeListView.getSelectionModel().getSelectedItem());

            //opdatere produktGruppeListViewe efter produktGtuppe var slettet
            hentOgVisProduktGrupper(produktGruppeListView);

        } else {
            //vinduet lukkes
            alert.close();
        }
    }

    /**
     * Methode der opdatere og viser produktGrupper i produktGruppeListView
     */
    private void hentOgVisProduktGrupper(ListView<ProduktGruppe> pg) {

        //henter produktGrupper fra Controller
        pg.getItems().setAll(Controller.hentProduktGrupper());
    }

    /**
     * Method til at oprette vinduet opretProdukt
     */

    private void opretProdukt(ProduktGruppe pg) {
        ProduktOpretVinduet dialog = new ProduktOpretVinduet("Opret Produkt gruppe", null, pg);
        dialog.showAndWait();
        hentOgVisProdukter(produktGruppeListView);
    }

    /**
     * Metoden henter og viser produkter
     */

    private void hentOgVisProdukter(ListView<ProduktGruppe> p) {

        ProduktGruppe pg = p.getSelectionModel().getSelectedItem();
        //henter produkter af produktGruppe der er valgt hvis listen er ikke tom
        if (pg != null && !pg.hentProdukter().isEmpty()) {
            produktListView.getItems().setAll(
                    pg.hentProdukter());
        }
    }

    /**
     * Metode der fjerner den selekte produkt fra produktListView der tihoerer
     * til den valgte produktGruppeListView element
     */

    private void fjernProdukt(ProduktGruppe pg, Produkt p){
        // fjerner produkt p fra produktGruppe pg
        pg.fjernProdukt(p);

        //opdaterer produktListView
        hentOgVisProdukter(produktGruppeListView);
    }
}
