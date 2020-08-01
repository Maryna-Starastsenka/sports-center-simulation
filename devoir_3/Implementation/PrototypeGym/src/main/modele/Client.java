package main.modele;

import main.controleur.Verificateurs;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Client {
	protected String id;
	protected String nom;
	protected LocalDate dateNaissance;
	protected String adresseCourriel;
	protected String numeroPhone;
	protected String adresse;

	public String getId () { return id; }

	public String getNom () { return nom; }

	public LocalDate getDateNaissance() { return dateNaissance; }

	public String getAdresseCourriel() { return adresseCourriel; }

	public String getNumeroPhone() { return numeroPhone; }

	public String getAdresse() { return adresse; }

	public void setNom(String nom) { this.nom = nom; }

	public void setId(String id) { this.id = id; }

	public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

	public void setAdresseCourriel(String adresseCourriel) { this.adresseCourriel = adresseCourriel; }

	public void setNumeroPhone(String numeroPhone) { this.numeroPhone = numeroPhone; }

	public void setAdresse(String adresse) { this.adresse = adresse; }

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(this.adresseCourriel) % 1000000000); // 9 chiffres max
	}
	
	public static int hashCode(String adresseCourriel) {
		return Math.abs(Objects.hash(adresseCourriel) % 1000000000); // 9 chiffres max
	}

	public String getHashInString() {
		return String.format("%09d", this.hashCode());
	}
	
	public static String getHashInString(String adresseCourriel) {
		return String.format("%09d", hashCode(adresseCourriel));
	}

	@Override
	public String toString() {
		return "Nom : " + this.getNom() + "\n" + "Date de naissance : "
				+ Verificateurs.localDateFormatter.format(this.getDateNaissance())+ "\n" + "Adresse courriel : " + this.getAdresseCourriel() + "\n"
				+ "Numéro de téléphone : " + this.getNumeroPhone() + "\n" + "Adresse : " + this.getAdresse() + "\n";
	}

}