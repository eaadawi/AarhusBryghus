package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Gui extends Application {

    @Override
    public void init() {
        Controller.initStorage();
    }



    public void start(Stage stage) {

        BorderPane pane = new BorderPane();
        this.initContent(pane);

        //oprettelse af scene
        Scene scene = new Scene(pane);
        stage.setTitle("Aarhus Bryghus");
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------------

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        //
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
   // ------------------------------------------------------------------------------------
        Tab produktGruppeTab = new Tab("ProduktGruppe");
        tabPane.getTabs().add(produktGruppeTab);

        ProduktGruppeTab pgTab = new ProduktGruppeTab();
        produktGruppeTab.setContent(pgTab);
        // ------------------------------------------------------------------------------------
        Tab tabTilmeldingsblanket = new Tab("Tilmeldingsblanket");
        tabPane.getTabs().add(tabTilmeldingsblanket);

//        Tilmeldingsblanket tilmeldingsblanketPane = new Tilmeldingsblanket();
//        tabTilmeldingsblanket.setContent(tilmeldingsblanketPane);
//        // ------------------------------------------------------------------------------------
//        Tab tabDeltageroversigt = new Tab("Deltageroversigt");
//        tabPane.getTabs().add(tabDeltageroversigt);
//
//        Deltageroversigt deltageroversigtPane = new Deltageroversigt();
//        tabDeltageroversigt.setContent(deltageroversigtPane);
////        tabDeltageroversigt.setOnSelectionChanged(event -> deltageroversigtPane.updateControls());
//        // ------------------------------------------------------------------------------------
//        Tab tabUdflugtsoversigt = new Tab("Udflugtsoversigt");
//        tabPane.getTabs().add(tabUdflugtsoversigt);
//
//        Udflugtsoversigt udflugtsoversigtPane = new Udflugtsoversigt();
//        tabUdflugtsoversigt.setContent(udflugtsoversigtPane);
//        // ------------------------------------------------------------------------------------
//        Tab tabHoteloversigt = new Tab("Hoteloversigt");
//        tabPane.getTabs().add(tabHoteloversigt);
//
//        Hoteloversigt hoteloversigtPane = new Hoteloversigt();
//        tabHoteloversigt.setContent(hoteloversigtPane);
//        // ------------------------------------------------------------------------------------
    }
}
