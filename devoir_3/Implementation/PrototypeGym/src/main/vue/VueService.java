package main.vue;

import main.controleur.ControleurClient;
import main.controleur.ControleurService;
import main.controleur.Helper;
import main.modele.TypeClient;
import java.util.Arrays;
import java.util.Collections;

import static main.controleur.Helper.*;
import static main.modele.Champs.*;

public class VueService extends Vue {

    ControleurService controleurService;

    public VueService() {
        this.controleurService = new ControleurService();
    }

    protected void enTeteGestionService() {
        effacerEcran();

        afficher("------Gestion d'un service------");
    }

    protected void gestionService() {
        enTeteGestionService();

        afficher("Entrez l'identifiant du Professionnel (9 chiffres) :");
        String idProfessionnel = acquisitionReponse((String s) ->
                        identifiantClientValide(s) && ControleurClient.authentifier(TypeClient.PROFESSIONNEL, s),
                "Numéro incorrect. Réessayez ou entrez 0 pour retourner au menu principal :");

        if (idProfessionnel.equals("0")) return;

        afficher("1. Création d'un nouveau service");
        afficher("2. Gestion d'un service existant");
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        switch (reponse) {
            case "1" -> creerService(idProfessionnel);
            case "2" -> gererServiceExistant(idProfessionnel);
        }
    }

    private void gererServiceExistant(String idProfessionnel) {
        enTeteGestionService();

        afficher("Services et séances du Professionnel : \n" + controleurService.obtenirToutesLesSeancesDuProfessionnelEnString(idProfessionnel));
        afficher("Veuillez choisir le service ou 0 pour retourner au menu principal.");
        String idSeance = acquisitionReponse(controleurService.obtenirListeSeancesDuProfessionnel(idProfessionnel));
        if (idSeance.equals("0")) {
            return;
        }

        effacerEcran();

        afficher("1. Mise à jour d'un service");
        afficher("2. Suppression d'un service");
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        switch (reponse) {
            case "1" -> mettreAJourService(idSeance);
            case "2" -> supprimerService(idSeance);
        }
    }

    private void mettreAJourService(String idSeance) {
        enTeteGestionService();

        afficher(controleurService.getInformationsService(idSeance));

        String action;

        afficher("Veuillez choisir l'action");
        afficher("1. Modifier le nom de service.");
        afficher("2. Modifier la date de début de service.");
        afficher("3. Modifier la date de fin de service.");
        afficher("4. Modifier l'heure de service.");
        afficher("5. Modifier la récurrence hebdomadaire.");
        afficher("6. Modifier la capacité maximale.");
        afficher("7. Modifier les frais de service.");
        afficher("8. Modifier le commentaire.");
        afficher("0. Retour au menu principal.");

        action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8"));

        switch (action) {
            case "1" -> {
                Vue.afficher("Veuillez entrer le nouveau nom du service :");
                String nomService = acquisitionReponse();
                controleurService.mettreServiceAJour(idSeance, NOM_SERVICE, nomService);
                afficher(String.format("%s du service %s modifié.", NOM_SERVICE.name(), idSeance));
            }
            case "2" -> {
                afficher("Veuillez entrer la nouvelle date de début du service (jj-mm-aaaa) :");
                String dateDebutService = acquisitionReponse(Helper::dateValide);
                controleurService.mettreServiceAJour(idSeance, DATE_DEBUT_SERVICE, dateDebutService);
                afficher(String.format("%s du service %s modifié.", DATE_DEBUT_SERVICE.name(), idSeance));
            }
            case "3" -> {
                afficher("Veuillez entrer la nouvelle date de fin du service (jj-mm-aaaa) :");
                String dateFinService = acquisitionReponse(Helper::dateValide);
                controleurService.mettreServiceAJour(idSeance, DATE_FIN_SERVICE, dateFinService);
                afficher(String.format("%s du service %s modifié.", DATE_FIN_SERVICE.name(), idSeance));
            }
            case "4" -> {
                afficher("Veuillez entrer la nouvelle heure du service (hh:mm) :");
                String heureService = acquisitionReponse(Helper::horaireValide);
                controleurService.mettreServiceAJour(idSeance, HEURE_SERVICE, heureService);
                afficher(String.format("%s du service %s modifié.", HEURE_SERVICE.name(), idSeance));
            }
            case "5" -> {
                afficher("Veuillez entrée la nouvelle journée (ex : \"mercredi\") : ");
                String recurrenceHebdo = acquisitionReponse(Helper::jourSemaineValide);
                controleurService.mettreServiceAJour(idSeance, RECURRENCE_HEBDO_SERVICE, recurrenceHebdo);
                afficher(String.format("%s du service %s modifié.", RECURRENCE_HEBDO_SERVICE.name(), idSeance));
            }
            case "6" -> {
                afficher("Veuillez entrer la nouvelle capacité maximale (1-30) :");
                String capaciteMaximale = acquisitionReponse((String s) -> Helper.intValide(s)
                        && getIntFromString(s) >= 1
                        && getIntFromString(s) <= 30);
                controleurService.mettreServiceAJour(idSeance, CAPACITE_MAX_SERVICE, capaciteMaximale);
                afficher(String.format("%s du service %s modifié.", CAPACITE_MAX_SERVICE.name(), idSeance));
            }
            case "7" -> {
                afficher("Veuillez entrer les frais du service (XXX.XX) :");
                String fraisService = acquisitionReponse(Helper::fraisServiceValide);
                controleurService.mettreServiceAJour(idSeance, FRAIS_SERVICE, fraisService);
                afficher(String.format("%s du service %s modifié.", FRAIS_SERVICE.name(), idSeance));
            }
            case "8" -> {
                afficher("Veuillez entrer le nouveau commentaire :");
                String commentaires = getTexteConsole();
                controleurService.mettreServiceAJour(idSeance, COMMENTAIRE_SERVICE, commentaires);
                afficher(String.format("%s du service %s modifié.", COMMENTAIRE_SERVICE.name(), idSeance));
            }
        }
    }

    private void supprimerService(String idSeance) {
        afficher("1. Valider suppression.");
        afficher("0. Retour au menu principal.");

        String reponse = acquisitionReponse(Collections.singletonList("1"));

        if (reponse.equals("1")) {
            controleurService.supprimerService(idSeance);
            afficher(String.format("Service %s supprimé.", idSeance));
        }
    }

    public void creerService(String idProfessionnel) {
        Vue.afficher("Veuillez entrer le nom du service :");
        String nomService = acquisitionReponse();
        if (nomService.equals("0")) return;

        afficher("Veuillez entrer la date de début du service (jj-mm-aaaa) :");
        String dateDebutService = acquisitionReponse(Helper::dateValide);
        if (dateDebutService.equals("0")) return;

        afficher("Veuillez entrer la date de fin du service (jj-mm-aaaa) :");
        String dateFinService = acquisitionReponse(Helper::dateValide);
        if (dateFinService.equals("0")) return;

        afficher("Veuillez entrer l'heure du service (hh:mm) :");
        String heureService = acquisitionReponse(Helper::horaireValide);
        if (heureService.equals("0")) return;

        afficher("Veuillez entrer la récurrence hebdomadaire (ex : entrez \"mercredi\") :");
        String recurrenceHebdo = acquisitionReponse(Helper::jourSemaineValide);
        if (recurrenceHebdo.equals("0")) return;

        afficher("Veuillez entrer la capacité maximale (1-30) :");
        String capaciteMaximale = acquisitionReponse((String s) -> Helper.intValide(s)
                && getIntFromString(s) >= 1
                && getIntFromString(s) <= 30);
        if (capaciteMaximale.equals("0")) return;

        afficher("Veuillez entrer les frais du service (XXX.XX) :");
        String fraisService = acquisitionReponse(Helper::fraisServiceValide);
        if (fraisService.equals("0")) return;

        afficher("Veuillez entrer les commentaires (ENTREE pour passer) :");
        String commentaires = getTexteConsole();

        controleurService.creerService(nomService,
                dateDebutService,
                dateFinService,
                heureService,
                recurrenceHebdo,
                capaciteMaximale,
                idProfessionnel,
                fraisService,
                commentaires);

        Vue.afficher("Service créé");
    }

    public void inscriptionSeance() {
    	String membreId = validerTypeClient();
    	if(membreId==null) { return;}
    	inscriptionSeance(membreId);
    }

    public void inscriptionSeance(String membreId) {
        effacerEcran();
        afficher("---Inscription à une séance---");
        
        String idSeance = validerIdSeance();
        if(idSeance.equals("0")) {return;}

        afficher("Les frais à payer pour la séance sont de : " + controleurService.getFraisService(idSeance) + "$");

        afficher("1. Continuer inscription");
        afficher("2. Quitter et revenir au menu principal");

        String reponseContinuer = acquisitionReponse(Arrays.asList("1","2"));
        if (!reponseContinuer.equals("1")) return;

        afficher("Le paiement est-il valide ?");
        afficher("1. Oui");
        afficher("2. Non");
        String responsePaiement = acquisitionReponse(Arrays.asList("1","2"));

        if (responsePaiement.equals("1")) {
            afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
            String commentaire = getTexteConsole();

            String nomClient = ControleurClient.nomClient(membreId);
            String inscriptionId = controleurService.inscriptionSeance(membreId, nomClient, idSeance, commentaire);

            afficher("Le membre " +
                    membreId +
                    " a été inscrit à la séance : \n" +
                    controleurService.getInformationSeance(idSeance) +
                    "\nRécapitulatif de l'inscription : \n" +
                    controleurService.getInformationsInscription(inscriptionId)
                    );
        } else {
            afficher("Annulation de l'inscription. Retour au menu principal.");
        }
    }

    public void consultationSeance() {
        effacerEcran();
        afficher("Veuillez entrer le numéro du professionnel ou entrez 0 pour revenir au menu principal.");

        String idProfessionnel = acquisitionReponse((String s) -> ControleurClient.authentifier(TypeClient.PROFESSIONNEL, s), "Numéro du professionnel incorrect.");
        if (idProfessionnel.equals("0")) return;
        afficherSeance(idProfessionnel);
    }
    
    public String afficherSeance(String idProfessionnel) {
        afficher("---Consultation d'une séance---");


        afficher("Veuillez choisir une séance parmi celles du professionnel " + idProfessionnel + " ou entrez 0 pour revenir au menu principal :");
        afficher(controleurService.obtenirToutesLesSeancesDuProfessionnelEnString(idProfessionnel));

        String idSeance = acquisitionReponse(controleurService.obtenirListeSeancesDuProfessionnel(idProfessionnel));
        if (idSeance.equals("0")) return null;
        afficher(controleurService.getInformationSeance(idSeance));

        afficher("Liste des inscriptions à la séance :");
        afficher(controleurService.obtenirInscriptionsASeanceEnString(idSeance));
        return idSeance;
    }

    public String validerTypeClient() {
    	afficher("Veuillez entrer le numéro du membre ou scanner le code QR :");
        String idMembre = acquisitionReponse(Helper::identifiantClientValide);

        TypeClient typeClient = ControleurClient.verifierTypeClient(TypeClient.MEMBRE, idMembre);
        switch (typeClient) {
            case MEMBRE_SUSPENDU -> {
                afficher("Membre suspendu. Retour au menu principal.");
                return null;
            }
            case MEMBRE_VALIDE -> {
                afficher("Membre valide.");
                return idMembre;
            }
            default -> {
                afficher("Membre inconnu. Retour au menu principal.");
                return null;
            }
        }
    }
    
    public String validerIdSeance() {
    	afficher("Références des séances disponibles aujourd'hui, le " + Helper.today() + " :");
        afficher(controleurService.obtenirToutesLesSeancesDuJourEnString(Helper.today()));

        afficher("Veuillez entrer le numéro de séance ou entrer 0 pour revenir au menu principal :");
        return acquisitionReponse(controleurService.obtenirListeIdSeancesDuJour(Helper.today()));
    }
    
    public void confirmationPresence() {
        afficher("---Confirmation de la présence---");

        String idSeance = validerIdSeance();
    	if(idSeance.equals("0")) {return;}
        confirmationPresence(idSeance);
    }

    public void confirmationPresence(String idSeance) {
    	
    	String idMembre = validerTypeClient();
        if(idMembre==null) { return;}

    	if (!controleurService.inscriptionExiste(idMembre, idSeance)) {
    		afficher("Le membre n'est pas inscrit. Accès refusé.");
        } else {
    		afficher("Confirmer la présence ?");
    		afficher("1. Oui");
    		afficher("2. Non");
    		String reponse = acquisitionReponse(Arrays.asList("1","2"));

    		if (reponse.equals("1")) {
    			afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
    			String commentaire = getTexteConsole();

    			if (controleurService.confirmerPresence(idSeance, idMembre, commentaire)) {
    				afficher("Présence validée.");
    			} else {
    				afficher("Échec de la confirmation de présence.");
    			}
    		} else {
    			afficher("Annulation de la confirmation.");
    		}

    	}
    }

}
