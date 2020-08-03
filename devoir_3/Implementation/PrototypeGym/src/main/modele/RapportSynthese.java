package main.modele;

import java.util.ArrayList;
import java.util.List;

public class RapportSynthese {
    private List<ProfessionnelTef> proTef = new ArrayList<>();
    private int nombreTotalProfessionnels;
    private int nombreTotalServices;
    private double totalFrais;

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
    
    @Override
    public String toString() {
    	String print = "Rapport de synthèse \n";
    	print += "Liste des professionnels à payer : \n";
    	for(ProfessionnelTef pro : proTef) {
    		print += String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donnés cette semaine.",
                    pro.getNom(),
                    pro.getNumero(),
                    pro.getMontant(),
                    pro.getNombreServices()) + "\n";
    	}
    	print += "\n* Nombre total de professionnels à payer : " + nombreTotalProfessionnels;
        print += "\n* Nombre total de services : " + nombreTotalServices;
        print += "\n* Nombre total des frais à payer : " + totalFrais + "$ \n";
    	return print;
    }
}