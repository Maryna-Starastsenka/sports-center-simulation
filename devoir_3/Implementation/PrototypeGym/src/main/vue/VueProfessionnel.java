package main.vue;

import java.util.Arrays;

import main.controleur.ControleurClient;
import main.controleur.Verificateurs;
import main.modele.Professionnel;
import main.modele.TypeClient;

public class VueProfessionnel extends VueClient<Professionnel> {

    private VueService vueService;
	
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

    @Override
    public void confirmerEnregistrement(String id) {
        afficher("Enregistrement du professionnel numéro : " + id);
    }

    @Override
    public void accesAutorise(String idProfessionnel) {
        afficher(String.format("Le professionnel numéro %s est autorisé à accéder au gym.",
                idProfessionnel));
    }

    @Override
    public void accesRefuse(String idProfessionnel) {
        afficher(String.format("Le professionnel numéro %s n'est pas enregistré.",
                idProfessionnel));
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
