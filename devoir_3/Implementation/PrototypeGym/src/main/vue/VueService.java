package main.vue;

import main.controleur.ControleurClient;
import main.controleur.ControleurService;
import main.controleur.Verificateurs;
import main.modele.TypeClient;

import java.util.Arrays;

import static main.controleur.Verificateurs.getIntFromString;
import static main.controleur.Verificateurs.identifiantClientValide;
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
            case "1":
                creerService(idProfessionnel);
                break;
            case "2":
                gererServiceExistant(idProfessionnel);
                break;
        }
    }

    private void gererServiceExistant(String idProfessionnel) {
        enTeteGestionService();

        afficher("Service du Professionnel : " + controleurService.getListeService(this, idProfessionnel));
        afficher("Veuillez choisir le service ou 0 pour retourner au menu principal.");
        String idService = acquisitionReponse(Verificateurs::identifiantServiceValide);
        if (idService.equals("0")) {
            return;
        }

        effacerEcran();

        afficher("1. Mise à jour d'un service");
        afficher("2. Suppression d'un service");
        afficher("0. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        switch (reponse) {
            case "1":
                mettreAJourService(idService);
                break;
            case "2":
                supprimerService(idService);
                break;
        }
    }

    private void mettreAJourService(String idService) {
        enTeteGestionService();

        afficher(controleurService.getInformationsService(idService));

        String action = null;

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
            case "1":
                Vue.afficher("Veuillez entrer le nouveau nom du service :");
                String nomService = acquisitionReponse();
                controleurService.mettreServiceAJour(idService, NOM_SERVICE, nomService);
                afficher(String.format("%s du service %s modifié.", NOM_SERVICE.name(), idService));
                break;
            case "2":
                afficher("Veuillez entrer la nouvelle date de début du service (jj-mm-aaaa) :");
                String dateDebutService = acquisitionReponse(Verificateurs::dateValide);
                controleurService.mettreServiceAJour(idService, DATE_DEBUT_SERVICE, dateDebutService);
                afficher(String.format("%s du service %s modifié.", DATE_DEBUT_SERVICE.name(), idService));
                break;
            case "3":
                afficher("Veuillez entrer la nouvelle date de fin du service (jj-mm-aaaa) :");
                String dateFinService = acquisitionReponse(Verificateurs::dateValide);
                controleurService.mettreServiceAJour(idService, DATE_FIN_SERVICE, dateFinService);
                afficher(String.format("%s du service %s modifié.", DATE_FIN_SERVICE.name(), idService));
                break;
            case "4":
                afficher("Veuillez entrer la nouvelle heure du service (hh:mm) :");
                String heureService = acquisitionReponse(Verificateurs::horaireValide);
                controleurService.mettreServiceAJour(idService, HEURE_SERVICE, heureService);
                afficher(String.format("%s du service %s modifié.", HEURE_SERVICE.name(), idService));
                break;
            case "5":
                afficher("Veuillez entrer la nouvelle récurrence hebdomadaire (ex : entrez \"mercredi\") :");
                String recurrenceHebdo = acquisitionReponse(Verificateurs::jourSemaineValide);
                controleurService.mettreServiceAJour(idService, RECURRENCE_HEBDO_SERVICE, recurrenceHebdo);
                afficher(String.format("%s du service %s modifié.", RECURRENCE_HEBDO_SERVICE.name(), idService));
                break;
            case "6":
                afficher("Veuillez entrer la nouvelle capacité maximale (1-30) :");
                String capaciteMaximale = acquisitionReponse((String s) -> Verificateurs.intValide(s)
                        && getIntFromString(s) >= 1
                        && getIntFromString(s) <= 30);
                controleurService.mettreServiceAJour(idService, CAPACITE_MAX_SERVICE, capaciteMaximale);
                afficher(String.format("%s du service %s modifié.", CAPACITE_MAX_SERVICE.name(), idService));
                break;
            case "7":
                afficher("Veuillez entrer les frais du service (XXX.XX) :");
                String fraisService = acquisitionReponse(Verificateurs::fraisServiceValide);
                controleurService.mettreServiceAJour(idService, FRAIS_SERVICE, fraisService);
                afficher(String.format("%s du service %s modifié.", FRAIS_SERVICE.name(), idService));
                break;
            case "8":
                afficher("Veuillez entrer le nouveau commentaire :");
                String commentaires = getTexteConsole();
                controleurService.mettreServiceAJour(idService, COMMENTAIRE_SERVICE, commentaires);
                afficher(String.format("%s du service %s modifié.", COMMENTAIRE_SERVICE.name(), idService));
                break;
        }
    }

    private void supprimerService(String idService) {
        afficher("1. Valider suppression.");
        afficher("0. Retour au menu principal.");

        String reponse = acquisitionReponse(Arrays.asList("1"));

        if (reponse.equals("1")) {
            controleurService.supprimerService(idService);
            afficher(String.format("Service %s supprimé.", idService));
        }
    }

    public void creerService(String idProfessionnel) {
        String nomService;
        String dateEtHeureActuelles = null;
        String dateDebutService = null;
        String dateFinService = null;
        String heureService;
        String recurrenceHebdo;
        String capaciteMaximale;
        String numeroProfessionnel = idProfessionnel;
        String codeService;
        String fraisService;
        String commentaires;

        Vue.afficher("Veuillez entrer le nom du service :");
        nomService = acquisitionReponse();
        if (nomService.equals("0")) return;

        afficher("Veuillez entrer la date de début du service (jj-mm-aaaa) :");
        dateDebutService = acquisitionReponse(Verificateurs::dateValide);
        if (dateDebutService.equals("0")) return;

        afficher("Veuillez entrer la date de fin du service (jj-mm-aaaa) :");
        dateFinService = acquisitionReponse(Verificateurs::dateValide);
        if (dateFinService.equals("0")) return;

        afficher("Veuillez entrer l'heure du service (hh:mm) :");
        heureService = acquisitionReponse(Verificateurs::horaireValide);
        if (heureService.equals("0")) return;

        afficher("Veuillez entrer la récurrence hebdomadaire (ex : entrez \"mercredi\") :");
        recurrenceHebdo = acquisitionReponse(Verificateurs::jourSemaineValide);
        if (recurrenceHebdo.equals("0")) return;

        afficher("Veuillez entrer la capacité maximale (1-30) :");
        capaciteMaximale = acquisitionReponse((String s) -> Verificateurs.intValide(s)
                && getIntFromString(s) >= 1
                && getIntFromString(s) <= 30);
        if (capaciteMaximale.equals("0")) return;

        afficher("Veuillez entrer le code du service à 7 chiffres (XXXXXXX) :");
        codeService = acquisitionReponse(Verificateurs::identifiantServiceValide);
        if (codeService.equals("0")) return;

        afficher("Veuillez entrer les frais du service (XXX.XX) :");
        fraisService = acquisitionReponse(Verificateurs::fraisServiceValide);
        if (fraisService.equals("0")) return;

        afficher("Veuillez entrer les commentaires (ENTREE pour passer) :");
        commentaires = getTexteConsole();

        controleurService.creerService(nomService,
                dateDebutService,
                dateFinService,
                heureService,
                recurrenceHebdo,
                capaciteMaximale,
                numeroProfessionnel,
                codeService,
                fraisService,
                commentaires);

        Vue.afficher("Service créé");
    }

    public void inscriptionSeance() {
        effacerEcran();
        afficher("---Inscription à une séance---");
        afficher("Références des séances disponibles ajourd'hui, le " + Verificateurs.today() + " :");
        afficher(controleurService.obtenirToutesLesSeancesDuJourEnString(Verificateurs.today()));

        afficher("Veuillez entrer la référence de la séance à laquelle vous voulez " +
                "inscrire un membre ou appuyez sur 0 pour revenir au menu principal :");

        String seanceId = acquisitionReponse(controleurService.obtenirListeIdSeancesDuJour(Verificateurs.today()));
        if (seanceId.equals("0")) return;

//        afficherTousLesMembres();
        afficher("Veuillez entrer le numéro du membre :");
        String membreId = acquisitionReponse(Verificateurs::identifiantClientValide);

        TypeClient statutMembre = ControleurClient.verifierTypeClient(TypeClient.MEMBRE, membreId);

        if (statutMembre == TypeClient.MEMBRE_SUSPENDU) {
            afficher("Membre suspendu. Retour au menu principal.");
            return;
        }

        if (statutMembre == TypeClient.CLIENT_INVALIDE) {
            afficher("Membre inconnu. Retour au menu principal.");
            return;
        }

        if (statutMembre != TypeClient.MEMBRE_VALIDE) return;

        afficher("Les frais à payer pour la séance sont de : " + controleurService.getFraisService(seanceId) + "$");

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

            String inscriptionId = controleurService.inscriptionSeance(membreId, seanceId, commentaire);

            afficher("Le membre " +
                    membreId +
                    " a été inscrit à la séance : \n" +
                    controleurService.getInformationSeance(seanceId) +
                    "\nRécapitulatif de l'inscription : \n" +
                    controleurService.getInformationsInscription(inscriptionId)
                    );
        } else {
            afficher("Annulation de l'inscription. Retour au menu principal.");
        }

    }

    public void consultationSeance() {
        effacerEcran();
        afficher("---Consultation d'une séance---");
        afficher("Veuillez entrer le numéro du professionnel ou entrez 0 pour revenir au menu principal.");

        String idProfessionnel = acquisitionReponse((String s) -> ControleurClient.authentifier(TypeClient.PROFESSIONNEL, s), "Numéro du professionnel incorrect.");
        if (idProfessionnel.equals("0")) return;

        afficher("Veuillez choisir une séance parmi celles du professionnel " + idProfessionnel + " ou entrez 0 pour revenir au menu principal :");
        afficher(controleurService.obtenirToutesLesSeancesDuProfessionnelEnString(idProfessionnel));

        String idSeance = acquisitionReponse(controleurService.obtenirListeSeancesDuProfessionnel(idProfessionnel));
        if (idSeance.equals("0")) return;
        afficher(controleurService.getInformationSeance(idSeance));

        afficher("Liste des inscriptions à la séance :");
        afficher(controleurService.obtenirInscriptionsASeanceEnString(idSeance));
    }

    public void confirmationPresence() {
        afficher("---Confirmation de la présence---");

        afficher("Veuillez entrer le numéro du membre :");
        String idMembre = acquisitionReponse(Verificateurs::identifiantClientValide);

        TypeClient typeClient = ControleurClient.verifierTypeClient(TypeClient.MEMBRE, idMembre);
        switch (typeClient) {
            case CLIENT_INVALIDE:
                afficher("Membre inconnu. Retour au menu principal.");
                return;
            case MEMBRE_SUSPENDU:
                afficher("Membre suspendu. Retour au menu principal.");
                return;
            case MEMBRE_VALIDE:
                afficher("Références des séances disponibles ajourd'hui, le " + Verificateurs.today() + " :");
                afficher(controleurService.obtenirToutesLesSeancesDuJourEnString(Verificateurs.today()));

                afficher("Veuillez entrer la référence de la séance à laquelle vous voulez inscrire un membre ou appuyez sur 0 pour revenir au menu principal :");
                String idSeance = acquisitionReponse(controleurService.obtenirListeIdSeancesDuJour(Verificateurs.today()));

                if (!controleurService.inscriptionExiste(idMembre, idSeance)) {
                    afficher("Le membre n'est pas inscrit. Accès refusé.");
                    return;
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
                break;
        }
    }
}
