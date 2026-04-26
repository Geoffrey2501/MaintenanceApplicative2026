package ValueObject;


public record Identifiant(String valeur) {
    public Identifiant {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être vide");
        }
    }
}
