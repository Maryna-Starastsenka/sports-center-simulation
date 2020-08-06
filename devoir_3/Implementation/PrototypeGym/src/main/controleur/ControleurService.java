package main.controleur;

import main.modele.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import static main.controleur.Helper.getIntFromString;

/**
 * Classe Contôleur Service hérite la classe Contôleur. Gère le flux de données des modèles service, séance,
 * inscription, confirmation de présence et met à jour la vue lorsque les données changent
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class ControleurService extends Controleur {

	protected static CentreDonneesServices centreDonneesServices = new CentreDonneesServices();

	/**
	 * Constructeur de ControleurService qui instancie le centre de données de services
	 */
	public ControleurService() {
		ControleurService.centreDonneesServices = new CentreDonneesServices();
	}

	/**
	 * Demande au Centre de données de créer un nouveau service
	 * @param nomService nom du service
	 * @param dateDebutServiceString date de début du service
	 * @param dateFinServiceString date de fin du service
	 * @param heureServiceString heure du service
	 * @param recurrenceHebdoString récurrence hebdomadaire du service
	 * @param capaciteMaximaleString capacité maximale du service
	 * @param numeroProfessionnel numéro du professionnel qui offre le service
	 * @param fraisServiceString frais du service
	 * @param commentaires commentaires
	 * @return service instancié
	 */
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

	/**
	 * Demande au Centre de données de mettre à jour les informations sur le service
	 * @param idSeance code de la séance
	 * @param champs type d'information à modifier
	 * @param valeur nouvelle valeur
	 */
	public void mettreServiceAJour(String idSeance, Champs champs, String valeur) {
		centreDonneesServices.mettreAJour(idSeance, champs, valeur);
	}

	/**
	 * Demande au Centre de données de supprimer la séance
	 * @param idSeance code de la séance
	 */
	public void supprimerService(String idSeance) {
			centreDonneesServices.supprimer(idSeance);
	}

	/**
	 * Demande au Centre d'inscrire un membre à une séance
	 * @param membreId numéro unique du membre
	 * @param nomClient nom du client
	 * @param seanceId code de la séance
	 * @param commentaire commentaire
	 * @return id de l'inscription à la séance
	 */
	public String inscriptionSeance(String membreId, String nomClient, String seanceId, String commentaire) {
		return centreDonneesServices.inscrireMembreASeance(membreId, nomClient, seanceId, commentaire).getHashInString();
	}

	/**
	 * Demande au Centre de données de confirmer la présence d'un membre à une séance
	 * @param idSeance code de la séance
	 * @param idMembre numéro unique du membre
	 * @param commentaire commentaire
	 * @return vrai si la confirmation de présence est créée. Sinon, retourne faux
	 */
	public boolean confirmerPresence(String idSeance, String idMembre, String commentaire) {
		return centreDonneesServices.confirmationPresence(idSeance, idMembre, commentaire);
	}

	/**
	 * Demande au Centre de données la liste des séances disponibles ce jour-ci et la retourne en string
	 * @param jour jour actuel
	 * @return liste des séances disponibles ce jour-ci en string
	 */
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

	/**
	 * Demande au Centre de données la liste des séances disponibles le jour spécifié et retourne la liste
	 * des codes de la séance
	 * @param jour jour pour lequel on souhaite obtenir les séances
	 * @return liste des code des séances disponibles le jour spécifié
	 */
	public List<String> obtenirListeIdSeancesDuJour(LocalDate jour) {
		var listeSeances = centreDonneesServices.getListeSeances(jour);
		List<String> idSeances = new LinkedList<>();
		for (Seance seance : listeSeances) {
			idSeances.add(seance.getCodeSeance());
		}
		return idSeances;
	}

	/**
	 * Demande au Centre de données la liste des séances du professionel et la retourne en string
	 * @param idProfessionnel numéro unique du professionnel
	 * @return liste des séances du professionel en string
	 */
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

	/**
	 * Demande au Centre de données la liste des séances du professionnel et retourne la liste des codes de ses séances
	 * @param idProfessionnel numéro unique du professionnel
	 * @return liste des code des séances du professionnel
	 */
	public List<String> obtenirListeSeancesDuProfessionnel(String idProfessionnel) {
		var listeSeances = centreDonneesServices.getListeSeancesPro(idProfessionnel);
		List<String> idSeances = new LinkedList<>();
		for (Seance seance : listeSeances) {
			idSeances.add(seance.getCodeSeance());
		}
		return idSeances;
	}

	/**
	 * Retourne l'information sur le service
	 * @param idSeance code de la séance
	 * @return information sur la séance
	 */
	public String getInformationsService(String idSeance) {
		String infos = "";

		Seance seance = centreDonneesServices.lireSeance(idSeance);
		Service service = seance.getService();

		if (service != null) infos = seance.toString() + service.toString();
		return infos;
	}

	/**
	 * Demande au Centre de données le service associé au code de la séance et retourne ses frais
	 * @param seanceId code de la séance
	 * @return frais du service
	 */
	public String getFraisService(String seanceId) {
		Seance seance = centreDonneesServices.getSeance(seanceId);
		Service service = centreDonneesServices.getService(seance.getCodeService());
		return "" + service.getFraisService();
	}

	/**
	 * Demande au Centre de données la séance associée au code de la séance et retourne ses informations
	 * @param seanceId code de la séance
	 * @return information sur la séance
	 */
	public String getInformationSeance(String seanceId) {
		Seance seance = centreDonneesServices.getSeance(seanceId);
		String infos = "";

		if (seance != null) infos = seance.toString();
		return infos;
	}

	/**
	 * Demande au Centre de données l'inscription associée au code et retourne ses informations
	 * @param inscriptionId code de l'inscription
	 * @return information sur l'inscription
	 */
	public String getInformationsInscription(String inscriptionId) {
		Inscription inscription = centreDonneesServices.getInscription(inscriptionId);
		String infos = "";

		if (inscription != null) {
			infos = inscription.toString();
		}
		return infos;
	}

	/**
	 * Demande au Centre de données la liste des inscriptions à la séance et la retourne en string
	 * @param idSeance code de la séance
	 * @return liste des inscriptions à la séance en string
	 */
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

	/**
	 * Demande au Centre de données la liste des inscription à la séance
	 * @param idSeance code de la séance
	 * @return liste des inscriptions à la séance
	 */
	public List<Inscription> obtenirInscriptionsASeance(String idSeance) {
		return centreDonneesServices.getListeInscriptionsSeance(idSeance);
	}

	/**
	 * Demande au Centre de données de vérifier si le membre est inscrit à la séance
	 * @param idMembre numéro du membre
	 * @param idSeance code de la séance
	 * @return vrai si le membre est inscrit à la séance. sinon, retourne faux
	 */
	public boolean inscriptionExiste(String idMembre, String idSeance) {
		return centreDonneesServices.inscriptionExiste(idMembre, idSeance);
	}

	/**
	 * Demande au Centre de données de retourner la séance associée au code unique
	 * @param idSeance code de la séance
	 * @return séance
	 */
	public Seance lireSeance(String idSeance) {
		return centreDonneesServices.lireSeance(idSeance);
	}

	/**
	 * Obtenir le nom du service
	 * @param idService code du service
	 * @return nom du service
	 */
	public static String nomService(String idService) {
		Service service = centreDonneesServices.lire(idService);
		return service.getNomService();
	}

}
