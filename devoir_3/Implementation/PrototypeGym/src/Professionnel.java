import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Professionnel extends Client {
	
	private HashMap<String, Service> listeServices = new HashMap<>();

	public Professionnel (String nom, LocalDate dateNaissance, String adresse, String numeroPhone, String adresseCourriel) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
	}

	@Override
	public String toString() {
		return "Professionnel numéro : " + getHashInString() + ".";
	}
	
}
