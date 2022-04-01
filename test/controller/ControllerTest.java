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
        assertEquals(navn, pg.hentNavn());
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
        String navn = "Ikke eksisterende";
        Controller.initStorage();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.hentPrislisteFraNavn(navn));
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

    @Test
    @Order(17)
    void opretUdlejningTest() {

        // Arrange
        Controller.initStorage();

        // Act
        Udlejning udlejning = Controller.opretUdlejning();

        // Assert
        assertTrue(Storage.hentInstans().hentOrdrer().contains(udlejning));
        assertEquals(udlejning.hentDato(), LocalDate.now());
        assertEquals(udlejning.hentId(), Storage.hentInstans().hentOrdrer().size());
    }

    @Test
    @Order(18)
    void opretKlippekortTest() {

        // Arrange
        String kundeNavn = "Anders Andersen";

        // Act
        Klippekort klippekort = Controller.opretKlippekort(kundeNavn);

        // Assert
        assertEquals(klippekort.hentId(), Storage.hentInstans().hentKlippekort().size());
        assertEquals(klippekort.hentKundeNavn(), kundeNavn);
        assertTrue(Storage.hentInstans().hentKlippekort().contains(klippekort));
    }

    @Test
    @Order(19)
    void fjernKlippekortTest() {

        // Arrange
        String kundeNavn = "Anders Andersen";
        Klippekort klippekort = Controller.opretKlippekort(kundeNavn);

        // Act
        Controller.fjernKlippekort(klippekort);

        // Assert
        assertFalse(Storage.hentInstans().hentKlippekort().contains(klippekort));
    }

    @Test
    @Order(20)
    void hentKlippekortTest() {

        // Arrange
        String kundeNavn = "Anders Andersen";
        Klippekort klippekort = Controller.opretKlippekort(kundeNavn);

        // Act
        Set<Klippekort> klippekortSet = Controller.hentKlippekort();

        // Assert
        assertEquals(klippekort.hentId(), Storage.hentInstans().hentKlippekort().size());
        assertEquals(klippekort.hentKundeNavn(), kundeNavn);
        assertTrue(klippekortSet.contains(klippekort));
    }

    @Test
    @Order(21)
    void hentOrdreAfTypeTest_o() {

        // Arrange
        String type = "o";
        Ordre ordre = Controller.opretOrdre();
        Udlejning udlejning = Controller.opretUdlejning();

        // Act
        Set<Ordre> ordreSet = Controller.hentOdreAfType(type);

        // Assert
        assertTrue(ordreSet.contains(ordre));
        assertFalse(ordreSet.contains(udlejning));
    }

    @Test
    @Order(22)
    void hentOrdreAfTypeTest_u() {

        // Arrange
        String type = "u";
        Udlejning udlejning = Controller.opretUdlejning();
        Ordre ordre = Controller.opretOrdre();

        // Act
        Set<Ordre> ordreSet = Controller.hentOdreAfType(type);

        // Assert
        assertTrue(ordreSet.contains(udlejning));
        assertFalse(ordreSet.contains(ordre));
    }

    @Test
    @Order(23)
    void hentOrdreAfTypeTest_kasterFejl() {

        // Arrange
        String type = "p";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.hentOdreAfType(type));
        assertTrue(exception.getMessage().contains("Type skal være o eller u"));
    }

    @Test
    @Order(23)
    void soegKlipppekort() {

        // Arrange
        String navn1 = "Anders Andersen";
        String navn2 = "Hans Hansen";
        String input = "Anders Andersen";
        Klippekort klippekort1 = Controller.opretKlippekort(navn1);
        Klippekort klippekort2 = Controller.opretKlippekort(navn1);
        Klippekort klippekort3 = Controller.opretKlippekort(navn2);

        // Act
        Set<Klippekort> klippekortSet = Controller.soegKlippekort(input);

        // Assert
        assertTrue(klippekortSet.contains(klippekort1));
        assertTrue(klippekortSet.contains(klippekort2));
        assertFalse(klippekortSet.contains(klippekort3));

    }
}
