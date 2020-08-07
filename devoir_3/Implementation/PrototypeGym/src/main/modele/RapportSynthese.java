package main.modele;

import java.util.List;

/**
 * Classe RapportSynthese. Permet la centralisation de l'information nécessaire
 * à la génération d'un rapport de synthèse
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class RapportSynthese {
    private List<ProfessionnelTef> proTef;
    private int nombreTotalProfessionnels;
    private int nombreTotalServices;
    private double totalFrais;

    /**
     * Constructeur de RapportSynthese
     * @param proTef liste des TEF professionnel
     * @param nombreTotalProfessionnels nombre total des professionnels
     * @param nombreTotalServices nombre total des services
     * @param totalFrais total des frais de service
     */
    public RapportSynthese(List<ProfessionnelTef> proTef,
                           int nombreTotalProfessionnels,
                           int nombreTotalServices,
                           double totalFrais) {
        this.proTef = proTef;
        this.nombreTotalProfessionnels = nombreTotalProfessionnels;
        this.nombreTotalServices = nombreTotalServices;
        this.totalFrais = totalFrais;
    }

    public List<ProfessionnelTef> getProTef() {
        return proTef;
    }

    public int getNombreTotalServices() {
        return nombreTotalServices;
    }

    public double getTotalFrais() {
        return totalFrais;
    }

    public int getNombreTotalProfessionnels() {
        return nombreTotalProfessionnels;
    }

    /**
     * Retourne le rapport de synthèse en string
     * @return rapport de synthèse
     */
    @Override
    public String toString() {
    	StringBuilder print = new StringBuilder("Rapport de synthèse \n");
    	print.append("Liste des professionnels à payer : \n");
    	for(ProfessionnelTef pro : proTef) {
    		print.append(String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donnés cette semaine.",
                    pro.getNom(),
                    pro.getNumero(),
                    pro.getMontant(),
                    pro.getNombreServices())).append("\n");
    	}
    	print.append("\n* Nombre total de professionnels à payer : ").append(nombreTotalProfessionnels);
        print.append("\n* Nombre total de services : ").append(nombreTotalServices);
        print.append("\n* Nombre total des frais à payer : ").append(totalFrais).append("$ \n");
    	return print.toString();
    }
}