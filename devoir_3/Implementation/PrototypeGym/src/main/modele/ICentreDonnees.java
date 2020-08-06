package main.modele;

/**
 * Interface de Cendre de Données
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public interface ICentreDonnees<T> {
    void creer(T client);
    T lire(String id);
    void mettreAJour(String id, Champs champs, String valeur);
    void supprimer(String id);
    void ajouterClient(T client);
}
