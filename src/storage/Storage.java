package storage;

import model.ProduktGruppe;

import java.util.HashSet;
import java.util.Set;

public class Storage {

    private static Set<ProduktGruppe> produktGruppeSet = new HashSet<>();

    /**
     * Pre:
     */
    public static void tiljoejProduktGruppe (ProduktGruppe produktGruppe){
        produktGruppeSet.add(produktGruppe);
    }

    /**
     * Pre: produktGruppe er ind i settet
     */
    public static void fjernjProduktGruppe (ProduktGruppe produktGruppe){
        produktGruppeSet.remove(produktGruppe);
    }

    /**
     * Henter en kopi af produktGruppeSet
     */
    public static Set<ProduktGruppe> hentProduktGrupper(){
        return new HashSet<>(produktGruppeSet);
    }

}
