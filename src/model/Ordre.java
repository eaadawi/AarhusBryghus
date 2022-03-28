package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ordre {
    private LocalDate dato;
    private Betalinsgmetode betalinsgmetode;
    private int id;

    private List<Ordrelinje> ordrelinjer = new ArrayList<>();

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
        return ol;
    }

    /**
     * Pre: Ordrelinjen er i denne ordre
     */
    public void fjernOrdrelinje(Ordrelinje ordrelinje) {
        ordrelinjer.remove(ordrelinje);
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
}
