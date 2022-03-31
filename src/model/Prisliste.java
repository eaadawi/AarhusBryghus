package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Prisliste {
    private final String navn;
    private final Valuta valuta;

    private final Map<Produkt, Double> produkter = new HashMap<>();

    public Prisliste(String navn, Valuta valuta) {
        this.navn = navn;
        this.valuta = valuta;
    }

    /**
     * Pre: Produktet er ikke allerede i denne prisliste
     */
    public void tilfoejProdukt(Produkt produkt, double pris) {
        produkter.put(produkt, pris);
    }

    /**
     * Pre: Produktet er i denne prisliste
     */
    public void fjernProdukt(Produkt produkt) {
        produkter.remove(produkt);
    }

    public Set<Produkt> hentProdukter() {
        return produkter.keySet();
    }

    /**
     * Pre: Produktet er i denne prisliste
     */
    public double hentPris(Produkt produkt) {
        return produkter.get(produkt);
    }

    public String hentNavn() {
        return navn;
    }

    public Valuta hentValuta() {
        return valuta;
    }

    // TODO ?
    /**
     * udskriver navn og valuta af prisliste ind i listView
     */

    @Override
    public String toString() {
        return navn+" / "+valuta.toString();
    }
}
