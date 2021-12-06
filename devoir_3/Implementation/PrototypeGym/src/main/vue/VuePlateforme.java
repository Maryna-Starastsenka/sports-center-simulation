package main.vue;

/**
 * Classe abstraite Vue Plateforme qui hérite de la classe Vue.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public abstract class VuePlateforme extends Vue {

    /**
     * Affiche le menu d'accueil du logiciel
     */
    public abstract void menuAccueil();

    /**
     * Authentifie un client dans le système
     */
    public abstract void authentification();

    /**
     * Demarre le logiciel à partir du menu d'accueil en boucle tant que l'utilisateur ne quitte pas l'application
     */
    public void start() {
        while (true) {
            menuAccueil();
        }
    }

    /**
     * Retourne au menu d'accueil lorsque ENTREE est appuyée
     */
    public void retourMenuPrincipal() {
        afficher("\nAppuyez sur ENTREE pour revenir à l'écran principal");
        getTexteConsole();
    }

}
