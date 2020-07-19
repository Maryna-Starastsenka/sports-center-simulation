import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CentreDonnees {
    private HashMap<String, Membre> listeMembres = new HashMap<>();
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();
    private HashMap<String, Service> listeServices = new HashMap<>();
    private HashMap<String, Seance> listeSeances = new HashMap<>();
    private HashMap<String, Inscription> listeInscriptions = new HashMap<>();
    private HashMap<String, ConfirmationPresence> listeConfirmationsPresence = new HashMap<>();

    public static ZoneId zoneId = ZoneId.systemDefault();
    public static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public CentreDonnees() {
        // valeurs par défaut
        listeMembres.put("123456789", new Membre("Maryna", LocalDate.of(1989, 9, 28), "123 rue Martin, Montreal",
                "123-123-1234", "maryna@udem.com", true));
        listeProfessionnels.put("987654321", new Professionnel("Jean", LocalDate.of(1980, 12, 25),
                "456 rue Michel, Laval", "987-987-9876", "Jean@udem.com"));
        listeServices.put("1234567", new Service(LocalDateTime.of(LocalDate.of(2020, 1,1), LocalTime.of(8,0,0)),
                LocalDate.of(2025, 12, 31), LocalDate.of(2020, 7, 19), LocalTime.of(22, 30),
                7, 25, "987654321", "1234567", 63.25, "Rien à signaler"));
        listeServices.put("2345678", new Service(LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
                LocalDate.of(2030, 11, 30), LocalDate.of(2020, 7, 19), LocalTime.of(18,20),
                1, 30, "222222222", "2345678", 100.00, "Aucun commentaire"));
    }

    public void ajouterMembre(Membre membre) {
        listeMembres.put(membre.getNumero(), membre);
    }

    public void ajouterProfessionnel(Professionnel professionnel) {
        listeProfessionnels.put(professionnel.getNumero(), professionnel);
    }

    public void ajouterService(Service service) {
        listeServices.put(service.getCode(), service);
    }

    public boolean membreEstValide(String idMembre) {
        return listeMembres.containsKey(idMembre);
    }

    public boolean professionnelEstValide(String idProfessionnel) {
        return listeProfessionnels.containsKey(idProfessionnel);
    }

    public List<Service> getServices(String idProfessionnel) {
        return listeServices
                .values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

    public Service getService(String idService) {
        return listeServices.get(idService);
    }

    public Membre getMembre(String idMembre) {
        return listeMembres.get(idMembre);
    }

    public Professionnel getProfessionnel(String idProfessionnel) {
        return listeProfessionnels.get(idProfessionnel);
    }

    public List<Seance> getListeSeances(LocalDate date) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDateSeance().equals(date))
                .collect(Collectors.toList());
    }

    public void supprimerService(String serviceEntre) {
        listeServices.remove(serviceEntre);
    }

    public void supprimerMembre(String idMembre) {
        listeMembres.remove(idMembre);
    }

    public void supprimerProfessionnel(String idProfessionnel) {
        listeMembres.remove(idProfessionnel);
    }


}
