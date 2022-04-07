package storage;

import controller.Controller;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    private StorageI testStorage;

    @BeforeEach
    void init() {
        testStorage = Storage.hentTestStorage();
    }


    @Test
    @Order(1)
    void tilfoejProduktGruppe() {
        // Arrange
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
        Prisliste pl = new Prisliste("Prisliste test", Valuta.KLIP);
        testStorage.tilfoejPrisliste(pl);

        // Act
        testStorage.fjernPrisliste(pl);

        // Assert
        assertFalse(testStorage.hentPrislister().contains(pl));
    }

    @Test
    @Order(5)
    void tilfoejOrdreTest() {

        // Arrange
        Ordre ordre = new Ordre(LocalDate.of(2022, 3, 28), 1);

        // Act
        testStorage.tilfoejOrdre(ordre);


        // Assert
        assertTrue(testStorage.hentOrdrer().contains(ordre));
    }

    @Test
    @Order(6)
    void fjernOrdreTest() {

        // Arrange
        Ordre ordre = new Ordre(LocalDate.of(2022, 3, 28), 1);
        testStorage.tilfoejOrdre(ordre);

        // Act
        testStorage.fjernOrdre(ordre);

        // Assert
        assertFalse(testStorage.hentOrdrer().contains(ordre));
    }

    @Test
    @Order(6)
    void rydTest() {

        // Arrange
        Controller.initStorage();

        // Act
        Storage.hentInstans().rydStorage();

        // Assert
        assertTrue(Storage.hentInstans().hentProduktGrupper().isEmpty());
        assertTrue(Storage.hentInstans().hentPrislister().isEmpty());
        assertTrue(Storage.hentInstans().hentOrdrer().isEmpty());
    }

    @Test
    @Order(7)
    void tilfoejKlippekort() {
        // Arrange
        Klippekort klippekort = new Klippekort(1, "Bo Ibsen");

        // Act
        testStorage.tilfoejKlippekort(klippekort);

        // Assert
        assertTrue(testStorage.hentKlippekort().contains(klippekort));
    }

    @Test
    @Order(8)
    void fjernKlippekort() {
        // Arrange
        Klippekort klippekort = new Klippekort(1, "Bo Ibsen");
        testStorage.tilfoejKlippekort(klippekort);

        // Act
        testStorage.fjernKlippekort(klippekort);

        // Assert
        assertFalse(testStorage.hentKlippekort().contains(klippekort));
    }

}