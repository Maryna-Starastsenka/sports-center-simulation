package main.vue;

/**
 * Classe abstraite Vue Plateforme hérite la classe Vue.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public abstract class VuePlateforme extends Vue {

    /**
     * Affiche le menu d'accueil du logiciel
     */
    public abstract void menuAccueil();

    /**
     * Fait l''authentification d'un client
     */
    public abstract void authentification();

    /**
     * Demarre le logiciel et fait un appel de l'affichage du menu d'accueil
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
