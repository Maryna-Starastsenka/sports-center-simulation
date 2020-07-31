import Controleur.*;
import Vue.*;

public class Main {

    public static void main(String[] args) {
    	VuePlateforme vue = VueChoixPlateforme.selectionnerOption();
    	vue.start();
    }
}
