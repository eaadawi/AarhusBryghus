package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.*;

class PantProduktTest {

    @Test
    @Order(1)
    void constructor() {

        // Arrange
        String navn = "PantProdukt Test";
        int antalPaaLager = 100;
        int stoerrelse = 25;

        // Act
        PantProdukt pantProdukt = new PantProdukt(navn, antalPaaLager, stoerrelse);

        // Assert
        assertEquals(pantProdukt.hentNavn(), navn);
        assertEquals(pantProdukt.hentAntalPaaLager(), antalPaaLager);
        assertEquals(pantProdukt.hentStoerrelse(), stoerrelse);
    }

    @Test
    @Order(2)
    void constructor_kasterFejl() {

        // Arrange
        String navn = "PantProdukt test";
        int antalPaaLager = -1;
        int stoerrelse = 25;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new PantProdukt(navn, antalPaaLager, stoerrelse));
        assertTrue(exception.getMessage().contains("Antal må ikke være negativt"));
    }

    @Test
    @Order(3)
    void hentProduktGruppe() {

        // Arrange
        String navn = "PantProdukt test";
        int antalPaaLager = 100;
        int stoerrelse = 25;
        PantProdukt pantProdukt = new PantProdukt(navn, antalPaaLager, stoerrelse);
        ProduktGruppe forventetProduktGruppe = new ProduktGruppe("Fadøl");
        pantProdukt.produktGruppe = forventetProduktGruppe;

        // Act
        ProduktGruppe produktGruppe = pantProdukt.hentProduktGruppe();

        // Assert
        assertEquals(forventetProduktGruppe, produktGruppe);
    }

    @Test
    @Order(4)
    void hentStoerrelse() {

        // Arrange
        String navn = "PantProdukt test";
        int antalPaaLager = 100;
        int forventetStoerrelse = 25;
        PantProdukt pantProdukt = new PantProdukt(navn, antalPaaLager, forventetStoerrelse);

        // Act
        int storrelse = pantProdukt.hentStoerrelse();

        // Assert
        assertEquals(storrelse, forventetStoerrelse);
    }

}
