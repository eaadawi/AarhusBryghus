package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Betalinsgmetode;
import model.Ordre;
import model.Ordrelinje;
import model.Prisliste;

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
        pane.add(valyteProdukter, 0, 0);


        //ListView<Ordrelinje> ordreLinjeListView
        int size = 200;
        ordrelinjeListView.setPrefSize(size, size);
        ordrelinjeListView.getItems().setAll(ordre.hentOrdrelinjer());
        pane.add(ordrelinjeListView, 0, 1);

        //Knappe tilfoej
        Button buttonTilfoejordre = new Button("Tilfoej");
        buttonTilfoejordre.setOnAction(event -> this.tilfoejOrdreLinjeKnapMetode());


        //Knappe fjern
        Button buttonFjernordre = new Button("Fjern");
        buttonFjernordre.setOnAction(event -> this.fjernordreLinje());

        //HBox hBoxTFOrdre
        HBox hBoxTFOrdre = new HBox();
        hBoxTFOrdre.getChildren().add(buttonTilfoejordre);
        hBoxTFOrdre.getChildren().add(buttonFjernordre);
        pane.add(hBoxTFOrdre, 0, 2);


        //---------------COMBOBOX-------------------------------------

        //Label labelComboBox
        Label labelComboBox = new Label();
        labelComboBox.setText("Valg betaling");
        pane.add(labelComboBox, 1, 0);

        //ComboBox<Ordre> ordreComboBox
        ComboBox<Betalinsgmetode> betalinsgmetodeComboBox = new ComboBox<>();
        betalinsgmetodeComboBox.getItems().setAll(Betalinsgmetode.MOBILPAY, Betalinsgmetode.KORT, Betalinsgmetode.KONTANT, Betalinsgmetode.REGNING);
        betalinsgmetodeComboBox.getSelectionModel().select(0);
        pane.add(betalinsgmetodeComboBox, 1, 1);

        //Label labelPrisDKK
        Label labelPrisDKK = new Label();
        labelPrisDKK.setText("Pris i DKK");
        pane.add(labelPrisDKK, 1, 2);


        //Label labelPrisKlip
        Label labelPrisKlip = new Label();
        labelPrisKlip.setText("Pris i klip");
        pane.add(labelPrisKlip, 1, 3);

        //TextField textFieldDkk
        textFieldDKK.setEditable(false);
        pane.add(textFieldDKK, 2, 2);

        //TextField textFieldKlip
        textFieldKlip.setEditable(false);
        pane.add(textFieldKlip, 2, 3);


        //-------------------------- Button ----------------------------

        //Button buttonBeloeb
        Button knapBeloeb = new Button();
        knapBeloeb.setText("Beloeb");
        knapBeloeb.setOnAction(event -> this.beloebKnapMetode());
        pane.add(knapBeloeb, 3, 3);

        //
        //-------------- Button ------------

    }

    private void beloebKnapMetode() {
        textFieldKlip.clear();
        textFieldDKK.clear();
        this.hide();
    }

    /**
     * Metoden aabner et vindue for at tilfoeje en ny ordre linje
     */
    private void tilfoejOrdreLinjeKnapMetode() {
        TilfoejOrderLinjeVinduet dialog = new TilfoejOrderLinjeVinduet("Tilfoej ordre linje", null, ordre);
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
        alert.setTitle("Baekreftelse vinduet");

        //anden linje text
        alert.setHeaderText("Oensker at slette ordreLinje?");
        //tredje linje text
        alert.setContentText("Er du sikkert på at ordreLinje skal slettes?");

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
        alert.setTitle("Information vinduet");

        //anden linje text
        alert.setHeaderText("ordre linje er ikke vaglt?");

        //tredje linje text
        alert.setContentText("Du skal valge ordre linje.");

    }

}


/**
 *
 */