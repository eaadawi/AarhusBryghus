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
        LocalDate kundeFoedselsdag = LocalDate.of(2022,3,29);
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
    }

    @Test
    void totalPrisMedPant() {
    }

    @Test
    void tilfoejStartDato_kasterFejl() {
        // Arrange

        // Act

        // Assert

    }

    @Test
    void tilfoejSlutDato_kasterFejl() {
    }

    @Test
    void tilfoejKundeFoedselsdag_kasterFejl() {
    }

}