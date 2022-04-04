package model;

import java.time.LocalDate;

public class Klippekort {
    private static int antalKlip;
    private static double klippekortPris;
    private final int id;
    private final String kundeNavn;
    private final double pris;
    private int antalKlipTilbage;
    private final LocalDate dato;

    public Klippekort(int id, String kundeNavn) {
        this.id = id;
        this.kundeNavn = kundeNavn;
        this.dato = LocalDate.now();
        pris = klippekortPris;
        antalKlipTilbage = antalKlip;
    }

    /**
     * Ændre prisen på alle klippekort der bliver oprettet efter ændringen
     */
    public static void aendreKlippekortPris(double pris) {
        klippekortPris = pris;
    }

    /**
     * Ændre antallet af klip et klippekort starter med på alle
     * klippekort der bliver oprettet efter ændringen
     */
    public static void aendreAntalKlip(int antal) {
        antalKlip = antal;
    }

    /**
     * Henter den nuværende pris på klippekort
     */
    public static double hentKlippekortPris() {
        return klippekortPris;
    }

    /**
     * Henter det nuværende antal klip et klippekort starter med
     */
    public static int hentAntalKlip() {
        return antalKlip;
    }

    public int hentId() {
        return id;
    }

    public String hentKundeNavn() {
        return kundeNavn;
    }

    /**
     * Henter den pris der blev betalt for kortet da det blev købt
     */
    public double hentPris() {
        return pris;
    }

    public int hentAntalKlipTilbage() {
        return antalKlipTilbage;
    }

    public LocalDate hentDato() {
        return dato;
    }

    /**
     * Kaster en IllegalArgumentException hvis det givet antal er
     * højere ind antal klip tilbage på kortet
     */
    public void fjernKlip(int antal) {
        if(antalKlipTilbage - antal < 0)
            throw new IllegalArgumentException("Det er kun " + antalKlipTilbage + " klip tilbage på dette kort");
        antalKlipTilbage -= antal;
    }

    @Override
    public String toString() {
        return "ID:" + id + ", " + kundeNavn + "(" + antalKlipTilbage + ")";
    }
}
