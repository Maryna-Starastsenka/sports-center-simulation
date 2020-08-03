package main.modele;

import main.controleur.Verificateurs;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Objects;

public class Seance {
	private DayOfWeek recurrence;
	private LocalDate date;
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
	
	public LocalDate getDate() {
		return date;
	}
	
	public Service getService() {
		return service;
	}
	
	public String dateString() {
		return Verificateurs.localDateFormatter.format(date);
	}

	public String getRecurrenceString() {
		switch (recurrence) {
		case MONDAY:
			return Jour.LUNDI.toString();
		case TUESDAY:
			return Jour.MARDI.toString();
		case WEDNESDAY:
			return Jour.MERCREDI.toString();
		case THURSDAY:
			return Jour.JEUDI.toString();
		case FRIDAY:
			return Jour.VENDREDI.toString();
		case SATURDAY:
			return Jour.SAMEDI.toString();
		case SUNDAY:
			return Jour.DIMANCHE.toString();
		default:
			return null;
		}
	}

	public String getCodeService() {
		return codeService;
	}

	@Override
	public int hashCode() {
		return Math.abs(Objects.hash(recurrence, codeService, codeProfessionnel) % 100); // 2 chiffres max
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
}
