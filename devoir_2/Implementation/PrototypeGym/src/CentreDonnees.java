import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CentreDonnees {
    private HashMap<String, Membre> listeMembres = new HashMap<>();
//	public HashMap<String, Membre> listeMembresSuspendus = new HashMap<>();
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();
    private HashMap<String, Service> listeServices = new HashMap<>();
    private HashMap<String, Seance> listeSeances = new HashMap<>();
    private HashMap<String, Inscription> listeInscriptions = new HashMap<>();
    private HashMap<String, Inscription> listeConfirmPresence = new HashMap<>();

    public CentreDonnees() {
        // todo retirer valeurs par défaut
        listeMembres.put("111111111", new Membre("Maryna", new Date(1234, 12, 27), "qwerty",
                "123-123-1234", "qwe@asd.com", true));
        listeProfessionnels.put("222222222", new Professionnel("Tim", new Date(1905, 6, 3),
                "qwerty", "123-321-1213", "vrve@asd.com"));
        listeServices.put("3333333", new Service(new Date(2020, 7,2,10, 30, 00),
                new Date(2020, 7, 5), new Date(2020, 10, 7), "18:00",
                2, 25, "222222222", "3333333", 60.00, "bla bla bla"));
        listeServices.put("4444444", new Service(new Date(2020, 7,18,10, 30, 00),
                new Date(2020, 7, 18), new Date(2020, 9, 18), "08:00",
                1, 30, "222222222", "4444444", 50.00, "-"));
    }

    // public void genererListeSeances (Date date) { }

    public void ajouterMembre(Membre membre) {
        listeMembres.put(membre.getNumero(), membre);
    }

    public void ajouterProfessionnel(Professionnel professionnel) {
        listeProfessionnels.put(professionnel.getNumero(), professionnel);
    }

    public void ajouterService(Service service) {
        listeServices.put(service.getCode(), service);
    }

    public boolean membreEstValide(String id) {
        return listeMembres.containsKey(id);
    }

    public boolean professionnelEstValide(String id) {
        return listeProfessionnels.containsKey(id);
    }

    public List<Service> getServices(String idProfessionnel) {
        return listeServices.values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

    public Service getService(String idService) {
        return listeServices.get(idService);
    }

    public void supprimerService(String serviceEntre) {
        listeServices.remove(serviceEntre);
    }
}
