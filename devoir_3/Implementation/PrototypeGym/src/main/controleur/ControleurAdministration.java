package main.controleur;

import main.modele.Professionnel;
import main.modele.ProfessionnelTef;
import main.vue.Vue;

import java.util.HashMap;
import java.util.List;

public class ControleurAdministration extends Controleur {

    public ControleurAdministration() {

    }
    
    public String genererTef() {
    	 HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
    	 HashMap<String, Membre> listeMembres = ControleurClient.getListeMembres();
    }

    public String genererRapportSynthese() {
 
        HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
        var rapport = ControleurService.centreDonneesServices.genererRapportSynthese(listeProfessionnels);

        String concat = rapport.toString()+ "\n";
 
        return concat;
    }
}
