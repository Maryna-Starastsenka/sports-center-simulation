package main.vue;

public abstract class VuePlateforme extends Vue {

    public void start() {
        while (true) {
            menuAccueil();
        }
    }

    public abstract void menuAccueil();

    public abstract void authentification();

    public void retourMenuPrincipal() {
        afficher("\nAppuyez sur ENTREE pour revenir à l'écran principal");
        getTexteConsole();
//        menuAccueil();
    }

}
