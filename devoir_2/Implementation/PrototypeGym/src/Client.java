import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public abstract class Client {
	protected String nom;
	protected LocalDate dateNaissance;
	protected String adresseCourriel;
	protected String numeroPhone;
	protected String adresse;

	public String getNom () { return nom; }

	public LocalDate getDateNaissance() { return dateNaissance; }

	public String getAdresseCourriel() { return adresseCourriel; }

	public String getNumeroPhone() { return numeroPhone; }

	public String getAdresse() { return adresse; }

	public void setNom(String nom) { this.nom = nom; }

	public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

	public void setAdresseCourriel(String adresseCourriel) { this.adresseCourriel = adresseCourriel; }

	public void setNumeroPhone(String numeroPhone) { this.numeroPhone = numeroPhone; }

	public void setAdresse(String adresse) { this.adresse = adresse; }

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(nom, dateNaissance, adresseCourriel, numeroPhone, adresse) % 1000000000); // 9 chiffres max
	}

	protected String getNumero() {
		return String.format("%09d", this.hashCode());
	}

	@Override
	public String toString() {
		return "Client numéro : " + getNumero() + ".";
	}

	//public void modifierCompte(String nom, int date, String courriel, int numero, String adresse) { }
		
}
	
	

