package model;

public class Ordrelinje {
    private int antal;

    private Produkt produkt;
    private Prisliste prisliste;

    public Ordrelinje(int antal, Produkt produkt, Prisliste prisliste) {
        this.antal = antal;
        this.produkt = produkt;
        this.prisliste = prisliste;
    }

    public double samletPris() {
        double pris = prisliste.hentPris(produkt);
        return pris * antal;
    }

    public int hentAntal() {
        return antal;
    }

    public Produkt hentProdukt() {
        return produkt;
    }

    public Prisliste hentPrisliste() {
        return prisliste;
    }
}
