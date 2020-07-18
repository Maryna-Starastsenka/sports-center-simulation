import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Membre extends Client {

	public static HashMap<String, Membre> listeMembres = new HashMap<>();
//	public static HashMap<String, Membre> listeMembresSuspendus = new HashMap<>();
	private boolean membreValide;

	//private String fichier;
	//private Inscription[] listeInscriptions;
	//private ConfirmationPresence[] listeConfirmations;
	
	public Membre(String nom, Date dateNaissance, String adresse, String numeroPhone, String adresseCourriel, boolean membreValide) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
		this.membreValide = membreValide;
	}

	@Override
	public String toString() {
		return "Membre numéro : " + getNumero() + ".";
	}

	//public boolean validerMembre(){
	//	return this.membreValide;
	//}
	
	//public void ajouterInscription(Inscription inscription) { }
	
    //public void ajouterConfirmation(ConfirmationPresence confirmation) { }
    
    //public void validerOuSuspendre(boolean bool) { }
	
}
