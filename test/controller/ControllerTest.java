package controller;


import static org.junit.jupiter.api.Assertions.*;

import model.Prisliste;
import model.Produkt;
import model.ProduktGruppe;
import model.Valuta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import storage.Storage;

import java.util.HashSet;
import java.util.Set;

class ControllerTest {

    @Test
    @Order(1)
    void opretProduktgruppeTest(){

        // Arrange
        String navn = "Produktgruppe Test";

        // Act
        ProduktGruppe pg = Controller.opretProduktGruppe(navn);

        // Assert
        assertTrue(Storage.hentInstans().hentProduktGrupper().contains(pg));

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
        assertFalse(Storage.hentInstans().hentProduktGrupper().contains(pg));

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

    @Test
    @Order(5)
    void hentProduktGrupperTest() {

        // Arrange
        String navn = "Produktgruppe Test";
        ProduktGruppe pg = Controller.opretProduktGruppe(navn);

        // Act
        Set<ProduktGruppe> pgSet = Controller.hentProduktGrupper();

        // Assert
        assertTrue(pgSet.contains(pg));

    }

    @Test
    @Order(6)
    void opretPrislisteTest() {

        // Arrange
        String navn = "Test prisliste";
        Valuta valuta = Valuta.DKK;

        // Act
        Prisliste pl = Controller.opretPrisliste(navn, valuta);

        // Assert
        assertEquals(pl.hentNavn(), navn);
        assertEquals(pl.hentValuta(), valuta);
        assertTrue(Storage.hentInstans().hentPrislister().contains(pl));
    }

    @Test
    @Order(7)
    void fjernPrislisteTest() {

        // Arrange
        String navn = "Prisliste Test";
        Valuta valuta = Valuta.DKK;
        Prisliste pl = Controller.opretPrisliste(navn, valuta);

        // Act
        Controller.fjernPrisliste(pl);

        // Assert
        assertFalse(Storage.hentInstans().hentPrislister().contains(pl));
    }

    @Test
    @Order(8)
    void hentPrislisterTest() {

        // Arrange
        String navn = "Prisliste test";
        Valuta valuta = Valuta.KLIP;
        Prisliste pl = Controller.opretPrisliste(navn, valuta);

        // Act
        Set<Prisliste> plSet = Controller.hentPrislister();

        // Assert
        assertEquals(pl.hentNavn(), navn);
        assertEquals(pl.hentValuta(), valuta);
        assertTrue(plSet.contains(pl));
    }
}
