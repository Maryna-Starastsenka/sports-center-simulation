package main.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static main.controleur.Helper.getDateFromString;

/**
 * Classe CentreDonneesProfessionnel qui implémente ICentreDonnees<Professionnel>. Répertoire des professionnels
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class CentreDonneesProfessionnel implements ICentreDonnees<Professionnel> {
    private HashMap<String, Professionnel> listeProfessionnels;
    private HashMap<String, String> listeAdressesProfessionnels;

    /**
     * Constructeur de CentreDonneesProfessionnel qui initialise la liste des professionnels et
     * la liste des adresses courriel des professionnels
     */
    public CentreDonneesProfessionnel() {
        listeProfessionnels = new HashMap<>();
        listeAdressesProfessionnels = new HashMap<>();
    }

    /**
     * Fait un appel de la méthode quu ajoute un nouveau client dans la liste des professionnels
     * @param professionnel
     */
    @Override
    public void creer(Professionnel professionnel) {
        ajouterClient(professionnel);
    }

    /**
     * Retourne le professionnel associé au numéro unique
     * @param id numéro unique du professionnel
     * @return professionnel
     */
    @Override
    public Professionnel lire(String id) {
        if (listeProfessionnels.containsKey(id)) {
            return listeProfessionnels.get(id);
        }
        return null;
    }

    /**
     * Retourne le numéro du professionnel assisié à l'adresse courriel
     * @param adresseCourriel adresse courriel du professionnel
     * @return numéro du professionnel
     */
    public String getIdDepuisAdresse(String adresseCourriel) {
        if (listeAdressesProfessionnels.containsKey(adresseCourriel)) {
            return listeAdressesProfessionnels.get(adresseCourriel);
        }
        return null;
    }

    /**
     * Met à jour les informations sur le professionnel
     * @param idProfessionnel numéro unique du professionnel
     * @param champsClient type d'information à modifier
     * @param valeur nouvelle valuer
     */
    @Override
    public void mettreAJour(String idProfessionnel, Champs champsClient, String valeur) {
        Professionnel professionnel = lire(idProfessionnel);

        switch (champsClient) {
            case NOM_CLIENT -> professionnel.setNom(valeur);
            case DATE_NAISSANCE_CLIENT -> professionnel.setDateNaissance(getDateFromString(valeur));
            case ADRESSE_COURRIEL_CLIENT -> {
                String ancienneAdresse = professionnel.getAdresseCourriel();
                professionnel.setAdresseCourriel(valeur);
                listeAdressesProfessionnels.remove(ancienneAdresse);
                listeAdressesProfessionnels.put(valeur, idProfessionnel);
            }
            case TELEPHONE_CLIENT -> professionnel.setNumeroPhone(valeur);
            case ADRESSE_CLIENT -> professionnel.setAdresse(valeur);
            case VILLE_CLIENT -> professionnel.setVille(valeur);
            case PROVINCE_CLIENT -> professionnel.setProvince(valeur);
            case CODEPOSTAL_CLIENT -> professionnel.setCodePostal(valeur);
        }
    }

    /**
     * Supprime le professionnel de la liste des professionnels
     * @param id numéro unique du professionnel
     */
    @Override
    public void supprimer(String id) {
        String adresseCourriel = listeProfessionnels.get(id).getAdresseCourriel();
        listeProfessionnels.remove(id);
        listeAdressesProfessionnels.remove(adresseCourriel);
    }

    /**
     * Fait un appel de la méthode quu ajoute un nouveau professionnel dans la liste des professionnels
     * @param professionnel professionnel
     */
    @Override
    public void ajouterClient(Professionnel professionnel) {
        if (!listeProfessionnels.containsKey(professionnel.getHashInString()) &&
        !listeAdressesProfessionnels.containsKey(professionnel.adresseCourriel)) {
            listeProfessionnels.put(professionnel.getHashInString(), professionnel);
            listeAdressesProfessionnels.put(professionnel.adresseCourriel, professionnel.getHashInString());
        }
    }

    /**
     * Retourne la liste des professionnels existants
     * @return liste des professionnels
     */
    public List<Client> getClients() {
        return new ArrayList<>(listeProfessionnels.values());
    }

    /**
     * Retourne la liste des professionnels existants
     * @return liste des professionnels
     */
    public HashMap<String, Professionnel> getListeProfessionnels() {
        return listeProfessionnels;
    }
}
