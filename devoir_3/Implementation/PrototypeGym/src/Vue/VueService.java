package Vue;

import Controleur.ControleurClient;
import Controleur.ControleurService;
import Controleur.Verificateurs;
import Modele.TypeClient;

import java.util.Arrays;

import static Controleur.Verificateurs.identifiantClientValide;
import static Modele.Champs.*;

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
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

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
        afficher("3. Retour au menu principal");

        String reponse = acquisitionReponse(Arrays.asList("1","2","3"));

        switch (reponse) {
            case "1":
                mettreAJourService(idService);
                break;
            case "2":
                supprimerService(idService);
                break;
            case "3":
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
        afficher("9. Retour au menu principal.");

        action = acquisitionReponse(Arrays.asList("1","2","3","4","5","6","7","8","9"));

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
                        && Integer.parseInt(s) >= 1
                        && Integer.parseInt(s) <= 30);
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
        afficher("2. Retour au menu principal.");

        String reponse = acquisitionReponse(Arrays.asList("1","2"));

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
                && Integer.parseInt(s) >= 1
                && Integer.parseInt(s) <= 30);
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
}
