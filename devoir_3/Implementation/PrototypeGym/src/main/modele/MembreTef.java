package main.modele;

import java.util.ArrayList;

import main.controleur.ControleurClient;
import main.controleur.ControleurService;
import main.controleur.Verificateurs;

public class MembreTef extends Tef {

    private ArrayList<Inscription> listInscription;

    public MembreTef(String nom, String numero, String adresse, String ville, String province, String codePostal) {
        super(nom, numero, adresse, ville, province, codePostal);
        this.listInscription = new ArrayList<Inscription>();
    }

    public void ajouterInscription(Inscription inscription) {
    	this.listInscription.add(inscription);
    }
    
    @Override
    public String toString() {
    	String print = super.toString();
    	if(this.listInscription!=null) {
    		for(Inscription i : this.listInscription) {
    			print += "Date séance" + i.getDateSeanceString() + "\n";
    			print += "Nom du professionnel" + ControleurClient.nomClient(i.getNumeroProfessionnel()) + "\n";
    			print += "Nom du service" + ControleurService.nomService(i.getNumeroProfessionnel())+ "\n";
    		}
    	}
    	return print;
    }
}