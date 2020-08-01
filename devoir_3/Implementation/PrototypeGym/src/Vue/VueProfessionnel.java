package Vue;

import Controleur.ControleurClient;
import Modele.Professionnel;
import Modele.TypeClient;

public class VueProfessionnel extends VueClient<Professionnel> {

    public VueProfessionnel() {
        clientString = "Professionnel";
        typeClient = TypeClient.PROFESSIONNEL;
        controleurClient = new ControleurClient();
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


}
