package Modele;

import java.util.List;

public interface ICentreDonnees<T> {
    T creer(T client);
    T lire(String id);
    void mettreAJour(String id, IChamps champsClient, String valeur);
    void supprimer(String id);
    void ajouterClient(T client);
    List<Client> getClients();
}
