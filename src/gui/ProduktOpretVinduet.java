package gui;

import com.sun.javafx.scene.control.FakeFocusTextField;
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
import model.Produkt;
import model.ProduktGruppe;

public class ProduktOpretVinduet extends Stage {

    private final Produkt produkt;// OBS :nullable
    private ProduktGruppe produktGruppe;

    //text fields oprettes til navn og antal
    private TextField textFieldNavnProdukt = new TextField();
    private TextField textFieldAntalProdukt = new TextField();
    /**
     * Note: Nullable param produkt gruppe.
     */
    public ProduktOpretVinduet(String title, Produkt produkt,ProduktGruppe pg) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.produkt = produkt;
        produktGruppe = pg;
        this.setTitle(title);

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

        //Label navn
        Label labelNavn = new Label();
        labelNavn.setText("Navn");

        //Label antal
        Label labelAntal = new Label();
        labelAntal.setText("Antal");


        //labelNavn og labelAntal tilfoejes til pane
        pane.add(labelNavn, 0, 0);
        pane.add(labelAntal, 0, 1);

        //textfielder tilfoejes til pane
        pane.add(textFieldNavnProdukt, 1, 0);
        pane.add(textFieldAntalProdukt, 1, 1);

        //knap opret oprettes
        Button opretPG = new Button();
        opretPG.setText("Opret");

        //aktion til opret knappe tilfoejes
        opretPG.setOnAction(event -> this.opretProdukt());

        //knap tilfoejes til pane
        pane.add(opretPG, 0, 2);

        //knap cancell oprettes
        Button cancellButton = new Button();
        cancellButton.setText("Cancell");

        //knap cancell tilfoejes til pane
        pane.add(cancellButton,1,2);

        //knap cancel action tilfoejes at gemme vinduet
        cancellButton.setOnAction(event -> this.hide());
    }


    /**
     * Metod som opretter produkt
     */
    private void opretProdukt(){
        //textfieldAntal til int
        int antal = Integer.parseInt(textFieldAntalProdukt.getText());

        // produkt oprettes
        produktGruppe.opretProdukt(textFieldNavnProdukt.getText(), antal);
//        Controller.opretProdukt(textFieldNavnProdukt.getText(), antal, produktGruppe);

        //fjerner text og lukker vinduet
        textFieldAntalProdukt.clear();
        textFieldNavnProdukt.clear();
        this.hide();
    }

}
