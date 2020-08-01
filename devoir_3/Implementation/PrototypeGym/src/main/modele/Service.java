package main.modele;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;


public class Service {
	private String nomService;
	private LocalDateTime dateEtHeureActuelles;
	private LocalDate dateDebutService;
	private LocalDate dateFinService;
	private LocalTime heureService;
	private Jour recurrenceHebdo;
	private int capaciteMaximale;
	private String numeroProfessionnel;
	private String codeService;
	private double fraisService;
	private String commentaires;

	private HashMap<String, Seance> seances;


	public Service(String nomService,
                   LocalDateTime dateEtHeureActuelles,
                   LocalDate dateDebutService,
                   LocalDate dateFinService,
                   LocalTime heureService,
                   Jour recurrenceHebdo,
                   int capaciteMaximale,
                   String numeroProfessionnel,
                   String codeService,
                   double fraisService,
                   String commentaires) {
		this.nomService = nomService;
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.dateDebutService = dateDebutService;
		this.dateFinService = dateFinService;
		this.heureService = heureService;
		this.recurrenceHebdo = recurrenceHebdo;
		this.capaciteMaximale = capaciteMaximale;
		this.numeroProfessionnel = numeroProfessionnel;
		this.codeService = codeService;
		this.fraisService = fraisService;
		this.commentaires = commentaires;
	}

	public LocalDate getDateDebutService() { return dateDebutService; }

	public LocalDate getDateFinService() { return dateFinService; }

	public LocalTime getHeureService() {
		return heureService;
	}


	public String getNomService() { return nomService; }

	public String getNumeroProfessionnel() { return numeroProfessionnel; }

	public String getRecurrenceHebdo() { return "" + recurrenceHebdo; }

	public int getCapaciteMaximale() {return capaciteMaximale; }

	public String getCode() { return codeService; }

	public double getFraisService() {return fraisService; }

	public String getCommentaires() { return commentaires; }

	public void setRecurrenceHebdo(Jour valeur) {
		this.recurrenceHebdo = valeur;
	}

	public void setHeureService(LocalTime valeur){
		this.heureService = valeur;
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(dateEtHeureActuelles,
				dateDebutService,
				dateFinService,
				heureService,
				recurrenceHebdo,
				capaciteMaximale,
				numeroProfessionnel,
				codeService,
				fraisService,
				commentaires) % 10000); // 4 chiffres max
	}

	protected String getHashInString() {
		return String.format("%04d", this.hashCode());
	}

	public void setNomService(String valeur) {
		this.nomService = valeur;
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
}