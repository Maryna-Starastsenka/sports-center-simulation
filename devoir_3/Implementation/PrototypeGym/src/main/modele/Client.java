package main.modele;

import main.controleur.Helper;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe abstraite Client dont héritent les membres et les professionnels
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public abstract class Client {
	protected String id;
	protected String nom;
	protected LocalDate dateNaissance;
	protected String adresseCourriel;
	protected String numeroPhone;
	protected String adresse;
	protected String ville;
	protected String province;
	protected String codePostal;

	public String getId () { return id; }

	public String getNom () { return nom; }

	public LocalDate getDateNaissance() { return dateNaissance; }

	public String getAdresseCourriel() { return adresseCourriel; }

	public String getNumeroPhone() { return numeroPhone; }

	public String getAdresse() { return adresse; }

	public String getVille() { return ville; }

	public String getProvince() { return province; }

	public String getCodePostal() { return codePostal; }

	public void setNom(String nom) { this.nom = nom; }

	public void setId(String id) { this.id = id; }

	public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

	public void setAdresseCourriel(String adresseCourriel) { this.adresseCourriel = adresseCourriel; }

	public void setNumeroPhone(String numeroPhone) { this.numeroPhone = numeroPhone; }

	public void setAdresse(String adresse) { this.adresse = adresse; }

	public void setVille(String ville) { this.ville = ville; }

	public void setProvince(String province) { this.province = province; }

	public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

	@Override
	public int hashCode() {
		return hashCode(adresseCourriel);
	}

	public String getHashInString() {
		return String.format("%09d", this.hashCode());
	}

	public static int hashCode(String adresseCourriel) {
		return Math.abs(Objects.hash(adresseCourriel) % 1000000000);
	}

	public static String getHashInString(String adresseCourriel) {
		return String.format("%09d", hashCode(adresseCourriel));
	}

	/**
	 * Retourne les informations sur le membre en string
	 * @return informations sur le membre
	 */
	@Override
	public String toString() {
		return "Nom : " + this.getNom() + "\n" + "Date de naissance : "
				+ Helper.localDateFormatter.format(this.getDateNaissance())+ "\n" + "Adresse courriel : " + this.getAdresseCourriel() + "\n"
				+ "Numéro de téléphone : " + this.getNumeroPhone() + "\n" + "Adresse : " + this.getAdresse() + "\n"+ "Ville : " + this.getVille() + "\n"+
				"Province : " + this.getProvince() + "\n" + "Code postal : " + this.getCodePostal() + "\n";
	}
}