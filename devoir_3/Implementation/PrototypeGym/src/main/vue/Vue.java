package main.vue;

import main.controleur.IVerificateur;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe abstraite de la vue
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
     * Efface l'écran dans la console
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
     * Affiche l'information dans la console
     * @param information information à afficher dans la console
     */
    public static void afficher(String information) {
        System.out.println(information);
    }

    /**
     * Prend la ligne dans la console
     * @return information saisie dans la console
     */
    public static String getTexteConsole() {
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Lit la console tant que la condition n'est pas respectée
     * @param verificateur condition de la validation d'information
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
     * Lit la console tant que la condition n'est pas respectée et affiche le message d'erreur si
     * l'information est invalide
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
     * Lit la console tant que l'entrée n'est pas saisie
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
     * Lit la console tant que la réponse ne correspond pas à la liste des réponses proposées
     * @param jeuDeReponses liste des réponses possibles
     * @return reponse saisie dans la console
     */
    public static String acquisitionReponse(List<String> jeuDeReponses) {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(!jeuDeReponses.contains(reponse) && !reponse.equals("0"));
        return reponse;
    }
}