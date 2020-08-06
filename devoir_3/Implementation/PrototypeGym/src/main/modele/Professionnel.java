package main.modele;

import java.time.LocalDate;
import java.util.HashMap;

public class Professionnel extends Client {
	
	private HashMap<String, Service> listeServices = new HashMap<>();//TODO remplacer par une expertise unique String

	public Professionnel (String nom, LocalDate dateNaissance, String adresse, String ville, String province, String codePostal, String numeroPhone, String adresseCourriel) {
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
