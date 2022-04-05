package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Ordre;

public class StatistikTab extends GridPane {

    private ListView<Ordre> listViewOrdre = new ListView();
    private ListView<Ordre> listViewOrdreKlip = new ListView();
    private TextField textField1 = new TextField();
    private TextField textField2 = new TextField();
    private DatePicker datePickerDato = new DatePicker();
    private DatePicker datePickerStartDato = new DatePicker();
    private DatePicker datePickerSlutDato = new DatePicker();


    public StatistikTab(){
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
        Label labelStartDato = new Label(String.format("%10s","Start dato"));

        //
        Label labelSlutDato = new Label(String.format("%10s","Slut dato"));

        //
        Label labelSFK = new Label("Samlet forbrugte klip");
        this.add(labelSFK, 2, 3);

        //-----------------------DatePicker--------------

        //

        datePickerDato.valueProperty().addListener((observable, oldValue, newValue) -> this.listenerDPL(datePickerDato));
        this.add(datePickerDato, 1, 1);
        //

        //

        datePickerStartDato.valueProperty().addListener((observable, oldValue, newValue) -> this.listenerDPR(datePickerStartDato,datePickerSlutDato));
        datePickerSlutDato.valueProperty().addListener((observable, oldValue, newValue) -> this.listenerDPR(datePickerStartDato,datePickerSlutDato));

        //-----------------------ListView--------------

        //
        listViewOrdre.setPrefSize(200, 200);
        this.add(listViewOrdre, 0, 2);

        //
        listViewOrdreKlip.setPrefSize(200, 200);
        this.add(listViewOrdreKlip, 2, 2);

        //-----------------------TextField--------------

        //TextField textField1
        this.add(textField1, 1,3);

        //TextField textField2
        this.add(textField2, 3,3);

        //-----------------------HBox------------------

        //
        HBox hBox1 = new HBox(labelStartDato,datePickerStartDato);
        hBox1.setSpacing(10);
        HBox hBox2 = new HBox(labelSlutDato,datePickerSlutDato);
        hBox2.setSpacing(10);
        VBox vBox = new VBox(hBox1,hBox2);
        vBox.setSpacing(10);
        this.add(vBox, 2, 1,2,1);

        //
        HBox hBox0 = new HBox(labelDato,datePickerDato);
        hBox0.setSpacing(10);
        this.add(hBox0, 0, 1,2,1);

        //
        HBox hBox0a = new HBox(labelSamletOmsaetning,textField1);
        hBox0a.setSpacing(10);
        this.add(hBox0a, 0 , 3,2,1);
        //
        HBox hBox1c = new HBox(labelSFK,textField2);
        hBox1c.setSpacing(10);
        this.add(hBox1c, 2 , 3,2,1);
    }

    private void listenerDPL(DatePicker dp){
        if(dp.getValue()!=null){
            //TODO items
            listViewOrdre.getItems().setAll(Controller.hentOrdreDato(datePickerDato.getValue()));

            //TODO textField1 opdatering

        }
    }

    private void listenerDPR(DatePicker dp1,DatePicker dp2){
        if (dp1.getValue()!=null && dp2.getValue()!=null){
            //TODO items
            listViewOrdreKlip.getItems().setAll(Controller.hentOrdrePeriode(datePickerStartDato.getValue(), datePickerSlutDato.getValue()));

            //TODO textField2 opdatering
        }
    }
}
