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

	/**
	 * Constructeur de CentreDonneesServices
	 */
	public CentreDonneesServices() {
		this.listeServices = new HashMap<>();
		this.listeSeances = new HashMap<>();
		this.listeInscriptions = new HashMap<>();
		this.listeConfirmationsPresence = new HashMap<>();
		this.listeProfessionnelsTef = new ArrayList<>();
		this.listeMembresTef = new ArrayList<>();
	}

	/**
	 * Ajoute le service dans la liste des services
	 * @param service service
	 */
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

	/**
	 * Retire le service de la liste des services
	 * @param serviceId code du service
	 */
	public void supprimerService(String serviceId) {
        listeServices.remove(serviceId);
    }

	/**
	 * Met à jour les séances
	 */
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

	/**
	 * Retourne la liste des services fournis par le professionnel
	 * @param idProfessionnel numéro unique du professionnel
	 * @return liste des services fournis par le professionnel
	 */
	public List<Service> getListeServicesPro(String idProfessionnel) {
        return listeServices
                .values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

	/**
	 * Retourne la liste des séances du professionnel
	 * @param idProfessionnel numéro unique du professionnel
	 * @return liste des séances du professionnel
	 */
	public List<Seance> getListeSeancesPro(String idProfessionnel) {
		return listeSeances
				.values()
				.stream()
				.filter(x -> x.getCodeProfessionnel().equals(idProfessionnel))
				.collect(Collectors.toList());
	}

	/**
	 * Retourne la liste des séances disponibles un jour donné
	 * @param jour jour auquel on souhaite obtenir la liste des séances
	 * @return liste des séances disponibles le jour spécifié
	 */
	public List<Seance> getListeSeances(LocalDate jour) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDate().equals(jour))
                .collect(Collectors.toList());
    }

	/**
	 * Retourne l'inscription du membre à la séance
	 * @param idMembre numéro unique du membre
	 * @param nomClient nom du client
	 * @param idSeance code de la séance
	 * @param commentaires commentaires
	 * @return inscription du membre à la séance
	 */
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

	/**
	 * Vérifie si le membre est inscrit à la séance
	 * @param idMembre numéro du membre
	 * @param idSeance code de la séance
	 * @return vrai si le membre est inscrit à la séance. Sinon, retourne faux
	 */
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

	/**
	 * Retourne la liste des inscriptions associées au numéro du professionnel
	 * @param idProfessionnel numéro du professionnel
	 * @return liste des inscriptions associées au numéro du professionnel
	 */
	public List<Inscription> getListeInscriptionsPro(String idProfessionnel) {
        return listeInscriptions
                .values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

	/**
	 * Retourne la liste des inscriptions à la séance
	 * @param idSeance code de la séance
	 * @return liste des inscriptions à la séance
	 */
	public List<Inscription> getListeInscriptionsSeance(String idSeance) {
		return listeInscriptions
				.values()
				.stream()
				.filter(x -> x.getCodeSeance().equals(idSeance))
				.collect(Collectors.toList());
	}


	/**
	 * Confirme la présence d'un membre à une séance
	 * @param idSeance code de la séance
	 * @param idMembre numéro du membre
	 * @param commentaires commentaires
	 * @return vrai si la confirmation de présence est créée. Sinon, retourne faux
	 */
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

	/**
	 * Retourne la liste des séances entre deux dates
	 * @param debut date de début
	 * @param fin date de fin
	 * @return liste des séances entre les deux dates spécifiées
	 */
	public List<Seance> getSeancesEntre(LocalDate debut, LocalDate fin) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDate().compareTo(debut) >= 0 && x.getDate().compareTo(fin) <= 0)
                .collect(Collectors.toList());
    }

	/**
	 * Retourne la liste des inscriptions entre deux dates
	 * @param debut date de début
	 * @param fin date de fin
	 * @return liste des inscriptions entre les deux dates spécifiées
	 */
	public List<Inscription> getInscriptionsEntre(LocalDate debut, LocalDate fin) {
        return listeInscriptions
                .values()
                .stream()
                .filter(x -> x.getDateSeance().compareTo(debut) >= 0 && x.getDateSeance().compareTo(fin) <= 0)
                .collect(Collectors.toList());
    }


	/**
	 * Génère les fichiers TEF pour les professionnels
	 * @param listeProfessionnels liste des professionnels
     * @return liste TEF professionnels
	 */
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

	/**
	 * Génère les fichiers TEF pour les membres
	 * @param listeMembres liste des membres
	 * @return liste TEF membre
	 */
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

	/**
	 * Génère le rapport de synthèse
	 * @param listeProfessionnels liste des professionnels
	 * @return rapport de synthèse
	 */
	public RapportSynthese genererRapportSynthese(HashMap<String, Professionnel> listeProfessionnels) {
		genererTefProfessionnel(listeProfessionnels);
		int nombreTotalServices = listeProfessionnelsTef.stream().mapToInt(ProfessionnelTef::getNombreServices).sum();
		double fraisTotaux = listeProfessionnelsTef.stream().mapToDouble(ProfessionnelTef::getMontant).sum();
		return new RapportSynthese(listeProfessionnelsTef,
				listeProfessionnelsTef.size(),
				nombreTotalServices,
				fraisTotaux);
	}

	/**
	 * Renvoie la liste de tous les services.
	 * @return liste des services
	 */
	public List<Service> getServices() {
		return new ArrayList<>(listeServices.values());
	}

	/**
	 * Crée un client
	 * @param client client
	 */
	@Override
	public void creer(Object client) {
	}

	/**
	 * Retourne le service
	 * @param id numéro du client
	 * @return service
	 */
	@Override
	public Service lire(String id) {
		if (listeServices.containsKey(id)) {
			return listeServices.get(id);
		}
		return null;
	}

	/**
	 * Retourne la séance
	 * @param id code de la séance
	 * @return séance
	 */
	public Seance lireSeance(String id) {
		if (listeSeances.containsKey(id)) {
			return listeSeances.get(id);
		}
		return null;
	}

	/**
	 * Met à jour les informations sur le service
	 * @param idSeance code de la séance
	 * @param champsService type d'information à modifier
	 * @param valeur nouvelle valeur
	 */
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

	/**
	 * Retire la séance de la listes des séances
	 * @param seanceId code de la séance
	 */
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

	/**
	 * Retourne l'inscription associée au code
	 * @param inscriptionId code de l'inscription
	 * @return inscription
	 */
	public Inscription getInscription(String inscriptionId) {
		if (listeInscriptions.containsKey(inscriptionId)) {
			return listeInscriptions.get(inscriptionId);
		}
		return null;
	}

	/**
	 * Retourne la liste des services
	 * @return liste des services
	 */
	public List<Service> getListeServices() {
		return new ArrayList<>(listeServices.values());
	}

	/**
	 * Retourne la liste des séances
	 * @return liste des séances
	 */
	public List<Seance> getListeSeances() {
		return new ArrayList<>(listeSeances.values());
	}
	
}
