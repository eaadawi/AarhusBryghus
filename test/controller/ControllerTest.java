package controller;


import static org.junit.jupiter.api.Assertions.*;

import model.Produkt;
import model.ProduktGruppe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import storage.Storage;

class ControllerTest {

    @Test
    @Order(1)
    void opretProduktgruppeTest(){

        // Arrange
        String navn = "Produktgruppe Test";

        // Act
        ProduktGruppe pg = Controller.opretProduktGruppe(navn);

        // Assert
        assert(Storage.getTestStorage.hentProduktGrupper().contains(pg));
    }

    @Test
    @Order(2)
    void opretProduktTest() {

        // Arrange
        String produktNavn = "Test øl";
        String produktgruppeNavn = "Test gruppe";
        int antal = 100;
        ProduktGruppe pg = Controller.opretProduktGruppe(produktgruppeNavn);

        // Act
        Produkt p = Controller.opretProdukt(produktNavn, antal, pg);

        // Assert
        assertEquals(p.hentNavn(), produktNavn);
        assertEquals(p.hentAntalPaaLager(), antal);
        assert(pg.hentProdukter().contains(p));
    }

    @Test
    @Order(3)
    void fjernProduktgruppeTest() {

        // Arrange
        String navn = "Produktgruppe test";

        // Act
        ProduktGruppe pg = Controller.opretProduktGruppe(navn);
        Controller.fjernProduktGruppe(pg);

        // Assert

    }
}
