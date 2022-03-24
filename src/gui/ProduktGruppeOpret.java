package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ProduktGruppe;

public class ProduktGruppeOpret extends Stage {

    private final ProduktGruppe produktGruppe;// OBS :nullable

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

        //text field oprettes
        TextField textFieldNavnPG = new TextField();

        //
        pane.add(textFieldNavnPG, 1, 0);



    }
}
