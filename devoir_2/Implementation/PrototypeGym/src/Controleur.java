import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Controleur {
	private Membre[] listeMembres;
	private Professionnel[] listeProfessionnels;
	private Service[] listeServicesSemaine;


	public Controleur() {
	}

	public void start() {
		InterfaceUtilisateur.afficherMenuPrincipal();
		InterfaceUtilisateur.ReadCommandLine();
	}

	public boolean input(NomsMenus menu, String entreeConsole) {
		String entreeMajuscule = entreeConsole.toUpperCase();
		if (entreeMajuscule.equals("X")) {
			System.exit(0);
		}

		switch (menu) {
			case PRINCIPAL:
				gererMenuPrincipal(entreeMajuscule);
				break;
			case ACCES_GYM:
				return gererMenuAccesGym(entreeMajuscule);
			case GESTION_COMPTE:
				gererGestionCompte(entreeMajuscule);
				break;
			case GESTION_SERVICE:
				gererGestionService(entreeMajuscule);
				break;
			case INSCRIPTION_SEANCE:
				gererInscriptionSeance(entreeMajuscule);
				break;
			case CONFIRMATION_PRESENCE:
				gererConfirmationPresence(entreeMajuscule);
				break;
			case CONSULTATION_SEANCE:
				gererConsultationSeance(entreeMajuscule);
				break;
			case PROCEDURE_COMPTABLE:
				gererProcedureComptable(entreeMajuscule);
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

	private void gererGestionService(String entree) {
		switch (entree) {
			case "1":

				break;
			default:
				break;
		}
	}

	private void gererGestionCompte(String entree) {
		switch (entree) {
			case "1":
				formulaireInscription();
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

	private void formulaireInscription() {
		InterfaceUtilisateur.clearScreen();
		String entree2;

		String typeClient;
		String nom;
		Date dateNaissance = null;
		String adresseCourriel = null;
		String numeroTelephone = null;
		String adresse = null;

		InterfaceUtilisateur.afficher("------Formulaire d'inscription------");

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer le nom :");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!nomValide(entree2));
		nom = entree2;

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer la date de naissance (jj-mm-aaaa):");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!dateNaissanceValide(entree2));
		try {
			dateNaissance = getDateFromString(entree2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		do {
			InterfaceUtilisateur.afficher("Veuillez entrer l'adresse :");
			entree2 = InterfaceUtilisateur.getTexteConsole();
		} while (!adressevalide(entree2));
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

	private void gererMenuPrincipal(String entree) {
		switch (entree) {
			case "1":
				InterfaceUtilisateur.afficherDemandeAcces();
				break;
			case "2":
				InterfaceUtilisateur.afficherGestionCompte();
				break;
			case "3":
				break;
			case "4":
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
				InterfaceUtilisateur.afficherMenuPrincipal();
				break;
			default:
				return false;
		}
		return true;
	}

	private void verifierAutorisationProfessionnel() {
		String idProfessionnel;
		do {
			InterfaceUtilisateur.afficher("Entrez l'identifiant du professionnel puis appuyez sur ENTREE :");
			idProfessionnel = InterfaceUtilisateur.getTexteConsole();
		} while (idProfessionnel.length() != 9);
		if (Professionnel.listeProfessionnels.containsKey(idProfessionnel)) {
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
		if (Membre.listeMembres.containsKey(idMembre)) {
			InterfaceUtilisateur.afficher("Le membre est autorisé à accéder au gym.");
		} else {
			InterfaceUtilisateur.afficher("Le membre n'est pas autorisé à accéder au gym.");
		}
	}


	public boolean validerNumeroClient() {
		return true; //todo
	}
	
	public void genererRapportAutomatique() {
		
	}
	
	public void creerRapportSynthese() {
		
	}
	
    public void creerTEF() {
		
	}


	public boolean nomValide(String entree) {
		return entree.length() >= 1;
	}

	public boolean dateNaissanceValide(String entree) {
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

	public void inscrireClient(String typeMembre, String nom, Date dateNaissance, String adresseCourriel, String numeroPhone, String adresse) {
		Client client = null;
		switch (typeMembre) {
			case "1":
				Membre membreValide = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, true);
				Membre.listeMembres.put(membreValide.getNumero(), membreValide);
				client = membreValide;
				break;
			case "2":
				Membre membreSuspendu = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, false);
				break;
			case "3":
				Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, numeroPhone, adresseCourriel);
				Professionnel.listeProfessionnels.put(professionnel.getNumero(), professionnel);
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
		InterfaceUtilisateur.getTexteConsole(); // TODO peut-être en trop, à vérifier
		InterfaceUtilisateur.afficherMenuPrincipal();
	}

	public boolean adressevalide(String entree) {
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
}
