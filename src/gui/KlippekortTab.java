package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Klippekort;

public class KlippekortTab extends GridPane {

    private ListView<Klippekort> klippekortListView = new ListView<>();
    private ListView<Klippekort> klippekortListViewValgte = new ListView<>();

    private Button buttonOpretNyKlippekort = new Button("Opret Ny");
    private Button buttonTilfoejKk = new Button("Tilfoej");
    private Button buttonFjernKk = new Button("Fjern");

    private TextField textFieldPris = new TextField();
    private TextField textFieldKlppris = new TextField();

    public KlippekortTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(true);

        //-------------------------LABEL----------------------------------
        //Label ordre
        Label labelKundenavn = new Label("Kundenavn");
        this.add(labelKundenavn, 0, 0);

        //Label label2
        Label labelKlippekort = new Label("Klippekort");
        this.add(labelKlippekort, 0, 1);

        //Label label3
        Label labelEvtSoeg = new Label("evt soeg");
        this.add(labelEvtSoeg, 2, 0);

        //Label label4
        Label labelValgteKK = new Label("Valgte klippekort");
        this.add(labelValgteKK, 2, 1);

        //Label label5
        Label labelPris = new Label("Pris");
        this.add(labelPris, 3, 2);

        //Label label6
        Label labelKlippris = new Label("Klippris");
        this.add(labelKlippris, 3, 3);

        //-------------------------LISTVIEW----------------------------------
        //LISTVIEW_1
        klippekortListView.setPrefSize(200, 300);
        klippekortListView.getItems().setAll(Controller.hentKlippekort());

        //LISTVIEW_2
        //klippekortListViewValgte.
        klippekortListViewValgte.setPrefSize(200, 300);

        //-------------------------BUTTON----------------------------------
        //Button button1
        buttonOpretNyKlippekort.setMaxWidth(Double.MAX_VALUE);
        buttonOpretNyKlippekort.setMaxHeight(27);
        buttonOpretNyKlippekort.setOnAction(event -> this.opretNyKkMetod());


        //Button button2
        buttonTilfoejKk.setMaxWidth(Double.MAX_VALUE);
        buttonTilfoejKk.setMaxHeight(27);

        //Button button3
        buttonFjernKk.setMaxWidth(Double.MAX_VALUE);
        buttonFjernKk.setMaxHeight(27);

        //Button button4
        Button buttonAfslut = new Button("Afslut betaling");
        this.add(buttonAfslut, 4, 5);

//-------------------------HBox-VBox---------------------------------
        //VBox vBox1
        VBox vBox1 = new VBox();
        vBox1.getChildren().setAll(klippekortListView, buttonOpretNyKlippekort);
        this.add(vBox1, 0, 2, 1, 3);

        //HBox hBox1
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(buttonTilfoejKk);
        hBox2.getChildren().add(buttonFjernKk);
        HBox.setHgrow(buttonTilfoejKk, Priority.ALWAYS);
        HBox.setHgrow(buttonFjernKk, Priority.ALWAYS);
        hBox2.setSpacing(1);

        //VBox vBox2
        VBox vBox2 = new VBox();
        vBox2.getChildren().setAll(klippekortListViewValgte, hBox2);
        this.add(vBox2, 2, 2, 1, 3);


        //-------------------------TEXT_FIELD---------------------------------
        //TextField textFieldPris
        this.add(textFieldPris, 4,2 );
        //TextField textFieldPris
        this.add(textFieldKlppris, 4, 3);


    }

    private void opretNyKkMetod() {
        OpretNyKlippekortVinduet dialog = new OpretNyKlippekortVinduet("Oprettelse af et nyt klippekort");
        dialog.showAndWait();
        //henter de tidlige kort sammen med den som var lige oprettet
        klippekortListView.getItems().setAll(Controller.hentKlippekort());
    }


}
