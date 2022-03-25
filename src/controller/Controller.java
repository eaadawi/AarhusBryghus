package controller;

import model.*;
import storage.Storage;

import java.util.Set;

public class Controller {

    /**
     * ProduktGruppe opretter og returnere produkt med given navn og antalPåLager
     */

    public static Produkt opretProdukt(String navn, int antalPaaLager, ProduktGruppe produktGruppe){
        return produktGruppe.opretProdukt(navn,antalPaaLager);

    }

    /**
     * Opretter og returnere produktGruppe med given navn og tiljoeje den ind i storrage
     */

    public static ProduktGruppe opretProduktGruppe(String navn){
        ProduktGruppe pg = new ProduktGruppe(navn);
        Storage.hentInstans().tiljoejProduktGruppe(pg);
        return pg;
    }

    /**
     * Opretter og retunere en prisliste med givent navn og tilføjer den til Storage
     */
    public static Prisliste opretPrisliste(String navn, Valuta valuta) {
        Prisliste pl = new Prisliste(navn, valuta);
        Storage.hentInstans().tilfoejPrisliste(pl);
        return pl;
    }

    /**
     * Fjerner produkt fra produktGruppe
     */
    public static void fjernProdukt(Produkt produkt, ProduktGruppe produktGruppe){
        produktGruppe.fjernProdukt(produkt);
    }

    /**
     * Fjerner produktGruppe fra Storage
     */
    public static void fjernProduktGruppe(ProduktGruppe produktGruppe){
        Storage.hentInstans().fjernjProduktGruppe(produktGruppe);
    }

    /**
     * Fjerner prisliste fra Storage
     */
    public static void fjernPrisliste(Prisliste prisliste) {
        Storage.hentInstans().fjernPrisliste(prisliste);
    }

    /**
     * Henter set af produktGruppe fra storage
     */
    public static Set<ProduktGruppe> hentProduktGrupper(){
        return Storage.hentInstans().hentProduktGrupper();
    }

    /**
     * Henter set af prisliste fra Storage
     */
    public static Set<Prisliste> hentPrislister() {
        return Storage.hentInstans().hentPrislister();
    }


    public static void initStorage() {

        // Produktgrupper
        ProduktGruppe produktGruppe1 = Controller.opretProduktGruppe("flaske");
        ProduktGruppe produktGruppe2 = Controller.opretProduktGruppe("fadøl, 40cl");
        ProduktGruppe produktGruppe3 = Controller.opretProduktGruppe("spiritus");
        ProduktGruppe produktGruppe4 = Controller.opretProduktGruppe("fustage");
        ProduktGruppe produktGruppe5 = Controller.opretProduktGruppe("Kulsyre");
        ProduktGruppe produktGruppe6 = Controller.opretProduktGruppe("Malt");
        ProduktGruppe produktGruppe7 = Controller.opretProduktGruppe("Beklædning");
        ProduktGruppe produktGruppe8 = Controller.opretProduktGruppe("Anlæg");
        ProduktGruppe produktGruppe9 = Controller.opretProduktGruppe("Glas");
        ProduktGruppe produktGruppe10 = Controller.opretProduktGruppe("sampakninger");

        // Produkter - gruppe1
        produktGruppe1.opretProdukt("Klosterbryg", 100);

        // Produkter - gruppe2

        // Produkter - gruppe3

        // Produkter - gruppe4

        // Produkter - gruppe5

        // Produkter - gruppe6

        // Produkter - gruppe7

        // Produkter - gruppe8

        // Produkter - gruppe9

        // Produkter - gruppe10
    }

}
