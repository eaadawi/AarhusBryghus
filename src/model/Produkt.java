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

    /**
     * Kaster en IllegalArgumentException hvis der ikke er nok tilbage på lager
     */
    public void fjernAntalPaaLager(int antal) {
        if(antalPaaLager - antal < 0)
            throw new IllegalArgumentException("Der er kun " + antalPaaLager + " tilbage på lager");
        antalPaaLager -= antal;
    }
}
