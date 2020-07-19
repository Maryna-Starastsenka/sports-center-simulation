import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        Membre membre1 = new Membre("Maryna", LocalDate.of(1989, 9, 28), "123 rue Martin, Montreal",
                "123-123-1234", "maryna@udem.com", true);
        listeMembres.put(membre1.getHashInString(), membre1);

        Professionnel professionnel1 = new Professionnel("Jean", LocalDate.of(1980, 12, 25),
                "456 rue Michel, Laval", "987-987-9876", "Jean@udem.com");
        listeProfessionnels.put(professionnel1.getHashInString(), professionnel1);

        Service service1 = new Service("Zumba", LocalDateTime.of(LocalDate.of(2020, 1,1), LocalTime.of(8,0,0)),
                LocalDate.of(2025, 12, 31), LocalDate.of(2020, 7, 19), LocalTime.of(22, 30),
                7, 25, professionnel1.getHashInString(), "1234567", 63.25, "Rien à signaler");
        listeServices.put(service1.getCode(), service1);

        Service service2 = new Service("Yoga", LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
                LocalDate.of(2030, 11, 30), LocalDate.of(2020, 7, 19), LocalTime.of(18,20),
                1, 30, professionnel1.getHashInString(), "2345678", 100.00, "Aucun commentaire");
        listeServices.put(service2.getCode(), service2);

        // crée une séance au jour d'exécution du programme pour les tests
        Seance seance1 = new Seance(LocalDateTime.of(today(), LocalTime.of(12, 30)), service1.getCode());
        listeSeances.put(seance1.getHashInString(), seance1);

        Seance seance2 = new Seance(LocalDateTime.of(today(), LocalTime.of(17, 20)), service2.getCode());
        listeSeances.put(seance2.getHashInString(), seance2);
    }

    public void ajouterMembre(Membre membre) {
        listeMembres.put(membre.getHashInString(), membre);
    }

    public void ajouterProfessionnel(Professionnel professionnel) {
        if (!listeProfessionnels.containsKey(professionnel.getHashInString())) {
            listeProfessionnels.put(professionnel.getHashInString(), professionnel);
        }
    }

    public void ajouterService(Service service) {
        if (!listeServices.containsKey(service.getCode())) {
            listeServices.put(service.getCode(), service);
        }
    }

    public void inscrireMembreASeance(String membreId, String seanceID, String commentaires) {
        Seance seance = listeSeances.get(seanceID);
        Service service = listeServices.get(seance.getCodeService());
        Inscription inscription = new Inscription(
                LocalDateTime.now(),
                seance.getDateTimeSeance().toLocalDate(),
                service.getNumeroProfessionnel(),
                membreId,
                seance.getCodeService(),
                commentaires);
        if (!listeInscriptions.containsKey(inscription.getHashInString())) {
            listeInscriptions.put(inscription.getHashInString(), inscription);
        }
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
                .filter(x -> x.getDateTimeSeance().toLocalDate().equals(date))
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

    public static LocalDate today() {
        return LocalDate.now(zoneId) ;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(zoneId) ;
    }

    public Seance getSeance(String id) {
        return listeSeances.get(id);
    }

    public List<Membre> getListeMembre() {
        return listeMembres.values().stream().collect(Collectors.toList());
    }

    public List<Professionnel> getListeProfessionnels() {
        return listeProfessionnels.values().stream().collect(Collectors.toList());
    }

    public List<Service> getListeServices() {
        return listeServices.values().stream().collect(Collectors.toList());
    }

    public boolean membreExiste(String membreId) {
        return listeMembres.containsKey(membreId);
    }

    public boolean inscriptionExiste(String membreId, String seanceId) {
        List<Inscription> inscriptions = null;
        if (listeServices.containsKey(getSeance(seanceId).getCodeService())) {
            Service service = getService(getSeance(seanceId).getCodeService());
             inscriptions = listeInscriptions
                    .values()
                    .stream()
                    .filter(x -> x.getNumeroMembre().equals(membreId) && x.getCodeService().equals(service.getCode()))
                    .collect(Collectors.toList());
        }
        return inscriptions != null && inscriptions.size() >= 1;
    }

    public void confirmationPresence(String seanceId, String membreId, String commentaire) {
        String codeService = listeSeances.get(seanceId).getCodeService();
        String numeroProfessionnel = listeServices.get(codeService).getNumeroProfessionnel();
        ConfirmationPresence cp = new ConfirmationPresence(now(), membreId, numeroProfessionnel, codeService, commentaire);
        if (!listeConfirmationsPresence.containsKey(cp.getHashInString())) {
            listeConfirmationsPresence.put(cp.getHashInString(), cp);
        }
    }
}
