package main.controleur;

import main.modele.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import static main.controleur.Helper.getIntFromString;

public class ControleurService extends Controleur {

	protected static CentreDonneesServices centreDonneesServices = new CentreDonneesServices();

	public ControleurService() {
		ControleurService.centreDonneesServices = new CentreDonneesServices();
	}

	public Service creerService(String nomService,
							 String dateDebutServiceString,
							 String dateFinServiceString,
							 String heureServiceString,
							 String recurrenceHebdoString,
							 String capaciteMaximaleString,
							 String numeroProfessionnel,
							 String fraisServiceString,
							 String commentaires) {

		LocalDateTime dateEtHeureActuelles = Helper.now();
		LocalDate dateDebutService = LocalDate.from(Helper.localDateFormatter.parse(dateDebutServiceString));
		LocalDate dateFinService = LocalDate.from(Helper.localDateFormatter.parse(dateFinServiceString));
		LocalTime heureService = LocalTime.from(Helper.localTimeFormatter.parse(heureServiceString));
		Jour recurrenceHebdo = Jour.valueOf(recurrenceHebdoString.toUpperCase());
		int capaciteMaximale = getIntFromString(capaciteMaximaleString);
		double fraisService = Double.parseDouble(fraisServiceString);

		Service service = new Service(nomService,
				dateEtHeureActuelles,
				dateDebutService,
				dateFinService,
				heureService,
				Helper.getDayOfWeek(recurrenceHebdo),
				capaciteMaximale,
				numeroProfessionnel,
				fraisService,
				commentaires);

		centreDonneesServices.ajouterService(service);
		return service;
	}

	public void mettreServiceAJour(String idSeance, Champs champs, String valeur) {
		centreDonneesServices.mettreAJour(idSeance, champs, valeur);
	}

	public void supprimerService(String idSeance) {
			centreDonneesServices.supprimer(idSeance);
	}

	public String inscriptionSeance(String membreId, String nomClient, String seanceId, String commentaire) {
		return centreDonneesServices.inscrireMembreASeance(membreId, nomClient, seanceId, commentaire).getHashInString();
	}

	public boolean confirmerPresence(String idSeance, String idMembre, String commentaire) {
		return centreDonneesServices.confirmationPresence(idSeance, idMembre, commentaire);
	}

	public String obtenirToutesLesSeancesDuJourEnString(LocalDate jour) {
		var seances = centreDonneesServices.getListeSeances(jour);
		StringBuilder concatenation = new StringBuilder();
		for (Seance s : seances) {
			concatenation
					.append(s.getCodeSeance())
					.append(" (service de ")
					.append(centreDonneesServices.getService(s.getCodeService()).getNomService())
					.append(") : le ")
					.append(Helper.localDateFormatter.format(s.getDate()))
					.append("\n");
		}
		return concatenation.toString();
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
		StringBuilder concatenation = new StringBuilder();
		for (Seance s : seances) {
			concatenation
					.append(s.getCodeSeance())
					.append(" (service de ")
					.append(centreDonneesServices.getService(s.getCodeService()).getNomService())
					.append(") : le ")
					.append(s.getRecurrenceString())
					.append("\n");
		}
		return concatenation.toString();
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
		String infos = "";

		Seance seance = centreDonneesServices.lireSeance(idSeance);
		Service service = seance.getService();

		if (service != null) infos = seance.toString() + service.toString();
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

		if (seance != null) infos = seance.toString();
		return infos;
	}

	public String getInformationsInscription(String inscriptionId) {
		Inscription inscription = centreDonneesServices.getInscription(inscriptionId);
		String infos = "";

		if (inscription != null) {
			infos = inscription.toString();
		}
		return infos;
	}

	public String obtenirInscriptionsASeanceEnString(String idSeance) {
		var listeInscriptions = obtenirInscriptionsASeance(idSeance);
		StringBuilder inscriptions = new StringBuilder();
		for (Inscription inscription : listeInscriptions) {
			inscriptions
					.append("Membre ")
					.append(inscription.getNumeroMembre())
					.append(inscription.getCommentaires().length() != 0 ? "; Commentaires : " : "")
					.append(inscription.getCommentaires())
					.append("\n");
		}
		return inscriptions.toString();
	}

	public List<Inscription> obtenirInscriptionsASeance(String idSeance) {
		return centreDonneesServices.getListeInscriptionsSeance(idSeance);
	}

	public boolean inscriptionExiste(String idMembre, String idSeance) {
		return centreDonneesServices.inscriptionExiste(idMembre, idSeance);
	}

	public Seance lireSeance(String idSeance) {
		return centreDonneesServices.lireSeance(idSeance);
	}

	public static String nomService(String idService) {
		Service service = centreDonneesServices.lire(idService);
		return service.getNomService();
	}

}
