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

import java.util.Random;

public class OrdreTab extends GridPane {
    private ListView<Ordre> ordreListView = new ListView<>();
    private ListView<Ordre> udlejningListView = new ListView<>();
    private Ordre ordre;
    private Button opretUdlejning = new Button();

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
        ordreListView.setPrefSize(x, x*1.5);
        this.add(ordreListView, 0, 1, 2, 2);

        //listView udlejningListView
        udlejningListView.setPrefSize(x, x*1.5);

        this.add(udlejningListView, 4, 1, 2, 2);

        //-------------------------BUTTON-------------------------------
        //Button opretOrder knap
        Button opretOrder = new Button();
        opretOrder.setText("Opret order");
        int size = 90;
        opretOrder.setPrefSize(size, size);
        opretOrder.setOnAction(event -> this.opretOrder());

        //Button opretUdlejning knap

        opretUdlejning.setPrefSize(size,size);

        opretUdlejning.setText("Ny Udlejning");
        this.add(opretUdlejning, 6, 1);
        opretUdlejning.setOnAction(event -> this.nyUdlejning());


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

    private void nyUdlejning(){
        OpretNyUdlejningVinduet dialog = new OpretNyUdlejningVinduet("Nyyyy Udlejning");
        dialog.showAndWait();
        //
        Random r = new Random();
        int a = r.nextInt(256);
        int b = r.nextInt(256);
        int c = r.nextInt(256);
        opretUdlejning.setStyle("-fx-background-color: rgb("+a+","+b+","+c+");");
        //
        udlejningListView.getItems().setAll(Controller.hentOdreAfType("u"));
    }

}
