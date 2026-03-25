package ValueObject;

import java.time.LocalDateTime;

public record DateEvenement(LocalDateTime valeur) {
    public DateEvenement {
        if (valeur == null) throw new IllegalArgumentException("La date ne peut pas être null");
    }

    public boolean estAvant(DateEvenement autre) {
        return valeur.isBefore(autre.valeur);
    }

    public DateEvenement getDateFin(DureeMinutes dureeMinutes) {
        return new DateEvenement(valeur.plusMinutes(dureeMinutes.valeur()));
    }
}
