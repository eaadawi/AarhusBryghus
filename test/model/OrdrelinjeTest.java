package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdrelinjeTest {

    @Test
    void constructor() {
        // Arrange
        int antal = 10;
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.DKK);

        // Act
        Ordrelinje ordrelinje = new Ordrelinje(antal, produkt, prisliste);

        // Assert
        assertEquals(antal, ordrelinje.hentAntal());
        assertEquals(produkt, ordrelinje.hentProdukt());
        assertEquals(prisliste, ordrelinje.hentPrisliste());
    }

    @Test
    void constructor_kasterFejl() {
        // Arrange
        int antal = -1;
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.DKK);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Ordrelinje(antal, produkt, prisliste));
        assertTrue(exception.getMessage().contains("Antal må ikke være negativt"));
    }

    @Test
    void samletPris() {
        // Arrange
        int antal = 10;
        Produkt produkt = new Produkt("Øl", 10);
        Prisliste prisliste = new Prisliste("Bar", Valuta.DKK);
        prisliste.tilfoejProdukt(produkt, 15.87);
        Ordrelinje ordrelinje = new Ordrelinje(antal, produkt, prisliste);
        double forventet = 158.7;

        // Act
        double samletPris = ordrelinje.samletPris();

        // Assert
        assertEquals(forventet, samletPris);
    }
}