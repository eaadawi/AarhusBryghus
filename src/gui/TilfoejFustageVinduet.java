package gui;

import controller.Controller;
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


public class TilfoejFustageVinduet extends Stage {


    private final Ordre ordre;
    private final Prisliste prisliste;
    private final ComboBox<Produkt> comboBoxType = new ComboBox<>();

    private final TextField textFieldAntal = new TextField();
    private final ComboBox<Integer> comboBoxStoerelse = new ComboBox<>();
    private final TextField textFieldPris = new TextField();

    public TilfoejFustageVinduet(String title, Ordre ordre, Prisliste pl) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        //

        this.setTitle(title);
        this.ordre = ordre;
        this.prisliste = pl;

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

        //---------------------COL1-------------------------------

        //Label fustage
        Label labelFustage = new Label();
        labelFustage.setText("Fustage");
        pane.add(labelFustage, 0, 0);

        //
        Label labelStoerelse = new Label();
        labelStoerelse.setText("Størrelse");
        pane.add(labelStoerelse, 0, 1);
        //
        Label labelAntal = new Label();
        labelAntal.setText("Antal");
        pane.add(labelAntal, 0, 2);
        //
        Label labelPris = new Label();
        labelPris.setText("Pris");
        pane.add(labelPris, 0, 3);
        //--------------------------COL2---------------------

        comboBoxType.getItems().setAll(Controller.hentProdukterFraGruppenavn("fustage"));
        comboBoxType.setEditable(false);
        pane.add(comboBoxType, 1, 0);
        //
        comboBoxStoerelse.getItems().setAll(Controller.muligeStoerrelser());
        pane.add(comboBoxStoerelse, 1, 1);
        //TextField textFieldAntal
        textFieldAntal.setText("0");
        //
        Button antalPlus = new Button("+");
        antalPlus.setOnAction(event -> this.antalPlusKnapMetod());
        Button antalMinus = new Button("-");
        antalMinus.setOnAction(event -> this.antalMinusKnapMetod());
        //
        HBox hBoxAntal = new HBox(textFieldAntal, antalPlus, antalMinus);
        pane.add(hBoxAntal, 1, 2);
        //
        textFieldPris.setEditable(false);
        pane.add(textFieldPris, 1, 3);
        //--------------------------COL3---------------------

        //

        Button buttonTilfoe = new Button();
        buttonTilfoe.setText("Tilføj");
        buttonTilfoe.setOnAction(event -> this.tilfoejFustageKnapMetod());
        pane.add(buttonTilfoe, 2, 5);
    }

    private void tilfoejFustageKnapMetod() {

        Produkt fustage = comboBoxType.getSelectionModel().getSelectedItem();
        int stoerelseNy = comboBoxStoerelse.getValue();

        if (fustage instanceof PantProdukt) {
            int stoerelse = ((PantProdukt) fustage).hentStoerrelse();
            if (stoerelse != stoerelseNy) {
                ProduktGruppe pg = fustage.hentProduktGruppe();
                Prisliste pl = Controller.hentPrislisteFraNavn("Brugerdefinerede fustager");
                Produkt p = pg.opretProdukt(fustage.hentNavn() + "(" + stoerelseNy + "L)",
                        Integer.parseInt(textFieldAntal.getText()));
                pl.tilfoejProdukt(p, Double.parseDouble(textFieldPris.getText()));

                ordre.opretOrdrelinje(getIntFraTF(textFieldAntal), p, pl);
            } else {
                Prisliste pl = Controller.hentPrislisteFraNavn("Butik");
                ordre.opretOrdrelinje(getIntFraTF(textFieldAntal), fustage, pl);
            }
        } else {
            ordre.opretOrdrelinje(getIntFraTF(textFieldAntal),
                    comboBoxType.getSelectionModel().getSelectedItem(), prisliste);
        }
        this.hide();
    }

    private int getIntFraTF(TextField tf) {
        return Integer.parseInt(tf.getText());
    }

    private void antalPlusKnapMetod() {
        int tal = Integer.parseInt(textFieldAntal.getText());
        tal++;

        textFieldAntal.setText("" + tal);
        if (comboBoxType.getSelectionModel().getSelectedItem() instanceof PantProdukt) {
            PantProdukt pp = (PantProdukt) comboBoxType.getSelectionModel().getSelectedItem();
            textFieldPris.setText("" + Controller.udregnFustagePris(
                    comboBoxStoerelse.getSelectionModel().getSelectedItem(), pp
            ) * tal);
        }
    }

    private void antalMinusKnapMetod() {
        int tal = Integer.parseInt(textFieldAntal.getText());
        tal--;
        if (tal >= 0) {
            textFieldAntal.setText("" + tal);
            if (comboBoxType.getSelectionModel().getSelectedItem() instanceof PantProdukt) {
                PantProdukt pp = (PantProdukt) comboBoxType.getSelectionModel().getSelectedItem();
                textFieldPris.setText("" + Controller.udregnFustagePris(
                        comboBoxStoerelse.getSelectionModel().getSelectedItem(), pp
                ) * tal);
            }
        } else {
            textFieldAntal.setText("0");
            textFieldPris.setText("0.0");
        }
    }
}
