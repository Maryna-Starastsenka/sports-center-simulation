package Vue;

import java.time.LocalDate;

public interface ICentreDonnees<T> {
    T creer(T client);
    T lire(String id);
    void mettreAJour(String id);
    void supprimer(String id);
}
