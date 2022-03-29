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



public class OpretOrderVinduet extends Stage {

    private final Ordre order;
    private ListView<Ordre> ordreListView = new ListView<>();
    /**
     *
     */
    public OpretOrderVinduet(String title, Ordre o){
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

    }

    private void initContentPane(GridPane pane){
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //Label valyteProdukter
        Label valyteProdukter = new Label();
        valyteProdukter.setText("Valyte produkter");
        pane.add(valyteProdukter, 0, 0);


        //ListView<Ordre> ordreListView
        int size = 200;
        ordreListView.setPrefSize(size,size);
        ordreListView.getItems().setAll(Controller.hentOrdre());
        pane.add(ordreListView, 0, 1);

        //Knappe tilfoej
        Button buttonTilfoejOrder = new Button("Tilfoej");
        buttonTilfoejOrder.setOnAction(event -> this.tilfoejOrdreLinjeKnapMetode());


        //Knappe fjern
        Button buttonFjernOrder = new Button("Fjern");

        //HBox hBoxTFOrdre
        HBox hBoxTFOrdre =  new HBox();
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
        betalinsgmetodeComboBox.getItems().setAll(Betalinsgmetode.MOBILPAY,Betalinsgmetode.KORT,Betalinsgmetode.KONTANT,Betalinsgmetode.REGNING);
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
        TextField textFieldDKK = new TextField();
        textFieldDKK.setEditable(false);
        pane.add(textFieldDKK, 2, 2);

        //TextField textFieldKlip
        TextField textFieldKlip = new TextField();
        textFieldKlip.setEditable(false);
        pane.add(textFieldKlip, 2, 3);


        //--------------------------BELOEB----------------------------

        //Button buttonBeloeb
        Button knapBeloeb = new Button();
        knapBeloeb.setText("Beloeb");
        knapBeloeb.setOnAction(event -> this.beloebKnapMetode());
        pane.add(knapBeloeb, 3, 3);

    }

    private void beloebKnapMetode(){


    }

    /**
     * Metoden aabner et vindue for at tilfoeje en ny order linje
     */
    private void tilfoejOrdreLinjeKnapMetode() {
        TilfoejOrderLinjeVinduet dialog = new TilfoejOrderLinjeVinduet("Tilfoej order linje",null);
        dialog.showAndWait();
        //
        //
        ordreListView.getItems().setAll(Controller.hentOrdre());
    }
}


/**
 *
 */