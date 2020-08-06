 package Tests;
 
 import static main.controleur.Helper.getDateFromString;
 import static org.junit.jupiter.api.Assertions.*;

 import java.time.LocalDate;
import java.util.HashMap;

import main.controleur.*;
import main.modele.*;
import org.junit.jupiter.api.*;


class ControleurClientTests {

	ControleurClient controleurClient;

	@BeforeEach
	void initialisation() {
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
		controleurClient.creerClient(
        		TypeClient.MEMBRE_SUSPENDU, 
        		"Vlad", 
        		"01-01-1970", 
        		"gmail@gmail.com", 
        		"999-999-9999", 
        		"456 avenue Henri", 
        		"Las Vegas", 
        		"Nevada", 
        		"G1H2Y0");

		
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
	}

	@Test
	void testAuthentifier() {
		String idClient = Client.getHashInString("John@doe.com");
		assertTrue(ControleurClient.authentifier(TypeClient.MEMBRE,idClient),"Test authentification échoué");
	}
	
	@Test
	void testCreerClient() {
		//Test inscrire membre qui a payé
		TypeClient typeClient = TypeClient.MEMBRE_VALIDE;
		String nom = "Maryna";
		String dateNaissance = "02-02-1990";
		String adresseCourriel = "maryna@udem.com";
		String numeroTel = "123-123-1234";
		String adresse = "123 rue Martin";
		String ville = "Montreal";
		String province = "Québec";
		String codePostal = "H0H0H0";
		
		controleurClient.creerClient(typeClient, nom, dateNaissance, adresseCourriel, numeroTel, adresse, ville, province, codePostal);

		
		assertTrue(
				ControleurClient.authentifier(TypeClient.MEMBRE,controleurClient.getIdDepuisAdresse(adresseCourriel)),
				"Test membre valide échoué");

		//Test inscrire membre suspendu
		TypeClient typeClient2 = TypeClient.MEMBRE_SUSPENDU;
		String nom2 = "Alex";
		String dateNaissance2 = "02-02-1800";
		String adresseCourriel2 = "adressegenerique@udem.com";
		String numeroTel2 = "123-123-1234";
		String adresse2 = "Yes, Montreal";
		String ville2 = "Montreal";
		String province2 = "Québec";
		String codePostal2 = "H1H1H1";

		
		controleurClient.creerClient(typeClient2, nom2, dateNaissance2, adresseCourriel2, numeroTel2, adresse2, ville2, province2, codePostal2);
		String getInfo2 = controleurClient.getInformationsClient(TypeClient.MEMBRE, controleurClient.getIdDepuisAdresse(adresseCourriel2));
		String infos2 = "ID : " + controleurClient.getIdDepuisAdresse(adresseCourriel2) + "\n" +
				"Nom : " + nom2 + "\n" +
				"Date de naissance : 02-02-1800\n" +
				"Adresse courriel : " + adresseCourriel2 + "\n" +
				"Numéro de téléphone : " + numeroTel2 + "\n" +
				"Adresse : " + adresse2 + "\n" + 
				"Ville : " + ville2 + "\n" +
				"Province : " + province2 + "\n" +
				"Code postal : " + codePostal2 + "\n" +
				"Statut : " + "suspendu\n";
		assertEquals(
				infos2,
				getInfo2,
				"Test membre suspendu échoué");


		//
		//Test inscrire professionnel
		TypeClient typeClient3 = TypeClient.PROFESSIONNEL;
		String nom3 = "Hey toi";
		String dateNaissance3 = "31-12-1999";
		String adresseCourriel3 = "farfar@booboo.com";
		String numeroTel3 = "888-888-8888";
		String adresse3 = "200 avenue du Pabos";
		String ville3 = "Anse-à-Beaufils";
		String province3 = "Québec";
		String codePostal3 = "H1H1H1";
		
		controleurClient.creerClient(typeClient3, nom3, dateNaissance3, adresseCourriel3, numeroTel3, adresse3, ville3, province3, codePostal3);
		String getInfo3 = controleurClient.getInformationsClient(typeClient3, controleurClient.getIdDepuisAdresse(adresseCourriel3));
		String infos3 = "ID : " + controleurClient.getIdDepuisAdresse(adresseCourriel3) + "\n" +
				"Nom : " + nom3 + "\n" +
				"Date de naissance : 31-12-1999\n" +
				"Adresse courriel : " + adresseCourriel3 + "\n" +
				"Numéro de téléphone : " + numeroTel3 + "\n" +
				"Adresse : " + adresse3 + "\n" + 
				"Ville : " + ville3 + "\n" +
				"Province : " + province3 + "\n" +
				"Code postal : " + codePostal3 + "\n";
		assertEquals(
				infos3,
				getInfo3,
				"Test professionnel échoué");
	}

	@Test
	void testMettreClientAJour() {
		TypeClient typeClient = TypeClient.MEMBRE;
		String idClient = controleurClient.getIdDepuisAdresse("John@doe.com");
		String adresse = "nouvelle adresse";
		String dateNaissance = "01-01-0001";
		LocalDate localDate = getDateFromString(dateNaissance);
		String numeroTel = "001-001-0001";
		String nom = "Jean";
		String ville = "Saint-Jean";
		String province = "Saint-John";
		String codePostal = "T8T8T8";
		
		Client client = controleurClient.lireClient(idClient);

		controleurClient.mettreClientAJour(typeClient, idClient, Champs.NOM_CLIENT, nom);
		assertTrue(client.getNom().equals(nom), "Test mettre a jour nom échoué");
		
		assertFalse(client.getAdresse().equals(adresse), "Test mettre a jour adresse échoué");
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.ADRESSE_CLIENT, adresse);
		assertTrue(client.getAdresse().equals(adresse), "Test mettre a jour adresse échoué");
		

		controleurClient.mettreClientAJour(typeClient, idClient, Champs.DATE_NAISSANCE_CLIENT, dateNaissance);
		assertTrue(client.getDateNaissance().toString().equals(localDate.toString()), "Test mettre a jour date échoué");
		
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.TELEPHONE_CLIENT, numeroTel);
		assertTrue(client.getNumeroPhone().equals(numeroTel), "Test mettre a jour numero tel échoué");
		
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.VILLE_CLIENT, ville);
		assertTrue(client.getVille().equals(ville), "Test mettre a jour ville échoué");
		
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.PROVINCE_CLIENT, province);
		assertTrue(client.getProvince().equals(province), "Test mettre a jour province échoué");
		
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.CODEPOSTAL_CLIENT, codePostal);
		assertTrue(client.getCodePostal().equals(codePostal), "Test mettre a jour code postal échoué");
		
		Membre membre = (Membre)client;
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.STATUT_MEMBRE, "");
		assertFalse(membre.getAPaye(), "Test mettre a jour statut échoué");
		
		
		String nouvelleAdresse = "salut@gmail.com";
		
		controleurClient.mettreClientAJour(typeClient, idClient, Champs.ADRESSE_COURRIEL_CLIENT, nouvelleAdresse);
		assertEquals(nouvelleAdresse, controleurClient.lireClient(idClient).getAdresseCourriel(), "Test mettre a jour adresse courriel. Nouveau client non ajouté.");
		assertNotNull(controleurClient.getIdDepuisAdresse(nouvelleAdresse), "Test mettre a jour, nouveau courriel pas ajouté");
		assertNull(controleurClient.getIdDepuisAdresse("John@doe.com"), "Test mettre a jour, ancien courriel non supprimé");
		
		idClient = Client.getHashInString("salut@gmail.com");
		assertNull(controleurClient.lireClient(idClient), "Test mettre a jour adresse courriel. Client créer en double.");

		
	}

	@Test
	void testSupprimerClient() {
		TypeClient typeClient = TypeClient.MEMBRE;
		String idClient = controleurClient.getIdDepuisAdresse("John@doe.com");
		
		assertNotNull(controleurClient.lireClient(idClient),"Test supprimer : client n'existe pas.");
		controleurClient.supprimerClient(typeClient, idClient);
		assertNull(controleurClient.lireClient(idClient),"Test supprimer client échoué");
	}
	
	@Test
	void testVerifierTypeClient() {
		String idClient = Client.getHashInString("John@doe.com");
		TypeClient typeClient = ControleurClient.verifierTypeClient(TypeClient.MEMBRE,idClient);
		assertEquals(TypeClient.MEMBRE_VALIDE, typeClient,"Test vérifier type vrai échoué");
		assertFalse(typeClient.equals(TypeClient.MEMBRE_SUSPENDU),"Test vérifier type faux échoué");

		String idClient2 = Client.getHashInString("Jean@udem.com");
		TypeClient typeClient2 = ControleurClient.verifierTypeClient(TypeClient.PROFESSIONNEL,idClient2);
		assertEquals(TypeClient.PROFESSIONNEL_VALIDE, typeClient2,"Test vérifier type professionnel échoué");
	}
	
	@Test
	void testModifierStatutMembre() {
		String idMembre1 = controleurClient.getIdDepuisAdresse("John@doe.com");
		Membre membre1 = (Membre) controleurClient.lireClient(idMembre1);
		String idMembre2 = controleurClient.getIdDepuisAdresse("gmail@gmail.com");
		Membre membre2 = (Membre) controleurClient.lireClient(idMembre2);
		HashMap<String, Boolean> listeModifs = new HashMap<String, Boolean>();
		listeModifs.put(idMembre1, false);
		listeModifs.put(idMembre2, true);
		
        assertTrue(membre1.getAPaye(),"Test membre 1 devrait être vrai");
		assertFalse(membre2.getAPaye(),"Test membre 2 devrait être faux");
		controleurClient.modifierStatutMembre(listeModifs);
		assertFalse(membre1.getAPaye(),"Test membre 1 devrait être faux");
		assertTrue(membre2.getAPaye(),"Test membre 2 devrait être vrai");
		
	}

}
