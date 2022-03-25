package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProduktTest {
    @Test
    void constructor() {
        // Arrange
        String navn = "Øl";
        int antal = 10;

        // Act
        Produkt produkt = new Produkt(navn, antal);

        // Assert
        assertEquals(navn, produkt.hentNavn());
        assertEquals(antal, produkt.hentAntalPaaLager());
    }

    @Test
    void constructor_kasterFejl() {
        // Arrange
        String navn = "Øl";
        int antal = -1;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Produkt(navn, antal));
        assertTrue(exception.getMessage().contains("Antal må ikke være negativt"));
    }

    @Test
    void hentProduktGruppe() {
        // Arrange
        String navn = "Øl";
        int antal = 10;
        Produkt produkt = new Produkt(navn, antal);
        ProduktGruppe forventetProduktGruppe = new ProduktGruppe("Flaskeøl");
        produkt.produktGruppe = forventetProduktGruppe;

        // Act
        ProduktGruppe produktGruppe = produkt.hentProduktGruppe();

        // Assert
        assertEquals(forventetProduktGruppe, produktGruppe);
    }

    @Test
    void tilfoejAntalPaaLager() {
        // Arrange
        String navn = "Øl";
        int antal = 10;
        Produkt produkt = new Produkt(navn, antal);
        int forventet = 17;

        // Act
        produkt.tilfoejAntalPaaLager(7);

        // Assert
        assertEquals(forventet, produkt.hentAntalPaaLager());
    }

    @Test
    void fjernAntalPaaLager() {
        // Arrange
        String navn = "Øl";
        int antal = 10;
        Produkt produkt = new Produkt(navn, antal);
        int forventet = 3;

        // Act
        produkt.fjernAntalPaaLager(7);

        // Assert
        assertEquals(forventet, produkt.hentAntalPaaLager());
    }

    @Test
    void fjernAntalPaaLager_kasterFejl() {
        // Arrange
        String navn = "Øl";
        int antal = 10;
        Produkt produkt = new Produkt(navn, antal);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> produkt.fjernAntalPaaLager(11));
        assertTrue(exception.getMessage().contains("Der er kun " + antal + " tilbage på lager"));
    }
}