import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CentreDonneesClient extends CentreDonnees{
	private HashMap<String, Membre> listeMembres = new HashMap<>();
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();
    

    
    public CentreDonneesClient() {
        // valeurs par défaut
        /*** MEMBRES ***/
    	Membre membre1 = new Membre("John Doe", LocalDate.of(1970, 2, 2),
                "456 du Brésil, Brasilia",
                "999-999-9999",
                "John@doe.com", false);
        listeMembres.put(membre1.getHashInString(), membre1);
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
    
    public Client inscrireClient (String typeClient, String nom, LocalDate dateNaissance, String adresse, String
			numeroPhone, String adresseCourriel){
				Client client = null;
				switch (typeClient) {
					case "1":
						Membre membreValide = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, true);
						if (!estMembre(membreValide.getHashInString())) {
							ajouterMembre(membreValide);
						}
						client = membreValide;
						break;
					case "2":
						Membre membreSuspendu = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, false);
						if (!estMembre(membreSuspendu.getHashInString())) {
							ajouterMembre(membreSuspendu);
						}
						client = membreSuspendu;
						break;
					case "3":
						Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, numeroPhone, adresseCourriel);
						if (!estProfessionnel(professionnel.getHashInString())) {
							ajouterProfessionnel(professionnel);
						}
						client = professionnel;
						break;
					default:
						break;
				}

				return client;
			}
    
    public void ajouterMembre(Membre membre) {
        listeMembres.put(membre.getHashInString(), membre);
    }

    public void ajouterProfessionnel(Professionnel professionnel) {
        if (!listeProfessionnels.containsKey(professionnel.getHashInString())) {
            listeProfessionnels.put(professionnel.getHashInString(), professionnel);
        }
    }
    
    public boolean estMembre(String idMembre) { return listeMembres.containsKey(idMembre); }
    
    public boolean estMembreValide(String idMembre) { 
    	if (listeMembres.containsKey(idMembre)) {
    		return listeMembres.get(idMembre).getMembreValide(); 
    	}
    	return false;
    }

    public boolean estProfessionnel(String idProfessionnel) {
        return listeProfessionnels.containsKey(idProfessionnel);
    }
    
    
    public Membre getMembre(String idMembre) {
        return listeMembres.get(idMembre);
    }

    public Professionnel getProfessionnel(String idProfessionnel) {
        return listeProfessionnels.get(idProfessionnel);
    }
    
    public List<Membre> getListeMembres() {
        return listeMembres.values().stream().collect(Collectors.toList());
    }

    public List<Professionnel> getListeProfessionnels() {
        return listeProfessionnels.values().stream().collect(Collectors.toList());
    }
    
    public HashMap<String, Professionnel> getHashMapProfessionnel(){
    	return this.listeProfessionnels;
    }
    
    public void supprimerMembre(String idMembre) {
        listeMembres.remove(idMembre);
    }

    public void supprimerProfessionnel(String idProfessionnel) {
        listeMembres.remove(idProfessionnel);
    }
    
    public String getNumeroClient(String adresseCourriel) {
    	return Client.getHashInString(adresseCourriel);
    }

}
