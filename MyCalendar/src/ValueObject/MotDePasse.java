package ValueObject;

public record MotDePasse(String valeur) {
    public MotDePasse {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }
    }

    public boolean correspond(MotDePasse autre) {
        return this.valeur.equals(autre.valeur());
    }
}