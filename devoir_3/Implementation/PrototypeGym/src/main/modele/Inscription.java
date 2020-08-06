package main.modele;

import main.controleur.Helper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe Inscription qui constitue un enregistrement d'une inscription
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Inscription {

	private LocalDateTime dateEtHeureActuelles;
	private LocalDate dateSeance;
	private String numeroProfessionnel;
	private String numeroMembre;
	private String codeService;
	private String commentaires;
	private String codeSeance;
	private double montant;
	private String nomMembre;

	public Inscription(LocalDateTime dateEtHeureActuelles, LocalDate dateSeance, String numeroProfessionnel,
					   String numeroMembre, String nomMembre, String codeService, String commentaires,
					   String codeSeance, double montant) {
		this.dateEtHeureActuelles = dateEtHeureActuelles;
		this.dateSeance = dateSeance;
		this.numeroProfessionnel = numeroProfessionnel;
		this.numeroMembre = numeroMembre;
		this.nomMembre = nomMembre;
		this.codeService = codeService;
		this.commentaires = commentaires;
		this.codeSeance = codeSeance;
		this.montant = montant;
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
	
	public String getNomMembre() {
		return nomMembre;
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
	
	public double getMontant() {
		return montant;
	}

	public String getDateEtHeureActuelleString() {
		return Helper.localDateTimeFormatter.format(dateEtHeureActuelles);
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
		return Helper.localDateFormatter.format(dateSeance);
	}

	@Override
	public String toString() {
		return "Date et heure d'inscription : " + getDateEtHeureActuelleString() + "\n" +
				"Date de la séance : " + getDateSeanceString() + "\n" +
				"Numéro de professionnel : " + getNumeroProfessionnel() + "\n" +
				"Numéro de membre : " + getNumeroMembre() + "\n" +
				"Code de service : " + getCodeService() + "\n" +
				"Commentaires : " + getCommentaires() + "\n";
	}
}