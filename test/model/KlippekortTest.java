package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KlippekortTest {

    @Test
    void constructor() {
        // Arrange
        Klippekort.aendreAntalKlip(4);
        Klippekort.aendreKlippekortPris(130);
        int id = 1;
        String kundeNavn = "Bo Ibsen";

        // Act
        Klippekort klippekort = new Klippekort(id, kundeNavn);

        // Assert
        assertEquals(id, klippekort.hentId());
        assertEquals(kundeNavn, klippekort.hentKundeNavn());
        assertEquals(Klippekort.hentKlippekortPris(), klippekort.hentPris());
        assertEquals(Klippekort.hentAntalKlip(), klippekort.hentAntalKlipTilbage());
    }

    @Test
    void aendreKlippekortPris() {
        // Arrange
        double forventet = 107.3;

        // Act
        Klippekort.aendreKlippekortPris(forventet);

        // Assert
        assertEquals(forventet, Klippekort.hentKlippekortPris());
    }

    @Test
    void aendreAntalKlip() {
        // Arrange
        int forventet = 10;

        // Act
        Klippekort.aendreAntalKlip(forventet);

        // Assert
        assertEquals(forventet, Klippekort.hentAntalKlip());
    }

    @Test
    void fjernKlip() {
        // Arrange
        int fjern = 4;
        Klippekort.aendreAntalKlip(4);
        Klippekort klippekort = new Klippekort(1, "Bo Ibsen");
        int forventet = 0;

        // Act
        klippekort.fjernKlip(fjern);

        // Assert
        assertEquals(forventet, klippekort.hentAntalKlipTilbage());
    }

    @Test
    void fjernKlip_kasterFejl() {
        // Arrange
        int fjern = 5;
        Klippekort.aendreAntalKlip(4);
        Klippekort klippekort = new Klippekort(1, "Bo Ibsen");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> klippekort.fjernKlip(fjern));
        assertTrue(exception.getMessage().contains("Det er kun 4 klip tilbage på dette kort"));
    }
}