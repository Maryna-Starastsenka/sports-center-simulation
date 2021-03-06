import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Controleur {
	private CentreDonnees centreDonnees;

	public Controleur() {
		centreDonnees = new CentreDonnees();
	}

	public void start() {
		String entree;
		do {
			Gui.afficherMenuPrincipal();

			entree = Gui.getTexteConsole().toUpperCase();
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
				Gui.afficherDemandeAcces();
				entreeSecondaire = Gui.getTexteConsole();
				if (!Arrays.asList("1", "2", "3", "X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererMenuAccesGym(entreeSecondaire);
				}
				break;
			case "2":
				Gui.afficherGestionCompte();
				entreeSecondaire = Gui.getTexteConsole();
				if (!Arrays.asList("1", "2", "3", "X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererGestionCompte(entreeSecondaire);
				}

				break;
			case "3":
				Gui.afficher("--------Gestion d'un service-------");

				afficherTousLesProfessionnels();

				Gui.afficher("Veuillez entrer le numéro de professionnel");

				String idProfessionnel;
				do {
					idProfessionnel = Gui.getTexteConsole();
				} while (!validerProfessionnel(idProfessionnel) /*|| fermetureApplicationDemandee(idProfessionnel)*/);

				Gui.afficherGestionService();

				entreeSecondaire = Gui.getTexteConsole();
				if (!Arrays.asList("1", "2", "3", "X").contains(entreeSecondaire)) {
					gererMenuPrincipal(entreePrincipale);
				} else {
					gererGestionService(entreeSecondaire, idProfessionnel);
				}
				break;
			case "4":
				Gui.afficher("---Inscription à une séance---");

				Gui.afficher("Références des séances disponibles ajourd'hui, le " + CentreDonnees.today() + " :");
				afficherToutesLesSeancesDuJour(CentreDonnees.today());

				Gui.afficher("Veuillez entrer la référence de la séance à laquelle vous voulez inscrire un membre ou appuyez sur ENTREE pour revenir au menu principal :");
				String seanceId = Gui.getTexteConsole();
				if (seanceId.equals("")) {
					break;
				}

				afficherTousLesMembres();
				Gui.afficher("Veuillez entrer le numéro du membre :");
				String membreId = Gui.getTexteConsole();

				if (!centreDonnees.estMembre(membreId)) {
					Gui.afficher("Membre inconnu. Retour au menu principal.");
					break;
				} else if (!centreDonnees.getMembre(membreId).getMembreValide()) {
					Gui.afficher("Membre suspendu. Retour au menu principal.");
					break;
				} else {

					Seance seance = centreDonnees.getSeance(seanceId);
					Service service = centreDonnees.getService(seance.getCodeService());
					Gui.afficher("Les frais à payer pour la séance sont de : " + service.getFraisService() + "$");

					Gui.afficher("1. Continuer inscription");
					Gui.afficher("2. Quitter et revenir au menu principal");
					entreeSecondaire = Gui.getTexteConsole();
					switch (entreeSecondaire) {
						case "1":
							Gui.afficher("Le paiement est-il valide ?");
							Gui.afficher("1. Oui");
							Gui.afficher("2. Non");
							entreeSecondaire = Gui.getTexteConsole();

							if (entreeSecondaire.equals("1")) {
								Gui.afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
								String commentaire = Gui.getTexteConsole();

								centreDonnees.inscrireMembreASeance(membreId, seanceId, commentaire);

								Gui.afficher("Le membre " +
										membreId +
										" a été inscrit à la séance " +
										seanceId +
										" qui aura lieu le " +
										CentreDonnees.localDateTimeFormatter.format(centreDonnees.getSeance(seanceId).getDateTimeSeance()));
							} else {
								Gui.afficher("Annulation de l'inscription. Retour au menu principal.");
							}

							break;
						case "2":
							break;
						default:
							break;
					}
				}
				break;
			case "5":
				Gui.afficher("---Confirmation de la présence---");

				Gui.afficher("Références des séances disponibles ajourd'hui, le " + CentreDonnees.today() + " :");
				afficherToutesLesSeancesDuJour(CentreDonnees.today());

				Gui.afficher("Veuillez entrer la référence de la séance à laquelle vous voulez inscrire un membre ou appuyez sur ENTREE pour revenir au menu principal :");
				seanceId = Gui.getTexteConsole();
				if (seanceId.equals("")) {
					break;
				}

				afficherTousLesMembres();
				Gui.afficher("Veuillez entrer le numéro du membre :");
				membreId = Gui.getTexteConsole();

				if (!centreDonnees.estMembre(membreId)) {
					Gui.afficher("Membre inconnu.");
					break;
				} else if (!centreDonnees.inscriptionExiste(membreId, seanceId)) {
					Gui.afficher("Le membre n'est pas inscrit. Accès refusé.");
				} else {
					Gui.afficher("Confirmer la présence ?");
					Gui.afficher("1. Oui");
					Gui.afficher("2. Non");
					entreeSecondaire = Gui.getTexteConsole();

					if (entreeSecondaire.equals("1")) {
						Gui.afficher("Veuillez entrer un commentaire (appuyez sur ENTREE si vous le ne souhaitez pas) :");
						String commentaire = Gui.getTexteConsole();

						centreDonnees.confirmationPresence(seanceId, membreId, commentaire);
						Gui.afficher("Présence validée.");

					} else {
						Gui.afficher("Annulation de la confirmation.");
						break;
					}
				}

				break;
			case "6":
				Gui.afficher("---Consultation d'une séance---");
				afficherTousLesProfessionnels();
				Gui.afficher("Veuillez entrer le numéro du professionnel.");
				entreeSecondaire = Gui.getTexteConsole();

				if (centreDonnees.estProfessionnel(entreeSecondaire)) {
				Gui.afficher("Inscriptions aux séances du professionnel " + entreeSecondaire);
				afficherToutesLesInscriptionDuPro(entreeSecondaire);
				} else {
					Gui.afficher("Numéro du professionnel incorrect.");
				}
				break;
			case "7":
				Gui.afficher("---Procédure comptable---");
				Gui.afficher("Voulez-vous générer et afficher le rapport de synthèse ?");
				Gui.afficher("1. Oui");
				Gui.afficher("2. Non");
				entreeSecondaire = Gui.getTexteConsole();

				if (entreeSecondaire.equals("1")) {
					var rapport = centreDonnees.genererRapportSynthese();
					Gui.afficher("Liste des professionnels à payer :");

					for (ProfessionnelTef pro : rapport.getProTef()) {
						Gui.afficher(String.format("-%s (%s) doit recevoir %.2f$ pour les %s services qu'il a donnés cette semaine.",
								pro.getNom(),
								pro.getNumero(),
								pro.getMontant(),
								pro.getNombreServices()));
					}
					Gui.afficher("\n* Nombre total de professionnels à payer : " + rapport.getNombreTotalProfessionnels());
					Gui.afficher("* Nombre total de services : " + rapport.getNombreTotalServices());
					Gui.afficher("* Nombre total des frais à payer : " + rapport.getTotalFrais() + "$");

					Gui.afficher("\nUne copie du rapport a été envoyée au gérant.");

				} else {
					Gui.afficher("Annulation de la génération du rapport de synthèse.");
					break;
				}
				break;
			default:
				break;
		}
		resetEnFinDeTransaction();
	}

	private void afficherToutesLesInscriptionDuPro(String idProfessionnel) {
		var inscriptions = centreDonnees.getListeInscriptionsPro(idProfessionnel);
		if (inscriptions.size() == 0) {
			Gui.afficher("Aucune inscription");
		} else {
			for (Inscription i : inscriptions) {
				Gui.afficher("Séance " + i.getHashInString() + " date " + i.getDateSeance() + " membre "
						+ i.getNumeroMembre() + ". Commentaire : " + i.getCommentaires());
			}
		}
	}

	private void afficherToutesLesSeancesDuJour(LocalDate jour) {
		var seances = centreDonnees.getListeSeances(jour);
		for (Seance s : seances) {
			Gui.afficher(s.getHashInString() + " (service de " +
					centreDonnees.getService(s.getCodeService()).getNomService() +
					") : le " +
					CentreDonnees.localDateTimeFormatter.format(s.getDateTimeSeance()));
		}
	}

	private void afficherTousLesMembres() {
		Gui.afficher("Numéros des membres du centre de données (pour faciliter les tests) :");
		var membres = centreDonnees.getListeMembres();
		for (Membre m : membres) {
			Gui.afficher(m.getHashInString());
		}
	}

	private void afficherTousLesProfessionnels() {
		Gui.afficher("Numéros des professionnels du centre de données (pour faciliter les tests) :");
		var professionnels = centreDonnees.getListeProfessionnels();
		for (Professionnel m : professionnels) {
			Gui.afficher(m.getHashInString());
		}
	}

	private void gererMenuAccesGym(String entree) {
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

	private void gererGestionService(String entree, String idProfessionnel) {
		switch (entree) {
			case "1":
				formulaireNouveauService(idProfessionnel);
				break;
			case "2":
				List<Service> servicesDuProfessionnel = centreDonnees.getListeServicesPro(idProfessionnel);
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
		Gui.effacerEcran();

		Gui.afficher("------Services du professionnel------");
		Gui.afficher("Liste des services disponibles pour ce professionnel :");
		for (Service s : servicesDuProfessionnel) {
			Gui.afficher(s.getCode() + " (" + s.getNomService() + ")");
		}
	}

	private void gererServiceExistant() {
		Gui.afficher("Veuillez choisir un service :");
		String serviceEntre = Gui.getTexteConsole();
		Service serviceAModifier = centreDonnees.getService(serviceEntre);

		Gui.afficher("1. Modifier :");
		Gui.afficher("2. Supprimer :");

		String modifOuSuppr = Gui.getTexteConsole();
		switch (modifOuSuppr) {
			case "1":
				Gui.afficher("1. Modifier récurrence hebdo. Valeur actuelle : " + serviceAModifier.getRecurrenceHebdo());// todo faire les autres
				Gui.afficher("2. Modifier heure du service. Valeur actuelle : " + serviceAModifier.getHeureService());
				String modifChamps = Gui.getTexteConsole();
				switch (modifChamps) {
					case "1":
						Gui.afficher("Entrez la nouvelle valeur :");
						String nouvelleRecurrence = Gui.getTexteConsole();
						serviceAModifier.setRecurrenceHebdo(nouvelleRecurrence);
						Gui.afficher("Service modifié.");
						break;

					case "2":
						Gui.afficher("Entrez la nouvelle valeur :");
						String entree = Gui.getTexteConsole();
						LocalTime nouvelleHeure = getHoraire(entree);
						serviceAModifier.setHeureService(nouvelleHeure);
						Gui.afficher("Service modifié.");
						break;
					default:
						break;
				}

				break;
			case "2":
				centreDonnees.supprimerService(serviceEntre);
				Gui.afficher("Service " + serviceEntre + " supprimé.");
				break;
			default:
				break;
		}
	}

	private void formulaireNouveauService(String idProfessionnel) {
		Gui.effacerEcran();

		Gui.afficher("------Formulaire de nouveau service------");

		String entree;

		String nomService;
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

		Gui.afficher("Veuillez entrer le nom du service :");
		nomService = Gui.getTexteConsole();

		dateEtHeureActuelles = CentreDonnees.now();

		do {
			Gui.afficher("Veuillez entrer la date de début du service (jj-mm-aaaa) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		dateDebutService = getDateFromString(entree);

		do {
			Gui.afficher("Veuillez entrer la date de fin du service (jj-mm-aaaa) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		dateFinService = getDateFromString(entree);


		do {
			Gui.afficher("Veuillez entrer l'heure du service (hh:mm) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		heureService = getHoraire(entree);

		do {
			Gui.afficher("Veuillez entrer la récurrence hebdomadaire (1-7) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		recurrenceHebdo = getInt(entree);//getHoraire(entree);

		do {
			Gui.afficher("Veuillez entrer la capacité maximale (1-30) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		capaciteMaximale = getInt(entree);

		do {
			Gui.afficher("Veuillez entrer le code du service (XXXXXXX) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		codeService = entree;

		do {
			Gui.afficher("Veuillez entrer les frais du service (XXX.XX) :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		fraisService = getDouble(entree);

		do {
			Gui.afficher("Veuillez entrer les commentaires :");
			entree = Gui.getTexteConsole();
		} while (false); //todo
		commentaires = entree;

		Service service = new Service(nomService,
				dateEtHeureActuelles,
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

		Gui.afficher("Service " + service.getCode() + " enregistré.");
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
		Gui.afficher("Veuillez choisir le type de compte");
		Gui.afficher("1. Compte membre");
		Gui.afficher("2. Compte professionnel");
		String entree = Gui.getTexteConsole();
		switch (entree) {
			case "1":
				afficherTousLesMembres();
				Gui.afficher("Veuillez entrer le numéro du membre");
				String idMembre = Gui.getTexteConsole();
				Membre membre = centreDonnees.getMembre(idMembre);
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
				Professionnel professionnel = centreDonnees.getProfessionnel(idProfessionnel);
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
							centreDonnees.supprimerProfessionnel(idProfessionnel);
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
						centreDonnees.supprimerMembre(idMembre);
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

		private boolean validerMembre (String id){
			return centreDonnees.estMembre(id);
		}

		private boolean validerProfessionnel (String id){
			return centreDonnees.estProfessionnel(id);
		}

		private boolean nomValide (String entree){
			return entree.length() >= 1;
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
					if (!centreDonnees.estMembre(membreValide.getHashInString())) {
						centreDonnees.ajouterMembre(membreValide);
					}
					client = membreValide;
					break;
				case "2":
					Membre membreSuspendu = new Membre(nom, dateNaissance, adresse, numeroPhone, adresseCourriel, false);
					break;
				case "3":
					Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, numeroPhone, adresseCourriel);
					if (!centreDonnees.estProfessionnel(professionnel.getHashInString())) {
						centreDonnees.ajouterProfessionnel(professionnel);
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

		private void resetEnFinDeTransaction () {
			Gui.afficher("Appuyez sur ENTREE pour revenir à l'écran principal");
			Gui.getTexteConsole();
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
}
