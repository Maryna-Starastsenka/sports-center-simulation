import java.util.Date;
import java.util.Objects;

public abstract class Client {
	protected String nom;
	protected Date dateNaissance;
	protected String adresseCourriel;
	protected String numeroPhone;
	protected String adresse;

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
	
	

