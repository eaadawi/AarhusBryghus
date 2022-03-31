package model;

public class Ordrelinje {

    private final int antal;

    private final Produkt produkt;
    private final Prisliste prisliste;

    /**
     * Kaster en IllegalArgumentException hvis antal er under 0
     */
    public Ordrelinje(int antal, Produkt produkt, Prisliste prisliste) {
        if(antal < 0)
            throw new IllegalArgumentException("Antal må ikke være negativt");

        this.antal = antal;
        this.produkt = produkt;
        this.prisliste = prisliste;
    }

    public double samletPris() {

        double pris = prisliste.hentPris(produkt) * antal;
        String str = String.format("%.2f", pris);
        pris = Double.parseDouble(str.replace(',', '.'));
        return pris;
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

    @Override
    public String toString() {
        return produkt +" (" +antal+")" ;
    }
}
