package Vue;

import Controleur.ControleurClient;
import Controleur.Verificateurs;
import Modele.TypeClient;

import java.util.Arrays;

import static Modele.Champs.*;
import static Controleur.Verificateurs.identifiantClientValide;

public abstract class VueClient<T> extends Vue {

    protected String clientString = "Client";
    protected TypeClient typeClient = TypeClient.INDEFINI;
    ControleurClient controleurClient;

    protected void enTeteGestionCompte() {
        effacerEcran();
        afficher(String.format("------Gestion d'un compte %s------", clientString));
    }

    protected void gestionCompte() {
        enTeteGestionCompte();
        afficher(String.format("1. Création d'un nouveau compte %s", clientString));
        afficher(String.format("2. Gestion d'un compte %s existant", clientString));
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

        switch (reponse) {
            case "1":
                creerClient();
                break;
            case "2":
                gererCompteExistant();
                break;
            case "3":
                break;
        }
    }

    public abstract TypeClient getTypeClientPrecis();

    public void creerClient() {
        String nom;
        String dateNaissanceString;
        String adresseCourriel;
        String numeroTelephone;
        String adresse;

        effacerEcran();
        afficher(String.format("------Formulaire de nouveau compte %s------", clientString));
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

        controleurClient.creerClient(typeClient, getTypeClientPrecis(), nom, dateNaissanceString, adresseCourriel, numeroTelephone, adresse);
    }

    public void gererCompteExistant() {
        enTeteGestionCompte();

        afficher(String.format("Clients actuels (%s): %s", clientString, controleurClient.getListeClients(this)));
        afficher(String.format("Veuillez entrer le numéro du %s ou 0 pour retourner au menu principal.", clientString));
        String idClient = acquisitionReponse((String s) -> identifiantClientValide(s) && controleurClient.authentifier(typeClient, s),
                "Numéro invalide. Réessayez ou entrez 0 pour retourner au menu principal :");

        if (idClient.equals("0")) {
            return;
        }

        effacerEcran();

        afficher(String.format("1. Mise à jour d'un compte %s", clientString));
        afficher(String.format("2. Suppression d'un compte %s", clientString));
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));


        switch (reponse) {
            case "1":
                mettreAJourClient(idClient);
                break;
            case "2":
                supprimerClient(idClient);
                break;
            case "3":
                break;
        }
    }

    private void supprimerClient(String idClient) {
        afficher("1. Valider suppression.");
        afficher("2. Retour au menu principal.");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        if (reponse.equals("1")) {
            controleurClient.supprimerClient(this, idClient);
            afficher(String.format("Compte du %s %s supprimé.", clientString, idClient));
        }
    }

    public void mettreAJourClient(String idClient) {
        enTeteGestionCompte();

        // TODO traiter le cas où l'usager écrit "retour"
//				while (!validerMembre(idMembre)&&!idMembre.equals("retour")) {
//					afficher("Numéro de membre invalide. Réessayez.");
//					idMembre = Vue.getTexteConsole();
//				}
//				if(!idMembre.equals("retour")) {

        afficher(controleurClient.getInformationsClient(typeClient, idClient));

        String action = null;

        afficher("Veuillez choisir l'action");
        afficher("1. Modifier le nom.");
        afficher("2. Modifier la date de naissance.");
        afficher("3. Modifier l'adresse courriel.");
		afficher("4. Modifier le numéro de téléphone.");
        afficher("5. Modifier l'adresse.");

        if (this instanceof VueMembre) {
            afficher("6. Modifier le statut du Membre.");
            afficher("7. Retour au menu principal.");

            action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7"));

            switch (action) {
                case "6":
                    controleurClient.mettreClientAJour(this, idClient, STATUT_MEMBRE, "");
                    afficher(String.format("%s du %s %s modifié.", STATUT_MEMBRE.name(), clientString, idClient));
                    break;
            }
        } else if (this instanceof VueProfessionnel) {
            afficher("6. Retour au menu principal.");

            action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6"));
        }

        switch (action) {
            case "1":
                afficher(String.format("Veuillez entrer le nouveau nom du %s.", clientString));
                String nom = acquisitionReponse(Verificateurs::nomValide);
                controleurClient.mettreClientAJour(this, idClient, NOM_CLIENT, nom);
                afficher(String.format("%s du %s %s modifié.", NOM_CLIENT.name(), clientString, idClient));
                break;
            case "2":
                afficher(String.format("Veuillez entrer la nouvelle date de naissance du %s (jj-mm-aaaa).", clientString));
                String date = acquisitionReponse(Verificateurs::dateValide);
                controleurClient.mettreClientAJour(this, idClient, DATE_NAISSANCE_CLIENT, date);
                afficher(String.format("%s du %s %s modifié.", DATE_NAISSANCE_CLIENT.name(), clientString, idClient));
                break;
            case "3":
                afficher(String.format("Veuillez entrer la nouvelle adresse courriel du %s (xxx@xxx.xxx).", clientString));
                String adresseCourriel = acquisitionReponse(Verificateurs::courrielValide);
                controleurClient.mettreClientAJour(this, idClient, ADRESSE_COURRIEL_CLIENT, adresseCourriel);
                afficher(String.format("%s du %s %s modifié.", ADRESSE_COURRIEL_CLIENT.name(), clientString, idClient));
                break;
            case "4":
                afficher(String.format("Veuillez entrer le nouveau numéro de téléphone du %s (xxx-xxx-xxxx).", clientString));
                String numero = acquisitionReponse(Verificateurs::telephoneValide);
                controleurClient.mettreClientAJour(this, idClient, TELEPHONE_CLIENT, numero);
                afficher(String.format("%s du %s %s modifié.", TELEPHONE_CLIENT.name(), clientString, idClient));
                break;
            case "5":
                afficher(String.format("Veuillez entrer la nouvelle adresse du %s.", clientString));
                String adresse = acquisitionReponse(Verificateurs::adresseValide);
                controleurClient.mettreClientAJour(this, idClient, ADRESSE_CLIENT, adresse);
                afficher(String.format("%s du %s %s modifié.", ADRESSE_CLIENT.name(), clientString, idClient));
                break;
        }
    }

    public void authentifier() {
        afficher(String.format("Entrez l'identifiant du %s (9 chiffres) :", clientString));
        String reponse = acquisitionReponse(Verificateurs::identifiantClientValide);
        if (controleurClient.authentifier(typeClient, reponse)) {
            accesAutorise(reponse);
        } else {
            accesRefuse(reponse);
        }
    }

    public abstract void accesAutorise(String idClient);
    public abstract void accesRefuse(String idClient);

    public abstract void confirmerEnregistrement(String id);
}
