package main.vue;

import main.controleur.ControleurAdministration;
import main.controleur.ControleurService;
import main.modele.ProfessionnelTef;

import java.util.Arrays;

public class VueAdministration extends Vue {


        ControleurAdministration controleurAdministration;

        public VueAdministration() {
            this.controleurAdministration = new ControleurAdministration();
        }

//        public void procedureComptable() {
//            effacerEcran();
//
//            afficher("---Procédure comptable---");
//            afficher("Voulez-vous générer et afficher le rapport de synthèse ?");
//            afficher("1. Oui");
//            afficher("2. Non");
//            String reponse = acquisitionReponse(Arrays.asList("1","2"));
//
//            if (reponse.equals("1")) {
//                controleurAdministration.genererRapportSynthese();
//                afficher("Liste des professionnels à payer :");
//
//                for (ProfessionnelTef pro : rapport.getProTef()) {
//                    afficher(String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donnés cette semaine.",
//                            pro.getNom(),
//                            pro.getNumero(),
//                            pro.getMontant(),
//                            pro.getNombreServices()));
//                }
//                afficher("\n* Nombre total de professionnels à payer : " + rapport.getNombreTotalProfessionnels());
//                afficher("* Nombre total de services : " + rapport.getNombreTotalServices());
//                afficher("* Nombre total des frais à payer : " + rapport.getTotalFrais() + "$");
//
//                afficher("\nUne copie du rapport a été envoyée au gérant.");
//
//            } else {
//                afficher("Annulation de la génération du rapport de synthèse. Retour au menu principal.");
//            }
//
//            controleurAdministration.procedureComptable(hashMapProfessionnel);
//
//        }
}
