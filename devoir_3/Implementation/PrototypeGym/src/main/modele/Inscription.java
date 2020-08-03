package main.modele;

import main.controleur.Verificateurs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class Inscription {

	private LocalDateTime dateEtHeureActuelles;
	private LocalDate dateSeance;
	private String numeroProfessionnel;
	private String numeroMembre;
	private String codeService;
	private String commentaires;
	private String codeSeance;

	public Inscription(LocalDateTime dateEtHeureActuelles, LocalDate dateSeance, String numeroProfessionnel, String numeroMembre,
					   String codeService, String commentaires, String codeSeance) {
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.dateSeance = dateSeance;
		this.numeroProfessionnel = numeroProfessionnel;
		this.numeroMembre = numeroMembre;
		this.codeService = codeService;
		this.commentaires = commentaires;
		this.codeSeance = codeSeance;
	}

	public LocalDate getDateSeance() {
		return dateSeance;
	}

	public String getNumeroProfessionnel() {
		return numeroProfessionnel;
	}

	public String getNumeroMembre() {
		return numeroMembre;
	}

	public String getCodeService() {
		return codeService;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public String getCodeSeance() {
		return codeSeance;
	}

	public String getDateEtHeureActuelleString() {
		return Verificateurs.localDateTimeFormatter.format(dateEtHeureActuelles);
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(
				dateSeance,
				numeroProfessionnel,
				numeroMembre,
				codeService,
				codeSeance) % 10000); // 4 chiffres max
	}

	public String getHashInString() {
		return String.format("%04d", this.hashCode());
	}

	public String getDateSeanceString() {
		return Verificateurs.localDateFormatter.format(dateSeance);
	}



}