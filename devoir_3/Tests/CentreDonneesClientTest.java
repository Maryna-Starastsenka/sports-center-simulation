import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class CentreDonneesClientTest {

	private CentreDonneesClient centreDonneesClient;
	
	public CentreDonneesClientTest() {
		
		this.centreDonneesClient = new CentreDonneesClient();
		
        
	}
	
	@Test
	void testInscrireClient() {
		//Test inscrire membre qui a payé
		String nom = "Maryna";
        LocalDate date = LocalDate.of(1989, 9, 28);
        String adresse = "123 rue Martin, Montreal";
        String numeroTel = "123-123-1234";
        String adresseCourriel = "maryna@udem.com";
        String membreValide = "1";
		Client membre = centreDonneesClient.inscrireClient(membreValide, nom, date, adresse, numeroTel, adresseCourriel);
		assertTrue(centreDonneesClient.estMembre(membre.getHashInString()),"Test membre qui paye échoué");
		assertTrue(centreDonneesClient.estMembreValide(membre.getHashInString()), "Test membre qui paye valide échoué");
		assertEquals(membre.getHashInString(), centreDonneesClient.getNumeroClient("maryna@udem.com"), "Test hash numéro échoué");
		
		//Test inscrire membre qui n'a pas payé
		String nom2 = "Alex";
        LocalDate date2 = LocalDate.of(1800, 1, 1);
        String adresse2 = "Yes, Montreal";
        String numeroTel2 = "123-123-1234";
        String adresseCourriel2 = "adressegenerique@udem.com";
        String membreSuspendu = "2";
        Client suspendu = centreDonneesClient.inscrireClient(membreSuspendu, nom2, date2, adresse2, numeroTel2, adresseCourriel2);
        assertTrue(centreDonneesClient.estMembre(suspendu.getHashInString()), "Test membre suspendu échoué");
		assertFalse(centreDonneesClient.estMembreValide(suspendu.getHashInString()), "Test membre suspendu non valide échoué");

		//
		String nom3 = "Hey toi";
        LocalDate date3 = LocalDate.of(1999, 12, 31);
        String adresse3 = "200 avenue du Pabos, Anse-à-Beaufils";
        String numeroTel3 = "888-888-8888";
        String adresseCourriel3 = "farfar@booboo.com";
        String clientProf = "3";
        Client professionnel = centreDonneesClient.inscrireClient(clientProf, nom3, date3, adresse3, numeroTel3, adresseCourriel3);
        assertTrue(centreDonneesClient.estProfessionnel(professionnel.getHashInString()), "Test professionnel échoué");
		assertFalse(centreDonneesClient.estMembre(professionnel.getHashInString()), "Test professionnel n'est pas membre échoué");
		
		
	}


	@Test
	void testSupprimerMembre() {
		centreDonneesClient.supprimerMembre(centreDonneesClient.getNumeroClient("adressegenerique@udem.com"));
		assertFalse(centreDonneesClient.estMembre(centreDonneesClient.getNumeroClient("adressegenerique@udem.com")), "Test supprimer membre échoué");
	}

	@Test
	void testSupprimerProfessionnel() {
		centreDonneesClient.supprimerMembre(centreDonneesClient.getNumeroClient("farfar@booboo.com"));
		assertFalse(centreDonneesClient.estProfessionnel(centreDonneesClient.getNumeroClient("farfar@booboo.com")), "Test supprimer professionnel échoué");
	}

}
