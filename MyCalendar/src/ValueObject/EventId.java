package ValueObject;

import java.util.UUID;

public record EventId(String valeur) {
    public EventId {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("L'identifiant metier ne peut pas etre vide");
        }
    }

    public static EventId generer() {
        return new EventId(UUID.randomUUID().toString());
    }
}
