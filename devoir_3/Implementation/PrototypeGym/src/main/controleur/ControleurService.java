package main.controleur;

import main.modele.*;
import main.vue.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static main.controleur.Verificateurs.getIntFromString;
import static main.controleur.Verificateurs.getJourFromString;

public class ControleurService extends Controleur {

	protected static CentreDonneesServices centreDonneesServices = new CentreDonneesServices();

	public ControleurService() {
	}

	public void creerService(String nomService,
							 String dateDebutServiceString,
							 String dateFinServiceString,
							 String heureServiceString,
							 String recurrenceHebdoString,
							 String capaciteMaximaleString,
							 String numeroProfessionnel,
							 String fraisServiceString,
							 String commentaires) {

		LocalDateTime dateEtHeureActuelles = Verificateurs.now();
		LocalDate dateDebutService = LocalDate.from(Verificateurs.localDateFormatter.parse(dateDebutServiceString));
		LocalDate dateFinService = LocalDate.from(Verificateurs.localDateFormatter.parse(dateFinServiceString));
		LocalTime heureService = LocalTime.from(Verificateurs.localTimeFormatter.parse(heureServiceString));
		Jour recurrenceHebdo = Jour.valueOf(recurrenceHebdoString.toUpperCase());
		int capaciteMaximale = getIntFromString(capaciteMaximaleString);
		double fraisService = Double.parseDouble(fraisServiceString);

		Service service = new Service(nomService,
				dateEtHeureActuelles,
				dateDebutService,
				dateFinService,
				heureService,
				centreDonneesServices.getDayOfWeek(recurrenceHebdo),
				capaciteMaximale,
				numeroProfessionnel,
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

	public String getListeService(String idProfessionnel) {
		List<Service> services = centreDonneesServices.getListeServicesPro(idProfessionnel);

		String servicesString = "";

		if (services.size() != 0) {
			for (Service s : services) {
				servicesString += s.getNomService() + " " + s.getCode() + "; ";
			}
		}
		return servicesString;
	}

	public String inscriptionSeance(String membreId, String seanceId, String commentaire) {
		return centreDonneesServices.inscrireMembreASeance(membreId, seanceId, commentaire).getHashInString();
	}

	public boolean confirmerPresence(String idSeance, String idMembre, String commentaire) {
		return centreDonneesServices.confirmationPresence(idSeance, idMembre, commentaire);
	}

	private void afficherServicesProfessionnel(List<Service> servicesDuProfessionnel) {
		Vue.effacerEcran();

		Vue.afficher("------Services du professionnel------");
		Vue.afficher("Liste des services disponibles pour ce professionnel :");
		for (Service s : servicesDuProfessionnel) {
			Vue.afficher(s.getCode() + " (" + s.getNomService() + ")");
		}
	}

	public String obtenirToutesLesSeancesDuJourEnString(LocalDate jour) {
		var seances = centreDonneesServices.getListeSeances(jour);
		String concatenation = "";
		for (Seance s : seances) {
			concatenation += s.getCodeSeance() + " (service de " +
					centreDonneesServices.getService(s.getCodeService()).getNomService() +
					") : le " +
					Verificateurs.localDateFormatter.format(s.getDate()) + "\n";
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
					s.getRecurrenceString() + "\n";
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

	public String getInformationsService(String idSeance) {
		Service service = null;
		Seance seance = null;
		String infos = "";
		
		seance = centreDonneesServices.lireSeance(idSeance);
		service = centreDonneesServices.lire(seance.getCodeService());
		

		if (service != null) {
			infos = "ID : " + service.getCode() + "\n" +
					"Nom de service : " + service.getNomService() + "\n" +
					"Date de début de service : " + service.getDateDebutService() + "\n" +
					"Date de fin de service : " + service.getDateFinService() + "\n" +
					"Heure de service : " + service.getHeureService() + "\n" +
					"Récurrence hebdomadaire : " + seance.getRecurrence() + "\n" +
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
					"Journée : " + seance.getRecurrenceString() + "\n" + 
					"Date de la séance : " + seance.dateString() + "\n";
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


	public String getIDServiceFromSeance(String idSeance) {
		return centreDonneesServices.getIDServiceFromSeance(idSeance);
	}

}
