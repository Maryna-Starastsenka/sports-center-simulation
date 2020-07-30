import Controleur.*;
import Vue.*;

public class Main {

    public static void main(String[] args) {
		Controleur controleur = new Controleur();
		Gui.controleur = controleur;
		controleur.start();
    }
}
