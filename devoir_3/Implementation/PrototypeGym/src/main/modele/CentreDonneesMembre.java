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
            case VILLE_CLIENT:
                membre.setVille(valeur);
                break;
            case PROVINCE_CLIENT:
                membre.setProvince(valeur);
                break;
            case CODEPOSTAL_CLIENT:
                membre.setCodePostal(valeur);
                break;
            case STATUT_MEMBRE:
                membre.setAPaye(!membre.getAPaye());
        }
    }
    
    public void modifierStatutMembres(HashMap<String, Boolean> listeValidations) {
    	List<Membre> listeMembreModifier= listeMembres.values().stream().collect(Collectors.toList());
    			for(Membre membre : listeMembreModifier) {
    				boolean statut = listeValidations.get(membre.getId());
    				membre.setAPaye(statut);
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
    
    public HashMap<String, Membre> getListeMembres() {
        return listeMembres;
    }
}
