package Modele;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static Controleur.Verificateurs.getDateFromString;

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
    public void mettreAJour(String idMembre, IChamps champs, String valeur) {
        Membre membre = lire(idMembre);

        if (!(champs instanceof ChampsClient)) return;
        ChampsClient champsClient = (ChampsClient) champs;

        switch (champsClient) {
            case NOM:
                membre.setNom(valeur);
                break;
            case DATE_NAISSANCE:
                membre.setDateNaissance(getDateFromString(valeur));
                break;
            case ADRESSE_COURRIEL:
                membre.setAdresseCourriel(valeur);
                break;
            case TELEPHONE:
                membre.setNumeroPhone(valeur);
                break;
            case ADRESSE:
                membre.setAdresse(valeur);
                break;
            case STATUT:
                membre.setAPaye(!membre.getAPaye());
        }
    }

    @Override
    public void supprimer(String id) {
        listeMembres.remove(id);
    }

    @Override
    public void ajouterClient(Membre membre) {
        if (!listeMembres.containsKey(membre.getHashInString())) {
            listeMembres.put(membre.getHashInString(), membre);
        }
    }

    @Override
    public List<Client> getClients() {
        return listeMembres.values().stream().collect(Collectors.toList());
    }
}
