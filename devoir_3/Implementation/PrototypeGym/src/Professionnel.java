import java.time.LocalDate;

public class Professionnel extends Client {

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
