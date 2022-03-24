package model;

public class Produkt {
    private String navn;
    private int antalPaaLager;

    ProduktGruppe produktGruppe; // OBS: Package visibility

    public Produkt(String navn, int antalPaaLager) {
        this.navn = navn;
        this.antalPaaLager = antalPaaLager;
    }

    public void tilfoejAntalPaaLager(int antal) {
        antalPaaLager += antal;
    }

    public void fjernAntalPaaLager(int antal) {
        antalPaaLager -= antal;
    }
}
