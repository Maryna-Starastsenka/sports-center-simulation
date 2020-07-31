package Vue;

import Controleur.ControleurClient;
import Controleur.Verificateurs;
import Modele.TypeClient;

import java.util.Arrays;

import static Modele.ChampsClients.*;
import static Controleur.Verificateurs.identifiantClientValide;

public abstract class VueClient<T> extends Vue {

    protected String typeClient = "Client";
    ControleurClient controleurClient;

    protected void enTeteGestionCompte() {
        effacerEcran();
        afficher(String.format("------Gestion d'un compte %s------", typeClient));
    }

    protected void gestionCompte() {
        enTeteGestionCompte();
        afficher(String.format("1. Création d'un nouveau compte %s", typeClient));
        afficher(String.format("2. Gestion d'un compte %s existant", typeClient));
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

    public void gererCompteExistant() {
        enTeteGestionCompte();

        afficher(String.format("Clients actuels (%s): %s", typeClient, controleurClient.getListeClients(this)));
        afficher(String.format("Veuillez entrer le numéro du %s ou 000000000 pour retourner au menu principal.", typeClient));
        String idClient = acquisitionReponse((String s) -> identifiantClientValide(s) && controleurClient.authentifier(this, s),
                "Numéro invalide. Réessayez ou entrez 000000000 pour retourner au menu principal :");

        if (idClient.equals("000000000")) {
            return;
        }

        effacerEcran();

        afficher(String.format("1. Mise à jour d'un compte %s", typeClient));
        afficher(String.format("2. Suppression d'un compte %s", typeClient));
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
            afficher(String.format("Compte du %s %s supprimé.", typeClient, idClient));
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

        afficher(controleurClient.getInformationsClient(this, idClient));

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
                    controleurClient.mettreClientAJour(this, idClient, STATUT, "");
                    afficher(String.format("%s du %s %s modifié.", STATUT.name(), typeClient, idClient));
                    break;
            }
        } else if (this instanceof VueProfessionnel) {
            afficher("6. Retour au menu principal.");

            action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6"));
        }

        switch (action) {
            case "1":
                afficher(String.format("Veuillez entrer le nouveau nom du %s.", typeClient));
                String nom = acquisitionReponse(Verificateurs::nomValide);
                controleurClient.mettreClientAJour(this, idClient, NOM, nom);
                afficher(String.format("%s du %s %s modifié.", NOM.name(), typeClient, idClient));
                break;
            case "2":
                afficher(String.format("Veuillez entrer la nouvelle date de naissance du %s (jj-mm-aaaa).", typeClient));
                String date = acquisitionReponse(Verificateurs::dateValide);
                controleurClient.mettreClientAJour(this, idClient, DATE_NAISSANCE, date);
                afficher(String.format("%s du %s %s modifié.", DATE_NAISSANCE.name(), typeClient, idClient));
                break;
            case "3":
                afficher(String.format("Veuillez entrer la nouvelle adresse courriel du %s (xxx@xxx.xxx).", typeClient));
                String adresseCourriel = acquisitionReponse(Verificateurs::courrielValide);
                controleurClient.mettreClientAJour(this, idClient, ADRESSE_COURRIEL, adresseCourriel);
                afficher(String.format("%s du %s %s modifié.", ADRESSE_COURRIEL.name(), typeClient, idClient));
                break;
            case "4":
                afficher(String.format("Veuillez entrer le nouveau numéro de téléphone du %s (xxx-xxx-xxxx).", typeClient));
                String numero = acquisitionReponse(Verificateurs::telephoneValide);
                controleurClient.mettreClientAJour(this, idClient, TELEPHONE, numero);
                afficher(String.format("%s du %s %s modifié.", TELEPHONE.name(), typeClient, idClient));
                break;
            case "5":
                afficher(String.format("Veuillez entrer la nouvelle adresse du %s.", typeClient));
                String adresse = acquisitionReponse(Verificateurs::adresseValide);
                controleurClient.mettreClientAJour(this, idClient, ADRESSE, adresse);
                afficher(String.format("%s du %s %s modifié.", ADRESSE.name(), typeClient, idClient));
                break;
        }
    }

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
