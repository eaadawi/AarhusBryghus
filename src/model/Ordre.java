package model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ordre {

    private final LocalDate dato;
    private Betalinsgmetode betalinsgmetode;
    private final int id;
    private boolean betaltMedKlip;
    private double totalPris;

    private final List<Ordrelinje> ordrelinjer = new ArrayList<>();
    private final List<Klippekort> klippekortList = new ArrayList<>();

    public Ordre(LocalDate dato, int id) {
        this.dato = dato;
        this.id = id;
        betaltMedKlip = false;
    }

    /**
     * Udregner den totale pris for de produkter som ikke skal betales med klip
     */
    public double totalPris() {
        if (totalPris == 0) {
            double pris = 0;
            for (Ordrelinje ol : ordrelinjer) {
                if (ol.hentPrisliste().hentValuta() != Valuta.KLIP)
                    pris += ol.samletPris();
            }
            String str = String.format("%.2f", pris);
            pris = Double.parseDouble(str.replace(',', '.'));
            totalPris = pris;
        }
        return totalPris;
    }

    /**
     * Udregner den totale pris for de produkter som skal betales med klip
     */
    public int klipPris() {
        int pris = 0;
        for (Ordrelinje ol : ordrelinjer) {
            if (ol.hentPrisliste().hentValuta() == Valuta.KLIP)
                pris += ol.samletPris();
        }
        return pris;
    }

    public void tilfoejBetalingsmetode(Betalinsgmetode betalinsgmetode) {
        this.betalinsgmetode = betalinsgmetode;
    }

    public void tilfoejKlippekort(Klippekort klippekort) {
        this.klippekortList.add(klippekort);
    }

    public void TilfoejTilTotalPris(double beloeb) {
        totalPris += beloeb;
    }

    public Ordrelinje opretOrdrelinje(int antal, Produkt produkt, Prisliste prisliste) {
        Ordrelinje ol = new Ordrelinje(antal, produkt, prisliste);
        ordrelinjer.add(ol);
        produkt.fjernAntalPaaLager(antal);
        return ol;

    }

    /**
     * Pre: Ordrelinjen er i denne ordre
     */
    public void fjernOrdrelinje(Ordrelinje ordrelinje) {
        ordrelinjer.remove(ordrelinje);
        int antal = ordrelinje.hentAntal();
        Produkt produkt = ordrelinje.hentProdukt();
        produkt.tilfoejAntalPaaLager(antal);
    }

    /**
     * Pre: Klippekort er i denne ordre
     */
    public void fjernKlippekort(Klippekort klippekort) {
        this.klippekortList.remove(klippekort);
    }

    public LocalDate hentDato() {
        return dato;
    }

    public Betalinsgmetode hentBetalinsgmetode() {
        return betalinsgmetode;
    }

    public int hentId() {
        return id;
    }

    public List<Ordrelinje> hentOrdrelinjer() {
        return new ArrayList<>(ordrelinjer);
    }

    public List<Klippekort> hentKlippekort() {
        return new ArrayList<>(klippekortList);
    }

    public boolean harBetaltMedKlip() {
        return betaltMedKlip;
    }

    /**
     * Betaler for den del af ordren der skal betales med klip,
     * med de tilknyttede klippekort hvis der ikke er betalt allerede og s??tter betaltMedKlip til true
     * Kaster en IllegalArgumentException hvis der ikke er nok klip tilsammen p?? de tilknyttede klippekort
     */
    public void betalMedKlippekort() {
        int pris = klipPris();
        int antalKlip = 0;
        for (Klippekort k : klippekortList) {
            antalKlip += k.hentAntalKlipTilbage();
        }
        if (pris > antalKlip)
            throw new IllegalArgumentException("Der er ikke nok klip");

        betaltMedKlip = true;
        for (Klippekort k : klippekortList) {
            while (pris > 0 && k.hentAntalKlipTilbage() > 0) {
                pris--;
                k.fjernKlip(1);
            }
        }
    }

    @Override
    public String toString() {

        return "Ordre id:" + id + ' ' +
                "(" + dato + ')';
    }


}
