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
