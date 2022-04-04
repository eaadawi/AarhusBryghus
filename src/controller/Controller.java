package controller;

import model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.*;

public class Controller {

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
     * Opretter og retunere en ordre med dato og id, tilføjer ordren til Storage
     */
    public static Ordre opretOrdre() {
        LocalDate dato = LocalDate.now();
        int id = Storage.hentInstans().hentOrdrer().size() + 1;
        Ordre ordre = new Ordre(dato, id);
        Storage.hentInstans().tilfoejOrdre(ordre);
        return ordre;
    }

    /**
     * Opretter og retunere en udlejning med dato og id, tilføjer udlejning til Storage
     */
    public static Udlejning opretUdlejning() {
        LocalDate dato = LocalDate.now();
        int id = Storage.hentInstans().hentOrdrer().size() + 1;
        Udlejning udlejning = new Udlejning(dato, id);
        Storage.hentInstans().tilfoejOrdre(udlejning);
        return udlejning;
    }

    /**
     * Opretter og retunere et klippekort med id, tilføjer klippekortet til Storage
     */

    public static Klippekort opretKlippekort(String kundeNavn) {
        int id = Storage.hentInstans().hentKlippekort().size() + 1;
        Klippekort klippekort = new Klippekort(id, kundeNavn);
        Storage.hentInstans().tilfoejKlippekort(klippekort);
        return klippekort;
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
     * Fjerner ordre fra storage
     */
    public static void fjernOrdre(Ordre ordre) {
        Storage.hentInstans().fjernOrdre(ordre);
    }

    /**
     * Fjerner klippekort fra storage
     */
    public static void fjernKlippekort(Klippekort klippekort) {
        Storage.hentInstans().fjernKlippekort(klippekort);
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

    /**
     * Henter set af ordre fra Storage
     */
    public static Set<Ordre> hentOrdrer() {
        return Storage.hentInstans().hentOrdrer();
    }


    /**
     * Henter set af klippekort fra Storage
     */
    public static Set<Klippekort> hentKlippekort() {
        return Storage.hentInstans().hentKlippekort();
    }

    /**
     * Henter produkter en produktgruppe og en prisliste har til fælles
     */
    public static Set<Produkt> hentFaellesProdukter(ProduktGruppe produktGruppe, Prisliste prisliste) {
        Set<Produkt> produkter = new HashSet<>();
        Set<Produkt> plProdukter = prisliste.hentProdukter();
        for(Produkt p : produktGruppe.hentProdukter()) {
            if(plProdukter.contains(p))
                produkter.add(p);
        }
        return produkter;
    }

    /**
     * Henter en liste af produkter fra en produktgruppe ved at give navnet på produktgruppen
     */
    public static List<Produkt> hentProdukterFraGruppenavn(String pgNavn) {
        List<Produkt> produkter = new ArrayList<>();
        for(ProduktGruppe pg : hentProduktGrupper()) {
            if(pg.hentNavn().equals(pgNavn))
                produkter = pg.hentProdukter();
        }
        return produkter;
    }

    /**
     * Henter en prisliste ved at give navnet på prislisten
     */
    public static Prisliste hentPrislisteFraNavn(String plNavn) {
        Prisliste prisliste = null;
        for(Prisliste pl : hentPrislister()) {
            if(pl.hentNavn().equals(plNavn))
                prisliste = pl;
        }
        if(prisliste == null) throw new IllegalArgumentException("Der findes ingen prisliste med dette navn");
        return prisliste;
    }

    /**
     * Henter et produkt udfra et navn på produktgruppen der indeholder den og navnet på produktet
     */
    public static Produkt hentProduktFraNavn(String pgNavn, String pNavn) {
        List<Produkt> produkter = hentProdukterFraGruppenavn(pgNavn);
        Produkt produkt = null;
        for(Produkt p : produkter) {
            if(p.hentNavn().equals(pNavn))
                produkt = p;
        }
        if(produkt == null) throw new IllegalArgumentException("Der findes ingen produkter med dette navn");
        return produkt;
    }

    /**
     * Henter aller ordre af en bestemt type
     * "o" for kun normale ordre og "u" for udlejninger
     */
    public static Set<Ordre> hentOdreAfType(String type) {
        Set<Ordre> ordre = new HashSet<>();
        if(type.equals("o")) {
            for(Ordre o : Storage.hentInstans().hentOrdrer()) {
                if(!(o instanceof Udlejning))
                    ordre.add(o);
            }
        }else if(type.equals("u")) {
            for(Ordre o : Storage.hentInstans().hentOrdrer()) {
                if(o instanceof Udlejning)
                    ordre.add(o);
            }
        }else throw new IllegalArgumentException("Type skal være o eller u");
        return ordre;
    }

    /**
     * Henter alle klippekort hvor kundenavnet indeholder den givet string
     */
    public static Set<Klippekort> soegKlippekort(String input){
        Set<Klippekort> klippekort = new HashSet<>();
        CharSequence inputCharSequence = input.toLowerCase(Locale.ROOT).trim();

        for(Klippekort k : Storage.hentInstans().hentKlippekort()) {
            String navn = k.hentKundeNavn().toLowerCase(Locale.ROOT);
            if(navn.contains(inputCharSequence))
                klippekort.add(k);
        }

        return klippekort;
    }

    /**
     * Henter alle normale ordre som er oprettet inden for den givet periode
     */
    //TODO Lav test
    public static Set<Ordre> hentOrdrePeriode(LocalDate startDato, LocalDate slutDato) {
        Set<Ordre> set = new HashSet<>();
        for(Ordre o : hentOdreAfType("o")) {
            LocalDate dato = o.hentDato();
            if(!(startDato.isBefore(dato)) && !(slutDato.isAfter(dato)))
                set.add(o);
        }
        return set;
    }

    /**
     * Udregner den samlet mængde af klip brugt til at betale for de givet ordre
     */
    //TODO Lav test
    public static int udregnSamletForbrugteKlip(Set<Ordre> ordre) {
        int samletKlip = 0;
        for(Ordre o : ordre) {
            samletKlip += o.klipPris();
        }
        return samletKlip;
    }

    /**
     * Henter og retunere en liste med ordre på en given dato
     */
    //TODO Lav test
    public static Set<Ordre> hentOrdreDato(LocalDate dato) {

        Set<Ordre> ordrer = new HashSet<>();
        String type = "o";
        for (Ordre o : hentOdreAfType(type)) {
            if (o.hentDato() == dato) {
                ordrer.add(o);
            }
        }


        return ordrer;
    }

    /**
     * Udregner den samlede omsætning på en liste af ordre
     */
    //TODO Lav test
    public static double hentSamletOmsaetning(LocalDate dato) {

        double samletOmsaetning = 0;
        Set<Ordre> ordrer = hentOrdreDato(dato);
        for (Ordre o : ordrer) {
            samletOmsaetning += o.totalPris();
        }

        for (Klippekort k : hentKlippekort()){
            if (k.hentDato() == dato) {
                samletOmsaetning += k.hentPris();
            }
        }

        return samletOmsaetning;
    }

    public static void initStorage() {
        Klippekort.aendreKlippekortPris(130);
        Klippekort.aendreAntalKlip(4);
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

        // PRODUKTER - gruppe1
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

        // PRODUKTER - gruppe2
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

        // PRODUKTER - gruppe3
        Produkt p1g3 = produktGruppe3.opretProdukt("Whisky 45% 50cl rør", 100);
        Produkt p2g3 = produktGruppe3.opretProdukt("Whisky 4cl", 100);
        Produkt p3g3 = produktGruppe3.opretProdukt("Whisky 43% 50cl rør", 100);
        Produkt p4g3 = produktGruppe3.opretProdukt("u/ egesplint", 100);
        Produkt p5g3 = produktGruppe3.opretProdukt("m/ egesplint", 100);
        Produkt p6g3 = produktGruppe3.opretProdukt("2*whisky glas + brikker", 100);
        Produkt p7g3 = produktGruppe3.opretProdukt("Liquor of Aarhus", 100);
        Produkt p8g3 = produktGruppe3.opretProdukt("Lyng gin 50 cl", 100);
        Produkt p9g3 = produktGruppe3.opretProdukt("Lyng gin 4 cl", 100);

        // PRODUKTER - gruppe4
        PantProdukt p1g4 = produktGruppe4.opretPantProdukt("Klosterbryg, 20 liter", 100, 20);
        PantProdukt p2g4 = produktGruppe4.opretPantProdukt("Jazz Classic, 25 liter", 100, 25);
        PantProdukt p3g4 = produktGruppe4.opretPantProdukt("Extra Pilsner, 25 liter", 100, 25);
        PantProdukt p4g4 = produktGruppe4.opretPantProdukt("Celebration, 20 liter", 100, 20);
        PantProdukt p5g4 = produktGruppe4.opretPantProdukt("Blondie, 25 liter", 100, 25);
        PantProdukt p6g4 = produktGruppe4.opretPantProdukt("Forårsbryg, 20 liter", 100, 20);
        PantProdukt p7g4 = produktGruppe4.opretPantProdukt("India Pale Ale, 20 liter", 100, 20);
        PantProdukt p8g4 = produktGruppe4.opretPantProdukt("Julebryg, 20 liter", 100, 20);
        PantProdukt p9g4 = produktGruppe4.opretPantProdukt("Impreial Stout, 20 liter", 100, 20);
        Produkt p10g4 = produktGruppe4.opretProdukt("Pant", 100);

        // PRODUKTER - gruppe5
        PantProdukt p1g5 = produktGruppe5.opretPantProdukt("6 kg", 100, 6);
        Produkt p2g5 = produktGruppe5.opretProdukt("Pant", 100);
        produktGruppe5.opretPantProdukt("4 kg", 100, 4);
        produktGruppe5.opretPantProdukt("10 kg", 100, 10);

        // PRODUKTER - gruppe6
        Produkt p1g6 = produktGruppe6.opretProdukt("25kg sæk", 100);

        // PRODUKTER - gruppe7
        Produkt p1g7 = produktGruppe7.opretProdukt("t-shirt", 100);
        Produkt p2g7 = produktGruppe7.opretProdukt("polo", 100);
        Produkt p3g7 = produktGruppe7.opretProdukt("cap", 100);

        // PRODUKTER - gruppe8
        Produkt p1g8 = produktGruppe8.opretProdukt("1-hane", 100);
        Produkt p2g8 = produktGruppe8.opretProdukt("2-haner", 100);
        Produkt p3g8 = produktGruppe8.opretProdukt("Bar med flere haner", 100);
        Produkt p4g8 = produktGruppe8.opretProdukt("Levering", 100);
        Produkt p5g8 = produktGruppe8.opretProdukt("Krus", 100);

        // PRODUKTER - gruppe9
        Produkt p1g9 = produktGruppe9.opretProdukt("uanset størelse", 100);

        // PRODUKTER - gruppe10
        Produkt p1g10 = produktGruppe10.opretProdukt("gaveæske 2 øl, 2 glas", 100);
        Produkt p2g10 = produktGruppe10.opretProdukt("gaveæske 4 øl", 100);
        Produkt p3g10 = produktGruppe10.opretProdukt("trækasse 4 øl", 100);
        Produkt p4g10 = produktGruppe10.opretProdukt("gavekurv 6 øl, 2 glas", 100);
        Produkt p5g10 = produktGruppe10.opretProdukt("trækasse 6 øl, 6 glas", 100);
        Produkt p6g10 = produktGruppe10.opretProdukt("trækasse 12 øl, 6 glas", 100);
        Produkt p7g10 = produktGruppe10.opretProdukt("papkasse 12 øl, 6 glas", 100);

        // PRODUKTER - gruppe11
        Produkt p1g11 = produktGruppe11.opretProdukt("pr person dag", 100);


        // PRISLISTE - Fredagsbar
        Prisliste fredagsbar = Controller.opretPrisliste("Fredagsbar", Valuta.DKK);

        // GRUPPE 1
        fredagsbar.tilfoejProdukt(p1g1, 70);
        fredagsbar.tilfoejProdukt(p2g1, 70);
        fredagsbar.tilfoejProdukt(p3g1, 70);
        fredagsbar.tilfoejProdukt(p4g1, 70);
        fredagsbar.tilfoejProdukt(p5g1, 70);
        fredagsbar.tilfoejProdukt(p6g1, 70);
        fredagsbar.tilfoejProdukt(p7g1, 70);
        fredagsbar.tilfoejProdukt(p8g1, 70);
        fredagsbar.tilfoejProdukt(p9g1, 70);
        fredagsbar.tilfoejProdukt(p10g1, 70);
        fredagsbar.tilfoejProdukt(p11g1, 70);
        fredagsbar.tilfoejProdukt(p12g1, 70);
        fredagsbar.tilfoejProdukt(p13g1, 70);
        fredagsbar.tilfoejProdukt(p14g1, 100);

        // GRUPPE 2
        fredagsbar.tilfoejProdukt(p1g2, 38);
        fredagsbar.tilfoejProdukt(p2g2, 38);
        fredagsbar.tilfoejProdukt(p3g2, 38);
        fredagsbar.tilfoejProdukt(p4g2, 38);
        fredagsbar.tilfoejProdukt(p5g2, 38);
        fredagsbar.tilfoejProdukt(p6g2, 38);
        fredagsbar.tilfoejProdukt(p7g2, 38);
        fredagsbar.tilfoejProdukt(p8g2, 38);
        fredagsbar.tilfoejProdukt(p9g2, 38);
        fredagsbar.tilfoejProdukt(p10g2, 38);
        fredagsbar.tilfoejProdukt(p11g2, 15);
        fredagsbar.tilfoejProdukt(p12g2, 10);
        fredagsbar.tilfoejProdukt(p13g2, 15);
        fredagsbar.tilfoejProdukt(p14g2, 15);
        fredagsbar.tilfoejProdukt(p15g2, 15);
        fredagsbar.tilfoejProdukt(p16g2,15);
        fredagsbar.tilfoejProdukt(p17g2, 10);
        fredagsbar.tilfoejProdukt(p18g2, 30);

        // GRUPPE 3
        fredagsbar.tilfoejProdukt(p1g3, 599);
        fredagsbar.tilfoejProdukt(p2g3, 50);
        fredagsbar.tilfoejProdukt(p3g3,499);
        fredagsbar.tilfoejProdukt(p4g3, 300);
        fredagsbar.tilfoejProdukt(p5g3, 350);
        fredagsbar.tilfoejProdukt(p6g3, 80);
        fredagsbar.tilfoejProdukt(p7g3, 175);
        fredagsbar.tilfoejProdukt(p8g3, 350);
        fredagsbar.tilfoejProdukt(p9g3, 40);

        // GRUPPE 5
        fredagsbar.tilfoejProdukt(p1g5, 400);
        fredagsbar.tilfoejProdukt(p2g5, 1000);
//        fredagsbar.tilfoejProdukt(p3g5, 0);
//        fredagsbar.tilfoejProdukt(p4g5, 0);

        // GRUPPE 7
        fredagsbar.tilfoejProdukt(p1g7, 70);
        fredagsbar.tilfoejProdukt(p2g7, 100);
        fredagsbar.tilfoejProdukt(p3g7, 30);

        // GRUPPE 10
        fredagsbar.tilfoejProdukt(p1g10, 110);
        fredagsbar.tilfoejProdukt(p2g10, 140);
        fredagsbar.tilfoejProdukt(p3g10, 260);
        fredagsbar.tilfoejProdukt(p4g10, 260);
        fredagsbar.tilfoejProdukt(p5g10, 350);
        fredagsbar.tilfoejProdukt(p6g10, 410);
        fredagsbar.tilfoejProdukt(p7g10, 370);


        // PRISLISTE - Butik
        Prisliste butik = opretPrisliste("Butik", Valuta.DKK);

        butik.tilfoejProdukt(p1g1, 36);
        butik.tilfoejProdukt(p2g1, 36);
        butik.tilfoejProdukt(p3g1, 36);
        butik.tilfoejProdukt(p4g1, 36);
        butik.tilfoejProdukt(p5g1, 36);
        butik.tilfoejProdukt(p6g1, 36);
        butik.tilfoejProdukt(p7g1, 36);
        butik.tilfoejProdukt(p8g1, 36);
        butik.tilfoejProdukt(p9g1, 36);
        butik.tilfoejProdukt(p10g1, 36);
        butik.tilfoejProdukt(p11g1, 36);
        butik.tilfoejProdukt(p12g1, 36);
        butik.tilfoejProdukt(p13g1, 36);
        butik.tilfoejProdukt(p14g1, 60);

        butik.tilfoejProdukt(p1g3, 599);
        butik.tilfoejProdukt(p3g3, 499);
        butik.tilfoejProdukt(p4g3, 300);
        butik.tilfoejProdukt(p5g3, 350);
        butik.tilfoejProdukt(p6g3, 80);
        butik.tilfoejProdukt(p7g3, 175);
        butik.tilfoejProdukt(p8g3, 350);

        butik.tilfoejProdukt(p1g4, 775);
        butik.tilfoejProdukt(p2g4, 625);
        butik.tilfoejProdukt(p3g4, 575);
        butik.tilfoejProdukt(p4g4, 775);
        butik.tilfoejProdukt(p5g4, 700);
        butik.tilfoejProdukt(p6g4, 775);
        butik.tilfoejProdukt(p7g4, 775);
        butik.tilfoejProdukt(p8g4, 775);
        butik.tilfoejProdukt(p9g4, 775);
        butik.tilfoejProdukt(p10g4, 200);

        butik.tilfoejProdukt(p1g5, 400);
        butik.tilfoejProdukt(p2g5, 1000);

        butik.tilfoejProdukt(p1g6, 300);

        butik.tilfoejProdukt(p1g7, 70);
        butik.tilfoejProdukt(p2g7, 100);
        butik.tilfoejProdukt(p3g7, 30);

        butik.tilfoejProdukt(p1g8, 250);
        butik.tilfoejProdukt(p2g8, 400);
        butik.tilfoejProdukt(p3g8, 500);
        butik.tilfoejProdukt(p4g8, 500);
        butik.tilfoejProdukt(p5g8, 60);

        butik.tilfoejProdukt(p1g9, 15);

        butik.tilfoejProdukt(p1g10, 110);
        butik.tilfoejProdukt(p2g10, 140);
        butik.tilfoejProdukt(p3g10, 260);
        butik.tilfoejProdukt(p4g10, 260);
        butik.tilfoejProdukt(p5g10, 350);
        butik.tilfoejProdukt(p6g10, 410);
        butik.tilfoejProdukt(p7g10, 370);

        butik.tilfoejProdukt(p1g11, 10);

        // PRISLISTE - FredagsbarKlip
        Prisliste fredagsbarKlip = Controller.opretPrisliste("Fredagsbar klip", Valuta.KLIP);

        // GRUPPE 1
        fredagsbarKlip.tilfoejProdukt(p1g1, 2);
        fredagsbarKlip.tilfoejProdukt(p2g1, 2);
        fredagsbarKlip.tilfoejProdukt(p3g1, 2);
        fredagsbarKlip.tilfoejProdukt(p4g1, 2);
        fredagsbarKlip.tilfoejProdukt(p5g1, 2);
        fredagsbarKlip.tilfoejProdukt(p6g1, 2);
        fredagsbarKlip.tilfoejProdukt(p7g1, 2);
        fredagsbarKlip.tilfoejProdukt(p8g1, 2);
        fredagsbarKlip.tilfoejProdukt(p9g1, 2);
        fredagsbarKlip.tilfoejProdukt(p10g1, 2);
        fredagsbarKlip.tilfoejProdukt(p11g1, 2);
        fredagsbarKlip.tilfoejProdukt(p12g1, 2);
        fredagsbarKlip.tilfoejProdukt(p13g1, 2);
        fredagsbarKlip.tilfoejProdukt(p14g1, 3);


        // GRUPPE 2
        fredagsbarKlip.tilfoejProdukt(p1g2, 1);
        fredagsbarKlip.tilfoejProdukt(p2g2, 1);
        fredagsbarKlip.tilfoejProdukt(p3g2, 1);
        fredagsbarKlip.tilfoejProdukt(p4g2, 1);
        fredagsbarKlip.tilfoejProdukt(p5g2, 1);
        fredagsbarKlip.tilfoejProdukt(p6g2, 1);
        fredagsbarKlip.tilfoejProdukt(p7g2, 1);
        fredagsbarKlip.tilfoejProdukt(p8g2, 1);
        fredagsbarKlip.tilfoejProdukt(p9g2, 1);
        fredagsbarKlip.tilfoejProdukt(p10g2, 1);
        fredagsbarKlip.tilfoejProdukt(p18g2, 1);
    }

}
