import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Inscription {

	private LocalDateTime dateEtHeureActuelles;
	private LocalDate dateSeance;
	private String numeroProfessionnel;
	private String numeroMembre;
	private String codeService;
	private String commentaires;
	
	public Inscription(LocalDateTime dateEtHeureActuelles, LocalDate dateSeance, String numeroProfessionnel, String numeroMembre,
					   String codeService, String commentaires) {
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.dateSeance = dateSeance;
		this.numeroProfessionnel = numeroProfessionnel;
		this.numeroMembre = numeroMembre;
		this.codeService = codeService;
		this.commentaires = commentaires;
	}
}
