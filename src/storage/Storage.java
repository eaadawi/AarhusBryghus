package storage;

import controller.Controller;
import model.ProduktGruppe;

import java.util.HashSet;
import java.util.Set;

public class Storage {

    private volatile static Storage uniqueInstans = new Storage();

    private static Set<ProduktGruppe> produktGruppeSet = new HashSet<>();

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
