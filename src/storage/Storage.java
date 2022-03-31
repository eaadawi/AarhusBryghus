package storage;

import model.Ordre;
import model.Prisliste;
import model.ProduktGruppe;

import java.util.HashSet;
import java.util.Set;

public class Storage {

    private volatile static Storage uniqueInstans = new Storage();

    private final Set<ProduktGruppe> produktGruppeSet = new HashSet<>();
    private final Set<Prisliste> prislisteSet = new HashSet<>();
    private final Set<Ordre> ordreSet = new HashSet<>();

    public static Storage hentInstans() {
        if(uniqueInstans == null) {
            synchronized (Storage.class) {
                if(uniqueInstans == null) {
                    uniqueInstans = new Storage();
                }
            }
        }
        return uniqueInstans;
    }

    public static Storage hentTestStorage() {
        return new Storage();
    }

    /**
     * Pre:
     */
    public void tiljoejProduktGruppe (ProduktGruppe produktGruppe){
        produktGruppeSet.add(produktGruppe);
    }

    /**
     *
     */
    public void tilfoejPrisliste(Prisliste prisliste) {
        prislisteSet.add(prisliste);
    }

    /**
     *
     */
    public void tilfoejOrdre(Ordre ordre) {
        ordreSet.add(ordre);
    }

    /**
     * Pre: produktGruppe er ind i settet
     */
    public void fjernjProduktGruppe (ProduktGruppe produktGruppe){
        produktGruppeSet.remove(produktGruppe);
    }

    /**
     * Pre: Prislisten er i settet prislisteSet
     */
    public void fjernPrisliste(Prisliste prisliste) {
        prislisteSet.remove(prisliste);
    }

    /**
     * Pre: Ordre er i settet ordreSet
     */
    public void fjernOrdre(Ordre ordre) {
        ordreSet.remove(ordre);
    }

    /**
     * Henter en kopi af produktGruppeSet
     */
    public Set<ProduktGruppe> hentProduktGrupper(){
        return new HashSet<>(produktGruppeSet);
    }

    /**
     * Henter en kopi af prislisteSet
     */
    public Set<Prisliste> hentPrislister() {
        return new HashSet<>(prislisteSet);
    }

    /**
     * Henter en kopi af ordreSet
     */
    public Set<Ordre> hentOrdrer() {
        return new HashSet<>(ordreSet);
    }

    /**
     * nulsætter alt data i storage
     */
    public void rydStorage() {
        prislisteSet.clear();
        produktGruppeSet.clear();
        ordreSet.clear();
    }

}
