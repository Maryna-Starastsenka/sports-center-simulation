package main.modele;

import main.controleur.Verificateurs;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class Seance {
	private LocalDateTime dateSeance;
	private String codeService;
	private String codeProfessionnel;

	private HashMap<String, Inscription> inscriptions;

	public Seance(LocalDateTime dateSeance, String codeService, String codeProfessionnel) {
		this.dateSeance = dateSeance;
		this.codeService = codeService;
		this.codeProfessionnel = codeProfessionnel;
	}

	public LocalDateTime getDateTimeSeance() {
		return dateSeance;
	}

	public String getDateTimeSeanceString() {
		return Verificateurs.localDateTimeFormatter.format(dateSeance);
	}

	public String getCodeService() {
		return codeService;
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(dateSeance, codeService, codeProfessionnel) % 100); // 2 chiffres max
	}

	public String getCodeSeance() {
		return codeService.substring(0, 3) + hashCode() + codeProfessionnel.substring(7);
	}

	public String getCodeProfessionnel() {
		return codeProfessionnel;
	}

	public String getDateSeance() {
		return Verificateurs.localDateFormatter.format(dateSeance);
	}

//	public String getHashInString() {
//		return String.format("%04d", this.hashCode());
//	}
}
