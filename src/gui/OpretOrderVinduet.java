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

import java.util.Optional;


public class OpretOrderVinduet extends Stage {

    private final Ordre order;
    private ListView<Ordrelinje> ordreLinjeListView = new ListView<>();

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
        order = o;

        GridPane pane = new GridPane();
        this.initContentPane(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        //order slettes hvis vinduet er blive lukket
        this.setOnCloseRequest(event -> Controller.fjernOrdre(order));
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


        //ListView<Ordre> ordreLinjeListView
        int size = 200;
        ordreLinjeListView.setPrefSize(size, size);
        ordreLinjeListView.getItems().setAll(order.hentOrdrelinjer());
        pane.add(ordreLinjeListView, 0, 1);

        //Knappe tilfoej
        Button buttonTilfoejOrder = new Button("Tilfoej");
        buttonTilfoejOrder.setOnAction(event -> this.tilfoejOrdreLinjeKnapMetode());


        //Knappe fjern
        Button buttonFjernOrder = new Button("Fjern");
        buttonFjernOrder.setOnAction(event -> this.fjernOrderLinje());

        //HBox hBoxTFOrdre
        HBox hBoxTFOrdre = new HBox();
        hBoxTFOrdre.getChildren().add(buttonTilfoejOrder);
        hBoxTFOrdre.getChildren().add(buttonFjernOrder);
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


        //--------------------------BELOEB----------------------------

        //Button buttonBeloeb
        Button knapBeloeb = new Button();
        knapBeloeb.setText("Beloeb");
        knapBeloeb.setOnAction(event -> this.beloebKnapMetode());
        pane.add(knapBeloeb, 3, 3);

    }

    private void beloebKnapMetode() {
        textFieldKlip.clear();
        textFieldDKK.clear();
        this.hide();
    }

    /**
     * Metoden aabner et vindue for at tilfoeje en ny order linje
     */
    private void tilfoejOrdreLinjeKnapMetode() {
        TilfoejOrderLinjeVinduet dialog = new TilfoejOrderLinjeVinduet("Tilfoej order linje", null, order);
        dialog.showAndWait();
        //
        opdatereTF();
        //
        ordreLinjeListView.getItems().setAll(order.hentOrdrelinjer());
    }

    private void opdatereTF() {
        double talDkk = order.totalPris();
        int talKlip = order.klipPris();

        textFieldDKK.setText("" + talDkk);
        textFieldKlip.setText("" + talKlip);
    }

    /**
     * Metoden der fjerner den valgte order linje
     */
    private void fjernOrderLinje() {
        if (ordreLinjeListView.getSelectionModel().getSelectedItem() != null) {
            fjernOrderLinjeBesked();
        } else {
            fjernOrderLinjeBeskedFejl();
        }
    }

    private void fjernOrderLinjeBesked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //navn på alert vinduet
        alert.setTitle("Baekreftelse vinduet");

        //anden linje text
        alert.setHeaderText("Oensker at slette orderLinje?");
        //tredje linje text
        alert.setContentText("Er du sikkert på at orderLinje skal slettes?");

        //oprettes reaktion på knappe truk
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            //den valgte produkt fra listView Prisliste fjernes
            order.fjernOrdrelinje(ordreLinjeListView.getSelectionModel().getSelectedItem());

            //opdatere orderLinjeListViewe efter orderLinje var slettet
            ordreLinjeListView.getItems().setAll(order.hentOrdrelinjer());

        } else {
            //vinduet lukkes
            alert.close();
        }
    }

    private void fjernOrderLinjeBeskedFejl() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        //navn på alert vinduet
        alert.setTitle("Information vinduet");

        //anden linje text
        alert.setHeaderText("Order linje er ikke vaglt?");

        //tredje linje text
        alert.setContentText("Du skal valge order linje.");

    }
}


/**
 *
 */