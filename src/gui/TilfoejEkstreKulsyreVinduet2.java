package gui;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.Ordre;
import model.Prisliste;
import model.Produkt;
import model.ProduktGruppe;

public class TilfoejEkstreKulsyreVinduet2 extends Stage {

    private Ordre ordre;
    private Prisliste prisliste;

    private ComboBox<Prisliste> comboBoxPrisliste = new ComboBox<>();
    private ComboBox<ProduktGruppe> comboBoxProduktGruppe = new ComboBox<>();
    private ComboBox<Produkt> comboBoxProdukt = new ComboBox<>();

    public TilfoejEkstreKulsyreVinduet2(String title,Ordre ordre) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        //

        this.setTitle(title);
        this.ordre=ordre;
        this.prisliste = prisliste;
        //

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

        //---------------------LABELS---------------------------------

        //Label labelPrisliste
        Label labelPrisliste = new Label();
        labelPrisliste.setText("Prisliste");
        pane.add(labelPrisliste,0 , 0);
        //Label labelAntal
        Label labelProduktGruppe = new Label();
        labelProduktGruppe.setText("ProduktGruppe");
        pane.add(labelProduktGruppe,0 ,1 );
        //Label labelPris
        Label labelProdukt = new Label();
        labelProdukt.setText("Produkt");
        pane.add(labelProdukt,0 ,2 );
        //
        Label labelAntal = new Label();
        //---------------------TEXTFIELDS---------------------------------

        //comboBoxPrisliste
        comboBoxPrisliste.getItems().setAll(Controller.hentPrislister());
        ChangeListener<Prisliste> listener1 = (observable, oldValue, newValue) -> this.comboBoxPrislisteListener();
        comboBoxPrisliste.getSelectionModel().selectedItemProperty().addListener(listener1);
        pane.add(comboBoxPrisliste,1 ,0 );

        //comboBoxProduktGruppe
        ChangeListener<ProduktGruppe> listener2 = (observable, oldValue, newValue) -> this.comboBoxProduktGruppeListener();
        comboBoxProduktGruppe.getSelectionModel().selectedItemProperty().addListener(listener2);
        pane.add(comboBoxProduktGruppe, 1,1 );

        //comboboxProdukt
        pane.add(comboBoxProdukt,1 ,2 );

        //
        Button buttonTilfoej = new Button();
        buttonTilfoej.setText("Tilfoej");
        buttonTilfoej.setOnAction(event -> this.tilfoejEkstraKulsyreKnap());
        pane.add(buttonTilfoej,1 ,3 );
    }

    private void tilfoejEkstraKulsyreKnap(){
        //ordre.opretOrdrelinje();
    }

    private void comboBoxPrislisteListener() {
        opdaterProduktGruppeListView();
    }

    private void opdaterProduktGruppeListView(){
        comboBoxProduktGruppe.getItems().setAll(Controller.hentProduktGrupper());
    }

    private void comboBoxProduktGruppeListener(){
        opdaterProduktListView();
    }

    private void opdaterProduktListView(){
        comboBoxProdukt.getItems().setAll(comboBoxProduktGruppe.getSelectionModel().getSelectedItem().hentProdukter());
    }
}
