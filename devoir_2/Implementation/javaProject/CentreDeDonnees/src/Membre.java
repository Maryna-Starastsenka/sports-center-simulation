
public class Membre extends Client {
	private Inscription[] listeInscriptions;
	private ConfirmationPresence[] listeConfirmations;
	private int numeroMembre;
	private boolean membreValide;
	private String fichier;
	
	
	public Membre() {
		
	}
	
	public boolean validerMembre(){
		return this.membreValide;
	}
	
	public void ajouterInscription(Inscription inscription) {
		
		
	}
	
    public void ajouterConfirmation(ConfirmationPresence confirmation) {
		
		
	}
    
    public void validerOuSuspendre(boolean bool) {
    	
    	
    }
	
}
