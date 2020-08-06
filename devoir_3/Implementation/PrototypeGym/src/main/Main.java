package main;

import main.vue.*;

/**
 * Classe Main qui contient le point d'entrée du programme
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Main {

    /**
     * Point d'entrée du programme qui instancie la vue de choix de plateforme
     * @param args paramètres d'entrée du programme (aucun a priori)
     */
    public static void main(String[] args) {
    	VuePlateforme vue = VueChoixPlateforme.selectionnerOption();
        if (vue != null) {
            vue.start();
        }
    }
}
