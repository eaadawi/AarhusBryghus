package model;

public class PantProdukt extends Produkt {

    private final int stoerrelse;

    ProduktGruppe produktGruppe;

    PantProdukt(String navn, int antalPaaLager, int stoerrelse) {
        super(navn, antalPaaLager);
        this.stoerrelse = stoerrelse;
    }

    public int hentStoerrelse() {
        return stoerrelse;
    }
}
