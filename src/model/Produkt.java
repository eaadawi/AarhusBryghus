package model;

public class Produkt {
    private final String navn;
    private int antalPaaLager;

    ProduktGruppe produktGruppe; // OBS: Package visibility

    /**
     * Kaster en IllegalArgumentException hvis antalPaaLager er nigativt
     */
    public Produkt(String navn, int antalPaaLager) {
        this.navn = navn;
        if(antalPaaLager < 0)
            throw new IllegalArgumentException("Antal må ikke være negativt");
        this.antalPaaLager = antalPaaLager;
    }

    public String hentNavn() {
        return navn;
    }

    public int hentAntalPaaLager() {
        return antalPaaLager;
    }

    public ProduktGruppe hentProduktGruppe() {
        return produktGruppe;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produkt)) return false;
        Produkt produkt = (Produkt) o;
        return antalPaaLager == produkt.antalPaaLager && navn.equals(produkt.navn);
    }

    /**
     * Metoden tilfoejes for at vise navnet i listView
     */
    @Override
    public String toString() {
        return navn+" "+hentProduktGruppe().hentNavn();
    }
}
