import java.util.Date;
import java.util.HashMap;

public class Professionnel extends Client {
	public static HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();

	//private String fichier;
	//private Service[] services;

	public Professionnel (String nom, Date dateNaissance, String adresse, String numeroPhone, String adresseCourriel) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
	}


	@Override
	public String toString() {
		return "Professionnel numéro : " + getNumero() + ".";
	}
}
