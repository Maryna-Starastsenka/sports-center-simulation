package main.vue;

import java.util.Arrays;

/**
 * Classe Vue Choix Plateforme hérite la classe Vue. Permet de faire le choix entre.
 * le logiciel pour pour l'agent et l'application mobile
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class VueChoixPlateforme extends Vue {

    /**
     * Constructeur de VueChoixPlateforme
     */
    public VueChoixPlateforme() {
    }

    /**
     * Affiche le choix des plateformes et instancie la plateforme choisie
     * @return instance de VueAgent ou de VueApplicationMobile
     */
    public static VuePlateforme selectionnerOption() {
        effacerEcran();

        afficher("Veuillez choisir la plateforme que vous souhaitez lancer :");
        afficher("1. Version agent");
        afficher("2. Version application");

        var reponse = acquisitionReponse(Arrays.asList("1", "2"));
        switch (reponse) {
            case "1" -> {
                afficher("Lancement de la version agent...");
                return new VueAgent();
            }
            case "2" -> {
                afficher("Lancement de la version application...");
                return new VueApplicationMobile();
            }
        }

        return null;
    }

}
