package Vue;

import Controleur.ControleurClient;

public abstract class VuePlateforme extends Vue {

    public void start() {
        menuLogiciel();
    }

    public abstract void menuLogiciel();

    public abstract void authentifier();

}
