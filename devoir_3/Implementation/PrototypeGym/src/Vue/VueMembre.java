package Vue;

import Controleur.ControleurClient;
import Modele.Membre;

public class VueMembre extends VueClient<Membre> {

    public VueMembre() {
        controleurClient = new ControleurClient();
    }

    @Override
    public void creerClient() {

    }

    @Override
    public void mettreClientAJour() {

    }

    @Override
    public void authentifier() {
        afficher("Entrez l'identifiant du membre (9 chiffres) :");
        String reponse = acquisitionReponse(9);
        controleurClient.authentifier(this, reponse);
    }

    @Override
    public void accesAutorise(Membre client) {
        afficher(String.format("Le membre %s (%s) est autorisé à accéder au gym.",
                client.getNom(), client.getHashInString()));
    }

    @Override
    public void accesRefuse(String idClient) {
        afficher(String.format("Le membre numéro %s n'est pas autorisé à accéder au gym.",
                idClient));
    }

}
