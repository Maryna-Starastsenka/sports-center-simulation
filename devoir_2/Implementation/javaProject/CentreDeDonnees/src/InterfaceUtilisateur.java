import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class InterfaceUtilisateur {
    static Controleur controleur;
    static NomsMenus menuCourant = NomsMenus.PRINCIPAL;

    public InterfaceUtilisateur() {
    }

    public static void ReadCommandLine() {
        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            option = scanner.nextLine();
            controleur.input(menuCourant, option);
        } while (true);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void afficherMenuPrincipal() {
        menuCourant = NomsMenus.PRINCIPAL;
        StringBuilder sb = new StringBuilder();
        clearScreen();
        sb.append("---------------Accueil---------------\n");
        sb.append("1. Demande d'accès au gym\n");
        sb.append("2. Gestion d'un compte\n");
        sb.append("3. Gestion d'un service\n");
        sb.append("4. Inscription à une séance\n");
        sb.append("5. Confirmation de la présence\n");
        sb.append("6. Consultation d'une séance\n");
        sb.append("7. Procédure comptable\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficherDemandeAcces() {
        menuCourant = NomsMenus.ACCES_GYM;
        StringBuilder sb = new StringBuilder();
        clearScreen();
        sb.append("------Demande d'accès au gym------\n");
        sb.append("1. Membre\n");
        sb.append("2. Professionnel\n");
        sb.append("3. Retour au menu principal\n");
        sb.append("X. Sortir");

        System.out.println(sb.toString());
    }

    public static void afficherGestionCompte() {
        menuCourant = NomsMenus.GESTION_COMPTE;
        StringBuilder sb = new StringBuilder();
        clearScreen();
        sb.append("------Gestion d'un compte------\n");
        sb.append("1. Création d'un compte\n");
        sb.append("2. Gestion d'un compte existant\n");
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
