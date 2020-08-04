package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

		Seance seance = service.obtenirListeSeances().get(0);
		String codeSeance = seance.getCodeSeance();

		 
		String infoService = "ID : " + codeSeance + "\n" +
				"Nom de service : danse\n" +
				"Date de début de service : 2020-08-01\n" +
				"Date de fin de service : 2021-08-01\n" +
				"Heure de service : 08:30\n" +
				"Récurrence hebdomadaire : WEDNESDAY\n" +
				"Capacité maximale : 25\n" +
				"Numéro de professionnel : 150337313\n" +
				"Frais de service : 50.5\n" +
				"Commentaire : non\n";

		assertEquals(controleurService.getInformationsService(codeSeance),infoService, "Test créer service échoué");

	}

	@Test
	void testMettreServiceAJour() {

		List<String> seancesId = controleurService.obtenirListeSeancesDuProfessionnel("150337313");
		Seance seance = controleurService.lireSeance(seancesId.get(0));
		String idSeance = seance.getCodeSeance();

		controleurService.mettreServiceAJour(idSeance, Champs.NOM_SERVICE, "Massage");
		idSeance = seance.getCodeSeance();
		controleurService.mettreServiceAJour(idSeance, Champs.DATE_DEBUT_SERVICE, "02-03-2020");
		controleurService.mettreServiceAJour(idSeance, Champs.DATE_FIN_SERVICE, "03-03-2020");
		controleurService.mettreServiceAJour(idSeance, Champs.HEURE_SERVICE, "09:15");
		controleurService.mettreServiceAJour(idSeance, Champs.RECURRENCE_HEBDO_SERVICE, "Mardi");
		 idSeance = seance.getCodeSeance();
		controleurService.mettreServiceAJour(idSeance, Champs.CAPACITE_MAX_SERVICE, "30");
		controleurService.mettreServiceAJour(idSeance, Champs.FRAIS_SERVICE, "60.00");
		controleurService.mettreServiceAJour(idSeance, Champs.COMMENTAIRE_SERVICE, "Oui");

		String infoService = "ID : "+seance.getCodeSeance() + "\n" +
				"Nom de service : Massage\n" +
				"Date de début de service : 2020-03-02\n" +
				"Date de fin de service : 2020-03-03\n" +
				"Heure de service : 09:15\n" +
				"Récurrence hebdomadaire : TUESDAY\n" +
				"Capacité maximale : 30\n" +
				"Numéro de professionnel : 150337313\n" +
				"Frais de service : 60.0\n" +
				"Commentaire : Oui\n";
		
		assertEquals(controleurService.getInformationsService(idSeance),infoService, "Test modifier service échoué");
	}

	@Test
	void testSupprimerService() {
		List<String> seancesId = controleurService.obtenirListeSeancesDuProfessionnel("150337313");
		Seance seance = controleurService.lireSeance(seancesId.get(0));
		String idSeance = seance.getCodeSeance();
		
		assertNotNull(controleurService.lireSeance(idSeance),"Test supprimer service. Service n'existe pas");
		controleurService.supprimerService(idSeance);
		assertNull(controleurService.lireSeance(idSeance),"Test supprimer service. Service pas supprimé.");
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
		
		assertTrue(controleurService.confirmerPresence(idSeance, idMembre, commentaire),"Test ajout confirmation");
		assertFalse(controleurService.confirmerPresence(idSeance, idMembre, commentaire),"Test ajout confirmation");
	}

}
