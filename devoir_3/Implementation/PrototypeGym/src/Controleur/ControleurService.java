package Controleur;

import Modele.*;
import Vue.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static Controleur.Verificateurs.getJourFromString;

public class ControleurService extends Controleur {

	private CentreDonneesServices centreDonneesServices;


	public ControleurService() {
		this.centreDonneesServices = new CentreDonneesServices();
	}

	public void creerService(String nomService,
							 String dateDebutServiceString,
							 String dateFinServiceString,
							 String heureServiceString,
							 String recurrenceHebdoString,
							 String capaciteMaximaleString,
							 String numeroProfessionnel,
							 String codeService,
							 String fraisServiceString,
							 String commentaires) {

		LocalDateTime dateEtHeureActuelles = Verificateurs.now();
		LocalDate dateDebutService = LocalDate.from(Verificateurs.localDateFormatter.parse(dateDebutServiceString));
		LocalDate dateFinService = LocalDate.from(Verificateurs.localDateFormatter.parse(dateFinServiceString));
		LocalTime heureService = LocalTime.from(Verificateurs.localTimeFormatter.parse(heureServiceString));
		Jour recurrenceHebdo = Jour.valueOf(recurrenceHebdoString.toUpperCase());
		int capaciteMaximale = Integer.parseInt(capaciteMaximaleString);
		double fraisService = Double.parseDouble(fraisServiceString);

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
	}

	public void mettreServiceAJour(String idService, Champs champs, String valeur) {
		centreDonneesServices.mettreAJour(idService, champs, valeur);
	}

	public void supprimerService(String idService) {
			centreDonneesServices.supprimer(idService);
	}

	public String getListeService(VueService vue, String idProfessionnel) {
		List<Service> services = centreDonneesServices.getListeServicesPro(idProfessionnel);

		String servicesString = "";

		if (services.size() != 0) {
			for (Service s : services) {
				servicesString += s.getNomService() + " " + s.getCode() + "; ";
			}
		}
		return servicesString;
	}

	public void gererService(String idProfessionnel) {

//		Vue.afficherGestionService();
		String entree = Vue.getTexteConsole();

		if (Arrays.asList("1", "2", "3", "X").contains(entree)) {
			switch (entree) {
			case "1":
//				formulaireNouveauService(idProfessionnel);
				break;
			case "2":
				List<Service> servicesDuProfessionnel = centreDonneesServices.getListeServicesPro(idProfessionnel);
				afficherServicesProfessionnel(servicesDuProfessionnel);
				gererServiceExistant();
				break;
			case "3": // Retour au menu principal par défaut
				break;
			default:
				break;
			}
		}
	}

	public void inscriptionSeance(String membreId) {
		Vue.afficher("Références des séances disponibles aujourd'hui, le " + Verificateurs.today() + " :");
		afficherToutesLesSeancesDuJour(Verificateurs.today());

		Vue.afficher("Veuillez entrer la référence de la séance à laquelle vous voulez inscrire un membre ou appuyez sur ENTREE pour revenir au menu principal :");
		String seanceId = Vue.getTexteConsole();
		if (!seanceId.equals("")) {

			Seance seance = centreDonneesServices.getSeance(seanceId);
			Service service = centreDonneesServices.getService(seance.getCodeService());
			Vue.afficher("Les frais à payer pour la séance sont de : " + service.getFraisService() + "$");

			Vue.afficher("1. Continuer inscription");
			Vue.afficher("2. Quitter et revenir au menu principal");
			String entree = Vue.getTexteConsole();
			switch (entree) {
			case "1":
				Vue.afficher("Le paiement est-il valide ?");
				Vue.afficher("1. Oui");
				Vue.afficher("2. Non");
				entree = Vue.getTexteConsole();

				if (entree.equals("1")) {
					Vue.afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
					String commentaire = Vue.getTexteConsole();

					centreDonneesServices.inscrireMembreASeance(membreId, seanceId, commentaire);

					Vue.afficher("Le membre " +
							membreId +
							" a été inscrit à la séance " +
							seanceId +
							" qui aura lieu le " +
							Verificateurs.localDateTimeFormatter.format(centreDonneesServices.getSeance(seanceId).getDateTimeSeance()));
				} else {
					Vue.afficher("Annulation de l'inscription. Retour au menu principal.");
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

		Vue.afficher("Références des séances disponibles ajourd'hui, le " + Verificateurs.today() + " :");
		afficherToutesLesSeancesDuJour(Verificateurs.today());

		Vue.afficher("Veuillez entrer la référence de la séance à laquelle vous voulez inscrire un membre ou appuyez sur ENTREE pour revenir au menu principal :");
		String seanceId = Vue.getTexteConsole();
		if (seanceId.equals("")) {
			return;
		}else if (!centreDonneesServices.inscriptionExiste(membreId, seanceId)) {
			Vue.afficher("Le membre n'est pas inscrit. Accès refusé.");
			return;
		}else {

			Vue.afficher("Confirmer la présence ?");
			Vue.afficher("1. Oui");
			Vue.afficher("2. Non");
			String entree = Vue.getTexteConsole();

			if (entree.equals("1")) {
				Vue.afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
				String commentaire = Vue.getTexteConsole();

				centreDonneesServices.confirmationPresence(seanceId, membreId, commentaire);
				Vue.afficher("Présence validée.");

			} else {
				Vue.afficher("Annulation de la confirmation.");
			}
		}
	}

	public void consultationInscription(String idProfessionnel) {
		Vue.afficher("Inscriptions aux séances du professionnel " + idProfessionnel);
		afficherToutesLesInscriptionDuPro(idProfessionnel);
	}

	public void procedureComptable(HashMap<String, Professionnel> listeProfessionnels) {

		Vue.afficher("---Procédure comptable---");
		Vue.afficher("Voulez-vous générer et afficher le rapport de synthèse ?");
		Vue.afficher("1. Oui");
		Vue.afficher("2. Non");
		String entree = Vue.getTexteConsole();

		if (entree.equals("1")) {
			var rapport = centreDonneesServices.genererRapportSynthese(listeProfessionnels);
			Vue.afficher("Liste des professionnels à payer :");

			for (ProfessionnelTef pro : rapport.getProTef()) {
				Vue.afficher(String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donnés cette semaine.",
						pro.getNom(),
						pro.getNumero(),
						pro.getMontant(),
						pro.getNombreServices()));
			}
			Vue.afficher("\n* Nombre total de professionnels à payer : " + rapport.getNombreTotalProfessionnels());
			Vue.afficher("* Nombre total de services : " + rapport.getNombreTotalServices());
			Vue.afficher("* Nombre total des frais à payer : " + rapport.getTotalFrais() + "$");

			Vue.afficher("\nUne copie du rapport a été envoyée au gérant.");

		} else {
			Vue.afficher("Annulation de la génération du rapport de synthèse.");
		}

	}


	private void afficherServicesProfessionnel(List<Service> servicesDuProfessionnel) {
		Vue.effacerEcran();

		Vue.afficher("------Services du professionnel------");
		Vue.afficher("Liste des services disponibles pour ce professionnel :");
		for (Service s : servicesDuProfessionnel) {
			Vue.afficher(s.getCode() + " (" + s.getNomService() + ")");
		}
	}


	private void gererServiceExistant() {
		Vue.afficher("Veuillez choisir un service :");
		String serviceEntre = Vue.getTexteConsole();
		Service serviceAModifier = centreDonneesServices.getService(serviceEntre);

		Vue.afficher("1. Modifier :");
		Vue.afficher("2. Supprimer :");

		String modifOuSuppr = Vue.getTexteConsole();
		switch (modifOuSuppr) {
			case "1":
				Vue.afficher("1. Modifier récurrence hebdo. Valeur actuelle : " + serviceAModifier.getRecurrenceHebdo());// todo faire les autres
				Vue.afficher("2. Modifier heure du service. Valeur actuelle : " + serviceAModifier.getHeureService());
				String modifChamps = Vue.getTexteConsole();
				switch (modifChamps) {
					case "1":
						Vue.afficher("Entrez la nouvelle valeur :");
						String nouvelleRecurrence = Vue.getTexteConsole();
						serviceAModifier.setRecurrenceHebdo(getJourFromString(nouvelleRecurrence));
						Vue.afficher("Modele.Service modifié.");
						break;

					case "2":
						Vue.afficher("Entrez la nouvelle valeur :");
						String entree = Vue.getTexteConsole();
						LocalTime nouvelleHeure = getHoraire(entree);
						serviceAModifier.setHeureService(nouvelleHeure);
						Vue.afficher("Modele.Service modifié.");
						break;
					default:
						break;
				}

				break;
			case "2":
				centreDonneesServices.supprimerService(serviceEntre);
				Vue.afficher("Modele.Service " + serviceEntre + " supprimé.");
				break;
			default:
				break;
		}
	}

//	private void formulaireNouveauService(String idProfessionnel) {
//		Vue.effacerEcran();
//
//		Vue.afficher("------Formulaire de nouveau service------");
//
//		String entree;
//
//		String nomService;
//		LocalDateTime dateEtHeureActuelles = null;
//		LocalDate dateDebutService = null;
//		LocalDate dateFinService = null;
//		LocalTime heureService;
//		int recurrenceHebdo;
//		int capaciteMaximale;
//		String numeroProfessionnel = idProfessionnel;
//		String codeService;
//		double fraisService;
//		String commentaires;
//
//		Vue.afficher("Veuillez entrer le nom du service :");
//		nomService = Vue.getTexteConsole();
//
//		dateEtHeureActuelles = Verificateurs.now();
//
//		do {
//			Vue.afficher("Veuillez entrer la date de début du service (jj-mm-aaaa) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		dateDebutService = Verificateurs.getDateFromString(entree);
//
//		do {
//			Vue.afficher("Veuillez entrer la date de fin du service (jj-mm-aaaa) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		dateFinService = Verificateurs.getDateFromString(entree);
//
//
//		do {
//			Vue.afficher("Veuillez entrer l'heure du service (hh:mm) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		heureService = getHoraire(entree);
//
//		do {
//			Vue.afficher("Veuillez entrer la récurrence hebdomadaire (1-7) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		recurrenceHebdo = getInt(entree);//getHoraire(entree);
//
//		do {
//			Vue.afficher("Veuillez entrer la capacité maximale (1-30) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		capaciteMaximale = getInt(entree);
//
//		do {
//			Vue.afficher("Veuillez entrer le code du service (XXXXXXX) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		codeService = entree;
//
//		do {
//			Vue.afficher("Veuillez entrer les frais du service (XXX.XX) :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		fraisService = getDouble(entree);
//
//		do {
//			Vue.afficher("Veuillez entrer les commentaires :");
//			entree = Vue.getTexteConsole();
//		} while (false); //todo
//		commentaires = entree;
//
//		Service service = new Service(nomService,
//				dateEtHeureActuelles,
//				dateDebutService,
//				dateFinService,
//				heureService,
//				recurrenceHebdo,
//				capaciteMaximale,
//				numeroProfessionnel,
//				codeService,
//				fraisService,
//				commentaires);
//		centreDonneesServices.ajouterService(service);
//
//		Vue.afficher("Modele.Service " + service.getCode() + " enregistré.");
//	}

	private void afficherToutesLesSeancesDuJour(LocalDate jour) {
		var seances = centreDonneesServices.getListeSeances(jour);
		for (Seance s : seances) {
			Vue.afficher(s.getHashInString() + " (service de " +
					centreDonneesServices.getService(s.getCodeService()).getNomService() +
					") : le " +
					Verificateurs.localDateTimeFormatter.format(s.getDateTimeSeance()));
		}
	}

	private void afficherToutesLesInscriptionDuPro(String idProfessionnel) {
		var inscriptions = centreDonneesServices.getListeInscriptionsPro(idProfessionnel);
		if (inscriptions.size() == 0) {
			Vue.afficher("Aucune inscription");
		} else {
			for (Inscription i : inscriptions) {
				Vue.afficher("Séance " + i.getHashInString() + " date " + i.getDateSeance() + " membre "
						+ i.getNumeroMembre() + ". Commentaire : " + i.getCommentaires());
			}
		}
	}

	public static LocalTime getHoraire (String stringHoraire){
		return LocalTime.parse(stringHoraire, Verificateurs.localTimeFormatter);
	}

	public int getInt (String stringInt){
		return Integer.parseInt(stringInt);
	}

	public double getDouble (String stringDouble){
		return Double.parseDouble(stringDouble);
	}

	public String getInformationsService(String idService) {
		Service service = null;
		String infos = "";
		service = centreDonneesServices.lire(idService);

		if (service != null) {
			infos = "ID : " + service.getCode() + "\n" +
					"Nom de service : " + service.getNomService() + "\n" +
					"Date de début de service : " + service.getDateDebutService() + "\n" +
					"Date de fin de service : " + service.getDateFinService() + "\n" +
					"Heure de service : " + service.getHeureService() + "\n" +
					"Récurrence hebdomadaire : " + service.getRecurrenceHebdo() + "\n" +
					"Capacité maximale : " + service.getCapaciteMaximale() + "\n" +
					"Numéro de professionnel : " + service.getNumeroProfessionnel() + "\n" +
					"Frais de service : " + service.getFraisService() + "\n" +
					"Commentaire : " + service.getCommentaires() + "\n";
		}
		return infos;
	}
}
