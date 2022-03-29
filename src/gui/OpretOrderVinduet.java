package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        this.setResizable(false);

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
        ComboBox<Ordre> ordreComboBox = new ComboBox<>();
        ordreComboBox.getItems().setAll(Betalinsgmetode.MOBILPAY,)

    }

}


/**
 *
 */