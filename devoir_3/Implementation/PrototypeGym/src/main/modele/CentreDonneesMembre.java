package main.modele;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static main.controleur.Verificateurs.getDateFromString;

public class CentreDonneesMembre implements ICentreDonnees<Membre> {
    private HashMap<String, Membre> listeMembres = new HashMap<>();
    private HashMap<String, String> listeAdressesMembres = new HashMap<>();

    public CentreDonneesMembre() {
        // valeurs par défaut
        /*** MEMBRES ***/
        Membre membre1 = new Membre("John Doe", LocalDate.of(1970, 2, 2),
                "456 du Brésil, Brasilia",
                "999-999-9999",
                "John@doe.com", true);
        listeMembres.put(membre1.getHashInString(), membre1);
        listeAdressesMembres.put(membre1.adresseCourriel, membre1.getHashInString());
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

    public String getIdDepuisAdresse(String adresseCourriel) {
        if (listeAdressesMembres.containsKey(adresseCourriel)) {
            return listeAdressesMembres.get(adresseCourriel);
        }
        return null;
    }

    @Override
    public void mettreAJour(String idMembre, Champs champsClient, String valeur) {
        Membre membre = lire(idMembre);

        switch (champsClient) {
            case NOM_CLIENT:
                membre.setNom(valeur);
                break;
            case DATE_NAISSANCE_CLIENT:
                membre.setDateNaissance(getDateFromString(valeur));
                break;
            case ADRESSE_COURRIEL_CLIENT:
                String ancienneAdresse = membre.getAdresseCourriel();
                membre.setAdresseCourriel(valeur);
                listeAdressesMembres.remove(ancienneAdresse);
                listeAdressesMembres.put(valeur, idMembre);
                break;
            case TELEPHONE_CLIENT:
                membre.setNumeroPhone(valeur);
                break;
            case ADRESSE_CLIENT:
                membre.setAdresse(valeur);
                break;
            case STATUT_MEMBRE:
                membre.setAPaye(!membre.getAPaye());
        }
    }

    @Override
    public void supprimer(String id) {
        String adresseCourriel = listeMembres.get(id).getAdresseCourriel();
        listeMembres.remove(id);
        listeAdressesMembres.remove(adresseCourriel);
    }

    @Override
    public void ajouterClient(Membre membre) {
        if (!listeMembres.containsKey(membre.getHashInString()) &&
        !listeAdressesMembres.containsKey(membre.adresseCourriel)) {
            listeMembres.put(membre.getHashInString(), membre);
            listeAdressesMembres.put(membre.adresseCourriel, membre.getHashInString());
        }
    }

    public List<Client> getClients() {
        return listeMembres.values().stream().collect(Collectors.toList());
    }
}