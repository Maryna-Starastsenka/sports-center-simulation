package main.modele;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * Classe Seance. Enregistrement des informations propres à une séance
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Seance {
	private DayOfWeek recurrence;
	private final LocalDate date;
	private String codeService;
	private Service service;
	private String codeProfessionnel;

	public Seance(DayOfWeek recurrence, String codeService, String codeProfessionnel, Service service) {
		this.recurrence = recurrence;
		this.date = LocalDate.now().minusDays(1).with(TemporalAdjusters.next(recurrence));
		this.codeService = codeService;
		this.codeProfessionnel = codeProfessionnel;
		this.service = service;
	}

	public DayOfWeek getRecurrence() {
		return recurrence;
	}
	
	public void setRecurrence(DayOfWeek valeur) {
		this.recurrence = valeur;
	}
	
	public void setCodeService() {
		this.codeService = this.service.getCode();
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public Service getService() {
		return service;
	}
	
	public void setService(Service service) {
		this.service = service;
	}

	public String getRecurrenceString() {
		return switch (recurrence) {
			case MONDAY -> Jour.LUNDI.toString();
			case TUESDAY -> Jour.MARDI.toString();
			case WEDNESDAY -> Jour.MERCREDI.toString();
			case THURSDAY -> Jour.JEUDI.toString();
			case FRIDAY -> Jour.VENDREDI.toString();
			case SATURDAY -> Jour.SAMEDI.toString();
			case SUNDAY -> Jour.DIMANCHE.toString();
		};
	}

	public String getCodeService() {
		return codeService;
	}

	public String getCodeSeance() {
		return codeService + this.getHashInString()+ codeProfessionnel.substring(7);
	}

	public String getCodeProfessionnel() {
		return codeProfessionnel;
	}

	public String getHashInString() {
		return String.format("%02d", this.hashCode());
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(recurrence) % 100); // 2 chiffres max
	}

	@Override
	public String toString() {
		return "Numéro de séance : " + getCodeSeance() + "\n" +
			"Numéro de professionnel de séance : " + getCodeProfessionnel() + "\n" +
			"Récurrence hebdomadaire : " + getRecurrenceString() + "\n";
	}
}
