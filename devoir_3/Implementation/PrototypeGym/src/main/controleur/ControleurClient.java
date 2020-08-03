package main.controleur;

import main.modele.*;
import main.vue.*;

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
		Client client = null;
		switch (typeClient) {
			case MEMBRE_VALIDE: // membre qui a payé les frais d'adhésion
			case MEMBRE_SUSPENDU: // membre qui n'a pas payé les frais d'adhésion
				Membre membre = new Membre(nom, dateNaissance, adresse, ville, province, codePostal, numeroTelephone, adresseCourriel, typeClient.equals("1") ? true : false);
				client = centreDonneesMembre.creer(membre);
				break;
			case PROFESSIONNEL:
				Professionnel professionnel = new Professionnel(nom, dateNaissance, adresse, ville, province, codePostal, numeroTelephone, adresseCourriel);
				client = centreDonneesProfessionnel.creer(professionnel);
				break;
		}
//		if (typeClient.equals(TypeClient.MEMBRE_VALIDE) || typeClient.equals(TypeClient.PROFESSIONNEL)) {
//			vueClient.confirmerEnregistrement(client.getHashInString()); // TODO à gérer, surtout avec les membres suspendus
//		}
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
		String idClient = null;
		if (typeClient.equals(TypeClient.MEMBRE)) {
			return idClient = (centreDonneesMembre.getIdDepuisAdresse(adresseCourriel));
		} else if (typeClient.equals(TypeClient.PROFESSIONNEL)) {
			return idClient = (centreDonneesProfessionnel.getIdDepuisAdresse(adresseCourriel));
		}
		return idClient;
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
		return typeClient.CLIENT_INVALIDE;
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
			infos = "ID : " + client.getHashInString() + "\n" +
					"Nom : " + client.getNom() + "\n" +
					"Date de naissance : " + client.getDateNaissance() + "\n" +
					"Adresse courriel : " + client.getAdresseCourriel() + "\n" +
					"Numéro de téléphone : " + client.getNumeroPhone() + "\n" +
					"Adresse : " + client.getAdresse() + "\n";
		}
		if (typeClient.equals(TypeClient.MEMBRE)) {
			Membre membre = (Membre)client;
			infos += "Statut : " + (membre.getAPaye() ? "valide (a payé)\n" : "suspendu\n");
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

		String clientsString = "";

		if (clients.size() != 0) {
			for (Client c : clients) {
				clientsString += c.getHashInString() + "; ";
			}
		}
		return clientsString;
	}

	public Client lireClient(String idClient) {
		return this.centreDonneesMembre.lire(idClient);
	}
	
	public String getIdDepuisAdresse(String adresseCourriel) {
		return this.centreDonneesMembre.getIdDepuisAdresse(adresseCourriel);
	}

	public static HashMap<String, Professionnel> getListeProfessionnels() {
		return centreDonneesProfessionnel.getListeProfessionnels();
	}
	
	public static HashMap<String, Membre> getListeMembres() {
		return centreDonneesMembre.getListeMembres();
	}
	
	public void modifierStatutMembre(HashMap<String, Boolean> listeValidations) {
		centreDonneesMembre.modifierStatutMembres(listeValidations);
	}
	
//public boolean validerMembre (String id){
//		return centreDonneesMembre.estMembre(id);
//}
//
//	public boolean membrePasSuspendu (String id) {
//		return centreDonneesClient.estMembreValide(id);
//	}
//
//	public boolean validerProfessionnel (String id){
//		return centreDonneesClient.estProfessionnel(id);
//	}
//
//	public HashMap<String, Professionnel> getHashMapProfessionnel(){
//    	return centreDonneesClient.getHashMapProfessionnel();
//    }
//
}
