package storage;

import model.Prisliste;
import model.ProduktGruppe;

import java.util.HashSet;
import java.util.Set;

public class Storage {

    private volatile static Storage uniqueInstans = new Storage();

    private final Set<ProduktGruppe> produktGruppeSet = new HashSet<>();
    private final Set<Prisliste> prislisteSet = new HashSet<>();

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

}
