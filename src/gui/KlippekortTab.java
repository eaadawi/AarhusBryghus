package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class KlippekortTab extends GridPane {



    public KlippekortTab(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //-------------------------LABEL----------------------------------
        //Label ordre
        Label labelKlippekort = new Label("Klippekort");
        this.add(labelKlippekort, 0, 0);

        //Label label2
        Label label2 = new Label("label2");
        this.add(label2,0 , 1);

        //Label label3
        Label label3 = new Label("label3");
        this.add(label3, 0, 2);

        //Label label4
        Label label4 = new Label("label4");
        this.add(label4,0 , 3);

        //-------------------------BUTTON----------------------------------
        //Button button1
        Button button1 = new Button("Button1");
        this.add(button1, 1, 0);

        //Button button2
        Button button2 = new Button("Button2");
        this.add(button2,1, 1);

        //Button button3
        Button button3 = new Button("Button3");
        this.add(button3, 1, 2);

        //Button button4
        Button button4 = new Button("Button4");
        this.add(button4,1, 3);

//-------------------------Button2----------------------------------
        //Button button11
        Button button11 = new Button("Button11");
        this.add(button11, 2, 0);

        //Button button12
        Button button12 = new Button("Button12");
        this.add(button12,2, 1);

        //Button button13
        Button button13 = new Button("Button13");
        this.add(button13, 2, 2);

        //Button button14
        Button button14 = new Button("Button14");
        this.add(button14,2, 3);

    }

}
