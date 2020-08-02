package main.vue;

import java.util.Arrays;

public class VueAgent extends VuePlateforme {
    VueAdministration vueAdministration;
    VueMembre vueMembre;
    VueProfessionnel vueProfessionnel;
    VueService vueService;

    public VueAgent() {
        this.vueAdministration = new VueAdministration();
        this.vueMembre = new VueMembre();
        this.vueProfessionnel = new VueProfessionnel();
        this.vueService = new VueService();
    }

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
        afficher("8. Mettre à jour statut membres");
        afficher("9. Sortir de l'application");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8","9"));

        switch (reponse) {
            case "1":
                authentification();
                break;
            case "2":
                gestionCompte();
                break;
            case "3":
                vueService.gestionService();
                break;
            case "4":
                vueService.inscriptionSeance();
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                System.exit(0);
                break;
        }

        retourMenuPrincipal();
    }

    public void gestionCompte() {
        effacerEcran();

        afficher("------Gestion d'un compte------");
        afficher("1. Compte de Membre");
        afficher("2. Compte de Professionnel");
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

        switch (reponse) {
            case "1":
                vueMembre.gestionCompte();
                break;
            case "2":
                vueProfessionnel.gestionCompte();
                break;
            case "3":
                break;
        }
    }

    @Override
    public void authentification() {
        effacerEcran();

        afficher("------Demande d'accès au gym------");
        afficher("1. Membre");
        afficher("2. Professionnel");
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

        switch (reponse) {
            case "1":
                vueMembre.verifierTypeCleint();
                break;
            case "2":
                vueProfessionnel.verifierTypeCleint();
                break;
            case "3":
                break;
        }
    }


//            case "4":
//                Vue.afficher("---Modele.Inscription à une séance---");
//                controleurClient.afficherTousLesMembres();
//
//                Vue.afficher("Veuillez entrer le numéro du membre :");
//                membreId = Vue.getTexteConsole();
//                if (!controleurClient.validerMembre(membreId)) {
//                    Vue.afficher("Modele.Membre inconnu. Retour au menu principal.");
//                    break;
//                } else if (!controleurClient.membrePasSuspendu(membreId)) {
//                    Vue.afficher("Modele.Membre suspendu. Retour au menu principal.");
//                    break;
//                }
//                controleurService.inscriptionSeance(membreId);
//                break;
//            case "5":
//                Vue.afficher("---Confirmation de la présence---");
//
//                controleurClient.afficherTousLesMembres();
//                Vue.afficher("Veuillez entrer le numéro du membre :");
//                membreId = Vue.getTexteConsole();
//
//                if (!controleurClient.validerMembre(membreId)) {
//                    Vue.afficher("Modele.Membre inconnu. Retour au menu principal.");
//                    break;
//                } else if (!controleurClient.membrePasSuspendu(membreId)) {
//                    Vue.afficher("Modele.Membre suspendu. Retour au menu principal.");
//                    break;
//                }
//                controleurService.confirmerPresence(membreId);
//                break;
//            case "6":
//                Vue.afficher("---Consultation d'une séance---");
//                controleurClient.afficherTousLesProfessionnels();
//                Vue.afficher("Veuillez entrer le numéro du professionnel.");
//                idProfessionnel = Vue.getTexteConsole();
//
//                if (controleurClient.validerProfessionnel(idProfessionnel)) {
//                    controleurService.consultationInscription(idProfessionnel);
//                } else {
//                    Vue.afficher("Numéro du professionnel incorrect.");
//                }
//                break;
//            case "7":
//                HashMap<String, Professionnel> hashMapProfessionnel = controleurClient.getHashMapProfessionnel();
//                controleurService.procedureComptable(hashMapProfessionnel);
//                break;
//            case "8":
//                controleurClient.modifierStatutMembres();
//                break;
//            default:
//                break;
//        }
//        resetEnFinDeTransaction();
//    }
}
