package main.controleur;

import main.modele.Jour;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe Helper qui offre un certain nombre d'outils à toutes les classes du programme
 * Il s'agit notamment de convertisseurs ou de validateurs
 * @author Maryna Starastsenka
 * @author Alex Defoy
 */
public class Helper {

    /**
     * Id de la zone de localisation
     */
    public static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * Pattern d'affichage d'un LocalDateTime
     */
    public static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Pattern d'affichage d'un LocalDate
     */
    public static DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Pattern d'affichage d'un LocalTime
     */
    public static DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Convertir les jours de la semaine de l'enum en français vers l'enum en anglais
     * @param jour jour de l'enum en français
     * @return jour de l'enum en anglais
     */
    public static DayOfWeek getDayOfWeek(Jour jour) {
        return switch (jour) {
            case LUNDI -> DayOfWeek.MONDAY;
            case MARDI -> DayOfWeek.TUESDAY;
            case MERCREDI -> DayOfWeek.WEDNESDAY;
            case JEUDI -> DayOfWeek.THURSDAY;
            case VENDREDI -> DayOfWeek.FRIDAY;
            case SAMEDI -> DayOfWeek.SATURDAY;
            case DIMANCHE -> DayOfWeek.SUNDAY;
        };
    }

    /**
     * Obtenir la date d'aujourd'hui
     * @return c
     */
    public static LocalDate today() {
        return LocalDate.now(zoneId) ;
    }

    /**
     * Obtenir la date d'aujourd'hui y compris l'heure précise
     * @return date d'aujourd'hui et heure précise
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(zoneId) ;
    }

    /**
     * Parser une date depuis une string
     * @param stringDate date à parser
     * @return date convertie
     */
    public static LocalDate getDateFromString(String stringDate) {
        return LocalDate.parse(stringDate, localDateFormatter);
    }

    /**
     * Parser un horaire depuis une string
     * @param stringTime horaire à parser
     * @return horaire converti
     */
    public static LocalTime getHeureFromString(String stringTime) {
        return LocalTime.parse(stringTime, localTimeFormatter);
    }

    /**
     * Parser un jour en français depuis une string
     * @param stringJour jour en français
     * @return jour en enum français
     */
    public static Jour getJourFromString(String stringJour) {
        return Jour.valueOf(stringJour.toUpperCase());
    }

    /**
     * Parser un entier depuis une string
     * @param stringInt entier en string
     * @return entier en int
     */
    public static int getIntFromString(String stringInt) {
        return Integer.parseInt(stringInt);
    }

    /**
     * Parser un nombre décimal depuis une string
     * @param stringDouble nombre décimal en string
     * @return nombre décimal en double
     */
    public static double getDoubleFromString(String stringDouble) {
        return Double.parseDouble(stringDouble);
    }

    /**
     * Validation du format d'une date en string
     * @param s date en string
     * @return vrai si le format de la date est valide
     */
    public static boolean dateValide (String s){
        try {
            getDateFromString(s);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validation d'un code postal
     * @param s code postal en string
     * @return vrai si le code postal fait 6 ou 7 caractères
     */
    public static boolean codePostalValide(String s) {
        return s != null && (s.length() == 6 || s.length() == 7) ;
    }

    /**
     * Validation d'une adresse courriel
     * @param s adresse courriel en string
     * @return vrai si l'adresse courriel a un format standard
     */
    public static boolean courrielValide (String s) {
        String regexPattern = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return s.matches(regexPattern);
    }

    /**
     * Validation d'un nom
     * @param s nom en string
     * @return vrai si le nom fait au moins un caractère
     */
    public static boolean nomValide (String s){
        return s.length() >= 1;
    }

    /**
     * Validation d'un horaire
     * @param s horaire en string
     * @return vrai si l'horraire est au format HH:mm
     */
    public static boolean horaireValide(String s) {
        String regexPattern = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$";
        return s.matches(regexPattern);
    }

    /**
     * Validation d'un jour de semaine
     * @param s jour en string et en français
     * @return vrai si le jour en string peut être converti en enum
     */
    public static boolean jourSemaineValide(String s) {
        for (Jour jour : Jour.values()) {
            if (s.toUpperCase().equals(jour.name().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validation de la structure de l'identifiant d'un client
     * @param s identifiant du client
     * @return vrai si l'identifiant est un entier de 9 chiffres
     */
    public static boolean identifiantClientValide(String s) {
        return s != null && s.length() == 9 && intValide(s);
    }

    /**
     * Validation des frais de service
     * @param s montant des frais de service en string
     * @return vrai si le montant entré peut être converti en double et a le bon nombre de chiffres
     */
    public static boolean fraisServiceValide(String s) {
        String regexPattern = "^\\d+([.,]\\d{1,2})?$";
        return s.matches(regexPattern);
    }

    /**
     * Valide si l'entier peut être parsé
     * @param s entier en string
     * @return vrai si l'entier peut être parsé
     */
    public static boolean intValide(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valide si l'adresse est valide
     * @param s adresse en string
     * @return vrai si l'adresse fait plus d'un caractère
     */
    public static boolean adresseValide (String s){
        return s.length() >= 1;
    }

    /**
     * Valide si la ville est valide
     * @param s ville en string
     * @return vrai si la ville fait plus d'un caractère
     */
    public static boolean villeValide (String s){
        return s.length() >= 1;
    }

    /**
     * Valide si la province est valide
     * @param s province en string
     * @return vrai si la province fait plus d'un caractère
     */
    public static boolean provinceValide (String s){
        return s.length() >= 1;
    }

    /**
     * Valide si le numéro de téléphone est valide
     * @param s numéro de téléphone en string
     * @return vrai si le numéro de téléphone est dans le format xxx-xxx-xxxx
     */
    public static boolean telephoneValide (String s){
        String regexPattern = "\\d{3}-\\d{3}-\\d{4}";
        return s.matches(regexPattern);
    }
}
