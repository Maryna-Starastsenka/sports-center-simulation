package main.vue;

import java.util.Arrays;
import main.controleur.ControleurClient;
import main.modele.TypeClient;

/**
 * Classe Vue Professionnel hérite la classe Vue Client. Permet d'afficher les options du menu de gestion d'un professionnel.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class VueProfessionnel extends VueClient {
	
    public VueProfessionnel() {
        clientString = "Professionnel";
        typeClient = TypeClient.PROFESSIONNEL;
        controleurClient = new ControleurClient();
        vueService = new VueService();
    }

    @Override
    public TypeClient getTypeClientPrecis() {
        return TypeClient.PROFESSIONNEL;
    }

    public void seConnecterApp(String adresseCourriel) {
        String idProfessionnel = ControleurClient.seConnecterApp(typeClient, adresseCourriel);
        if (idProfessionnel != null) {
            afficher("Bienvenue au #GYM");
            String idSeance = vueService.afficherSeance(idProfessionnel);
            confirmerPresences(idSeance);
        } else {
            afficher("L'adresse courriel renseignée n'est pas valide");
        }
    }

    /**
     * Affiche le menu de confirmation de présence effectuée par un professionnel
     * @param idSeance code de la séance
     */
    public void confirmerPresences(String idSeance) {
    	afficher("------Voulez-vous confirmer une présence?------");
        afficher("1. Confirmer présence");
        afficher("2. Retour au menu");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        while(reponse.equals("1")){
        	vueService.confirmationPresence(idSeance);
        	afficher("------Voulez-vous confirmer une autre présence?------");
            afficher("1. Confirmer présence");
            afficher("2. Retour au menu");
            reponse = acquisitionReponse(Arrays.asList("1","2"));
        }
    }
}
