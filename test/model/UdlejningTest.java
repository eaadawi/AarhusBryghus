package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UdlejningTest {

    @Test
    void constructor_med_tilfoej() {
        // Arrange
        LocalDate dato = LocalDate.of(2022,3,29);
        int id = 1;
        LocalDate startDato = LocalDate.of(2022,3,29);
        LocalDate slutDato = LocalDate.of(2022,3,29);
        String kundeNavn = "Kurt";
        String kundeTlfNr = "88888888";
        LocalDate kundeFoedselsdag = LocalDate.of(2004,3,29);
        String adresse = "vej 3 8210";

        // Act
        Udlejning u = new Udlejning(dato, id);
        u.tilfoejStartDato(startDato);
        u.tilfoejSlutDato(slutDato);
        u.tilfoejKundeNavn(kundeNavn);
        u.tilfoejKundeTlfNr(kundeTlfNr);
        u.tilfoejKundeFoedselsdag(kundeFoedselsdag);
        u.tilfoejAdresse(adresse);

        // Assert
        assertEquals(dato, u.hentDato());
        assertEquals(id, u.hentId());
        assertEquals(startDato, u.hentStartDato());
        assertEquals(slutDato, u.hentSlutDato());
        assertEquals(kundeNavn, u.hentKundeNavn());
        assertEquals(kundeTlfNr, u.hentKundeTlfNr());
        assertEquals(kundeFoedselsdag, u.hentKundeFoedselsdag());
        assertEquals(adresse, u.hentAdresse());
    }

    @Test
    void totalPrisMedPant() {
    }

    @Test
    void tilfoejStartDato_kasterFejl() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        LocalDate slutDato = LocalDate.of(2022,4,30);
        LocalDate startDato1 = LocalDate.of(2022,3,29);
        LocalDate startDato2 = LocalDate.of(2022,5,1);
        u1.tilfoejSlutDato(slutDato);

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejStartDato(startDato1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejStartDato(startDato2));

        // Assert
        assertTrue(exception1.getMessage().contains("StatDato kan ikke være før i dag"));
        assertTrue(exception2.getMessage().contains("StartDato kan ikke være efter slutDato"));

    }

    @Test
    void tilfoejSlutDato_kasterFejl() {
    }

    @Test
    void tilfoejKundeFoedselsdag_kasterFejl() {
    }

    @Test
    void tilfoejLevering() {

    }

    @Test
    void fjernLevering() {

    }

}