package main.controleur;

import main.modele.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ControleurClient extends Controleur {

	private static CentreDonneesMembre centreDonneesMembre = new CentreDonneesMembre();
	private static CentreDonneesProfessionnel centreDonneesProfessionnel = new CentreDonneesProfessionnel();

	public ControleurClient() {
		centreDonneesMembre = new CentreDonneesMembre();
		centreDonneesProfessionnel = new CentreDonneesProfessionnel();
	}

	public void creerClient(TypeClient typeClient,
							String nom,
							String dateNaissanceString,
							String adresseCourriel,
							String numeroTelephone,
							String adresse,
							String ville,
							String province,
							String codePostal) {
		LocalDate dateNaissance = Verificateurs.getDateFromString(dateNaissanceString);
		switch (typeClient) {
			case MEMBRE_VALIDE, MEMBRE_SUSPENDU -> {
				Membre membre = new Membre(nom, dateNaissance, adresse, ville, province, codePostal, numeroTelephone,
						adresseCourriel, typeClient.equals(TypeClient.MEMBRE_VALIDE));
				centreDonneesMembre.creer(membre);
			}
			case PROFESSIONNEL -> {
				Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, ville, province,
						codePostal, numeroTelephone, adresseCourriel);
				centreDonneesProfessionnel.creer(professionnel);
			}
		}
	}

	public void mettreClientAJour(TypeClient typeClient, String idClient, Champs champs, String valeur) {
		if (typeClient == TypeClient.MEMBRE) {
			centreDonneesMembre.mettreAJour(idClient, champs, valeur);
		} else if (typeClient == TypeClient.PROFESSIONNEL) {
			centreDonneesProfessionnel.mettreAJour(idClient, champs, valeur);
		}
	}

	public void supprimerClient(TypeClient typeClient, String idClient) {
		if (typeClient == TypeClient.MEMBRE) {
			centreDonneesMembre.supprimer(idClient);
		} else if (typeClient == TypeClient.PROFESSIONNEL) {
			centreDonneesProfessionnel.supprimer(idClient);
		}
	}

	public static boolean authentifier(TypeClient typeClient, String idClient) {
		Client client = null;
		if (typeClient.equals(TypeClient.MEMBRE)) {
			client = centreDonneesMembre.lire(idClient);
		} else if (typeClient.equals(TypeClient.PROFESSIONNEL)) {
			client = centreDonneesProfessionnel.lire(idClient);
		}
		return client != null;
	}

	public static String seConnecterApp(TypeClient typeClient, String adresseCourriel) {
		if (typeClient.equals(TypeClient.MEMBRE)) {
			return centreDonneesMembre.getIdDepuisAdresse(adresseCourriel);
		} else if (typeClient.equals(TypeClient.PROFESSIONNEL)) {
			return centreDonneesProfessionnel.getIdDepuisAdresse(adresseCourriel);
		}
		return null;
	}

	public static TypeClient verifierTypeClient(TypeClient typeClient, String idClient) {
		if (typeClient == TypeClient.MEMBRE) {
			Membre membre = centreDonneesMembre.lire(idClient);
			if (membre != null) {
				return membre.getAPaye() ? TypeClient.MEMBRE_VALIDE : TypeClient.MEMBRE_SUSPENDU;
			}
		}
		if (typeClient == TypeClient.PROFESSIONNEL) {
			Professionnel professionnel = centreDonneesProfessionnel.lire(idClient);
			if (professionnel != null) {
				return TypeClient.PROFESSIONNEL_VALIDE;
			}
		}
		return TypeClient.CLIENT_INVALIDE;
	}
	
	public static String nomClient(String idClient) {
		Client client = centreDonneesMembre.lire(idClient);
		return client.getNom();
	}

	public String getInformationsClient(TypeClient typeClient, String idClient) {
		Client client = null;
		String infos = "";
		if (typeClient.equals(TypeClient.MEMBRE)) {
			client = centreDonneesMembre.lire(idClient);
		} else if (typeClient.equals(TypeClient.PROFESSIONNEL)) {
			client = centreDonneesProfessionnel.lire(idClient);
		}
		if (client != null) {
			infos = "ID : " + idClient + "\n" + client.toString();
		}
		return infos;
	}

	public String getListeClients(TypeClient typeClient) {
		List<Client> clients = null;
		if (typeClient == TypeClient.MEMBRE) {
			clients = centreDonneesMembre.getClients();
		} else if (typeClient == TypeClient.PROFESSIONNEL) {
			clients = centreDonneesProfessionnel.getClients();
		}

		StringBuilder clientsString = new StringBuilder();

		if (clients != null && clients.size() != 0) {
			for (Client c : clients) {
				clientsString.append(c.getHashInString()).append("; ");
			}
		}
		return clientsString.toString();
	}

	public Client lireClient(String idClient) {
		return centreDonneesMembre.lire(idClient);
	}
	
	public String getIdDepuisAdresse(String adresseCourriel) {
		String idClient = centreDonneesMembre.getIdDepuisAdresse(adresseCourriel);
		if(idClient==null)
			idClient = centreDonneesProfessionnel.getIdDepuisAdresse(adresseCourriel);
		return idClient;
	}

	public static HashMap<String, Professionnel> getListeProfessionnels() {
		return centreDonneesProfessionnel.getListeProfessionnels();
	}

	public void modifierStatutMembre(HashMap<String, Boolean> listeValidations) {
		centreDonneesMembre.modifierStatutMembres(listeValidations);
	}
}
