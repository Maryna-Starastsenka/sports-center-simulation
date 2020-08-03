package main.modele;

import jdk.jshell.spi.ExecutionControl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static main.controleur.Verificateurs.*;

public class CentreDonneesServices implements ICentreDonnees {

	private HashMap<String, Service> listeServices = new HashMap<>();
    private HashMap<String, Seance> listeSeances = new HashMap<>();
    
	private HashMap<String, Inscription> listeInscriptions = new HashMap<>();
    private HashMap<String, ConfirmationPresence> listeConfirmationsPresence = new HashMap<>();
  
    
    private List<ProfessionnelTef> listeProfessionnelsTef = new ArrayList<>();
    
	public CentreDonneesServices() {
		String idProfessionel1 = "150337313";
		String idProfessionel2 = "173262475";
		String idMembre1 = "554365143";

		Service service1 = new Service("Zumba",
				LocalDateTime.of(LocalDate.of(2020, 1,1), LocalTime.of(8,0,0)),
				LocalDate.of(2025, 12, 31),
				LocalDate.of(2020, 7, 19),
				LocalTime.of(22, 30),
				DayOfWeek.SUNDAY,
				25,
				idProfessionel1,
				63.25,
				"Rien à signaler");
		listeServices.put(service1.getCode(), service1);
		System.out.println(service1.getCode());
		
		
		Service service3 = new Service("Yoga",
				LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
				LocalDate.of(2015, 11, 30),
				LocalDate.of(2025, 7, 12),
				LocalTime.of(18,20),
				DayOfWeek.MONDAY,
				25,
				idProfessionel1,
				100.00,
				"Aucun commentaire");
		listeServices.put(service3.getCode(), service3);

		Service service4 = new Service("Danse", LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
				LocalDate.of(2017, 11, 15),
				LocalDate.of(2036, 9, 20),
				LocalTime.of(18,20),
				DayOfWeek.MONDAY,
				2,
				idProfessionel2,
				50.12,
				"En refonte");
		listeServices.put(service3.getCode(), service3);

		
		// crée une séance au jour d'exécution du programme pour les tests
		Seance seance1 = new Seance(DayOfWeek.MONDAY, service1.getCode(), service1.getNumeroProfessionnel());
		listeSeances.put(seance1.getCodeSeance(), seance1);
		service1.ajouterSeance(seance1);

		this.mettreAJourSeances();


        Inscription inscription1 = new Inscription(now(),
                seance1.getDate(),
				idProfessionel1,
				idMembre1,
                seance1.getCodeService(),
                "",
				seance1.getCodeSeance());
        listeInscriptions.put(inscription1.getHashInString(), inscription1);
	}

	public void ajouterService(Service service) {
		if (!listeServices.containsKey(service.getCode())) {
			listeServices.put(service.getCode(), service);
		}else {
			List<Seance> seances = service.obtenirListeSeances();
			for(Seance seance : seances) {
				listeSeances.put(seance.getCodeSeance(), seance);
				listeServices.get(service.getCode()).ajouterSeance(seance);
			}
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
	
	public void supprimerService(String serviceEntre) {
        listeServices.remove(serviceEntre);
    }
	
	public void mettreAJourSeances() {
		List<Service> services = this.getServices();
		for(Service service : services) {
			List<Seance> seances = service.obtenirListeSeances();
			for(Seance seance : seances) {
				listeSeances.put(seance.getCodeSeance(), seance);
			}
		}
		
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
	
	public Inscription inscrireMembreASeance(String idMembre, String idSeance, String commentaires) {
        Seance seance = listeSeances.get(idSeance);
        Service service = listeServices.get(seance.getCodeService());
        Inscription inscription = new Inscription(
                LocalDateTime.now(),
                seance.getDate(),
                service.getNumeroProfessionnel(),
                idMembre,
                seance.getCodeService(),
                commentaires,
				seance.getCodeSeance());
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

	public void genererTef(HashMap<String, Professionnel> listeProfessionnels) {
		LocalDate dateDebut = LocalDate.now().minusDays(8).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
		LocalDate dateFin = dateDebut.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

		List<Seance> seances = getSeancesEntre(dateDebut, dateFin);
		HashMap<String, ProfessionnelTef> professionnelsAPayer = new HashMap<>();
		HashSet<String> serviceIds = new HashSet<>();
		for (Seance seance: seances) {
			Service service = getService(seance.getCodeService());

			if (!serviceIds.contains(service.getCode())) {
				serviceIds.add(service.getCode());
			}

			Professionnel pro = listeProfessionnels.get(service.getNumeroProfessionnel());

			if (!professionnelsAPayer.containsKey(pro.getHashInString())){
				var proTef = new ProfessionnelTef(pro.getNom(), pro.getHashInString(), service.getFraisService(), 0);

				professionnelsAPayer.put(pro.getHashInString(), proTef);
			} else {
				professionnelsAPayer.get(pro.getHashInString()).ajouterFraisAPayer(service.getFraisService());
			}
		}

		for (String s : serviceIds) {
			String pro = listeServices.get(s).getNumeroProfessionnel();
			professionnelsAPayer.get(pro).ajouterService();
		}

		listeProfessionnelsTef = professionnelsAPayer
				.values()
				.stream()
				.collect(Collectors.toList());
	}

	public RapportSynthese genererRapportSynthese(HashMap<String, Professionnel> listeProfessionnels) {
		genererTef(listeProfessionnels);
		int nombreTotalServices = listeProfessionnelsTef.stream().mapToInt(ProfessionnelTef::getNombreServices).sum();
		double fraisTotaux = listeProfessionnelsTef.stream().mapToDouble(ProfessionnelTef::getMontant).sum();
		return new RapportSynthese(listeProfessionnelsTef,
				listeProfessionnelsTef.size(),
				nombreTotalServices,
				fraisTotaux);
	}

	public List<Service> getServices() {
		return listeServices.values().stream().collect(Collectors.toList());
	}

	@Override
	public Object creer(Object client) {
		return null;
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
	public void mettreAJour(String idService, Champs champsService, String valeur) {
		Service service = lire(idService);

		switch (champsService) {
			case NOM_SERVICE:
				service.setNomService(valeur);
				break;
			case DATE_DEBUT_SERVICE:
				service.setDateDebutService(getDateFromString(valeur));
				break;
			case DATE_FIN_SERVICE:
				service.setDateFinService(getDateFromString(valeur));
				break;
			case HEURE_SERVICE:
				service.setHeureService(getHeureFromString(valeur));
				break;
			case RECURRENCE_HEBDO_SERVICE:
				listeSeances.get(idService).setRecurrence(getDayOfWeek(getJourFromString(valeur)));
				break;
			case CAPACITE_MAX_SERVICE:
				service.setCapaciteMax(getIntFromString(valeur));
				break;
			case FRAIS_SERVICE:
				service.setFrais(getDoubleFromString(valeur));
				break;
			case COMMENTAIRE_SERVICE:
				service.setCommentaires(valeur);
				break;
		}
	}

	@Override
	public void supprimer(String id) { listeServices.remove(id); }

	@Override
	public void ajouterClient(Object client) {

	}

	public List<Client> getClients() throws ExecutionControl.NotImplementedException {
		throw new ExecutionControl.NotImplementedException("not implemented");
	}

	public Inscription getInscription(String inscriptionId) {
		if (listeInscriptions.containsKey(inscriptionId)) {
			return listeInscriptions.get(inscriptionId);
		}
		return null;
	}
	
	public DayOfWeek getDayOfWeek(Jour jour) {
		switch (jour) {
		case LUNDI:
			return DayOfWeek.MONDAY;
		case MARDI:
			return DayOfWeek.TUESDAY;
		case MERCREDI:
			return DayOfWeek.WEDNESDAY;
		case JEUDI:
			return DayOfWeek.THURSDAY;
		case VENDREDI:
			return DayOfWeek.FRIDAY;
		case SAMEDI:
			return DayOfWeek.SATURDAY;
		case DIMANCHE:
			return DayOfWeek.SUNDAY;
		default:
			return null;
		}
	}
	
}
