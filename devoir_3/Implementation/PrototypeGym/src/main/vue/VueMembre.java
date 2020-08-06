package main.vue;

import main.controleur.ControleurClient;
import main.modele.Client;
import main.modele.TypeClient;
import java.util.Arrays;

/**
 * Classe Vue Membre qui hérite de la classe Vue Client. Permet d'afficher les options du menu de gestion d'un membre.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class VueMembre extends VueClient {

    /**
     * Constructeur de Vue Membre qui définit le type de client à Membre
     */
    public VueMembre() {
        clientString = "Membre";
        typeClient = TypeClient.MEMBRE;
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

    /**
     * Accueillir le client et lui permettre de s'authentifier avec son courriel pour entrer dans l'app
     * @param adresseCourriel adresse courriel unique du client (membre ou professionnel)
     */
    public void seConnecterApp(String adresseCourriel) {
        String idMembre = ControleurClient.seConnecterApp(typeClient, adresseCourriel);
        if (idMembre != null) {
            afficher("Bienvenue au #GYM");
            Client client = controleurClient.lireMembre(idMembre);
            afficher("Nom du membre : " + client.getNom());
            afficher("Numéro du membre : " + idMembre);
            afficher("code QR");
            inscriptionApp(idMembre);
        } else {
            afficher("L'adresse courriel renseignée n'est pas valide");
        }
    }

    /**
     * Fait un appel à la méthode qui affiche le menu d'inscription aux séances si le membre confirme son choix
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
