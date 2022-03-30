package model;

import controller.Controller;
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

        // Act & Assert
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejStartDato(startDato1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejStartDato(startDato2));

        assertTrue(exception1.getMessage().contains("StatDato kan ikke være før i dag"));
        assertTrue(exception2.getMessage().contains("StartDato kan ikke være efter slutDato"));
    }

    @Test
    void tilfoejSlutDato_kasterFejl() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        LocalDate startDato = LocalDate.of(2022,4,30);
        LocalDate slutDato1 = LocalDate.of(2022,4,29);
        LocalDate slutDato2 = LocalDate.of(2022,3,29);
        u1.tilfoejStartDato(startDato);

        // Act & Assert
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejSlutDato(slutDato1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejSlutDato(slutDato2));

        assertTrue(exception1.getMessage().contains("StartDato kan ikke være efter slutDato"));
        assertTrue(exception2.getMessage().contains("SlutDato kan ikke være før i dag"));
    }

    @Test
    void tilfoejKundeFoedselsdag_kasterFejl() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        LocalDate foedselsdag = LocalDate.of(2004,5,1);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejKundeFoedselsdag(foedselsdag));
        assertTrue(exception.getMessage().contains("Kunden skal være fyldt 18 år"));

    }

    @Test
    void tilfoejLevering() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        Controller.initStorage();

        // Act
        u1.tilfoejLevering();

        // Assert
        assertTrue(u1.harLevering());
    }

    @Test
    void tilfoejLevering_KasterFejlPrisliste() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        Controller.initStorage();
        Prisliste prisliste = null;
        for(Prisliste pl : Controller.hentPrislister()) {
            if(pl.hentNavn().equals("Butik"))
                prisliste = pl;
        }
        Controller.fjernPrisliste(prisliste);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejLevering());
        assertTrue(exception.getMessage().contains("Der er ikke oprettet prislisten \"Butik\""));
    }

    @Test
    void tilfoejLevering_KasterFejlProduktGruppe() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        Controller.initStorage();
        ProduktGruppe produktGruppe = null;
        for(ProduktGruppe pg : Controller.hentProduktGrupper()) {
            if(pg.hentNavn().equals("Anlæg"))
                produktGruppe = pg;
        }
        Controller.fjernProduktGruppe(produktGruppe);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejLevering());
        assertTrue(exception.getMessage().contains("Der er ikke oprettet produktgruppe \"Anlæg\""));
    }

    @Test
    void tilfoejLevering_KasterFejlProdukt() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022,3,30), 1);
        Controller.initStorage();
        Produkt produkt = null;
        for(Produkt p : Controller.hentProdukterFraGruppenavn("Anlæg")) {
            if(p.hentNavn().equals("Levering"))
                produkt = p;
        }
        Controller.fjernPrisliste(prisliste);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejLevering());
        assertTrue(exception.getMessage().contains("Der er ikke oprettet prislisten \"Butik\""));
    }

    @Test
    void fjernLevering() {

    }

}