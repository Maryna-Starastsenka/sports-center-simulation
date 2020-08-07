package main.controleur;

import main.modele.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Classe ControleurClient qui hérite de la classe Controleur. Gère le flux de données du modèle client (membre et
 * professionnel) et met à jour la vue lorsque les données changent
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class ControleurClient extends Controleur {

	private static CentreDonneesMembre centreDonneesMembre = new CentreDonneesMembre();
	private static CentreDonneesProfessionnel centreDonneesProfessionnel = new CentreDonneesProfessionnel();

	/**
	 * Constructeur de ControleurClient qui instancie les centres de données (modèle)
	 */
	public ControleurClient() {
		centreDonneesMembre = new CentreDonneesMembre();
		centreDonneesProfessionnel = new CentreDonneesProfessionnel();
	}

	/**
	 * Instanciation du client et enregistrement dans le centre de données
	 * @param typeClient tyde de client
	 * @param nom nom du client
	 * @param dateNaissanceString date de naissance du client
	 * @param adresseCourriel adresse courriel du client
	 * @param numeroTelephone numéro de téléphone du client
	 * @param adresse adresse du client
	 * @param ville ville du client
	 * @param province province du client
	 * @param codePostal code postal du client
	 */
	public void creerClient(TypeClient typeClient,
							String nom,
							String dateNaissanceString,
							String adresseCourriel,
							String numeroTelephone,
							String adresse,
							String ville,
							String province,
							String codePostal) {
		LocalDate dateNaissance = Helper.getDateFromString(dateNaissanceString);
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

	/**
	 * Demande au Centre de données de mettre à jour les informations sur le client
	 * @param typeClient type de client
	 * @param idClient numéro unique du client
	 * @param champs type d'information à modifier
	 * @param valeur nouvelle valeur
	 */
	public void mettreClientAJour(TypeClient typeClient, String idClient, Champs champs, String valeur) {
		if (typeClient == TypeClient.MEMBRE) {
			centreDonneesMembre.mettreAJour(idClient, champs, valeur);
		} else if (typeClient == TypeClient.PROFESSIONNEL) {
			centreDonneesProfessionnel.mettreAJour(idClient, champs, valeur);
		}
	}

	/**
	 * Demande au Centre de données de supprimer le client
	 * @param typeClient type de client
	 * @param idClient numéro unique du client
	 */
	public void supprimerClient(TypeClient typeClient, String idClient) {
		if (typeClient == TypeClient.MEMBRE) {
			centreDonneesMembre.supprimer(idClient);
		} else if (typeClient == TypeClient.PROFESSIONNEL) {
			centreDonneesProfessionnel.supprimer(idClient);
		}
	}

	/**
	 * Demande au Centre de données de retourner le client associé au numéro unique
	 * @param typeClient type de client
	 * @param idClient numéro unique du client
	 * @return client possédant un numéro unique d'identification. Si le client n'est pas retrouvé, retourne null.
	 */
	public static boolean authentifier(TypeClient typeClient, String idClient) {
		Client client = null;
		if (typeClient.equals(TypeClient.MEMBRE)) {
			client = centreDonneesMembre.lire(idClient);
		} else if (typeClient.equals(TypeClient.PROFESSIONNEL)) {
			client = centreDonneesProfessionnel.lire(idClient);
		}
		return client != null;
	}

	/**
	 * Demande au Centre de données de retourner le numéro unique du client assosié à son adresse courriel
	 * @param typeClient tyde de client
	 * @param adresseCourriel adresse courriel de client
	 * @return numéro unique du client si son adresse courriel est retrouvée. Sinon, retourne null
	 */
	public static String seConnecterApp(TypeClient typeClient, String adresseCourriel) {
		if (typeClient.equals(TypeClient.MEMBRE)) {
			return centreDonneesMembre.getIdDepuisAdresse(adresseCourriel);
		} else if (typeClient.equals(TypeClient.PROFESSIONNEL)) {
			return centreDonneesProfessionnel.getIdDepuisAdresse(adresseCourriel);
		}
		return null;
	}

	/**
	 * Demande au Centre de données de vérifier le type de membre en utilisant son numéro unique
	 * @param typeClient type de client (membre ou professionnel)
	 * @param idClient numéro unique du client
	 * @return type de client
	 */
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

	/** Obtenir le nom du membre associé à son numéro unique
	 * @param idMembre numéro unique du membre
	 * @return nom du membre
	 */
	public static String nomClient(String idMembre) {
		Client client = centreDonneesMembre.lire(idMembre);
		return client.getNom();
	}
	
	/** Obtenir le nom du professionnel associé à son numéro unique
	 * @param idProfessionnel numéro unique du professionnel
	 * @return nom du professionnel
	 */
	public static String nomProfessionnel(String idProfessionnel) {
		Client client = centreDonneesProfessionnel.lire(idProfessionnel);
		return client.getNom();
	}

	/**
	 * Obtenir de l'information sur le client en utilisant son numéro unique
	 * @param typeClient type de client
	 * @param idClient numéro unique du client
	 * @return information sur le client
	 */
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

	/**
	 * Obtenir la liste des clients existants selon leur type
	 * @param typeClient type de client
	 * @return liste des clients
	 */
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

	/**
	 * Demande au Centre de données de retourné le membre qui a le numéro unique spécifié
	 * @param idMembre numéro unique du membre
	 * @return membre recherché
	 */
	public Client lireMembre(String idMembre) {
		return centreDonneesMembre.lire(idMembre);
	}
	
	/**
	 * Demande au Centre de données de retourné le professionnel qui a le numéro unique spécifié
	 * @param idProfessionnel numéro unique du professionnel
	 * @return professionnel recherché
	 */
	public Client lireProfessionnel(String idProfessionnel) {
		return centreDonneesProfessionnel.lire(idProfessionnel);
	}

	/**
	 * Demande au Centre de données de retourner le numéro unique du client assosié à son adresse courriel
	 * @param adresseCourriel adresse courriel du client
	 * @return numéro unique du client
	 */
	public String getIdDepuisAdresse(String adresseCourriel) {
		String idClient = centreDonneesMembre.getIdDepuisAdresse(adresseCourriel);
		if(idClient==null)
			idClient = centreDonneesProfessionnel.getIdDepuisAdresse(adresseCourriel);
		return idClient;
	}

	/**
	 * Demande au Centre de données Professionnel de retourner la liste des professionnels existants
	 * @return liste des professionnels
	 */
	public static HashMap<String, Professionnel> getListeProfessionnels() {
		return centreDonneesProfessionnel.getListeProfessionnels();
	}
	
	/**
	 * Demande au Centre de données Professionnel de retourner la liste des professionnels existants
	 * @return liste des professionnels
	 */
	public static HashMap<String, Membre> getListeMembres() {
		return centreDonneesMembre.getListeMembres();
	}

	/** Demande de modifier le statut des membres (suspendu ou non) spécifiés dans la liste
	 * @param listeValidations liste des membres avec le statut à modifier
	 */
	public void modifierStatutMembre(HashMap<String, Boolean> listeValidations) {
		centreDonneesMembre.modifierStatutMembres(listeValidations);
	}
}
