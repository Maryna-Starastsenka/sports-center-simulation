package main.modele;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static main.controleur.Verificateurs.getDateFromString;

public class CentreDonneesProfessionnel implements ICentreDonnees<Professionnel> {
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();
    private HashMap<String, String> listeAdressesProfessionnels = new HashMap<>();

    public CentreDonneesProfessionnel() {

    }

    @Override
    public Professionnel creer(Professionnel professionnel) {
        ajouterClient(professionnel);
        return professionnel;
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
            case NOM_CLIENT:
                professionnel.setNom(valeur);
                break;
            case DATE_NAISSANCE_CLIENT:
                professionnel.setDateNaissance(getDateFromString(valeur));
                break;
            case ADRESSE_COURRIEL_CLIENT:
                //String ancienneAdresse = professionnel.getAdresseCourriel();
                String ancienneAdresse = professionnel.getAdresseCourriel();
                professionnel.setAdresseCourriel(valeur);
                listeAdressesProfessionnels.remove(ancienneAdresse);
                listeAdressesProfessionnels.put(valeur, idProfessionnel);
                break;
            case TELEPHONE_CLIENT:
                professionnel.setNumeroPhone(valeur);
                break;
            case ADRESSE_CLIENT:
                professionnel.setAdresse(valeur);
                break;
            case VILLE_CLIENT:
                professionnel.setVille(valeur);
                break;
            case PROVINCE_CLIENT:
                professionnel.setProvince(valeur);
                break;
            case CODEPOSTAL_CLIENT:
                professionnel.setCodePostal(valeur);
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
        return listeProfessionnels.values().stream().collect(Collectors.toList());
    }

    public HashMap<String, Professionnel> getListeProfessionnels() {
        return listeProfessionnels;
    }
}
