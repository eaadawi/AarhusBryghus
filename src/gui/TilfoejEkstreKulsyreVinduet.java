package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Ordre;
import model.PantProdukt;
import model.Prisliste;
import storage.Storage;

public class TilfoejEkstreKulsyreVinduet extends Stage {

    private Ordre ordre;
    private Prisliste prisliste;

    private TextField textFieldStoerelse = new TextField();
    private TextField textFieldAntal = new TextField();
    private TextField textFieldPris = new TextField();

    public TilfoejEkstreKulsyreVinduet(String title,Ordre ordre,Prisliste prisliste) {
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
        Label labelStoerelse = new Label();
        labelStoerelse.setText("Stoerelse");
        pane.add(labelStoerelse,0 , 0);
        //Label labelAntal
        Label labelAntal = new Label();
        labelAntal.setText("Antal");
        pane.add(labelAntal,0 ,1 );
        //Label labelPris
        Label labelPris = new Label();
        labelPris.setText("Pris");
        pane.add(labelPris,0 ,2 );
        //---------------------TEXTFIELDS---------------------------------

        //TextField textFieldStoerelse
        pane.add(textFieldStoerelse,1 ,0 );

        //TextField textFieldAntal
        pane.add(textFieldAntal, 1,1 );

        //TextField textFieldPris
        pane.add(textFieldPris,1 ,2 );

        //
        Button buttonTilfoej = new Button();
        buttonTilfoej.setText("Tilfoej");
        buttonTilfoej.setOnAction(event -> this.tilfoejEkstraKulsyreKnap());
        pane.add(buttonTilfoej,1 ,3 );
    }

    private void tilfoejEkstraKulsyreKnap(){
        PantProdukt pp = new PantProdukt("Kulsyre", textFieldAntal.getText(),textFieldStoerelse.getText());
        ordre.opretOrdrelinje(Integer.parseInt(textFieldAntal.getText()),pp, 1);

    }
}
