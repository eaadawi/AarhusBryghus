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
        assertTrue(Storage.getTestStorage().hentProduktGrupper().contains(pg));

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
        assertTrue(pg.hentProdukter().contains(p));
    }

    @Test
    @Order(3)
    void fjernProduktgruppeTest() {

        // Arrange
        String navn = "Produktgruppe test";
        ProduktGruppe pg = Controller.opretProduktGruppe(navn);

        // Act
        Controller.fjernProduktGruppe(pg);

        // Assert
        assertFalse(Storage.getTestStorage().hentProduktGrupper().contains(pg));

    }

    @Test
    @Order(4)
    void fjernProduktTest() {

        // Arrange
        String produktNavn = "Test øl";
        String produktgruppeNavn = "Test gruppe";
        int antal = 100;
        ProduktGruppe pg = Controller.opretProduktGruppe(produktgruppeNavn);
        Produkt p = Controller.opretProdukt(produktNavn, antal, pg);

        // Act
        Controller.fjernProdukt(p, pg);

        // Assert
        assertFalse(pg.hentProdukter().contains(p));
    }
}
