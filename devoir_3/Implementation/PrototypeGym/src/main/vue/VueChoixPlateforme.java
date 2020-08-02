package main.vue;

import java.util.Arrays;

public class VueChoixPlateforme extends Vue {

    public VueChoixPlateforme() {
    }

    public static VuePlateforme selectionnerOption() {
        effacerEcran();
        afficher("Veuillez choisir la plateforme que vous souhaitez lancer :");
        afficher("1. Version agent");
        afficher("2. Version application");


        var reponse = acquisitionReponse(Arrays.asList("1", "2"));
        switch (reponse) {
            case "1" :
                afficher("Lancement de la version agent...");
                return new VueAgent();
            case "2" :
                afficher("Lancement de la version application...");
                return new VueApplicationMobile();
        }

        return null;
    }

}
