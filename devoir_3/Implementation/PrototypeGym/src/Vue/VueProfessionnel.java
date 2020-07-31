package Vue;

import Controleur.ControleurClient;
import Modele.Membre;
import Modele.Professionnel;
import Modele.Verificateurs;

import java.util.Arrays;

public class VueProfessionnel extends VueClient<Professionnel> {

    public VueProfessionnel() {
        controleurClient = new ControleurClient();
    }

    @Override
    public void creerClient() {
        String nom;
        String dateNaissanceString;
        String adresseCourriel;
        String numeroTelephone;
        String adresse;

        effacerEcran();
        afficher("------Formulaire de nouveau compte professionnel------");
        afficher("Veuillez entrer le nom :");
        nom = acquisitionReponse();

        afficher("Veuillez entrer la date de naissance (jj-mm-aaaa):");
        dateNaissanceString = acquisitionReponse(Verificateurs::dateValide);

        afficher("Veuillez entrer l'adresse :");
        adresse = acquisitionReponse();

        afficher("Veuillez entrer le numéro de téléphone (XXX-XXX-XXXX):");
        numeroTelephone = acquisitionReponse(Verificateurs::telephoneValide);

        afficher("Veuillez entrer l'adresse courriel (xxx@xxx.xxx) :");
        adresseCourriel = acquisitionReponse(Verificateurs::courrielValide);

        controleurClient.creerClient(this, "3", nom, dateNaissanceString, adresseCourriel, numeroTelephone, adresse);
    }

    @Override
    public void confirmerEnregistrement(String id) {
        afficher("Enregistrement du professionnel numéro : " + id);
    }

    @Override
    public void mettreClientAJour() {

    }

    @Override
    public void authentifier() {
        afficher("Entrez l'identifiant du professionnel (9 chiffres) :");
        String reponse = acquisitionReponse(9);
        controleurClient.authentifier(this, reponse);
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
