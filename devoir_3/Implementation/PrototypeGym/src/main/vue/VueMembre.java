package main.vue;

import main.controleur.ControleurClient;
import main.modele.Client;
import main.modele.TypeClient;
import java.util.Arrays;

/**
 * Classe Vue Membre hérite la classe Vue Client. Permet d'afficher les options du menu de gestion d'un membre.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class VueMembre extends VueClient {

    /**
     * Constructeur de VueMembre
     */
    public VueMembre() {
        clientString = "Membre";
        typeClient = TypeClient.MEMBRE;
        controleurClient = new ControleurClient();
        vueService = new VueService();
    }

    /**
     * Retourne le type de client
     * @return type de client "membre valide" s'il a payé les frais d'adhésion. sinon, type de client "membre suspendu"
     */
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

    /**
     * Fait un appel à la méthode qui affiche le menu d'inscription aux séance si le membre confirme son choix
     * @param idMembre numéro unique du membre
     */
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
