package Vue;

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

//    protected void afficherMenuPrincipal() {
//        StringBuilder sb = new StringBuilder();
//        effacerEcran();
//        sb.append("---------------Accueil---------------\n");
//        sb.append("1. Demande d'accès au gym\n");
//        sb.append("2. Gestion d'un compte\n");
//        sb.append("3. Gestion d'un service\n");
//        sb.append("4. Modele.Inscription à une séance\n");
//        sb.append("5. Confirmation de la présence\n");
//        sb.append("6. Consultation d'une séance\n");
//        sb.append("7. Procédure comptable\n");
//        sb.append("8. Mettre à jour statut membres\n");
//        sb.append("X. Sortir");
//
//        System.out.println(sb.toString());
//    }

//    public static void afficherDemandeAcces() {
//        StringBuilder sb = new StringBuilder();
//        effacerEcran();
//        sb.append("------Demande d'accès au gym------\n");
//        sb.append("1. Modele.Membre\n");
//        sb.append("2. Modele.Professionnel\n");
//        sb.append("3. Retour au menu principal\n");
//        sb.append("X. Sortir");
//
//        System.out.println(sb.toString());
//    }

    public static void afficherGestionCompte() {
        StringBuilder sb = new StringBuilder();
        effacerEcran();
        sb.append("------Gestion d'un compte------\n");
        sb.append("1. Création d'un compte\n");
        sb.append("2. Gestion d'un compte existant\n");
        sb.append("3. Retour au menu principal\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficherGestionService() {
        StringBuilder sb = new StringBuilder();
        effacerEcran();
        sb.append("------Gestion d'un service------\n");
        sb.append("1. Création d'un nouveau service\n");
        sb.append("2. Gestion d'un service existant\n");
        sb.append("3. Retour au menu principal\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficher(String information) {
        System.out.println(information);
    }

    public static String getTexteConsole() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String acquisitionReponse(IVerificateur verificateur) {
        String reponse;
        do {
            System.out.print("> ");
            reponse = getTexteConsole();
        } while(!verificateur.verifier(reponse));
        return reponse;
    }

    public static String acquisitionReponse() {
        String reponse;
        do {
            System.out.print("> ");
            reponse = getTexteConsole();
        } while(reponse.length() < 1);
        return reponse;
    }

    public static String acquisitionReponse(List<String> jeuDeReponses) {
        String reponse;
        do {
            System.out.print("> ");
            reponse = getTexteConsole();
        } while(!jeuDeReponses.contains(reponse));
        return reponse;
    }
}