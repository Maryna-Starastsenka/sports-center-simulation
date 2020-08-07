package main.modele;

import main.controleur.ControleurClient;
import main.controleur.ControleurService;
import java.util.ArrayList;

/**
 * Classe MembreTef qui hérite de Tef. TEF d'un membre
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class MembreTef extends Tef {

    private ArrayList<Inscription> listInscription;

    /**
     * Constructeur de MembreTef
     * @param nom nom du membre
     * @param numero numéro unique du membre
     * @param adresse adresse du membre
     * @param ville ville du membre
     * @param province province du membre
     * @param codePostal code postal du membre
     */
    public MembreTef(String nom, String numero, String adresse, String ville, String province, String codePostal) {
        super(nom, numero, adresse, ville, province, codePostal);
        this.listInscription = new ArrayList<>();
    }

    /**
     * Ajoute l'inscription dans la liste des inscriptions
     * @param inscription inscription
     */
    public void ajouterInscription(Inscription inscription) {
    	this.listInscription.add(inscription);
    }

    /**
     * Retourne les informations sur l'inscription en string
     * @return informations sur l'inscription
     */
    @Override
    public String toString() {
    	StringBuilder print = new StringBuilder(super.toString());
    	if(this.listInscription!=null) {
    		for(Inscription i : this.listInscription) {
    			print.append("Date séance : ").append(i.getDateSeanceString()).append("\n");
    			print.append("Nom du professionnel : ")
                        .append(ControleurClient.nomClient(i.getNumeroProfessionnel())).append("\n");
    			print.append("Nom du service : ")
                        .append(ControleurService.nomService(i.getNumeroProfessionnel())).append("\n\n");
    		}
    	}
    	return print.toString();
    }
}