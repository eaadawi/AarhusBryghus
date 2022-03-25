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
import model.ProduktGruppe;


public class ProduktGruppeOpret extends Stage {

    private final ProduktGruppe produktGruppe;// OBS :nullable

    //text field oprettes
    private TextField textFieldNavnPG = new TextField();

    /**
     * Note: Nullable param produkt gruppe.
     */
    public ProduktGruppeOpret(String title, ProduktGruppe produktGruppe) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.produktGruppe = produktGruppe;
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

        //labelNavn tilfoejes til pane
        pane.add(labelNavn, 0, 0);

        //textfield tilfoejes til pane
        pane.add(textFieldNavnPG, 1, 0);

        //knap opret oprettes
        Button opretPG = new Button();
        opretPG.setText("Opret");

        //aktion til opret knappe tilfoejes
        opretPG.setOnAction(event -> this.opretProduktGruppe());

        //knap tilfoejes til pane
        pane.add(opretPG, 0, 1);

        //knap cancell oprettes
        Button cancellButton = new Button();
        cancellButton.setText("Cancell");

        //knap cancell tilfoejes til pane
        pane.add(cancellButton,1,1);

        //knap cancel action tilfoejes at gemme vinduet
        cancellButton.setOnAction(event -> this.hide());
    }

    /**
     * Metode opretter produkt gruppe
     * med at tage string fra textFieldNavnPG som navn
     */
    private void opretProduktGruppe(){
        Controller.opretProduktGruppe(textFieldNavnPG.getText());

        //textFieldNavnPG fjerne text
        textFieldNavnPG.clear();

        //gemme opretVinduet
        this.hide();
    }
}
