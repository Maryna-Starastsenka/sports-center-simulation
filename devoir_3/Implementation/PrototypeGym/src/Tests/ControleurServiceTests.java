package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import main.controleur.*;
import main.modele.*;

class ControleurServiceTests {
	
	ControleurService controleurService;
	ControleurClient controleurClient;
	
	@BeforeEach
	void initialisation() {
		this.controleurService = new ControleurService();
		this.controleurClient = new ControleurClient();

		/*** MEMBRES ***/
		controleurClient.creerClient(
        		TypeClient.MEMBRE_VALIDE, 
        		"John Doe", 
        		"02-02-1970", 
        		"John@doe.com", 
        		"999-999-9999", 
        		"456 du Brésil", 
        		"Memphré", 
        		"Québec", 
        		"G1H2Y8");

		
        /*** PROFESSIONNELS ***/

        controleurClient.creerClient(
        		TypeClient.PROFESSIONNEL, 
        		"Jean", 
        		"25-12-1975", 
        		"Jean@udem.com", 
        		"987-987-9876", 
        		"456 rue Michel", 
        		"Laval", 
        		"Québec", 
        		"G2T2T2");

        controleurClient.creerClient(
        		TypeClient.PROFESSIONNEL, 
        		"Baptiste", 
        		"18-06-1970", 
        		"baptiste@udem.com", 
        		"182-323-3432", 
        		"1000 bld Henri", 
        		"Longueil", 
        		"Québec", 
        		"T6Y0K0");
		
		String idProfessionel1 = "150337313";
		String idProfessionel2 = "173262475";
		String idMembre1 = "554365143";

		controleurService.creerService(
				"Zumba",
				"19-07-2020",
				"31-12-2025",
				"22:30",
				"mercredi",
				"25",
				idProfessionel1,
				"63.25",
				"Rien à signaler");
		

		controleurService.creerService(
				"Yoga",
				"30-11-2015",
				"12-07-2025",
				"18:20",
				"lundi",
				"20",
				idProfessionel1,
				"100.00",
				"Rien à signaler");


		controleurService.creerService(
				"Danse",
				"15-11-2017",
				"20-09-2036",
				"15:30",
				"lundi",
				"2",
				idProfessionel1,
				"50.12",
				"En refonte");

		
		// crée une séance un autre jour qu'un service pareil
		controleurService.creerService(
				"Zumba",
				"19-07-2020",
				"31-12-2025",
				"22:30",
				"lundi",
				"25",
				idProfessionel1,
				"63.25",
				"Rien à signaler");
		

		List<String> listeSeances =  controleurService.obtenirListeSeancesDuProfessionnel(idProfessionel1);
		controleurService.inscriptionSeance(idMembre1, controleurClient.nomClient(idMembre1), listeSeances.get(0), "J'ai hâte!");
	}

	@Test
	void testCreerService() {

		//Création d'un nouveau service
		String nomService = "danse";
		String dateDebutServiceString = "01-08-2020";
		String dateFinServiceString = "01-08-2021";
		String heureServiceString = "08:30";
		String recurrenceHebdoString = "mercredi";
		String capaciteMaximaleString = "25";
		String numeroProfessionnel = "150337313";
		String fraisServiceString = "050.50";
		String commentaires = "non";
		Service service = controleurService.creerService(nomService, dateDebutServiceString, dateFinServiceString, heureServiceString, 
				recurrenceHebdoString, capaciteMaximaleString, numeroProfessionnel, fraisServiceString, commentaires);

		//Vérifier qu'une séance a bien été ajoutée
		List<Seance> seances = service.obtenirListeSeances();
		Seance seance = null;
		for(Seance s : seances) {
			if (s.getRecurrence().equals(DayOfWeek.WEDNESDAY)&&s.getService().equals(service))
				seance = s;
		}
		assertNotNull(seance, "Test échoué :  la séance n'est pas ajouté dans la liste de séances du service.");
		
		//Test que les informations du service concordent
		String codeSeance = seance.getCodeSeance();
		String infoService = "Numéro de séance : " + codeSeance + "\n" +
			"Numéro de professionnel de séance : " + numeroProfessionnel + "\n" +
			"Récurrence hebdomadaire : MERCREDI" + "\n" +
			"Nom de service : danse" + "\n" +
			"Date de début de service : 2020-08-01" + "\n" +
			"Date de fin de service : 2021-08-01" + "\n" +
			"Heure de service : 08:30" + "\n" +
			"Capacité maximale : 25" + "\n" +
			"Numéro de professionnel : " + numeroProfessionnel + "\n" +
			"Frais de service : 50.5" + "\n" +
			"Commentaire : non" + "\n";
		assertEquals(infoService, controleurService.getInformationsService(codeSeance), "Test créer service, les informations ne sont pas bonnes.");
		
		//Test que deux séances pareils, mais avec deux différents professionnels ne devraient pas être pareils.
		String numeroProfessionnel2 = "250337313";
		Service service2 = controleurService.creerService(nomService, dateDebutServiceString, dateFinServiceString, heureServiceString, 
				recurrenceHebdoString, capaciteMaximaleString, numeroProfessionnel2, fraisServiceString, commentaires);		
		seances = service2.obtenirListeSeances();
		Seance seance2 = null;
		for(Seance s : seances) {
			if (s.getRecurrence().equals(DayOfWeek.WEDNESDAY)&&s.getService().equals(service2))
				seance2 = s;
		}
		assertNotEquals(seance.getCodeSeance(),seance2.getCodeSeance(),"Tests :  deux séances pareilles fait par deux professionnels différents ne devraient pas être pareilles");
		
		List<Seance> seancesCentreDonnees= controleurService.obtenirListeSeances();
		assertTrue(seancesCentreDonnees.contains(seance),"Test échoué: la séance n'est pas ajoutée dans la liste de CentreDonneesService");
		assertTrue(seancesCentreDonnees.contains(seance2),"Test échoué: la séance2 n'est pas ajoutée dans la liste de CentreDonneesService");
	}

	@Test
	void testMettreServiceAJour() {

		String idProfessionnel = "150337313";
		List<String> seancesId = controleurService.obtenirListeSeancesDuProfessionnel(idProfessionnel);
		Seance seance = controleurService.lireSeance(seancesId.get(0));
		String idSeance = seance.getCodeSeance();
		
		//Mise à jour du nom du service et de son code et du code de ses séances.
		controleurService.mettreServiceAJour(idSeance, Champs.NOM_SERVICE, "Massage");
		String idSeance2 = seance.getCodeSeance();
		assertNotEquals(idSeance,idSeance2,"Test échoué : Le code de séance n'a pas été modifié après le changement de service.");
		assertNull(controleurService.lireSeance(idSeance), "Test échoué :  ancienne séance pas supprimée.");
		assertNotNull(controleurService.lireSeance(idSeance2), "Test échoué :  séance modifiée pas ajoutée.");

		//Mise à jour de la récurrence d'une séance et modification du code de la séance.
		controleurService.mettreServiceAJour(idSeance2, Champs.RECURRENCE_HEBDO_SERVICE, "Vendredi");
		String idSeance3 = seance.getCodeSeance();
		assertNotEquals(idSeance2,idSeance3,"Test échoué : Le code de séance n'a pas été modifié après le changement de récurrence.");
		assertNull(controleurService.lireSeance(idSeance2), "Test échoué :  ancienne séance pas supprimée.");
		assertNotNull(controleurService.lireSeance(idSeance3), "Test échoué :  séance modifiée pas ajoutée.");
		
		//Modifications des autres informations
		controleurService.mettreServiceAJour(idSeance3, Champs.DATE_DEBUT_SERVICE, "02-03-2020");
		controleurService.mettreServiceAJour(idSeance3, Champs.DATE_FIN_SERVICE, "03-03-2025");
		controleurService.mettreServiceAJour(idSeance3, Champs.HEURE_SERVICE, "09:15");
		controleurService.mettreServiceAJour(idSeance3, Champs.CAPACITE_MAX_SERVICE, "30");
		controleurService.mettreServiceAJour(idSeance3, Champs.FRAIS_SERVICE, "60.00");
		controleurService.mettreServiceAJour(idSeance3, Champs.COMMENTAIRE_SERVICE, "Oui");

		//Vérification que toutes les informations correspondent
		String infoService = "Numéro de séance : " + seance.getCodeSeance() + "\n" +
		"Numéro de professionnel de séance : " + idProfessionnel + "\n" +
		"Récurrence hebdomadaire : VENDREDI" + "\n" +
		"Nom de service : Massage" + "\n" +
		"Date de début de service : 2020-03-02" + "\n" +
		"Date de fin de service : 2025-03-03" + "\n" +
		"Heure de service : 09:15" + "\n" +
		"Capacité maximale : 30" + "\n" +
		"Numéro de professionnel : 150337313" + "\n" +
		"Frais de service : 60.0" + "\n" +
		"Commentaire : Oui" + "\n";
		assertEquals(infoService, controleurService.getInformationsService(idSeance3), "Test échoué : les informations n'ont pas été bien modifiées.");
	}

	@Test
	void testSupprimerService() {
		//Trouver un service qui a plus qu'une séance pour les tests
		List<Service> services = controleurService.obtenirListeServices();
		Service serviceASupprimer = null;
		for(Service s : services) {
			if(s.obtenirListeSeances().size()>1) {
				serviceASupprimer = s;
			}
		}
		assertNotNull(serviceASupprimer, "Aucun service avec plus d'une séance à tester");
		
		//Supprimer une séance du service sans supprimer le service
		Seance seanceASupprimer = serviceASupprimer.obtenirListeSeances().get(0);
		String idSeance = seanceASupprimer.getCodeSeance();
		assertNotNull(controleurService.lireSeance(idSeance),"Test supprimer une séance. La séance n'existe pas.");
		controleurService.supprimerService(idSeance);
		assertNull(controleurService.lireSeance(idSeance),"Test supprimer séance. La séance n'est pas supprimé.");
		assertTrue(serviceASupprimer.obtenirListeSeances().size()==1, "Test échoué : le service ne devrait avoir plus qu'une séance.");
		
		//Supprimer la dernière séance devrait supprimer le service
		seanceASupprimer = serviceASupprimer.obtenirListeSeances().get(0);
		idSeance = seanceASupprimer.getCodeSeance();
		controleurService.supprimerService(idSeance);
		assertNull(controleurService.lireSeance(idSeance),"Test supprimer service. La dernière séance n'est pas supprimé.");
		services = controleurService.obtenirListeServices();
		assertFalse(services.contains(serviceASupprimer), "Le service n'a pas été supprimé");
	}

	@Test
	void testInscriptionSeance() {
		String professionnelId = "150337313";
		String membreId = "554365143";
		List<String> seancesId = controleurService.obtenirListeSeancesDuProfessionnel(professionnelId);
		Seance seance = controleurService.lireSeance(seancesId.get(1));
		String idSeance = seance.getCodeSeance();

		String commentaire = "Pas de commentaire";
		
		assertFalse(controleurService.inscriptionExiste(membreId, idSeance), "Test inscription échoué");
		controleurService.inscriptionSeance(membreId, controleurClient.nomClient(membreId), idSeance, commentaire);
		assertTrue(controleurService.inscriptionExiste(membreId, idSeance), "Test inscription échoué");
	}

	@Test
	void testConfirmerPresence() {
		String professionnelId = "150337313";
		String idMembre = "554365143";
		List<String> idSeances = controleurService.obtenirListeSeancesDuProfessionnel(professionnelId);
		Seance seance = controleurService.lireSeance(idSeances.get(0));
		String idSeance = seance.getCodeSeance();
		String commentaire = "Pas de commentaire";
		
		//Test que la confirmation est bien ajoutée
		assertTrue(controleurService.confirmerPresence(idSeance, idMembre, commentaire),"Test ajout confirmation");
		//Test que la confirmation ne peut pas être ajoutée une deuxième fois.
		assertFalse(controleurService.confirmerPresence(idSeance, idMembre, commentaire),"Test ajout confirmation");
	}

}
