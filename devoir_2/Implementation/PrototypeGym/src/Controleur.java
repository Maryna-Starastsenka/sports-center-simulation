import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Controleur {
	private CentreDonnees centreDonnees;

	public Controleur() {
		centreDonnees = new CentreDonnees();
	}

	public void start() {
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
				if (!Arrays.asList("1", "2", "3", "X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererMenuAccesGym(entreeSecondaire);
				}
				break;
			case "2":
				InterfaceUtilisateur.afficherGestionCompte();
				entreeSecondaire = InterfaceUtilisateur.getTexteConsole();
				if (!Arrays.asList("1", "2", "3", "X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererGestionCompte(entreeSecondaire);
				}

				break;
			case "3":
				InterfaceUtilisateur.afficher("--------Gestion d'un service-------");
				InterfaceUtilisateur.afficher("Veuillez entrer le numéro de professionnel");
				String idProfessionnel;
				do {
					idProfessionnel = InterfaceUtilisateur.getTexteConsole();
				} while (!validerProfessionnel(idProfessionnel) /*|| fermetureApplicationDemandee(idProfessionnel)*/);

				InterfaceUtilisateur.afficherGestionService();

				entreeSecondaire = InterfaceUtilisateur.getTexteConsole();
				if (!Arrays.asList("1", "2", "3", "X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererGestionService(entreeSecondaire, idProfessionnel);
				}
				break;
			case "4":
				InterfaceUtilisateur.afficher("---Inscription à une séance---");
				InterfaceUtilisateur.afficher("Séances disponibles ajourd'hui");

				LocalDate today = LocalDate.now(CentreDonnees.zoneId) ;

				centreDonnees.getListeSeances(today);

				break;
			case "5":
				InterfaceUtilisateur.afficher("---Confirmation de la présence---");

				break;
			case "6":
				InterfaceUtilisateur.afficher("---Consultation d'une séance---");

				break;
			case "7":
				InterfaceUtilisateur.afficher("---Procédure comptable---");

				break;
			default:
				break;
		}
	}

	private boolean gererMenuAccesGym(String entree) {
		switch (entree) {
			case "1": // Membre
				afficherAutorisationMembre();
				resetEnFinDeTransaction();
				break;
			case "2": // Professionnel
				afficherAutorisationProfessionnel();
				resetEnFinDeTransaction();
				break;
			case "3": // Retour au menu principal par défaut
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
			case "3": // Retour au menu principal par défaut
				break;
			default:
				break;
		}
	}

	private void afficherServicesProfessionnel(List<Service> servicesDuProfessionnel) {
		InterfaceUtilisateur.clearScreen();

		InterfaceUtilisateur.afficher("------Services du professionnel------");
		InterfaceUtilisateur.afficher("Liste des services disponibles :");
		for (Service s : servicesDuProfessionnel) {
			InterfaceUtilisateur.afficher(s.getCode());
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
						String nouvelleRecurrence = InterfaceUtilisateur.getTexteConsole();
						serviceAModifier.setRecurrenceHebdo(nouvelleRecurrence);
						InterfaceUtilisateur.afficher("Service modifié.");
						resetEnFinDeTransaction();
						break;

					case "2":
						InterfaceUtilisateur.afficher("Entrez la nouvelle valeur :");
						String entree = InterfaceUtilisateur.getTexteConsole();
						LocalTime nouvelleHeure = getHoraire(entree);
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

		LocalDateTime dateEtHeureActuelles = null;
		LocalDate dateDebutService = null;
		LocalDate dateFinService = null;
		LocalTime heureService;
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
				gererCompreExistant();
				break;
			case "3": // Retour au menu principal par défaut
				break;
			default:
				break;
		}
	}

	private void gererCompreExistant() {
		InterfaceUtilisateur.afficher("Veuillez choisir le type de compte");
		InterfaceUtilisateur.afficher("1. Compte membre");
		InterfaceUtilisateur.afficher("2. Compte professionnel");
		String entree = InterfaceUtilisateur.getTexteConsole();
		switch (entree) {
			case "1":
				InterfaceUtilisateur.afficher("Veuillez entrer le numéro du membre");
				String idMembre = InterfaceUtilisateur.getTexteConsole();
				Membre membre = centreDonnees.getMembre(idMembre);
				String dateFormateMembre = new SimpleDateFormat("dd-MM-yyyy").format(membre.getDateNaissance());

				InterfaceUtilisateur.afficher("Nom : " + membre.getNom() + "\n" + "Date de naissance : "
						+ dateFormateMembre + "\n" + "Adresse courriel : " + membre.getAdresseCourriel() + "\n"
						+ "Numéro de téléphone : " + membre.getNumeroPhone() + "\n" + "Adresse : " + membre.getAdresse() + "\n"
						+ "Membre valide : " + membre.getMembreValide() + "\n");

				InterfaceUtilisateur.afficher("Veuillez choisir l'action");
				InterfaceUtilisateur.afficher("1. Modifier.");
				InterfaceUtilisateur.afficher("2. Supprimer.");
				String action = InterfaceUtilisateur.getTexteConsole();
				gererMembreExistant(action, membre, idMembre);
				break;
			case "2":
				InterfaceUtilisateur.afficher("Veuillez entrer le numéro du professionnel");
				String idProfessionnel = InterfaceUtilisateur.getTexteConsole();
				Professionnel professionnel = centreDonnees.getProfessionnel(idProfessionnel);
				String dateFormateProf = new SimpleDateFormat("dd-MM-yyyy").format(professionnel.getDateNaissance());

				InterfaceUtilisateur.afficher("Nom : " + professionnel.getNom() + "\n" + "Date de naissance : "
						+ dateFormateProf + "\n" + "Adresse courriel : " + professionnel.getAdresseCourriel() + "\n"
						+ "Numéro de téléphone : " + professionnel.getNumeroPhone() + "\n" + "Adresse : " + professionnel.getAdresse() + "\n");

				InterfaceUtilisateur.afficher("Veuillez choisir l'action");
				InterfaceUtilisateur.afficher("1. Modifier.");
				InterfaceUtilisateur.afficher("2. Supprimer.");
				String actionProf = InterfaceUtilisateur.getTexteConsole();
				gererProfessionnelExistant(actionProf, professionnel, idProfessionnel);
				break;
			case "3":
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}

	private void gererProfessionnelExistant (String action, Professionnel professionnel, String idProfessionnel) {
		switch (action) {
			case "1":
				InterfaceUtilisateur.afficher("1. Modifier l'adresse.");// todo faire les autres
				InterfaceUtilisateur.afficher("2. Modifier l'adresse courriel.");
				InterfaceUtilisateur.afficher("3. Retour au menu principal.");
				String modifProfessionnale = InterfaceUtilisateur.getTexteConsole();

				switch (modifProfessionnale) {
					case "1":
						InterfaceUtilisateur.afficher("Veuillez entrer la nouvelle addresse.");
						String nouvelleAdresse = InterfaceUtilisateur.getTexteConsole();
						professionnel.setAdresse(nouvelleAdresse);
						InterfaceUtilisateur.afficher("Professionnel modifié.");
						resetEnFinDeTransaction();
						break;
					case "2":
						InterfaceUtilisateur.afficher("Veuillez entrer la nouvelle addresse courriel.");
						String nouvelleAdresseCourriel = InterfaceUtilisateur.getTexteConsole();
						professionnel.setAdresseCourriel(nouvelleAdresseCourriel);
						InterfaceUtilisateur.afficher("Membre modifié.");
						resetEnFinDeTransaction();
						break;
					case "3":
						InterfaceUtilisateur.afficherMenuPrincipal();
						break;
					default:
						break;
				}
				break;
			case "2":
				InterfaceUtilisateur.afficher("1. Valider suppression.");
				InterfaceUtilisateur.afficher("2. Retour au menu principal.");
				String validationSuppression = InterfaceUtilisateur.getTexteConsole();
					switch (validationSuppression) {
						case "1":
							centreDonnees.supprimerProfessionnel(idProfessionnel);
							InterfaceUtilisateur.afficher("Professionnel supprimé.");
							resetEnFinDeTransaction();
							break;
						case "2":
							InterfaceUtilisateur.afficherMenuPrincipal();
						default:
							break;
					}
			case "3":
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}

	private void gererMembreExistant(String entree, Membre membre, String idMembre) {
		switch (entree) {
			case "1":
				InterfaceUtilisateur.afficher("1. Modifier le statut du membre. Valeur actuelle : " + membre.getMembreValide());// todo faire les autres
				InterfaceUtilisateur.afficher("2. Modifier le numéro de téléphone.");
				InterfaceUtilisateur.afficher("3. Retour au menu principal.");
				String modifMembre = InterfaceUtilisateur.getTexteConsole();

				switch (modifMembre) {
					case "1":
						membre.setMembreValide(!membre.getMembreValide());
						InterfaceUtilisateur.afficher("Membre modifié.");
						resetEnFinDeTransaction();
						break;
					case "2":
						InterfaceUtilisateur.afficher("Veuillez entrer le nouveau numéro de téléphone.");
						String nouveauNumeroTel = InterfaceUtilisateur.getTexteConsole();
						membre.setNumeroPhone(nouveauNumeroTel);
						InterfaceUtilisateur.afficher("Membre modifié.");
						resetEnFinDeTransaction();
						break;
					case "3":
						InterfaceUtilisateur.afficherMenuPrincipal();
						break;
					default:
						break;
				}
				break;
			case "2":
				InterfaceUtilisateur.afficher("1. Valider suppression.");
				InterfaceUtilisateur.afficher("2. Retour au menu principal.");
				String validationSuppression = InterfaceUtilisateur.getTexteConsole();
				switch (validationSuppression) {
					case "1":
						centreDonnees.supprimerMembre(idMembre);
						InterfaceUtilisateur.afficher("Memebre supprimé.");
						resetEnFinDeTransaction();
						break;
					case "2":
						InterfaceUtilisateur.afficherMenuPrincipal();
					default:
						break;
				}
			case "3":
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}

		private void formulaireNouveauCompte () {
			InterfaceUtilisateur.clearScreen();
			String entree2;

			String typeClient;
			String nom;
			LocalDate dateNaissance = null;
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

		private void afficherAutorisationProfessionnel () {
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

		private void afficherAutorisationMembre () {
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

		private boolean validerMembre (String id){
			return centreDonnees.membreEstValide(id);
		}

		private boolean validerProfessionnel (String id){
			return centreDonnees.professionnelEstValide(id);
		}

		private boolean nomValide (String entree){
			return entree.length() >= 1;
		}

		private boolean dateValide (String entree){
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			try {
				format.parse(entree);
				return true;
			} catch (ParseException e) {
				return false;
			}
		}

		public static LocalDate getDateFromString (String stringDate) throws ParseException {
			return LocalDate.parse(stringDate, CentreDonnees.localDateFormatter);
		}

		public static LocalDateTime getDateEtHeureFromString (String stringDate) throws ParseException {
			return LocalDateTime.parse(stringDate, CentreDonnees.localDateTimeFormatter);
		}

		public static LocalTime getHoraire (String stringHoraire){
			return LocalTime.parse(stringHoraire, CentreDonnees.localTimeFormatter);
		}

		public int getInt (String stringInt){
			return Integer.parseInt(stringInt);
		}

		public double getDouble (String stringDouble){
			return Double.parseDouble(stringDouble);
		}

		private void inscrireClient (String typeClient, String nom, LocalDate dateNaissance, String adresseCourriel, String
		numeroPhone, String adresse){
			Client client = null;
			switch (typeClient) {
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

		private void resetEnFinDeTransaction () {
			InterfaceUtilisateur.afficher("Appuyez sur ENTREE pour revenir à l'écran principal");
			InterfaceUtilisateur.getTexteConsole();
		}

		public boolean adresseValide (String entree){
			return entree.length() >= 1;
		}

		public boolean telephoneValide (String entree){
			String regexPattern = "\\d{3}-\\d{3}-\\d{4}";
			return entree.matches(regexPattern);
		}

		public boolean courrielValide (String entree){
			String regexPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			return entree.matches(regexPattern);
		}

		public boolean typeMembreValide (String entree){
			return entree.equals("1") || entree.equals("2") || entree.equals("3");
		}


		// public void genererRapportAutomatique() { }

		// public void creerRapportSynthese() { }

		// public void creerTEF() { }

}
