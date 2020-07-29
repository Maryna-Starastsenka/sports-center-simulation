import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CentreDonnees {
    private HashMap<String, Membre> listeMembres = new HashMap<>();
    private HashMap<String, Professionnel> listeProfessionnels = new HashMap<>();
    private HashMap<String, Service> listeServices = new HashMap<>();
    private HashMap<String, Seance> listeSeances = new HashMap<>();
    private HashMap<String, Inscription> listeInscriptions = new HashMap<>();
    private HashMap<String, ConfirmationPresence> listeConfirmationsPresence = new HashMap<>();

    private List<ProfessionnelTef> listeProfessionnelsTef = new ArrayList<>();

    public static ZoneId zoneId = ZoneId.systemDefault();
    public static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public CentreDonnees() {
        // valeurs par défaut
        /*** MEMBRES ***/
        Membre membre1 = new Membre("Maryna",
                LocalDate.of(1989, 9, 28),
                "123 rue Martin, Montreal",
                "123-123-1234",
                "maryna@udem.com",
                true);
        listeMembres.put(membre1.getHashInString(), membre1);

        /*** PROFESSIONNELS ***/
        Professionnel professionnel1 = new Professionnel("Jean", LocalDate.of(1980, 12, 25),
                "456 rue Michel, Laval",
                "987-987-9876",
                "Jean@udem.com");
        listeProfessionnels.put(professionnel1.getHashInString(), professionnel1);

        Professionnel professionnel2 = new Professionnel("Baptiste",
                LocalDate.of(1970, 6, 18),
                "1000 bld Henri, Longueil",
                "182-323-3432",
                "baptiste@udem.com");
        listeProfessionnels.put(professionnel2.getHashInString(), professionnel2);

        /*** SERVICES ***/
        Service service1 = new Service("Zumba",
                LocalDateTime.of(LocalDate.of(2020, 1,1), LocalTime.of(8,0,0)),
                LocalDate.of(2025, 12, 31),
                LocalDate.of(2020, 7, 19),
                LocalTime.of(22, 30),
                7,
                25,
                professionnel1.getHashInString(),
                "1234567",
                63.25,
                "Rien à signaler");
        listeServices.put(service1.getCode(), service1);

        Service service2 = new Service("Yoga",
                LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2025, 7, 12),
                LocalTime.of(18,20),
                1,
                25,
                professionnel1.getHashInString(),
                "2345678",
                100.00,
                "Aucun commentaire");
        listeServices.put(service2.getCode(), service2);

        Service service3 = new Service("Danse", LocalDateTime.of(LocalDate.of(2020, 3,1), LocalTime.of(7, 30, 0)),
                LocalDate.of(2017, 11, 15),
                LocalDate.of(2036, 9, 20),
                LocalTime.of(18,20),
                1,
                2,
                professionnel2.getHashInString(),
                "967588234",
                50.12,
                "En refonte");
        listeServices.put(service3.getCode(), service3);

        /*** SEANCES ***/
        // crée une séance au jour d'exécution du programme pour les tests
        Seance seance1 = new Seance(LocalDateTime.of(today(), LocalTime.of(12, 30)),
                service1.getCode());
        listeSeances.put(seance1.getHashInString(), seance1);

        Seance seance2 = new Seance(LocalDateTime.of(today(), LocalTime.of(17, 20)),
                service2.getCode());
        listeSeances.put(seance2.getHashInString(), seance2);

        Seance seance3 = new Seance(LocalDateTime.of(today(), LocalTime.of(11, 00)).minusDays(1),
                service3.getCode());
        listeSeances.put(seance3.getHashInString(), seance3);

        Seance seance4 = new Seance(LocalDateTime.of(today(), LocalTime.of(15, 55)).minusDays(8),
                service2.getCode());
        listeSeances.put(seance4.getHashInString(), seance4);

        /*** INSCRIPTION ***/
        Inscription inscription1 = new Inscription(now(),
                seance2.getDateTimeSeance().toLocalDate(),
                professionnel1.getHashInString(),
                membre1.getHashInString(),
                seance2.getCodeService(),
                "");
        listeInscriptions.put(inscription1.getHashInString(), inscription1);
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

    public void inscrireMembreASeance(String idMembre, String idSeance, String commentaires) {
        Seance seance = listeSeances.get(idSeance);
        Service service = listeServices.get(seance.getCodeService());
        Inscription inscription = new Inscription(
                LocalDateTime.now(),
                seance.getDateTimeSeance().toLocalDate(),
                service.getNumeroProfessionnel(),
                idMembre,
                seance.getCodeService(),
                commentaires);
        if (!listeInscriptions.containsKey(inscription.getHashInString())) {
            listeInscriptions.put(inscription.getHashInString(), inscription);
        }
    }

    public boolean estMembre(String idMembre) { return listeMembres.containsKey(idMembre); }

    public boolean estProfessionnel(String idProfessionnel) {
        return listeProfessionnels.containsKey(idProfessionnel);
    }

    public List<Service> getListeServicesPro(String idProfessionnel) {
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

    public Seance getSeance(String idSeance) {
        return listeSeances.get(idSeance);
    }

    public List<Membre> getListeMembres() {
        return listeMembres.values().stream().collect(Collectors.toList());
    }

    public List<Professionnel> getListeProfessionnels() {
        return listeProfessionnels.values().stream().collect(Collectors.toList());
    }

    public boolean inscriptionExiste(String idMembre, String idSeance) {
        List<Inscription> inscriptions = null;
        if (listeServices.containsKey(getSeance(idSeance).getCodeService())) {
            Service service = getService(getSeance(idSeance).getCodeService());
             inscriptions = listeInscriptions
                    .values()
                    .stream()
                    .filter(x -> x.getNumeroMembre().equals(idMembre) && x.getCodeService().equals(service.getCode()))
                    .collect(Collectors.toList());
        }
        return inscriptions != null && inscriptions.size() >= 1;
    }

    public void confirmationPresence(String idSeance, String idMembre, String commentaires) {
        String codeService = listeSeances.get(idSeance).getCodeService();
        String numeroProfessionnel = listeServices.get(codeService).getNumeroProfessionnel();
        ConfirmationPresence cp = new ConfirmationPresence(now(), idMembre, numeroProfessionnel, codeService, commentaires);
        if (!listeConfirmationsPresence.containsKey(cp.getHashInString())) {
            listeConfirmationsPresence.put(cp.getHashInString(), cp);
        }
    }

    public List<Inscription> getListeInscriptionsPro(String idProfessionnel) {
        return listeInscriptions
                .values()
                .stream()
                .filter(x -> x.getNumeroProfessionnel().equals(idProfessionnel))
                .collect(Collectors.toList());
    }

    public List<Seance> getSeancesEntre(LocalDate debut, LocalDate fin) {
        return listeSeances
                .values()
                .stream()
                .filter(x -> x.getDateTimeSeance().toLocalDate().compareTo(debut) >= 0 && x.getDateTimeSeance().toLocalDate().compareTo(fin) <= 0)
                .collect(Collectors.toList());
    }

    public void genererTef() {
        LocalDate dateDebut = LocalDate.now().minusDays(8).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        LocalDate dateFin = dateDebut.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        List<Seance> seances = getSeancesEntre(dateDebut, dateFin);
        HashMap<String, ProfessionnelTef> professionnelsAPayer = new HashMap<>();
        HashSet<String> serviceIds = new HashSet<>();
        for (Seance seance: seances) {
            Service service = getService(seance.getCodeService());

            if (!serviceIds.contains(service.getCode())) {
                serviceIds.add(service.getCode());
            }

            Professionnel pro = getProfessionnel(service.getNumeroProfessionnel());

            if (!professionnelsAPayer.containsKey(pro.getHashInString())){
                var proTef = new ProfessionnelTef(pro.getNom(), pro.getHashInString(), service.getFraisService(), 0);

                professionnelsAPayer.put(pro.getHashInString(), proTef);
            } else {
                professionnelsAPayer.get(pro.getHashInString()).ajouterFraisAPayer(service.getFraisService());
            }
        }

        for (String s : serviceIds) {
            String pro = listeServices.get(s).getNumeroProfessionnel();
            professionnelsAPayer.get(pro).ajouterService();
        }

        listeProfessionnelsTef = professionnelsAPayer
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    public RapportSynthese genererRapportSynthese() {
        genererTef();
        int nombreTotalServices = listeProfessionnelsTef.stream().mapToInt(ProfessionnelTef::getNombreServices).sum();
        double fraisTotaux = listeProfessionnelsTef.stream().mapToDouble(ProfessionnelTef::getMontant).sum();
        return new RapportSynthese(listeProfessionnelsTef,
                listeProfessionnelsTef.size(),
                nombreTotalServices,
                fraisTotaux);
    }
}
