package main.vue;

import java.util.Arrays;

public class VueAgent extends VuePlateforme {
    private final VueAdministration vueAdministration;
    private final VueMembre vueMembre;
    private final VueProfessionnel vueProfessionnel;
    private final VueService vueService;

    public VueAgent() {
        this.vueAdministration = new VueAdministration();
        this.vueMembre = new VueMembre();
        this.vueProfessionnel = new VueProfessionnel();
        this.vueService = new VueService();
    }

    @Override
    public void menuAccueil() {
        effacerEcran();

        afficher("---------------Accueil---------------");
        afficher("1. Demande d'accès au gym");
        afficher("2. Gestion d'un compte");
        afficher("3. Gestion d'un service");
        afficher("4. Inscription à une séance");
        afficher("5. Confirmation de la présence");
        afficher("6. Consultation d'une séance");
        afficher("7. Procédure comptable");
        afficher("8. Sortir de l'application");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8"));

        switch (reponse) {
            case "1" -> authentification();
            case "2" -> gestionCompte();
            case "3" -> vueService.gestionService();
            case "4" -> vueService.inscriptionSeance();
            case "5" -> vueService.confirmationPresence();
            case "6" -> vueService.consultationSeance();
            case "7" -> vueAdministration.procedureComptable();
            case "8" -> System.exit(0);
        }

        retourMenuPrincipal();
    }

    public void gestionCompte() {
        effacerEcran();

        afficher("------Gestion d'un compte------");
        afficher("1. Compte de Membre");
        afficher("2. Compte de Professionnel");
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        switch (reponse) {
            case "1" -> vueMembre.gestionCompte();
            case "2" -> vueProfessionnel.gestionCompte();
        }
    }

    @Override
    public void authentification() {
        effacerEcran();

        afficher("------Demande d'accès au gym------");
        afficher("1. Membre");
        afficher("2. Professionnel");
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        switch (reponse) {
            case "1" -> vueMembre.verifierTypeClient();
            case "2" -> vueProfessionnel.verifierTypeClient();
        }
    }
}
