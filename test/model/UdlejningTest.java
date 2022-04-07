package model;

import controller.Controller;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UdlejningTest {

    @Test
    @Order(1)
    void constructor_med_tilfoej() {
        // Arrange
        LocalDate dato = LocalDate.of(2022, 3, 29);
        int id = 1;
        LocalDate startDato = LocalDate.of(2022, 3, 29);
        LocalDate slutDato = LocalDate.of(2022, 3, 29);
        String kundeNavn = "Kurt";
        String kundeTlfNr = "88888888";
        LocalDate kundeFoedselsdag = LocalDate.of(2004, 3, 29);
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
    @Order(2)
    void totalPrisMedPant_fustage() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        Prisliste pl = Controller.hentPrislisteFraNavn("Butik");
        Produkt p1 = Controller.hentProduktFraNavn("fustage", "Klosterbryg, 20 liter");
        u1.opretOrdrelinje(1, p1, pl);

        double forventet = 975;

        // Act
        double pris = u1.totalPrisMedPant();

        // Assert
        assertEquals(forventet, pris);
    }

    @Test
    @Order(3)
    void totalPrisMedPant_kulsyre() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);

        Controller.initStorage();
        Prisliste pl = Controller.hentPrislisteFraNavn("Butik");
        Produkt p1 = Controller.hentProduktFraNavn("Kulsyre", "6 kg");
        u1.opretOrdrelinje(2, p1, pl);
        double forventet = 2800;

        // Act
        double pris = u1.totalPrisMedPant();

        // Assert
        assertEquals(forventet, pris);
    }

    @Test
    @Order(4)
    void totalPrisMedPant_hane() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        Prisliste pl = Controller.hentPrislisteFraNavn("Butik");
        Produkt p1 = Controller.hentProduktFraNavn("Anlæg", "2-haner");
        u1.opretOrdrelinje(1, p1, pl);
        double forventet = 400;

        // Act
        double pris = u1.totalPrisMedPant();

        // Assert
        assertEquals(forventet, pris);
    }

    @Test
    @Order(5)
    void totalPrisMedPant_samlet() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        Prisliste pl = Controller.hentPrislisteFraNavn("Butik");
        Produkt p1 = Controller.hentProduktFraNavn("fustage", "Klosterbryg, 20 liter");
        Produkt p2 = Controller.hentProduktFraNavn("fustage", "Jazz Classic, 25 liter");
        Produkt p3 = Controller.hentProduktFraNavn("Kulsyre", "6 kg");
        Produkt p4 = Controller.hentProduktFraNavn("Anlæg", "2-haner");
        u1.tilfoejLevering();
        u1.opretOrdrelinje(2, p1, pl);
        u1.opretOrdrelinje(1, p2, pl);
        u1.opretOrdrelinje(2, p3, pl);
        u1.opretOrdrelinje(1, p4, pl);
        double forventet = 6475;

        // Act
        double pris = u1.totalPrisMedPant();

        // Assert
        assertEquals(forventet, pris);
    }

    @Test
    @Order(6)
    void tilfoejStartDato_kasterFejl() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 30);
        LocalDate startDato1 = LocalDate.of(2022, 3, 29);
        LocalDate startDato2 = LocalDate.of(2022, 5, 1);
        u1.tilfoejSlutDato(slutDato);

        // Act & Assert
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejStartDato(startDato1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejStartDato(startDato2));

        assertTrue(exception1.getMessage().contains("StatDato kan ikke være før i dag"));
        assertTrue(exception2.getMessage().contains("StartDato kan ikke være efter slutDato"));
    }

    @Test
    @Order(7)
    void tilfoejSlutDato_kasterFejl() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        LocalDate startDato = LocalDate.of(2022, 4, 30);
        LocalDate slutDato1 = LocalDate.of(2022, 4, 29);
        LocalDate slutDato2 = LocalDate.of(2022, 3, 29);
        u1.tilfoejStartDato(startDato);

        // Act & Assert
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejSlutDato(slutDato1));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejSlutDato(slutDato2));

        assertTrue(exception1.getMessage().contains("StartDato kan ikke være efter slutDato"));
        assertTrue(exception2.getMessage().contains("SlutDato kan ikke være før i dag"));
    }

    @Test
    @Order(8)
    void tilfoejKundeFoedselsdag_kasterFejl() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        LocalDate foedselsdag = LocalDate.of(2004, 5, 1);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> u1.tilfoejKundeFoedselsdag(foedselsdag));
        assertTrue(exception.getMessage().contains("Kunden skal være fyldt 18 år"));

    }

    @Test
    @Order(9)
    void tilfoejLevering() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();

        // Act
        u1.tilfoejLevering();

        // Assert
        assertTrue(u1.harLevering());
    }

    @Test
    @Order(10)
    void tilfoejLevering_KasterFejlPrisliste() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
        Controller.fjernPrisliste(prisliste);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, u1::tilfoejLevering);
        assertTrue(exception.getMessage().contains("Der er ikke oprettet prislisten \"Butik\""));
    }

    @Test
    @Order(11)
    void tilfoejLevering_KasterFejlProduktGruppe() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        ProduktGruppe produktGruppe = null;
        for (ProduktGruppe pg : Controller.hentProduktGrupper()) {
            if (pg.hentNavn().equals("Anlæg"))
                produktGruppe = pg;
        }
        Controller.fjernProduktGruppe(produktGruppe);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, u1::tilfoejLevering);
        assertTrue(exception.getMessage().contains("Der er ikke oprettet produktgruppe \"Anlæg\""));
    }

    @Test
    @Order(12)
    void tilfoejLevering_KasterFejlProdukt() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        ProduktGruppe produktGruppe = null;
        for (ProduktGruppe pg : Controller.hentProduktGrupper()) {
            if (pg.hentNavn().equals("Anlæg"))
                produktGruppe = pg;
        }
        if (produktGruppe != null)
            produktGruppe.fjernProdukt(Controller.hentProduktFraNavn("Anlæg", "Levering"));


        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, u1::tilfoejLevering);
        assertTrue(exception.getMessage().contains("Der er ikke oprettet produktet \"Levering\""));
    }

    @Test
    @Order(13)
    void fjernLevering() {
        // Arrange
        Udlejning u1 = new Udlejning(LocalDate.of(2022, 3, 30), 1);
        Controller.initStorage();
        u1.tilfoejLevering();
        u1.tilfoejAdresse("Vej 3");

        // Act
        u1.fjernLevering();

        // Assert
        assertFalse(u1.harLevering());
        assertEquals("", u1.hentAdresse());
    }

    @Test
    @Order(14)
    void erAfregnet() {

        // Arrange
        Udlejning udlejning = Controller.opretUdlejning();
        boolean forventet = false;

        // Act
        boolean aktuel = udlejning.erAfregnet();

        // Assert
        assertEquals(forventet, aktuel);
    }

    @Test
    @Order(15)
    void opretReturFustage() {

        // Arrange
        Udlejning udlejning = Controller.opretUdlejning();
        int antal = 1;
        PantProdukt pp = new PantProdukt("Test", 100, 25);
        Prisliste prisliste = Controller.opretPrisliste("Test Prisliste", Valuta.DKK);
        prisliste.tilfoejProdukt(pp, 775);

        // Act
        Ordrelinje ordrelinje = udlejning.opretReturFustage(antal, pp, prisliste);

        // Assert
        assertEquals(ordrelinje.hentProdukt(), pp);
        assertEquals(ordrelinje.hentAntal(), antal);
        assertEquals(ordrelinje.hentPrisliste(), prisliste);
        assertTrue(udlejning.hentReturFustager().contains(ordrelinje));
    }

    @Test
    @Order(16)
    void fjernReturFustage() {

        // Arrange
        Udlejning udlejning = Controller.opretUdlejning();
        int antal = 3;
        PantProdukt pantProdukt = new PantProdukt("Test", 100, 25);
        Prisliste prisliste = Controller.opretPrisliste("Test Prisliste", Valuta.DKK);
        prisliste.tilfoejProdukt(pantProdukt, 100);
        Ordrelinje ordrelinje = udlejning.opretReturFustage(antal, pantProdukt, prisliste);

        // Act
        udlejning.fjernReturFustage(ordrelinje);

        // Assert
        assertFalse(udlejning.hentReturFustager().contains(ordrelinje));
    }

    @Test
    @Order(17)
    void afregn_anlaegOk_kulsyreNul_returFustagerNul() {

        // Arrange
        Controller.initStorage();
        boolean anlaegOk = true;
        int fustagePant = 13;
        int kulsyrePant = 0;
        Udlejning udlejning = Controller.opretUdlejning();
        ProduktGruppe produktGruppe = Controller.opretProduktGruppe("Test Produktgruppe");
        PantProdukt pantProdukt = produktGruppe.opretPantProdukt("Test Fustage", 100, 25);
        Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
        prisliste.tilfoejProdukt(pantProdukt, 775);
        udlejning.opretOrdrelinje(14, pantProdukt, prisliste);
        double forventet = 2600;

        // Act
        double aktuel = udlejning.afregn(anlaegOk, fustagePant, kulsyrePant);

        // Assert
        assertEquals(forventet, aktuel);
        assertTrue(udlejning.erAfregnet());
    }

    @Test
    @Order(18)
    void afregn_anlaegOK_fustagePantNul_returFustagerNul() {

        // Arrange
        Controller.initStorage();
        boolean anlaegOk = true;
        int fustagePant = 0;
        int kulsyrePant = 10;
        Udlejning udlejning = Controller.opretUdlejning();
        ProduktGruppe produktGruppe = Controller.opretProduktGruppe("Test Produktgruppe");
        PantProdukt pantProdukt = produktGruppe.opretPantProdukt("Test Kulsyre", 100, 8);
        Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
        prisliste.tilfoejProdukt(pantProdukt, 800);
        udlejning.opretOrdrelinje(10, pantProdukt, prisliste);
        double forventet = 10000;

        // Act
        double aktuel = udlejning.afregn(anlaegOk, fustagePant, kulsyrePant);

        // Assert
        assertEquals(forventet, aktuel);
        assertTrue(udlejning.erAfregnet());
    }

    @Test
    @Order(19)
    void afregn_anlaegOK_kulsyrePantNul() {

        // Arrange
        Controller.initStorage();
        boolean anlaegOk = true;
        int fustagePant = 3;
        int kulsyrePant = 0;
        Udlejning udlejning = Controller.opretUdlejning();
        ProduktGruppe produktGruppe = Controller.opretProduktGruppe("Test Produktgruppe");
        PantProdukt fustage = produktGruppe.opretPantProdukt("Test fustage", 100, 25);
        Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
        prisliste.tilfoejProdukt(fustage, 500);
        udlejning.opretOrdrelinje(5, fustage, prisliste);

        udlejning.opretReturFustage(3, fustage, prisliste);

        double forventet = 1950;

        // Act
        double aktuel = udlejning.afregn(anlaegOk, fustagePant, kulsyrePant);

        // Assert
        assertEquals(forventet, aktuel);
        assertTrue(udlejning.erAfregnet());
    }

    @Test
    @Order(20)
    void afregn_anlaegOk() {

        // Arrange
        Controller.initStorage();
        boolean anlaegOk = true;
        int fustagePant = 0;
        int kulsyrePant = 0;
        Udlejning udlejning = Controller.opretUdlejning();
        Produkt produkt = Controller.hentProduktFraNavn("Anlæg", "1-hane");
        Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
        udlejning.opretOrdrelinje(1, produkt, prisliste);
        double forventet = 0;

        // Act
        double aktuel = udlejning.afregn(anlaegOk, fustagePant, kulsyrePant);

        // Assert
        assertEquals(forventet, aktuel);
        assertEquals(produkt.hentAntalPaaLager(), 100);
        assertTrue(udlejning.erAfregnet());
    }

    @Test
    @Order(21)
    void afregn_anlaegIkkeOk() {

        // Arrange
        Controller.initStorage();
        boolean anlaegOk = false;
        int fustagePant = 0;
        int kulsyrePant = 0;
        Udlejning udlejning = Controller.opretUdlejning();
        Produkt produkt = Controller.hentProduktFraNavn("Anlæg", "1-hane");
        Prisliste prisliste = Controller.hentPrislisteFraNavn("Butik");
        udlejning.opretOrdrelinje(2, produkt, prisliste);
        double forventet = 0;

        // Act
        double aktuel = udlejning.afregn(anlaegOk, fustagePant, kulsyrePant);

        // Assert
        assertEquals(forventet, aktuel);
        assertEquals(produkt.hentAntalPaaLager(), 98);
        assertTrue(udlejning.erAfregnet());
    }

    @Test
    @Order(22)
    void afregn_kasterFejl_fustagePant() {

        // Arrange
        boolean anlaegOk = true;
        int fustagePant = -1;
        int kulsyrePant = 0;
        Udlejning udlejning = Controller.opretUdlejning();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> udlejning.afregn(anlaegOk, fustagePant, kulsyrePant));
        assertTrue(exception.getMessage().contains("Du kan ikke returnere et negativt antal (Fustager)"));
    }

    @Test
    @Order(23)
    void afregn_kasterFejl_kulsyrePant() {

        // Arrange
        boolean anlaegOk = true;
        int fustagePant = 0;
        int kulsyrePant = -1;
        Udlejning udlejning = Controller.opretUdlejning();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> udlejning.afregn(anlaegOk, fustagePant, kulsyrePant));
        assertTrue(exception.getMessage().contains("Du kan ikke returnere et negativt antal (Kulsyre)"));
    }

    @Test
    @Order(24)
    void afregn_kasterFejl_forkertPrisliste() {

        // Arrange
        Storage.hentInstans().rydStorage();
        boolean anlaegOk = true;
        int fustagePant = 1;
        int kulsyrePant = 0;
        Udlejning udlejning = Controller.opretUdlejning();
        Prisliste prisliste = Controller.opretPrisliste("Forkert prisliste", Valuta.DKK);
        ProduktGruppe produktGruppe = Controller.opretProduktGruppe("Test produtkgruppe");
        Produkt produkt = produktGruppe.opretProdukt("Test Produkt", 100);
        prisliste.tilfoejProdukt(produkt, 100);
        udlejning.opretOrdrelinje(1, produkt, prisliste);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> udlejning.afregn(anlaegOk, fustagePant, kulsyrePant));
        assertTrue(exception.getMessage().contains("Prislisten findes ikke"));

    }
}