package main.vue;

import main.controleur.IVerificateur;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class Vue {
//    public ControleurClient controleurClient;
//    public ControleurService controleurService;

    public Vue() {

    }

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
        } catch (IOException | InterruptedException ex) {}
    }

    public static void afficher(String information) {
        System.out.println(information);
    }

    public static String getTexteConsole() {
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String acquisitionReponse(IVerificateur verificateur) {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(!verificateur.verifier(reponse) && !reponse.equals("0"));
        return reponse;
    }

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

    public static String acquisitionReponse() {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(reponse.length() < 1 && !reponse.equals("0"));
        return reponse;
    }

    public static String acquisitionReponse(List<String> jeuDeReponses) {
        String reponse;
        do {
            reponse = getTexteConsole();
        } while(!jeuDeReponses.contains(reponse) && !reponse.equals("0"));
        return reponse;
    }
}