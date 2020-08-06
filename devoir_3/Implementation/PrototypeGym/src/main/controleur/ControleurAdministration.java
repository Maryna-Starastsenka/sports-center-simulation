package main.controleur;

import main.modele.Professionnel;
import java.util.HashMap;

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
}
