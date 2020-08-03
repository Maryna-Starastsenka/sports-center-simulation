package main.modele;

import static main.controleur.Verificateurs.today;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import main.controleur.Verificateurs;


public class Service {
	private String nomService;
	private LocalDateTime dateEtHeureActuelles;
	private LocalDate dateDebutService;
	private LocalDate dateFinService;
	private LocalTime heureService;
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
		this.seances = new HashMap<String, Seance>();
		this.seances.put(seance.getCodeSeance(), seance);		
	}

	public String getDateActuelleString() { return Verificateurs.localDateTimeFormatter.format(dateEtHeureActuelles);}
	
	public LocalDate getDateDebutService() { return dateDebutService; }

	public LocalDate getDateFinService() { return dateFinService; }

	public LocalTime getHeureService() {
		return heureService;
	}

	public void ajouterSeance(Seance seance) {
		this.seances.put(seance.getCodeSeance(), seance);
	}
	
	public void enleverSeance(String id) {
		this.seances.remove(id);
	}
	
	public List<Seance> obtenirListeSeances() {
		return seances
				.values()
				.stream()
				.collect(Collectors.toList());
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
		return Math.abs((Objects.hash(nomService) + Objects.hash(numeroProfessionnel)) % 1000); // 3 chiffres max
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
}