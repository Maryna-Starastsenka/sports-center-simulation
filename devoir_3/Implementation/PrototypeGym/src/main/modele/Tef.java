package main.modele;

import java.time.LocalDateTime;
import java.util.List;

import main.controleur.Verificateurs;


public abstract class Tef {
    private String nom;
    private String numero;
    private String adresse;
    private String ville;
    private String province;
    private String codePostal;


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
    	String print = "Nom : " + this.nom + "\n";
    	print += "Date de facturation : " + Verificateurs.localDateTimeFormatter.format(LocalDateTime.now());
    	print += "Numéro : " + this.numero + "\n";
    	print += "Adresse : " + this.adresse + "\n";
    	print += "Ville : " + this.ville + "\n";
    	print += "Province : " + this.province + "\n";
    	print += "Code postal : " + this.codePostal + "\n";
    	return print;
    }

}