package Modele;

import java.time.LocalDate;
import java.util.HashMap;

public class CentreDonneesMembre implements ICentreDonnees<Membre> {
    private HashMap<String, Membre> listeMembres = new HashMap<>();

    public CentreDonneesMembre() {
        // valeurs par défaut
        /*** MEMBRES ***/
        Membre membre1 = new Membre("John Doe", LocalDate.of(1970, 2, 2),
                "456 du Brésil, Brasilia",
                "999-999-9999",
                "John@doe.com", false);
        listeMembres.put(membre1.getHashInString(), membre1);
    }

    @Override
    public Membre creer(Membre membre) {
        ajouterClient(membre);
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

    @Override
    public void ajouterClient(Membre membre) {
        if (!listeMembres.containsKey(membre.getHashInString())) {
            listeMembres.put(membre.getHashInString(), membre);
        }
    }
}
