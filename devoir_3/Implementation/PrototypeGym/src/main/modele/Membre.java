package main.modele;

import java.time.LocalDate;
import java.util.HashMap;

public class Membre extends Client {

	private boolean aPaye;
	private HashMap<String, Inscription> inscriptions;

	public Membre(String nom, LocalDate dateNaissance, String adresse, String numeroPhone, String adresseCourriel,
				  boolean aPaye) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
		this.aPaye = aPaye;
	}

	public boolean getAPaye() {return aPaye; }

	public void setAPaye(boolean aPaye) { this.aPaye = aPaye; }

	@Override
	public String toString() {
		return super.toString() + "Modele.Membre valide : " + this.getAPaye() + "\n";
	}

}