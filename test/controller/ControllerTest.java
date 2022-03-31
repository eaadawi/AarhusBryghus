package controller;


import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import storage.Storage;
import java.time.LocalDate;
import java.util.List;
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
    @Order(3)
    void fjernProduktTest() {

        // Arrange
        String produktNavn = "Test øl";
        String produktgruppeNavn = "Test gruppe";
        int antal = 100;
        ProduktGruppe pg = Controller.opretProduktGruppe(produktgruppeNavn);
        Produkt p = pg.opretProdukt(produktNavn, antal);

        // Act
        Controller.fjernProdukt(p, pg);

        // Assert
        assertFalse(pg.hentProdukter().contains(p));
    }

    @Test
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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

    @Test
    @Order(8)
    void opretOrdreTest() {

        // Act
        Ordre ordre = Controller.opretOrdre();

        // Assert
        assertEquals(ordre.hentDato(), LocalDate.now());
        assertEquals(ordre.hentId(), Storage.hentTestStorage().hentOrdrer().size() + 1);
        assertTrue(Storage.hentInstans().hentOrdrer().contains(ordre));
    }

    @Test
    @Order(9)
    void fjernOrdreTest() {

        // Arrange
        Ordre ordre = Controller.opretOrdre();

        // Act
        Controller.fjernOrdre(ordre);

        // Assert
        assertFalse(Storage.hentInstans().hentOrdrer().contains(ordre));
    }

    @Test
    @Order(10)
    void hentOrdrerTest() {

        // Arrange
        Ordre ordre = Controller.opretOrdre();

        // Act
        Set<Ordre> ordreSet = Controller.hentOrdrer();

        // Assert
        assertEquals(ordre.hentDato(), LocalDate.now());
        assertEquals(ordre.hentId(), Storage.hentInstans().hentOrdrer().size());
        assertTrue(ordreSet.contains(ordre));
    }

    @Test
    @Order(11)
    void hentFaellesProdukter() {

        // Arrange
        ProduktGruppe pg1 = Controller.opretProduktGruppe("Flaskeøl");
        ProduktGruppe pg2 = Controller.opretProduktGruppe("Fadøl");
        Produkt p1 = pg1.opretProdukt("Klosterbryg", 10);
        Produkt p2 = pg1.opretProdukt("Forårsbryg", 10);
        Produkt p3 = pg1.opretProdukt("Julebryg", 10);
        Produkt p4 = pg2.opretProdukt("Påskebryg", 10);
        Prisliste pl = Controller.opretPrisliste("Bar", Valuta.DKK);
        pl.tilfoejProdukt(p1, 30);
        pl.tilfoejProdukt(p3, 30);
        pl.tilfoejProdukt(p4, 30);

        // Act
        Set<Produkt> set= Controller.hentFaellesProdukter(pg1, pl);

        // Assert
        assertTrue(set.contains(p1));
        assertFalse(set.contains(p2));
        assertTrue(set.contains(p3));
        assertFalse(set.contains(p4));
    }

    @Test
    @Order(12)
    void hentProdukterFraGruppenavn() {

        // Arrange
        String pgNavn = "Test";
        ProduktGruppe produktGruppe = Controller.opretProduktGruppe(pgNavn);
        produktGruppe.opretProdukt("Produkt 1", 1);
        produktGruppe.opretProdukt("Produkt 2", 1);
        produktGruppe.opretProdukt("Produkt 3", 1);
        produktGruppe.opretProdukt("Produkt 4", 1);
        produktGruppe.opretProdukt("Produkt 5", 1);
        List<Produkt> forventet;
        forventet = produktGruppe.hentProdukter();

        // Act
        List<Produkt> aktuel = Controller.hentProdukterFraGruppenavn("Test");


        // Assert
        assertEquals(forventet, aktuel);

    }

    @Test
    @Order(13)
    void hentPrislisteFraNavn() {

        // Arrange
        String plNavn = "Test";
        Prisliste prisliste = Controller.opretPrisliste(plNavn, Valuta.DKK);
        Prisliste aktuelPrisliste;

        // Act
        aktuelPrisliste = Controller.hentPrislisteFraNavn("Test");

        // Assert
        assertEquals(prisliste, aktuelPrisliste);

    }

    @Test
    @Order(14)
    void hentPrislisteFraNavn_kasterFejl() {

        // Arrange
        Controller.initStorage();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.hentPrislisteFraNavn("Ikke eksisterende"));
        assertTrue(exception.getMessage().contains("Der findes ingen prisliste med dette navn"));
    }

    @Test
    @Order(15)
    void hentProduktFraNavn() {

        // Arrange
        String pgNavn = "Test";
        ProduktGruppe produktGruppe = Controller.opretProduktGruppe(pgNavn);
        produktGruppe.opretProdukt("Produkt 1" ,1);
        produktGruppe.opretProdukt("Produkt 2" ,1);
        produktGruppe.opretProdukt("Produkt 3" ,1);
        produktGruppe.opretProdukt("Produkt 4" ,1);
        produktGruppe.opretProdukt("Produkt 5" ,1);
        produktGruppe.hentProdukter();
        String forventetProdukt = "Produkt 3";

        // Act
        Produkt aktuel = Controller.hentProduktFraNavn("Test", forventetProdukt);


        // Assert
        assertEquals(forventetProdukt, aktuel.hentNavn());
    }

    @Test
    @Order(16)
    void hentProduktFraNavn_kasterFejl() {

        // Arrange
        Controller.opretProduktGruppe("Test");
        Controller.initStorage();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.hentProduktFraNavn("Test", "Ikke Eksisterende"));
        assertTrue(exception.getMessage().contains("Der findes ingen produkter med dette navn"));
    }


}
