package main.vue;

import main.controleur.ControleurClient;
import main.modele.Client;
import main.modele.Membre;
import main.modele.TypeClient;

import java.util.Arrays;

public class VueMembre extends VueClient<Membre> {

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
