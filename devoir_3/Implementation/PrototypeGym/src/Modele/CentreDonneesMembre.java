package Modele;

import Vue.ICentreDonnees;

import java.time.LocalDate;
import java.util.HashMap;

public class CentreDonneesMembre implements ICentreDonnees<Membre> {
    private HashMap<String, Membre> listeMembres = new HashMap<>();

    @Override
    public Membre creer(Membre membre) {
        listeMembres.put(membre.getHashInString(), membre);
        return membre;
    }

    @Override
    public Membre lire(String id) {
        if (listeMembres.containsKey(id)) {
            return listeMembres.get(id);
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
