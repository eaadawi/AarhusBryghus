package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

public class TilfoejEkstreKulsyreVinduet2 extends Stage {

    private Ordre ordre;
    private Prisliste prisliste;

    private ComboBox<Prisliste> comboBoxPrisliste = new ComboBox<>();
    private ComboBox<ProduktGruppe> comboBoxProduktGruppe = new ComboBox<>();
    private ComboBox<Produkt> comboBoxProdukt = new ComboBox<>();

    private TextField textFieldAntal = new TextField();
    private TextField textFieldPris = new TextField();

    public TilfoejEkstreKulsyreVinduet2(String title,Ordre ordre,Prisliste pl) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        //

        this.setTitle(title);
        this.ordre=ordre;
        this.prisliste = pl;
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
        //Label labelPris
        Label labelProdukt = new Label();
        labelProdukt.setText("Produkt");
        pane.add(labelProdukt,0 ,1 );
        //
        Label labelAntal = new Label();
        labelAntal.setText("Antal");//tilfoejes i row2 senere ind i hboxMain
        //
        Label labelPris = new Label("Pris");
        pane.add(labelPris, 0, 3);

        //---------------------TEXTFIELDS---------------------------------

        //comboBoxPrisliste
        comboBoxPrisliste.getItems().setAll(prisliste);
        comboBoxPrisliste.getSelectionModel().select(0);
        ChangeListener<Prisliste> listener1 = (observable, oldValue, newValue) -> this.comboBoxPrislisteListener();
        comboBoxPrisliste.getSelectionModel().selectedItemProperty().addListener(listener1);
        pane.add(comboBoxPrisliste,1 ,0 );

        //comboBoxProduktGruppe

        //comboboxProdukt
        comboBoxProdukt.getItems().setAll(Controller.hentProdukterFraGruppenavn("Kulsyre"));
        pane.add(comboBoxProdukt, 1,1 );

        //
        textFieldAntal.setText("0");
        textFieldAntal.setEditable(false);

        //
        Button buttonPlus = new Button("+");
        buttonPlus.setOnAction(event -> this.plusKulsyre());

        Button buttonMinus = new Button("-");
        buttonMinus.setOnAction(event -> this.minusKulsyre());


        //
        HBox hBoxMain = new HBox(labelAntal);
        hBoxMain.setPadding(new Insets(10));
        hBoxMain.setSpacing(10);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(textFieldAntal,buttonPlus,buttonMinus);
        hBoxMain.getChildren().add(hBox);
        pane.add(hBoxMain, 0, 2,2,1);

        //
        pane.add(textFieldPris, 1, 3);

        //
        Button buttonTilfoej = new Button();
        buttonTilfoej.setText("Tilfoej");
        buttonTilfoej.setOnAction(event -> this.tilfoejEkstraKulsyreKnap());
        pane.add(buttonTilfoej,2 ,3 );

    }

    private void tilfoejEkstraKulsyreKnap(){
        int tal = Integer.parseInt(textFieldAntal.getText());
        PantProdukt pp = new PantProdukt(comboBoxProdukt.getSelectionModel().getSelectedItem().hentNavn(),tal,1);
        ordre.opretOrdrelinje(tal,comboBoxProdukt.getSelectionModel().getSelectedItem(),comboBoxPrisliste.getSelectionModel().getSelectedItem());
        this.hide();
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

    private void plusKulsyre(){
        int tal = Integer.parseInt(textFieldAntal.getText());
        tal++;
        textFieldAntal.setText(""+tal);

        udregnPris(textFieldPris);
    }

    private void minusKulsyre(){
        int tal = Integer.parseInt(textFieldAntal.getText());
        tal--;
        if(tal<0)
            tal =0;
        textFieldAntal.setText(""+tal);

        udregnPris(textFieldPris);
    }

    private void udregnPris(TextField tf){
        int antal = Integer.parseInt(textFieldAntal.getText());
        double pris = Controller.hentPrislisteFraNavn("Butik").hentPris(comboBoxProdukt.getSelectionModel().getSelectedItem());
        textFieldPris.setText(""+antal*pris);
    }
}
