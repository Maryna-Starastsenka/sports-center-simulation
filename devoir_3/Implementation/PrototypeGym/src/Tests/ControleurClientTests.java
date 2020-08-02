 package Tests;
 
 import static main.controleur.Verificateurs.getDateFromString;
 import static org.junit.jupiter.api.Assertions.*;

 import java.time.LocalDate;

 
import main.controleur.*;
import main.modele.*;
import main.vue.*;
import org.junit.jupiter.api.*;


class ControleurClientTests {

	
	@Test
	void testAuthentifier() {
		
		ControleurClient controleurClient = new ControleurClient();
		String idClient = Client.getHashInString("John@doe.com");
		assertTrue(ControleurClient.authentifier(TypeClient.MEMBRE,idClient),"Test authentification échoué");
	}
	
	@Test
	void testCreerClient() {

		ControleurClient controleurClient = new ControleurClient();
		
		//Test inscrire membre qui a payé
		TypeClient typeClient = TypeClient.MEMBRE_VALIDE;
		String nom = "Maryna";
		String dateNaissance = "02-02-1990";
		String adresseCourriel = "maryna@udem.com";
		String numeroTel = "123-123-1234";
		String adresse = "123 rue Martin, Montreal";
		
		controleurClient.creerClient(typeClient, typeClient, nom, dateNaissance, adresseCourriel, numeroTel, adresse);

		
		assertTrue(ControleurClient.authentifier(TypeClient.MEMBRE,Client.getHashInString(adresseCourriel)),"Test membre valide échoué");
		//assertTrue(centreDonneesClient.estMembreValide(membre.getHashInString()), "Test membre qui paye valide échoué");
		//assertEquals(membre.getHashInString(), centreDonneesClient.getNumeroClient("maryna@udem.com"), "Test hash numéro échoué");

		//Test inscrire membre suspendu
		TypeClient typeClient2 = TypeClient.MEMBRE_SUSPENDU;
		String nom2 = "Alex";
		String dateNaissance2 = "02-02-1800";
		String adresseCourriel2 = "adressegenerique@udem.com";
		String numeroTel2 = "123-123-1234";
		String adresse2 = "Yes, Montreal";
		
		controleurClient.creerClient(typeClient2, typeClient2, nom2, dateNaissance2, adresseCourriel2, numeroTel2, adresse2);
		assertTrue(ControleurClient.authentifier(TypeClient.MEMBRE,Client.getHashInString(adresseCourriel2)),"Test membre suspendu échoué");
		//assertFalse(centreDonneesClient.estMembreValide(suspendu.getHashInString()), "Test membre suspendu non valide échoué");

		//
		//Test inscrire membre suspendu
		TypeClient typeClient3 = TypeClient.PROFESSIONNEL;
		String nom3 = "Hey toi";
		String dateNaissance3 = "31-12-1999";
		String adresseCourriel3 = "farfar@booboo.com";
		String numeroTel3 = "888-888-8888";
		String adresse3 = "200 avenue du Pabos, Anse-à-Beaufils";;
		
		controleurClient.creerClient(typeClient3, typeClient3, nom3, dateNaissance3, adresseCourriel3, numeroTel3, adresse3);
		assertTrue(ControleurClient.authentifier(TypeClient.PROFESSIONNEL,Client.getHashInString(adresseCourriel3)),"Test professionnel échoué");
	}

	@Test
	void testMettreClientAJour() {
		
		ControleurClient controleurClient = new ControleurClient();
		VueMembre vue = new VueMembre();
		String idClient = Client.getHashInString("John@doe.com");
		String adresse = "nouvelle adresse";
		String dateNaissance = "01-01-0001";
		LocalDate localDate = getDateFromString(dateNaissance);
		String numeroTel = "001-001-0001";
		
		Client client = controleurClient.lireClient(idClient);

		assertFalse(client.getAdresse().equals(adresse), "Test mettre a jour adresse échoué");
		controleurClient.mettreClientAJour(vue, idClient, Champs.ADRESSE_CLIENT, adresse);
		assertTrue(client.getAdresse().equals(adresse), "Test mettre a jour adresse échoué");
		

		controleurClient.mettreClientAJour(vue, idClient, Champs.DATE_NAISSANCE_CLIENT, dateNaissance);
		assertTrue(client.getDateNaissance().toString().equals(localDate.toString()), "Test mettre a jour date échoué");
		
		controleurClient.mettreClientAJour(vue, idClient, Champs.TELEPHONE_CLIENT, numeroTel);
		assertTrue(client.getNumeroPhone().equals(numeroTel), "Test mettre a jour numero tel échoué");
		
		Membre membre = (Membre)client;
		controleurClient.mettreClientAJour(vue, idClient, Champs.STATUT_MEMBRE, "");
		assertFalse(membre.getAPaye(), "Test mettre a jour statut échoué");

		
	}

	@Test //TODO
	void testSupprimerClient() {
		ControleurClient controleurClient = new ControleurClient();
		VueMembre vue = new VueMembre();
		String idClient = Client.getHashInString("John@doe.com");
		
		controleurClient.supprimerClient(vue, idClient);
		//assertFalse(controleurClient.lireClient(idClient),"Test supprimer client échoué");
	}
	
	@Test
	void testVerifierTypeClient() {
		ControleurClient controleurClient = new ControleurClient();
		String idClient = Client.getHashInString("John@doe.com");
		TypeClient typeClient = ControleurClient.verifierTypeClient(TypeClient.MEMBRE,idClient);
		assertEquals(typeClient,TypeClient.MEMBRE_VALIDE,"Test vérifier type vrai échoué");
		assertFalse(typeClient.equals(TypeClient.MEMBRE_SUSPENDU),"Test vérifier type faux échoué");

		String idClient2 = Client.getHashInString("Jean@udem.com");
		TypeClient typeClient2 = ControleurClient.verifierTypeClient(TypeClient.PROFESSIONNEL,idClient2);
		assertEquals(typeClient2,TypeClient.PROFESSIONNEL_VALIDE,"Test vérifier type professionnel échoué");
	}

}
