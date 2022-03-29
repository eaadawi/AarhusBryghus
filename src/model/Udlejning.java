package model;

import java.time.LocalDate;
import java.util.Objects;

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
    public LocalDate getStartDato() {
        return startDato;
    }

    /**
     * Pre: Der er sat en slutDato
     */
    public LocalDate getSlutDato() {
        return slutDato;
    }

    /**
     * Pre: Der er sat et kundeNavn
     */
    public String getKundeNavn() {
        return kundeNavn;
    }

    /**
     * Pre: Der er sat et tlf.Nr.
     */
    public String getKundeTlfNr() {
        return kundeTlfNr;
    }

    /**
     * Pre: Der er sat en fødselsdagsdato
     */
    public LocalDate getKundeFoedselsdag() {
        return kundeFoedselsdag;
    }

    /**
     * Pre: Der er sat en adresse
     */
    public String getAdresse() {
        return adresse;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public void setKundeNavn(String kundeNavn) {
        this.kundeNavn = kundeNavn;
    }

    public void setKundeTlfNr(String kundeTlfNr) {
        this.kundeTlfNr = kundeTlfNr;
    }

    public void setKundeFoedselsdag(LocalDate kundeFoedselsdag) {
        this.kundeFoedselsdag = kundeFoedselsdag;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
