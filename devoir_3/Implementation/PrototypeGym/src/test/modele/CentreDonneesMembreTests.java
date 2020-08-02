package test.modele;

import main.modele.CentreDonneesMembre;
import main.modele.Client;
import main.modele.Membre;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CentreDonneesMembreTests {

    private CentreDonneesMembre centreDonneesMembre;
    Membre membre1, membre2;

    public CentreDonneesMembreTests() {
    }

    @BeforeAll
    public void initialisation() {
        centreDonneesMembre = new CentreDonneesMembre();
        for (Client membre : centreDonneesMembre.getClients()) {
            centreDonneesMembre.supprimer(membre.getHashInString());
        }

        String nom1 = "Maryna";
        LocalDate date1 = LocalDate.of(1950, 10, 1);
        String adresse1 = "123 rue Qwerty, Montreal";
        String numeroTel1 = "123-123-1234";
        String adresseCourriel1 = "maryna@udem.com";
        boolean aPaye1 = true;

        String nom2 = "Alex";
        LocalDate date2 = LocalDate.of(1800, 1, 1);
        String adresse2 = "Yes, Montreal";
        String numeroTel2 = "321-321-3214";
        String adresseCourriel2 = "adressegenerique@udem.com";
        boolean aPaye2 = false;

        membre1 = new Membre(nom1, date1, adresse1, numeroTel1, adresseCourriel1, aPaye1);
        membre2 = new Membre(nom2, date2, adresse2, numeroTel2, adresseCourriel2, aPaye2);
    }

    @Test
    public void lireMembreDoitRetournerMembre() {

        centreDonneesMembre.creer(membre1);
        assertTrue(centreDonneesMembre.lire(membre1.getHashInString()).equals(membre1));

        centreDonneesMembre.creer(membre2);
        assertNotEquals(membre2, centreDonneesMembre.lire(membre1.getHashInString()));
    }

    @Test
    public void testSupprimerMembre() {
        centreDonneesMembre.creer(membre1);
        centreDonneesMembre.creer(membre2);

        var listeMembres = centreDonneesMembre.getClients();
        assertTrue(listeMembres.size() == 2);
        assertTrue(centreDonneesMembre.lire(membre1.getHashInString()).equals(membre1));

        centreDonneesMembre.supprimer(membre1.getHashInString());
        listeMembres = centreDonneesMembre.getClients();
        assertTrue(listeMembres.size() == 1);
        assertNull(centreDonneesMembre.lire(membre1.getHashInString()));
    }
}
