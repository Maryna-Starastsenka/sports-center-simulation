package main.vue;

import main.controleur.ControleurAdministration;
import java.util.Arrays;

public class VueAdministration extends Vue {


        ControleurAdministration controleurAdministration;

        public VueAdministration() {
            this.controleurAdministration = new ControleurAdministration();
        }

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
            } else {
                afficher("Annulation de la génération du rapport de synthèse. Retour au menu principal.");
            }
        }
}
