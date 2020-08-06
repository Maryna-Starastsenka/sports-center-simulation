package main.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static main.controleur.Helper.getDateFromString;

/**
 * Classe CentreDonneesMembre qui implémente ICentreDonnees<Membre>. Répertoire des membres
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class CentreDonneesMembre implements ICentreDonnees<Membre> {
    private HashMap<String, Membre> listeMembres;
    private HashMap<String, String> listeAdressesMembres;

    public CentreDonneesMembre() {
        this.listeMembres = new HashMap<>();
        this.listeAdressesMembres = new HashMap<>();
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
            case NOM_CLIENT -> membre.setNom(valeur);
            case DATE_NAISSANCE_CLIENT -> membre.setDateNaissance(getDateFromString(valeur));
            case ADRESSE_COURRIEL_CLIENT -> {
                String ancienneAdresse = membre.getAdresseCourriel();
                membre.setAdresseCourriel(valeur);
                listeAdressesMembres.remove(ancienneAdresse);
                listeAdressesMembres.put(valeur, idMembre);
            }
            case TELEPHONE_CLIENT -> membre.setNumeroPhone(valeur);
            case ADRESSE_CLIENT -> membre.setAdresse(valeur);
            case VILLE_CLIENT -> membre.setVille(valeur);
            case PROVINCE_CLIENT -> membre.setProvince(valeur);
            case CODEPOSTAL_CLIENT -> membre.setCodePostal(valeur);
            case STATUT_MEMBRE -> membre.setAPaye(!membre.getAPaye());
        }
    }
    
    public void modifierStatutMembres(HashMap<String, Boolean> listeValidations) {
    	List<Membre> listeMembreModifier= new ArrayList<>(listeMembres.values());
    			for(Membre membre : listeMembreModifier) {
    				String id = this.getIdDepuisAdresse(membre.getAdresseCourriel());
    				if(listeValidations.containsKey(id)) {
    					Boolean statut = listeValidations.get(id);
    					membre.setAPaye(statut);
    				}
    			}
    }

    public List<Client> getClients() {
        return new ArrayList<>(listeMembres.values());
    }

    public HashMap<String, Membre> getListeMembres() {
        return listeMembres;
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

    @Override
    public void creer(Membre membre) {
        ajouterClient(membre);
    }

    @Override
    public Membre lire(String id) {
        if (listeMembres.containsKey(id)) {
            return listeMembres.get(id);
        }
        return null;
    }
}
