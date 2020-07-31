package Modele;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CentreDonneesProfessionnel implements ICentreDonnees<Professionnel> {
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();

    public CentreDonneesProfessionnel() {
        /*** PROFESSIONNELS ***/
        Professionnel professionnel1 = new Professionnel("Jean", LocalDate.of(1980, 12, 25),
                "456 rue Michel, Laval",
                "987-987-9876",
                "Jean@udem.com");
        listeProfessionnels.put(professionnel1.getHashInString(), professionnel1);

        Professionnel professionnel2 = new Professionnel("Baptiste",
                LocalDate.of(1970, 6, 18),
                "1000 bld Henri, Longueil",
                "182-323-3432",
                "baptiste@udem.com");
        listeProfessionnels.put(professionnel2.getHashInString(), professionnel2);
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

    @Override
    public void mettreAJour(String id, ChampsClients champs, String valeur) {
        //TODO
    }

    @Override
    public void supprimer(String id) {
        listeProfessionnels.remove(id);
    }

    @Override
    public void ajouterClient(Professionnel professionnel) {
        if (!listeProfessionnels.containsKey(professionnel.getHashInString())) {
            listeProfessionnels.put(professionnel.getHashInString(), professionnel);
        }
    }

    @Override
    public List<Client> getClients() {
        return listeProfessionnels.values().stream().collect(Collectors.toList());
    }
}
