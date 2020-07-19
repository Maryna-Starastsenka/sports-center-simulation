import java.util.Date;
import java.util.Enumeration;

public class Seance {
	private Date dateSeance;
	private String codeService;

	public Seance(Date dateSeance, String codeService) {
		this.dateSeance = dateSeance;
		this.codeService = codeService;
	}

	public Date getDateSeance() {
		return dateSeance;
	}

	public String getCodeService() {
		return codeService;
	}
}
