package main.modele;

import java.util.ArrayList;

/**
 * Classe ProfessionnelTef qui hérite de Tef. TEF d'un professionnel
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class ProfessionnelTef extends Tef {
 
	private double montant;
    private int nombreServices;
    private ArrayList<Inscription> listInscription;

    public ProfessionnelTef(String nom, String numero, String adresse, String ville, String province, String codePostal, int nombreServices) {
        super(nom, numero, adresse, ville, province, codePostal);
        this.nombreServices = nombreServices;
        this.listInscription = new ArrayList<>();
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
    	StringBuilder print = new StringBuilder(super.toString());
    	if(this.listInscription!=null) {
    		for(Inscription i : this.listInscription) {
    			print.append("Date séance").append(i.getDateSeanceString()).append("\n");
    			print.append("Date inscriptions").append(i.getDateEtHeureActuelleString()).append("\n");
    			print.append("Nom du membre").append(i.getNomMembre()).append("\n");
    			print.append("Numéro du membre").append(i.getNumeroMembre()).append("\n");
    			print.append("Code de la séance").append(i.getCodeSeance()).append("\n");
    			print.append("Montant à payer").append(i.getMontant()).append("\n");
    		}
    	}
    	return print.toString();
    }
}