package main;

import main.vue.*;

public class Main {

    public static void main(String[] args) {
    	VuePlateforme vue = VueChoixPlateforme.selectionnerOption();
        if (vue != null) {
            vue.start();
        }
    }
}
