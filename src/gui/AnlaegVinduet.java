package gui;

import controller.Controller;
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
import model.Produkt;

import java.util.List;

public class AnlaegVinduet extends Stage {

    private Ordre ordre;

    private ComboBox<Produkt> produktComboBox = new ComboBox<>();
    private TextField textField1 = new TextField();
    private TextField textField2 = new TextField();

    public AnlaegVinduet(String title, Ordre ordre){
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(true);
        //
        this.setTitle(title);
        this.ordre = ordre;
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

        //-------------------- ComboBox --------------------
        //produktComboBox
        //TODO items
        hentProdukterAnlaeg(produktComboBox);
        pane.add(produktComboBox, 0, 0);
        //-------------------- TextField --------------------
        textField1.setEditable(false);
        pane.add(textField1, 0, 1);
        pane.add(textField2, 0, 2);
        //-------------------- Button --------------------
        //
        Button button = new Button("Tilfoej");
        button.setOnAction(event -> this.buttonKnapMetod());
        pane.add(button, 1, 3);
        //-------------------- Label --------------------
        //
        Label label1 = new Label("Hvis flere haner");
        pane.add(label1, 1, 1);
        Label label2 = new Label("Antal");
        pane.add(label2, 1, 2);

    }

    private void buttonKnapMetod(){
        ordre.opretOrdrelinje(Integer.parseInt(textField2.getText()),
                produktComboBox.getSelectionModel().getSelectedItem(),Controller.hentPrislisteFraNavn("Butik"));
        this.hide();

//        ordre.opretOrdrelinje(Integer.parseInt(textField2.getText()),produktComboBox.getSelectionModel().getSelectedItem(),Controller.hentPrislisteFraNavn("Butik"));
    }

    private void hentProdukterAnlaeg(ComboBox<Produkt> comboBox){
        List<Produkt> produkts = Controller.hentProdukterFraGruppenavn("Anlæg");
        produkts.removeIf(p -> p.hentNavn().equals("Levering") || p.hentNavn().equals("Krus"));
        produktComboBox.getItems().setAll(produkts);
    }
}
