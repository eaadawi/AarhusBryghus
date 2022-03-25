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

        // PRODUKTGRUPPER
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
        ProduktGruppe produktGruppe11 = Controller.opretProduktGruppe("Rundvisning");

        // Produkter - gruppe1
        Produkt p1g1 = produktGruppe1.opretProdukt("Klosterbryg", 100);
        Produkt p2g1 = produktGruppe1.opretProdukt("Sweet Georgia Brown", 100);
        Produkt p3g1 = produktGruppe1.opretProdukt("Extra Pilsner", 100);
        Produkt p4g1 = produktGruppe1.opretProdukt("Celebration", 100);
        Produkt p5g1 = produktGruppe1.opretProdukt("Blondie", 100);
        Produkt p6g1 = produktGruppe1.opretProdukt("Forårsbryg", 100);
        Produkt p7g1 = produktGruppe1.opretProdukt("India Pale Ale", 100);
        Produkt p8g1 = produktGruppe1.opretProdukt("Julebryg", 100);
        Produkt p9g1 = produktGruppe1.opretProdukt("Juletønden", 100);
        Produkt p10g1 = produktGruppe1.opretProdukt("Old Strong Ale", 100);
        Produkt p11g1 = produktGruppe1.opretProdukt("Fregatten Jylland", 100);
        Produkt p12g1 = produktGruppe1.opretProdukt("Impreial Stout", 100);
        Produkt p13g1 = produktGruppe1.opretProdukt("Tribute", 100);
        Produkt p14g1 = produktGruppe1.opretProdukt("Black Monster", 100);

        // Produkter - gruppe2
        Produkt p1g2 = produktGruppe2.opretProdukt("Klosterbryg", 100);
        Produkt p2g2 = produktGruppe2.opretProdukt("Jazz Classic", 100);
        Produkt p3g2 = produktGruppe2.opretProdukt("Extra Pilsner", 100);
        Produkt p4g2 = produktGruppe2.opretProdukt("Celebration", 100);
        Produkt p5g2 = produktGruppe2.opretProdukt("Blondie", 100);
        Produkt p6g2 = produktGruppe2.opretProdukt("Forårsbryg", 100);
        Produkt p7g2 = produktGruppe2.opretProdukt("India Pale Ale", 100);
        Produkt p8g2 = produktGruppe2.opretProdukt("Julebryg", 100);
        Produkt p9g2 = produktGruppe2.opretProdukt("Imperial Stout", 100);
        Produkt p10g2 = produktGruppe2.opretProdukt("Special", 100);
        Produkt p11g2 = produktGruppe2.opretProdukt("Æblebrus", 100);
        Produkt p12g2 = produktGruppe2.opretProdukt("chips", 100);
        Produkt p13g2 = produktGruppe2.opretProdukt("peanuts", 100);
        Produkt p14g2 = produktGruppe2.opretProdukt("cola", 100);
        Produkt p15g2 = produktGruppe2.opretProdukt("Nikoline", 100);
        Produkt p16g2 = produktGruppe2.opretProdukt("7-Up", 100);
        Produkt p17g2 = produktGruppe2.opretProdukt("vand", 100);
        Produkt p18g2 = produktGruppe2.opretProdukt("Ølpølser", 100);

        // Produkter - gruppe3
        Produkt p1g3 = produktGruppe3.opretProdukt("Whisky 45% 50cl rør", 100);
        Produkt p2g3 = produktGruppe3.opretProdukt("Whisky 4cl", 100);
        Produkt p3g3 = produktGruppe3.opretProdukt("Whisky 43% 50cl rør", 100);
        Produkt p4g3 = produktGruppe3.opretProdukt("u/ egesplint", 100);
        Produkt p5g3 = produktGruppe3.opretProdukt("m/ egesplint", 100);
        Produkt p6g3 = produktGruppe3.opretProdukt("Liquor of Aarhus", 100);
        Produkt p7g3 = produktGruppe3.opretProdukt("Lyng gin 50 cl", 100);
        Produkt p8g3 = produktGruppe3.opretProdukt("Lyng gin 4 cl", 100);

        // Produkter - gruppe4
        Produkt p1g4 = produktGruppe4.opretProdukt("Klosterbryg, 20 liter", 100);
        Produkt p2g4 = produktGruppe4.opretProdukt("Jazz Classic, 25 liter", 100);
        Produkt p3g4 = produktGruppe4.opretProdukt("Extra Pilsner, 25 liter", 100);
        Produkt p4g4 = produktGruppe4.opretProdukt("Celebration, 20 liter", 100);
        Produkt p5g4 = produktGruppe4.opretProdukt("Blondie, 25 liter", 100);
        Produkt p6g4 = produktGruppe4.opretProdukt("Forårsbryg, 20 liter", 100);
        Produkt p7g4 = produktGruppe4.opretProdukt("India Pale Ale, 20 liter", 100);
        Produkt p8g4 = produktGruppe4.opretProdukt("Julebryg, 20 liter", 100);
        Produkt p9g4 = produktGruppe4.opretProdukt("Impreial Stout, 20 liter", 100);
        Produkt p10g4 = produktGruppe4.opretProdukt("Pant", 100);

        // Produkter - gruppe5
        Produkt p1g5 = produktGruppe5.opretProdukt("6 kg", 100);
        Produkt p2g5 = produktGruppe5.opretProdukt("Pant", 100);
        Produkt p3g5 = produktGruppe5.opretProdukt("4 kg", 100);
        Produkt p4g5 = produktGruppe5.opretProdukt("10 kg", 100);

        // Produkter - gruppe6
        Produkt p1g6 = produktGruppe6.opretProdukt("25kg sæk", 100);

        // Produkter - gruppe7
        Produkt p1g7 = produktGruppe7.opretProdukt("t-shirt", 100);
        Produkt p2g7 = produktGruppe7.opretProdukt("polo", 100);
        Produkt p3g7 = produktGruppe7.opretProdukt("cap", 100);

        // Produkter - gruppe8
        Produkt p1g8 = produktGruppe8.opretProdukt("1-hane", 100);
        Produkt p2g8 = produktGruppe8.opretProdukt("2-haner", 100);
        Produkt p3g8 = produktGruppe8.opretProdukt("Bar med flere haner", 100);
        Produkt p4g8 = produktGruppe8.opretProdukt("Levering", 100);
        Produkt p5g8 = produktGruppe8.opretProdukt("Krus", 100);

        // Produkter - gruppe9
        Produkt p1g9 = produktGruppe9.opretProdukt("uanset størelse", 100);

        // Produkter - gruppe10
        Produkt p1g10 = produktGruppe10.opretProdukt("gaveæske 2 øl, 2 glas", 100);
        Produkt p2g10 = produktGruppe10.opretProdukt("gaveæske 4 øl", 100);
        Produkt p3g10 = produktGruppe10.opretProdukt("trækasse 4 øl", 100);
        Produkt p4g10 = produktGruppe10.opretProdukt("gavekurv 6 øl, 2 glas", 100);
        Produkt p5g10 = produktGruppe10.opretProdukt("trækasse 6 øl, 6 glas", 100);
        Produkt p6g10 = produktGruppe10.opretProdukt("trækasse 12 øl, 6 glas", 100);
        Produkt p7g10 = produktGruppe10.opretProdukt("papkasse 12 øl, 6 glas", 100);

        // Produkter - gruppe11
        Produkt p1g11 = produktGruppe11.opretProdukt("pr person dag", 100);

    }

}
