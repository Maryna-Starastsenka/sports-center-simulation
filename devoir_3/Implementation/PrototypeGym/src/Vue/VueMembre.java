package Vue;

import Controleur.ControleurClient;
import Modele.Membre;
import Modele.TypeClient;

import java.util.Arrays;

public class VueMembre extends VueClient<Membre> {

    public VueMembre() {
        typeClient = "Membre";
        controleurClient = new ControleurClient();
    }

    @Override
    public void gestionCompteExistant() {
        enTeteGestionCompte();
        afficher("1. Mise à jour d'un compte membre");
        afficher("2. Suppression d'un compte membre");
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

//        switch (reponse) {
//            case "1":
//                afficherTousLesMembres();
//				afficher("Veuillez entrer le numéro du membre ou entrer \"retour\" pour retourner au menu principal.");
//				String idMembre = acquisitionReponse(Verificateurs::identifiantClientValide);
//				// TODO traiter le cas où l'usager écrit "retour"
////				while (!validerMembre(idMembre)&&!idMembre.equals("retour")) {
////					afficher("Numéro de membre invalide. Réessayez.");
////					idMembre = Vue.getTexteConsole();
////				}
////				if(!idMembre.equals("retour")) {
//                controleurClient.
//					Membre membre = centreDonneesClient.getMembre(idMembre);
//					afficher(membre.toString());
//					afficher("Veuillez choisir l'action");
//					afficher("1. Modifier.");
//					afficher("2. Supprimer.");
//					String action = Vue.getTexteConsole();
//					gererMembreExistant(action, membre, idMembre);
//                //controleurClient.mettreClientAJour();
//                break;
//            case "2":
//                //controleurClient.supprimerClient();
//                break;
//            case "3":
//                break;
//        }


//        switch (reponse) {
//            case "1":
//                afficherTousLesMembres();
//				afficher("Veuillez entrer le numéro du membre ou entrer \"retour\" pour retourner au menu principal.");
//				String idMembre = acquisitionReponse(Verificateurs::identifiantClientValide);
//				// TODO traiter le cas où l'usager écrit "retour"
////				while (!validerMembre(idMembre)&&!idMembre.equals("retour")) {
////					afficher("Numéro de membre invalide. Réessayez.");
////					idMembre = Vue.getTexteConsole();
////				}
////				if(!idMembre.equals("retour")) {
//                controleurClient.
//					Membre membre = centreDonneesClient.getMembre(idMembre);
//					afficher(membre.toString());
//					afficher("Veuillez choisir l'action");
//					afficher("1. Modifier.");
//					afficher("2. Supprimer.");
//					String action = Vue.getTexteConsole();
//					gererMembreExistant(action, membre, idMembre);
//                //controleurClient.mettreClientAJour();
//                break;
//            case "2":
//                //controleurClient.supprimerClient();
//                break;
//            case "3":
//                break;
//        }

    }

    @Override
    public TypeClient getTypeClient() {
        afficher("Inscrivez-vous un membre qui a payé les frais d'adhésion ?");
        afficher("1. Oui");
        afficher("2. Non");

        typeClient = acquisitionReponse(Arrays.asList("1", "2"));

        switch (typeClient) {
            case "1":
                return TypeClient.MEMBRE_VALIDE;
            case "2":
                afficher("Le membre est suspendu");
                return TypeClient.MEMBRE_SUSPENDU;
        }
        return TypeClient.MEMBRE_SUSPENDU;
    }

    @Override
    public void confirmerEnregistrement(String id) {
        afficher("Enregistrement du membre numéro : " + id);
    }

    @Override
    public void accesAutorise(String idMembre) {
        afficher(String.format("Le membre numéro %s est autorisé à accéder au gym.",
                idMembre));
    }

    @Override
    public void accesRefuse(String idMembre) {
        afficher(String.format("Le membre numéro %s n'est pas autorisé à accéder au gym.",
                idMembre));
    }



}
