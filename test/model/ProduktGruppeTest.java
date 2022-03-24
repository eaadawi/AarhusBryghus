package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProduktGruppeTest {

    @Test
    void constructor() {
        // Arrange
        String navn = "Flasekeøl";

        // Act
        ProduktGruppe produktGruppe = new ProduktGruppe(navn);

        // Assert
        assertEquals(navn, produktGruppe.hentNavn());
    }

    @Test
    void opretProdukt() {
        // Arrange
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");
        String navn = "Øl";
        int antal = 10;
        Produkt forventetProdukt = new Produkt(navn, antal);

        // Act
        Produkt produkt = produktGruppe.opretProdukt(navn, antal);

        // Assert
        assertTrue(produktGruppe.hentProdukter().contains(produkt));
        assertEquals(forventetProdukt, produkt);
    }

    @Test
    void fjernProdukt() {
        // Arrange
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");
        String navn = "Øl";
        int antal = 10;
        Produkt produkt = produktGruppe.opretProdukt(navn, antal);

        // Act
        produktGruppe.fjernProdukt(produkt);

        // Assert
        assertFalse(produktGruppe.hentProdukter().contains(produkt));
    }
}