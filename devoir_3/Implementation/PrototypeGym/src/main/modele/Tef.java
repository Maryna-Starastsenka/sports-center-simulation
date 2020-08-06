package main.modele;

import java.time.LocalDateTime;
import main.controleur.Helper;

/**
 * Classe abstraite Tef. Enregistrement des informations nécessaires au TEF
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public abstract class Tef {
    private final String nom;
    private final String numero;
    private final String adresse;
    private final String ville;
    private final String province;
    private final String codePostal;

    /**
     * Constructeur de Tef
     * @param nom nom du client
     * @param numero numéro du client
     * @param adresse adresse du client
     * @param ville ville du client
     * @param province province du client
     * @param codePostal code postal du client
     */
    public Tef(String nom, String numero, String adresse, String ville, String province, String codePostal) {
        this.nom = nom;
        this.numero = numero;
        this.adresse = adresse;
        this.ville = ville;
        this.province = province;
        this.codePostal = codePostal;
    }

    public String getNom() {
        return nom;
    }

    public String getNumero() {
        return numero;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }
    
    public String getProvince() {
        return province;
    }
    
    public String getCodePostal() {
        return codePostal;
    }

    @Override
    public String toString() {
        return "Nom : " + this.nom + "\n" +
                ("Date de facturation : " + Helper.localDateTimeFormatter.format(LocalDateTime.now())) +
                ("Numéro : " + this.numero + "\n") +
                ("Adresse : " + this.adresse + "\n") + ("Ville : " + this.ville + "\n") +
                ("Province : " + this.province + "\n") + ("Code postal : " + this.codePostal + "\n");
    }

}