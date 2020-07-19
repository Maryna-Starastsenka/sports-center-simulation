import java.time.LocalDateTime;
import java.util.Objects;

public class Seance {
	private LocalDateTime dateSeance;
	private String codeService;

	public Seance(LocalDateTime dateSeance, String codeService) {
		this.dateSeance = dateSeance;
		this.codeService = codeService;
	}

	public LocalDateTime getDateTimeSeance() {
		return dateSeance;
	}

	public String getCodeService() {
		return codeService;
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(dateSeance, codeService) % 10000); // 4 chiffres max
	}

	protected String getHashInString() {
		return String.format("%04d", this.hashCode());
	}
}
