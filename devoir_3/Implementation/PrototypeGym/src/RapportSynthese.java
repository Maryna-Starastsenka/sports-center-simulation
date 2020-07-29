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
}