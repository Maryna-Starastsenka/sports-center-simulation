import java.util.Date;
import java.util.Enumeration;

public class Seance {
	private Date dateSeance;
	private String heureService;
	private String numeroProfessionnel;
	private String codeService;
	private double fraisService;

	public Seance(Date dateSeance, String heureService, String numeroProfessionnel, String codeService, double fraisService) {
		this.dateSeance = dateSeance;
		this.heureService = heureService;
		this.codeService = codeService;
		this.numeroProfessionnel = numeroProfessionnel;
		this.fraisService = fraisService;
	}
}
