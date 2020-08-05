package main.vue;

import main.controleur.ControleurClient;
import main.modele.Client;
import main.modele.TypeClient;
import java.util.Arrays;

public class VueMembre extends VueClient {

    public VueMembre() {
        clientString = "Membre";
        typeClient = TypeClient.MEMBRE;
        controleurClient = new ControleurClient();
        vueService = new VueService();
    }

    @Override
    public TypeClient getTypeClientPrecis() {
        afficher("Inscrivez-vous un membre qui a payé les frais d'adhésion ?");
        afficher("1. Oui");
        afficher("2. Non");

        clientString = acquisitionReponse(Arrays.asList("1", "2"));

        switch (clientString) {
            case "1":
                return TypeClient.MEMBRE_VALIDE;
            case "2":
                afficher("Le membre est suspendu");
                return TypeClient.MEMBRE_SUSPENDU;
        }
        return TypeClient.MEMBRE_SUSPENDU;
    }

    public void seConnecterApp(String adresseCourriel) {
        String idMembre = ControleurClient.seConnecterApp(typeClient, adresseCourriel);
        if (idMembre != null) {
            afficher("Bienvenue au #GYM");
            Client client = controleurClient.lireClient(idMembre);
            afficher("Nom du membre : " + client.getNom());
            afficher("Numéro du membre : " + idMembre);
            afficher("code QR");
            inscriptionApp(idMembre);
        } else {
            afficher("L'adresse courriel renseignée n'est pas valide");
        }
    }
    
    public void inscriptionApp(String idMembre) {
    	afficher("------Voulez-vous inscrire à une séance?------");
    	afficher("1. Inscription séance");
    	afficher("2. Retour au menu");
    	String reponse = acquisitionReponse(Arrays.asList("1","2"));
    	if(reponse.equals("1")){
    		vueService.inscriptionSeance(idMembre);
    	}
    }
}
