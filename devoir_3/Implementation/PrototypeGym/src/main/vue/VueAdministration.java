package main.vue;

import main.controleur.ControleurAdministration;
import java.util.Arrays;

/**
 * Classe Vue Administration hérite la classe Vue. Permet d'afficher le menu de la procédure comptable.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class VueAdministration extends Vue {

    ControleurAdministration controleurAdministration;

    /**
         * Constructeur de Vue Administration qui initialise le contrôleur associé
     */
    public VueAdministration() { this.controleurAdministration = new ControleurAdministration(); }

    /**
     * Affiche le menu de la procédure comptable.
     */
    public void procedureComptable() {
        effacerEcran();

        afficher("---Procédure comptable---");
        afficher("Voulez-vous générer et afficher le rapport de synthèse ?");
        afficher("1. Oui");
        afficher("2. Non");
        String reponse = acquisitionReponse(Arrays.asList("1","2"));

        if (reponse.equals("1")) {
            afficher(controleurAdministration.genererRapportSynthese());
            afficher("\nUne copie du rapport a été envoyée au gérant.");
            afficher("Exemple du format des TEF : ");
            afficher(controleurAdministration.genererProfessionnelsTef());
            afficher(controleurAdministration.genererMembresTef());
        } else {
            afficher("Annulation de la génération du rapport de synthèse. Retour au menu principal.");
        }
    }
}
