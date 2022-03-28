package gui;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OrdreTab extends GridPane {


    public OrdreTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //-----------------------------------------

        //Label ordre
        Label ordreLabel = new Label();
        ordreLabel.setText("Ordre");

        this.add(ordreLabel, 0, 0);
    }
}
