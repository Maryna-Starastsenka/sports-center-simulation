package Vue;

import Controleur.ControleurClient;
import Modele.Membre;
import Modele.Professionnel;

public class VueProfessionnel extends VueClient<Professionnel> {

    public VueProfessionnel() {
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

    }

    @Override
    public void accesAutorise(Professionnel client) {
        afficher(String.format("Le professionnel %s (%s) est autorisé à accéder au gym.",
                client.getNom(), client.getHashInString()));
    }

    @Override
    public void accesRefuse(String idClient) {
        afficher(String.format("Le professionnel numéro %s n'est pas enregistré.",
                idClient));
    }
}
