package main.vue;

import main.controleur.IVerificateur;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe abstraite dont héritent toutes les vues
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public abstract class Vue {

    /**
     * Contructeur de Vue
     */
    public Vue() {
    }

    /**
     * Efface l'écran dans la console en prenant en compte l'OS
     * Dans Windows, on efface vraiment ce que contient la console alors que sur UNIX on scrolle la vue
     */
    public static void effacerEcran() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Clear screen dans la commande Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                // Dans les environnements UNIX, on déplace l'information déjà présente
                // en dehors de la zone visible mais il est possible de la retrouver en scrollant vers le haut
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    /**
     * Affiche du texte dans la console en sautant une ligne à la fin
     * @param information information/texte à afficher dans la console
     */
    public static void afficher(String information) {
        System.out.println(information);
    }

    /**
     * Indique à l'usager qu'on attend qu'il écrive quelque chose dans la console et récupération de son entrée
     * @return information saisie dans la console quand l'usager appuie sur la touche ENTREE
     */
    public static String getTexteConsole() {
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Lit la console tant que la condition n'est pas respectée
     * et que l'usager ne souhaite pas revenir au menu principal (touche 0)
     * @param verificateur condition de validation de l'information
     * @return information saisie dans la console qui respecte la condition
     */
    public static String acquisitionReponse(IVerificateur verificateur) {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(!verificateur.verifier(reponse) && !reponse.equals("0"));
        return reponse;
    }

    /**
     * Lit la console tant que la condition n'est pas respectée et que
     * l'usager ne souhaite pas revenir au menu principal (touche 0)
     * et affiche un message d'erreur tant que l'information est invalide
     * @param verificateur condition de la validation d'information
     * @param messageErreur message d'erreur pour signaler que l'information saisie n'est pas valide
     * @return information saisie dans la console qui respecte la condition
     */
    public static String acquisitionReponse(IVerificateur verificateur, String messageErreur) {
        String reponse = null;
        do {
            if (reponse != null && !verificateur.verifier(reponse)) {
                afficher(messageErreur);
            }
            reponse = getTexteConsole();
        } while(!verificateur.verifier(reponse) && !reponse.equals("0"));
        return reponse;
    }

    /**
     * Lit la console tant que l'entrée validée de l'utilisateur ne comporte pas au moins un caractère
     * @return information saisie dans la console
     */
    public static String acquisitionReponse() {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(reponse.length() < 1);
        return reponse;
    }

    /**
     * Lit la console tant que la réponse ne correspond pas à la liste des réponses proposées et que l'usager
     * ne souhaite pas revenir au menu principal (touche 0)
     * @param jeuDeReponses liste des réponses acceptées
     * @return réponse saisie dans la console
     */
    public static String acquisitionReponse(List<String> jeuDeReponses) {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(!jeuDeReponses.contains(reponse) && !reponse.equals("0"));
        return reponse;
    }
}