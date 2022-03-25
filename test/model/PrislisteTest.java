package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrislisteTest {

    @Test
    void constructor() {
        // Arrange
        String navn = "Jul";
        Valuta valuta = Valuta.DKK;

        // Act
        Prisliste prisliste = new Prisliste(navn, valuta);

        // Assert
        assertEquals(navn, prisliste.hentNavn());
        assertEquals(valuta, prisliste.hentValuta());
    }

    @Test
    void tilfoejProdukt() {
        // Arrange
        Prisliste prisliste = new Prisliste("Jul", Valuta.DKK);
        Produkt produkt = new Produkt("Øl", 10);
        double pris = 39.95;

        // Act
        prisliste.tilfoejProdukt(produkt, pris);

        // Assert
        assertTrue(prisliste.hentProdukter().contains(produkt));
        assertEquals(pris, prisliste.hentPris(produkt));
    }

    @Test
    void fjernProdukt() {
        // Arrange
        Prisliste prisliste = new Prisliste("Jul", Valuta.DKK);
        Produkt produkt = new Produkt("Øl", 10);
        double pris = 39.95;
        prisliste.tilfoejProdukt(produkt, pris);

        // Act
        prisliste.fjernProdukt(produkt);

        // Assert
        assertFalse(prisliste.hentProdukter().contains(produkt));

    }

}