import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ControleurService {

	private CentreDonneesServices centreDonneesServices;
	
	
public ControleurService() {
		
		this.centreDonneesServices = new CentreDonneesServices();
	}
	
	
	public void gererService(String idProfessionnel) {
		
		Gui.afficherGestionService();
		String entree = Gui.getTexteConsole();
		
		if (Arrays.asList("1", "2", "3", "X").contains(entree)) {
			switch (entree) {
			case "1":
				formulaireNouveauService(idProfessionnel);
				break;
			case "2":
				List<Service> servicesDuProfessionnel = centreDonneesServices.getListeServicesPro(idProfessionnel);
				afficherServicesProfessionnel(servicesDuProfessionnel);
				gererServiceExistant();
				break;
			case "3": // Retour au menu principal par d�faut
				break;
			default:
				break;
			}
		}
	}
	
	public void inscriptionSeance(String membreId) {
		Gui.afficher("R�f�rences des s�ances disponibles ajourd'hui, le " + CentreDonnees.today() + " :");
		afficherToutesLesSeancesDuJour(CentreDonnees.today());

		Gui.afficher("Veuillez entrer la r�f�rence de la s�ance � laquelle vous voulez inscrire un membre ou appuyez sur ENTREE pour revenir au menu principal :");
		String seanceId = Gui.getTexteConsole();
		if (!seanceId.equals("")) {

			Seance seance = centreDonneesServices.getSeance(seanceId);
			Service service = centreDonneesServices.getService(seance.getCodeService());
			Gui.afficher("Les frais � payer pour la s�ance sont de : " + service.getFraisService() + "$");

			Gui.afficher("1. Continuer inscription");
			Gui.afficher("2. Quitter et revenir au menu principal");
			String entree = Gui.getTexteConsole();
			switch (entree) {
			case "1":
				Gui.afficher("Le paiement est-il valide ?");
				Gui.afficher("1. Oui");
				Gui.afficher("2. Non");
				entree = Gui.getTexteConsole();

				if (entree.equals("1")) {
					Gui.afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
					String commentaire = Gui.getTexteConsole();

					centreDonneesServices.inscrireMembreASeance(membreId, seanceId, commentaire);

					Gui.afficher("Le membre " +
							membreId +
							" a �t� inscrit � la s�ance " +
							seanceId +
							" qui aura lieu le " +
							CentreDonneesServices.localDateTimeFormatter.format(centreDonneesServices.getSeance(seanceId).getDateTimeSeance()));
				} else {
					Gui.afficher("Annulation de l'inscription. Retour au menu principal.");
				}

				break;
			case "2":
				break;
			default:
				break;
			}
		}
	}
	
	public void confirmerPresence(String membreId) {
		
		Gui.afficher("R�f�rences des s�ances disponibles ajourd'hui, le " + CentreDonnees.today() + " :");
		afficherToutesLesSeancesDuJour(CentreDonneesServices.today());

		Gui.afficher("Veuillez entrer la r�f�rence de la s�ance � laquelle vous voulez inscrire un membre ou appuyez sur ENTREE pour revenir au menu principal :");
		String seanceId = Gui.getTexteConsole();
		if (seanceId.equals("")) {
			return;
		}else if (!centreDonneesServices.inscriptionExiste(membreId, seanceId)) {
			Gui.afficher("Le membre n'est pas inscrit. Acc�s refus�.");
			return;
		}else {
			
			Gui.afficher("Confirmer la pr�sence ?");
			Gui.afficher("1. Oui");
			Gui.afficher("2. Non");
			String entree = Gui.getTexteConsole();

			if (entree.equals("1")) {
				Gui.afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
				String commentaire = Gui.getTexteConsole();

				centreDonneesServices.confirmationPresence(seanceId, membreId, commentaire);
				Gui.afficher("Pr�sence valid�e.");

			} else {
				Gui.afficher("Annulation de la confirmation.");
			}
		}
	}
	
	public void consultationInscription(String idProfessionnel) {
		Gui.afficher("Inscriptions aux s�ances du professionnel " + idProfessionnel);
		afficherToutesLesInscriptionDuPro(idProfessionnel);
	}
	
	public void procedureComptable(HashMap<String, Professionnel> listeProfessionnels) {
		
		Gui.afficher("---Proc�dure comptable---");
		Gui.afficher("Voulez-vous g�n�rer et afficher le rapport de synth�se ?");
		Gui.afficher("1. Oui");
		Gui.afficher("2. Non");
		String entree = Gui.getTexteConsole();

		if (entree.equals("1")) {
			var rapport = centreDonneesServices.genererRapportSynthese(listeProfessionnels);
			Gui.afficher("Liste des professionnels � payer :");

			for (ProfessionnelTef pro : rapport.getProTef()) {
				Gui.afficher(String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donn�s cette semaine.",
						pro.getNom(),
						pro.getNumero(),
						pro.getMontant(),
						pro.getNombreServices()));
			}
			Gui.afficher("\n* Nombre total de professionnels � payer : " + rapport.getNombreTotalProfessionnels());
			Gui.afficher("* Nombre total de services : " + rapport.getNombreTotalServices());
			Gui.afficher("* Nombre total des frais � payer : " + rapport.getTotalFrais() + "$");

			Gui.afficher("\nUne copie du rapport a �t� envoy�e au g�rant.");

		} else {
			Gui.afficher("Annulation de la g�n�ration du rapport de synth�se.");
		}
		
	}
	
	
	
	
	private void afficherServicesProfessionnel(List<Service> servicesDuProfessionnel) {
		Gui.effacerEcran();

		Gui.afficher("------Services du professionnel------");
		Gui.afficher("Liste des services disponibles pour ce professionnel :");
		for (Service s : servicesDuProfessionnel) {
			Gui.afficher(s.getCode() + " (" + s.getNomService() + ")");
		}
	}
	
	
	private void gererServiceExistant() {
		Gui.afficher("Veuillez choisir un service :");
		String serviceEntre = Gui.getTexteConsole();
		Service serviceAModifier = centreDonneesServices.getService(serviceEntre);

		Gui.afficher("1. Modifier :");
		Gui.afficher("2. Supprimer :");

		String modifOuSuppr = Gui.getTexteConsole();
		switch (modifOuSuppr) {
			case "1":
				Gui.afficher("1. Modifier r�currence hebdo. Valeur actuelle : " + serviceAModifier.getRecurrenceHebdo());// todo faire les autres
				Gui.afficher("2. Modifier heure du service. Valeur actuelle : " + serviceAModifier.getHeureService());
				String modifChamps = Gui.getTexteConsole();
				switch (modifChamps) {
					case "1":
						Gui.afficher("Entrez la nouvelle valeur :");
						String nouvelleRecurrence = Gui.getTexteConsole();
						serviceAModifier.setRecurrenceHebdo(nouvelleRecurrence);
						Gui.afficher("Service modifi�.");
						break;

					case "2":
						Gui.afficher("Entrez la nouvelle valeur :");
						String entree = Gui.getTexteConsole();
						LocalTime nouvelleHeure = getHoraire(entree);
						serviceAModifier.setHeureService(nouvelleHeure);
						Gui.afficher("Service modifi�.");
						break;
					default:
						break;
				}

				break;
			case "2":
				centreDonneesServices.supprimerService(serviceEntre);
				Gui.afficher("Service " + serviceEntre + " supprim�.");
				break;
			default:
				break;
		}
	}

	private void formulaireNouveauService(String idProfessionnel) {
		Gui.effacerEcran();

		Gui.afficher("------Formulaire de nouveau service------");

		String entree;

		String nomService;
		LocalDateTime dateEtHeureActuelles = null;
		LocalDate dateDebutService = null;
		LocalDate dateFinService = null;
		LocalTime heureService;
		int recurrenceHebdo;
		int capaciteMaximale;
		String numeroProfessionnel = idProfessionnel;
		String codeService;
		double fraisService;
		String commentaires;

		Gui.afficher("Veuillez entrer le nom du service :");
		nomService = Gui.getTexteConsole();

		dateEtHeureActuelles = CentreDonnees.now();

		do {
			Gui.afficher("Veuillez entrer la date de d�but du service (jj-mm-aaaa) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		dateDebutService = getDateFromString(entree);

		do {
			Gui.afficher("Veuillez entrer la date de fin du service (jj-mm-aaaa) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		dateFinService = getDateFromString(entree);


		do {
			Gui.afficher("Veuillez entrer l'heure du service (hh:mm) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		heureService = getHoraire(entree);

		do {
			Gui.afficher("Veuillez entrer la r�currence hebdomadaire (1-7) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		recurrenceHebdo = getInt(entree);//getHoraire(entree);

		do {
			Gui.afficher("Veuillez entrer la capacit� maximale (1-30) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		capaciteMaximale = getInt(entree);

		do {
			Gui.afficher("Veuillez entrer le code du service (XXXXXXX) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		codeService = entree;

		do {
			Gui.afficher("Veuillez entrer les frais du service (XXX.XX) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		fraisService = getDouble(entree);

		do {
			Gui.afficher("Veuillez entrer les commentaires :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		commentaires = entree;

		Service service = new Service(nomService,
				dateEtHeureActuelles,
				dateDebutService,
				dateFinService,
				heureService,
				recurrenceHebdo,
				capaciteMaximale,
				numeroProfessionnel,
				codeService,
				fraisService,
				commentaires);
		centreDonneesServices.ajouterService(service);

		Gui.afficher("Service " + service.getCode() + " enregistr�.");
	}
	
	private void afficherToutesLesSeancesDuJour(LocalDate jour) {
		var seances = centreDonneesServices.getListeSeances(jour);
		for (Seance s : seances) {
			Gui.afficher(s.getHashInString() + " (service de " +
					centreDonneesServices.getService(s.getCodeService()).getNomService() +
					") : le " +
					CentreDonnees.localDateTimeFormatter.format(s.getDateTimeSeance()));
		}
	}
	
	private void afficherToutesLesInscriptionDuPro(String idProfessionnel) {
		var inscriptions = centreDonneesServices.getListeInscriptionsPro(idProfessionnel);
		if (inscriptions.size() == 0) {
			Gui.afficher("Aucune inscription");
		} else {
			for (Inscription i : inscriptions) {
				Gui.afficher("S�ance " + i.getHashInString() + " date " + i.getDateSeance() + " membre "
						+ i.getNumeroMembre() + ". Commentaire : " + i.getCommentaires());
			}
		}
	}
	
	public static LocalDate getDateFromString (String stringDate) {
		return LocalDate.parse(stringDate, CentreDonnees.localDateFormatter);
	}
	
	public static LocalTime getHoraire (String stringHoraire){
		return LocalTime.parse(stringHoraire, CentreDonnees.localTimeFormatter);
	}
	
	public int getInt (String stringInt){
		return Integer.parseInt(stringInt);
	}

	public double getDouble (String stringDouble){
		return Double.parseDouble(stringDouble);
	}
	
}