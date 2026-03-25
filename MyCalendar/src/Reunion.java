import ValueObject.*;

public class Reunion extends Event {
    private final Lieu lieu;
    private final Participant participants;

    public Reunion(TitreEvenement title, Proprietaire proprietaire, DateEvenement dateDebut,
                   DureeMinutes dureeMinutes, Lieu lieu, Participant participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title.valeur() + " à " + lieu.valeur() + " avec " + participants.participants();
    }

    @Override
    public boolean isWithinRange(DateEvenement debut, DateEvenement fin) {
        return !dateDebut.valeur().isBefore(debut.valeur()) && !dateDebut.valeur().isAfter(fin.valeur());
    }
}
