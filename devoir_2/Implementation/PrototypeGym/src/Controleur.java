import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controleur {
	private CentreDonnees centreDonnees;

	public Controleur() {
		centreDonnees = new CentreDonnees();
	}

	public void start() {
//		InterfaceUtilisateur.menuCourant = NomsMenus.PRINCIPAL;
		String entree;
		do {
			InterfaceUtilisateur.afficherMenuPrincipal();

			entree = InterfaceUtilisateur.getTexteConsole().toUpperCase();
			if (fermetureApplicationDemandee(entree)) {
				System.exit(0);
			}
			gererMenuPrincipal(entree);
		} while (true);
	}

	private boolean fermetureApplicationDemandee(String entree) {
		return entree.equals("X");
	}

	private void gererMenuPrincipal(String entreePrincipale) {
		String entreeSecondaire;
		switch (entreePrincipale) {
			case "1":
				InterfaceUtilisateur.afficherDemandeAcces();
				entreeSecondaire = InterfaceUtilisateur.getTexteConsole();
				if (!Arrays.asList("1","2","3","X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererMenuAccesGym(entreeSecondaire);
				}
				break;
			case "2":
				InterfaceUtilisateur.afficherGestionCompte();
				entreeSecondaire = InterfaceUtilisateur.getTexteConsole();
				if (!Arrays.asList("1","2","3","X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererGestionCompte(entreeSecondaire);
				}

				break;
			case "3":
				InterfaceUtilisateur.afficher("Veuillez entrer le numéro de professionnel");
				String idProfessionnel;
				do {
					idProfessionnel = InterfaceUtilisateur.getTexteConsole();
				} while (!validerProfessionnel(idProfessionnel) /*|| fermetureApplicationDemandee(idProfessionnel)*/);

				InterfaceUtilisateur.afficherGestionService();

				entreeSecondaire = InterfaceUtilisateur.getTexteConsole();
				if (!Arrays.asList("1","2","3","X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererGestionService(entreeSecondaire, idProfessionnel);
				}
				break;
			case "4":
				InterfaceUtilisateur.afficher("Séances disponibles ajourd'hui");


				break;
			case "5":
				break;
			case "6":
				break;
			case "7":
				break;
			default:
				break;
		}
	}

	private boolean gererMenuAccesGym(String entree) {
		switch (entree) {
			case "1":
				verifierAutorisationMembre();
				resetEnFinDeTransaction();
				break;
			case "2":
				verifierAutorisationProfessionnel();
				resetEnFinDeTransaction();
				break;
			case "3":
//				InterfaceUtilisateur.menuCourant = NomsMenus.PRINCIPAL;
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				return false;
		}
		return true;
	}

	private void gererProcedureComptable(String entree) {
		switch (entree) {
			case "1":

				break;
			default:
				break;
		}
	}

	private void gererConsultationSeance(String entree) {
		switch (entree) {
			case "1":

				break;
			default:
				break;
		}
	}

	private void gererConfirmationPresence(String entree) {
		switch (entree) {
			case "1":

				break;
			default:
				break;
		}
	}

	private void gererInscriptionSeance(String entree) {
		switch (entree) {
			case "1":

				break;
			default:
				break;
		}
	}



	private void gererGestionService(String entree, String idProfessionnel) {
		switch (entree) {
			case "1":
				formulaireNouveauService(idProfessionnel);
				break;
			case "2":
				List<Service> servicesDuProfessionnel = centreDonnees.getServices(idProfessionnel);
				afficherServicesProfessionnel(servicesDuProfessionnel);
				gererServiceExistant();

				break;
			case "3":
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}

	private void afficherServicesProfessionnel(List<Service> servicesDuProfessionnel) {
		InterfaceUtilisateur.clearScreen();

		InterfaceUtilisateur.afficher("------Services du professionnel------");
		for (Service s : servicesDuProfessionnel) {
			InterfaceUtilisateur.afficher("Service : " + s.getCode());
		}
	}

	private void gererServiceExistant() {
		InterfaceUtilisateur.afficher("Veuillez choisir un service :");
		String serviceEntre = InterfaceUtilisateur.getTexteConsole();
		Service serviceAModifier = centreDonnees.getService(serviceEntre);

		InterfaceUtilisateur.afficher("1. Modifier :");
		InterfaceUtilisateur.afficher("2. Supprimer :");

		String modifOuSuppr = InterfaceUtilisateur.getTexteConsole();
		switch (modifOuSuppr) {
			case "1":
				InterfaceUtilisateur.afficher("1. Modifier récurrence hebdo. Valeur actuelle : " + serviceAModifier.getRecurrenceHebdo());// todo faire les autres
				InterfaceUtilisateur.afficher("2. Modifier heure du service. Valeur actuelle : " + serviceAModifier.getHeureService());
				String modifChamps = InterfaceUtilisateur.getTexteConsole();
				switch (modifChamps) {
					case "1":
						InterfaceUtilisateur.afficher("Entrez la nouvelle valeur :");
						String nouvelleRecurrence= InterfaceUtilisateur.getTexteConsole();
						serviceAModifier.setRecurrenceHebdo(nouvelleRecurrence);
						InterfaceUtilisateur.afficher("Service modifié.");
						resetEnFinDeTransaction();
						break;

					case "2":
						InterfaceUtilisateur.afficher("Entrez la nouvelle valeur :");
						String nouvelleHeure = InterfaceUtilisateur.getTexteConsole();
						serviceAModifier.setHeureService(nouvelleHeure);
						InterfaceUtilisateur.afficher("Service modifié.");
						resetEnFinDeTransaction();
						break;
					default:
						break;
				}

				break;
			case "2":
				centreDonnees.supprimerService(serviceEntre);
				InterfaceUtilisateur.afficher("Service " + serviceEntre + " supprimé.");
				resetEnFinDeTransaction();
				break;
			default:
				break;
		}
	}

	private void formulaireNouveauService(String idProfessionnel) {
		InterfaceUtilisateur.clearScreen();

		InterfaceUtilisateur.afficher("------Formulaire de nouveau service------");

		String entree;

		Date dateEtHeureActuelles = null;
		Date dateDebutService = null;
		Date dateFinService = null;
		String heureService;
		int recurrenceHebdo;
		int capaciteMaximale;
		String numeroProfessionnel = idProfessionnel;
		String codeService;
		double fraisService;
		String commentaires;

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la date et l'heure actuelle (jj-mm-aaaa hh:mm:ss) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		try {
			dateEtHeureActuelles = getDateEtHeureFromString(entree);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la date de début du service (jj-mm-aaaa) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		try {
			dateDebutService = getDateFromString(entree);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la date de fin du service (jj-mm-aaaa) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		try {
			dateFinService = getDateFromString(entree);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer l'heure du service (hh:mm) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		heureService = getHoraire(entree);

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la récurrence hebdomadaire (1-7) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		recurrenceHebdo = getInt(entree);//getHoraire(entree);

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la capacité maximale (1-30) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		capaciteMaximale = getInt(entree);

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer le code du service (XXXXXXX) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		codeService = entree;

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer les frais du service (XXX.XX) :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		fraisService = getDouble(entree);

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer les commentaires :");
			entree = InterfaceUtilisateur.getTexteConsole();
		} while (false); //todo
		commentaires = entree;

		Service service = new Service(dateEtHeureActuelles,
				dateDebutService,
				dateFinService,
				heureService,
				recurrenceHebdo,
				capaciteMaximale,
				numeroProfessionnel,
				codeService,
				fraisService,
				commentaires);
		centreDonnees.ajouterService(service);

		InterfaceUtilisateur.afficher("Service " + service.getCode() + " enregistré.");
		resetEnFinDeTransaction();
	}

	private void gererGestionCompte(String entree) {
		switch (entree) {
			case "1":
				formulaireNouveauCompte();
				break;
			case "2":

				break;
			case "3":
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}

	private void formulaireNouveauCompte() {
		InterfaceUtilisateur.clearScreen();
		String entree2;

		String typeClient;
		String nom;
		Date dateNaissance = null;
		String adresseCourriel = null;
		String numeroTelephone = null;
		String adresse = null;

		InterfaceUtilisateur.afficher("------Formulaire de nouveau compte------");

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer le nom :");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!nomValide(entree2));
		nom = entree2;

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la date de naissance (jj-mm-aaaa):");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!dateValide(entree2));
		try {
			dateNaissance = getDateFromString(entree2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer l'adresse :");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!adresseValide(entree2));
		adresse = entree2;

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer le numéro de téléphone (XXX-XXX-XXXX):");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!telephoneValide(entree2));
		numeroTelephone = entree2;

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer l'adresse courriel (xxx@xxx.xxx) :");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!courrielValide(entree2));
		adresseCourriel = entree2;

		do {
			InterfaceUtilisateur.afficher("Inscrivez-vous un membre qui a payé les frais d'hadhésion (entrez \"1\"), " +
					"un membre qui n'a pas payé les frais (entrez \"2\"), " +
					"ou un professionnel (entrez \"3\") ?");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!typeMembreValide(entree2));
		typeClient = entree2;

		inscrireClient(typeClient, nom, dateNaissance, adresseCourriel, numeroTelephone, adresse);
	}

	private void verifierAutorisationProfessionnel() {
		String idProfessionnel;
		do {
			InterfaceUtilisateur.afficher("Entrez l'identifiant du professionnel puis appuyez sur ENTREE :");
			idProfessionnel = InterfaceUtilisateur.getTexteConsole();
		} while (idProfessionnel.length() != 9);
		if (validerProfessionnel(idProfessionnel)) {
			InterfaceUtilisateur.afficher("Le professionnel est autorisé à accéder au gym.");
		} else {
			InterfaceUtilisateur.afficher("Le professionnel n'est pas enregistré.");
		}
	}

	private void verifierAutorisationMembre() {
		String idMembre;
		do {
			InterfaceUtilisateur.afficher("Entrez l'identifiant du membre puis appuyez sur ENTREE :");
			idMembre = InterfaceUtilisateur.getTexteConsole();
		} while (idMembre.length() != 9);
		if (validerMembre(idMembre)) {
			InterfaceUtilisateur.afficher("Le membre est autorisé à accéder au gym.");
		} else {
			InterfaceUtilisateur.afficher("Le membre n'est pas autorisé à accéder au gym.");
		}
	}

	public boolean validerMembre(String id) {
		return centreDonnees.membreEstValide(id);
	}

	public boolean validerProfessionnel(String id) {
		return centreDonnees.professionnelEstValide(id);
	}

	public boolean nomValide(String entree) {
		return entree.length() >= 1;
	}

	public boolean dateValide(String entree) {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		try {
			format.parse(entree);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public Date getDateFromString(String stringDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
		return format.parse(stringDate);
	}

	public Date getDateEtHeureFromString(String stringDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
		return format.parse(stringDate);
	}

	public String getHoraire(String stringHoraire) {
		// TODO utiliser un vrai type de temps
//		DateFormat format = new SimpleDateFormat("mm:ss", Locale.FRENCH);
//		return format.parse(stringDate);
		return stringHoraire;
	}

	public int getInt(String stringInt) {
		return Integer.parseInt(stringInt);
	}

	public double getDouble(String stringDouble) {
		return Double.parseDouble(stringDouble);
	}

	public void inscrireClient(String typeMembre, String nom, Date dateNaissance, String adresseCourriel, String numeroPhone, String adresse) {
		Client client = null;
		switch (typeMembre) {
			case "1":
				Membre membreValide = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, true);
				if (!centreDonnees.membreEstValide(membreValide.getNumero())) {
					centreDonnees.ajouterMembre(membreValide);
				}
				client = membreValide;
				break;
			case "2":
				Membre membreSuspendu = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, false);
				break;
			case "3":
				Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, numeroPhone, adresseCourriel);
				if (!centreDonnees.professionnelEstValide(professionnel.getNumero())) {
					centreDonnees.ajouterProfessionnel(professionnel);
				}
				client = professionnel;
				break;
			default:
				break;
		}

		if (client != null) {
			InterfaceUtilisateur.afficher("Enregistrement du " + client);
		} else {
			InterfaceUtilisateur.afficher("Le client n'a pas été enregistré.");
		}
		resetEnFinDeTransaction();
	}

	public void resetEnFinDeTransaction() {
		InterfaceUtilisateur.afficher("Appuyez sur ENTREE pour revenir à l'écran principal");
		InterfaceUtilisateur.getTexteConsole();
	}

	public boolean adresseValide(String entree) {
		return entree.length() >= 1;
	}

	public boolean telephoneValide(String entree) {
		String regexPattern = "\\d{3}-\\d{3}-\\d{4}";
		return entree.matches(regexPattern);
	}

	public boolean courrielValide(String entree) {
		String regexPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return entree.matches(regexPattern);
	}

	public boolean typeMembreValide(String entree) {
		return entree.equals("1") || entree.equals("2") || entree.equals("3");
	}


	public void genererRapportAutomatique() {

	}

	public void creerRapportSynthese() {

	}

	public void creerTEF() {

	}
}
