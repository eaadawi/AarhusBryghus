package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ordre {

    private final LocalDate dato;
    private Betalinsgmetode betalinsgmetode;
    private final int id;

    private final List<Ordrelinje> ordrelinjer = new ArrayList<>();

    public Ordre(LocalDate dato, int id) {
        this.dato = dato;
        this.id = id;
    }

    /**
     * Udregner den totale pris for de produkter som ikke skal betales med klip
     */
    public double totalPris() {
        double pris = 0;
        for(Ordrelinje ol : ordrelinjer) {
            if(ol.hentPrisliste().hentValuta() != Valuta.KLIP)
                pris += ol.samletPris();
        }
        String str = String.format("%.2f", pris);
        pris = Double.parseDouble(str.replace(',', '.'));
        return pris;
    }

    /**
     * Udregner den totale pris for de produkter som skal betales med klip
     */
    public int klipPris() {
        int pris = 0;
        for(Ordrelinje ol : ordrelinjer) {
            if(ol.hentPrisliste().hentValuta() == Valuta.KLIP)
                pris += ol.samletPris();
        }
        return pris;
    }

    public void tilfoejBetalingsmetode(Betalinsgmetode betalinsgmetode) {
        this.betalinsgmetode = betalinsgmetode;
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

    @Override
    public String toString() {
        return "Ordre nr " + id +' '+
                "(" + dato +')';
    }
}
