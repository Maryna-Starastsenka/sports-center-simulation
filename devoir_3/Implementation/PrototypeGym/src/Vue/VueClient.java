package Vue;

import Controleur.ControleurClient;

public abstract class VueClient<T> extends Vue {

    ControleurClient controleurClient;

    public abstract void creerClient();

    public abstract void mettreClientAJour();

    public abstract void authentifier();

    public abstract void accesAutorise(T client);
    public abstract void accesRefuse(String idClient);
}
