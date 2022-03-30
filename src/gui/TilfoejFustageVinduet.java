package gui;

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

public class TilfoejFustageVinduet extends Stage {


    private Ordre ordre;

    public TilfoejFustageVinduet(String title, Ordre ordre) {
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

        //---------------------COL1-------------------------------

        //Label fustage
        Label labelFustage = new Label();
        labelFustage.setText("Fustage");
        pane.add(labelFustage, 0, 0);
        ////////////-----------mangler type og items
        ComboBox<Integer> comboBoxType = new ComboBox<>();
        pane.add(comboBoxType,0 , 1);
        //
        Label labelAntal = new Label();
        labelAntal.setText("Antal");
        pane.add(labelAntal,0, 2 );
        //
        Label labelStoerelse = new Label();
        labelStoerelse.setText("Stoerelse");
        pane.add(labelStoerelse,0 ,3 );
        //
        Label labelPris = new Label();
        labelPris.setText("Pris");
        pane.add(labelPris,0 ,4 );
        //--------------------------COL2---------------------

        //
        TextField textFieldAntal = new TextField();
        pane.add(textFieldAntal,1 ,2 );
        //
        TextField textFieldStoerelse = new TextField();
        pane.add(textFieldStoerelse,1 ,3 );
        //
        TextField textFieldPris = new TextField();
        pane.add(textFieldPris,1 ,4 );
        //--------------------------COL3---------------------

        //
        Button buttonTilfoe = new Button();
        buttonTilfoe.setText("Tilfoej");
        pane.add(buttonTilfoe,2 ,5 );
    }

}
