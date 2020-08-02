package main.controleur;

import main.modele.*;
import main.vue.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static main.controleur.Verificateurs.getJourFromString;

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
			}
		}
	}

	public String inscriptionSeance(String membreId, String seanceId, String commentaire) {
		return centreDonneesServices.inscrireMembreASeance(membreId, seanceId, commentaire).getHashInString();
	}

	public boolean confirmerPresence(String idSeance, String idMembre, String commentaire) {
		return centreDonneesServices.confirmationPresence(idSeance, idMembre, commentaire);
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

	public String obtenirToutesLesSeancesDuJourEnString(LocalDate jour) {
		var seances = centreDonneesServices.getListeSeances(jour);
		String concatenation = "";
		for (Seance s : seances) {
			concatenation += s.getCodeSeance() + " (service de " +
					centreDonneesServices.getService(s.getCodeService()).getNomService() +
					") : le " +
					Verificateurs.localDateTimeFormatter.format(s.getDateTimeSeance()) + "\n";
		}
		return concatenation;
	}

	public List<String> obtenirListeIdSeancesDuJour(LocalDate jour) {
		var listeSeances = centreDonneesServices.getListeSeances(jour);
		List<String> idSeances = new LinkedList<>();
		for (Seance seance : listeSeances) {
			idSeances.add(seance.getCodeSeance());
		}
		return idSeances;
	}

	public String obtenirToutesLesSeancesDuProfessionnelEnString(String idProfessionnel) {
		var seances = centreDonneesServices.getListeSeancesPro(idProfessionnel);
		String concatenation = "";
		for (Seance s : seances) {
			concatenation += s.getCodeSeance() + " (service de " +
					centreDonneesServices.getService(s.getCodeService()).getNomService() +
					") : le " +
					Verificateurs.localDateTimeFormatter.format(s.getDateTimeSeance()) + "\n";
		}
		return concatenation;
	}

	public List<String> obtenirListeSeancesDuProfessionnel(String idProfessionnel) {
		var listeSeances = centreDonneesServices.getListeSeancesPro(idProfessionnel);
		List<String> idSeances = new LinkedList<>();
		for (Seance seance : listeSeances) {
			idSeances.add(seance.getCodeSeance());
		}
		return idSeances;
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

	public String getFraisService(String seanceId) {
		Seance seance = centreDonneesServices.getSeance(seanceId);
		Service service = centreDonneesServices.getService(seance.getCodeService());
		return "" + service.getFraisService();
	}

	public String getInformationSeance(String seanceId) {
		Seance seance = centreDonneesServices.getSeance(seanceId);
		String infos = "";

		if (seance != null) {
			infos = "Code séance : " + seance.getCodeSeance() + "\n" +
					"Code professionnel : " + seance.getCodeProfessionnel() + "\n" +
					"Date de la séance : " + seance.getDateTimeSeanceString() + "\n";
		}
		return infos;
	}

	public String getInformationsInscription(String inscriptionId) {
		Inscription inscription = centreDonneesServices.getInscription(inscriptionId);
		String infos = "";

		if (inscription != null) {
			infos = "Date et heure d'inscription : " + inscription.getDateEtHeureActuelleString() + "\n" +
					"Date de la séance : " + inscription.getDateSeanceString() + "\n" +
					"Numéro de professionnel : " + inscription.getNumeroProfessionnel() + "\n" +
					"Numéro de membre : " + inscription.getNumeroMembre() + "\n" +
					"Code de service : " + inscription.getCodeService() + "\n" +
					"Commentaires : " + inscription.getCommentaires() + "\n";
		}
		return infos;
	}

	public String obtenirInscriptionsASeanceEnString(String idSeance) {
		var listeInscriptions = obtenirInscriptionsASeance(idSeance);
		String inscriptions = "";
		for (Inscription inscription : listeInscriptions) {
			inscriptions += "Membre " + inscription.getNumeroMembre() +
					(inscription.getCommentaires().length() != 0 ? "; Commentaires : " : "") +
					inscription.getCommentaires() + "\n";
		}
		return inscriptions;
	}

	public List<Inscription> obtenirInscriptionsASeance(String idSeance) {
		return centreDonneesServices.getListeInscriptionsSeance(idSeance);
	}

	public boolean inscriptionExiste(String idMembre, String idSeance) {
		return centreDonneesServices.inscriptionExiste(idMembre, idSeance);
	}
	
	public Service lire(String idService) {
		return centreDonneesServices.lire(idService);
	}
}
