package main.modele;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static main.controleur.Verificateurs.getDateFromString;

public class CentreDonneesProfessionnel implements ICentreDonnees<Professionnel> {
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();
    private HashMap<String, String> listeAdressesProffesionnels = new HashMap<>();

    public CentreDonneesProfessionnel() {
        /*** PROFESSIONNELS ***/
        Professionnel professionnel1 = new Professionnel("Jean", LocalDate.of(1980, 12, 25),
                "456 rue Michel, Laval",
                "987-987-9876",
                "Jean@udem.com");
        listeProfessionnels.put(professionnel1.getHashInString(), professionnel1);
        listeAdressesProffesionnels.put(professionnel1.adresseCourriel, professionnel1.getHashInString());

        Professionnel professionnel2 = new Professionnel("Baptiste",
                LocalDate.of(1970, 6, 18),
                "1000 bld Henri, Longueil",
                "182-323-3432",
                "baptiste@udem.com");
        listeProfessionnels.put(professionnel2.getHashInString(), professionnel2);
        listeAdressesProffesionnels.put(professionnel2.adresseCourriel, professionnel2.getHashInString());
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
        if (listeAdressesProffesionnels.containsKey(adresseCourriel)) {
            return listeAdressesProffesionnels.get(adresseCourriel);
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
                listeAdressesProffesionnels.remove(ancienneAdresse);
                listeAdressesProffesionnels.put(valeur, idProfessionnel);
                break;
            case TELEPHONE_CLIENT:
                professionnel.setNumeroPhone(valeur);
                break;
            case ADRESSE_CLIENT:
                professionnel.setAdresse(valeur);
                break;
        }
    }

    @Override
    public void supprimer(String id) {
        String adresseCourriel = listeProfessionnels.get(id).getAdresseCourriel();
        listeProfessionnels.remove(id);
        listeAdressesProffesionnels.remove(adresseCourriel);
    }

    @Override
    public void ajouterClient(Professionnel professionnel) {
        if (!listeProfessionnels.containsKey(professionnel.getHashInString()) &&
        !listeAdressesProffesionnels.containsKey(professionnel.adresseCourriel)) {
            listeProfessionnels.put(professionnel.getHashInString(), professionnel);
            listeAdressesProffesionnels.put(professionnel.adresseCourriel, professionnel.getHashInString());
        }
    }

    public List<Client> getClients() {
        return listeProfessionnels.values().stream().collect(Collectors.toList());
    }

    public HashMap<String, Professionnel> getListeProfessionnels() {
        return listeProfessionnels;
    }
}