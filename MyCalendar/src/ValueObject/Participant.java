package ValueObject;

public record Participant(String participants) {
    public Participant {
        if (participants == null || participants.isBlank()) {
            throw new IllegalArgumentException("Le participants du participant ne peut pas être vide");
        }
    }
}
