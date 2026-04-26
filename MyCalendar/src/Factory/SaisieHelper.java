package Factory;

import Service.InputHandler;
import ValueObject.DateEvenement;
import java.time.LocalDateTime;

/**
 * Helper utilitaire pour centraliser les saisies complexes via la console.
 */
public class SaisieHelper {

    /**
     * Orchestre la lecture de plusieurs entiers pour construire un objet DateEvenement.
     * Utilise les méthodes de saisie sécurisées de l'InputHandler.
     */
    public static DateEvenement saisirDate(InputHandler input) {
        int annee = input.lireEntier("Année (AAAA) : ");
        int mois = input.lireEntier("Mois (1-12) : ");
        int jour = input.lireEntier("Jour (1-31) : ");
        int heure = input.lireEntier("Heure (0-23) : ");
        int minute = input.lireEntier("Minute (0-59) : ");

        // Retourne le Value Object attendu par le domaine métier
        return new DateEvenement(LocalDateTime.of(annee, mois, jour, heure, minute));
    }
}