package ValueObject;

public record Proprietaire(String nom) {
    public Proprietaire {
        if(nom==null || nom.isBlank()) throw new IllegalArgumentException("nom n'existe pas");
    }
}
