package main.modele;

import java.time.LocalDate;

/**
 * Classe Professionnel qui hérite de Client. Un Professionnel est un client du #GYM qui fournit un service
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Professionnel extends Client {

	/**
	 * Constructeur de Professionnel
	 * @param nom nom du professionnel
	 * @param dateNaissance date de naissance du professionnel
	 * @param adresse adresse du professionnel
	 * @param ville ville du professionnel
	 * @param province province du professionnel
	 * @param codePostal code postal du professionnel
	 * @param numeroPhone numéro de téléphone du professionnel
	 * @param adresseCourriel adresse courriel du professionnel
	 */
	public Professionnel (String nom, LocalDate dateNaissance, String adresse, String ville, String province,
						  String codePostal, String numeroPhone, String adresseCourriel) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.ville = ville;
		this.province = province;
		this.codePostal = codePostal;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
	}
}
