package storage;

import controller.Controller;
import model.ProduktGruppe;

import java.util.HashSet;
import java.util.Set;

public class Storage {

    private volatile static Storage uniqueInstance = new Storage();

    private static Set<ProduktGruppe> produktGruppeSet = new HashSet<>();

    public static Storage getInstance() {
        if(uniqueInstance == null) {
            synchronized (Storage.class) {
                if(uniqueInstance == null) {
                    uniqueInstance = new Storage();
                }
            }
        }
        return uniqueInstance;
    }

    public static Storage getTestStorage() {
        return new Storage();
    }

    /**
     * Pre:
     */
    public void tiljoejProduktGruppe (ProduktGruppe produktGruppe){
        produktGruppeSet.add(produktGruppe);
    }

    /**
     * Pre: produktGruppe er ind i settet
     */
    public void fjernjProduktGruppe (ProduktGruppe produktGruppe){
        produktGruppeSet.remove(produktGruppe);
    }

    /**
     * Henter en kopi af produktGruppeSet
     */
    public Set<ProduktGruppe> hentProduktGrupper(){
        return new HashSet<>(produktGruppeSet);
    }

}
