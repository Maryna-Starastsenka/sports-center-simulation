package Vue;

import Controleur.ControleurClient;

public abstract class VueClient<T> extends Vue {

    protected String typeClient = "Client";
    ControleurClient controleurClient;

    public abstract void enTeteGestionCompte();

    public abstract void gestionCompte();

    public abstract void creerClient();

    public abstract void gestionCompteExistant();

    public void authentifier() {
        afficher(String.format("Entrez l'identifiant du %s (9 chiffres) :", typeClient));
        String reponse = acquisitionReponse(Verificateurs::identifiantClientValide);
        if (controleurClient.authentifier(this, reponse)) {
            accesAutorise(reponse);
        } else {
            accesRefuse(reponse);
        }
    }

    public abstract void accesAutorise(String idClient);
    public abstract void accesRefuse(String idClient);

    public abstract void confirmerEnregistrement(String id);
}
