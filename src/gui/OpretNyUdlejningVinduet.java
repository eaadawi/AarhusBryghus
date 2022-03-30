package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Ordrelinje;
import model.Udlejning;

import java.util.ArrayList;
import java.util.List;


public class OpretNyUdlejningVinduet extends Stage {

    private final Udlejning udlejning;

    private ListView<Ordrelinje> listViewFustager = new ListView<>();
    private ListView<Ordrelinje> listViewKulsyre = new ListView<>();

    public OpretNyUdlejningVinduet(String title, Udlejning udlejning) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);

        //
        this.udlejning = udlejning;
        this.setTitle(title);

        //
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

        //
        Label startDatoLabel = new Label();
        startDatoLabel.setText("Start dato");
        pane.add(startDatoLabel, 0, 0);
        //
        Label slutDatoLabel = new Label();
        slutDatoLabel.setText("Slut dato");
        pane.add(slutDatoLabel, 0, 1);
        //
        Label navnLabel = new Label();
        navnLabel.setText("Navn");
        pane.add(navnLabel, 0, 2);
        //
        Label telefonNrLabel = new Label();
        telefonNrLabel.setText("Telefon nr");
        pane.add(telefonNrLabel, 0, 3);
        //
        Label emailLabel = new Label();
        emailLabel.setText("Email");
        pane.add(emailLabel, 0, 4);
        //
        Label foedseldsdagLabel = new Label();
        foedseldsdagLabel.setText("Foedseldsdag");
        pane.add(foedseldsdagLabel, 0, 5);

        //-------------------COL_2_TEXTFIELD-----------------

        //
        DatePicker startDatoDatePicker = new DatePicker();
        pane.add(startDatoDatePicker, 1, 0);
        //
        DatePicker slutDatoDatePicker = new DatePicker();
        pane.add(slutDatoDatePicker, 1, 1);
        //
        TextField navnTextField = new TextField();
        pane.add(navnTextField, 1, 2);
        //
        TextField telefonNrTextField = new TextField();
        pane.add(telefonNrTextField, 1, 3);
        //
        TextField emailTextField = new TextField();
        pane.add(emailTextField, 1, 4);
        //
        TextField foedseldsdagTextField = new TextField();
        pane.add(foedseldsdagTextField, 1, 5);

        //-----------------------COL3_LABEL----------------------

        //
        Label labelLevering = new Label();
        labelLevering.setText("Levering");
        pane.add(labelLevering, 2, 0);
        //
        Label labelAdresseForLevering = new Label();
        labelAdresseForLevering.setText("Adresse for levering");
        pane.add(labelAdresseForLevering, 2, 1);
        //
        Label labelAntalHaner = new Label();
        labelAntalHaner.setText("Antal haner");
        pane.add(labelAntalHaner, 2, 2);
        //
        Button buttonTilfoejFustage = new Button();
        buttonTilfoejFustage.setText("Tilfoej fustage");
        buttonTilfoejFustage.setOnAction(event -> this.tilfoejFustageMetodeKnap());
        pane.add(buttonTilfoejFustage, 2, 3);
        //
        Label labelFustager = new Label();
        labelFustager.setText("Fustager:");
        pane.add(labelFustager, 2, 4);

        //ListView<?> listViewFustager
        int x = 200;
        listViewFustager.setPrefSize(x, x);
        //mangler items i listView
//        listViewFustager.getItems().setAll(hentFustageOLListView());
        pane.add(listViewFustager, 2, 5);

        //
        Label labelKrus = new Label();
        labelKrus.setText("Krus(...):");
        //
        TextField textFieldKrus = new TextField();
        //
        HBox hBoxK = new HBox();
        hBoxK.getChildren().addAll(labelKrus, textFieldKrus);
        pane.add(hBoxK, 2, 6);

        //-----------------------COL4_TF_OG_LABEL----------------------

        //
        CheckBox checkBoxLevering = new CheckBox();
        pane.add(checkBoxLevering, 3, 0);
        //
        TextField textFieldAdresse = new TextField();
        pane.add(textFieldAdresse, 3, 1);
        //
        TextField textFieldAntalHaner = new TextField();
        pane.add(textFieldAntalHaner, 3, 2);
        //
        Button buttonEkstreKulsyre = new Button();
        buttonEkstreKulsyre.setText("Ekstrea kulsyre");
        buttonEkstreKulsyre.setOnAction(event -> this.buttonEkstreKulsyreMetodeKnap());
        pane.add(buttonEkstreKulsyre, 3, 3);
        //
        Label labelKulsyre = new Label();
        labelKulsyre.setText("Kulsyre");
        pane.add(labelKulsyre, 3, 4);

        //ListView<Ordrelinje> listViewKulsyre
        listViewKulsyre.setPrefSize(x, x);
//        listViewKulsyre.getItems().setAll(this.hentEkstraKulsyreListView());
        pane.add(listViewKulsyre, 3, 5);

        //
        Label labelSamletPrisEKS = new Label();
        labelSamletPrisEKS.setText("Samlet pris (eks. pant):");
        pane.add(labelSamletPrisEKS, 3, 6);
        //
        Label labelSamletPris = new Label();
        labelSamletPris.setText("Samlet pris");
        pane.add(labelSamletPris, 3, 7);
        //
        Button buttonTilfoejUdlejning = new Button();
        buttonTilfoejUdlejning.setText("Tilfoej udlejning");
        pane.add(buttonTilfoejUdlejning, 3, 8);

        //-------------------COL5_textfields----------------

        //
        TextField textSamletPrisEKS = new TextField();
        pane.add(textSamletPrisEKS, 4, 6);
        //
        TextField textSamletPris = new TextField();
        pane.add(textSamletPris, 4, 7);
    }


    private void tilfoejFustageMetodeKnap() {
        TilfoejFustageVinduet dialog = new TilfoejFustageVinduet("Tilfoej fustage vinduet",udlejning);
        dialog.showAndWait();

        this.hentFustageOLListView();
    }


    private void buttonEkstreKulsyreMetodeKnap() {
//        TilfoejEkstreKulsyreVinduet dialog = new TilfoejEkstreKulsyreVinduet("Tilfoej ekstra kulsyre vinduet",udlejning);
//        dialog.showAndWait();

        this.hentEkstraKulsyreListView();
    }

    private List<Ordrelinje> hentFustageOLListView() {

            List<Ordrelinje> copy = new ArrayList<Ordrelinje>(udlejning.hentOrdrelinjer());
            copy.removeIf(o -> o.hentProdukt().hentProduktGruppe().hentNavn() != "Fustager");
            return copy;

    }

    private List<Ordrelinje> hentEkstraKulsyreListView() {

        List<Ordrelinje> copy = new ArrayList<Ordrelinje>(udlejning.hentOrdrelinjer());
        copy.removeIf(o -> o.hentProdukt().hentProduktGruppe().hentNavn() != "Kulsyre");
        return copy;

    }

}
