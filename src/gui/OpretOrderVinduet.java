package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

import java.util.Optional;


public class OpretOrderVinduet extends Stage {

    private final Ordre ordre;
    private final Prisliste prisliste;

    private ListView<Ordrelinje> ordrelinjeListView = new ListView<>();

    private TextField textFieldDKK = new TextField();
    private TextField textFieldKlip = new TextField();

    /**
     *
     */
    public OpretOrderVinduet(String title, Ordre o) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        //
        this.setTitle(title);
        ordre = o;
        prisliste =Controller.hentPrislisteFraNavn("Butik");
        //
        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        //ordre slettes hvis vinduet er blive lukket
        this.setOnCloseRequest(event -> Controller.fjernOrdre(ordre));
    }

    private void initContentPane(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //Label valyteProdukter
        Label valyteProdukter = new Label();
        valyteProdukter.setText("Valgte produkter");


        //ListView<Ordrelinje> ordreLinjeListView
        int size = 200;
        ordrelinjeListView.setPrefSize(size, size);
        ordrelinjeListView.getItems().setAll(ordre.hentOrdrelinjer());


        //Knappe tilfoej
        Button buttonTilfoejordre = new Button("Tilføj");
        buttonTilfoejordre.setOnAction(event -> this.tilfoejOrdreLinjeKnapMetode());


        //Knappe fjern
        Button buttonFjernordre = new Button("Fjern");
        buttonFjernordre.setOnAction(event -> this.fjernordreLinje());

        //HBox hBoxTFOrdre
        HBox hBoxTFOrdre = new HBox();
        hBoxTFOrdre.getChildren().add(buttonTilfoejordre);
        hBoxTFOrdre.getChildren().add(buttonFjernordre);



        //---------------COMBOBOX-------------------------------------

        //Label labelComboBox
        Label labelComboBox = new Label();
        labelComboBox.setText("Valg betaling");


        //ComboBox<Ordre> ordreComboBox
        ComboBox<Betalinsgmetode> betalinsgmetodeComboBox = new ComboBox<>();
        betalinsgmetodeComboBox.getItems().setAll(Betalinsgmetode.MOBILPAY, Betalinsgmetode.KORT, Betalinsgmetode.KONTANT, Betalinsgmetode.REGNING);
        betalinsgmetodeComboBox.getSelectionModel().select(0);


        //Label labelPrisDKK
        Label labelPrisDKK = new Label();
        labelPrisDKK.setText("Pris i DKK");



        //Label labelPrisKlip
        Label labelPrisKlip = new Label();
        labelPrisKlip.setText("Pris i klip");


        //TextField textFieldDkk
        textFieldDKK.setEditable(false);


        //TextField textFieldKlip
        textFieldKlip.setEditable(false);


        //-------------------------- Button ----------------------------

        //Button buttonBeloeb
        Button knapBeloeb = new Button();
        knapBeloeb.setText("Beløb");
        knapBeloeb.setOnAction(event -> this.beloebKnapMetode());


        //
        //-------------- pane tilfoej ------------

        pane.add(valyteProdukter, 0, 0);
        pane.add(hBoxTFOrdre, 0, 2);
        pane.add(knapBeloeb, 3, 3);
        pane.add(betalinsgmetodeComboBox, 1, 1);
        pane.add(ordrelinjeListView, 0, 1);
        pane.add(labelComboBox, 1, 0);
        pane.add(labelPrisDKK, 1, 2);
        pane.add(labelPrisKlip, 1, 3);
        pane.add(textFieldDKK, 2, 2);
        pane.add(textFieldKlip, 2, 3);
    }

    private void beloebKnapMetode() {
        textFieldKlip.clear();
        textFieldDKK.clear();

        this.hide();
        KlippekortVinduetBeloeb klippekortVinduetBeloeb = new KlippekortVinduetBeloeb("Klippekort vinduet",ordre);
        klippekortVinduetBeloeb.showAndWait();
    }

    /**
     * Metoden aabner et vindue for at tilfoeje en ny ordre linje
     */
    private void tilfoejOrdreLinjeKnapMetode() {
        TilfoejOrderLinjeVinduet dialog = new TilfoejOrderLinjeVinduet("Tilføj ordre linje", null, ordre);
        dialog.showAndWait();
        //
        opdatereTF();
        //
        ordrelinjeListView.getItems().setAll(ordre.hentOrdrelinjer());
    }

    private void opdatereTF() {
        double talDkk = ordre.totalPris();
        int talKlip = ordre.klipPris();

        textFieldDKK.setText("" + talDkk);
        textFieldKlip.setText("" + talKlip);
    }

    /**
     * Metoden der fjerner den valgte ordre linje
     */
    private void fjernordreLinje() {
        if (ordrelinjeListView.getSelectionModel().getSelectedItem() != null) {
            fjernordreLinjeBesked();
        } else {
            fjernordreLinjeBeskedFejl();
        }
    }

    private void fjernordreLinjeBesked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //navn på alert vinduet
        alert.setTitle("Bekræftelses vindue");

        //anden linje text
        alert.setHeaderText("Ønsker at slette ordreLinje?");
        //tredje linje text
        alert.setContentText("Er du sikker på at ordreLinjen skal slettes?");

        //oprettes reaktion på knappe truk
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            //den valgte produkt fra listView Prisliste fjernes
            ordre.fjernOrdrelinje(ordrelinjeListView.getSelectionModel().getSelectedItem());

            //opdatere ordreLinjeListViewe efter ordreLinje var slettet
            ordrelinjeListView.getItems().setAll(ordre.hentOrdrelinjer());

        } else {
            //vinduet lukkes
            alert.close();
        }
    }

    private void fjernordreLinjeBeskedFejl() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        //navn på alert vinduet
        alert.setTitle("Informations vindue");

        //anden linje text
        alert.setHeaderText("ordre linje er ikke valgt?");

        //tredje linje text
        alert.setContentText("Du skal vælge en ordrelinje.");

    }

}


/**
 *
 */

class KlippekortVinduetBeloeb extends Stage{

    private final Ordre ordre;
    private ListView<Klippekort> klippekortListView = new ListView<>();
    private ListView<Klippekort> klippekortListViewValgte = new ListView<>();

    private Button buttonOpretNyKlippekort = new Button("Opret Ny");
    private Button buttonSoeg = new Button("Søg");
    private Button buttonTilfoejKk = new Button("Tilføj");
    private Button buttonFjernKk = new Button("Fjern");


    private TextField textFieldKundenavn = new TextField();
    private TextField textFieldPris = new TextField();
    private TextField textFieldKlppris = new TextField();

    public KlippekortVinduetBeloeb(String title, Ordre ordre){

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        this.setOnCloseRequest(event -> this.vinduetLukkesMetod());
        //
        this.ordre = ordre;
        this.setTitle(title);
        //
        //
        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }

    private void initContentPane(GridPane pane){
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        //-------------------------LABEL----------------------------------
        //Label ordre
        Label labelKundenavn = new Label("Kundenavn");

        //Label label2
        Label labelKlippekort = new Label("Klippekort");
        pane.add(labelKlippekort, 0, 1);

        //Label label3
        Label labelEvt = new Label("evt");

        //Label label4
        Label labelValgteKK = new Label("Valgte klippekort");
        pane.add(labelValgteKK, 2, 1);

        //Label label5
        Label labelPris = new Label("Pris");
        pane.add(labelPris, 3, 2);

        //Label label6
        Label labelKlippris = new Label("Klippris");
        pane.add(labelKlippris, 3, 3);

        //-------------------------LISTVIEW----------------------------------
        //LISTVIEW_1
        klippekortListView.setPrefSize(200, 300);
        klippekortListView.getItems().setAll(Controller.hentKlippekort());

        //LISTVIEW_2
        //klippekortListViewValgte.
        klippekortListViewValgte.setPrefSize(200, 300);

        //-------------------------BUTTON----------------------------------

        //
        buttonSoeg.setOnAction(event -> this.buttonSoegKnapMetod());

        //Button button1
        buttonOpretNyKlippekort.setMaxWidth(Double.MAX_VALUE);
        buttonOpretNyKlippekort.setMaxHeight(27);
        buttonOpretNyKlippekort.setOnAction(event -> this.opretNyKkMetod());


        //Button button2
        buttonTilfoejKk.setOnAction(event -> this.buttonTilfoejKkKnapMetod());
        buttonTilfoejKk.setMaxWidth(Double.MAX_VALUE);
        buttonTilfoejKk.setMaxHeight(27);

        //Button button3
        buttonFjernKk.setOnAction(event -> this.buttonFjernKkMetod());
        buttonFjernKk.setMaxWidth(Double.MAX_VALUE);
        buttonFjernKk.setMaxHeight(27);

        //Button button4
        Button buttonAfslut = new Button("Afslut betaling");
        buttonAfslut.setOnAction(event -> this.buttonAfslutKnappeMetod());

//-------------------------HBox-VBox---------------------------------

        //
        HBox hBox0 = new HBox(labelKundenavn,textFieldKundenavn);
        hBox0.setSpacing(10);
        pane.add(hBox0, 0, 0);

        //VBox vBox1
        VBox vBox1 = new VBox();
        vBox1.getChildren().setAll(klippekortListView, buttonOpretNyKlippekort);
        pane.add(vBox1, 0, 2, 1, 3);

        //HBox hBox1
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(buttonTilfoejKk);
        hBox2.getChildren().add(buttonFjernKk);
        HBox.setHgrow(buttonTilfoejKk, Priority.ALWAYS);
        HBox.setHgrow(buttonFjernKk, Priority.ALWAYS);
        hBox2.setSpacing(1);

        //VBox vBox2
        VBox vBox2 = new VBox();
        vBox2.getChildren().setAll(klippekortListViewValgte, hBox2);
        pane.add(vBox2, 2, 2, 1, 3);

        //
        HBox hBoxSoeg = new HBox(labelEvt,buttonSoeg);
        hBoxSoeg.setSpacing(10);
        pane.add(hBoxSoeg, 2, 0);

        //-------------------------TEXT_FIELD---------------------------------

        //

        //TextField textFieldPris
        textFieldPris.setText(""+ordre.totalPris());
        pane.add(textFieldPris, 4,2 );
        //TextField textFieldPris
        textFieldKlppris.setText(""+ordre.klipPris());
        pane.add(textFieldKlppris, 4, 3);

        //---------------------- pane tilfoej ---------------
        pane.add(buttonAfslut, 4, 5);


    }

    private void opretNyKkMetod() {
        OpretNyKlippekortVinduet dialog = new OpretNyKlippekortVinduet("Oprettelse af et nyt klippekort");
        dialog.showAndWait();
        //henter de tidlige kort sammen med den som var lige oprettet
        klippekortListView.getItems().setAll(Controller.hentKlippekort());
    }

    private void buttonAfslutKnappeMetod(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kvitering");
        alert.setHeaderText("Orderskvitering");
        alert.setContentText("Varerne der var bestilt");

        StringBuilder sb = new StringBuilder();
        for(Ordrelinje o : ordre.hentOrdrelinjer())
            sb.append("\n"+o);
        TextArea ta = new TextArea();
        ta.setText(sb.toString());
        alert.showAndWait();
        this.hide();
    }

    private void buttonTilfoejKkKnapMetod(){
        klippekortListViewValgte.getItems().add(klippekortListView.getSelectionModel().getSelectedItem());
    }

    private void buttonSoegKnapMetod(){
        klippekortListView.getItems().setAll(Controller.soegKlippekort(textFieldKundenavn.getText()));
    }

    private void buttonFjernKkMetod(){
        klippekortListViewValgte.getItems().remove(klippekortListViewValgte.getSelectionModel().getSelectedItem());
    }

    private void vinduetLukkesMetod(){
        Controller.fjernOrdre(ordre);
    }
}
