package storage;

import model.ProduktGruppe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void tiljoejProduktGruppe() {
        // Arrange
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");

        // Act
        Storage.hentTestStorage().tiljoejProduktGruppe(produktGruppe);

        // Assert
        assertTrue(Storage.hentTestStorage().hentProduktGrupper().contains(produktGruppe));
    }

    @Test
    void fjernjProduktGruppe() {
        // Arrange
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");
        Storage.hentTestStorage().tiljoejProduktGruppe(produktGruppe);

        // Act
        Storage.hentTestStorage().fjernjProduktGruppe(produktGruppe);

        // Assert
        assertFalse(Storage.hentTestStorage().hentProduktGrupper().contains(produktGruppe));
    }

}