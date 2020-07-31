package Controleur;

import Modele.*;
import Vue.*;

import java.util.Arrays;
import java.util.HashMap;

public abstract class Controleur {

	public Controleur() {

	}

//	public static void start() {
//		menuLogiciel(); // TODO choix entre menuLogiciel et menuApplication
//	}

	private boolean fermetureApplicationDemandee(String entree) {
		return entree.equals("X");
	}
	
//	private static void menuLogiciel() {
//		String entree;
//		String[] listEntree = new String[] {"1","2","3","4","5","6","7","8","X"};
//		do {
//			afficherMenuPrincipal();
//			do {
//				entree = Vue.getTexteConsole().toUpperCase();
//				if (fermetureApplicationDemandee(entree)) {
//					Vue.afficher("Fin du programme");
//					System.exit(0);
//				}
//			} while (!Arrays.asList(listEntree).contains(entree));
//			gererMenuPrincipal(entree);
//		} while (true);
//	}
	
	private void menuApplication() {
		
	}

//	private void gererMenuPrincipal(String entreePrincipale) {
//		String membreId;
//		String idProfessionnel;
//		switch (entreePrincipale) {
//			case "1":
//				controleurClient.demanderAcces();
//				break;
//			case "2":
//				controleurClient.gestionCompte();
//				break;
//			case "3":
//				Vue.afficher("--------Gestion d'un service-------");
//				controleurClient.afficherTousLesProfessionnels();
//				Vue.afficher("Veuillez entrer le numéro de professionnel ou \"retour\" pour retourner au menu principal");
//				do {
//					idProfessionnel = Vue.getTexteConsole();
//				} while (!controleurClient.validerProfessionnel(idProfessionnel)&&!idProfessionnel.equals("retour"));
//				if(!idProfessionnel.equals("retour"))
//					controleurService.gererService(idProfessionnel);
//				break;
//			case "4":
//				Vue.afficher("---Modele.Inscription à une séance---");
//				controleurClient.afficherTousLesMembres();
//
//				Vue.afficher("Veuillez entrer le numéro du membre :");
//				membreId = Vue.getTexteConsole();
//				if (!controleurClient.validerMembre(membreId)) {
//					Vue.afficher("Modele.Membre inconnu. Retour au menu principal.");
//					break;
//				} else if (!controleurClient.membrePasSuspendu(membreId)) {
//					Vue.afficher("Modele.Membre suspendu. Retour au menu principal.");
//					break;
//				}
//				controleurService.inscriptionSeance(membreId);
//				break;
//			case "5":
//				Vue.afficher("---Confirmation de la présence---");
//
//				controleurClient.afficherTousLesMembres();
//				Vue.afficher("Veuillez entrer le numéro du membre :");
//				membreId = Vue.getTexteConsole();
//
//				if (!controleurClient.validerMembre(membreId)) {
//					Vue.afficher("Modele.Membre inconnu. Retour au menu principal.");
//					break;
//				} else if (!controleurClient.membrePasSuspendu(membreId)) {
//					Vue.afficher("Modele.Membre suspendu. Retour au menu principal.");
//					break;
//				}
//				controleurService.confirmerPresence(membreId);
//				break;
//			case "6":
//				Vue.afficher("---Consultation d'une séance---");
//				controleurClient.afficherTousLesProfessionnels();
//				Vue.afficher("Veuillez entrer le numéro du professionnel.");
//				idProfessionnel = Vue.getTexteConsole();
//
//				if (controleurClient.validerProfessionnel(idProfessionnel)) {
//					controleurService.consultationInscription(idProfessionnel);
//				} else {
//					Vue.afficher("Numéro du professionnel incorrect.");
//				}
//				break;
//			case "7":
//				HashMap<String, Professionnel> hashMapProfessionnel = controleurClient.getHashMapProfessionnel();
//				controleurService.procedureComptable(hashMapProfessionnel);
//				break;
//			case "8":
//				controleurClient.modifierStatutMembres();
//				break;
//			default:
//				break;
//		}
//		resetEnFinDeTransaction();
//	}

	private void resetEnFinDeTransaction () {
		Vue.afficher("Appuyez sur ENTREE pour revenir à l'écran principal");
		Vue.getTexteConsole();
	}
		
}