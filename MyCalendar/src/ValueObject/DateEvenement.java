package ValueObject;

import java.time.LocalDateTime;

public record DateEvenement(LocalDateTime valeur) {
    public DateEvenement {
        if (valeur == null) throw new IllegalArgumentException("La date ne peut pas être null");
    }
}
