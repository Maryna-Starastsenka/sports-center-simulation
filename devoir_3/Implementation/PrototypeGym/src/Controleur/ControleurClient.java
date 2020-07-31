package Controleur;

import Modele.*;
import Vue.*;

import java.time.LocalDate;

import static Modele.Verificateurs.getDateFromString;

public class ControleurClient extends Controleur {

	private CentreDonneesMembre centreDonneesMembre;
	private CentreDonneesProfessionnel centreDonneesProfessionnel;

	public ControleurClient() {
		this.centreDonneesMembre = new CentreDonneesMembre();
		this.centreDonneesProfessionnel = new CentreDonneesProfessionnel();
	}

	public void creerClient(VueClient vueClient, String typeClient,
							String nom,
							String dateNaissanceString,
							String adresseCourriel,
							String numeroTelephone,
							String adresse) {
		LocalDate dateNaissance = getDateFromString(dateNaissanceString);
		Client client = null;
		switch (typeClient) {
			case "1": // membre qui a payé les frais d'adhésion
			case "2": // membre qui n'a pas payé les frais d'adhésion
				Membre membre = new Membre(nom, dateNaissance, adresse, numeroTelephone, adresseCourriel, typeClient.equals("1") ? true : false);
				client = centreDonneesMembre.creer(membre);
				break;
			case "3": // professionnel
				Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, numeroTelephone, adresseCourriel);
				client = centreDonneesProfessionnel.creer(professionnel);
				break;
		}
		if (typeClient.equals("1") || typeClient.equals("3")) {
			vueClient.confirmerEnregistrement(client.getHashInString());
		}
	}

	public void mettreClientAJour(String idClient) {

	}

	public void supprimerClient(String idClient) {

	}

	public void authentifier(VueClient vueClient, String idClient) {
		if (vueClient instanceof VueMembre) {
			Membre membre = centreDonneesMembre.lire(idClient);
			if (membre == null) {
				vueClient.accesRefuse(idClient);
			} else {
				vueClient.accesAutorise(membre);
				// TODO traiter les cas où le membre est suspendu ?
			}
		} else if (vueClient instanceof VueProfessionnel) {
			Professionnel professionnel = centreDonneesProfessionnel.lire(idClient);
			if (professionnel == null) {
				vueClient.accesRefuse(idClient);
			} else {
				vueClient.accesAutorise(professionnel);
			}
		}
	}

//	public static void authentifier(VuePlateforme vue) {
//		vue.authentifier();
//
//		Vue.afficherDemandeAcces();
//		String entree = Vue.getTexteConsole();
//		if (Arrays.asList("1", "2", "3", "X").contains(entree)) {
//			switch (entree) {
//				case "1": // Modele.Membre
//					afficherAutorisationMembre();
//					break;
//				case "2": // Modele.Professionnel
//					afficherAutorisationProfessionnel();
//					break;
//				case "3": // Retour au menu principal par défaut
//					break;
//				default:
//					break;
//			}
//		}
//	}
	
	
//	private void afficherAutorisationProfessionnel () {
//		String idProfessionnel;
//		do {
//			Vue.afficher("Entrez l'identifiant du professionnel puis appuyez sur ENTREE :");
//			idProfessionnel = Vue.getTexteConsole();
//		} while (idProfessionnel.length() != 9);
//		if (validerProfessionnel(idProfessionnel)) {
//			Vue.afficher("Le professionnel est autorisé à accéder au gym.");
//		} else {
//			Vue.afficher("Le professionnel n'est pas enregistré.");
//		}
//	}
//
//	private void afficherAutorisationMembre () {
//		afficherTousLesMembres();
//		String idMembre;
//		do {
//			Vue.afficher("Entrez l'identifiant du membre puis appuyez sur ENTREE :");
//			idMembre = Vue.getTexteConsole();
//		} while (idMembre.length() != 9);
//		if (validerMembre(idMembre)) {
//			Vue.afficher("Le membre est autorisé à accéder au gym.");
//		} else {
//			Vue.afficher("Le membre n'est pas autorisé à accéder au gym.");
//		}
//	}



//	public void gestionCompte() {
//		Vue.afficherGestionCompte();
//		String entree = Vue.getTexteConsole();
//		if (Arrays.asList("1", "2", "3", "X").contains(entree)) {
//			switch (entree) {
//			case "1":
//				formulaireNouveauCompte();
//				break;
//			case "2":
//				gererCompteExistant();
//				break;
//			case "3": // Retour au menu principal par défaut
//				break;
//			default:
//				break;
//			}
//		}
//	}
	
//	private void gererCompteExistant() {
//		Vue.afficher("Veuillez choisir le type de compte");
//		Vue.afficher("1. Compte membre");
//		Vue.afficher("2. Compte professionnel");
//		String entree = Vue.getTexteConsole();
//		switch (entree) {
//			case "1":
//				afficherTousLesMembres();
//				Vue.afficher("Veuillez entrer le numéro du membre ou entrer \"retour\" pour retourner au menu principal.");
//				String idMembre = Vue.getTexteConsole();;
//				while (!validerMembre(idMembre)&&!idMembre.equals("retour")) {
//					Vue.afficher("Numéro de membre invalide. Réessayez.");
//					idMembre = Vue.getTexteConsole();
//				}
//				if(!idMembre.equals("retour")) {
//					Membre membre = centreDonneesClient.getMembre(idMembre);
//					Vue.afficher(membre.toString());
//					Vue.afficher("Veuillez choisir l'action");
//					Vue.afficher("1. Modifier.");
//					Vue.afficher("2. Supprimer.");
//					String action = Vue.getTexteConsole();
//					gererMembreExistant(action, membre, idMembre);
//				}
//				break;
//			case "2":
//				afficherTousLesProfessionnels();
//				Vue.afficher("Veuillez entrer le numéro du professionnel");
//				String idProfessionnel = Vue.getTexteConsole();
//				while (!validerProfessionnel(idProfessionnel)&&!idProfessionnel.equals("retour")) {
//					Vue.afficher("Numéro de professionnel invalide. Réessayez.");
//					idProfessionnel = Vue.getTexteConsole();
//				}
//				Professionnel professionnel = centreDonneesClient.getProfessionnel(idProfessionnel);
//				Vue.afficher(professionnel.toString());
//				Vue.afficher("Veuillez choisir l'action");
//				Vue.afficher("1. Modifier.");
//				Vue.afficher("2. Supprimer.");
//				String actionProf = Vue.getTexteConsole();
//				gererProfessionnelExistant(actionProf, professionnel, idProfessionnel);
//				break;
//			case "3":
//				afficherMenuPrincipal();
//				break;
//			default:
//				break;
//		}
//	}
	
//	private void gererMembreExistant(String action, Membre membre, String idMembre) {
//		switch (action) {
//			case "1":
//				Vue.afficher("1. Modifier le statut du membre. Valeur actuelle : " + membre.getMembreValide());// todo faire les autres
//				Vue.afficher("2. Modifier le numéro de téléphone.");
//				Vue.afficher("3. Retour au menu principal.");
//				String modifMembre = Vue.getTexteConsole();
//
//				switch (modifMembre) {
//					case "1":
//						membre.setMembreValide(!membre.getMembreValide());
//						Vue.afficher("Modele.Membre modifié.");
//						break;
//					case "2":
//						Vue.afficher("Veuillez entrer le nouveau numéro de téléphone.");
//						String nouveauNumeroTel = Vue.getTexteConsole();
//						membre.setNumeroPhone(nouveauNumeroTel);
//						Vue.afficher("Modele.Membre modifié.");
//						break;
//					case "3":
//						afficherMenuPrincipal();
//						break;
//					default:
//						break;
//				}
//				break;
//			case "2":
//				Vue.afficher("1. Valider suppression.");
//				Vue.afficher("2. Retour au menu principal.");
//				String validationSuppression = Vue.getTexteConsole();
//				switch (validationSuppression) {
//					case "1":
//						centreDonneesClient.supprimerMembre(idMembre);
//						Vue.afficher("Modele.Membre supprimé.");
//						break;
//					case "2":
//						afficherMenuPrincipal();
//					default:
//						break;
//				}
//			case "3":
//				afficherMenuPrincipal();
//				break;
//			default:
//				break;
//		}
//	}
	
//	private void gererProfessionnelExistant (String action, Professionnel professionnel, String idProfessionnel) {
//		switch (action) {
//			case "1":
//				Vue.afficher("1. Modifier l'adresse.");// todo faire les autres
//				Vue.afficher("2. Modifier l'adresse courriel.");
//				Vue.afficher("3. Retour au menu principal.");
//				String modifProfessionnale = Vue.getTexteConsole();
//
//				switch (modifProfessionnale) {
//					case "1":
//						Vue.afficher("Veuillez entrer la nouvelle addresse.");
//						String nouvelleAdresse = Vue.getTexteConsole();
//						professionnel.setAdresse(nouvelleAdresse);
//						Vue.afficher("Modele.Professionnel modifié.");
//						break;
//					case "2":
//						Vue.afficher("Veuillez entrer la nouvelle addresse courriel.");
//						String nouvelleAdresseCourriel = Vue.getTexteConsole();
//						professionnel.setAdresseCourriel(nouvelleAdresseCourriel);
//						Vue.afficher("Modele.Membre modifié.");
//						break;
//					case "3":
//						afficherMenuPrincipal();
//						break;
//					default:
//						break;
//				}
//				break;
//			case "2":
//				Vue.afficher("1. Valider suppression.");
//				Vue.afficher("2. Retour au menu principal.");
//				String validationSuppression = Vue.getTexteConsole();
//					switch (validationSuppression) {
//						case "1":
//							centreDonneesClient.supprimerProfessionnel(idProfessionnel);
//							Vue.afficher("Modele.Professionnel supprimé.");
//							break;
//						case "2":
//							afficherMenuPrincipal();
//						default:
//							break;
//					}
//			case "3":
//				afficherMenuPrincipal();
//				break;
//			default:
//				break;
//		}
//	}

	
//	public void modifierStatutMembres() {
//		List<Membre> listeMembres = centreDonneesClient.getListeMembres();
//		for(Membre membre : listeMembres) {
//			Vue.afficher("Est-ce que le membre a payé? Répondre :  \"oui\" ou \"non\".");
//			Vue.afficher(membre.toString());
//			String entree = Vue.getTexteConsole();
//			while(!entree.equals("oui")&&!entree.equals("non")) {
//				entree = Vue.getTexteConsole();
//			}
//			if(entree.equals("oui")) {
//				membre.setMembreValide(true);
//			} else {
//				membre.setMembreValide(false);
//			}
//		}
//	}



//	public boolean validerMembre (String id){
//		return centreDonneesClient.estMembre(id);
//	}
//
//	public boolean membrePasSuspendu (String id) {
//		return centreDonneesClient.estMembreValide(id);
//	}
//
//	public boolean validerProfessionnel (String id){
//		return centreDonneesClient.estProfessionnel(id);
//	}
//
//	public HashMap<String, Professionnel> getHashMapProfessionnel(){
//    	return centreDonneesClient.getHashMapProfessionnel();
//    }
//
//	public void afficherTousLesMembres() {
//		Vue.afficher("Numéros des membres du centre de données (pour faciliter les tests) :");
//		var membres = centreDonneesClient.getListeMembres();
//		for (Membre m : membres) {
//			Vue.afficher(m.getHashInString());
//		}
//	}
//
//	public void afficherTousLesProfessionnels() {
//		Vue.afficher("Numéros des professionnels du centre de données (pour faciliter les tests) :");
//		var professionnels = centreDonneesClient.getListeProfessionnels();
//		for (Professionnel m : professionnels) {
//			Vue.afficher(m.getHashInString());
//		}
//	}
}
