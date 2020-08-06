package main.controleur;

/**
 * Interface Vérificateur pour la validation de l'entrée.
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public interface IVerificateur {
    /**
     * Méthode de vérification dont la signature est utilisée pour valider les entrées
     * @param s entrée
     * @return vrai si l'entrée est valide
     */
    boolean verifier(String s);
}
