package main.modele;

import main.controleur.ControleurClient;
import main.controleur.ControleurService;

import java.util.ArrayList;

public class MembreTef extends Tef {

    private ArrayList<Inscription> listInscription;

    public MembreTef(String nom, String numero, String adresse, String ville, String province, String codePostal) {
        super(nom, numero, adresse, ville, province, codePostal);
        this.listInscription = new ArrayList<>();
    }

    public void ajouterInscription(Inscription inscription) {
    	this.listInscription.add(inscription);
    }
    
    @Override
    public String toString() {
    	StringBuilder print = new StringBuilder(super.toString());
    	if(this.listInscription!=null) {
    		for(Inscription i : this.listInscription) {
    			print.append("Date séance").append(i.getDateSeanceString()).append("\n");
    			print.append("Nom du professionnel").append(ControleurClient.nomClient(i.getNumeroProfessionnel())).append("\n");
    			print.append("Nom du service").append(ControleurService.nomService(i.getNumeroProfessionnel())).append("\n");
    		}
    	}
    	return print.toString();
    }
}