package main.modele;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Classe Service. Enregistrement des informations propres à un service
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Service {
	private String nomService;
	private final LocalDateTime dateEtHeureActuelles;
	private LocalDate dateDebutService;
	private LocalDate dateFinService;
	private LocalTime heureService;
	private int capaciteMaximale;
	private String numeroProfessionnel;
	private String codeService;
	private double fraisService;
	private String commentaires;
	private HashMap<String, Seance> seances;

	/**
	 * Constructeur d'un service
	 * @param nomService nom du service
	 * @param dateEtHeureActuelles date et heure au moment de la création du service
	 * @param dateDebutService date de début du service
	 * @param dateFinService date de fin du service
	 * @param heureService horaire du service
	 * @param recurrenceHebdo jour de récurrence hebdomadaire
	 * @param capaciteMaximale capacité maximale du service
	 * @param numeroProfessionnel numéro du professionnel dispensant le service
	 * @param fraisService montant du service
	 * @param commentaires commentaires
	 */
	public Service(String nomService,
                   LocalDateTime dateEtHeureActuelles,
                   LocalDate dateDebutService,
                   LocalDate dateFinService,
                   LocalTime heureService,
                   DayOfWeek recurrenceHebdo,
                   int capaciteMaximale,
                   String numeroProfessionnel,
                   double fraisService,
                   String commentaires) {

		this.nomService = nomService;
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.dateDebutService = dateDebutService;
		this.dateFinService = dateFinService;
		this.heureService = heureService;
		this.capaciteMaximale = capaciteMaximale;
		this.numeroProfessionnel = numeroProfessionnel;
		this.codeService = this.getHashInString();
		this.fraisService = fraisService;
		this.commentaires = commentaires;
		
		Seance seance = new Seance(recurrenceHebdo, this.codeService, numeroProfessionnel, this);
		this.seances = new HashMap<>();
		this.seances.put(seance.getCodeSeance(), seance);		
	}

	public LocalDate getDateDebutService() { return dateDebutService; }

	public LocalDate getDateFinService() { return dateFinService; }

	public LocalTime getHeureService() {
		return heureService;
	}

	/**
	 * Ajoute la séance dans la liste des séances
	 * @param seance seance
	 */
	public void ajouterSeance(Seance seance) {
		this.seances.put(seance.getCodeSeance(), seance);
	}

	/**
	 * Retire la séance dans la liste des séances
	 * @param id code de la séance
	 */
	public void enleverSeance(String id) {
		this.seances.remove(id);
	}

	/**
	 * Retourne la liste des séances
	 * @return liste des séances
	 */
	public List<Seance> obtenirListeSeances() {
		return new ArrayList<>(seances.values());
	}

	public String getNomService() { return nomService; }

	public String getNumeroProfessionnel() { return numeroProfessionnel; }

	public int getCapaciteMaximale() {return capaciteMaximale; }

	public String getCode() { return codeService; }

	public double getFraisService() {return fraisService; }

	public String getCommentaires() { return commentaires; }

	public void setHeureService(LocalTime valeur){
		this.heureService = valeur;
	}

	@Override
	public int hashCode() {
		return Math.abs((Objects.hash(nomService) + Objects.hash(numeroProfessionnel)) % 1000);
	}

	protected String getHashInString() {
		return String.format("%03d", this.hashCode());
	}

	public void setNomService(String valeur) {
		this.nomService = valeur;
		this.codeService = this.getHashInString();
		List<Seance> seances = obtenirListeSeances();
		for(Seance s : seances) {
			s.setCodeService();
		}
	}

	public void setDateDebutService(LocalDate valeur) {
		this.dateDebutService = valeur;
	}

	public void setDateFinService(LocalDate valeur) {
		this.dateFinService = valeur;
	}

	public void setCapaciteMax(int valeur) {
		this.capaciteMaximale = valeur;
	}

	public void setFrais(double valeur) {
		this.fraisService = valeur;
	}

	public void setCommentaires(String valeur) {
		this.commentaires = valeur;
	}

	/**
	 * Retourne les informations sur le service en string
	 * @return informations sur le service
	 */
	@Override
	public String toString() {
		return "Nom de service : " + getNomService() + "\n" +
				"Date de début de service : " + getDateDebutService() + "\n" +
				"Date de fin de service : " + getDateFinService() + "\n" +
				"Heure de service : " + getHeureService() + "\n" +
				"Capacité maximale : " + getCapaciteMaximale() + "\n" +
				"Numéro de professionnel : " + getNumeroProfessionnel() + "\n" +
				"Frais de service : " + getFraisService() + "\n" +
				"Commentaire : " + getCommentaires() + "\n";
	}
}