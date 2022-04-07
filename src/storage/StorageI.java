package storage;

import model.Klippekort;
import model.Ordre;
import model.Prisliste;
import model.ProduktGruppe;

import java.util.Set;

public interface StorageI {

    void tiljoejProduktGruppe(ProduktGruppe produktGruppe);

    void tilfoejPrisliste(Prisliste prisliste);

    void tilfoejOrdre(Ordre ordre);

    void tilfoejKlippekort(Klippekort klippekort);

    void fjernjProduktGruppe(ProduktGruppe produktGruppe);

    void fjernPrisliste(Prisliste prisliste);

    void fjernOrdre(Ordre ordre);

    void fjernKlippekort(Klippekort klippekort);

    Set<ProduktGruppe> hentProduktGrupper();

    Set<Prisliste> hentPrislister();

    Set<Ordre> hentOrdrer();

    Set<Klippekort> hentKlippekort();

    void rydStorage();
}
