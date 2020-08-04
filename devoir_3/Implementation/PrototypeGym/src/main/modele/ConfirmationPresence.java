package main.modele;

import java.time.LocalDateTime;
import java.util.Objects;

public class ConfirmationPresence {

	private LocalDateTime dateEtHeureActuelles;
	private String numeroMembre;
	private String numeroProfessionnel;
	private String codeService;
	private String commentaires;
	
	public ConfirmationPresence(LocalDateTime dateEtHeureActuelles,
								String numeroMembre,
								String numeroProfessionnel,
								String codeService,
								String commentaires) {
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.numeroMembre = numeroMembre;
		this.numeroProfessionnel = numeroProfessionnel;
		this.codeService = codeService;
		this.commentaires = commentaires;
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(
				numeroMembre,
				numeroProfessionnel,
				codeService,
				commentaires) % 10000); // 4 chiffres max
	}

	protected String getHashInString() {
		return String.format("%04d", this.hashCode());
	}
}