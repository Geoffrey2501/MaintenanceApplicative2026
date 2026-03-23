package ValueObject;

public record Participant(String nom) {
    public Participant {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom du participant ne peut pas être vide");
        }
    }
}
