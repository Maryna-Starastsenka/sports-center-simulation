import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class Controleur {
	
	private ControleurService controleurService;
	private ControleurClient controleurClient;
	
	
	public Controleur() {
		this.controleurService = new ControleurService();
		this.controleurClient = new ControleurClient();
	}

	public void start() {
		String entree;
		do {
			Gui.afficherMenuPrincipal();

			entree = Gui.getTexteConsole().toUpperCase();
			if (fermetureApplicationDemandee(entree)) {
				System.exit(0);
			}
			gererMenuPrincipal(entree);
		} while (true);
	}

	private boolean fermetureApplicationDemandee(String entree) {
		return entree.equals("X");
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
				Gui.afficher("Veuillez entrer le numéro de professionnel");
				
				do {
					idProfessionnel = Gui.getTexteConsole();
				} while (!controleurClient.validerProfessionnel(idProfessionnel) /*|| fermetureApplicationDemandee(idProfessionnel)*/);
				controleurService.gererService(idProfessionnel);
				break;
			case "4":
				Gui.afficher("---Inscription à une séance---");
				controleurClient.afficherTousLesMembres();

				Gui.afficher("Veuillez entrer le numéro du membre :");
				membreId = Gui.getTexteConsole();
				if (!controleurClient.validerMembre(membreId)) {
					Gui.afficher("Membre inconnu. Retour au menu principal.");
					break;
				} else if (!controleurClient.membrePasSuspendu(membreId)) {
					Gui.afficher("Membre suspendu. Retour au menu principal.");
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
					Gui.afficher("Membre inconnu. Retour au menu principal.");
					break;
				} else if (!controleurClient.membrePasSuspendu(membreId)) {
					Gui.afficher("Membre suspendu. Retour au menu principal.");
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