import java.time.LocalDate;

public class Membre extends Client {

	private boolean membreValide;

	public Membre(String nom, LocalDate dateNaissance, String adresse, String numeroPhone, String adresseCourriel,
				  boolean membreValide) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
		this.membreValide = membreValide;
	}

	public boolean getMembreValide() {return membreValide; }

	public void setMembreValide(boolean membreValide) { this.membreValide = membreValide; }

	@Override
	public String toString() {
		return super.toString() + "Membre valide : " + this.getMembreValide() + "\n";
	}

}