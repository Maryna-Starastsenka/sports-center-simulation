package main.modele;

import java.time.LocalDate;

/**
 * Classe Membre qui hérite de Client. Un membre est un usager du #GYM
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Membre extends Client {

	private boolean aPaye;

	/**
	 * Constructeur de Membre
	 * @param nom nom du membre
	 * @param dateNaissance date de naissance du membre
	 * @param adresse adresse du membre
	 * @param ville ville du membre
	 * @param province province du membre
	 * @param codePostal code postal du membre
	 * @param numeroPhone numéro de téléphone du membre
	 * @param adresseCourriel adresse courriel du membre
	 * @param aPaye statut du membre (frais d'adhésion sont payé ou pas)
	 */
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

	/**
	 * Retourne le statut du membre par rapport au paiement des frais d'adhésion
	 * @return statut du membre par rapport au paiement des frais d'adhésion
	 */
	@Override
	public String toString() {
		return super.toString() + "Statut : " + (getAPaye() ? "valide (a payé)\n" : "suspendu\n");
	}

}