package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Ordre;

import java.util.HashSet;
import java.util.Set;

public class StatistikTab extends GridPane {

    private final ListView<Ordre> listViewOrdre = new ListView();
    private final ListView<Ordre> listViewOrdreKlip = new ListView();
    private final TextField textField1 = new TextField();
    private final TextField textField2 = new TextField();
    private final DatePicker datePickerDato = new DatePicker();
    private final DatePicker datePickerStartDato = new DatePicker();
    private final DatePicker datePickerSlutDato = new DatePicker();


    public StatistikTab() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //-----------------------Label-------------

        //
        Label labelDRS = new Label("Dagens registrerede salg");
        this.add(labelDRS, 0, 0);

        //
        Label labelDato = new Label("Dato");
        this.add(labelDato, 0, 1);

        //
        Label labelSamletOmsaetning = new Label("Samlet omsætning");
        this.add(labelSamletOmsaetning, 0, 3);

        //
        Label labelFKlip = new Label("Forbrugte klip på solgte varer i en periode");
        this.add(labelFKlip, 2, 0);

        //
        Label labelStartDato = new Label(String.format("%10s", "Start dato"));

        //
        Label labelSlutDato = new Label(String.format("%10s", "Slut dato"));

        //
        Label labelSFK = new Label("Samlet forbrugte klip");
        this.add(labelSFK, 2, 3);

        //-----------------------DatePicker--------------

        //

        datePickerDato.valueProperty().addListener((observable, oldValue, newValue) -> this.listenerDPL(datePickerDato));
        this.add(datePickerDato, 1, 1);
        //

        //

        datePickerStartDato.valueProperty().addListener((observable, oldValue, newValue) -> this.listenerDPR(datePickerStartDato, datePickerSlutDato));
        datePickerSlutDato.valueProperty().addListener((observable, oldValue, newValue) -> this.listenerDPR(datePickerStartDato, datePickerSlutDato));

        //-----------------------ListView--------------

        //
        listViewOrdre.setPrefSize(200, 200);
        this.add(listViewOrdre, 0, 2);

        //
        listViewOrdreKlip.setPrefSize(200, 200);
        this.add(listViewOrdreKlip, 2, 2);

        //-----------------------TextField--------------

        //TextField textField1
        textField1.setEditable(false);
        this.add(textField1, 1, 3);

        //TextField textField2
        textField2.setEditable(false);
        this.add(textField2, 3, 3);

        //-----------------------HBox------------------

        //
        HBox hBox1 = new HBox(labelStartDato, datePickerStartDato);
        hBox1.setSpacing(10);
        HBox hBox2 = new HBox(labelSlutDato, datePickerSlutDato);
        hBox2.setSpacing(10);
        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setSpacing(10);
        this.add(vBox, 2, 1, 2, 1);

        //
        HBox hBox0 = new HBox(labelDato, datePickerDato);
        hBox0.setSpacing(10);
        this.add(hBox0, 0, 1, 2, 1);

        //
        HBox hBox0a = new HBox(labelSamletOmsaetning, textField1);
        hBox0a.setSpacing(10);
        this.add(hBox0a, 0, 3, 2, 1);
        //
        HBox hBox1c = new HBox(labelSFK, textField2);
        hBox1c.setSpacing(10);
        this.add(hBox1c, 2, 3, 2, 1);
    }

    private void listenerDPL(DatePicker dp) {
        if (dp.getValue() != null) {
            //TODO items
            listViewOrdre.getItems().setAll(Controller.hentOrdrerDato(datePickerDato.getValue()));

            //TODO textField1 opdatering
            opdateringIkkeKlip(textField1);

        }
    }

    private void listenerDPR(DatePicker dp1, DatePicker dp2) {
        if (dp1.getValue() != null && dp2.getValue() != null) {
            //TODO items

            listViewOrdreKlip.getItems().setAll(Controller.hentOrdrePeriode(datePickerStartDato.getValue(), datePickerSlutDato.getValue()));


            //TODO textField2 opdatering
            opdateringKlip(listViewOrdreKlip, textField2);

        }
    }

    private void opdateringKlip(ListView<Ordre> ordreListView, TextField tf) {
        Set<Ordre> ordreSet = new HashSet<>(ordreListView.getItems());
        int tal = Controller.udregnSamletForbrugteKlip(ordreSet);
        tf.setText("" + tal);
    }

    private void opdateringIkkeKlip(TextField tf) {
        double tal = Controller.hentSamletOmsaetning(datePickerDato.getValue());
        tf.setText("" + tal);
    }
}
