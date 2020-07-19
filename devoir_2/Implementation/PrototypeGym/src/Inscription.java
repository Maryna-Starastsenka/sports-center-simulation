import java.util.Date;


public class Inscription {

	private Date dateEtHeureActuelles;
	private Date dateSeance;
	private String numeroProfessionnel;
	private String numeroMembre;
	private String codeService;
	private String commentaires;
	
	public Inscription(Date dateEtHeureActuelles, Date dateSeance, String numeroProfessionnel, String numeroMmemre,
					   String codeService, String commentaires) {
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.dateSeance = dateSeance;
		this.numeroProfessionnel = numeroProfessionnel;
		this.numeroMembre = numeroMmemre;
		this.codeService = codeService;
		this.commentaires = commentaires;
	}
}
