package main.controleur;

import main.modele.Professionnel;
import java.util.HashMap;

public class ControleurAdministration extends Controleur {

    public ControleurAdministration() {

    }

    public String genererRapportSynthese() {
 
        HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
        var rapport = ControleurService.centreDonneesServices.genererRapportSynthese(listeProfessionnels);
 
        return rapport.toString()+ "\n";
    }
}
