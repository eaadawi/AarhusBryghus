package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Udlejning extends Ordre{
    private LocalDate startDato;
    private LocalDate slutDato;
    private String kundeNavn;
    private String kundeTlfNr;
    private LocalDate kundeFoedselsdag;
    private String adresse;

    public Udlejning(LocalDate dato, int id) {
        super(dato, id);
    }

    /**
     * Udregner den samlet pris på hele udlejningen inclusiv pant på fustager og kulsyre
     */
    public double totalPrisMedPant() {
        double pris = 0;
        for(Ordrelinje ol : super.hentOrdrelinjer()) {
            pris += ol.samletPris();
            Produkt produkt = ol.hentProdukt();
            ProduktGruppe pg = produkt.hentProduktGruppe();

            // Tilføjer pant for alle fustager/kulsyre
            if(produkt instanceof PantProdukt) {
                Produkt pant = null;
                for(Produkt p : pg.hentProdukter()) {
                    if(p.hentNavn().equals("Pant")) {
                        pant = p;
                        break;
                    }
                }
                if (pant != null)
                    pris += (ol.hentAntal() * ol.hentPrisliste().hentPris(pant));
                }
        }

        // Runder af til 2 decimaler efter punktumet
        String str = String.format("%.2f", pris);
        pris = Double.parseDouble(str.replace(',', '.'));
        return pris;
    }

    /**
     * Pre: Der er sat en startDato
     */
    public LocalDate hentStartDato() {
        return startDato;
    }

    /**
     * Pre: Der er sat en slutDato
     */
    public LocalDate hentSlutDato() {
        return slutDato;
    }

    /**
     * Pre: Der er sat et kundeNavn
     */
    public String hentKundeNavn() {
        return kundeNavn;
    }

    /**
     * Pre: Der er sat et tlf.Nr.
     */
    public String hentKundeTlfNr() {
        return kundeTlfNr;
    }

    /**
     * Pre: Der er sat en fødselsdagsdato
     */
    public LocalDate hentKundeFoedselsdag() {
        return kundeFoedselsdag;
    }

    /**
     * Pre: Der er sat en adresse
     */
    public String hentAdresse() {
        return adresse;
    }

    public void tilfoejStartDato(LocalDate startDato) {
        if(startDato.isBefore(super.hentDato()))
            throw new IllegalArgumentException("StatDato kan ikke være før i dag");
        if(slutDato != null && slutDato.isBefore(startDato))
            throw new IllegalArgumentException("StartDato kan ikke være efter slutDato");
        this.startDato = startDato;
    }

    public void tilfoejSlutDato(LocalDate slutDato) {
        if(startDato != null && slutDato.isBefore(startDato))
            throw new IllegalArgumentException("StartDato kan ikke være efter slutDato");
        this.slutDato = slutDato;
    }

    public void tilfoejKundeNavn(String kundeNavn) {
        this.kundeNavn = kundeNavn;
    }

    public void tilfoejKundeTlfNr(String kundeTlfNr) {
        this.kundeTlfNr = kundeTlfNr;
    }

    public void tilfoejKundeFoedselsdag(LocalDate kundeFoedselsdag) {
        if(ChronoUnit.YEARS.between(kundeFoedselsdag, super.hentDato()) >= 18 && kundeFoedselsdag.isBefore(super.hentDato())) {
            this.kundeFoedselsdag = kundeFoedselsdag;
        }else throw new IllegalArgumentException("Kunden skal være fyldt 18 år");
    }

    public void tilfoejAdresse(String adresse) {
        this.adresse = adresse;
    }
}
