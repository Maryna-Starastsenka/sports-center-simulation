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

    /**
     * Constructeur de ProfessionnelTef
     * @param nom nom du professionnel
     * @param numero numéro du professionnel
     * @param adresse adresse du professionnel
     * @param ville ville du professionnel
     * @param province province du professionnel
     * @param codePostal code postal du professionnel
     * @param nombreServices nombre de services fournis par le professionnel
     */
    public ProfessionnelTef(String nom, String numero, String adresse, String ville, String province, String codePostal,
                            int nombreServices) {
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

    /**
     * Calcule les frais des services pour la periode définie
     * @param montant frais des services
     */
    public void ajouterFraisAPayer(double montant) {
        this.montant += montant;
    }

    /**
     * Calcule le nombre de services pour la periode définie
     */
    public void ajouterService() {
        nombreServices++;
    }

    /**
     * Fait un appel de la méthode qui calcule les frais des services pour la periode définie
     * @param inscription inscription
     */
    public void ajouterInscription(Inscription inscription) {
    	this.listInscription.add(inscription);
    	ajouterFraisAPayer(inscription.getMontant());
    }

    /**
     * Retourne les informations sur l'inscription en string
     * @return informations sur l'inscription
     */
    @Override
    public String toString() {
    	int j = 1;
    	StringBuilder print = new StringBuilder(super.toString());
    	if(this.listInscription!=null) {
    		for(Inscription i : this.listInscription) {
    			print.append("\nInscription ").append(j++).append(" : \n");
    			print.append("Date séance : ").append(i.getDateSeanceString()).append("\n");
    			print.append("Date inscriptions : ").append(i.getDateEtHeureActuelleString()).append("\n");
    			print.append("Nom du membre : ").append(i.getNomMembre()).append("\n");
    			print.append("Numéro du membre : ").append(i.getNumeroMembre()).append("\n");
    			print.append("Code de la séance : ").append(i.getCodeSeance()).append("\n");
    			print.append("Montant à payer : ").append(i.getMontant()).append("\n");
    		}
    	}
    	return print.toString();
    }
}