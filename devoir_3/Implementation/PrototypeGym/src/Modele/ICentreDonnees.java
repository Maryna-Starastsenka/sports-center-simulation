package Modele;

public interface ICentreDonnees<T> {
    String creer();
    T lire(String id);
    void mettreAJour(String id);
    void supprimer(String id);
}
