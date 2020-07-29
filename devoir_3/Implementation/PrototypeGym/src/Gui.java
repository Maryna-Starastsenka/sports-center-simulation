import java.util.Scanner;

public class Gui {
    static Controleur controleur;

    public Gui() {
    }

    public static void effacerEcran() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void afficherMenuPrincipal() {
        StringBuilder sb = new StringBuilder();
        effacerEcran();
        sb.append("---------------Accueil---------------\n");
        sb.append("1. Demande d'acc�s au gym\n");
        sb.append("2. Gestion d'un compte\n");
        sb.append("3. Gestion d'un service\n");
        sb.append("4. Inscription � une s�ance\n");
        sb.append("5. Confirmation de la pr�sence\n");
        sb.append("6. Consultation d'une s�ance\n");
        sb.append("7. Proc�dure comptable\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficherDemandeAcces() {
        StringBuilder sb = new StringBuilder();
        effacerEcran();
        sb.append("------Demande d'acc�s au gym------\n");
        sb.append("1. Membre\n");
        sb.append("2. Professionnel\n");
        sb.append("3. Retour au menu principal\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficherGestionCompte() {
        StringBuilder sb = new StringBuilder();
        effacerEcran();
        sb.append("------Gestion d'un compte------\n");
        sb.append("1. Cr�ation d'un compte\n");
        sb.append("2. Gestion d'un compte existant\n");
        sb.append("3. Retour au menu principal\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficherGestionService() {
        StringBuilder sb = new StringBuilder();
        effacerEcran();
        sb.append("------Gestion d'un service------\n");
        sb.append("1. Cr�ation d'un nouveau service\n");
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
}