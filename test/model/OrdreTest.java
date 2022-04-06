package model;

import controller.Controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrdreTest {

    @Test
    void constructor() {
        // Arrange
        int id = 1;
        LocalDate dato = LocalDate.of(2022, 4, 28);

        // Act
        Ordre ordre = new Ordre(dato, id);

        // Assert
        assertFalse(ordre.harBetaltMedKlip());
        assertEquals(dato, ordre.hentDato());
        assertEquals(id, ordre.hentId());
    }

    @Test
    void totalPris() {
        // Arrange
        int id = 1;
        LocalDate dato = LocalDate.of(2022, 4, 28);
        Ordre ordre = new Ordre(dato, id);
        Produkt produkt = new Produkt("Øl", 100);

        Prisliste prisliste = new Prisliste("Bar", Valuta.DKK);
        prisliste.tilfoejProdukt(produkt, 15.87);
        ordre.opretOrdrelinje(10, produkt, prisliste);
        ordre.opretOrdrelinje(20, produkt, prisliste);

        Prisliste prislisteKlip = new Prisliste("Bar", Valuta.KLIP);
        prislisteKlip.tilfoejProdukt(produkt, 5);
        ordre.opretOrdrelinje(10, produkt, prislisteKlip);

        double forventet = 476.1;

        // Act
        double totalPris = ordre.totalPris();

        // Assert
        assertEquals(forventet, totalPris);
    }

    @Test
    void klipPris() {
        // Arrange
        int id = 1;
        LocalDate dato = LocalDate.of(2022, 4, 28);
        Ordre ordre = new Ordre(dato, id);
        Produkt produkt = new Produkt("Øl", 100);

        Prisliste prisliste = new Prisliste("Bar", Valuta.KLIP);
        prisliste.tilfoejProdukt(produkt, 2);
        ordre.opretOrdrelinje(10, produkt, prisliste);
        ordre.opretOrdrelinje(20, produkt, prisliste);

        Prisliste prislisteDKK = new Prisliste("Bar", Valuta.DKK);
        prislisteDKK.tilfoejProdukt(produkt, 5.5);
        ordre.opretOrdrelinje(10, produkt, prislisteDKK);

        double forventet = 60;

        // Act
        double klipPris = ordre.klipPris();

        // Assert
        assertEquals(forventet, klipPris);
    }

    @Test
    void tilfoejBetalingsmetode() {
        // Arrange
        int id = 1;
        LocalDate dato = LocalDate.of(2022, 4, 28);
        Ordre ordre = new Ordre(dato, id);
        Betalinsgmetode betalinsgmetode = Betalinsgmetode.MOBILPAY;

        // Act
        ordre.tilfoejBetalingsmetode(betalinsgmetode);

        // Assert
        assertEquals(betalinsgmetode, ordre.hentBetalinsgmetode());

    }

    @Test
    void opretOrdrelinje() {
        // Arrange
        int id = 1;
        LocalDate dato = LocalDate.of(2022, 4, 28);
        Ordre ordre = new Ordre(dato, id);
        int antal = 10;
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.DKK);
        prisliste.tilfoejProdukt(produkt, 20);

        // Act
        Ordrelinje ordrelinje = ordre.opretOrdrelinje(antal, produkt, prisliste);

        // Assert
        assertTrue(ordre.hentOrdrelinjer().contains(ordrelinje));
        assertEquals(0, produkt.hentAntalPaaLager());
    }

    @Test
    void fjernOrdrelinje() {
        // Arrange
        int id = 1;
        LocalDate dato = LocalDate.of(2022, 4, 28);
        Ordre ordre = new Ordre(dato, id);
        int antal = 10;
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.DKK);
        prisliste.tilfoejProdukt(produkt, 20);
        Ordrelinje ordrelinje = ordre.opretOrdrelinje(antal, produkt, prisliste);

        // Act
        ordre.fjernOrdrelinje(ordrelinje);

        // Assert
        assertFalse(ordre.hentOrdrelinjer().contains(ordrelinje));
        assertEquals(10, produkt.hentAntalPaaLager());
    }

    @Test
    void tilfoejKlippekort() {

        // Arrange
        String kundeNavn = "Anders Andersen";
        Klippekort klippekort = Controller.opretKlippekort(kundeNavn);
        Ordre ordre = Controller.opretOrdre();

        // Act
        ordre.tilfoejKlippekort(klippekort);

        // Assert
        assertTrue(ordre.hentKlippekort().contains(klippekort));
    }

    @Test
    void fjernKlippekort() {

        // Arrange
        String kundeNavn = "Anders Andersen";
        Ordre ordre = Controller.opretOrdre();
        Klippekort klippekort = Controller.opretKlippekort(kundeNavn);
        ordre.tilfoejKlippekort(klippekort);

        // Act
        ordre.fjernKlippekort(klippekort);

        // Assert
        assertFalse(ordre.hentKlippekort().contains(klippekort));
    }

    @Test
    void betalMedKlippekort() {

        // Arrange
        Ordre ordre = new Ordre(LocalDate.of(2022, 4, 28), 1);
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.KLIP);
        prisliste.tilfoejProdukt(produkt, 4);
        ordre.opretOrdrelinje(1, produkt, prisliste);
        Klippekort.aendreAntalKlip(4);
        Klippekort klippekort1 = new Klippekort(1, "Bo Ibsen");
        Klippekort klippekort2 = new Klippekort(2, "Bo Ibsen");
        ordre.tilfoejKlippekort(klippekort1);
        ordre.tilfoejKlippekort(klippekort2);

        // Act
        ordre.betalMedKlippekort();

        // Assert
        assertTrue(ordre.harBetaltMedKlip());
    }

    @Test
    void betalMedKlippekort_kasterFejl() {
        // Arrange
        Ordre ordre = new Ordre(LocalDate.of(2022, 4, 28), 1);
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.KLIP);
        prisliste.tilfoejProdukt(produkt, 5);
        ordre.opretOrdrelinje(2, produkt, prisliste);
        Klippekort.aendreAntalKlip(9);
        Klippekort klippekort = new Klippekort(1, "Bo Ibsen");
        ordre.tilfoejKlippekort(klippekort);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, ordre::betalMedKlippekort);
        assertTrue(exception.getMessage().contains("Der er ikke nok klip"));
    }

    @Test
    void tilfoejTilTotalPris() {
        // Arrange
        Ordre ordre = new Ordre(LocalDate.now(), 1);
        double beloeb = 7.95;

        // Act
        ordre.TilfoejTilTotalPris(beloeb);

        // Assert
        assertEquals(beloeb, ordre.totalPris());
    }
}