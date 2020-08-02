package main.controleur;

import main.modele.Professionnel;
import main.modele.ProfessionnelTef;
import main.vue.Vue;

import java.util.HashMap;
import java.util.List;

public class ControleurAdministration extends Controleur {

    public ControleurAdministration() {

    }

    public String genererRapportSynthese() {
        String concatenation = "";
        HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
        var rapport = ControleurService.centreDonneesServices.genererRapportSynthese(listeProfessionnels);

        concatenation += "Liste des professionnels à payer :\n";

        for (ProfessionnelTef pro : rapport.getProTef()) {
            concatenation += String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donnés cette semaine.",
                    pro.getNom(),
                    pro.getNumero(),
                    pro.getMontant(),
                    pro.getNombreServices()) + "\n";
        }
        concatenation += "\n* Nombre total de professionnels à payer : " + rapport.getNombreTotalProfessionnels();
        concatenation += "\n* Nombre total de services : " + rapport.getNombreTotalServices();
        concatenation += "\n* Nombre total des frais à payer : " + rapport.getTotalFrais() + "$";

        return concatenation;
    }
}
