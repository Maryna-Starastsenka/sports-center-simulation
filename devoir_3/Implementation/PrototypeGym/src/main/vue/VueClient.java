package main.vue;

import main.controleur.ControleurClient;
import main.controleur.Verificateurs;
import main.modele.TypeClient;

import java.util.Arrays;

import static main.modele.Champs.*;
import static main.controleur.Verificateurs.identifiantClientValide;

public abstract class VueClient<T> extends Vue {

    protected String clientString = "Client";
    protected TypeClient typeClient = TypeClient.CLIENT_INVALIDE;
    ControleurClient controleurClient;
    protected VueService vueService;

    protected void enTeteGestionCompte() {
        effacerEcran();
        afficher(String.format("------Gestion d'un compte %s------", clientString));
    }

    protected void gestionCompte() {
        enTeteGestionCompte();
        afficher(String.format("1. Création d'un nouveau compte %s", clientString));
        afficher(String.format("2. Gestion d'un compte %s existant", clientString));
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        switch (reponse) {
            case "1":
                creerClient();
                break;
            case "2":
                gererCompteExistant();
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
        String ville;
        String codePostal;
        String province;

        effacerEcran();
        afficher(String.format("------Formulaire de nouveau compte %s------", clientString));
        afficher("Veuillez entrer le nom :");
        nom = acquisitionReponse();

        afficher("Veuillez entrer la date de naissance (jj-mm-aaaa):");
        dateNaissanceString = acquisitionReponse(Verificateurs::dateValide);

        afficher("Veuillez entrer l'adresse :");
        adresse = acquisitionReponse();
        
        afficher("Veuillez entrer la ville :");
        ville = acquisitionReponse();
        
        afficher("Veuillez entrer la province :");
        province = acquisitionReponse();
        
        afficher("Veuillez entrer le code postal (XXXXXX) :");
        codePostal = acquisitionReponse(Verificateurs::codePostalValide).toUpperCase();

        afficher("Veuillez entrer le numéro de téléphone (XXX-XXX-XXXX):");
        numeroTelephone = acquisitionReponse(Verificateurs::telephoneValide);

        afficher("Veuillez entrer l'adresse courriel (xxx@xxx.xxx) :");
        adresseCourriel = acquisitionReponse(Verificateurs::courrielValide);
        
        TypeClient typeClient = getTypeClientPrecis();

        controleurClient.creerClient(typeClient, nom, dateNaissanceString, adresseCourriel, numeroTelephone, adresse, ville, province, codePostal);
        switch(typeClient) {
        case MEMBRE_VALIDE :
        	afficher("Membre créé : " + controleurClient.getIdDepuisAdresse(adresseCourriel));
        	break;
        case MEMBRE_SUSPENDU : 
        	afficher("Membre suspendu créé");
        	break;
        case PROFESSIONNEL : 
        	afficher("Professionnel créé : " + controleurClient.getIdDepuisAdresse(adresseCourriel));
        	break;
        default : 
        	break;        		
        }
    }

    public void gererCompteExistant() {
        enTeteGestionCompte();

        afficher(String.format("Clients actuels (%s): %s", clientString, controleurClient.getListeClients(typeClient)));
        afficher(String.format("Veuillez entrer le numéro du %s ou 0 pour retourner au menu principal.", clientString));
        String idClient = acquisitionReponse((String s) -> identifiantClientValide(s) && controleurClient.authentifier(typeClient, s),
                "Numéro invalide. Réessayez ou entrez 0 pour retourner au menu principal :");

        if (idClient.equals("0")) {
            return;
        }

        effacerEcran();

        afficher(String.format("1. Mise à jour d'un compte %s", clientString));
        afficher(String.format("2. Suppression d'un compte %s", clientString));
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));


        switch (reponse) {
            case "1":
                mettreAJourClient(idClient);
                break;
            case "2":
                supprimerClient(idClient);
                break;
        }
    }

    private void supprimerClient(String idClient) {
        afficher("1. Valider suppression.");
        afficher("0. Retour au menu principal.");

        String reponse = acquisitionReponse(Arrays.asList("1"));

        if (reponse.equals("1")) {
            controleurClient.supprimerClient(typeClient, idClient);
            afficher(String.format("Compte du %s %s supprimé.", clientString, idClient));
        }
    }

    public void mettreAJourClient(String idClient) {
    	enTeteGestionCompte();


    	afficher(controleurClient.getInformationsClient(typeClient, idClient));

    	String action = null;

    	afficher("Veuillez choisir l'action");
    	afficher("1. Modifier le nom.");
    	afficher("2. Modifier la date de naissance.");
    	afficher("3. Modifier l'adresse courriel.");
    	afficher("4. Modifier le numéro de téléphone.");
    	afficher("5. Modifier l'adresse.");
    	afficher("6. Modifier la ville.");
    	afficher("7. Modifier la province");
    	afficher("8. Modifier le code postal");

    	if (this instanceof VueMembre) {
    		afficher("9. Modifier le statut du Membre.");
    		afficher("0. Retour au menu principal.");

    		action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8","9","0"));
    		
    		if(action.equals("9")){
    			controleurClient.mettreClientAJour(typeClient, idClient, STATUT_MEMBRE, "");
    			afficher(String.format("%s du %s %s modifié.", STATUT_MEMBRE.name(), clientString, idClient));
    		}
    	} else if (this instanceof VueProfessionnel) {
    		afficher("0. Retour au menu principal.");
    		action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8"));
    	}

    	switch (action) {
    	case "1":
    		afficher(String.format("Veuillez entrer le nouveau nom du %s.", clientString));
    		String nom = acquisitionReponse(Verificateurs::nomValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, NOM_CLIENT, nom);
    		afficher(String.format("%s du %s %s modifié.", NOM_CLIENT.name(), clientString, idClient));
    		break;
    	case "2":
    		afficher(String.format("Veuillez entrer la nouvelle date de naissance du %s (jj-mm-aaaa).", clientString));
    		String date = acquisitionReponse(Verificateurs::dateValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, DATE_NAISSANCE_CLIENT, date);
    		afficher(String.format("%s du %s %s modifié.", DATE_NAISSANCE_CLIENT.name(), clientString, idClient));
    		break;
    	case "3":
    		afficher(String.format("Veuillez entrer la nouvelle adresse courriel du %s (xxx@xxx.xxx).", clientString));
    		String adresseCourriel = acquisitionReponse(Verificateurs::courrielValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, ADRESSE_COURRIEL_CLIENT, adresseCourriel);
    		afficher(String.format("%s du %s %s modifié.", ADRESSE_COURRIEL_CLIENT.name(), clientString, idClient));
    		break;
    	case "4":
    		afficher(String.format("Veuillez entrer le nouveau numéro de téléphone du %s (xxx-xxx-xxxx).", clientString));
    		String numero = acquisitionReponse(Verificateurs::telephoneValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, TELEPHONE_CLIENT, numero);
    		afficher(String.format("%s du %s %s modifié.", TELEPHONE_CLIENT.name(), clientString, idClient));
    		break;
    	case "5":
    		afficher(String.format("Veuillez entrer la nouvelle adresse du %s.", clientString));
    		String adresse = acquisitionReponse(Verificateurs::adresseValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, ADRESSE_CLIENT, adresse);
    		afficher(String.format("%s du %s %s modifié.", ADRESSE_CLIENT.name(), clientString, idClient));
    		break;
    	case "6":
    		afficher(String.format("Veuillez entrer la nouvelle ville du %s.", clientString));
    		String ville = acquisitionReponse(Verificateurs::villeValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, VILLE_CLIENT, ville);
    		afficher(String.format("Ville du %s %s modifié.", clientString, idClient));
    		break;
    	case "7":
    		afficher(String.format("Veuillez entrer la nouvelle province du %s.", clientString));
    		String province = acquisitionReponse(Verificateurs::provinceValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, PROVINCE_CLIENT, province);
    		afficher(String.format("Province du %s %s modifié.", clientString, idClient));
    		break;
    	case "8":
    		afficher(String.format("Veuillez entrer le nouveau code postal du %s.", clientString));
    		String codePostal = acquisitionReponse(Verificateurs::codePostalValide);
    		controleurClient.mettreClientAJour(typeClient, idClient, CODEPOSTAL_CLIENT, codePostal);
    		afficher(String.format("Code postal du %s %s modifié.", clientString, idClient));
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

    public void seConnecterApp(String adresseCourriel) {
        String idClient = ControleurClient.seConnecterApp(typeClient, adresseCourriel);
        if (idClient != null) {
            afficher("Bienvenue au #GYM");
        } else {
            afficher("L'adresse courriel renseignée n'est pas valide");
        }
    }

    public void verifierTypeClient() {
        afficher(String.format("Entrez l'identifiant du %s (9 chiffres) :", clientString));
        String reponse = acquisitionReponse(Verificateurs::identifiantClientValide);
        String typeClientVerifie = String.valueOf(controleurClient.verifierTypeClient(typeClient, reponse));

        switch (typeClientVerifie) {
            case "MEMBRE_VALIDE":
            case "PROFESSIONNEL_VALIDE":
                afficher("Accès validé");
                break;
            case "MEMBRE_SUSPENDU" :
                afficher("Membre suspendu");
                break;
            case "CLIENT_INVALIDE":
                afficher("Numéro invalide");
                break;
        }
    }


    public abstract void accesAutorise(String idClient);
    public abstract void accesRefuse(String idClient);

    public abstract void confirmerEnregistrement(String id);
}
