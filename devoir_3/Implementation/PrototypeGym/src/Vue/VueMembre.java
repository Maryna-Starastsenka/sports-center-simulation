package Vue;

import Controleur.ControleurClient;
import Modele.Membre;
import Modele.Verificateurs;

import java.util.Arrays;

public class VueMembre extends VueClient<Membre> {

    public VueMembre() {
        controleurClient = new ControleurClient();
    }

    @Override
    public void creerClient() {
        String typeClient;
        String nom;
        String dateNaissanceString;
        String adresseCourriel;
        String numeroTelephone;
        String adresse;

        effacerEcran();
        afficher("------Formulaire de nouveau compte membre------");
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

        afficher("Inscrivez-vous un membre qui a payé les frais d'adhésion ?");
        afficher("1. Oui");
        afficher("2. Non");

        typeClient = acquisitionReponse(Arrays.asList("1", "2"));

        controleurClient.creerClient(this, typeClient, nom, dateNaissanceString, adresseCourriel, numeroTelephone, adresse);

        if (typeClient.equals("2")) {
            afficher("Le membre est suspendu");
        }
    }

    @Override
    public void confirmerEnregistrement(String id) {
        afficher("Enregistrement du membre numéro : " + id);
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
