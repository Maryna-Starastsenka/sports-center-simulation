package main.modele;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe ConfirmationPresence qui constitue un enregistrement de présence confirmée
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class ConfirmationPresence {

	private LocalDateTime dateEtHeureActuelles;
	private String numeroMembre;
	private String numeroProfessionnel;
	private String codeService;
	private String commentaires;

	/**
	 * Constructeur de ConfirmationPresence
	 * @param dateEtHeureActuelles date et heur actuelles
	 * @param numeroMembre numéro du membre
	 * @param numeroProfessionnel numéro du professionnel
	 * @param codeService code du service
	 * @param commentaires commentaires
	 */
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
				commentaires) % 10000);
	}

	protected String getHashInString() {
		return String.format("%04d", this.hashCode());
	}
}