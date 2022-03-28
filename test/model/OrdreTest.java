package model;

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
        Produkt produkt = new Produkt("Øl", 10);

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
}