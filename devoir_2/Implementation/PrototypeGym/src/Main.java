public class Main {

    public static void main(String[] args) {
		Controleur controleur = new Controleur();
		InterfaceUtilisateur.controleur = controleur;
		controleur.start();
    }
}
