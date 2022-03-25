package controller;

import model.Produkt;
import model.ProduktGruppe;
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
     * Henter set af produktGruppe fra storage
     */

    public static Set<ProduktGruppe> hentProduktGrupper(){
        return Storage.hentInstans().hentProduktGrupper();
    }


}
