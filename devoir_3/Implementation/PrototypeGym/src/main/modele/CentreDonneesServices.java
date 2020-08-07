package main.modele;

import main.controleur.Helper;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static main.controleur.Helper.*;

/**
 * Classe CentreDonneesServices qui implémente ICentreDonnees brut. Répertoire des services
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class CentreDonneesServices implements ICentreDonnees {

	private HashMap<String, Service> listeServices;
    private HashMap<String, Seance> listeSeances;
    
	private HashMap<String, Inscription> listeInscriptions;
    private HashMap<String, ConfirmationPresence> listeConfirmationsPresence;
    
    private List<ProfessionnelTef> listeProfessionnelsTef;
    private List<MembreTef> listeMembresTef;
    
	public CentreDonneesServices() {
		this.listeServices = new HashMap<>();
		this.listeSeances = new HashMap<>();
		this.listeInscriptions = new HashMap<>();
		this.listeConfirmationsPresence = new HashMap<>();
		this.listeProfessionnelsTef = new ArrayList<>();
		this.listeMembresTef = new ArrayList<>();
	}

	public void ajouterService(Service service) {
		List<Seance> seances = service.obtenirListeSeances();
		
		if (!listeServices.containsKey(service.getCode())) {
			listeServices.put(service.getCode(), service);
			if(seances.size()==1) {
				listeSeances.put(seances.get(0).getCodeSeance(), seances.get(0));
			}
		} else {
			Service serviceDejaAjoute = listeServices.get(service.getCode());
			Seance ajoutDeSeance = seances.get(0);
			ajoutDeSeance.setService(serviceDejaAjoute);
			serviceDejaAjoute.ajouterSeance(ajoutDeSeance);
			listeSeances.put(ajoutDeSeance.getCodeSeance(), ajoutDeSeance);
		}
	}
	
	public Service getService(String idService) {
        return listeServices.get(idService);
    }
	
	public Seance getSeance(String idSeance) {
        return listeSeances.get(idSeance);
    }
	
	public String getIDServiceFromSeance(String idSeance) {
		return listeSeances.get(idSeance).getCodeService();
	}
	
	public void supprimerService(String serviceId) {
        listeServices.remove(serviceId);
    }
	
	public void mettreAJourSeances() {
		List<Service> services = this.getServices();
		HashMap<String, Seance> nouvelleListe = new HashMap<>();
		for(Service service : services) {
			List<Seance> seances = service.obtenirListeSeances();
			for(Seance seance : seances) {
				nouvelleListe.put(seance.getCodeSeance(), seance);
			}
		}
		listeSeances = nouvelleListe;
	}
	
	public List<Service> getListeServicesPro(String idProfessionnel) {
        return listeServices
                .values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

	public List<Seance> getListeSeancesPro(String idProfessionnel) {
		return listeSeances
				.values()
				.stream()
				.filter(x -> x.getCodeProfessionnel().equals(idProfessionnel))
				.collect(Collectors.toList());
	}
	
	public List<Seance> getListeSeances(LocalDate jour) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDate().equals(jour))
                .collect(Collectors.toList());
    }
	
	public Inscription inscrireMembreASeance(String idMembre, String nomClient, String idSeance, String commentaires) {
        Seance seance = listeSeances.get(idSeance);
        Service service = listeServices.get(seance.getCodeService());
        Inscription inscription = new Inscription(
                LocalDateTime.now(),
                seance.getDate(),
                service.getNumeroProfessionnel(),
                idMembre,
                nomClient,
                seance.getCodeService(),
                commentaires,
				seance.getCodeSeance(),
				service.getFraisService());
        if (!listeInscriptions.containsKey(inscription.getHashInString())) {
            listeInscriptions.put(inscription.getHashInString(), inscription);
        }
        return inscription;
    }
	
	public boolean inscriptionExiste(String idMembre, String idSeance) {
        List<Inscription> inscriptions = null;
        if (listeServices.containsKey(getSeance(idSeance).getCodeService())) {
            Service service = getService(getSeance(idSeance).getCodeService());
             inscriptions = listeInscriptions
                    .values()
                    .stream()
                    .filter(x -> x.getNumeroMembre().equals(idMembre) && x.getCodeService().equals(service.getCode()))
                    .collect(Collectors.toList());
        }
        return inscriptions != null && inscriptions.size() >= 1;
    }
	
	public List<Inscription> getListeInscriptionsPro(String idProfessionnel) {
        return listeInscriptions
                .values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

	public List<Inscription> getListeInscriptionsSeance(String idSeance) {
		return listeInscriptions
				.values()
				.stream()
				.filter(x -> x.getCodeSeance().equals(idSeance))
				.collect(Collectors.toList());
	}

	
	public boolean confirmationPresence(String idSeance, String idMembre, String commentaires) {
        String codeService = listeSeances.get(idSeance).getCodeService();
        String numeroProfessionnel = listeServices.get(codeService).getNumeroProfessionnel();
        ConfirmationPresence cp = new ConfirmationPresence(now(), idMembre, numeroProfessionnel, codeService, commentaires);
        if (!listeConfirmationsPresence.containsKey(cp.getHashInString())) {
            listeConfirmationsPresence.put(cp.getHashInString(), cp);
            return true;
        }
		return false;
	}
	
	public List<Seance> getSeancesEntre(LocalDate debut, LocalDate fin) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDate().compareTo(debut) >= 0 && x.getDate().compareTo(fin) <= 0)
                .collect(Collectors.toList());
    }
	
	public List<Inscription> getInscriptionsEntre(LocalDate debut, LocalDate fin) {
        return listeInscriptions
                .values()
                .stream()
                .filter(x -> x.getDateSeance().compareTo(debut) >= 0 && x.getDateSeance().compareTo(fin) <= 0)
                .collect(Collectors.toList());
    }

	public List<ProfessionnelTef> genererTefProfessionnel(HashMap<String, Professionnel> listeProfessionnels) {
		LocalDate dateDebut = LocalDate.now().minusDays(8).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
		LocalDate dateFin = dateDebut.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

		List<Inscription> inscriptions = getInscriptionsEntre(dateDebut, dateFin);
		HashMap<String, ProfessionnelTef> professionnelsAPayer = new HashMap<>();

		for(Inscription i : inscriptions) {
			String idProfessionnel = i.getNumeroProfessionnel();
			Professionnel pro = listeProfessionnels.get(idProfessionnel);
			if (!professionnelsAPayer.containsKey(idProfessionnel)){				
				ProfessionnelTef proTef = new ProfessionnelTef(pro.getNom(), idProfessionnel, pro.getAdresse(), pro.getVille(),
						pro.getProvince(), pro.getCodePostal(), getListeServicesPro(idProfessionnel).size());
				professionnelsAPayer.put(pro.getHashInString(), proTef);
			}
			professionnelsAPayer.get(pro.getHashInString()).ajouterInscription(i);

		}
		
		 listeProfessionnelsTef = new ArrayList<>(professionnelsAPayer.values());
		 return listeProfessionnelsTef;
	}
	
	public List<MembreTef> genererTefMembre(HashMap<String, Membre> listeMembres) {
		LocalDate dateDebut = LocalDate.now().minusDays(8).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
		LocalDate dateFin = dateDebut.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

		List<Inscription> inscriptions = getInscriptionsEntre(dateDebut, dateFin);
		HashMap<String, MembreTef> membresPaye = new HashMap<>();

		for(Inscription i : inscriptions) {
			String idMembre = i.getNumeroMembre();
			Membre membre = listeMembres.get(idMembre);
			if (!membresPaye.containsKey(idMembre)){				
				MembreTef membreTef = new MembreTef(membre.getNom(), idMembre, membre.getAdresse(), membre.getVille(),
						membre.getProvince(), membre.getCodePostal());
				membresPaye.put(membre.getHashInString(), membreTef);
			}
			membresPaye.get(membre.getHashInString()).ajouterInscription(i);

		}
		
		listeMembresTef = new ArrayList<>(membresPaye.values());
		return listeMembresTef;
	}

	public RapportSynthese genererRapportSynthese(HashMap<String, Professionnel> listeProfessionnels) {
		genererTefProfessionnel(listeProfessionnels);
		int nombreTotalServices = listeProfessionnelsTef.stream().mapToInt(ProfessionnelTef::getNombreServices).sum();
		double fraisTotaux = listeProfessionnelsTef.stream().mapToDouble(ProfessionnelTef::getMontant).sum();
		return new RapportSynthese(listeProfessionnelsTef,
				listeProfessionnelsTef.size(),
				nombreTotalServices,
				fraisTotaux);
	}

	public List<Service> getServices() {
		return new ArrayList<>(listeServices.values());
	}

	@Override
	public void creer(Object client) {
	}

	@Override
	public Service lire(String id) {
		if (listeServices.containsKey(id)) {
			return listeServices.get(id);
		}
		return null;
	}
	
	public Seance lireSeance(String id) {
		if (listeSeances.containsKey(id)) {
			return listeSeances.get(id);
		}
		return null;
	}

	@Override
	public void mettreAJour(String idSeance, Champs champsService, String valeur) {
		Service service = lireSeance(idSeance).getService();

		switch (champsService) {
			case NOM_SERVICE -> {
				String idService = service.getCode();
				service.setNomService(valeur);
				listeServices.remove(idService);
				listeServices.put(service.getCode(), service);
				mettreAJourSeances();
			}
			case DATE_DEBUT_SERVICE -> service.setDateDebutService(getDateFromString(valeur));
			case DATE_FIN_SERVICE -> service.setDateFinService(getDateFromString(valeur));
			case HEURE_SERVICE -> service.setHeureService(getHeureFromString(valeur));
			case RECURRENCE_HEBDO_SERVICE -> {
				listeSeances.get(idSeance).setRecurrence(Helper.getDayOfWeek(getJourFromString(valeur)));
				mettreAJourSeances();
			}
			case CAPACITE_MAX_SERVICE -> service.setCapaciteMax(getIntFromString(valeur));
			case FRAIS_SERVICE -> service.setFrais(getDoubleFromString(valeur));
			case COMMENTAIRE_SERVICE -> service.setCommentaires(valeur);
		}
	}

	@Override
	public void supprimer(String seanceId) {
		Seance seance = listeSeances.get(seanceId);
		Service service = listeServices.get(seance.getCodeService());
		listeSeances.remove(seanceId);
		service.enleverSeance(seanceId);
		if(service.obtenirListeSeances().size()==0)
			listeServices.remove(service.getCode());
		}

	@Override
	public void ajouterClient(Object client) {

	}

	public Inscription getInscription(String inscriptionId) {
		if (listeInscriptions.containsKey(inscriptionId)) {
			return listeInscriptions.get(inscriptionId);
		}
		return null;
	}
	
	
	public List<Service> getListeServices() {
		return listeServices
				.values()
				.stream()
				.collect(Collectors.toList());
	}
	
	public List<Seance> getListeSeances() {
		return listeSeances
				.values()
				.stream()
				.collect(Collectors.toList());
	}
	
}
