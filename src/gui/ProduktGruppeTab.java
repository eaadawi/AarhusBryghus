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
        produktGruppeListView.getItems().setAll(Controller.hentProduktGrupper());

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

    private void sletPGbekraeftelse(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //navn på alert vinduet
        alert.setTitle("Baekreftelse vinduet");

        //anden linje text
        alert.setHeaderText("Oensker at slette produkt gruppe?");
        //tredje linje text
        alert.setContentText("Er du sikkert på at produkt gruppe skal slettes?");

        //oprettes reaktion på knappe truk
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){

            //den valgte produkt fra listView produktGruppe fjernes
            Controller.fjernProduktGruppe(produktGruppeListView.getSelectionModel().getSelectedItem());


        }else{
            //vinduet lukkes
            alert.close();
        }
    }
}
