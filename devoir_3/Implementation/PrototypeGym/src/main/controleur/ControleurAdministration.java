package main.controleur;

import main.modele.Membre;
import main.modele.Professionnel;

import java.util.HashMap;

public class ControleurAdministration extends Controleur {

    public ControleurAdministration() {

    }
    
    public void genererTef() {
    	 HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
    	 HashMap<String, Membre> listeMembres = ControleurClient.getListeMembres();
    	 ControleurService.centreDonneesServices.genererTefMembre(listeMembres);
    	 ControleurService.centreDonneesServices.genererTefProfessionnel(listeProfessionnels);
    }

    public String genererRapportSynthese() {
 
        HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
        var rapport = ControleurService.centreDonneesServices.genererRapportSynthese(listeProfessionnels);
 
        return rapport.toString()+ "\n";
    }
}
