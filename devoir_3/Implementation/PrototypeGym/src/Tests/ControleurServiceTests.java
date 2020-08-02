package Tests;

import static main.controleur.Verificateurs.getDateFromString;
import static main.controleur.Verificateurs.getDoubleFromString;
import static main.controleur.Verificateurs.getHeureFromString;
import static main.controleur.Verificateurs.getIntFromString;
import static main.controleur.Verificateurs.getJourFromString;
import static main.modele.Champs.DATE_FIN_SERVICE;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;


import main.controleur.*;
import main.modele.*;
import main.vue.*;

class ControleurServiceTests {

	@Test
	void testCreerService() {

		ControleurService controleurService = new ControleurService();
		String nomService = "danse";
		String dateDebutServiceString = "01-08-2020";
		String dateFinServiceString = "01-08-2021";
		String heureServiceString = "08:30";
		String recurrenceHebdoString = "mercredi";
		String capaciteMaximaleString = "25";
		String numeroProfessionnel = "150337313";
		String codeService = "0101010";
		String fraisServiceString = "050.50";
		String commentaires = "non";
		controleurService.creerService(nomService, dateDebutServiceString, dateFinServiceString, heureServiceString, 
				recurrenceHebdoString, capaciteMaximaleString, numeroProfessionnel, codeService, fraisServiceString, commentaires);

		String infoService = "ID : 0101010\n" +
				"Nom de service : danse\n" +
				"Date de début de service : 2020-08-01\n" +
				"Date de fin de service : 2021-08-01\n" +
				"Heure de service : 08:30\n" +
				"Récurrence hebdomadaire : MERCREDI\n" +
				"Capacité maximale : 25\n" +
				"Numéro de professionnel : 150337313\n" +
				"Frais de service : 50.5\n" +
				"Commentaire : non\n";

		assertEquals(controleurService.getInformationsService(codeService),infoService, "Test créer service échoué");

	}

	@Test
	void testMettreServiceAJour() {

		String idService = "1234567";
		ControleurService controleurService = new ControleurService();

		controleurService.mettreServiceAJour(idService, Champs.NOM_SERVICE, "Massage");
		controleurService.mettreServiceAJour(idService, Champs.DATE_DEBUT_SERVICE, "02-03-2020");
		controleurService.mettreServiceAJour(idService, Champs.DATE_FIN_SERVICE, "03-03-2020");
		controleurService.mettreServiceAJour(idService, Champs.HEURE_SERVICE, "09:15");
		controleurService.mettreServiceAJour(idService, Champs.RECURRENCE_HEBDO_SERVICE, "Mardi");
		controleurService.mettreServiceAJour(idService, Champs.CAPACITE_MAX_SERVICE, "30");
		controleurService.mettreServiceAJour(idService, Champs.FRAIS_SERVICE, "60.00");
		controleurService.mettreServiceAJour(idService, Champs.COMMENTAIRE_SERVICE, "Oui");

		String infoService = "ID : 1234567\n" +
				"Nom de service : Massage\n" +
				"Date de début de service : 2020-03-02\n" +
				"Date de fin de service : 2020-03-03\n" +
				"Heure de service : 09:15\n" +
				"Récurrence hebdomadaire : MARDI\n" +
				"Capacité maximale : 30\n" +
				"Numéro de professionnel : 150337313\n" +
				"Frais de service : 60.0\n" +
				"Commentaire : Oui\n";
		System.out.println(infoService);
		System.out.println(controleurService.getInformationsService(idService));
		assertEquals(controleurService.getInformationsService(idService),infoService, "Test modifier service échoué");
	}

	@Test
	void testSupprimerService() {
		String idService = "1234567";
		ControleurService controleurService = new ControleurService();
		
		assertNotNull(controleurService.lire(idService),"Test supprimer service. Service n'existe pas");
		
		controleurService.supprimerService(idService);
		assertNull(controleurService.lire(idService),"Test suppermier service. Service pas supprimé.");
	}

	@Test
	void testInscriptionSeance() {
		fail("Not yet implemented");
	}

	@Test
	void testConfirmerPresence() {
		fail("Not yet implemented");
	}

	@Test
	void testProcedureComptable() {
		fail("Not yet implemented");
	}

}
