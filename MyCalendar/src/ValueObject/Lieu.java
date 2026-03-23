package ValueObject;

public record Lieu(String valeur) {
    public Lieu {
        if (valeur == null || valeur.isBlank()) throw new IllegalArgumentException("Le lieu ne peut pas être null");
    }
}
