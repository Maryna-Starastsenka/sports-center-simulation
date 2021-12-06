package main.controleur;

import main.modele.Professionnel;
import java.util.HashMap;
import java.util.List;
import main.modele.Membre;
import main.modele.Tef;
import main.modele.ProfessionnelTef;
import main.modele.MembreTef;

/**
 * Classe ControleurAdministration qui hérite de la classe Controleur. Gère le flux de données du rapport
 * de synthèse client et met à jour la vue lorsque les données changent
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class ControleurAdministration extends Controleur {

    /**
     * Génère le rapport de synthèse
     * @return rapport de synthèse
     */
    public String genererRapportSynthese() {
        HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
        var rapport = ControleurService.centreDonneesServices.genererRapportSynthese(listeProfessionnels);
 
        return rapport.toString()+ "\n";
    }
    
    /**
     * Génère le TEF des membres
     * @return TEF professionnels
     */
    public String genererProfessionnelsTef() {
        HashMap<String, Professionnel> listeProfessionnels = ControleurClient.getListeProfessionnels();
        List<ProfessionnelTef> tef = ControleurService.centreDonneesServices.genererTefProfessionnel(listeProfessionnels);
        String rapport = "TEF DES PROFESSIONNELS : \n";
        for(ProfessionnelTef p : tef){
            rapport +=p.toString()+"\n";
        }
        return rapport.toString()+ "\n";
    }
    
    /**
     * Génère le rapport de synthèse
     * @return TEF membres
     */
    public String genererMembresTef() {
        HashMap<String, Membre> listeMembres = ControleurClient.getListeMembres();
        List<MembreTef> tef = ControleurService.centreDonneesServices.genererTefMembre(listeMembres);
        String rapport = "TEF DES MEMBRES : \n";
        for(MembreTef m : tef){
            rapport += m.toString()+"\n";
        } 
        return rapport.toString()+ "\n";
    }
}
