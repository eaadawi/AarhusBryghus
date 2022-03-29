package gui;


import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Ordre;

public class OrdreTab extends GridPane {
    private ListView<Ordre> ordreListView = new ListView<>();
    private ListView<Ordre> udlejningListView = new ListView<>()
    private Ordre ordre;
    public OrdreTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(true);
        //-----------------------------------------

        //Label ordre
        Label ordreLabel = new Label();
        ordreLabel.setText("Ordre");

        this.add(ordreLabel, 0, 0);

        //listView
        ordreListView.setPrefSize(200, 300);
        this.add(ordreListView, 0, 1, 2, 2);

        //Button opretOrder knap
        Button opretOrder = new Button();
        opretOrder.setText("Opret order");
        int size = 90;
        opretOrder.setPrefSize(size, size);
        opretOrder.setOnAction(event -> this.opretOrder());


        //VBox
        VBox vBox = new VBox();
        vBox.getChildren().add(opretOrder);
        vBox.setAlignment(Pos.BOTTOM_CENTER);//pos bottom i vbox
        this.add(vBox, 3, 2);

    }

    private void opretOrder() {
        ordre = Controller.opretOrdre();
        OpretOrderVinduet dialog = new OpretOrderVinduet("Opret ordre vinduet", ordre);
        dialog.showAndWait();
        //
        //
        ordreListView.getItems().setAll(Controller.hentOrdre());
    }

}
