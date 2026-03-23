package ValueObject;

public record DureeMinutes(int valeur) {
    public DureeMinutes {
        if (valeur < 0) throw new IllegalArgumentException("La durée ne peut pas être négative");
    }
}
