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
				Jour.DIMANCHE,
				25,
				idProfessionel1,
				"1234567",
				63.25,
				"Rien à signaler");
		listeServices.put(service1.getCode(), service1);

		Service service2 = new Service("Yoga",
				LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
				LocalDate.of(2015, 11, 30),
				LocalDate.of(2025, 7, 12),
				LocalTime.of(18,20),
				Jour.LUNDI,
				25,
				idProfessionel1,
				"2345678",
				100.00,
				"Aucun commentaire");
		listeServices.put(service2.getCode(), service2);

		Service service3 = new Service("Danse", LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
				LocalDate.of(2017, 11, 15),
				LocalDate.of(2036, 9, 20),
				LocalTime.of(18,20),
				Jour.LUNDI,
				2,
				idProfessionel2,
				"9675882" +
						"",
				50.12,
				"En refonte");
		listeServices.put(service3.getCode(), service3);

		// crée une séance au jour d'exécution du programme pour les tests
		Seance seance1 = new Seance(LocalDateTime.of(today(), LocalTime.of(12, 30)),
				service1.getCode(), service1.getNumeroProfessionnel());
		listeSeances.put(seance1.getCodeSeance(), seance1);

		Seance seance2 = new Seance(LocalDateTime.of(today(), LocalTime.of(17, 20)),
				service2.getCode(), service2.getNumeroProfessionnel());
		listeSeances.put(seance2.getCodeSeance(), seance2);

		Seance seance3 = new Seance(LocalDateTime.of(today(), LocalTime.of(11, 00)).minusDays(1),
				service3.getCode(), service3.getNumeroProfessionnel());
		listeSeances.put(seance3.getCodeSeance(), seance3);

		Seance seance4 = new Seance(LocalDateTime.of(today(), LocalTime.of(15, 55)).minusDays(8),
				service2.getCode(), service2.getNumeroProfessionnel());
		listeSeances.put(seance4.getCodeSeance(), seance4);
		

        Inscription inscription1 = new Inscription(now(),
                seance2.getDateTimeSeance().toLocalDate(),
				idProfessionel1,
				idMembre1,
                seance2.getCodeService(),
                "",
				seance2.getCodeSeance());
        listeInscriptions.put(inscription1.getHashInString(), inscription1);
	}

	public void ajouterService(Service service) {
		if (!listeServices.containsKey(service.getCode())) {
			listeServices.put(service.getCode(), service);
		}
	}
	
	public Service getService(String idService) {
        return listeServices.get(idService);
    }
	
	public Seance getSeance(String idSeance) {
        return listeSeances.get(idSeance);
    }
	
	public void supprimerService(String serviceEntre) {
        listeServices.remove(serviceEntre);
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
	
	public List<Seance> getListeSeances(LocalDate date) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDateTimeSeance().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
	
	public Inscription inscrireMembreASeance(String idMembre, String idSeance, String commentaires) {
        Seance seance = listeSeances.get(idSeance);
        Service service = listeServices.get(seance.getCodeService());
        Inscription inscription = new Inscription(
                LocalDateTime.now(),
                seance.getDateTimeSeance().toLocalDate(),
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

	
	public void confirmationPresence(String idSeance, String idMembre, String commentaires) {
        String codeService = listeSeances.get(idSeance).getCodeService();
        String numeroProfessionnel = listeServices.get(codeService).getNumeroProfessionnel();
        ConfirmationPresence cp = new ConfirmationPresence(now(), idMembre, numeroProfessionnel, codeService, commentaires);
        if (!listeConfirmationsPresence.containsKey(cp.getHashInString())) {
            listeConfirmationsPresence.put(cp.getHashInString(), cp);
        }
    }
	
	public List<Seance> getSeancesEntre(LocalDate debut, LocalDate fin) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDateTimeSeance().toLocalDate().compareTo(debut) >= 0 && x.getDateTimeSeance().toLocalDate().compareTo(fin) <= 0)
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
				service.setRecurrenceHebdo(getJourFromString(valeur));
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
}
