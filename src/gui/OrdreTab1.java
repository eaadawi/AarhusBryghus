//package gui;
//
//import controller.Controller;
//import javafx.beans.value.ChangeListener;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import model.*;
//
//public class OrdreTab1 extends GridPane {
//    private Ordre ordre;
//    private Prisliste prisliste;
//    private Button buttonTilfoejAnlaeg = new Button("Tilføj anlaeg");
//    private Button buttonTilfoejFustage = new Button("Tilføj fustage");
//    private Button buttonTilfoejKulsyre = new Button("Tilføj kulsyre");
//    private Button buttonTilfoejKrus = new Button("Tilføj krus");
//    private Button buttonFjernOL = new Button("Fjern linje");
//
//    private ListView<Ordrelinje> ordrelinjeListView = new ListView<>();
//
//    public OrdreTab1() {
//        this.setPadding(new Insets(20));
//        this.setHgap(20);
//        this.setVgap(10);
//        this.setGridLinesVisible(false);
//        //
//
//        prisliste = Controller.hentPrislisteFraNavn("Butik");
//        //-------------- Button ------------
//        //Button buttonTilfoejAnlaeg
//        buttonTilfoejAnlaeg.setOnAction(event -> this.buttonTilfoejAnlaegKnapMetod());
//
//        //Button buttonTilfoejFustage
//        buttonTilfoejFustage.setOnAction(event -> this.buttonTilfoejFustageKnapMetod());
//
//        //Button buttonTilfoejKulsyre
//        buttonTilfoejKulsyre.setOnAction(event -> this.buttonTilfoejKulsyreKnapMetod());
//
//        //Button buttonTilfoejKrus
//        buttonTilfoejKrus.setOnAction(event -> this.buttonTilfoejKrusKnapMetod());
//
//        //Button buttonFjernOL
//        buttonFjernOL.setOnAction(event -> this.buttonFjernOLKnapMetod(ordrelinjeListView.getSelectionModel().getSelectedItem()));
//
//        //-------------- LABEL ------------
//        //
//        Label labelOrderlinje = new Label("Orderlinje/Produkter");
//        this.add(labelOrderlinje, 1, 0);
//
//        //-------------- LIST_VIEW ------------
//        //TODO items
//        ordrelinjeListView.getItems().setAll();
//
//        //-------------- VBOX ------------
//        //
//        VBox vBox1 = new VBox(buttonTilfoejAnlaeg,buttonTilfoejFustage,buttonTilfoejKulsyre,buttonTilfoejKrus);
//        vBox1.setSpacing(10);
//        this.add(vBox1, 0, 1);
//        //
//        VBox vBox2 = new VBox(ordrelinjeListView,buttonFjernOL);
//        this.add(vBox2, 1, 1);
//
//
//    }
//
//    private void buttonTilfoejAnlaegKnapMetod(){
//        //TODO items
//        AnlaegVinduet dialog = new AnlaegVinduet("Tilføj anlæg",ordre);
//        dialog.showAndWait();
//
//        opdaterListView();
//    }
//
//    private void buttonTilfoejFustageKnapMetod(){
//        TilfoejFustageVinduet dialog = new TilfoejFustageVinduet("Tilføj fustage",ordre,prisliste);
//        dialog.showAndWait();
//
//        opdaterListView();
//    }
//
//    private void buttonTilfoejKulsyreKnapMetod(){
//        TilfoejEkstreKulsyreVinduet2 dialog = new TilfoejEkstreKulsyreVinduet2("Tilføj kulsyre",ordre,prisliste);
//        dialog.showAndWait();
//
//        opdaterListView();
//    }
//
//    private void buttonTilfoejKrusKnapMetod(){
//        TilfoejEkstraKrus dialog = new TilfoejEkstraKrus("Tilføj ekstra krus", ordre, prisliste);
//        dialog.showAndWait();
//
//        opdaterListView();
//    }
//
//    private void buttonFjernOLKnapMetod(Ordrelinje ol){
//        ordre.fjernOrdrelinje(ordrelinjeListView.getSelectionModel().getSelectedItem());
//        opdaterListView();
//    }
//
//    private void opdaterListView(){
//        ordrelinjeListView.getItems().setAll(ordre.hentOrdrelinjer());
//    }
//
//}
//
////class TilfoejEkstraKrus extends Stage{
////
////    private Ordre ordre;
////    private Prisliste prisliste;
////    private TextField textFieldAntal = new TextField();
////
////    public TilfoejEkstraKrus(String title,Ordre ordre,Prisliste pl) {
////        this.initStyle(StageStyle.UTILITY);
////        this.initModality(Modality.APPLICATION_MODAL);
////        this.setResizable(false);
////
////        //
////
////        this.setTitle(title);
////        this.ordre=ordre;
////        this.prisliste = pl;
////        //
////
////        GridPane pane = new GridPane();
////        this.initContentPane(pane);
////
////        Scene scene = new Scene(pane);
////        this.setScene(scene);
////    }
////
////    private void initContentPane(GridPane pane){
////        pane.setPadding(new Insets(10));
////        pane.setHgap(10);
////        pane.setVgap(10);
////        pane.setGridLinesVisible(false);
////
////        //---------------------LABELS---------------------------------
////
////        //Label labelPrisliste
////        Label labelAntal = new Label();
////        labelAntal.setText("Antal");
////        pane.add(labelAntal, 0, 0);
////        //---------------------TEXTFIELDS---------------------------------
////        //
////        pane.add(textFieldAntal, 1, 0);
////        //-------------------- Button ------------------------------
////        //
////        Button buttonTilfoej = new Button("Tilfoej");
////        buttonTilfoej.setOnAction(event -> this.buttonTilfoejKnapMetod());
////    }
////
////    private void buttonTilfoejKnapMetod(){
////        ordre.opretOrdrelinje(Integer.parseInt(textFieldAntal.getText()), Controller.hentProduktFraNavn("Anlæg", "Krus"), prisliste);
////        this.hide();
////    }
////}
