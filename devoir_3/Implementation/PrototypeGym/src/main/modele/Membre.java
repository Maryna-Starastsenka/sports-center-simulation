package main.modele;

import java.time.LocalDate;

public class Membre extends Client {

	private boolean aPaye;

	public Membre(String nom, LocalDate dateNaissance, String adresse, String ville, String province, String codePostal,
				  String numeroPhone, String adresseCourriel, boolean aPaye) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.ville = ville;
		this.province = province;
		this.codePostal = codePostal;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
		this.aPaye = aPaye;
	}

	public boolean getAPaye() { return aPaye; }

	public void setAPaye(boolean aPaye) { this.aPaye = aPaye; }

	@Override
	public String toString() {
		return super.toString() + "Statut : " + (getAPaye() ? "valide (a payé)\n" : "suspendu\n");
	}

}