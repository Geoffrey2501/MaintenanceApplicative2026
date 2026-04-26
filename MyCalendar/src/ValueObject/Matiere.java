package ValueObject;

public record Matiere(String valeur) {
    public Matiere {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("La matière ne peut pas être vide");
        }
    }
}