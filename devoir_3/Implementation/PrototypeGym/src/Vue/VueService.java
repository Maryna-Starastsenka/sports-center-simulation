package Vue;

import Controleur.ControleurClient;
import Controleur.ControleurService;
import Controleur.Verificateurs;
import Modele.TypeClient;

import java.util.Arrays;

import static Controleur.Verificateurs.identifiantClientValide;

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
        //todo
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
