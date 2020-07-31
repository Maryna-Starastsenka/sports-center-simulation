package Vue;

import Controleur.ControleurClient;
import Modele.Professionnel;

public class VueProfessionnel extends VueClient<Professionnel> {

    public VueProfessionnel() {
        typeClient = "Professionnel";
        controleurClient = new ControleurClient();
    }

    @Override
    public void enTeteGestionCompte() {
        effacerEcran();
        afficher("------Gestion d'un compte professionnel------");
    }

    @Override
    public void gestionCompte() {
        enTeteGestionCompte();

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
    public void gestionCompteExistant() {

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
