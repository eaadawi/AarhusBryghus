package storage;

import model.Produkt;
import model.ProduktGruppe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void tiljoejProduktGruppe() {
        // Arrange
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");

        // Act
        Storage.getTestStorage().tiljoejProduktGruppe(produktGruppe);

        // Assert
        assertTrue(Storage.getTestStorage().hentProduktGrupper().contains(produktGruppe));
    }

    @Test
    void fjernjProduktGruppe() {
        // Arrange
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");
        Storage.getTestStorage().tiljoejProduktGruppe(produktGruppe);

        // Act
        Storage.getTestStorage().fjernjProduktGruppe(produktGruppe);

        // Assert
        assertFalse(Storage.getTestStorage().hentProduktGrupper().contains(produktGruppe));
    }

}