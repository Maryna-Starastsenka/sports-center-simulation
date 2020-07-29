import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;

public class ControleurClient {

	private CentreDonneesClient centreDonneesClient;
	
	public ControleurClient() {
		
		this.centreDonneesClient = new CentreDonneesClient();
	}
	
	public void demanderAcces() {
		Gui.afficherDemandeAcces();
		String entree = Gui.getTexteConsole();
		if (Arrays.asList("1", "2", "3", "X").contains(entree)) {
			switch (entree) {
				case "1": // Membre
					afficherAutorisationMembre();
					break;
				case "2": // Professionnel
					afficherAutorisationProfessionnel();
					break;
				case "3": // Retour au menu principal par défaut
					break;
				default:
					break;
			}
		}
	}
	
	
	private void afficherAutorisationProfessionnel () {
		String idProfessionnel;
		do {
			Gui.afficher("Entrez l'identifiant du professionnel puis appuyez sur ENTREE :");
			idProfessionnel = Gui.getTexteConsole();
		} while (idProfessionnel.length() != 9);
		if (validerProfessionnel(idProfessionnel)) {
			Gui.afficher("Le professionnel est autorisé à accéder au gym.");
		} else {
			Gui.afficher("Le professionnel n'est pas enregistré.");
		}
	}

	private void afficherAutorisationMembre () {
		afficherTousLesMembres();
		String idMembre;
		do {
			Gui.afficher("Entrez l'identifiant du membre puis appuyez sur ENTREE :");
			idMembre = Gui.getTexteConsole();
		} while (idMembre.length() != 9);
		if (validerMembre(idMembre)) {
			Gui.afficher("Le membre est autorisé à accéder au gym.");
		} else {
			Gui.afficher("Le membre n'est pas autorisé à accéder au gym.");
		}
	}
	public void gestionCompte() {
		Gui.afficherGestionCompte();
		String entree = Gui.getTexteConsole();
		if (Arrays.asList("1", "2", "3", "X").contains(entree)) {
			switch (entree) {
			case "1":
				formulaireNouveauCompte();
				break;
			case "2":
				gererCompteExistant();
				break;
			case "3": // Retour au menu principal par défaut
				break;
			default:
				break;
			}
		}
	}
	
	private void gererCompteExistant() {
		Gui.afficher("Veuillez choisir le type de compte");
		Gui.afficher("1. Compte membre");
		Gui.afficher("2. Compte professionnel");
		String entree = Gui.getTexteConsole();
		switch (entree) {
			case "1":
				afficherTousLesMembres();
				Gui.afficher("Veuillez entrer le numéro du membre");
				String idMembre = Gui.getTexteConsole();
				Membre membre = centreDonneesClient.getMembre(idMembre);
				String dateFormateMembre = CentreDonnees.localDateFormatter.format(membre.getDateNaissance());

				Gui.afficher("Nom : " + membre.getNom() + "\n" + "Date de naissance : "
						+ dateFormateMembre + "\n" + "Adresse courriel : " + membre.getAdresseCourriel() + "\n"
						+ "Numéro de téléphone : " + membre.getNumeroPhone() + "\n" + "Adresse : " + membre.getAdresse() + "\n"
						+ "Membre valide : " + membre.getMembreValide() + "\n");

				Gui.afficher("Veuillez choisir l'action");
				Gui.afficher("1. Modifier.");
				Gui.afficher("2. Supprimer.");
				String action = Gui.getTexteConsole();
				gererMembreExistant(action, membre, idMembre);
				break;
			case "2":
				Gui.afficher("Veuillez entrer le numéro du professionnel");
				String idProfessionnel = Gui.getTexteConsole();
				Professionnel professionnel = centreDonneesClient.getProfessionnel(idProfessionnel);
				String dateFormateProf = CentreDonnees.localDateFormatter.format(professionnel.getDateNaissance());

				Gui.afficher("Nom : " + professionnel.getNom() + "\n" + "Date de naissance : "
						+ dateFormateProf + "\n" + "Adresse courriel : " + professionnel.getAdresseCourriel() + "\n"
						+ "Numéro de téléphone : " + professionnel.getNumeroPhone() + "\n" + "Adresse : " + professionnel.getAdresse() + "\n");

				Gui.afficher("Veuillez choisir l'action");
				Gui.afficher("1. Modifier.");
				Gui.afficher("2. Supprimer.");
				String actionProf = Gui.getTexteConsole();
				gererProfessionnelExistant(actionProf, professionnel, idProfessionnel);
				break;
			case "3":
				Gui.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}
	
	private void gererMembreExistant(String action, Membre membre, String idMembre) {
		switch (action) {
			case "1":
				Gui.afficher("1. Modifier le statut du membre. Valeur actuelle : " + membre.getMembreValide());// todo faire les autres
				Gui.afficher("2. Modifier le numéro de téléphone.");
				Gui.afficher("3. Retour au menu principal.");
				String modifMembre = Gui.getTexteConsole();

				switch (modifMembre) {
					case "1":
						membre.setMembreValide(!membre.getMembreValide());
						Gui.afficher("Membre modifié.");
						break;
					case "2":
						Gui.afficher("Veuillez entrer le nouveau numéro de téléphone.");
						String nouveauNumeroTel = Gui.getTexteConsole();
						membre.setNumeroPhone(nouveauNumeroTel);
						Gui.afficher("Membre modifié.");
						break;
					case "3":
						Gui.afficherMenuPrincipal();
						break;
					default:
						break;
				}
				break;
			case "2":
				Gui.afficher("1. Valider suppression.");
				Gui.afficher("2. Retour au menu principal.");
				String validationSuppression = Gui.getTexteConsole();
				switch (validationSuppression) {
					case "1":
						centreDonneesClient.supprimerMembre(idMembre);
						Gui.afficher("Membre supprimé.");
						break;
					case "2":
						Gui.afficherMenuPrincipal();
					default:
						break;
				}
			case "3":
				Gui.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}
	
	private void gererProfessionnelExistant (String action, Professionnel professionnel, String idProfessionnel) {
		switch (action) {
			case "1":
				Gui.afficher("1. Modifier l'adresse.");// todo faire les autres
				Gui.afficher("2. Modifier l'adresse courriel.");
				Gui.afficher("3. Retour au menu principal.");
				String modifProfessionnale = Gui.getTexteConsole();

				switch (modifProfessionnale) {
					case "1":
						Gui.afficher("Veuillez entrer la nouvelle addresse.");
						String nouvelleAdresse = Gui.getTexteConsole();
						professionnel.setAdresse(nouvelleAdresse);
						Gui.afficher("Professionnel modifié.");
						break;
					case "2":
						Gui.afficher("Veuillez entrer la nouvelle addresse courriel.");
						String nouvelleAdresseCourriel = Gui.getTexteConsole();
						professionnel.setAdresseCourriel(nouvelleAdresseCourriel);
						Gui.afficher("Membre modifié.");
						break;
					case "3":
						Gui.afficherMenuPrincipal();
						break;
					default:
						break;
				}
				break;
			case "2":
				Gui.afficher("1. Valider suppression.");
				Gui.afficher("2. Retour au menu principal.");
				String validationSuppression = Gui.getTexteConsole();
					switch (validationSuppression) {
						case "1":
							centreDonneesClient.supprimerProfessionnel(idProfessionnel);
							Gui.afficher("Professionnel supprimé.");
							break;
						case "2":
							Gui.afficherMenuPrincipal();
						default:
							break;
					}
			case "3":
				Gui.afficherMenuPrincipal();
				break;
			default:
				break;
		}
	}
	
	private void formulaireNouveauCompte () {
		Gui.effacerEcran();
		String entree2;

		String typeClient;
		String nom;
		LocalDate dateNaissance = null;
		String adresseCourriel = null;
		String numeroTelephone = null;
		String adresse = null;

		Gui.afficher("------Formulaire de nouveau compte------");

		do {
			Gui.afficher("Veuillez entrer le nom :");
			entree2 = Gui.getTexteConsole();
		} while (!nomValide(entree2));
		nom = entree2;

		do {
			Gui.afficher("Veuillez entrer la date de naissance (jj-mm-aaaa):");
			entree2 = Gui.getTexteConsole();
		} while (!dateValide(entree2));
		dateNaissance = getDateFromString(entree2);


		do {
			Gui.afficher("Veuillez entrer l'adresse :");
			entree2 = Gui.getTexteConsole();
		} while (!adresseValide(entree2));
		adresse = entree2;

		do {
			Gui.afficher("Veuillez entrer le numéro de téléphone (XXX-XXX-XXXX):");
			entree2 = Gui.getTexteConsole();
		} while (!telephoneValide(entree2));
		numeroTelephone = entree2;

		do {
			Gui.afficher("Veuillez entrer l'adresse courriel (xxx@xxx.xxx) :");
			entree2 = Gui.getTexteConsole();
		} while (!courrielValide(entree2));
		adresseCourriel = entree2;

		do {
			Gui.afficher("Inscrivez-vous un membre qui a payé les frais d'hadhésion (entrez \"1\"), " +
					"un membre qui n'a pas payé les frais (entrez \"2\"), " +
					"ou un professionnel (entrez \"3\") ?");
			entree2 = Gui.getTexteConsole();
		} while (!typeMembreValide(entree2));
		typeClient = entree2;

		inscrireClient(typeClient, nom, dateNaissance, adresseCourriel, numeroTelephone, adresse);
	}
	
	private void inscrireClient (String typeClient, String nom, LocalDate dateNaissance, String adresseCourriel, String
			numeroPhone, String adresse){
				Client client = null;
				switch (typeClient) {
					case "1":
						Membre membreValide = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, true);
						if (!centreDonneesClient.estMembre(membreValide.getHashInString())) {
							centreDonneesClient.ajouterMembre(membreValide);
						}
						client = membreValide;
						break;
					case "2":
						Membre membreSuspendu = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, false);
						break;
					case "3":
						Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, numeroPhone, adresseCourriel);
						if (!centreDonneesClient.estProfessionnel(professionnel.getHashInString())) {
							centreDonneesClient.ajouterProfessionnel(professionnel);
						}
						client = professionnel;
						break;
					default:
						break;
				}

				if (client != null) {
					Gui.afficher("Enregistrement du " + client);
				} else {
					Gui.afficher("Le client n'a pas été enregistré.");
				}
			}
	

	private boolean nomValide (String entree){
		return entree.length() >= 1;
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
	
	private boolean dateValide (String entree){
		try {
			getDateFromString(entree);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	public static LocalDate getDateFromString (String stringDate) {
		return LocalDate.parse(stringDate, CentreDonnees.localDateFormatter);
	}

	public boolean validerMembre (String id){
		return centreDonneesClient.estMembre(id);
	}
	
	public boolean membrePasSuspendu (String id) {
		return centreDonneesClient.estMembreValide(id);
	}

	public boolean validerProfessionnel (String id){
		return centreDonneesClient.estProfessionnel(id);
	}
	
	public HashMap<String, Professionnel> getHashMapProfessionnel(){
    	return centreDonneesClient.getHashMapProfessionnel();
    }
	
	public void afficherTousLesMembres() {
		Gui.afficher("Numéros des membres du centre de données (pour faciliter les tests) :");
		var membres = centreDonneesClient.getListeMembres();
		for (Membre m : membres) {
			Gui.afficher(m.getHashInString());
		}
	}

	public void afficherTousLesProfessionnels() {
		Gui.afficher("Numéros des professionnels du centre de données (pour faciliter les tests) :");
		var professionnels = centreDonneesClient.getListeProfessionnels();
		for (Professionnel m : professionnels) {
			Gui.afficher(m.getHashInString());
		}
	}
}
