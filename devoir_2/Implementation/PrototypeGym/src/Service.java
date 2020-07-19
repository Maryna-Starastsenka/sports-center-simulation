import java.util.Date;


public class Service {
	private Date dateEtHeureActuelles;
	private Date dateDebutService;
	private Date dateFinService;
	private String heureService;
	private int recurrenceHebdo;
	private int capaciteMaximale;
	private String numeroProfessionnel;
	private String codeService;
	private double fraisService;
	private String commentaires;

	public Service(Date dateEtHeureActuelles,
				  Date dateDebutService,
				  Date dateFinService,
				  String heureService,
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

	public void setRecurrenceHebdo(String valeur) {
		this.recurrenceHebdo = Integer.parseInt(valeur);
	}

	public void setHeureService(String valeur){
		this.heureService = valeur;
	}

	public String getCode() {
		return codeService;
	}

	public String getNumeroProfessionnel() {
		return numeroProfessionnel;
	}

	public String getRecurrenceHebdo() {
		return "" + recurrenceHebdo;
	}

	public String getHeureService() {
		return heureService;
	}

}
