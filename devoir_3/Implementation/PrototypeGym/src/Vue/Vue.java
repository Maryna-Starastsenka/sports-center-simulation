package Vue;

import Controleur.IVerificateur;

import java.util.List;
import java.util.Scanner;

public abstract class Vue {
//    public ControleurClient controleurClient;
//    public ControleurService controleurService;

    public Vue() {

    }

    public static void effacerEcran() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
        } while(!jeuDeReponses.contains(reponse));
        return reponse;
    }
}