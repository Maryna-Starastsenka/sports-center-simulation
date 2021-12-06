package main.vue;

import main.controleur.Helper;
import java.util.Arrays;

/**
 * Classe Vue Application Mobile qui hérite de la classe Vue Plateforme. Permet d'afficher les options du menu
 * disponibles pour la simulation de l'application mobile.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class VueApplicationMobile extends VuePlateforme {
    VueAdministration vueAdministration;
    VueMembre vueMembre;
    VueProfessionnel vueProfessionnel;
    VueService vueService;

    /**
     * Constructeur de VueApplicationMobile
     */
    public VueApplicationMobile() {
        this.vueAdministration = new VueAdministration();
        this.vueMembre = new VueMembre();
        this.vueProfessionnel = new VueProfessionnel();
        this.vueService = new VueService();
    }

    @Override
    public void menuAccueil() {
        effacerEcran();

        afficher("Choisissez le type de compte.");
        afficher("1. Membre");
        afficher("2. Professionnel");
        afficher("3. Sortir de l'application");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

        if (reponse.equals("3")) System.exit(0);

        afficher("Entrez l'adresse courriel pour vous connecter.");
        String adresseCourriel = acquisitionReponse(Helper::courrielValide);

        switch (reponse) {
            case "1" -> vueMembre.seConnecterApp(adresseCourriel);
            case "2" -> vueProfessionnel.seConnecterApp(adresseCourriel);
        }

        retourMenuPrincipal();
    }

    @Override
    public void authentification() {

    }
}
