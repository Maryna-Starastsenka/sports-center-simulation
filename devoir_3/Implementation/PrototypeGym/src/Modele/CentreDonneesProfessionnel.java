package Modele;

import Vue.ICentreDonnees;

import java.time.LocalDate;
import java.util.HashMap;

public class CentreDonneesProfessionnel implements ICentreDonnees<Professionnel> {
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();

    @Override
    public Professionnel creer(Professionnel professionnel) {
        listeProfessionnels.put(professionnel.getHashInString(), professionnel);
        return professionnel;
    }

    @Override
    public Professionnel lire(String id) {
        if (listeProfessionnels.containsKey(id)) {
            return listeProfessionnels.get(id);
        }
        return null;
    }

    @Override
    public void mettreAJour(String id) {

    }

    @Override
    public void supprimer(String id) {

    }
}
