import javax.swing.plaf.PanelUI;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Membre extends Client {

	private boolean membreValide;

	//private String fichier;
	//private Inscription[] listeInscriptions;
	//private ConfirmationPresence[] listeConfirmations;
	
	public Membre(String nom, LocalDate dateNaissance, String adresse, String numeroPhone, String adresseCourriel, boolean membreValide) {
		this.nom = nom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroPhone = numeroPhone;
		this.adresseCourriel = adresseCourriel;
		this.membreValide = membreValide;
	}

	public boolean getMembreValide() {return membreValide; }

	public void setMembreValide(boolean membreValide) { this.membreValide = membreValide; }

	@Override
	public String toString() {
		return "Membre numéro : " + getNumero() + ".";
	}

	//public boolean validerMembre(){ return this.membreValide; }
	
	//public void ajouterInscription(Inscription inscription) { }
	
    //public void ajouterConfirmation(ConfirmationPresence confirmation) { }
    
    //public void validerOuSuspendre(boolean bool) { }
	
}
