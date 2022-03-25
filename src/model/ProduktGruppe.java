package model;

import java.util.ArrayList;
import java.util.List;

public class ProduktGruppe {
    private final String navn;

    private final List<Produkt> produkter = new ArrayList<>();

    public ProduktGruppe(String navn) {
        this.navn = navn;
    }

    public String hentNavn() {
        return navn;
    }

    /**
     * Pre: Produkt er ikke allerede i denne ProduktGruppe
     */
    public Produkt opretProdukt(String navn, int antalPaaLager) {
        Produkt produkt = new Produkt(navn, antalPaaLager);
        produkt.produktGruppe = this;
        produkter.add(produkt);
        return produkt;
    }
    public List<Produkt> hentProdukter() {
        return new ArrayList<>(produkter);
    }

    /**
     * Pre: Produkt er i denne ProduktGruppe
     */
    public void fjernProdukt(Produkt produkt) {
        produkt.produktGruppe = null;
        produkter.remove(produkt);
    }

    /**
     * Metoden tilfoejes for at vise navnet i listView
     */
    @Override
    public String toString() {
        return hentNavn();
    }
}
