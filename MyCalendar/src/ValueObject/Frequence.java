package ValueObject;

public record Frequence(int valeur) {
    public Frequence {
        if (valeur < 0) throw new IllegalArgumentException("La fréquence doit être supérieure à 0");
    }
}