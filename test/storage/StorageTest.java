package storage;

import model.Prisliste;
import model.ProduktGruppe;
import model.Valuta;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    @Order(1)
    void tilfoejProduktGruppe() {
        // Arrange
        Storage testStorage = Storage.hentTestStorage();
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");

        // Act
        testStorage.tiljoejProduktGruppe(produktGruppe);

        // Assert
        assertTrue(testStorage.hentProduktGrupper().contains(produktGruppe));
    }

    @Test
    @Order(2)
    void fjernProduktGruppe() {
        // Arrange
        Storage testStorage = Storage.hentTestStorage();
        ProduktGruppe produktGruppe = new ProduktGruppe("Flaskeøl");
        testStorage.tiljoejProduktGruppe(produktGruppe);

        // Act
        testStorage.fjernjProduktGruppe(produktGruppe);

        // Assert
        assertFalse(testStorage.hentProduktGrupper().contains(produktGruppe));
    }

    @Test
    @Order(3)
    void tilfoejPrislisteTest() {

        // Arrange
        Storage testStorage = Storage.hentTestStorage();
        Prisliste pl = new Prisliste("Prisliste test", Valuta.DKK);

        // Act
        testStorage.tilfoejPrisliste(pl);

        // Assert
        assertTrue(testStorage.hentPrislister().contains(pl));
    }

    @Test
    @Order(4)
    void fjernPrislisteTest() {

        // Arrange
        Storage testStorage = Storage.hentTestStorage();
        Prisliste pl = new Prisliste("Prisliste test", Valuta.KLIP);
        testStorage.tilfoejPrisliste(pl);

        // Act
        testStorage.fjernPrisliste(pl);

        // Assert
        assertFalse(testStorage.hentPrislister().contains(pl));
    }

}