package Vue;

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
        afficher("4. Modele.Inscription à une séance");
        afficher("5. Confirmation de la présence");
        afficher("6. Consultation d'une séance");
        afficher("7. Procédure comptable");
        afficher("8. Mettre à jour statut membres");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8"));

        switch (reponse) {
            case "1":
                authentification();
                break;
            case "2":
                gestionCompte();
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
        }
    }

    public void gestionCompte() {
        effacerEcran();

        afficher("------Gestion d'un compte------");
        afficher("1. Compte de membre");
        afficher("2. Compte de professionnel");
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

//        afficher("1. Création d'un compte membre");
//        afficher("2. Création d'un compte professionnel");
//        afficher("3. Gestion d'un compte membre existant");
//        afficher("4. Gestion d'un compte professionnel existant");
//        afficher("5. Retour au menu principal");
//
//        String reponse = acquisitionReponse(Arrays.asList("1","2","3","4","5"));
//
//        switch (reponse) {
//            case "1":
//                vueMembre.creerClient();
//                break;
//            case "2":
//                vueProfessionnel.creerClient();
//                break;
//            case "3":
//                vueMembre.mettreClientAJour();
//                break;
//            case "4":
//                vueProfessionnel.mettreClientAJour();
//                break;
//            case "5":
//                break;
//        }

        retourMenuPrincipal();
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
                vueMembre.authentifier();
                break;
            case "2":
                vueProfessionnel.authentifier();
                break;
            case "3":
                break;
        }

        retourMenuPrincipal();
    }


//    private void gererMenuPrincipal(String entreePrincipale) {
//        String membreId;
//        String idProfessionnel;
//        switch (entreePrincipale) {
//            case "1":
//                controleurClient.demanderAcces();
//                break;
//            case "2":
//                controleurClient.gestionCompte();
//                break;
//            case "3":
//                Vue.afficher("--------Gestion d'un service-------");
//                controleurClient.afficherTousLesProfessionnels();
//                Vue.afficher("Veuillez entrer le numéro de professionnel ou \"retour\" pour retourner au menu principal");
//                do {
//                    idProfessionnel = Vue.getTexteConsole();
//                } while (!controleurClient.validerProfessionnel(idProfessionnel)&&!idProfessionnel.equals("retour"));
//                if(!idProfessionnel.equals("retour"))
//                    controleurService.gererService(idProfessionnel);
//                break;
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
