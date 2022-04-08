package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Klippekort;

public class KlippekortTab extends GridPane {

    private final ListView<Klippekort> klippekortListView = new ListView<>();

    private final TextField textFieldKundenavn = new TextField();
    private final TextField textFieldAntalStartKlip = new TextField();
    private final TextField textFieldKlippekortPris = new TextField();

    public KlippekortTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //-------------------------LABEL----------------------------------
        //Label ordre
        Label labelKundenavn = new Label("Kundenavn");

        //Label label2
        Label labelKlippekort = new Label("Klippekort");
        this.add(labelKlippekort, 0, 1);

        //Label label3
        Label labelEvt = new Label("evt");

        //Label label5
        Label labelAntalStartKlip = new Label("Antal start klip");
        this.add(labelAntalStartKlip, 2, 2);

        //Label label6
        Label labelKlippekortPris = new Label("Klippekort pris");
        this.add(labelKlippekortPris, 2, 3);

        //-------------------------LISTVIEW----------------------------------
        //LISTVIEW_1
        klippekortListView.setPrefSize(200, 300);
        klippekortListView.getItems().setAll(Controller.hentKlippekort());

        //LISTVIEW_2
        //klippekortListViewValgte.
//        klippekortListViewValgte.setPrefSize(200, 300);

        //-------------------------BUTTON----------------------------------

        Button buttonSoeg = new Button("Søg");
        Button buttonTilfoejKk = new Button("Tilføj");
        Button buttonFjernKk = new Button("Fjern");

        //
        buttonSoeg.setOnAction(event -> this.buttonSoegKnapMetod());

        //Button button4
        Button buttonAfslut = new Button("Opdatere");
        buttonAfslut.setOnAction(event -> this.buttonOpdatereMetod());
        this.add(buttonAfslut, 4, 3);

//-------------------------HBox-VBox---------------------------------

        //
        HBox hBox0 = new HBox(labelKundenavn, textFieldKundenavn);
        hBox0.setSpacing(10);
        this.add(hBox0, 0, 0);

        //VBox vBox1
        VBox vBox1 = new VBox();
        vBox1.getChildren().setAll(klippekortListView);
        this.add(vBox1, 0, 2, 1, 3);

        //HBox hBox1
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(buttonTilfoejKk);
        hBox2.getChildren().add(buttonFjernKk);
        HBox.setHgrow(buttonTilfoejKk, Priority.ALWAYS);
        HBox.setHgrow(buttonFjernKk, Priority.ALWAYS);
        hBox2.setSpacing(1);

        //
        HBox hBoxSoeg = new HBox(labelEvt, buttonSoeg);
        hBoxSoeg.setSpacing(10);
        this.add(hBoxSoeg, 2, 0);

        //-------------------------TEXT_FIELD---------------------------------

        //TextField textFieldAntalStartKlip
        textFieldAntalStartKlip.setPromptText("" + Klippekort.hentAntalKlip());
        this.add(textFieldAntalStartKlip, 3, 2);
        //TextField textFieldAntalStartKlip
        textFieldKlippekortPris.setPromptText("" + Klippekort.hentKlippekortPris());
        this.add(textFieldKlippekortPris, 3, 3);


    }

    private void buttonOpdatereMetod() {
        if (!textFieldKlippekortPris.getText().isEmpty())
            Klippekort.aendreKlippekortPris(Double.parseDouble(textFieldKlippekortPris.getText()));

        if (!textFieldAntalStartKlip.getText().isEmpty())
            Klippekort.aendreAntalKlip(Integer.parseInt(textFieldAntalStartKlip.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opdatering af klippekort");
        alert.setHeaderText("Klippekorterne var opdateret");
        alert.setContentText("\nDen nye antal klip er : " + textFieldAntalStartKlip.getText() + "\nDen nye pris er : " + textFieldKlippekortPris.getText());
        alert.showAndWait();
        textFieldAntalStartKlip.clear();
        textFieldKlippekortPris.clear();
        textFieldAntalStartKlip.setPromptText("" + Klippekort.hentAntalKlip());
        textFieldKlippekortPris.setPromptText("" + Klippekort.hentKlippekortPris());
    }

    private void buttonSoegKnapMetod() {
        klippekortListView.getItems().setAll(Controller.soegKlippekort(textFieldKundenavn.getText()));
    }

}
