package Modele;

public class ProfessionnelTef {
    private String nom;
    private String numero;
    private double montant;
    private int nombreServices;

    public ProfessionnelTef(String nom, String numero, double montant, int nombreServices) {
        this.nom = nom;
        this.numero = numero;
        this.montant = montant;
        this.nombreServices = nombreServices;
    }

    public String getNom() {
        return nom;
    }

    public String getNumero() {
        return numero;
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
}