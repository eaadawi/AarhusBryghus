package gui;

import com.sun.javafx.property.adapter.PropertyDescriptor;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Produkt;
import model.Prisliste;
import model.ProduktGruppe;
import model.Valuta;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpretPrislisteTab extends GridPane {

    private final ListView<Prisliste> prislisteListView = new ListView<>();
    private final ListView<Produkt> produktListView = new ListView<>();
    private final ListView<Double> produktListViewPris = new ListView<>();

    //disable knapper
    private final Button fjernPrislisteKnappe = new Button();
    private final Button tiljoejProduktKnappe = new Button();
    private final Button fjernProduktKnappe = new Button();


    public OpretPrislisteTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(true);

        //----------------------------------------_Prisliste LIST VIEW


        //label produkt grp oprettes
        Label labelPrisliste = new Label();
        labelPrisliste.setText("Prislister");

        this.add(labelPrisliste, 0, 0);


        //tilfoejes prislisteListView til pane
        this.add(prislisteListView, 0, 1, 2, 4);
        prislisteListView.setPrefWidth(200);
        prislisteListView.setPrefHeight(200);

        //valger den oeverste element hvis listen er ikke tom
        if (!prislisteListView.getItems().isEmpty()) {
            prislisteListView.getSelectionModel().select(0);
        }

        //viser Prisliste i ListView
        hentOgVisPrislister(prislisteListView);

        //tilfoejes listener til prislisteListView
        ChangeListener<Prisliste> listener = (ov, o, n) -> this.selectedPrislisteChanged(fjernPrislisteKnappe, tiljoejProduktKnappe);
        prislisteListView.getSelectionModel().selectedItemProperty().addListener(listener);

        //Knappe tilfoej til Prisliste oprettes
        Button tiljoejPrislisteKnappe = new Button();
        tiljoejPrislisteKnappe.setText("TilfoejPL");
        tiljoejPrislisteKnappe.setOnAction(event -> this.opretPrisliste());

        //Knappe fjern text settes

        fjernPrislisteKnappe.setText("Fjern");

        //knappen er disabled hvis Prisliste er ikke valgt
        fjernPrislisteKnappe.setDisable(true);

        //metode til fjernPrislisteKnappe laves
        fjernPrislisteKnappe.setOnAction(event -> this.sletPLbekraeftelse());

        //HBox til knapper oprettes
        HBox hbPGt = new HBox();
        hbPGt.getChildren().add(tiljoejPrislisteKnappe);
        //hbPG.getChildren().add(fjernPrislisteKnappe);

        //HBox til knapper oprettes
        HBox hbPGf = new HBox();
        hbPGf.getChildren().add(fjernPrislisteKnappe);


        //HBox Prisliste tilfoejes til pane
        this.add(hbPGt, 0, 5);
        this.add(hbPGf, 1, 5);

        //--------------------------------------------_PRODUKTLISTVIEW
        //oprettes label produkt
        Label labelProdukt = new Label();
        labelProdukt.setText("Produkter");
        //tilfoejes label og produktListView
        this.add(labelProdukt, 2, 0);
        this.add(produktListView, 2, 1, 2, 4);

        //plw stoerelse
        produktListView.setPrefWidth(160);
        produktListView.setPrefHeight(200);

        //henter og viser produkter af selected Prisliste
        hentOgVisProdukter(prislisteListView.getSelectionModel().getSelectedItem());

//        //tilfoejes listener til produktListView
//        ChangeListener<Produkt> listenerProdukt = (ov, o, n) -> this.selectedProduktChanged(fjernProduktKnappe);
//        produktListView.getSelectionModel().selectedItemProperty().addListener(listenerProdukt);


        //Knapper til produkt tekst settes
        tiljoejProduktKnappe.setText("TilfoejP");

        //tilfoejProduktKnappe disables til der bliver valgt Prisliste
        tiljoejProduktKnappe.setDisable(true);

        //metoden til tiljoejProduktKnappe oprettes
        //med valgtePrisliste
        tiljoejProduktKnappe.setOnAction(event -> this.tilfoejProdukt(prislisteListView.getSelectionModel().getSelectedItem()));

        //settes tekst til fjernProduktKnappe
        fjernProduktKnappe.setText("Fjern");

        //fjernProduktKnappe disables til der bliver valgt et produkt
        fjernProduktKnappe.setDisable(true);

        //methode til fjernProduktKnappe laves her
        fjernProduktKnappe.setOnAction(event -> this.fjernProdukt(
                prislisteListView.getSelectionModel().getSelectedItem(), produktListView.getSelectionModel().getSelectedItem()));

        //HBox til knappeT oprettes
        HBox hbPt = new HBox();
        hbPt.getChildren().add(tiljoejProduktKnappe);

        //HBox til knappeF oprettes
        HBox hbPf = new HBox();
        hbPf.getChildren().add(fjernProduktKnappe);

        //HBox produkt tilfoejes til pane
        this.add(hbPt, 2, 5);
        this.add(hbPf, 3, 5);

        //----------------------produktListViewPris-----------------------

        //produktListViewPris size
        produktListViewPris.setPrefSize(40, 200);

        //HBox til 2 ListView navn og pris
        HBox hBoxLV = new HBox();
        hBoxLV.getChildren().addAll(produktListView,produktListViewPris);
        this.add(hBoxLV, 2, 1,2,4);

    }


    private void opretPrisliste() {
        OpretPrislisteVinduet dialog = new OpretPrislisteVinduet("Opret Prisliste Vinduet",null,null);
        dialog.showAndWait();

        // Wait for the modal dialog to close

        //hentes prislister fra Controller
        prislisteListView.getItems().setAll(Controller.hentPrislister());
//        int index = prislisteListView.getItems().size() - 1;
//        produktListView.getSelectionModel().select(index);

    }

    /**
     * Metode viser de produkter som er tilhoerer til valgte Prisliste
     */
    private void selectedPrislisteChanged(Button b1, Button b2) {
        opdatereProdukterListView(b1, b2);
    }


    private void opdatereProdukterListView(Button b1, Button b2) {

        //disable knapper bliver brugelige
        b1.setDisable(false);
        b2.setDisable(false);

        //pg er den valgte element fra listView
        Prisliste pg = prislisteListView.getSelectionModel().getSelectedItem();

        //opdateres produkt listView
        if (pg != null) {

            //oprettes list for at samle produkter
            List<Produkt> produktList = new ArrayList<>();

            for (Produkt p : pg.hentProdukter()) {
                produktList.add(p);
            }

            // produktListView opdateres med de produkter som passer til den valgte Prisliste
            produktListView.getItems().setAll(produktList);
        }
    }

    /**
     * Metod srpoerger om bekraeftelse at slette Prisliste
     */

    private void sletPLbekraeftelse() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //navn på alert vinduet
        alert.setTitle("Baekreftelse vinduet");

        //anden linje text
        alert.setHeaderText("Oensker at slette prisliste?");
        //tredje linje text
        alert.setContentText("Er du sikkert på at prislisten skal slettes?");

        //oprettes reaktion på knappe truk
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            //den valgte produkt fra listView Prisliste fjernes
            Controller.fjernPrisliste(prislisteListView.getSelectionModel().getSelectedItem());

            //opdatere prislisteListViewe efter produktGtuppe var slettet
            hentOgVisPrislister(prislisteListView);

        } else {
            //vinduet lukkes
            alert.close();
        }
    }

    /**
     * Methode der opdatere og viser Prislister i prislisteListView
     */
    private void hentOgVisPrislister(ListView<Prisliste> pl) {

        //henter Prislister fra Controller
        pl.getItems().setAll(Controller.hentPrislister());
    }

    /**
     * Method til at oprette vinduet tilfoejProdukt til prislist
     */

    private void tilfoejProdukt(Prisliste pl) {
        PrislistProduktTilfoejVinduet dialog = new PrislistProduktTilfoejVinduet("Opret Produkt", pl);
        dialog.showAndWait();

        //produktListView opdateres after produkt var tilfoejet

        hentOgVisProdukter(pl);
    }

    /**
     * Metoden henter og viser produkter
     */

    private void hentOgVisProdukter(Prisliste pl) {

        //henter produkter af Prisliste der er valgt hvis listen er ikke tom
        if (pl != null && !pl.hentProdukter().isEmpty()) {
            produktListView.getItems().setAll(pl.hentProdukter());


//            ////////////////////////////////////////////
            ArrayList<Double> priser = new ArrayList<>();
            for(Produkt p: pl.hentProdukter()){
                priser.add(pl.hentPris(p));
            }
            produktListViewPris.getItems().setAll(priser);

        } else {
            produktListView.getItems().clear();
//            produktListViewPris.getItems().clear();
        }



    }

    private void fjernProdukt(Prisliste pl, Produkt p) {
        pl.fjernProdukt(p);
        hentOgVisProdukter(pl);
    }

//    private void selectedProduktChanged(){
//
//    }
}


//-------------en gemt clas for at opret prisliste vinduet -------------
class OpretPrislisteVinduet extends Stage {


   private final Prisliste prisliste;// OBS :nullable

    //text field oprettes
    private TextField textFieldNavnPL = new TextField();
    private Valuta valuta;

    //combobox til valuta enum
    private ComboBox<Valuta> valutaComboBox = new ComboBox<>();

    /**
     *
     */
    public OpretPrislisteVinduet(String title,Prisliste prisliste,Valuta v) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.prisliste = prisliste;
        this.valuta = v;
        this.setTitle(title);

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

        //Label navn
        Label labelNavn = new Label();
        labelNavn.setText("Navn");

        //Label valuta
        Label labelValuta = new Label();
        labelValuta.setText("Valuta");


        //labelNavn og labelValuta tilfoejes til pane
        pane.add(labelNavn, 0, 0);
        pane.add(labelValuta, 0, 1);

        //textfield tilfoejes til pane
        pane.add(textFieldNavnPL, 1, 0);

        //Combobox henter sit data
        valutaComboBox.getItems().setAll(Valuta.DKK, Valuta.KLIP);

        //combobox valger automatisk den foerste element af sin data
        valutaComboBox.getSelectionModel().select(0);

        //Combobox tilfoejes til pane
        pane.add(valutaComboBox, 1, 1);

        //knap opret oprettes
        Button opretPL = new Button();
        opretPL.setText("Opret prislist");

        //aktion til opret knappe tilfoejes
        opretPL.setOnAction(event -> this.opretPrisliste());

        //knap tilfoejes til pane
        pane.add(opretPL, 0, 2);

        //knap cancell oprettes
        Button cancellButton = new Button();
        cancellButton.setText("Cancell");

        //knap cancell tilfoejes til pane
        pane.add(cancellButton, 1, 2);

        //knap cancel action tilfoejes at gemme vinduet
        cancellButton.setOnAction(event -> this.hide());
    }

    /**
     * Metode opretter produktgruppe
     * med at tage string fra textFieldNavnPL som navn
     */
    private void opretPrisliste() {
        Controller.opretPrisliste(textFieldNavnPL.getText(), valutaComboBox.getSelectionModel().getSelectedItem());

        //textFieldNavnPL fjerne text
        textFieldNavnPL.clear();

        //gemme opretVinduet
        this.hide();
    }


}


//-------------en anden gemt clas for at opret prisliste vinduet -------------

class PrislistProduktTilfoejVinduet extends Stage {

//    private final Produkt produkt;// OBS :nullable
    private Prisliste prisliste;

    //combobox produktGruppe
    private ComboBox<ProduktGruppe> produktGruppeComboBox = new ComboBox<>();

    //combobox produkt oprettes
    private ComboBox<Produkt> produktComboBox = new ComboBox<>();

    //text fields pris produkt
    private TextField textFieldPrisProdukt = new TextField();

    /**
     * Note: Nullable param produkt gruppe.
     */
    public PrislistProduktTilfoejVinduet(String title, Prisliste pl) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

//        this.produkt = produkt;
        prisliste = pl;
        this.setTitle(title);

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

        //Label navn produktGruppe
        Label labelNavnProduktGruppe = new Label();
        labelNavnProduktGruppe.setText("Navn produktGruppe");

        //Label navn produkt
        Label labelNavnProdukt = new Label();
        labelNavnProdukt.setText("Navn produkt");

        //Label pris
        Label labelPris = new Label();
        labelPris.setText("Pris");


        //labelNavn og labelAntal tilfoejes til pane
        pane.add(labelNavnProduktGruppe, 0, 0);
        pane.add(labelNavnProdukt, 2, 0);
        pane.add(labelPris, 2, 1);

        //combobokser tilfoejes til pane
        pane.add(produktGruppeComboBox, 1, 0);
        pane.add(produktComboBox, 3, 0);
        pane.add(textFieldPrisProdukt, 3, 1);

        //comboboxProduktGrupper henter produktGrupper
        produktGruppeComboBox.getItems().setAll(Controller.hentProduktGrupper());

        //reaktion paa combobox produktGruppe ind i comboboxProdukt
        ChangeListener<ProduktGruppe> listener = (ov,o,n) -> this.valgteProduktGruppeAendrerProdukter();
        produktGruppeComboBox.getSelectionModel().selectedItemProperty().addListener(listener);
        //produktGruppeComboBox valger den foerste element automatisk
        produktGruppeComboBox.getSelectionModel().select(0);



        //comboboxProdukt henter produkter ud af den valgte prisListe

produktComboBox.getItems().setAll(produktGruppeComboBox.getSelectionModel().getSelectedItem().hentProdukter());

        //knap tilfoej produkt til prislisten oprettes
        Button tilfoejProduktKnap2 = new Button();
        tilfoejProduktKnap2.setText("tilfoejPP");

        //aktion til opret knappe tilfoejes
        tilfoejProduktKnap2.setOnAction(event -> this.tilfoejProduktTilPL(prisliste));

        //knap tilfoejes til pane
        pane.add(tilfoejProduktKnap2, 0, 2);

        //knap cancell oprettes
        Button cancellButton = new Button();
        cancellButton.setText("Cancell");

        //knap cancell tilfoejes til pane
        pane.add(cancellButton, 1, 2);

        //knap cancel action tilfoejes at gemme vinduet
        cancellButton.setOnAction(event -> this.hide());
    }


    /**
     * Metod som tilfoeje produkt til prislisten
     */
    private void tilfoejProduktTilPL(Prisliste pl) {

        //textfieldAntal til int
        int pris = Integer.parseInt(textFieldPrisProdukt.getText());

        //
        //Valuta v = valuta.

        // prislisten tilfoeje valgte produkt med den pris
        //som er staar ind i textFieldPrisProdukt som er konverteres til int
        pl.tilfoejProdukt(produktComboBox.getSelectionModel().getSelectedItem(), Integer.parseInt(textFieldPrisProdukt.getText()));


        //fjerner text og lukker vinduet
        textFieldPrisProdukt.clear();
        this.hide();
    }


    private void valgteProduktGruppeAendrerProdukter(){
        this.opdatereProduktGrupperComboBox();
    }

    private void opdatereProduktGrupperComboBox(){
        produktComboBox.getItems().setAll(produktGruppeComboBox.getSelectionModel().getSelectedItem().hentProdukter());
    }
}
