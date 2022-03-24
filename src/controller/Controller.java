package controller;

import model.Produkt;
import model.ProduktGruppe;
import storage.Storage;

public class Controller {

    /**
     * ProduktGruppe opretter og returnere produkt med given navn og antalPåLager
     */

    public static Produkt opretProdukt(String navn, int antalPåLager, ProduktGruppe produktGruppe){
        Produkt p = produktGruppe.opretProdukt(navn,antalPåLager);
        return p;
    }

    /**
     * Opretter og returnere produktGruppe med given navn og tiljoeje den ind i storrage
     */

    public static ProduktGruppe opretProduktGruppe(String navn){
        ProduktGruppe pg = new ProduktGruppe(navn);
        Storage.tiljoejProduktGruppe(pg);
        return pg;
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
        Storage.fjernjProduktGruppe(produktGruppe);
    }
}
