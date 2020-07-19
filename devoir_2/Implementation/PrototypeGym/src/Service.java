import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


public class Service {
	private LocalDateTime dateEtHeureActuelles;
	private LocalDate dateDebutService;
	private LocalDate dateFinService;
	private LocalTime heureService;
	private int recurrenceHebdo;
	private int capaciteMaximale;
	private String numeroProfessionnel;
	private String codeService;
	private double fraisService;
	private String commentaires;

	public Service(LocalDateTime dateEtHeureActuelles,
				  LocalDate dateDebutService,
				  LocalDate dateFinService,
				  LocalTime heureService,
				  int recurrenceHebdo,
				  int capaciteMaximale,
				  String numeroProfessionnel,
				  String codeService,
				  double fraisService,
				  String commentaires) {

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

	public String getNumeroProfessionnel() { return numeroProfessionnel; }

	public String getRecurrenceHebdo() { return "" + recurrenceHebdo; }

	public int getCapaciteMaximale() {return capaciteMaximale; }

	public String getCode() { return codeService; }

	public double getFraisService() {return fraisService; }

	public String getCommentaires() { return getCommentaires(); }

	public void setRecurrenceHebdo(String valeur) {
		this.recurrenceHebdo = Integer.parseInt(valeur);
	}

	public void setHeureService(LocalTime valeur){
		this.heureService = valeur;
	}

}
