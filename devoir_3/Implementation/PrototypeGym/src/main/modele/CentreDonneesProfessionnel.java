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

    public CentreDonneesProfessionnel() {
        listeProfessionnels = new HashMap<>();
        listeAdressesProfessionnels = new HashMap<>();
    }

    @Override
    public void creer(Professionnel professionnel) {
        ajouterClient(professionnel);
    }

    @Override
    public Professionnel lire(String id) {
        if (listeProfessionnels.containsKey(id)) {
            return listeProfessionnels.get(id);
        }
        return null;
    }

    public String getIdDepuisAdresse(String adresseCourriel) {
        if (listeAdressesProfessionnels.containsKey(adresseCourriel)) {
            return listeAdressesProfessionnels.get(adresseCourriel);
        }
        return null;
    }

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

    @Override
    public void supprimer(String id) {
        String adresseCourriel = listeProfessionnels.get(id).getAdresseCourriel();
        listeProfessionnels.remove(id);
        listeAdressesProfessionnels.remove(adresseCourriel);
    }

    @Override
    public void ajouterClient(Professionnel professionnel) {
        if (!listeProfessionnels.containsKey(professionnel.getHashInString()) &&
        !listeAdressesProfessionnels.containsKey(professionnel.adresseCourriel)) {
            listeProfessionnels.put(professionnel.getHashInString(), professionnel);
            listeAdressesProfessionnels.put(professionnel.adresseCourriel, professionnel.getHashInString());
        }
    }

    public List<Client> getClients() {
        return new ArrayList<>(listeProfessionnels.values());
    }

    public HashMap<String, Professionnel> getListeProfessionnels() {
        return listeProfessionnels;
    }
}
