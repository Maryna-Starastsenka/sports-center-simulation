package Controleur;

import Modele.*;
import Vue.*;

import java.util.Arrays;
import java.util.HashMap;

public class Controleur {
	
	private ControleurService controleurService;
	private ControleurClient controleurClient;
	
	
	public Controleur() {
		this.controleurService = new ControleurService();
		this.controleurClient = new ControleurClient();
	}

	public void start() {
		menuLogiciel(); // TODO choix entre menuLogiciel et menuApplication
	}

	private boolean fermetureApplicationDemandee(String entree) {
		return entree.equals("X");
	}
	
	private void menuLogiciel() {
		String entree;
		String[] listEntree = new String[] {"1","2","3","4","5","6","7","8","X"};
		do {
			Gui.afficherMenuPrincipal();
			do {
				entree = Gui.getTexteConsole().toUpperCase();
				if (fermetureApplicationDemandee(entree)) {
					Gui.afficher("Fin du programme");
					System.exit(0);
				}
			} while (!Arrays.asList(listEntree).contains(entree));
			gererMenuPrincipal(entree);
		} while (true);
		
	}
	
	private void menuApplication() {
		
	}

	private void gererMenuPrincipal(String entreePrincipale) {
		String membreId;
		String idProfessionnel;
		switch (entreePrincipale) {
			case "1":
				controleurClient.demanderAcces();
				break;
			case "2":
				controleurClient.gestionCompte();
				break;
			case "3":
				Gui.afficher("--------Gestion d'un service-------");
				controleurClient.afficherTousLesProfessionnels();
				Gui.afficher("Veuillez entrer le numéro de professionnel ou \"retour\" pour retourner au menu principal");
				do {
					idProfessionnel = Gui.getTexteConsole();
				} while (!controleurClient.validerProfessionnel(idProfessionnel)&&!idProfessionnel.equals("retour"));
				if(!idProfessionnel.equals("retour"))
					controleurService.gererService(idProfessionnel);
				break;
			case "4":
				Gui.afficher("---Modele.Inscription à une séance---");
				controleurClient.afficherTousLesMembres();

				Gui.afficher("Veuillez entrer le numéro du membre :");
				membreId = Gui.getTexteConsole();
				if (!controleurClient.validerMembre(membreId)) {
					Gui.afficher("Modele.Membre inconnu. Retour au menu principal.");
					break;
				} else if (!controleurClient.membrePasSuspendu(membreId)) {
					Gui.afficher("Modele.Membre suspendu. Retour au menu principal.");
					break;
				}
				controleurService.inscriptionSeance(membreId);
				break;
			case "5":
				Gui.afficher("---Confirmation de la présence---");

				controleurClient.afficherTousLesMembres();
				Gui.afficher("Veuillez entrer le numéro du membre :");
				membreId = Gui.getTexteConsole();

				if (!controleurClient.validerMembre(membreId)) {
					Gui.afficher("Modele.Membre inconnu. Retour au menu principal.");
					break;
				} else if (!controleurClient.membrePasSuspendu(membreId)) {
					Gui.afficher("Modele.Membre suspendu. Retour au menu principal.");
					break;
				} 
				controleurService.confirmerPresence(membreId);
				break;
			case "6":
				Gui.afficher("---Consultation d'une séance---");
				controleurClient.afficherTousLesProfessionnels();
				Gui.afficher("Veuillez entrer le numéro du professionnel.");
				idProfessionnel = Gui.getTexteConsole();

				if (controleurClient.validerProfessionnel(idProfessionnel)) {
					controleurService.consultationInscription(idProfessionnel);
				} else {
					Gui.afficher("Numéro du professionnel incorrect.");
				}
				break;
			case "7":
				HashMap<String, Professionnel> hashMapProfessionnel = controleurClient.getHashMapProfessionnel();
				controleurService.procedureComptable(hashMapProfessionnel);
				break;
			case "8":
				controleurClient.modifierStatutMembres();
				break;
			default:
				break;
		}
		resetEnFinDeTransaction();
	}

	

	

	private void resetEnFinDeTransaction () {
		Gui.afficher("Appuyez sur ENTREE pour revenir à l'écran principal");
		Gui.getTexteConsole();
	}
		
}