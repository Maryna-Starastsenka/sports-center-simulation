package Vue;

import Controleur.ControleurClient;
import Modele.TypeClient;

import java.util.Arrays;

public abstract class VueClient<T> extends Vue {

    protected String typeClient = "Client";
    ControleurClient controleurClient;

    protected void enTeteGestionCompte() {
        effacerEcran();
        afficher(String.format("------Gestion d'un compte %s------", typeClient));
    }

    protected void gestionCompte() {
        enTeteGestionCompte();
        afficher(String.format("1. Création d'un compte %s", typeClient));
        afficher(String.format("2. Gestion d'un compte %s existant", typeClient));
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

        switch (reponse) {
            case "1":
                creerClient();
                break;
            case "2":
                gestionCompteExistant();
                break;
            case "3":
                break;
        }
    }

    public abstract TypeClient getTypeClient();

    public void creerClient() {
        String nom;
        String dateNaissanceString;
        String adresseCourriel;
        String numeroTelephone;
        String adresse;

        effacerEcran();
        afficher(String.format("------Formulaire de nouveau compte %s------", typeClient));
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

        controleurClient.creerClient(this, getTypeClient(), nom, dateNaissanceString, adresseCourriel, numeroTelephone, adresse);
    }

    public abstract void gestionCompteExistant();

    public void authentifier() {
        afficher(String.format("Entrez l'identifiant du %s (9 chiffres) :", typeClient));
        String reponse = acquisitionReponse(Verificateurs::identifiantClientValide);
        if (controleurClient.authentifier(this, reponse)) {
            accesAutorise(reponse);
        } else {
            accesRefuse(reponse);
        }
    }

    public abstract void accesAutorise(String idClient);
    public abstract void accesRefuse(String idClient);

    public abstract void confirmerEnregistrement(String id);
}
