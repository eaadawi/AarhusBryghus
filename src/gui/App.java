package gui;

import javafx.application.Application;
import storage.Storage;

public class App {

    public static void main(String[] args) {
        Application.launch(Gui.class);

        System.out.println(Storage.hentInstans().hentProduktGrupper().size());
    }

}
