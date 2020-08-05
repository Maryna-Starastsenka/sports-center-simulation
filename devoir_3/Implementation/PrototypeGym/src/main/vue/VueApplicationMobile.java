package main.vue;

import main.controleur.Verificateurs;
import java.util.Arrays;

public class VueApplicationMobile extends VuePlateforme {
    VueAdministration vueAdministration;
    VueMembre vueMembre;
    VueProfessionnel vueProfessionnel;
    VueService vueService;

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
        String adresseCourriel = acquisitionReponse(Verificateurs::courrielValide);

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
