package main.modele;

import java.util.ArrayList;
import java.util.List;

import main.controleur.Verificateurs;

public class ProfessionnelTef extends Tef {
 
	private double montant;
    private int nombreServices;
    private ArrayList<Inscription> listInscription;

    public ProfessionnelTef(String nom, String numero, String adresse, String ville, String province, String codePostal, int nombreServices) {
        super(nom, numero, adresse, ville, province, codePostal);
        this.nombreServices = nombreServices;
        this.listInscription = new ArrayList<Inscription>();
    }

    public double getMontant() {
        return montant;
    }

    public int getNombreServices() {
        return nombreServices;
    }

    public void ajouterFraisAPayer(double montant) {
        this.montant += montant;
    }

    public void ajouterService() {
        nombreServices++;
    }
    
    public void ajouterInscription(Inscription inscription) {
    	this.listInscription.add(inscription);
    	ajouterFraisAPayer(inscription.getMontant());
    }
    
    @Override
    public String toString() {
    	String print = super.toString();
    	if(this.listInscription!=null) {
    		for(Inscription i : this.listInscription) {
    			print += "Date séance" + i.getDateSeanceString() + "\n";
    			print += "Date inscriptions" + i.getDateEtHeureActuelleString() + "\n";
    			print += "Nom du membre" + i.getNomMembre() + "\n";
    			print += "Numéro du membre" + i.getNumeroMembre() + "\n";
    			print += "Code de la séance" + i.getCodeSeance() + "\n";
    			print += "Montant à payer" + i.getMontant() + "\n";
    		}
    	}
    	return print;
    }
}