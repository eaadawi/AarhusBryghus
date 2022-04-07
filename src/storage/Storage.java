package storage;

import model.Klippekort;
import model.Ordre;
import model.Prisliste;
import model.ProduktGruppe;

import java.util.HashSet;
import java.util.Set;

public class Storage implements StorageI {

    private volatile static Storage uniqueInstans = new Storage();

    private final Set<ProduktGruppe> produktGruppeSet = new HashSet<>();
    private final Set<Prisliste> prislisteSet = new HashSet<>();
    private final Set<Ordre> ordreSet = new HashSet<>();
    private final Set<Klippekort> klippekortSet = new HashSet<>();

    static public Storage hentInstans() {
        if (Storage.uniqueInstans == null) {
            synchronized (Storage.class) {
                if (Storage.uniqueInstans == null) {
                    Storage.uniqueInstans = new Storage();
                }
            }
        }
        return Storage.uniqueInstans;
    }

    static public Storage hentTestStorage() {
        return new Storage();
    }

    @Override
    public void tiljoejProduktGruppe(ProduktGruppe produktGruppe) {
        produktGruppeSet.add(produktGruppe);
    }

    @Override
    public void tilfoejPrisliste(Prisliste prisliste) {
        prislisteSet.add(prisliste);
    }

    @Override
    public void tilfoejOrdre(Ordre ordre) {
        ordreSet.add(ordre);
    }

    @Override
    public void tilfoejKlippekort(Klippekort klippekort) {
        klippekortSet.add(klippekort);
    }

    /**
     * Pre: produktGruppe er ind i settet
     */
    @Override
    public void fjernjProduktGruppe(ProduktGruppe produktGruppe) {
        produktGruppeSet.remove(produktGruppe);
    }

    /**
     * Pre: Prislisten er i settet prislisteSet
     */
    @Override
    public void fjernPrisliste(Prisliste prisliste) {
        prislisteSet.remove(prisliste);
    }

    /**
     * Pre: Ordre er i settet ordreSet
     */
    @Override
    public void fjernOrdre(Ordre ordre) {
        ordreSet.remove(ordre);
    }

    /**
     * Pre: Klippekort er i settet klippekortSet
     */
    @Override
    public void fjernKlippekort(Klippekort klippekort) {
        klippekortSet.remove(klippekort);
    }

    /**
     * Henter en kopi af produktGruppeSet
     */
    @Override
    public Set<ProduktGruppe> hentProduktGrupper() {
        return new HashSet<>(produktGruppeSet);
    }

    /**
     * Henter en kopi af prislisteSet
     */
    @Override
    public Set<Prisliste> hentPrislister() {
        return new HashSet<>(prislisteSet);
    }

    /**
     * Henter en kopi af ordreSet
     */
    @Override
    public Set<Ordre> hentOrdrer() {
        return new HashSet<>(ordreSet);
    }

    /**
     * Henter en kopi af klippekortSet
     */
    @Override
    public Set<Klippekort> hentKlippekort() {
        return new HashSet<>(klippekortSet);
    }

    /**
     * nulsætter alt data i storage
     */
    @Override
    public void rydStorage() {
        prislisteSet.clear();
        produktGruppeSet.clear();
        ordreSet.clear();
        klippekortSet.clear();
    }

}
