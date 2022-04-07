package gui;


import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdreTab extends GridPane {
    private ListView<Ordre> ordreListView = new ListView<>();
    private ListView<Ordre> udlejningListView = new ListView<>();
    private Ordre ordre;
    private Button opretUdlejning = new Button();
    private Button afrejnUdlejning = new Button("Afrejn udlejning");

    public OrdreTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //-------------------------LABEL----------------------------------
        //Label ordre
        Label ordreLabel = new Label();
        ordreLabel.setText("Ordre");
        this.add(ordreLabel, 0, 0);

        //Label ordre
        Label udlejningLabel = new Label();
        udlejningLabel.setText("Udlejninger");
        this.add(udlejningLabel, 4, 0);


        //-------------------------LIST_VIEW-------------------------------
        //listView ordre
        int x = 200;
        ordreListView.setPrefSize(x, x * 1.5);
        this.add(ordreListView, 0, 1, 2, 2);

        //listView udlejningListView
        udlejningListView.setPrefSize(x, x * 1.5);

        this.add(udlejningListView, 4, 1, 2, 2);

        //-------------------------BUTTON-------------------------------
        //Button opretOrder knap
        Button opretOrder = new Button();
        opretOrder.setText("Opret order");
        int size = 90;
        opretOrder.setPrefSize(size, size);
        opretOrder.setOnAction(event -> this.opretOrder());

        //Button opretUdlejning knap

        opretUdlejning.setPrefSize(size, size);

        opretUdlejning.setText("Ny Udlejning");
        this.add(opretUdlejning, 6, 1);
        opretUdlejning.setOnAction(event -> this.nyUdlejning());

        afrejnUdlejning.setPrefSize(size, size);
        this.add(afrejnUdlejning, 6, 2);
        afrejnUdlejning.setOnAction(event -> this.afrejnUdlejningKnapMetod());



        //VBox
        VBox vBox = new VBox();
        vBox.getChildren().add(opretOrder);
        vBox.setAlignment(Pos.BOTTOM_CENTER);//pos bottom i vbox
        this.add(vBox, 2, 2);

    }

    private void opretOrder() {
        ordre = Controller.opretOrdre();
        OpretOrderVinduet dialog = new OpretOrderVinduet("Opret ordre vinduet", ordre);
        dialog.showAndWait();
        //
        //
        ordreListView.getItems().setAll(Controller.hentOdreAfType("o"));
    }

    private void nyUdlejning() {
        OpretNyUdlejningVinduet dialog = new OpretNyUdlejningVinduet("Ny Udlejning");
        dialog.showAndWait();
        //
        Random r = new Random();
        int a = r.nextInt(256);
        int b = r.nextInt(256);
        int c = r.nextInt(256);
        opretUdlejning.setStyle("-fx-background-color: rgb(" + a + "," + b + "," + c + ");");
        //
        udlejningListView.getItems().setAll(Controller.hentOdreAfType("u"));

        if(!udlejningListView.getItems().isEmpty())
            udlejningListView.getSelectionModel().select(0);
    }

    private void afrejnUdlejningKnapMetod() {
        Ordre o = udlejningListView.getSelectionModel().getSelectedItem();
        //TODO metod knap
        if(o != null) {
            AfrejnUdlejningVinduet dialog = new AfrejnUdlejningVinduet("Afregn udlejning", o);
            dialog.showAndWait();
        }

    }
}

class AfrejnUdlejningVinduet extends Stage {

    private Ordre ordre;

    private TextField textFieldFustage = new TextField();
    private TextField textFieldKulsyre = new TextField();
    private Button buttonAfregnPris = new Button("Afregn pris");
    private CheckBox checkBox = new CheckBox();
    private ListView<Ordrelinje> listViewFustage = new ListView<>();

    public AfrejnUdlejningVinduet(String title, Ordre ordre) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        this.setTitle(title);
        this.ordre = ordre;

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //-------------------- Label -------------------------

        Label labelAnlaeg = new Label("Anlæg ok");
        Label labelFustagePant = new Label("Fustage pant");
        Label labelKulsyrePant = new Label("Kulsyre pant");
        Label labelAfregnPris = new Label("Afregn pris");

        //-------------------- Button -------------------------
        Button buttonFustage = new Button("Fuld fustage");
        buttonFustage.setOnAction(event -> this.buttonFustageKnapMetod());
        //
        Button buttonRyd = new Button("Ryd");
        buttonRyd.setOnAction(event -> this.buttonRydKnapMetod());
        //-------------------- CheckBox -------------------------


        //-------------------- Button -------------------------
        buttonAfregnPris.setOnAction(event -> this.buttonAfregnPrisKnapMetod());

        //-------------------- ListView -------------------------
        listViewFustage.setPrefSize(200, 200);

        //-------------------- Tilfoej -------------------------
        pane.add(labelAnlaeg, 0, 0);
        pane.add(labelFustagePant, 0, 1);
        pane.add(labelKulsyrePant, 0, 2);
        pane.add(labelAfregnPris, 2, 6);
        //
        pane.add(buttonFustage, 0, 3);
        pane.add(buttonRyd, 0, 5);
        //
        pane.add(checkBox, 1, 0);
        //
        pane.add(textFieldFustage, 1, 1);
        pane.add(textFieldKulsyre, 1, 2);
        pane.add(buttonAfregnPris, 3, 6);

        //
        pane.add(listViewFustage, 1, 3, 1, 3);
    }

    private void buttonFustageKnapMetod() {
        LilleFustageVinduet dialog = new LilleFustageVinduet(ordre,listViewFustage);
        dialog.showAndWait();

    }

    private void buttonRydKnapMetod(){
        listViewFustage.getItems().clear();
    }

    private void hentFustager(){
        List<Ordrelinje> ol = ordre.hentOrdrelinjer();
        ol.removeIf(o -> !o.hentProdukt().hentProduktGruppe().equals("fustage"));
        listViewFustage.getItems().setAll(ol);
    }

    private void buttonAfregnPrisKnapMetod(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Titel");
        double pris = 0;
        if(ordre instanceof Udlejning) {
            pris += ((Udlejning) ordre).afregn(checkBox.isSelected(),
                    Integer.parseInt(textFieldFustage.getText()), Integer.parseInt(textFieldKulsyre.getText()));
        }
        alert.setHeaderText("Udlejnings pris: "+pris);
        alert.setContentText("Content text");

        alert.showAndWait();
    }
}

class LilleFustageVinduet extends Stage {
    private Ordre ordre;
    private ListView<Ordrelinje> fustager;
    private ComboBox<Ordrelinje> produktComboBox = new ComboBox<>();
    private TextField textFieldAntal = new TextField();

    public LilleFustageVinduet(Ordre ordre,ListView<Ordrelinje> fustagerListView) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.ordre = ordre;
        fustager = fustagerListView;

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

        //-------------------- Button -------------------------
        Button buttonTilfoej = new Button("Tilføj");
        buttonTilfoej.setOnAction(event -> this.buttonTilfoejKnapMetod());
        //-------------------- ComboBox -------------------------
        hentFustager();
        //-------------------- Tilfoej -------------------------
        pane.add(produktComboBox, 0, 0,2,1);
        pane.add(new Label("Antal"), 0,1 );
        pane.add(textFieldAntal, 1, 1);
        pane.add(buttonTilfoej, 1, 3);


    }

    private void buttonTilfoejKnapMetod(){
        int antal = Integer.parseInt(textFieldAntal.getText());
        Produkt p = produktComboBox.getSelectionModel().getSelectedItem().hentProdukt();
        Prisliste pl = produktComboBox.getSelectionModel().getSelectedItem().hentPrisliste();

        System.out.println(pl);
        System.out.println(p);
        if(p instanceof PantProdukt) System.out.println("pantprodukt");

        if(ordre instanceof Udlejning && p instanceof PantProdukt) {
            Ordrelinje ol = ((Udlejning) ordre).opretReturFustage(antal, (PantProdukt) p, pl);
            fustager.getItems().add(ol);
        }
        this.hide();
    }

    private void hentFustager(){
        List<Ordrelinje> ol = new ArrayList<>(ordre.hentOrdrelinjer());
        ol.removeIf(o -> !o.hentProdukt().hentProduktGruppe().hentNavn().equals("fustage"));
        produktComboBox.getItems().setAll(ol);
    }
}